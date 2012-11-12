/*
* Filename: core_link_control.v
* Author: Christopher Wilcox and Dan Drusch (The Fighting Mongooses)
* Date: 2010-11-15
* Description: This file contains the Link Control for the core.
*/
/****************************************************************************
*																			*
*							Link Control Module								*
*																			*
****************************************************************************/

module Link_Control(//INPUTS
					Clk_r, Rst_n, 
					Tx_Ready, Tx_Error, 
					RxData_Valid, RxData,	
					Packet_From_Core, TxQueue_Write, RxQueue_Read,
					
					//OUTPUTS
					TxData_Valid, TxData, Error_Ack, 
					Core_Rcv_Ready,
					Packet_To_Core, RxQueue_Empty, TxQueue_Full
					);
// Global Interface
	input Clk_r; 	//The global Core clock. This clock is synchronized between Core and Processor Node.
	input Rst_n;	//The global asynchronous active-low reset signal.

//TX/CORE INTERFACE	
	output reg TxData_Valid;		//This signal serves as part of the TX/Core handshake
									//The Core should	set TxData_Valid high when it has valid data ready on 
									//the output TxData and after the TX has asserted its associated Ready signal.
	output [31:0] TxData;			//This signal represents a 32-bit packet for transmission.
	output reg Error_Ack;			//This signal acknowledges that the Core has seen that Tx_Error was asserted.
	input Tx_Ready;					//This signal serves as part of the TX/Core handshake. When Tx_Ready is
									//high, it means that the TX is ready to accept a packet from the Core.
	input Tx_Error;					//This signal is used to tell the Core that an error occurred while 
									//transmitting a packet on this link.
// RX / Router Core Interface
	output reg Core_Rcv_Ready;		//This signal serves as part of the RX/Core handshake. When
									//Core_Rcv_Ready is high, it means that the Core is capable of accepting a 
									//packet from the receiver.
	input RxData_Valid; 			//This signal serves as part of the RX/Core handshake. When
									//RxData_Valid is high, it means that the input RxData has valid data available on it.
	input [31:0] RxData; 			//This signal represents a 32-bit packet which has been received 
									//at the RX from a different router, de-serialized, and is now presented to the Core.
// Link / Routing State Machine Interface
	input [31:0] Packet_From_Core;	//A 32-bit packet from the Routing FSM that needs to be transmitted
	input TxQueue_Write;			//Used to signal internal transmission queue
	input RxQueue_Read;				//Used to signal internal receiver queue
	output [31:0] Packet_To_Core;//A 32-bit packet from the receiver queue to be sent into the Routing State Machine
	output RxQueue_Empty;			//Signal that indicates if there is no data in the receiver queue
	output TxQueue_Full;			//Signal that indicates if the transmission queue is full
	
//Internal Connections
	wire TxQueue_Empty;				//Indicates Transmission queue has no data
	wire RxQueue_Full;				//Indicates Reciever queue is full
	reg TxQueue_Read;				//"Read" Control Signal for Transmission queue
	reg RxQueue_Write;				//"Write" Control Signal for Receiver queue
	
//Queues
	Packet_Queue TxQueue(.Clk_r(Clk_r), .Rst_n(Rst_n), 
						 .Packet_In(Packet_From_Core), 
						 .Read_Ack(TxQueue_Read), .Write_Ack(TxQueue_Write), 
						 .Packet_Out(TxData), 
						 .Empty(TxQueue_Empty), .Full(TxQueue_Full));
	Packet_Queue RxQueue(.Clk_r(Clk_r), .Rst_n(Rst_n), 
						 .Packet_In(RxData), 
						 .Read_Ack(RxQueue_Read), .Write_Ack(RxQueue_Write), 
						 .Packet_Out(Packet_To_Core), 
						 .Empty(RxQueue_Empty), .Full(RxQueue_Full));
						 
//Rx Signal Translation
//Controlled Outputs: Core_Rcv_Ready
//Controlled Reg: RxQueue_Write
	always @(posedge Clk_r, negedge Rst_n) begin
		if(Rst_n == 1'b0) begin
			Core_Rcv_Ready <= 1'b0;
			RxQueue_Write <= 1'b0;
		end
		else begin
			if(RxQueue_Full == 1'b0 && RxData_Valid == 1'b0 && RxQueue_Write == 1'b0) begin //There is room and Valid is not asserted
				Core_Rcv_Ready <= 1'b1;
				RxQueue_Write <= 1'b0;
			end
			else if(RxQueue_Write == 1'b1) begin //Force one cycle delay for Core_Rcv_Ready to acknowledge full
				RxQueue_Write <= 1'b0;
				Core_Rcv_Ready <= 1'b0;
			end
			else if(RxData_Valid == 1'b1 && Core_Rcv_Ready == 1'b1) begin //Both are Valid
				Core_Rcv_Ready <= 1'b1;
				RxQueue_Write <= 1'b1;
			end
			else begin //Other situations
				Core_Rcv_Ready <= Core_Rcv_Ready;
				RxQueue_Write <= 1'b0;
			end
		end
	end

//Tx Signal Translation and Error Handling
//Controlled Outputs: TxData_Valid, Error_Ack
//Controlled Reg: TxQueue_Read
	always @(posedge Clk_r, negedge Rst_n) begin
		if(Rst_n == 1'b0) begin
			TxData_Valid <= 1'b0;
			Error_Ack <= 1'b0;
			TxQueue_Read <= 1'b0;
		end
		else begin
			//Handle Error
			Error_Ack <= Tx_Error;
			
			//Translate handshake
			/*if(TxQueue_Read == 1'b1) begin
				TxData_Valid <= 1'b0;
				TxQueue_Read <= 1'b0;
			end
			else */
			if(Tx_Ready == 1'b1 && TxQueue_Empty == 1'b0) begin  //Begin Valid
				TxData_Valid <= 1'b1;
				TxQueue_Read <= 1'b0;
			end
			else if(Tx_Ready == 1'b0 && TxData_Valid == 1'b1) begin //End Valid and signal queue
				TxData_Valid <= 1'b0;
				TxQueue_Read <= 1'b1;
			end
			else begin
				TxData_Valid <= TxData_Valid;
				TxQueue_Read <= 1'b0;
			end
		end
	end					 

endmodule

