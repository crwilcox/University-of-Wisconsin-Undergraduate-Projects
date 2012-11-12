`timescale 1ns/10ps

module dff_async_rst_0 ( clk, d, rst, q );
  input clk, d, rst;
  output q;
  wire   n1;

  FD2QM1P q_reg ( .D(d), .CP(clk), .CD(n1), .Q(q) );
  N1M1P U3 ( .A(rst), .Z(n1) );
endmodule


module dff_async_rst_1 ( clk, d, rst, q );
  input clk, d, rst;
  output q;
  wire   n2;

  FD2QM1P q_reg ( .D(d), .CP(clk), .CD(n2), .Q(q) );
  N1M1P U3 ( .A(rst), .Z(n2) );
endmodule


module dff_async_rst_2 ( clk, d, rst, q );
  input clk, d, rst;
  output q;
  wire   n2;

  FD2QM1P q_reg ( .D(d), .CP(clk), .CD(n2), .Q(q) );
  N1M1P U3 ( .A(rst), .Z(n2) );
endmodule


module dff_async_rst_3 ( clk, d, rst, q );
  input clk, d, rst;
  output q;
  wire   n2;

  FD2QM1P q_reg ( .D(d), .CP(clk), .CD(n2), .Q(q) );
  N1M1P U3 ( .A(rst), .Z(n2) );
endmodule


module prime_detector_structural_netlist ( in_bit, clk, rst, prime );
  input in_bit, clk, rst;
  output prime;
  wire   s0, s1, s2, s3, dff3_in, dff2_in, dff1_in, dff0_in, n1, n2, n3, n4,
         n5, n6, n7, n8, n9, n10, n11, n12, n13, n14, n15, n16;

  dff_async_rst_0 dff3 ( .clk(clk), .d(dff3_in), .rst(rst), .q(s3) );
  dff_async_rst_3 dff2 ( .clk(clk), .d(dff2_in), .rst(rst), .q(s2) );
  dff_async_rst_2 dff1 ( .clk(clk), .d(dff1_in), .rst(rst), .q(s1) );
  dff_async_rst_1 dff0 ( .clk(clk), .d(dff0_in), .rst(rst), .q(s0) );
  MUX21LM1P U22 ( .A(n15), .B(n14), .S(in_bit), .Z(n12) );
  N1M1P U23 ( .A(n8), .Z(n1) );
  N1M1P U24 ( .A(s3), .Z(n2) );
  NR3M1P U25 ( .A(s1), .B(s2), .C(n2), .Z(n8) );
  N1M1P U26 ( .A(s0), .Z(n4) );
  N1M1P U27 ( .A(s2), .Z(n3) );
  OAI21M1P U28 ( .A(s1), .B(n3), .C(n4), .Z(n15) );
  AND2M1P U29 ( .A(n1), .B(n16), .Z(n9) );
  ND3M1P U30 ( .A(n4), .B(n2), .C(s2), .Z(n16) );
  OAI22M1P U31 ( .A(in_bit), .B(n9), .C(n10), .D(n11), .Z(dff2_in) );
  AOI22M1P U32 ( .A(in_bit), .B(s0), .C(s1), .D(n4), .Z(n10) );
  ND2M1P U33 ( .A(n3), .B(n2), .Z(n11) );
  OAI22M1P U34 ( .A(s3), .B(n12), .C(n1), .D(n13), .Z(dff1_in) );
  ND2M1P U35 ( .A(n5), .B(n4), .Z(n13) );
  NR2M1P U36 ( .A(s2), .B(s0), .Z(n14) );
  OAI21M1P U37 ( .A(s3), .B(in_bit), .C(n9), .Z(dff0_in) );
  NR2M1P U38 ( .A(n7), .B(n5), .Z(dff3_in) );
  AOI21M1P U39 ( .A(s2), .B(n2), .C(n8), .Z(n7) );
  ND2M1P U40 ( .A(n2), .B(n6), .Z(prime) );
  ND3M1P U41 ( .A(s1), .B(s0), .C(s2), .Z(n6) );
  N1M1P U42 ( .A(in_bit), .Z(n5) );
endmodule

