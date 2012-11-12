`timescale 1ns / 1ns 
module t_shifter();

reg [31:0] A,B; //A = amount to shift
wire [31:0] Z;
reg error;
reg left_right=1'b1; //1=Right
reg shift_en=1'b0;
reg[31:0] expectedResult;


shifter DUT( Z, A, B, left_right, shift_en );

always @(expectedResult) begin
	if(expectedResult != Z) error <= 1'b1;
	else error <= 1'b0;
end

//THIS BLOCK ASSIGNS THE EXPECTED RESULT
integer i;
always @(A, B) begin
	if(shift_en) begin
		if(left_right == 1'b1) begin
			//need to do arithmetic shift, so after logically shifting, sign top bits
			expectedResult <= B>>A;
			
			for(i=0; i<A; i=i+1) begin
				expectedResult[31-i] <= B[31];
			end
		end
	
		else begin //left_right ==1'b0
			expectedResult <= B<<A;
		end
	end
	else begin //not shift_en
		expectedResult <= 32'hZZZZ_ZZZZ;
	end
end

initial begin
	error = 0;

   	shift_en = 1'b0;
   	#1;
   	#1 shift_en = 1'b1;
   	#1;
	   
	#1;
   	B = 32'ha5a5a5a5;
   	A = 32'h00000000;
   	#1;
   
   //TEST 1 bit SHIFTS	
   	#1;
   	A = 32'h00000001;
   	#1;
    
   	#1;
   	left_right = 1'b0;
   	#1;

	//TEST LEFT SHIFTS OF DIFFERENT SIZES
	#1
   	B = 32'hFFFF_FFFF;
   	A = 32'd2;
   	left_right = 1'b0;
	#1
   	
	#1
   	B = 32'hFFFF_FFFF;
   	A = 32'd4;
   	left_right = 1'b0;
   	#1

	#1
   	B = 32'hFFFF_FFFF;
   	A = 32'd8;
   	left_right = 1'b0;
	#1

	#1
   	B = 32'hFFFF_FFFF;
   	A = 32'd16;
   	left_right = 1'b0;
	#1

	#1
   	B = 32'hFFFF_FFFF;
   	A = 32'd31;//MAX SHIFT
   	left_right = 1'b0;
	#1
	#1

	//TEST RIGHT SHIFTS
	//WITH ALL 1's a right shift should still have all 1's
	#1
   	B = 32'hFFFF_FFFF;
   	A = 32'd2;
   	left_right = 1'b1;
	#1
   	
	#1
   	B = 32'h7FFF_FFFF;
   	A = 32'd4;
   	left_right = 1'b1;
   	#1

	#1
   	B = 32'hFF2F_FFFF;
   	A = 32'd8;
   	left_right = 1'b1;
	#1
	
	#1
   	B = 32'hF81F_FFFF;
   	A = 32'd16;
   	left_right = 1'b1;
	#1

	#1
   	B = 32'hF3FF_FFFF;
   	A = 32'd31;//MAX SHIFT
   	left_right = 1'b1;
	#1
	#1

	#1
   	B = 32'h06FF_F7FF;
   	A = 32'd2;
   	left_right = 1'b1;
	#1
   	
	#1
   	B = 32'h0F8F_F5FF;
   	A = 32'd4;
   	left_right = 1'b1;
   	#1

	#1
   	B = 32'h0FFF_FFFF;
   	A = 32'd8;
   	left_right = 1'b1;
	#1

	#1
   	B = 32'h0FFC_FFFF;
   	A = 32'd16;
   	left_right = 1'b1;
	#1

	#1
   	B = 32'h2DFF_CFBF;
   	A = 32'd31;//MAX SHIFT
   	left_right = 1'b1;
	#1

	#1
   	B = 32'h0002;
   	A = 32'd1;
	left_right = 1'b1;
	#1	

	#1

	$stop;

end

endmodule
