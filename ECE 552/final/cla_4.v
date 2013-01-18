
module cla_4(A, B, Cin, Cout, Pg, Gg, SUM);
   input [3:0] A, B;
   input       Cin;
   output [3:0] SUM;
   output 	Cout, Pg, Gg;
   wire [3:0] 	P, G;
   wire [4:1] 	C;
   
   pg_adder fa0(A[0], B[0], Cin, P[0], G[0], SUM[0]);
   pg_adder fa1(A[1], B[1], C[1], P[1], G[1], SUM[1]);
   pg_adder fa2(A[2], B[2], C[2], P[2], G[2], SUM[2]);
   pg_adder fa3(A[3], B[3], C[3], P[3], G[3], SUM[3]);

   cla_logic log0(P, G, Cin, Pg, Gg, C);

   assign Cout = C[4];

endmodule // cla_4
