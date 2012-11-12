module modulo_6_tb();
   
   // This is a testbench for the modulo_6 module.
   // It exhaustively tests all input values of modulo_6.
   
   reg [3:0]	original_value = 4'b0000;
   wire [2:0]	mod6_value;

   modulo_6 DUT(
				.original_value(original_value[2:0]),
				.mod6_value(mod6_value)
				);
				
	initial
	begin
		//Go over all possible values of input for the modulo_6 module
		for( original_value = 4'b0000; original_value < 4'b1000; original_value = original_value + 1)
		begin
			$display("Original Value = %d, mod6 Value = %d", original_value, mod6_value);
			#10;
		end
		$stop;
	end
	
endmodule