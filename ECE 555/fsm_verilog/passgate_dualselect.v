// Library - ece555_projectlib, Cell - passgate_dualselect, View -
//schematic
// LAST TIME SAVED: Nov 20 11:50:37 2010
// NETLIST TIME: Nov 21 20:38:45 2010
`timescale 1ns / 1ns 

module passgate_dualselect ( a, b, in, rd_enA, rd_enA_BAR, rd_enB,
     rd_enB_BAR );
output  a, b;

input  in, rd_enA, rd_enA_BAR, rd_enB, rd_enB_BAR;


specify 
    specparam CDS_LIBNAME  = "ece555_projectlib";
    specparam CDS_CELLNAME = "passgate_dualselect";
    specparam CDS_VIEWNAME = "schematic";
endspecify

nmos N4 ( b, net20, rd_enB);
nmos N0 ( net12, 0, in);
nmos N1 ( net16, 0, net12);
nmos N2 ( net20, 0, net12);
nmos N3 ( a, net16, rd_enA);
pmos P0 ( net12, 1, in);
pmos P1 ( net20, 1, net12);
pmos P2 ( net16, 1, net12);
pmos P4 ( b, net20, rd_enB_BAR);
pmos P3 ( a, net16, rd_enA_BAR);

endmodule
