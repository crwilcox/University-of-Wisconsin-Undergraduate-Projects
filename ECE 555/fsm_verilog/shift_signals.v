// Library - ece555_projectlib, Cell - shift_signals, View - schematic
// LAST TIME SAVED: Dec  3 12:23:49 2010
// NETLIST TIME: Dec  8 16:55:26 2010
`timescale 1ns / 1ns 

module shift_signals ( left, noshift, right, A, left_right,
     left_right_n );
output  left, noshift, right;

input  A, left_right, left_right_n;


specify 
    specparam CDS_LIBNAME  = "ece555_projectlib";
    specparam CDS_CELLNAME = "shift_signals";
    specparam CDS_VIEWNAME = "schematic";
endspecify

pmos P2 ( right, net055, left_right_n);
pmos P1 ( net055, 1, noshift);
pmos P0 ( left, net064, left_right);
pmos P14 ( net064, 1, noshift);
pmos P11 ( noshift, 1, A);
nmos N1 ( right, 0, left_right_n);
nmos N18 ( left, 0, noshift);
nmos N17 ( left, 0, left_right);
nmos N0 ( right, 0, noshift);
nmos N15 ( noshift, 0, A);

endmodule
