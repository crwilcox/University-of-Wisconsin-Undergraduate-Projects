/*
* Filename: core_packet_queue.v
* Author: Christopher Wilcox and Dan Drusch (The Fighting Mongooses)
* Date: 2010-11-15
* Description: This file contains the Packet Queue for the core.
*/
/****************************************************************************
*																			*
*							Packet Queue Module								*
*																			*
****************************************************************************/

module Packet_Queue(//INPUTS
					Clk_r, Rst_n,
					Packet_In,
					Read_Ack, Write_Ack,
					//OUTPUTS
					Packet_Out,
					Empty, Full
					);
	//Parameters
	parameter QUEUE_SIZE = 5;
	parameter COUNT_SIZE = 3; //Use Ceiling(log2(QUEUE_SIZE))
	
	// Global Interface
	input Clk_r; 	//The global Core clock. This clock is synchronized between Core and Processor Node.
	input Rst_n;	//The global asynchronous active-low reset signal.
	
	//Queue Interface
	input [31:0] Packet_In;
	input Read_Ack, Write_Ack;
	output [31:0] Packet_Out;
	output reg Empty, Full;
	
	//Internal Registers
	reg [31:0] Queue[QUEUE_SIZE-1:0];
	reg [COUNT_SIZE-1:0] Count;
	reg [COUNT_SIZE-1:0] OutAddr;
	
	assign Packet_Out = Queue[0];
	
	//Queue Assignment
	//Controlled Outputs: Packet_Out, Empty, Full
	//Controlled Reg: Queue, Count
	always @(negedge Clk_r, negedge Rst_n) begin
		if(Rst_n == 1'b0) begin
			Empty <= 1'b1;
			Full <= 1'b0;
			for(OutAddr=QUEUE_SIZE-1; OutAddr>0; OutAddr=OutAddr-1) begin
				Queue[OutAddr] <= Queue[OutAddr];
			end
			Count <= 0;
		end
		else begin
			if(Read_Ack && !Write_Ack) begin
				for(OutAddr=QUEUE_SIZE-1; OutAddr>=1; OutAddr=OutAddr-1) begin
					Queue[OutAddr-1] <= Queue[OutAddr];
				end
				if(Count == 1) begin
					Empty <= 1'b1;
				end
				else begin
					Empty <= 1'b0;
				end
				Full <= 1'b0;
				Count <= Count - 1'b1;
			end
			else if(Write_Ack && !Read_Ack) begin
				Queue[Count] <= Packet_In;
				//Latches should be created for all other regs in Queue
				if(Count == QUEUE_SIZE-1) begin
					Full <= 1'b1;
				end
				else begin
					Full <= 1'b0;
				end
				Empty <= 1'b0;
				Count <= Count + 1;
			end
			else if(Write_Ack && Read_Ack) begin
				Full <= Full;
				Empty <= Empty;
				for(OutAddr=QUEUE_SIZE-1; OutAddr>=1; OutAddr=OutAddr-1) begin
					if (OutAddr == Count) begin		
						Queue[OutAddr-1] <= Packet_In;		
					end
					else begin
						Queue[OutAddr-1] <= Queue[OutAddr];
					end
				end
				Count <= Count;		
			end
			else begin
				for(OutAddr=QUEUE_SIZE-1; OutAddr>0; OutAddr=OutAddr-1) begin
					Queue[OutAddr] <= Queue[OutAddr];
				end
				Empty <= Empty;
				Full <= Full;
				Count <= Count;
			end
		end
	end

endmodule
