module priority_encoder_10_to_4_74HC(input A0_n, A1_n, A2_n, A3_n, A4_n, A5_n, A6_n, A7_n, A8_n, output reg Y3_n, Y2_n, Y1_n, Y0_n);
	//The module implements a 10 to 4 line priority encoder with part number 74HC/HCT147
	
	wire [8:0] ABus= {A8_n, A7_n, A6_n, A5_n, A4_n, A3_n, A2_n, A1_n, A0_n};
	
	always @(*) begin
		casex(ABus)
			9'b1_1111_1111: {Y3_n, Y2_n, Y1_n, Y0_n} = 4'b1111;
			
			9'bx_xxxx_xxx0: {Y3_n, Y2_n, Y1_n, Y0_n} = 4'b0110;
			9'bx_xxxx_xx01: {Y3_n, Y2_n, Y1_n, Y0_n} = 4'b0111;
			9'bx_xxxx_x011: {Y3_n, Y2_n, Y1_n, Y0_n} = 4'b1000;
			9'bx_xxxx_0111: {Y3_n, Y2_n, Y1_n, Y0_n} = 4'b1001;
			
			9'bx_xxx0_1111: {Y3_n, Y2_n, Y1_n, Y0_n} = 4'b1010;
			9'bx_xx01_1111: {Y3_n, Y2_n, Y1_n, Y0_n} = 4'b1011;
			9'bx_x011_1111: {Y3_n, Y2_n, Y1_n, Y0_n} = 4'b1100;
			9'bx_0111_1111: {Y3_n, Y2_n, Y1_n, Y0_n} = 4'b1101;
			
			9'b0_1111_1111: {Y3_n, Y2_n, Y1_n, Y0_n} = 4'b1110;
		endcase
	end
	
endmodule // priority_encoder_10_to_4_74HC