module decoder_3_to_8(input enable_hi, A0, A1, A2, output O0, O1, O2, O3, O4, O5, O6, O7);

	//This module implements the functionality of a 3 to 8 decoder using multiple 74139 2-to-4 line decoders
	
	wire enable_hi_n, A0_n, enable_0_3, enable_4_7;
	
	not (enable_hi_n, enable_hi);
	not (A0_n, A0);
	
	//for bits 0 to 3 the enable signal should be !(enable_hi && !A0) = !enable_hi || A0
	or (enable_0_3, enable_hi_n, A0);
	decoder_2_to_4_139 output_0_to_3 (
									.E(enable_0_3),
									.A0(A1),
									.A1(A2),
									.O0(O0),
									.O1(O1),
									.O2(O2),
									.O3(O3)
									);
									
	//for bits 4 to 7 the enable signal should be !(enable_hi && A0) = !enable_hi || !A0
	or (enable_4_7, enable_hi_n, A0_n);
	decoder_2_to_4_139 output_4_to_7 (
									.E(enable_4_7),
									.A0(A1),
									.A1(A2),
									.O0(O4),
									.O1(O5),
									.O2(O6),
									.O3(O7)
									);
	
endmodule
