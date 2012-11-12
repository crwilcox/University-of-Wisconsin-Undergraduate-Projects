// Library - ece555_projectlib, Cell - FA_SUMBAR, View - schematic
// LAST TIME SAVED: Nov 21 23:00:28 2010
// NETLIST TIME: Nov 21 23:02:19 2010
`timescale 1ns / 1ns 

module FA_SUMBAR ( SUM_BAR, cout, a, b, cin );
output  SUM_BAR, cout;

input  a, b, cin;


specify 
    specparam CDS_LIBNAME  = "ece555_projectlib";
    specparam CDS_CELLNAME = "FA_SUMBAR";
    specparam CDS_VIEWNAME = "schematic";
endspecify

pmos P12 ( cout, 1, net14);
pmos P11 ( SUM_BAR, net13, net14);
pmos P10 ( SUM_BAR, net0139, cin);
pmos P9 ( net0139, net0143, b);
pmos P8 ( net0143, 1, a);
pmos P7 ( net13, 1, cin);
pmos P6 ( net13, 1, b);
pmos P5 ( net13, 1, a);
pmos P4 ( net14, net0163, a);
pmos P3 ( net14, net56, cin);
pmos P2 ( net0163, 1, b);
pmos P1 ( net56, 1, b);
pmos P0 ( net56, 1, a);
nmos N13 ( cout, 0, net14);
nmos N11 ( net72, 0, b);
nmos N10 ( net76, net72, a);
nmos N9 ( SUM_BAR, net76, cin);
nmos N8 ( net92, 0, cin);
nmos N7 ( net92, 0, b);
nmos N6 ( net92, 0, a);
nmos N5 ( SUM_BAR, net92, net14);
nmos N4 ( net111, 0, b);
nmos N14 ( net104, 0, b);
nmos N2 ( net104, 0, a);
nmos N1 ( net14, net111, a);
nmos N0 ( net14, net104, cin);

endmodule
