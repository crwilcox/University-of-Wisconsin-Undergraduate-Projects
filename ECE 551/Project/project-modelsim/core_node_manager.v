/*
* Filename: core_node_manager.v
* Author: Christopher Wilcox and Dan Drusch (The Fighting Mongooses)
* Date: 2010-11-15
* Description: This file contains the Node Manager for the core.
*/
/****************************************************************************
*																			*
*							Node Manager Module								*
*																			*
****************************************************************************/

//Node Manager will handle checking for Command Packets, Interpretting them, and sending the States to the Routing State Machine
//If not a Command Packet, Node Manager will send data packet to Routing State Machine for processing.
module Node_Manager(//INPUTS
					Clk_r, Rst_n, 
					Packet_From_Core, 
					Packet_From_Node, Packet_From_Node_Valid,
					Read_Packet,
					To_Node_Write,
					//OUTPUTS
					Router_Address, Link_Config, 
					Packet_To_Core,
					Packet_To_Node, Packet_To_Node_Valid, Core_Load_Ack,
					Queue_Empty,
					To_Node_Full
					);
// Global Interface
	input Clk_r; 	//The global Core clock. This clock is synchronized between Core and Processor Node.
	input Rst_n;	//The global asynchronous active-low reset signal.

// Router Core / Processor Node Interface
	input [31:0] Packet_From_Node;		//A 32-bit parallel interface for transferring packets from the Node to Core.
	input Packet_From_Node_Valid; 		//If this signal is high, it indicates that the Node has new, valid
										//data on the Packet_From_Node interface.
	input [31:0] Packet_From_Core;		//A 32-bit parallel interface for receiving packets from Routing State Machine
	input Read_Packet;					//Once Routing State Machine has read the packet, asserts for 1 cycle to acknowledge read
	input To_Node_Write;
	
	output reg[7:0] Router_Address;		//Address of the Router. MS 4 bits indicate WE-Pos, LS 4 bits indicate SN-Pos
	output reg[3:0] Link_Config;		//Indicates Enabled(1)/Disabled(0) Links in format NSWE
	output [31:0] Packet_To_Core;		//A 32-bit parallel interface for transferring packet to Routing State Machine
	output reg Core_Load_Ack; 			//Once the Core has loaded data from the Node, it will assert
										//Core_Load_Ack for one cycle of Clk_r.
	output [31:0] Packet_To_Node; 	//A 32-bit parallel interface for transferring packets from the Core to Node.
	output reg Packet_To_Node_Valid; 	//When the Core puts valid data on Packet_To_Node, it should
										//assert this signal for one cycle of Clk_r.
	output Queue_Empty;					//Interface to indicate there is no valid data available for the Routing State Machine
	
	output To_Node_Full;
	
//Internal Connections
	reg Write_Queue;					//Control signal for Packet Queue
	wire Queue_Full;					//Indicated Queue is full and cannot be written to
	reg Read_Out_Queue;
	wire Out_Queue_Empty;

//Create Queue
	Packet_Queue In_Queue(.Clk_r(Clk_r), .Rst_n(Rst_n), 
						  .Packet_In(Packet_From_Node), 
						  .Read_Ack(Read_Packet), .Write_Ack(Write_Queue), 
						  .Packet_Out(Packet_To_Core), 
						  .Empty(Queue_Empty), .Full(Queue_Full));
	Packet_Queue Out_Queue(.Clk_r(Clk_r), .Rst_n(Rst_n), 
						  .Packet_In(Packet_From_Core), 
						  .Read_Ack(Read_Out_Queue), .Write_Ack(To_Node_Write), 
						  .Packet_Out(Packet_To_Node), 
						  .Empty(Out_Queue_Empty), .Full(To_Node_Full));

//Packet output to Node
//Controlled Outputs: Packet_To_Node, Packet_To_Node_Valid
	always @(posedge Clk_r, negedge Rst_n) begin
		if(Rst_n == 1'b0) begin
			Read_Out_Queue <= 1'b0;
			Packet_To_Node_Valid <= 1'b0;
		end
		else if(!Out_Queue_Empty && !Packet_To_Node_Valid) begin
			Packet_To_Node_Valid <= 1'b1;
			Read_Out_Queue <= 1'b0;
		end
		else if(Packet_To_Node_Valid) begin
			Packet_To_Node_Valid <= 1'b0;
			Read_Out_Queue <= 1'b1;
		end
		else begin
			Packet_To_Node_Valid <= 1'b0;
			Read_Out_Queue <= 1'b0;
		end
	end				
	
//Packet input from Node
//Controlled Outputs: Router_Address, Link_Config, Core_Load_Ack
//Controlled Reg: Write_Queue
	always @(posedge Clk_r, negedge Rst_n) begin
		if(Rst_n == 1'b0) begin
			Router_Address <= 8'b0;
			Link_Config <= 4'b0;
			Core_Load_Ack <= 1'b0;
			Write_Queue <= 0;
		end
		else if (Core_Load_Ack == 1'b1) begin
			Router_Address <= Router_Address;
			Link_Config <= Link_Config;
			Core_Load_Ack <= 1'b0;
			Write_Queue <= 1'b0;
		end
		else begin
			if(Packet_From_Node_Valid == 1'b1) begin
				if(Packet_From_Node[31:24] == 8'b1111_1111) begin //Command Packet
					if(Packet_From_Node[23:16] == 8'd0) begin //Address Assignment
						Router_Address <= Packet_From_Node[7:0];
						Link_Config <= Link_Config;
					end
					else if(Packet_From_Node[23:16] == 8'd1) begin //Link Configuration
						Link_Config <= Packet_From_Node[3:0];
						Router_Address <= Router_Address;
					end
					else begin
						Link_Config <= Link_Config;
						Router_Address <= Router_Address;
					end
					
					Core_Load_Ack <= 1;
					Write_Queue <= 0;
				end
				else if(Packet_From_Node[31:28] != 4'b1111 && Packet_From_Node[27:24] != 4'b1111) begin //Message Packet
					if(Queue_Full == 1'b0) begin
						Router_Address <= Router_Address;
						Link_Config <= Link_Config;
						Core_Load_Ack <= 1;
						Write_Queue <= 1;
					end
					else begin //Queue is full, wait
						Router_Address <= Router_Address;
						Link_Config <= Link_Config;
						Core_Load_Ack <= 0;
						Write_Queue <= 0;
					end
				end
				else begin //Invalid Packet
					Router_Address <= Router_Address;
					Link_Config <= Link_Config;
					Core_Load_Ack <= 1;
					Write_Queue <= 0;
				end
			end
			else begin //No Valid Data
				Router_Address <= Router_Address;
				Link_Config <= Link_Config;
				Core_Load_Ack <= 0;
				Write_Queue <= 0;
			end
		end
	end
	
endmodule
