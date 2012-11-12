// Library - ece555_projectlib, Cell - RCA_1bit, View - schematic
// LAST TIME SAVED: Nov 21 23:10:21 2010
// NETLIST TIME: Nov 21 23:11:33 2010
`timescale 1ns / 1ns 

module RCA_1bit ( Z, cout, a, add_en, add_en_BAR, b, cin );

output  Z, cout;

input  a, add_en, add_en_BAR, b, cin;


specify 
    specparam CDS_LIBNAME  = "ece555_projectlib";
    specparam CDS_CELLNAME = "RCA_1bit";
    specparam CDS_VIEWNAME = "schematic";
endspecify

FA_SUMBAR I7 ( net041, cout, a, b, cin);
pmos P1 ( Z, net25, add_en_BAR);
pmos P0 ( net25, 1, net041);
nmos N1 ( Z, net25, add_en);
nmos N0 ( net25, 0, net041);

endmodule
