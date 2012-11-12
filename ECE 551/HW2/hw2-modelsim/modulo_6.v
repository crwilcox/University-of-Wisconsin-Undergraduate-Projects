module modulo_6 (input [2:0] original_value, output [2:0] mod6_value);

	//This module does the operation mod6_value = original_value mod 6. 
	//both input and output values are unsigned.

	wire original_value_2_n;
	wire original_value_1_n;
	
	not (original_value_2_n, original_value[2]);
	not (original_value_1_n, original_value[1]);
	
	//mod6_value[2] = ~original_value[1] & original_value[2] 
	and (mod6_value[2], original_value_1_n, original_value[2]);
	
	//mod6_value[1] = original_value[1] & ~original_value[2] 
	and (mod6_value[1], original_value[1], original_value_2_n);
	
	//mod6_value[0] = original_value[0];
	and(mod6_value[0], original_value[0], 1);

endmodule