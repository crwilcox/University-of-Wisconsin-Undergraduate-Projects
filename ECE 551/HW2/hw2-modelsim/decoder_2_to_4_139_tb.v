module decoder_2_to_4_139_tb();
   
	// This is a testbench for the decoder_2_to_4_139 module.
	// It exhaustively tests all input values of decoder_2_to_4.
   
	reg [3:0] input_values = 4'b0000;
	wire O0, O1, O2, O3;
	
	decoder_2_to_4_139 DUT(
				.E(input_values[2]),
				.A0(input_values[1]),
				.A1(input_values[0]),
				.O0(O0),
				.O1(O1),
				.O2(O2),
				.O3(O3)
				);
				
	initial
	begin
		//Go over all possible values of input for the decoder_2_to_4_139 Module
		for( input_values = 4'b0000; input_values < 4'b1000; input_values = input_values + 1)
		begin
			#5;
		end
		$stop;
	end
	
endmodule