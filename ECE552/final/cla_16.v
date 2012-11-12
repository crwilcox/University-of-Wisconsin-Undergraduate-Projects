
module cla_16(A, B, Cin, Cout, SUM);
   input [15:0] A, B;
   input 	Cin;
   output [15:0] SUM;
   output 	 Cout;
   wire [4:1] 	 C;
   wire [3:0] 	 P, G;
         
   cla_4 c0(A[3:0], B[3:0], Cin, C[1], P[0], G[0], SUM[3:0]);
   cla_4 c1(A[7:4], B[7:4], C[1], C[2], P[1], G[1], SUM[7:4]);
   cla_4 c2(A[11:8], B[11:8], C[2], C[3], P[2], G[2], SUM[11:8]);
   cla_4 c3(A[15:12], B[15:12], C[3], C[4], P[3], G[3], SUM[15:12]);
   
   cla_logic log0(P, G, Cin, Pg, Gg, C);
   
   assign Cout = C[4];

endmodule // CLA_16
