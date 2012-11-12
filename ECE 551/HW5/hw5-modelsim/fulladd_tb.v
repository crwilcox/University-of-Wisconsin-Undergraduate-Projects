//Dan Drusch
//Figure 4.2.c.1
//test bench to test and compare the generated added
//and the exam solution adder

module fulladd_tb();

	reg [167:0] a, b;
	reg c_in;
	wire[167:0] sum_a, sum_b;
	wire error, c_out_a, c_out_b;
	assign error = ((sum_a != sum_b) || (c_out_a != c_out_b));
	reg clk;
	
always begin
	#5
	clk <= ~clk;
end

	fulladd_param DUTbw(.clk(clk), .c_in(c_in), .c_out(c_out_b), .a(a), .b(b), .sum(sum_b));

initial
begin
	clk <= 1'b1;
	//Executes different additions throughout the 120 bits.
	c_in = 1'b0;
	a = 168'b0000_0000_0101_0101_0000_1111_1111_0000_0000_0000_0101_0101_0000_1111_1111_0000_0000_0000_0101_0101_0000_1111_1111_0000_0000_0000_0101_0101_0000_1111;
	b = 168'b1111_1111_1010_1010_0000_0000_0001_0000_1111_1111_1010_1010_0000_0000_0001_0000_1111_1111_1010_1010_0000_0000_0001_0000_1111_1111_1010_1010_0000_0000;
	
	#50
	
	//Results in the carry out being set
	c_in = 1'b1;
	a = 168'b0000_0001_0101_0101_0000_1111_1111_0000_0000_0000_0101_0101_0000_1111_1111_0000_0000_0000_0101_0101_0000_1111_1111_0000_0000_0000_0101_0101_0000_1111;
	b = 168'b1111_1111_1010_1010_0000_0000_0001_0000_1111_1111_1010_1010_0000_0000_0001_0000_1111_1111_1010_1010_0000_0000_0001_0000_1111_1111_1010_1010_0000_0000;
	
	#50

	$stop;
end 
endmodule
