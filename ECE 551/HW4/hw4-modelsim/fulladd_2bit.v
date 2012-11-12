module fulladd_2bit(
					input[1:0] a, b, input c_in,
					output c_out, output[1:0] sum
					);

	wire[2:0] outs;
	
	assign outs = a + b + c_in;
	assign c_out = outs[2];
	assign sum = outs[1:0];
endmodule

