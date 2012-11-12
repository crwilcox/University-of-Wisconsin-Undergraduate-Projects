/*
* Filename: core.v
* Author: Christopher Wilcox and Dan Drusch (The Fighting Mongooses)
* Date: 2010-11-15
* Description: This file connects the sub-modules of the core into a high
* 	level core...
*/

/****************************************************************************
*																			*
*								Router Core									*
*																			*
****************************************************************************/

module core	(
					//INPUTS
					Clk_r, Rst_n, 
					N_Tx_Ready, N_Tx_Error, N_RxData_Valid, N_RxData, 	
					S_Tx_Ready, S_Tx_Error, S_RxData_Valid, S_RxData, 
					W_Tx_Ready, W_Tx_Error, W_RxData_Valid, W_RxData, 
					E_Tx_Ready, E_Tx_Error, E_RxData_Valid, E_RxData, 
					Packet_From_Node, Packet_From_Node_Valid,
					
					//OUTPUTS
					N_TxData_Valid, N_TxData, N_Error_Ack, N_Core_Rcv_Ready, 
					S_TxData_Valid, S_TxData, S_Error_Ack, S_Core_Rcv_Ready, 
					W_TxData_Valid, W_TxData, W_Error_Ack, W_Core_Rcv_Ready, 
					E_TxData_Valid, E_TxData, E_Error_Ack, E_Core_Rcv_Ready, 
					Core_Load_Ack, Packet_To_Node, Packet_To_Node_Valid
					);
//INPUTS/OUTPUTS DESCRIBED
// Global Interface
	input Clk_r; 	//The global Core clock. This clock is synchronized between Core and Processor 
					//Node. The TX & RX do not necessarily use the same clock.
	input Rst_n;	//The global asynchronous active-low reset signal.

// Router Core /TX Interface
	output N_TxData_Valid, S_TxData_Valid, E_TxData_Valid, W_TxData_Valid;	
		//This signal serves as part of the TX/Core handshake
		//The Core should	set TxData_Valid high when it has valid data ready on 
		//the output TxData and after the TX has asserted its associated Ready signal.
	output [31:0] N_TxData, S_TxData, E_TxData, W_TxData;	
		//This signal represents a 32-bit packet for transmission.
	output N_Error_Ack, S_Error_Ack, E_Error_Ack, W_Error_Ack;		
		//This signal acknowledges that the Core has seen that Tx_Error was asserted.
	input N_Tx_Ready, S_Tx_Ready, E_Tx_Ready, W_Tx_Ready;		
		//This signal serves as part of the TX/Core handshake. When Tx_Ready is
		//high, it means that the TX is ready to accept a packet from the Core.
	input N_Tx_Error, S_Tx_Error, E_Tx_Error, W_Tx_Error;		
		//This signal is used to tell the Core that an error occurred while 
		//transmitting a packet on this link.

// RX / Router Core Interface
	output N_Core_Rcv_Ready, S_Core_Rcv_Ready, E_Core_Rcv_Ready, W_Core_Rcv_Ready;
		//This signal serves as part of the RX/Core handshake. When
		//Core_Rcv_Ready is high, it means that the Core is capable of accepting a 
		//packet from the receiver.
	input N_RxData_Valid, S_RxData_Valid, E_RxData_Valid, W_RxData_Valid; 	
		//This signal serves as part of the RX/Core handshake. When
		//RxData_Valid is high, it means that the input RxData has valid data available on it.
	input [31:0] N_RxData, S_RxData, E_RxData, W_RxData;
		//This signal represents a 32-bit packet which has been received 
		//at the RX from a different router, de-serialized, and is now presented to the Core.

// Router Core / Processor Node Interface
	input [31:0] Packet_From_Node;	//A 32-bit parallel interface for transferring packets from the Node to Core.
	input Packet_From_Node_Valid; 	//If this signal is high, it indicates that the Node has new, valid
									//data on the Packet_From_Node interface.
	output Core_Load_Ack; 			//Once the Core has loaded data from the Node, it will assert
									//Core_Load_Ack for one cycle of Clk_r.
	output [31:0] Packet_To_Node; 	//A 32-bit parallel interface for transferring packets from the Core to Node.
	output Packet_To_Node_Valid; 	//When the Core puts valid data on Packet_To_Node, it should
									//assert this signal for one cycle of Clk_r.

/******** Internal Interfaces ********/
//Routing SM / Link Controllers
	wire [31:0] N_Rx_Packet, 	S_Rx_Packet, 		E_Rx_Packet, 		W_Rx_Packet;
	wire [31:0] N_Tx_Packet, 	S_Tx_Packet, 		E_Tx_Packet, 		W_Tx_Packet;
	wire N_TxQueue_Write, 		S_TxQueue_Write, 	E_TxQueue_Write, 	W_TxQueue_Write;
	wire N_RxQueue_Read, 		S_RxQueue_Read, 	E_RxQueue_Read, 	W_RxQueue_Read;
	wire N_RxQueue_Empty, 		S_RxQueue_Empty, 	E_RxQueue_Empty, 	W_RxQueue_Empty;
	wire N_TxQueue_Full,		S_TxQueue_Full,		E_TxQueue_Full,		W_TxQueue_Full;

//Routing SM / Node Manager
	wire [31:0] NM_In_Packet;
	wire [31:0] NM_Out_Packet;
	wire [3:0] Link_Config;
	wire [7:0] Router_Addr;
	wire NM_Read, NM_Empty;
	wire NM_Write, NM_Full;


	 Link_Control N_Link_Control(.Clk_r(Clk_r), .Rst_n(Rst_n), 
					 			 .Tx_Ready(N_Tx_Ready), .Tx_Error(N_Tx_Error), 
					 			 .RxData_Valid(N_RxData_Valid), .RxData(N_RxData),
					 			 .Packet_From_Core(N_Tx_Packet), .TxQueue_Write(N_TxQueue_Write), .RxQueue_Read(N_RxQueue_Read),
					 			 .TxData_Valid(N_TxData_Valid), .TxData(N_TxData), .Error_Ack(N_Error_Ack),
					 			 .Core_Rcv_Ready(N_Core_Rcv_Ready),
					 			 .Packet_To_Core(N_Rx_Packet), .RxQueue_Empty(N_RxQueue_Empty), .TxQueue_Full(N_TxQueue_Full));
	 Link_Control S_Link_Control(.Clk_r(Clk_r), .Rst_n(Rst_n), 
					 			 .Tx_Ready(S_Tx_Ready), .Tx_Error(S_Tx_Error), 
					 			 .RxData_Valid(S_RxData_Valid), .RxData(S_RxData),
					 			 .Packet_From_Core(S_Tx_Packet), .TxQueue_Write(S_TxQueue_Write), .RxQueue_Read(S_RxQueue_Read),
					 			 .TxData_Valid(S_TxData_Valid), .TxData(S_TxData), .Error_Ack(S_Error_Ack),
					 			 .Core_Rcv_Ready(S_Core_Rcv_Ready),
					 			 .Packet_To_Core(S_Rx_Packet), .RxQueue_Empty(S_RxQueue_Empty), .TxQueue_Full(S_TxQueue_Full));
	 Link_Control E_Link_Control(.Clk_r(Clk_r), .Rst_n(Rst_n), 
					 			 .Tx_Ready(E_Tx_Ready), .Tx_Error(E_Tx_Error), 
					 			 .RxData_Valid(E_RxData_Valid), .RxData(E_RxData),
					 			 .Packet_From_Core(E_Tx_Packet), .TxQueue_Write(E_TxQueue_Write), .RxQueue_Read(E_RxQueue_Read),
					 			 .TxData_Valid(E_TxData_Valid), .TxData(E_TxData), .Error_Ack(E_Error_Ack),
					 			 .Core_Rcv_Ready(E_Core_Rcv_Ready),
					 			 .Packet_To_Core(E_Rx_Packet), .RxQueue_Empty(E_RxQueue_Empty), .TxQueue_Full(E_TxQueue_Full));
	 Link_Control W_Link_Control(.Clk_r(Clk_r), .Rst_n(Rst_n), 
					 			 .Tx_Ready(W_Tx_Ready), .Tx_Error(W_Tx_Error), 
					 			 .RxData_Valid(W_RxData_Valid), .RxData(W_RxData),
					 			 .Packet_From_Core(W_Tx_Packet), .TxQueue_Write(W_TxQueue_Write), .RxQueue_Read(W_RxQueue_Read),
					 			 .TxData_Valid(W_TxData_Valid), .TxData(W_TxData), .Error_Ack(W_Error_Ack),
					 			 .Core_Rcv_Ready(W_Core_Rcv_Ready),
					 			 .Packet_To_Core(W_Rx_Packet), .RxQueue_Empty(W_RxQueue_Empty), .TxQueue_Full(W_TxQueue_Full));
					 			 
	 Routing_State_Machine routing_state_machine(.Clk_r(Clk_r), .Rst_n(Rst_n), .Router_Address(Router_Addr), .Link_Config(Link_Config),
										   .Packet_From_Link_N(N_Rx_Packet), .Packet_From_Link_S(S_Rx_Packet), 
										   .Packet_From_Link_W(W_Rx_Packet), .Packet_From_Link_E(E_Rx_Packet), .Packet_From_Node(NM_In_Packet),
										   .RxQueue_Empty_N(N_RxQueue_Empty), .RxQueue_Empty_S(S_RxQueue_Empty), .RxQueue_Empty_W(W_RxQueue_Empty), .RxQueue_Empty_E(E_RxQueue_Empty),
										   .TxQueue_Full_N(N_TxQueue_Full), .TxQueue_Full_S(S_TxQueue_Full), .TxQueue_Full_W(W_TxQueue_Full), .TxQueue_Full_E(E_TxQueue_Full),  
										   .Node_Queue_Empty(NM_Empty), .To_Node_Full(NM_Full),
										   
										   .Packet_To_Link_N(N_Tx_Packet), .Packet_To_Link_S(S_Tx_Packet), .Packet_To_Link_W(W_Tx_Packet), 
										   .Packet_To_Link_E(E_Tx_Packet), .Packet_To_Node(NM_Out_Packet),
										   .TxQueue_Write_N(N_TxQueue_Write), .TxQueue_Write_S(S_TxQueue_Write), .TxQueue_Write_W(W_TxQueue_Write), .TxQueue_Write_E(E_TxQueue_Write),
										   .RxQueue_Read_N(N_RxQueue_Read), .RxQueue_Read_S(S_RxQueue_Read), .RxQueue_Read_W(W_RxQueue_Read), .RxQueue_Read_E(E_RxQueue_Read),
										   .Node_Read(NM_Read), .To_Node_Write(NM_Write));	
	 
	 
	 Node_Manager node_manager(.Clk_r(Clk_r), .Rst_n(Rst_n), 
					 		   .Packet_From_Core(NM_Out_Packet), 
					 		   .Packet_From_Node(Packet_From_Node), .Packet_From_Node_Valid(Packet_From_Node_Valid),
					 		   .Read_Packet(NM_Read),
					 		   .To_Node_Write(NM_Write),
					 		   .Router_Address(Router_Addr), .Link_Config(Link_Config),
					 		   .Packet_To_Core(NM_In_Packet),
					 		   .Packet_To_Node(Packet_To_Node), .Packet_To_Node_Valid(Packet_To_Node_Valid), .Core_Load_Ack(Core_Load_Ack),
					 		   .Queue_Empty(NM_Empty),
					 		   .To_Node_Full(NM_Full));
endmodule

