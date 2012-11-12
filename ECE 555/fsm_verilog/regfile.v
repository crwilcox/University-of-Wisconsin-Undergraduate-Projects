// Library - ece555_projectlib, Cell - regfile, View - schematic
// LAST TIME SAVED: Nov 20 14:43:31 2010
// NETLIST TIME: Nov 21 20:38:45 2010
`timescale 1ns / 1ns 

module regfile ( a, b, Z, clk, rd_enA, rd_enB, wr_en );

input  clk;

output [31:0]  b;
output [31:0]  a;

input [4:0]  rd_enA;
input [4:0]  wr_en;
input [31:0]  Z;
input [4:0]  rd_enB;


specify 
    specparam CDS_LIBNAME  = "ece555_projectlib";
    specparam CDS_CELLNAME = "regfile";
    specparam CDS_VIEWNAME = "schematic";
endspecify

reg32_tworead I9 ( a[31:0], b[31:0], Z[31:0], clk, rd_enA[0],
     rd_enB[0], wr_en[0]);
reg32_tworead I8 ( a[31:0], b[31:0], Z[31:0], clk, rd_enA[1],
     rd_enB[1], wr_en[1]);
reg32_tworead I7 ( a[31:0], b[31:0], Z[31:0], clk, rd_enA[2],
     rd_enB[2], wr_en[2]);
reg32_tworead I6 ( a[31:0], b[31:0], Z[31:0], clk, rd_enA[3],
     rd_enB[3], wr_en[3]);
reg32_tworead I5 ( a[31:0], b[31:0], Z[31:0], clk, rd_enA[4],
     rd_enB[4], wr_en[4]);//CHANGE BACK TO wr_en[4]

endmodule
