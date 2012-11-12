module decoder_2_to_4_139(input E, A0, A1, output O0, O1, O2, O3);

	//This module recreates the functionality of the 74139 2-to-4 line decoder.
	
	assign O0 = E || A1 || A0;

	assign O1 = E || !A0 || A1;
	
	assign O2 = E || A0 || !A1;
	
	assign O3 = E || !A0 || !A1;
	
endmodule
