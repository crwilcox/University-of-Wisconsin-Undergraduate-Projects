//Dan Drusch
//test bench designed to test the packet queue
//this testbench is shows basic reading, writing, an resetting
//this testbench is NOT exhaustive
module Packet_Queue_tb();
	reg clk;
	reg rst;

	//Queue Input
	reg [31:0] Packet_In;
	reg write;
	wire full;
	
	//Queue Output
	reg read;
	wire empty;
	wire [31:0] Packet_Out;

	Packet_Queue DUT(.Clk_r(clk), .Rst_n(rst), .Packet_In(Packet_In), .Read_Ack(read), .Write_Ack(write),
			.Packet_Out(Packet_Out), .Empty(empty), .Full(full));
	
	integer i;
	
always
 #5  clk = ~clk;

initial
begin
	$display("Set up initial values");
	clk = 1'b1; 
	rst = 1'b0;
	Packet_In = 32'b0;
	write = 1'b0;
	read = 1'b0;
	
	#10
	rst = 1'b1; //disable reset

	/**********************
	*     TEST QUEUE      *
	**********************/
	
	#20	
	
	/* Fill all the way, empty all the way */
	$display("Fill Queue Completely");
	Packet_In = 32'd5;
	write = 1'b1;
	#10
	write = 1'b0;
	#10
	Packet_In = 32'd6;
	write = 1'b1;
	#10
	write = 1'b0;
	#10
	Packet_In = 32'd7;
	write = 1'b1;
	#10
	write = 1'b0;
	#10
	Packet_In = 32'd8;
	write = 1'b1;
	#10
	write = 1'b0;
	#10
	Packet_In = 32'd9;
	write = 1'b1;
	#10
	write = 1'b0;
	#10
	
	#10
	read = 1'b1;
	#10
	read = 1'b0;
	#10
	read = 1'b1;
	#10
	read = 1'b0;
	#10
	read = 1'b1;
	#10
	read = 1'b0;
	#10
	read = 1'b1;
	#10
	read = 1'b0;
	#10
	read = 1'b1;
	#10
	read = 1'b0;

	#20	
	
	/* Fill part way, empty all the way */
	$display("Fill Queue Completely");
	Packet_In = 32'd5;
	write = 1'b1;
	#10
	write = 1'b0;
	#10
	Packet_In = 32'd6;
	write = 1'b1;
	#10
	write = 1'b0;
	#10
	Packet_In = 32'd7;
	write = 1'b1;
	#10
	write = 1'b0;
	#10
	
	#10
	read = 1'b1;
	#10
	read = 1'b0;
	#10
	read = 1'b1;
	#10
	read = 1'b0;
	#10
	read = 1'b1;
	#10
	read = 1'b0;

	#20	
	
	/* Fill part way, empty part way, fill all the way, empty all the way*/
	$display("Fill Queue Completely");
	Packet_In = 32'd5;
	write = 1'b1;
	#10
	write = 1'b0;
	#10
	Packet_In = 32'd6;
	write = 1'b1;
	#10
	write = 1'b0;
	#10
	Packet_In = 32'd7;
	write = 1'b1;
	#10
	write = 1'b0;
	#10
	
	read = 1'b1;
	#10
	read = 1'b0;
	#10
	
	
	Packet_In = 32'd8;
	write = 1'b1;
	#10
	write = 1'b0;
	#10
	Packet_In = 32'd9;
	write = 1'b1;
	#10
	write = 1'b0;
	#10
	Packet_In = 32'd10;
	write = 1'b1;
	#10
	write = 1'b0;
	#10
	
	#10
	read = 1'b1;
	#10
	read = 1'b0;
	#10
	read = 1'b1;
	#10
	read = 1'b0;
	#10
	read = 1'b1;
	#10
	read = 1'b0;
	#10
	read = 1'b1;
	#10
	read = 1'b0;
	#10
	read = 1'b1;
	#10
	read = 1'b0;

	#20	
	$stop;

end
endmodule
		