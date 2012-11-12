//Dan Drusch
//test bench designed to test the Link Control
//this testbench shows basic reading, writing, an resetting
//this testbench is NOT exhaustive
`timescale 1ns/10ps
module Link_Control_tb();
	reg clk;
	reg rst;

	//Link Control Tx
	reg Tx_Ready, Tx_Error;
	wire [31:0] TxData;
	wire TxData_Valid, Error_Ack;
	
	//Link Control Rx
	reg RxData_Valid;
	reg [31:0] RxData;
	wire Core_Rcv_Ready;
	
	//Link Control Routing FSM
	reg TxQueue_Write, RxQueue_Read;
	reg [31:0] Packet_From_Core;
	wire RxQueue_Empty, TxQueue_Full;
	wire [31:0] Packet_To_Core;
	
	//Synth outputs
	wire [31:0] TxData_Synth;
	wire TxData_Valid_Synth, Error_Ack_Synth;
	wire Core_Rcv_Ready_Synth;
	wire RxQueue_Empty_Synth, TxQueue_Full_Synth;
	wire [31:0] Packet_To_Core_Synth;
	
	//Func vs. Synth Errors
	reg TxData_Error;
	reg TxData_Valid_Error, Error_Ack_Error;
	reg Core_Rcv_Ready_Error;
	reg RxQueue_Empty_Error, TxQueue_Full_Error;
	reg Packet_To_Core_Error;	
	
	wire Global_Error;
	
	//Set errors (Always block to prevent delay blips)
	always @(negedge clk) begin
		TxData_Error <= TxData !== TxData_Synth;
		TxData_Valid_Error <= TxData_Valid != TxData_Valid_Synth;
		Error_Ack_Error <= Error_Ack != Error_Ack_Synth;
		Core_Rcv_Ready_Error <= Core_Rcv_Ready != Core_Rcv_Ready_Synth;
		RxQueue_Empty_Error <= RxQueue_Empty != RxQueue_Empty_Synth;
		TxQueue_Full_Error <= TxQueue_Full != TxQueue_Full_Synth;
		Packet_To_Core_Error <= Packet_To_Core !== Packet_To_Core_Synth;
	end
	
	assign Global_Error = TxData_Error || TxData_Valid_Error || Error_Ack_Error  ||Core_Rcv_Ready_Error || RxQueue_Empty_Error || TxQueue_Full_Error || Packet_To_Core_Error;	

	Link_Control DUT(.Clk_r(clk), .Rst_n(rst), 
					 .Tx_Ready(Tx_Ready), .Tx_Error(Tx_Error), 
					 .RxData_Valid(RxData_Valid), .RxData(RxData),
					 .Packet_From_Core(Packet_From_Core), .TxQueue_Write(TxQueue_Write), .RxQueue_Read(RxQueue_Read),
					 .TxData_Valid(TxData_Valid), .TxData(TxData), .Error_Ack(Error_Ack),
					 .Core_Rcv_Ready(Core_Rcv_Ready),
					 .Packet_To_Core(Packet_To_Core), .RxQueue_Empty(RxQueue_Empty), .TxQueue_Full(TxQueue_Full));
	Link_Control_synth synthDUT(.Clk_r(clk), .Rst_n(rst), 
					 .Tx_Ready(Tx_Ready), .Tx_Error(Tx_Error), 
					 .RxData_Valid(RxData_Valid), .RxData(RxData),
					 .Packet_From_Core(Packet_From_Core), .TxQueue_Write(TxQueue_Write), .RxQueue_Read(RxQueue_Read),
					 .TxData_Valid(TxData_Valid_Synth), .TxData(TxData_Synth), .Error_Ack(Error_Ack_Synth),
					 .Core_Rcv_Ready(Core_Rcv_Ready_Synth),
					 .Packet_To_Core(Packet_To_Core_Synth), .RxQueue_Empty(RxQueue_Empty_Synth), .TxQueue_Full(TxQueue_Full_Synth));
	
	integer i;
	
always
 #5  clk = ~clk;

initial
begin
	$display("Set up initial values");
	//Need to control following variables
	//clk, rst
	//Tx_Ready, Tx_Error
	//RxData_Valid
	//[31:0]RxData
	//TxQueue_Write, RxQueue_Read
	//[31:0]Packet_From_Core
	
	clk = 1'b1; 
	rst = 1'b0;
	Tx_Ready = 1'b0;
	Tx_Error = 1'b0;
	RxData_Valid = 1'b0;
	RxData = 32'b0;
	TxQueue_Write = 1'b0;
	RxQueue_Read = 1'b0;
	Packet_From_Core = 32'b0;
	
	#10
	rst = 1'b1; //disable reset

	/*************************
	*  TEST LINK_CONTROLLER  *
	*************************/
	
	#21	
	/* Test Standard Rx Incoming */
	//Fill Queue
	RxData = 32'hFFFF_FF1F;
	RxData_Valid = 1'b1;
	#10
	RxData_Valid = 1'b0;
	#20
	RxData = 32'hFFFF_FF1E;
	RxData_Valid = 1'b1;
	#10
	RxData_Valid = 1'b0;
	#20
	RxData = 32'hFFFF_FF1D;
	RxData_Valid = 1'b1;
	#10
	RxData_Valid = 1'b0;
	#20
	RxData = 32'hFFFF_FF1C;
	RxData_Valid = 1'b1;
	#10
	RxData_Valid = 1'b0;
	#20
	RxData = 32'hFFFF_FF1B;
	RxData_Valid = 1'b1;
	#10
	RxData_Valid = 1'b0;
	#20
	
	//Empty single value
	RxQueue_Read = 1'b1;
	#10
	RxQueue_Read = 1'b0;
	#10
	
	//Refill
	RxData = 32'hFFFF_FF1A;
	RxData_Valid = 1'b1;
	#10
	RxData_Valid = 1'b0;
	#20
	
	
	//Empty Queue
	RxQueue_Read = 1'b1;
	#10
	RxQueue_Read = 1'b0;
	#10
	RxQueue_Read = 1'b1;
	#10
	RxQueue_Read = 1'b0;
	#10
	RxQueue_Read = 1'b1;
	#10
	RxQueue_Read = 1'b0;
	#10
	RxQueue_Read = 1'b1;
	#10
	RxQueue_Read = 1'b0;
	#10
	RxQueue_Read = 1'b1;
	#10
	RxQueue_Read = 1'b0;
	#10
	
	#100
	
	/* Test Standard Tx Outgoing */
	Packet_From_Core = 32'd5;
	TxQueue_Write = 1'b1;
	#10
	TxQueue_Write = 1'b0;
	#10
	Packet_From_Core = 32'd6;
	TxQueue_Write = 1'b1;
	#10
	TxQueue_Write = 1'b0;
	#10
	Packet_From_Core = 32'd7;
	TxQueue_Write = 1'b1;
	#10
	TxQueue_Write = 1'b0;
	#10
	Packet_From_Core = 32'd8;
	TxQueue_Write = 1'b1;
	#10
	TxQueue_Write = 1'b0;
	#10
	Packet_From_Core = 32'd9;
	TxQueue_Write = 1'b1;
	#10
	TxQueue_Write = 1'b0;
	#10
	
	Tx_Ready = 1'b1;
	#10
	Tx_Ready = 1'b0;
	#10
	Tx_Ready = 1'b1;
	#10
	Tx_Ready = 1'b0;
	#10
	Tx_Ready = 1'b1;
	#10
	Tx_Ready = 1'b0;
	#10
	Tx_Ready = 1'b1;
	#10
	Tx_Ready = 1'b0;
	#10
	Tx_Ready = 1'b1;
	#10
	Tx_Ready = 1'b0;
	#10
	
	/* Error on Tx */
	Tx_Error = 1'b1;
	#10
	Tx_Error = 1'b0;

	#20	
	$stop;

end
endmodule
		