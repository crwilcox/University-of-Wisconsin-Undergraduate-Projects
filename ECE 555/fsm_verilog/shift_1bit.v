// Library - ece555_projectlib, Cell - shift_1bit, View - schematic
// LAST TIME SAVED: Nov 30 15:38:12 2010
// NETLIST TIME: Dec  8 16:55:26 2010
`timescale 1ns / 1ns 

module shift_1bit ( B, A, LEFT, NOSHIFT, RIGHT, lower, upper );
output  B;

input  A, LEFT, NOSHIFT, RIGHT, lower, upper;


specify 
    specparam CDS_LIBNAME  = "ece555_projectlib";
    specparam CDS_CELLNAME = "shift_1bit";
    specparam CDS_VIEWNAME = "schematic";
endspecify

nmos N3 ( B, lower, LEFT);
nmos N136 ( B, A, NOSHIFT);
nmos N134 ( B, upper, RIGHT);

endmodule
