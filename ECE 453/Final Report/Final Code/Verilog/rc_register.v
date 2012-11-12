`timescale 1ns / 1ps
//////////////////////////////////////////////////////////////////////////////////
// Company: 
// Engineer: 
// 
// Create Date:    12:21:23 03/14/2011 
// Design Name: 
// Module Name:    reg_file 
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
module rc_register(
	input fpga_clk,
	input rst_n,
	inout [31:0] arm_data,
	inout [19:0] rc_port,
	input [19:0] rs_vector
	);
	
	// Memory Registers
	integer i;
	reg [15:0] rc_value [19:0];
	wire [15:0] rc_value_timer [19:0];

	// Data bus
	reg drive_arm_d;
	reg [31:0] arm_d;
	assign arm_data = drive_arm_d ? arm_d : 32'bz;

	genvar j;
	generate
		for (j=0; j<20; j = j + 1) begin: floop
			rc_timer RC_TIMER(
							.clk(fpga_clk),
							.rst_n(rst_n),
							.rc_pin_inout(rc_port[j]),
							.value(rc_value_timer[j])
							);
		end
	endgenerate

	// Memory Region/Register File
	always @(posedge fpga_clk, negedge rst_n) begin
		drive_arm_d <= 1'b0; // Default
		
		if (~rst_n) begin
			for (i=0; i<20; i = i + 1) begin
				rc_value[i] <= 16'b0;
			end
			drive_arm_d <= 1'b0;
		end
		else begin
			// RS and WS must be mutually exclusive
			// ############## READ STROBES ##############
			if (~rs_vector[0]) begin
				drive_arm_d <= 1'b1;
				arm_d <= {16'b0, rc_value[0]};
			end
			else begin
				rc_value[0] <= rc_value_timer[0];
			end
			if (~rs_vector[1]) begin
				drive_arm_d <= 1'b1;
				arm_d <= {16'b0, rc_value[1]};
			end
			else begin
				rc_value[1] <= rc_value_timer[1];
			end
			if (~rs_vector[2]) begin
				drive_arm_d <= 1'b1;
				arm_d <= {16'b0, rc_value[2]};
			end
			else begin
				rc_value[2] <= rc_value_timer[2];
			end
			if (~rs_vector[3]) begin
				drive_arm_d <= 1'b1;
				arm_d <= {16'b0, rc_value[3]};
			end
			else begin
				rc_value[3] <= rc_value_timer[3];
			end
			if (~rs_vector[4]) begin
				drive_arm_d <= 1'b1;
				arm_d <= {16'b0, rc_value[4]};
			end
			else begin
				rc_value[4] <= rc_value_timer[4];
			end
			if (~rs_vector[5]) begin
				drive_arm_d <= 1'b1;
				arm_d <= {16'b0, rc_value[5]};
			end
			else begin
				rc_value[5] <= rc_value_timer[5];
			end
			if (~rs_vector[6]) begin
				drive_arm_d <= 1'b1;
				arm_d <= {16'b0, rc_value[6]};
			end
			else begin
				rc_value[6] <= rc_value_timer[6];
			end
			if (~rs_vector[7]) begin
				drive_arm_d <= 1'b1;
				arm_d <= {16'b0, rc_value[7]};
			end
			else begin
				rc_value[7] <= rc_value_timer[7];
			end
			if (~rs_vector[8]) begin
				drive_arm_d <= 1'b1;
				arm_d <= {16'b0, rc_value[8]};
			end
			else begin
				rc_value[8] <= rc_value_timer[8];
			end
			if (~rs_vector[9]) begin
				drive_arm_d <= 1'b1;
				arm_d <= {16'b0, rc_value[9]};
			end
			else begin
				rc_value[9] <= rc_value_timer[9];
			end
			if (~rs_vector[10]) begin
				drive_arm_d <= 1'b1;
				arm_d <= {16'b0, rc_value[10]};
			end
			else begin
				rc_value[10] <= rc_value_timer[10];
			end
			if (~rs_vector[11]) begin
				drive_arm_d <= 1'b1;
				arm_d <= {16'b0, rc_value[11]};
			end
			else begin
				rc_value[11] <= rc_value_timer[11];
			end
			if (~rs_vector[12]) begin
				drive_arm_d <= 1'b1;
				arm_d <= {16'b0, rc_value[12]};
			end
			else begin
				rc_value[12] <= rc_value_timer[12];
			end
			if (~rs_vector[13]) begin
				drive_arm_d <= 1'b1;
				arm_d <= {16'b0, rc_value[13]};
			end
			else begin
				rc_value[13] <= rc_value_timer[13];
			end
			if (~rs_vector[14]) begin
				drive_arm_d <= 1'b1;
				arm_d <= {16'b0, rc_value[14]};
			end
			else begin
				rc_value[14] <= rc_value_timer[14];
			end
			if (~rs_vector[15]) begin
				drive_arm_d <= 1'b1;
				arm_d <= {16'b0, rc_value[15]};
			end
			else begin
				rc_value[15] <= rc_value_timer[15];
			end
			if (~rs_vector[16]) begin
				drive_arm_d <= 1'b1;
				arm_d <= {16'b0, rc_value[16]};
			end
			else begin
				rc_value[16] <= rc_value_timer[16];
			end
			if (~rs_vector[17]) begin
				drive_arm_d <= 1'b1;
				arm_d <= {16'b0, rc_value[17]};
			end
			else begin
				rc_value[17] <= rc_value_timer[17];
			end
			if (~rs_vector[18]) begin
				drive_arm_d <= 1'b1;
				arm_d <= {16'b0, rc_value[18]};
			end
			else begin
				rc_value[18] <= rc_value_timer[18];
			end
			if (~rs_vector[19]) begin
				drive_arm_d <= 1'b1;
				arm_d <= {16'b0, rc_value[19]};
			end
			else begin
				rc_value[19] <= rc_value_timer[19];
			end
		end
	end

endmodule
