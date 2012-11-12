
`timescale 1ns / 1ns 

module t_toplevel();

	reg clk,rst,start,fail;
	reg [31:0] Z;
	wire [31:0] A,B;
	reg [31:0] multiplier32, multiplicand32,result;
	localparam input_length = 2;
	integer i = 0;
	reg [15:0] mpliers[0:input_length-1];
	reg [15:0] mcands[0:input_length-1];


	toplevel DUT(.A(A),.B(B),.Z(Z),.done(done),.clk(clk),.rst(rst),.start(start));

	task multiply;
		input [15:0] multiplier16, multiplicand16;
		begin	
			multiplier32 = {16'd0,multiplier16[15:0]}; //zext
			//multiplier32 = {{16{multiplier16[15]}},multiplier16[15:0]}; //zext
			multiplicand32 = {16'd0,multiplicand16[15:0]};
			//multiplicand32 = {{16{multiplicand16[15]}},multiplicand16[15:0]};
			/*first cycle*/ @(posedge clk)  #1 start = 1'b1;
							Z = multiplicand32;
			/*second cycle*/@(posedge clk)	#1 Z = multiplier32; start = 1'b0;
			/*third cycle*/	@(posedge clk)	#1 Z = 32'd0;
			/*third cycle*/	@(posedge clk)	#1 Z = 32'd1;
			/*stop driving bus Q5*/	@(posedge clk)#1	 Z = 32'hzzzzzzzz;
			/*sit and wait for iterations*/
					@(posedge done) result = A;
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
		$readmemh("./test_mpliers",mpliers,0,input_length-1);
		$readmemh("./test_mcands",mcands,0,input_length-1);
		#40 rst = 1'b0;

		for(i=0; i < input_length; i=i+1) begin
			multiply(mpliers[i],mcands[i]);
			if ( result != mpliers[i]*mcands[i]) begin
				fail = 1'b1;
			end
		end

		#5 $stop;

	end


endmodule
