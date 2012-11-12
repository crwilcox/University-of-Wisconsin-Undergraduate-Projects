/*
* Filename: core_routing_state_machine.v
* Author: Christopher Wilcox and Dan Drusch (The Fighting Mongooses)
* Date: 2010-11-15
* Description: This file contains the Routing State Machine for the core.
*/
/****************************************************************************
*																			*
*							Routing State Machine							*
*																			*
****************************************************************************/
module Routing_State_Machine(//INPUTS
							Clk_r, Rst_n,		
							Router_Address, Link_Config, 
							
							Packet_From_Link_N, Packet_From_Link_S, Packet_From_Link_W, Packet_From_Link_E, Packet_From_Node,
							RxQueue_Empty_N, RxQueue_Empty_S, RxQueue_Empty_W, RxQueue_Empty_E,
							TxQueue_Full_N, TxQueue_Full_S, TxQueue_Full_W, TxQueue_Full_E, To_Node_Full,
							Node_Queue_Empty,
							
							//OUTPUTS
							Packet_To_Link_N, Packet_To_Link_S, Packet_To_Link_W, Packet_To_Link_E, Packet_To_Node,
							TxQueue_Write_N, TxQueue_Write_S, TxQueue_Write_W, TxQueue_Write_E, To_Node_Write,
							RxQueue_Read_N, RxQueue_Read_S, RxQueue_Read_W, RxQueue_Read_E,
							Node_Read
							 );
//DON'T CHANGE THESE DIGITS.  THEY ARE SPECIAL INDEXES AND ORDERING IS IMPORTANT!!!
localparam NODE 	= 3'd4;
localparam NLINK 	= 3'd3;
localparam SLINK	= 3'd2;
localparam WLINK	= 3'd1;
localparam ELINK	= 3'd0;

//THESE PARAMS CAN BE MODIFIED IF DESIRED
localparam MAX_DELIVERIES =  8'b1111_1111;							 
							 
// Global Interface
	input Clk_r; 	//The global Core clock. This clock is synchronized between Core and Processor 
					//Node. The TX & RX do not necessarily use the same clock.
	input Rst_n;	//The global asynchronous active-low reset signal.

// Router Core / Node Interface
	input [7:0] Router_Address;		//Address of the Router. MS 4 bits indicate WE-Pos, LS 4 bits indicate SN-Pos
	input [3:0] Link_Config; 		//Indicates Enabled(1)/Disabled(0) Links in format NSWE
// Link/Node Ins	
	input [31:0] Packet_From_Link_N, Packet_From_Link_S, Packet_From_Link_W, Packet_From_Link_E, Packet_From_Node;//Use to get packet from its location
	input RxQueue_Empty_N, RxQueue_Empty_S, RxQueue_Empty_W, RxQueue_Empty_E, Node_Queue_Empty;//This Goes High to Let us Know there is no work to be done...
	input TxQueue_Full_N, TxQueue_Full_S, TxQueue_Full_W, TxQueue_Full_E, To_Node_Full;//TxQueue_Full goes high to say that the TxModule cannot accept new requests
// Link/Node Outs
	output reg [31:0] Packet_To_Link_N, Packet_To_Link_S, Packet_To_Link_W, Packet_To_Link_E, Packet_To_Node;//used to send data to a new router, or back to the node if this is the final destination
	output TxQueue_Write_N, TxQueue_Write_S, TxQueue_Write_W, TxQueue_Write_E;
	output reg To_Node_Write;
	output RxQueue_Read_N, RxQueue_Read_S, RxQueue_Read_W, RxQueue_Read_E, Node_Read; // All reads should be held high for a cycle as to let the control/node know that you have read the data
//////////////////////////////////////////////////////////////////////////////////////////////////////
    reg[31:0] chosenPacket;
	//THESE ARE JUST COMBINATIONS OF THE INPUT SIGNALS TO BE USED TO MAKE
	//LOOPING MORE SIMPLE
	wire[4:0] Queue_Empty, TxQueue_Full;
	reg[4:0] TxQueue_Write, RxQueue_Read;
	assign Queue_Empty = {Node_Queue_Empty, RxQueue_Empty_N, RxQueue_Empty_S, RxQueue_Empty_W, RxQueue_Empty_E};
	assign TxQueue_Full = { TxQueue_Full_N, TxQueue_Full_S, TxQueue_Full_W, TxQueue_Full_E };
	assign {TxQueue_Write_N, TxQueue_Write_S, TxQueue_Write_W, TxQueue_Write_E} = TxQueue_Write;
	assign {Node_Read, RxQueue_Read_N, RxQueue_Read_S, RxQueue_Read_W, RxQueue_Read_E} = RxQueue_Read; 

	
    wire[3:0] directionToGo; // hot encoding of NSWE Possible directions.  if all 0s we should abandone the packet
	//Find the direction(s) to go taking into account disabled nodes
	assign directionToGo[3] = ((Router_Address[3:0] < chosenPacket[27:24]) && Link_Config[3]); 	//coreNS < packetNS
	assign directionToGo[2] = ((Router_Address[3:0] > chosenPacket[27:24]) && Link_Config[2]);	//coreNS > packetNS
	assign directionToGo[1] = ((Router_Address[7:4] > chosenPacket[31:28]) && Link_Config[1]);	//coreWE > packetWE
	assign directionToGo[0] = ((Router_Address[7:4] < chosenPacket[31:28]) && Link_Config[0]);	//coreWE < packetWE	

	reg[2:0] dataLocation; //this variable will go from 0 to 4 representing node, nLink, sLink, wLink, eLink, of which to check for data...	We will also use this to know data origin.
//[31:24] --> |WE Pos | NS Pos|
//[23:16] --> Use as Count for packet dropping...
//[15:0] --> DATA
   reg[1:0] currStage;
   reg[1:0] nextStage;
	
	always @(posedge Clk_r, negedge Rst_n) begin
		if (Rst_n == 1'b0) begin
			dataLocation = NODE;
			TxQueue_Write = 4'b0000;
			RxQueue_Read = 5'b00000;  
			currStage = 2'b0;
			nextStage = 2'b0;
			To_Node_Write = 1'b0;
			Packet_To_Node = 32'hFFFF_FFFF;//TODO: LETS SET TO ALL ONES, SEEING AS THAT WOULD NEVER EXIST
		end
		else begin
			currStage = nextStage; // UPDATE THE STAGE
			case (currStage)
			////////// STAGE 0: FIND PACKET WE WILL WORK ON ////////// 
				2'b0: begin 
			   		//Set all write signals low
					TxQueue_Write = 4'b0000;
					To_Node_Write = 1'b0;
	
					//Advance the packet locator (dataLocation)
					dataLocation = dataLocation + 1;
					if(dataLocation > 3'd4) dataLocation = 3'd0;// advance dataLocation by 1.  Keep in range of the 4 links and node
	
					//if this location is not disabled and not empty, grab the packet
					if(!Queue_Empty[dataLocation] && ((dataLocation == NODE) || Link_Config[dataLocation])) begin //if node is nonempty and enabled, or if node, just nonempty
						case (dataLocation) //set chosen packet to packet on node/rx
		                 	NLINK: chosenPacket = Packet_From_Link_N;
		            		SLINK: chosenPacket = Packet_From_Link_S;
		                  	WLINK: chosenPacket = Packet_From_Link_W;
		                  	ELINK: chosenPacket = Packet_From_Link_E;
		                  	NODE: chosenPacket = {Packet_From_Node[31:24], 8'd0, Packet_From_Node[15:0]};//SET OUR ALGO BITS TO ZERO
	                  	endcase
						RxQueue_Read[dataLocation] = 1'b1;//assert read for packet (will deassert in next stage)
						nextStage = 2'd1;//we wish to go to stage 1 next
					end
					else begin 	//else, loop to the next dataLocation and try again
						nextStage = currStage; // stay in this stage
					end
				end
			////////// STAGE 1: FIND THE DIRECTIONS WE SHOULD TAKE THIS PACKET & SEND IT!////////// 
				2'd1: begin 
					RxQueue_Read = 4'b0000;//deassert read for packet (was asserted by prev stage)
					
					chosenPacket[23:16] = chosenPacket[23:16] + 1;//ADVANCE CHOSEN PACKETS COUNT	
	
				//CHECK IF THIS IS THE DEST
					if (chosenPacket[31:24] == Router_Address) begin
						if(!To_Node_Full) begin //we have reached the destination, send to processor node
							Packet_To_Node = chosenPacket; 
							To_Node_Write = 1'b1;
							//we are done here, lets go back to stage 0
							nextStage = 2'd0;
						end
						else begin//can't send to node yet.  Try again next cycle with same data
							nextStage = currStage;//Can't Leave the stage as we haven't given the packet to the node manager
						end
					end
				//CHECK IF THIS PACKET HAS BEEN TRANSFERRED TOO MUCH...
					else if (chosenPacket[23:16] >= MAX_DELIVERIES) begin
						nextStage = 2'b0;  //drop packet, ie, go back to stage 0, find a new one
					end
				//SINCE NEITHER OF THE PREVIOUS BLOCKS TRIGGRED, WE SHOULD TRY
				//TO SEND THIS PACKET.  IF THE PACKET IS BLOCKED BY DOWN
				//LINKS, WE WILL DROP IT, OTHERWISE, THE FSM WILL GIVE THE
				//DATA TO THE FIRST AVAILABLE LINK THAT WOULD MOVE OUR PACKET
				//TOWARDS ITS DESTINATION
					else begin
						//FIRST, MAKE SURE THERE IS A DIRECTION TO GO
						if(directionToGo == 4'b0000) begin //we should just drop the packet as there is nowhere to send it
							nextStage = 2'b0;
						end

						//GREAT, WE ARE NOT BLOCKED, LETS TRY TO TRAVEL.  IF
						//THE NODE IS FULL, WE WILL NOT TRY TO TRAVEL ON IT
						//AND INSTEAD WILL TRY OTHER DIRECTIONS WHICH WILL
						//BRING US CLOSER TO OUR DESTINATION
						if((Router_Address[3:0] < chosenPacket[27:24]) && Link_Config[3] && !TxQueue_Full_N) begin 	//coreNS < packetNS
							//GO NORTH!
							Packet_To_Link_N = chosenPacket;
							TxQueue_Write[3] = 1'b1;
							nextStage = 2'd0;
						end
						else if((Router_Address[3:0] > chosenPacket[27:24]) && Link_Config[2] && !TxQueue_Full_S) begin	//coreNS > packetNS
							//GO SOUTH!
						    Packet_To_Link_S = chosenPacket;
							TxQueue_Write[2] = 1'b1;
							nextStage = 2'd0;
						end
						else if((Router_Address[7:4] > chosenPacket[31:28]) && Link_Config[1] && !TxQueue_Full_W) begin 		//coreWE > packetWE
							//GO WEST!
						    Packet_To_Link_W = chosenPacket;
							TxQueue_Write[1] = 1'b1;
							nextStage = 2'd0;
						end
						else if((Router_Address[7:4] < chosenPacket[31:28]) && Link_Config[0] & !TxQueue_Full_E) begin	//coreWE < packetWE
							//GO EAST!
						    Packet_To_Link_E = chosenPacket;
							TxQueue_Write[0] = 1'b1;
							nextStage = 2'd0;
						end
						else begin //ALL OF THE DIRECTIONS WE WANTED TO TRAVEL MUST HAVE BEEN FULL.  LETS JUST WAIT HERE...
							nextStage = 2'd1;
						end
					end
				end
			endcase
		end
end
endmodule
