// Library - ece555_projectlib, Cell - shift_pass, View - schematic
// LAST TIME SAVED: Dec  7 17:25:12 2010
// NETLIST TIME: Dec  8 16:55:26 2010
`timescale 1ns / 1ns 

module shift_pass ( OUT, IN, shift_en, shift_en_n );
output  OUT;

input  IN, shift_en, shift_en_n;


specify 
    specparam CDS_LIBNAME  = "ece555_projectlib";
    specparam CDS_CELLNAME = "shift_pass";
    specparam CDS_VIEWNAME = "schematic";
endspecify

nmos N102 ( OUT, IN, shift_en);
pmos P67 ( OUT, IN, shift_en_n);

endmodule
