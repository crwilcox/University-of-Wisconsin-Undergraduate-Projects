module decoder_3_to_8_tb();
   
   // This is a testbench for the decoder_3_to_8_tb module.
   // It exhaustively tests all input values of decoder_3_to_8.
   
	reg [4:0]	input_values = 5'b00000;
	wire O0, O1, O2, O3;


   decoder_3_to_8 DUT(
				.enable_hi(input_values[3]),
				.A0(input_values[2]),
				.A1(input_values[1]),
				.A2(input_values[0]),
				.O0(O0),
				.O1(O1),
				.O2(O2),
				.O3(O3),
				.O4(O4),
				.O5(O5),
				.O6(O6),
				.O7(O7)
				);
				
	initial
	begin
		//Go over all possible values of input for the decoder_3_to_8 Module
		for( input_values = 5'b00000; input_values < 5'b10000; input_values = input_values + 1)
		begin
			#5;
		end
		$stop;
	end
	
endmodule