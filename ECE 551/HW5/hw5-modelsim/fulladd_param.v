//Dan Drusch
//Figure 4.2.b
//Problem 8 from Exam 1 - Version A
//BITWIDTH-bit full adder created from 2-bit full adders
//in a generate block.

module fulladd_param(clk, c_in, c_out, a, b, sum);
	parameter	BITWIDTH	= 168;
	input clk;
	input c_in;      //carry in
	input [BITWIDTH-1:0] a, b;      //input operands
	output reg c_out;    //carry out
	output reg [BITWIDTH-1:0] sum;      //64-bit sum
	
	wire[(BITWIDTH/2)-2:0] carry;
	genvar i;
	reg [BITWIDTH-1:0] a_clk, b_clk;
	reg c_in_clk;
	wire [BITWIDTH-1:0] sum_int;
	wire c_out_int;
	
	always @(posedge clk) begin
		a_clk <= a;
		b_clk <= b;
		c_in_clk <= c_in;
		c_out <= c_out_int;
		sum <= sum_int;
	end
	
	fulladd_2bit bottomAdder(c_in_clk, carry[0], a_clk[1:0], b_clk[1:0], sum_int[1:0]);
   generate
      for(i = 1; i < ((BITWIDTH/2)-1); i = i+1) begin: bit
         fulladd_2bit adders(carry[i-1], carry[i], a_clk[i*2+1:i*2], b_clk[i*2+1:i*2], sum_int[i*2+1:i*2]);
      end
   endgenerate
   fulladd_2bit topAdder(carry[(BITWIDTH/2)-2], c_out_int, a_clk[BITWIDTH-1:BITWIDTH-2], b_clk[BITWIDTH-1:BITWIDTH-2], sum_int[BITWIDTH-1:BITWIDTH-2]);
	
endmodule
