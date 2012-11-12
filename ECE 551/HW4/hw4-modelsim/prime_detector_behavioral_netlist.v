`timescale 1ns/10ps

module prime_detector_behavioral_netlist ( in_bit, clk, rst, prime );
  input in_bit, clk, rst;
  output prime;
  wire   N64, N65, N66, N67, N68, n1, n2, n3, n4, n5, n6, n7, n8, n9, n10, n11,
         n12, n13, n14, n15, n16, n17, n18, n19, n20, n21;
  wire   [3:0] state;

  FD2QM1P \state_reg[0]  ( .D(N64), .CP(clk), .CD(n1), .Q(state[0]) );
  FD2QM1P \state_reg[2]  ( .D(N66), .CP(clk), .CD(n1), .Q(state[2]) );
  FD2QM1P \state_reg[1]  ( .D(N65), .CP(clk), .CD(n1), .Q(state[1]) );
  FD2QM1P \state_reg[3]  ( .D(N67), .CP(clk), .CD(n1), .Q(state[3]) );
  FD2QM1P prime_reg ( .D(N68), .CP(clk), .CD(n1), .Q(prime) );
  N1M1P U3 ( .A(rst), .Z(n1) );
  ND3CNM1P U27 ( .C(state[1]), .A(state[2]), .B(n19), .Z(n16) );
  AO21CNM1P U28 ( .A(n18), .B(n19), .C(n11), .Z(n14) );
  ND2M1P U29 ( .A(n8), .B(n12), .Z(n15) );
  AOI21M1P U30 ( .A(n9), .B(n8), .C(n5), .Z(N67) );
  ND2M1P U31 ( .A(n21), .B(n3), .Z(n12) );
  OAI21M1P U32 ( .A(n6), .B(n5), .C(n2), .Z(N68) );
  N1M1P U33 ( .A(n7), .Z(n2) );
  AND2M1P U34 ( .A(n8), .B(n9), .Z(n6) );
  AOI22M1P U35 ( .A(state[2]), .B(n19), .C(n18), .D(state[3]), .Z(n9) );
  NR2M1P U36 ( .A(state[3]), .B(state[0]), .Z(n19) );
  NR2M1P U37 ( .A(state[2]), .B(state[1]), .Z(n18) );
  ND2M1P U38 ( .A(n21), .B(state[2]), .Z(n8) );
  NR2M1P U39 ( .A(n4), .B(state[3]), .Z(n21) );
  ND3M1P U40 ( .A(n19), .B(n3), .C(state[1]), .Z(n11) );
  OAI211M1P U41 ( .A(in_bit), .B(n9), .C(n10), .D(n11), .Z(N66) );
  ND2BNM1P U42 ( .B(n12), .A(in_bit), .Z(n10) );
  N1M1P U43 ( .A(state[2]), .Z(n3) );
  ND2M1P U44 ( .A(n16), .B(n17), .Z(n7) );
  ND3M1P U45 ( .A(n18), .B(n4), .C(state[3]), .Z(n17) );
  OAI21M1P U46 ( .A(in_bit), .B(n20), .C(n9), .Z(N64) );
  NR2M1P U47 ( .A(n15), .B(n14), .Z(n20) );
  N1M1P U48 ( .A(state[0]), .Z(n4) );
  OAI22CDNM1P U49 ( .A(in_bit), .B(n13), .C(n14), .D(in_bit), .Z(N65) );
  NR2M1P U50 ( .A(n7), .B(n15), .Z(n13) );
  N1M1P U51 ( .A(in_bit), .Z(n5) );
endmodule

