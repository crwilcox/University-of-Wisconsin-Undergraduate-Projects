// Library - ece555_projectlib, Cell - count16, View - schematic
// LAST TIME SAVED: Nov 22 21:19:43 2010
// NETLIST TIME: Nov 22 21:20:17 2010
`timescale 1ns / 1ns 

module count16 ( Q0, Q1, Q2, Q3, clk, count16_inc, count16_init );
output  Q0, Q1, Q2, Q3;

input  clk, count16_inc, count16_init;


specify 
    specparam CDS_LIBNAME  = "ece555_projectlib";
    specparam CDS_CELLNAME = "count16";
    specparam CDS_VIEWNAME = "schematic";
endspecify

dff I3 ( Q3, D3, clk);
dff I2 ( Q2, D2, clk);
dff I1 ( Q1, D1, clk);
dff I0 ( Q0, D0, clk);
nmos N110 ( D3, 0, notS3);
nmos N109 ( D3, 0, count16_init);
nmos N108 ( D2, 0, count16_init);
nmos N107 ( D2, 0, notS2);
nmos N106 ( D1, 0, notS1);
nmos N105 ( D1, 0, count16_init);
nmos N100 ( D0, 0, count16_init);
nmos N99 ( D0, 0, notS0);
pmos P110 ( net0223, 1, count16_init);
pmos P109 ( D3, net0223, notS3);
pmos P108 ( D2, net0227, notS2);
pmos P107 ( net0227, 1, count16_init);
pmos P106 ( net0239, 1, count16_init);
pmos P105 ( D1, net0239, notS1);
pmos P99 ( D0, net087, notS0);
pmos P100 ( net087, 1, count16_init);
FA_SUMBAR I16 ( notS3, net0147, Q3, 0, net0220);
FA_SUMBAR I15 ( notS2, net0220, Q2, 0, net0215);
FA_SUMBAR I14 ( notS1, net0215, Q1, 0, net0225);
FA_SUMBAR I13 ( notS0, net0225, Q0, 0, count16_inc);
endmodule
