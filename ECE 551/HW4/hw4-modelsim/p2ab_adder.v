module p2ab_adder( c_in, a, b, sum, c_out);
	parameter BITWIDTH = 64;
	
	input c_in;
	input [BITWIDTH-1:0] a, b;
	output [BITWIDTH-1:0] sum;
	output c_out;

	genvar i;
	generate 
		wire[BITWIDTH/2:0] cs;
		
		assign cs[0] = c_in;
		assign c_out = cs[BITWIDTH/2];
	
		for(i=0; i<=(BITWIDTH/2)-1; i=i+1) begin : adder
			fulladd_2bit fa	(
							.c_in(cs[i]), 
							.c_out(cs[i+1]), 
							.a({a[i*2 + 1],a[i*2]}), 
							.b({b[i*2 + 1],b[i*2]}), 
							.sum({sum[i*2 + 1],sum[i*2]})
							);
		end
	endgenerate
endmodule

