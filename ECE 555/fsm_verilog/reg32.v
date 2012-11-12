// Library - ece555_projectlib, Cell - reg32, View - schematic
// LAST TIME SAVED: Nov 21 21:16:43 2010
// NETLIST TIME: Nov 21 21:17:00 2010
`timescale 1ns / 1ns 

module reg32 ( Q, D, clk, wr_en );


input  clk, wr_en;

output [31:0]  Q;

input [31:0]  D;


specify 
    specparam CDS_LIBNAME  = "ece555_projectlib";
    specparam CDS_CELLNAME = "reg32";
    specparam CDS_VIEWNAME = "schematic";
endspecify

dff I31 ( Q[31], D[31], net42);
dff I30 ( Q[30], D[30], net42);
dff I29 ( Q[29], D[29], net42);
dff I28 ( Q[28], D[28], net42);
dff I27 ( Q[27], D[27], net42);
dff I26 ( Q[26], D[26], net42);
dff I25 ( Q[25], D[25], net42);
dff I24 ( Q[24], D[24], net42);
dff I23 ( Q[23], D[23], net42);
dff I22 ( Q[22], D[22], net42);
dff I21 ( Q[21], D[21], net42);
dff I20 ( Q[20], D[20], net42);
dff I19 ( Q[19], D[19], net42);
dff I18 ( Q[18], D[18], net42);
dff I17 ( Q[17], D[17], net42);
dff I16 ( Q[16], D[16], net42);
dff I15 ( Q[15], D[15], net42);
dff I14 ( Q[14], D[14], net42);
dff I13 ( Q[13], D[13], net42);
dff I12 ( Q[12], D[12], net42);
dff I11 ( Q[11], D[11], net42);
dff I10 ( Q[10], D[10], net42);
dff I9 ( Q[9], D[9], net42);
dff I8 ( Q[8], D[8], net42);
dff I7 ( Q[7], D[7], net42);
dff I6 ( Q[6], D[6], net42);
dff I5 ( Q[5], D[5], net42);
dff I4 ( Q[4], D[4], net42);
dff I3 ( Q[3], D[3], net42);
dff I2 ( Q[2], D[2], net42);
dff I1 ( Q[1], D[1], net42);
dff I0 ( Q[0], D[0], net42);
pmos P2 ( net0196, 1, clk);
pmos P1 ( net42, 1, wr_en);
pmos P0 ( net42, 1, net0196);
nmos N2 ( net0196, 0, clk);
nmos N1 ( net0107, 0, net0196);
nmos N0 ( net42, net0107, wr_en);

endmodule
