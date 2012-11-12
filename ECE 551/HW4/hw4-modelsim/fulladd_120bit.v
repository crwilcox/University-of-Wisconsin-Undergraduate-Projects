module fulladd_120bit (c_in, c_out, a, b, sum);
	input c_in;
	input [119:0] a, b;
	output c_out;
	output [119:0] sum;
	wire [58:0] carry;
	fulladd_2bit fa [59:0] ( .c_in({carry[58:0],c_in}), .c_out({c_out,carry[58:0]}), .a(a), .b(b), .sum(sum) );
endmodule
