`timescale 1ns / 1ps
//////////////////////////////////////////////////////////////////////////////////
// Company: 
// Engineer: 
// 
// Create Date:    18:53:50 03/09/2011 
// Design Name: 
// Module Name:    BIU 
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
module BIU(
	input fpga_clk, // Fast clock ~100 MHz
	input rst_n,
	input cpld_rs,
	input cpld_ws,
	input [23:0] arm_a,
	inout [31:0] arm_data,
	output [3:0] tlc5940_sin,
	output tlc5940_xlat,
	output tlc5940_blank,
	output tlc5940_dcprg,
	output tlc5940_vprg,
	output tlc5940_gsclk,
	output tlc5940_sclk,
	inout [19:0] rc_port
	);
	 
	 parameter BASE_ADDR = 24'h000000;
	
	// TLC5940 Wrappers and Photoresistors
	reg [19:0] ws_vector; // Active-low // 20 LEDs = 20 words of memory
	reg [39:0] rs_vector; // Active-low // 20 Photoresistors // 20 LEDs
	//reg [3:0] be_vector; // Active-low
	
	// 4 TLC5940 chips - 1 module
	tlc5940_register TLC5940_REG (
						.fpga_clk(fpga_clk),
						.rst_n(rst_n),
						.arm_data(arm_data),
						.ws_vector(ws_vector),
						.rs_vector(rs_vector[19:0]),
						.tlc5940_sin(tlc5940_sin), // 4 pins
						.tlc5940_xlat(tlc5940_xlat),
						.tlc5940_blank(tlc5940_blank),
						.tlc5940_dcprg(tlc5940_dcprg),
						.tlc5940_vprg(tlc5940_vprg),
						.tlc5940_gsclk(tlc5940_gsclk),
						.tlc5940_sclk(tlc5940_sclk)
						);
						
	rc_register RC_REG (
						.fpga_clk(fpga_clk),
						.rst_n(rst_n),
						.arm_data(arm_data),
						.rs_vector(rs_vector[39:20]),
						.rc_port(rc_port)
						);
	
	// Read Strobes
	reg cpld_rs_lvl1, cpld_rs_lvl2;
	always @(posedge fpga_clk, negedge rst_n) begin
		cpld_rs_lvl1 <= cpld_rs;
		cpld_rs_lvl2 <= cpld_rs_lvl1;
		rs_vector <= 40'hFFFFFFFFFF; // Default
		
		if (~rst_n) begin
			cpld_rs_lvl1 <= 1'b1;
			cpld_rs_lvl2 <= 1'b1;
			rs_vector <= 40'hFFFFFFFFFF;
		end
		else if (cpld_rs_lvl2 == 1'b0 && cpld_rs_lvl1 == 1'b0) begin
			// RS is enabled and no longer metastable
			// There should be no need to clock address or data bus
			// Match address to read vector
			if (arm_a >= BASE_ADDR && arm_a < (BASE_ADDR + 4*40)) begin
				rs_vector[(arm_a - BASE_ADDR) >> 2] <= 1'b0;
			end
			/*if (arm_a == 24'b000000) begin
				rs_vector[0] <= 1'b0;
			end*/
			// First 20 are LEDs
			// Last 20 are photoresistors
		end
	end
	
	// Write Strobes
	reg cpld_ws_lvl1, cpld_ws_lvl2;
	reg prev_ws_enable;
	wire ws_enable;
	assign ws_enable = (~cpld_ws_lvl2 & ~cpld_ws_lvl1);
	always @(posedge fpga_clk) begin
		cpld_ws_lvl1 <= cpld_ws;
		cpld_ws_lvl2 <= cpld_ws_lvl1;
		
		ws_vector <= 20'hFFFFF; // Default
		//be_vector <= 4'hF; // Default
		prev_ws_enable <= ws_enable;
		
		if (ws_enable & ~prev_ws_enable) begin
			// WS is enabled and no longer metastable
			// There should be no need to clock address or data bus
			if (arm_a >= BASE_ADDR && arm_a < (BASE_ADDR + 4*20)) begin
				ws_vector[(arm_a - BASE_ADDR) >> 2] <= 1'b0;
			end
			/*if (arm_a == 24'b000000) begin
				ws_vector[0] <= 1'b0;
			end*/
		end
	end
	
endmodule
