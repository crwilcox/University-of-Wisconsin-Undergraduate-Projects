module alu_16(A, B, Cin, Op, invA, invB, Cout, Out, OFL, Zero);
   input [15:0] A, B;
   input [2:0] 	Op;
   input 	Cin, invA, invB;
   output [15:0] Out;
   output 	 OFL, Zero, Cout;
   wire [15:0] 	 A_inv, B_inv, shift, sum, and16, or16, xor16;
      
   // inversion
   assign A_inv = invA ? ~A : A;
   assign B_inv = invB ? ~B : B;

   // CLA  
   cla_16 cla(.A(A_inv), .B(B_inv), .Cin(Cin), .Cout(Cout), .SUM(sum));

   // shifter
   barrel_shifter shifter(.In(A_inv), .Cnt(B_inv[3:0]), .Op(Op[1:0]), .Out(shift));

   assign and16 = (A_inv & B_inv);
   assign or16 = (A_inv | B_inv);
   assign xor16 = (A_inv ^ B_inv);

   // main mux
   assign Out = Op[2] ? 
		Op[1:0] == 2'b00 ? sum :
		Op[1:0] == 2'b01 ? or16 :
		Op[1:0] == 2'b10 ? xor16 : and16
		: shift;
   

   // overflow
   assign OFL = (~(A_inv[15] ^ B_inv[15]) & (B_inv[15] ^ Out[15]));
   
   // zero
   assign Zero = ~|Out[15:0];
        
endmodule // alu

   
