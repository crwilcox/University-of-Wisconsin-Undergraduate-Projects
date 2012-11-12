module pg_adder(A, B, Cin, P, G, S);
   input A, B, Cin;
   output P, G, S;

   assign P = (A | B);
   assign G = (A & B);
   assign S = (A ^ B) ^ Cin;
   
endmodule // pg_adder
