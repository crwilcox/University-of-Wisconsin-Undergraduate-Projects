// Library - ece555_projectlib, Cell - ppgen_1bit, View - schematic
// LAST TIME SAVED: Nov 21 22:34:30 2010
// NETLIST TIME: Nov 21 22:35:27 2010
`timescale 1ns / 1ns 

module ppgen_1bit ( Z, A, B, ppgen_en, ppgen_en_BAR );

output  Z;

input  A, B, ppgen_en, ppgen_en_BAR;


specify 
    specparam CDS_LIBNAME  = "ece555_projectlib";
    specparam CDS_CELLNAME = "ppgen_1bit";
    specparam CDS_VIEWNAME = "schematic";
endspecify

pmos P3 ( Z, net9, ppgen_en_BAR);
pmos P2 ( net9, 1, net14);
pmos P1 ( net14, 1, B);
pmos P0 ( net14, 1, A);
nmos N3 ( Z, net9, ppgen_en);
nmos N2 ( net9, 0, net14);
nmos N1 ( net37, 0, B);
nmos N0 ( net14, net37, A);

endmodule
