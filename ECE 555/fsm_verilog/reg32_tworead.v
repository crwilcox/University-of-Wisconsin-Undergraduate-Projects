// Library - ece555_projectlib, Cell - reg32_tworead, View - schematic
// LAST TIME SAVED: Nov 20 11:51:02 2010
// NETLIST TIME: Nov 21 20:38:45 2010
`timescale 1ns / 1ns 

module reg32_tworead ( a, b, D, clk, rd_enA, rd_enB, wr_en );

input  clk, rd_enA, rd_enB, wr_en;

output [31:0]  b;
output [31:0]  a;

input [31:0]  D;

// Buses in the design

wire  [31:0]  Q;


specify 
    specparam CDS_LIBNAME  = "ece555_projectlib";
    specparam CDS_CELLNAME = "reg32_tworead";
    specparam CDS_VIEWNAME = "schematic";
endspecify

reg32 I0 ( Q[31:0], D[31:0], clk, wr_en);
nmos N1 ( rd_enB_BAR, 0, rd_enB);
nmos N0 ( rd_enA_BAR, 0, rd_enA);
passgate_dualselect I41 ( a[31], b[31], Q[31], rd_enA, rd_enA_BAR,
     rd_enB, rd_enB_BAR);
passgate_dualselect I40 ( a[30], b[30], Q[30], rd_enA, rd_enA_BAR,
     rd_enB, rd_enB_BAR);
passgate_dualselect I39 ( a[29], b[29], Q[29], rd_enA, rd_enA_BAR,
     rd_enB, rd_enB_BAR);
passgate_dualselect I38 ( a[28], b[28], Q[28], rd_enA, rd_enA_BAR,
     rd_enB, rd_enB_BAR);
passgate_dualselect I37 ( a[27], b[27], Q[27], rd_enA, rd_enA_BAR,
     rd_enB, rd_enB_BAR);
passgate_dualselect I36 ( a[26], b[26], Q[26], rd_enA, rd_enA_BAR,
     rd_enB, rd_enB_BAR);
passgate_dualselect I35 ( a[25], b[25], Q[25], rd_enA, rd_enA_BAR,
     rd_enB, rd_enB_BAR);
passgate_dualselect I34 ( a[24], b[24], Q[24], rd_enA, rd_enA_BAR,
     rd_enB, rd_enB_BAR);
passgate_dualselect I33 ( a[23], b[23], Q[23], rd_enA, rd_enA_BAR,
     rd_enB, rd_enB_BAR);
passgate_dualselect I32 ( a[22], b[22], Q[22], rd_enA, rd_enA_BAR,
     rd_enB, rd_enB_BAR);
passgate_dualselect I31 ( a[21], b[21], Q[21], rd_enA, rd_enA_BAR,
     rd_enB, rd_enB_BAR);
passgate_dualselect I30 ( a[20], b[20], Q[20], rd_enA, rd_enA_BAR,
     rd_enB, rd_enB_BAR);
passgate_dualselect I29 ( a[19], b[19], Q[19], rd_enA, rd_enA_BAR,
     rd_enB, rd_enB_BAR);
passgate_dualselect I25 ( a[15], b[15], Q[15], rd_enA, rd_enA_BAR,
     rd_enB, rd_enB_BAR);
passgate_dualselect I26 ( a[16], b[16], Q[16], rd_enA, rd_enA_BAR,
     rd_enB, rd_enB_BAR);
passgate_dualselect I27 ( a[17], b[17], Q[17], rd_enA, rd_enA_BAR,
     rd_enB, rd_enB_BAR);
passgate_dualselect I28 ( a[18], b[18], Q[18], rd_enA, rd_enA_BAR,
     rd_enB, rd_enB_BAR);
passgate_dualselect I20 ( a[10], b[10], Q[10], rd_enA, rd_enA_BAR,
     rd_enB, rd_enB_BAR);
passgate_dualselect I21 ( a[11], b[11], Q[11], rd_enA, rd_enA_BAR,
     rd_enB, rd_enB_BAR);
passgate_dualselect I22 ( a[12], b[12], Q[12], rd_enA, rd_enA_BAR,
     rd_enB, rd_enB_BAR);
passgate_dualselect I23 ( a[13], b[13], Q[13], rd_enA, rd_enA_BAR,
     rd_enB, rd_enB_BAR);
passgate_dualselect I24 ( a[14], b[14], Q[14], rd_enA, rd_enA_BAR,
     rd_enB, rd_enB_BAR);
passgate_dualselect I10 ( a[0], b[0], Q[0], rd_enA, rd_enA_BAR, rd_enB,
     rd_enB_BAR);
passgate_dualselect I11 ( a[1], b[1], Q[1], rd_enA, rd_enA_BAR, rd_enB,
     rd_enB_BAR);
passgate_dualselect I12 ( a[2], b[2], Q[2], rd_enA, rd_enA_BAR, rd_enB,
     rd_enB_BAR);
passgate_dualselect I13 ( a[3], b[3], Q[3], rd_enA, rd_enA_BAR, rd_enB,
     rd_enB_BAR);
passgate_dualselect I14 ( a[4], b[4], Q[4], rd_enA, rd_enA_BAR, rd_enB,
     rd_enB_BAR);
passgate_dualselect I16 ( a[6], b[6], Q[6], rd_enA, rd_enA_BAR, rd_enB,
     rd_enB_BAR);
passgate_dualselect I15 ( a[5], b[5], Q[5], rd_enA, rd_enA_BAR, rd_enB,
     rd_enB_BAR);
passgate_dualselect I17 ( a[7], b[7], Q[7], rd_enA, rd_enA_BAR, rd_enB,
     rd_enB_BAR);
passgate_dualselect I18 ( a[8], b[8], Q[8], rd_enA, rd_enA_BAR, rd_enB,
     rd_enB_BAR);
passgate_dualselect I19 ( a[9], b[9], Q[9], rd_enA, rd_enA_BAR, rd_enB,
     rd_enB_BAR);
pmos P1 ( rd_enB_BAR, 1, rd_enB);
pmos P0 ( rd_enA_BAR, 1, rd_enA);

endmodule
