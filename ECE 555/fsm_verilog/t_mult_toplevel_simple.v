

`timescale 1ns / 1ns 

module t_mult_toplevel_simple();

	reg clk,rst,start,fail;
	reg [31:0] Z;
	wire [31:0] A,B;
	reg [31:0] multiplier32, multiplicand32,result;
	localparam input_length = 7;
	integer i = 0;
	reg [15:0] mpliers[0:input_length-1];
	reg [15:0] mcands[0:input_length-1];


	mult_toplevel DUT(.A(A),.B(B),.Z(Z),.done(done),.clk(clk),.rst(rst),.start(start));

	task multiply;
		input [15:0] multiplier16, multiplicand16;
		output[31:0] product32;
		begin	
			multiplier32 = {{16{multiplier16[15]}},multiplier16[15:0]};
			multiplicand32 = {{16{multiplicand16[15]}},multiplicand16[15:0]};
			/*first cycle Q0*/ @(posedge clk)   #1 start = 1'b1;
			/*first cycle Q1*/ @(posedge clk)   #1 Z = multiplicand32;
			/*second cycle Q2*/@(posedge clk)#1	Z = multiplier32; start = 1'b0;
			/*third cycle Q3*/	@(posedge clk)#1	 Z = 32'd0;
			/*third cycle Q4*/	@(posedge clk)#1	 Z = 32'd1;
			/*stop driving bus Q5*/	@(posedge clk)#1	 Z = 32'hzzzzzzzz;
			
			/*sit and wait for iterations*/
					@(posedge done) product32 = A;
		end
	endtask


	initial begin
		fail = 1'b0;
		clk = 1'b0;
		forever #5 clk = ~clk;
	end

	initial begin

		rst = 1'b1;
		start = 1'b0;
		Z = 32'd0;
		#40 rst = 1'b0;
		multiply(16'd5,16'd12,result);
		if ( result != 32'd10) begin
			fail = 1'b1;
		end
		#5 $stop;

	end


endmodule
