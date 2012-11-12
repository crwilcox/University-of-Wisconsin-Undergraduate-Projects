// Library - ece555_projectlib, Cell - ppgen_32, View - schematic
// LAST TIME SAVED: Nov 20 13:17:45 2010
// NETLIST TIME: Nov 21 20:38:45 2010
`timescale 1ns / 1ns 

module ppgen_32 ( Z, A, B, ppgen_en );

input  B, ppgen_en;

output [31:0]  Z;

input [31:0]  A;


specify 
    specparam CDS_LIBNAME  = "ece555_projectlib";
    specparam CDS_CELLNAME = "ppgen_32";
    specparam CDS_VIEWNAME = "schematic";
endspecify

ppgen_1bit I31 ( Z[31], A[31], B, ppgen_en, ppgen_en_BAR);
ppgen_1bit I30 ( Z[30], A[30], B, ppgen_en, ppgen_en_BAR);
ppgen_1bit I29 ( Z[29], A[29], B, ppgen_en, ppgen_en_BAR);
ppgen_1bit I28 ( Z[28], A[28], B, ppgen_en, ppgen_en_BAR);
ppgen_1bit I27 ( Z[27], A[27], B, ppgen_en, ppgen_en_BAR);
ppgen_1bit I26 ( Z[26], A[26], B, ppgen_en, ppgen_en_BAR);
ppgen_1bit I25 ( Z[25], A[25], B, ppgen_en, ppgen_en_BAR);
ppgen_1bit I24 ( Z[24], A[24], B, ppgen_en, ppgen_en_BAR);
ppgen_1bit I23 ( Z[23], A[23], B, ppgen_en, ppgen_en_BAR);
ppgen_1bit I22 ( Z[22], A[22], B, ppgen_en, ppgen_en_BAR);
ppgen_1bit I21 ( Z[21], A[21], B, ppgen_en, ppgen_en_BAR);
ppgen_1bit I20 ( Z[20], A[20], B, ppgen_en, ppgen_en_BAR);
ppgen_1bit I19 ( Z[19], A[19], B, ppgen_en, ppgen_en_BAR);
ppgen_1bit I18 ( Z[18], A[18], B, ppgen_en, ppgen_en_BAR);
ppgen_1bit I17 ( Z[17], A[17], B, ppgen_en, ppgen_en_BAR);
ppgen_1bit I16 ( Z[16], A[16], B, ppgen_en, ppgen_en_BAR);
ppgen_1bit I15 ( Z[15], A[15], B, ppgen_en, ppgen_en_BAR);
ppgen_1bit I14 ( Z[14], A[14], B, ppgen_en, ppgen_en_BAR);
ppgen_1bit I13 ( Z[13], A[13], B, ppgen_en, ppgen_en_BAR);
ppgen_1bit I12 ( Z[12], A[12], B, ppgen_en, ppgen_en_BAR);
ppgen_1bit I11 ( Z[11], A[11], B, ppgen_en, ppgen_en_BAR);
ppgen_1bit I10 ( Z[10], A[10], B, ppgen_en, ppgen_en_BAR);
ppgen_1bit I9 ( Z[9], A[9], B, ppgen_en, ppgen_en_BAR);
ppgen_1bit I8 ( Z[8], A[8], B, ppgen_en, ppgen_en_BAR);
ppgen_1bit I7 ( Z[7], A[7], B, ppgen_en, ppgen_en_BAR);
ppgen_1bit I6 ( Z[6], A[6], B, ppgen_en, ppgen_en_BAR);
ppgen_1bit I5 ( Z[5], A[5], B, ppgen_en, ppgen_en_BAR);
ppgen_1bit I4 ( Z[4], A[4], B, ppgen_en, ppgen_en_BAR);
ppgen_1bit I3 ( Z[3], A[3], B, ppgen_en, ppgen_en_BAR);
ppgen_1bit I2 ( Z[2], A[2], B, ppgen_en, ppgen_en_BAR);
ppgen_1bit I1 ( Z[1], A[1], B, ppgen_en, ppgen_en_BAR);
ppgen_1bit I0 ( Z[0], A[0], B, ppgen_en, ppgen_en_BAR);
pmos P0 ( ppgen_en_BAR, 1, ppgen_en);
nmos N0 ( ppgen_en_BAR, 0, ppgen_en);

endmodule
