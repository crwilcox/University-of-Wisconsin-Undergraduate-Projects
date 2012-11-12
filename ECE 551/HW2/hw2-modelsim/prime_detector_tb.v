module prime_detector_tb();
   // This is a testbench for the modulo_6 module.
   // It exhaustively tests all input values of modulo_6.
	
	reg clk;
	reg rst;
	reg in_bit;
	wire prime;

   prime_detector DUT(
				.in_bit(in_bit),
				.clk(clk),
				.rst(rst),
				.prime(prime)
				);
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