`timescale 1ns / 1ns
//////////////////////////////////////////////////////////////////////////////////
// Company: 
// Engineer: 
// 
// Create Date:    17:06:15 04/15/2011 
// Design Name: 
// Module Name:    t_tlc5940_wrap_slow 
// Project Name: 
// Target Devices: 
// Tool versions: 
// Description: 
//
// Dependencies: 
//
// Revision: 
// Revision 0.01 - File Created
// Additional Comments: 
//
//////////////////////////////////////////////////////////////////////////////////
module t_tlc5940_wrap_slow(

    );

	reg clk;
	reg rst_n;
	wire [193:2] data_register0, data_register1, data_register2, data_register3;
	wire [3:0] sin;
	wire xlat, blank, dcprg, vprg, gsclk, sclk;
	wire [3:0] state;
	wire [15:0] gs_count;
	wire [7:0] s_count;

	tlc5940_wrap_slow UUT(
	clk,
	rst_n,
	data_register0,
	data_register1,
	data_register2,
	data_register3,
	sin,				//	Serial	input	to	the	TLC5940
	xlat,						//	Latch	trigger	(level	sensitive	-	active	high)
	blank,						//	Output	enable	(active	low)/GS	count	reset	(active	high)
	dcprg,							//	H	for	DC	Register,	L	for	EEPROM
	vprg,							//	H	for	Dot	correction,	L	for	Grayscale
	gsclk,						//	Grayscale	clock	(MAX	30	MHz)
	sclk,						//	System	clock	(MAX	30	MHz)
	state,
	gs_count,
	s_count
	);

	initial begin
		clk = 0;
		forever clk = #5 ~clk;
	end

	assign data_register0 = 0;
	assign data_register1 = 0;
	assign data_register2 = 0;
	assign data_register3 = 0;

	initial begin
		rst_n = 1'b0;
		#25 rst_n = 1'b1;
	end

endmodule
