`timescale 1ns/10ps

module prime_detector_behavioral_noglitch_tb();
	// This is a testbench for the structural and behavioral prime detector
   
	parameter SAMPLING_DELAY = 2;

   	reg clk;
	reg rst;
	reg in_bit;
	reg error;
	wire behavioral_output, behavioral_netlist_output;
	
	always @(behavioral_output or behavioral_netlist_output) begin
		#SAMPLING_DELAY
		error = (behavioral_output != behavioral_netlist_output);
	end
	
prime_detector_behavioral_netlist behavNetlistDUT(.in_bit(in_bit), .clk(clk), .rst(rst), .prime(behavioral_netlist_output));

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
