module reg15(q, d, clk, rst);
   input [14:0] d;
   input 	clk, rst;
   
   output [14:0] q;

   dff r[14:0](q, d, clk, rst);
endmodule
   
