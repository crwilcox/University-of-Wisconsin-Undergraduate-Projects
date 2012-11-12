`timescale 1ns / 1ps
//////////////////////////////////////////////////////////////////////////////////
// Company: 
// Engineer: 
// 
// Create Date:    13:56:44 03/14/2011 
// Design Name: 
// Module Name:    rc_timer 
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
module rc_timer(
    input clk,
    input rst_n,
	 inout rc_pin_inout,
    output reg [15:0] value
    );

	parameter STATE_PULL_GND = 2'd0; // Pull cap to ground
	parameter STATE_WAIT_HIGH = 2'd1; // Read the input until it goes high
	parameter STATE_FOUND_ONE = 2'd2;
	
	parameter BAD_VALUE = 16'hFFFF;
	parameter BIT_WIDTH = 16;
	parameter GND_TIME = 500000;
	
	reg rc_pin;
	reg next_rc_pin;
	reg rc_assert;
	reg next_rc_assert;
	assign rc_pin_inout = rc_assert ? rc_pin : 1'bz;
	
	reg [1:0] rc_state;
	reg [1:0] next_state;
	reg [BIT_WIDTH-1:0] count, next_count;
	reg [BIT_WIDTH-1:0] next_value;
	reg [31:0] gnd_count, next_gnd_count;
	
	// Assign state
	always @(posedge clk, negedge rst_n) begin
		if (!rst_n) begin
			rc_state <= STATE_PULL_GND;
			rc_assert <= 1'b1;
			rc_pin <= 1'b0;
			count <= 0;
			value <= BAD_VALUE;
			gnd_count <= 0;
		end
		else begin
			rc_state <= next_state;
			rc_assert <= next_rc_assert;
			rc_pin <= next_rc_pin;
			count <= next_count;
			value <= next_value;
			gnd_count <= next_gnd_count;
		end
	end
	
	reg clocked_pin;
	always @(posedge clk) begin
		clocked_pin <= rc_pin_inout;
	end
	
	// Next state logic
	always @(*) begin
		case (rc_state)
			STATE_PULL_GND: begin
				next_rc_pin = 1'b0; // Pull cap to ground
				next_value = value;
				
				if (gnd_count > GND_TIME) begin
					next_state = STATE_WAIT_HIGH;
					next_rc_assert = 1'b0;
					next_count = 0;
					next_gnd_count = 0;
				end
				else begin
					next_rc_assert = 1'b1; // We have control of the pin
					next_state = STATE_PULL_GND;
					next_count = count;
					next_gnd_count = gnd_count + 1;
				end
			end
			
			STATE_WAIT_HIGH: begin
				next_rc_assert = 1'b0;
				next_rc_pin = 1'b0;
				
				//if (rc_pin_inout == 1'b1) begin
				if (clocked_pin == 1'b1) begin
					next_count = count;
					next_gnd_count = 0;
					//next_value = count;
					next_state = STATE_FOUND_ONE;
					next_value = value;
				end
				else begin
					next_gnd_count = gnd_count;
					if (count > (BAD_VALUE - 16'd500)) begin
						next_count = BAD_VALUE; // Legal verilog?
						next_value = BAD_VALUE;
						next_state = STATE_PULL_GND;
					end
					else begin
						next_count = count + 1;
						next_value = value;
						next_state = STATE_WAIT_HIGH;
					end
				end
			end
			
			STATE_FOUND_ONE: begin
				next_rc_assert = 1'b0;
				next_rc_pin = 1'b0;
				//next_value = value;
				next_count = count;
				next_state = STATE_FOUND_ONE;
				
				//if (rc_pin_inout == 1'b1) begin
				if (clocked_pin == 1'b1) begin
					if (gnd_count > GND_TIME) begin
						next_state = STATE_PULL_GND;
						next_value = count;
						next_gnd_count = 0;
					end
					else begin
						next_state = STATE_FOUND_ONE;
						next_value = value;
						next_gnd_count = gnd_count + 1;
					end
				end
				else begin
					// Wait for bouncing
					next_state = STATE_FOUND_ONE;
					next_value = value;
					//next_state = STATE_WAIT_HIGH;
					next_gnd_count = 0;
				end
			end
			
			default: begin
				next_rc_pin = 1'b0;
				next_rc_assert = 1'b1;
				next_state = STATE_PULL_GND;
				next_gnd_count = 0;
				next_count = 0;
				next_value = 16'hDEAD;
			end
			
		endcase
	end

endmodule
