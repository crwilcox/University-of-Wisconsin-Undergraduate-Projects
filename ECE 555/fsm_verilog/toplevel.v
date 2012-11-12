 // Library - ece555_projectlib, Cell - toplevel, View - schematic
// LAST TIME SAVED: Nov 30 12:57:40 2010
// NETLIST TIME: Nov 30 12:58:10 2010
`timescale 1ns / 1ns 

module toplevel ( A, B, done, Z, clk, rst, start );

output  done;

input  clk, rst, start;

output [31:0]  B;
output [31:0]  A;

input [31:0]  Z;

// Buses in the design

wire  [4:0]  wr_en;

wire  [4:0]  rd_enB;

wire  [4:0]  rd_enA;

// List of all aliases


/*cds_alias  cds_alias_inst1(rd_enB[4], 0);
cds_alias  cds_alias_inst2(rd_enB[2], 0);
cds_alias  cds_alias_inst3(rd_enA[1], 0);
cds_alias  cds_alias_inst4(rd_enA[3], done);
cds_alias cds_alias_inst5(rd_enB[3], rd_enA[2]);
cds_alias  cds_alias_inst6(wr_en[2], rd_enA[0]);
*/
assign rd_enB[4] = 0;
assign rd_enB[2] = 0;
assign rd_enA[1] = 0;
//assign rd_enA[3] = done;
assign  done= rd_enA[3];
assign rd_enB[3] = rd_enA[2];
assign wr_en[2] = rd_enA[0];

specify 
    specparam CDS_LIBNAME  = "ece555_projectlib";
    specparam CDS_CELLNAME = "toplevel";
    specparam CDS_VIEWNAME = "schematic";
endspecify

RCA_32 I0 ( Z[31:0], A[31:0], rd_enA[2], B[31:0]);
regfile I1 ( A[31:0], B[31:0], Z[31:0], clk, rd_enA[4:0], rd_enB[4:0], wr_en[4:0]);
fsm I2 ( net33, rd_enA[2], rd_enA[3], rd_enA[4], rd_enB[0], rd_enB[1], wr_en[0], wr_en[1], rd_enA[0], wr_en[3], wr_en[4], clk, rst, start);
ppgen_32 I3 ( Z[31:0], A[31:0], B[0], rd_enA[0]);
shifter I4 ( Z[31:0], A[31:0], B[31:0], net33, rd_enA[4]);

endmodule
