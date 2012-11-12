module prime_detector_behavioral_structural_tb();
	// This is a testbench for the structural and behavioral prime detector
   
   	reg clk;
	reg rst;
	reg in_bit;
	wire behavioral_output, structural_output, error;
	assign error = (behavioral_output != structural_output);
	
prime_detector_structural strucDUT(.in_bit(in_bit), .clk(clk), .rst(rst), .prime(structural_output));

prime_detector_behavioral behavDUT(.in_bit(in_bit), .clk(clk), .rst(rst), .prime(behavioral_output));	

//Establish a clk				
always 
 #5  clk =  ! clk;

initial 
begin 
	clk = 1'b0; 
	rst = 1'b1;
	in_bit = 1'b0;
	#10
	
	//release reset
	rst = 1'b0; 
	
	//xx0
	in_bit = 1'b0;
	#10
	
	//x01
	in_bit = 1'b1;
	#10
	
	//010
	in_bit = 1'b0;
	#10
	
	//100
	in_bit = 1'b0;
	#10
	
	//001
	in_bit = 1'b1;
	#10
	
	//011
	in_bit = 1'b1;
	#10
	
	//111
	in_bit = 1'b1;
	#10
	
	//reset the detector
	rst = 1'b1;
	#10
	rst = 1'b0;
	
	//xx1
	in_bit = 1'b1;
	#10
	
	//x10
	in_bit = 1'b0;
	#10

	//101
	in_bit = 1'b1;
	#10
	
	//010
	in_bit = 1'b0;
	#10
	
	//100
	in_bit = 1'b0;
	#10
	
	//000
	in_bit = 1'b0;
	#10
	
	//000
	in_bit = 1'b0;
	#10
	
	$stop;
end 
endmodule