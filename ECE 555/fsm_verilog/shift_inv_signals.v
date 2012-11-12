// Library - ece555_projectlib, Cell - shift_inv_signals, View -
//schematic
// LAST TIME SAVED: Dec  1 19:26:09 2010
// NETLIST TIME: Dec  8 16:55:26 2010
`timescale 1ns / 1ns 

module shift_inv_signals ( OUT, IN );
output  OUT;

input  IN;


specify 
    specparam CDS_LIBNAME  = "ece555_projectlib";
    specparam CDS_CELLNAME = "shift_inv_signals";
    specparam CDS_VIEWNAME = "schematic";
endspecify

nmos N137 ( OUT, 0, IN);
pmos P99 ( OUT, 1, IN);

endmodule
