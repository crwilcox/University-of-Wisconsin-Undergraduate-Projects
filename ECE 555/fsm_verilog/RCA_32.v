// Library - ece555_projectlib, Cell - RCA_32, View - schematic
// LAST TIME SAVED: Nov 20 14:21:35 2010
// NETLIST TIME: Nov 21 20:38:45 2010
`timescale 1ns / 1ns 

module RCA_32 ( Z, a, add_en, b );

input  add_en;

output [31:0]  Z;

input [31:0]  b;
input [31:0]  a;


specify 
    specparam CDS_LIBNAME  = "ece555_projectlib";
    specparam CDS_CELLNAME = "RCA_32";
    specparam CDS_VIEWNAME = "schematic";
endspecify

RCA_1bit I32 ( Z[31], net418, a[31], add_en, net641, b[31], net423);
RCA_1bit I31 ( Z[30], net423, a[30], add_en, net641, b[30], net430);
RCA_1bit I30 ( Z[29], net430, a[29], add_en, net641, b[29], net437);
RCA_1bit I29 ( Z[28], net437, a[28], add_en, net641, b[28], net444);
RCA_1bit I28 ( Z[27], net444, a[27], add_en, net641, b[27], net451);
RCA_1bit I27 ( Z[26], net451, a[26], add_en, net641, b[26], net458);
RCA_1bit I26 ( Z[25], net458, a[25], add_en, net641, b[25], net465);
RCA_1bit I25 ( Z[24], net465, a[24], add_en, net641, b[24], net472);
RCA_1bit I24 ( Z[23], net472, a[23], add_en, net641, b[23], net479);
RCA_1bit I23 ( Z[22], net479, a[22], add_en, net641, b[22], net486);
RCA_1bit I22 ( Z[21], net486, a[21], add_en, net641, b[21], net493);
RCA_1bit I21 ( Z[20], net493, a[20], add_en, net641, b[20], net500);
RCA_1bit I20 ( Z[19], net500, a[19], add_en, net641, b[19], net507);
RCA_1bit I19 ( Z[18], net507, a[18], add_en, net641, b[18], net514);
RCA_1bit I18 ( Z[17], net514, a[17], add_en, net641, b[17], net521);
RCA_1bit I17 ( Z[16], net521, a[16], add_en, net641, b[16], net528);
RCA_1bit I16 ( Z[15], net528, a[15], add_en, net641, b[15], net535);
RCA_1bit I15 ( Z[14], net535, a[14], add_en, net641, b[14], net542);
RCA_1bit I14 ( Z[13], net542, a[13], add_en, net641, b[13], net549);
RCA_1bit I13 ( Z[12], net549, a[12], add_en, net641, b[12], net556);
RCA_1bit I12 ( Z[11], net556, a[11], add_en, net641, b[11], net563);
RCA_1bit I11 ( Z[10], net563, a[10], add_en, net641, b[10], net570);
RCA_1bit I10 ( Z[9], net570, a[9], add_en, net641, b[9], net577);
RCA_1bit I9 ( Z[8], net577, a[8], add_en, net641, b[8], net584);
RCA_1bit I8 ( Z[7], net584, a[7], add_en, net641, b[7], net591);
RCA_1bit I7 ( Z[6], net591, a[6], add_en, net641, b[6], net598);
RCA_1bit I6 ( Z[5], net598, a[5], add_en, net641, b[5], net605);
RCA_1bit I5 ( Z[4], net605, a[4], add_en, net641, b[4], net612);
RCA_1bit I4 ( Z[3], net612, a[3], add_en, net641, b[3], net619);
RCA_1bit I3 ( Z[2], net619, a[2], add_en, net641, b[2], net626);
RCA_1bit I2 ( Z[1], net626, a[1], add_en, net641, b[1], net633);
RCA_1bit I1 ( Z[0], net633, a[0], add_en, net641, b[0],
     0);
nmos N0 ( net641, 0, add_en);
pmos P0 ( net641, 1, add_en);

endmodule
