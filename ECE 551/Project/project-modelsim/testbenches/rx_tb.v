//Dan Drusch
//Figure 3.4.b
//test bench designed to test the 16 register memory file
//this testbench is shows basic reading, writing, an resetting
//this testbench is NOT exhaustive

module rx_tb();
	reg clk;
	reg rst;

	// RX/TX Wires
	reg S_Data;
	wire Rx_Ready, Rx_Error;
	
	// RX/Core Wires
	reg Core_Rcv_Ready;
	wire RxData_Valid;
	wire [31:0] RxData;
	
	//Test Registers
	reg [32:0] TransPacket;
	reg [34:0] TransPacket2;

	rx DUT(.Clk_s(clk), .Rst_n(rst), .Core_Rcv_Ready(Core_Rcv_Ready), .S_Data(S_Data),
			.RxData_Valid(RxData_Valid), .RxData(RxData), .Rx_Ready(Rx_Ready), .Rx_Error(Rx_Error));
	
	integer i;
	
always
 #5  clk = ~clk;

initial
begin
	$display("Set up initial values");
	clk = 1'b1; 
	rst = 1'b0;
	S_Data = 1'b1;
	Core_Rcv_Ready = 1'b0;
	TransPacket = 33'b0;
	TransPacket2 = 35'b0;
	
	#10
	rst = 1'b1; //disable reset

	/**********************
	*  TEST TRANSMISSION  *
	**********************/
	
	#20	
	
	/* Send Standard Data Packet */
	$display("Writing Standard Data Packet");
	for (i = 3'd0 ; i < 4'd5 ; i = i+1'b1) 
	begin
		S_Data = 1'b0;
		#10;
	end
	
	TransPacket = 33'b0101_0101_0101_0101_0101_0101_0101_0101_1;
	
	for (i = 6'b0 ; i < 6'd33 ; i = i+1'b1) begin
		S_Data = TransPacket[32-i];
		#10;
	end

	//Do handshake
	#10
	$display("Expected RxData:\t\t01010101010101010101010101010100");
   $display("Actual RxData:\t\t%b", RxData);
	Core_Rcv_Ready = 1'b1;
	#10
	Core_Rcv_Ready = 1'b0;

	//
	/* Send Bit Stuffed Data Packet */
	//

   $display("Writing Bitstuffed Data Packet");
	for (i = 3'd0 ; i < 4'd5 ; i = i+1'b1) 
	begin
		S_Data = 1'b0;
		#10;
	end
	
	TransPacket2 = 35'b0000_1000_0101_0101_0101_0101_0101_0101_000;
	
	for (i = 6'b0 ; i < 6'd35 ; i = i+1'b1) begin
		S_Data = TransPacket2[34-i];
		#10;
	end

	//Do handshake
	#10
	$display("Expected RxData:\t\t00000000010101010101010101010100");
   $display("Actual RxData:\t\t%b", RxData);
	Core_Rcv_Ready = 1'b1;
	#10
	Core_Rcv_Ready = 1'b0;

	//
	/* Send 2 data packets without Core handshake */
	//

	/* Send Standard Data Packet */
	$display("Writing Standard Data Packet");
	for (i = 3'd0 ; i < 4'd5 ; i = i+1'b1) 
	begin
		S_Data = 1'b0;
		#10;
	end
	
	TransPacket = 33'b0101_0101_0101_0101_0101_0101_0101_0101_1;
	
	for (i = 6'b0 ; i < 6'd33 ; i = i+1'b1) begin
		S_Data = TransPacket[32-i];
		#10;
	end

   $display("Writing Bitstuffed Data Packet");
	for (i = 3'd0 ; i < 4'd5 ; i = i+1'b1) 
	begin
		S_Data = 1'b0;
		#10;
	end
	
	TransPacket2 = 35'b0000_1000_0101_0101_0101_0101_0101_0101_000;
	
	for (i = 6'b0 ; i < 6'd35 ; i = i+1'b1) begin
		S_Data = TransPacket2[34-i];
		#10;
	end
	S_Data = 1'b1;
   
   #20

	//Do handshake
	#10
	$display("Expected RxData:\t\t01010101010101010101010101010100");
   $display("Actual RxData:\t\t%b", RxData);
	Core_Rcv_Ready = 1'b1;
	#10
	Core_Rcv_Ready = 1'b0;
	#30
	$display("Expected RxData:\t\t00000000010101010101010101010100");
   $display("Actual RxData:\t\t%b", RxData);
	Core_Rcv_Ready = 1'b1;
	#10
	Core_Rcv_Ready = 1'b0;
	#10
   
	//
	/* Send data packet with bad parity */
	//

	/* Send Bad Parity Data Packet */
	$display("Writing Bad Parity Data Packet");
	for (i = 3'd0 ; i < 4'd5 ; i = i+1'b1) 
	begin
		S_Data = 1'b0;
		#10;
	end
	
	TransPacket = 33'b0101_0101_0101_0101_0101_0101_0101_0101_0;
	
	for (i = 6'b0 ; i < 6'd33 ; i = i+1'b1) begin
		S_Data = TransPacket[32-i];
		#10;
	end

	//Do handshake
	#10
	$display("Expected Rx_Error:\t1");
   $display("Actual Rx_Error:\t\t%b", Rx_Error);
	Core_Rcv_Ready = 1'b1;
	#10
	Core_Rcv_Ready = 1'b0;

	#20	
	$stop;

end
endmodule
		