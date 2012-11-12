module p2_tb();

	reg c_in;
	reg[119:0] a, b;
	wire[119:0] sum_a, sum_b;
	wire c_out_a, c_out_b;

	defparam DUT2.BITWIDTH = 120;

	fulladd_120bit DUT1 (.c_in(c_in), .a(a),.b(b), .sum(sum_a), .c_out(c_out_a));
	p2ab_adder DUT2 (.c_in(c_in), .a(a), .b(b), .sum(sum_b), .c_out(c_out_b));

	wire error;
	assign error = ((sum_a != sum_b) || (c_out_a != c_out_b));
	
	initial begin
		c_in = 1'b0; a = 120'hFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF; b= 120'd0;
		#5
		
		c_in = 1'b1; a = 120'hFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF; b= 120'd0;
		#5
		
		c_in = 1'b0; a = 120'hFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF; b= 120'd1;
		#5
		
		c_in = 1'b0; a = 120'd0; b= 120'd0;
		#5
		
		c_in = 1'b1; a = 120'd0; b= 120'd0;
		#5
		
		c_in = 1'b1; a = 120'd10; b= 120'd20;
		#5
		
		c_in = 1'b1; a = 120'd10; b= 120'd20;
		#5
		
		c_in = 1'b1; a = 120'd234513; b= 120'd4352134625436;
		#5

		$stop;
	end
endmodule

