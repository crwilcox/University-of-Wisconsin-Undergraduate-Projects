module reg16(q, d, clk, rst);
   input [15:0] d;
   input 	clk, rst;
   
   output [15:0] q;

   dff r[15:0](q, d, clk, rst);
endmodule
   
