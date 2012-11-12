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
module tlc5940_register(
	input fpga_clk,
	input rst_n,
	inout [31:0] arm_data,
	input [19:0] ws_vector,
	input [19:0] rs_vector,
	output [3:0] tlc5940_sin,
	output tlc5940_xlat,
	output tlc5940_blank,
	output tlc5940_dcprg,
	output tlc5940_vprg,
	output tlc5940_gsclk,
	output tlc5940_sclk
	);

	// RGB LED 0 - Any Chip
	// All the same base is offset of 12 start at 6
	// For Blue and Green shifted offsets are 11 12 13 start at 5
	/*localparam CH0_LED0 = 5;//5					// Blue
	localparam CH0_LED1 = CH0_LED0 + 12;//12	// Green
	localparam CH0_LED2 = CH0_LED1 + 13;//13	// Red
	
	localparam CH1_LED0 = CH0_LED2 + 11;//11	// Blue
	localparam CH1_LED1 = CH1_LED0 + 12;//12	// Green
	localparam CH1_LED2 = CH1_LED1 + 13;//13	// Red
	
	localparam CH2_LED0 = CH1_LED2 + 11;//11	// Blue
	localparam CH2_LED1 = CH2_LED0 + 12;//12	// Green
	localparam CH2_LED2 = CH2_LED1 + 13;//13	// Red
	
	localparam CH3_LED0 = CH2_LED2 + 11;//11	// Blue
	localparam CH3_LED1 = CH3_LED0 + 12;//12	// Green
	localparam CH3_LED2 = CH3_LED1 + 13;//13	// Red
	
	localparam CH4_LED0 = CH3_LED2 + 11;//11	// Blue
	localparam CH4_LED1 = CH4_LED0 + 12;//12	// Green
	localparam CH4_LED2 = CH4_LED1 + 13;//13	// Red*/
	
	localparam CH0_LED2= 6;//5					// Red
	localparam CH0_LED1 = CH0_LED2 + 12;//12	// Green
	localparam CH0_LED0 = CH0_LED1 + 12;//13	// Blue
	
	localparam CH1_LED2 = CH0_LED0 + 12;//11	// Red
	localparam CH1_LED1 = CH1_LED2 + 12;//12	// Green
	localparam CH1_LED0 = CH1_LED1 + 12;//13	// Blue
	
	localparam CH2_LED2 = CH1_LED0 + 12;//11	// Red
	localparam CH2_LED1 = CH2_LED2 + 12;//12	// Green
	localparam CH2_LED0 = CH2_LED1 + 12;//13	// Blue
	
	localparam CH3_LED2 = CH2_LED0 + 12;//11	// Red
	localparam CH3_LED1 = CH3_LED2 + 12;//12	// Green
	localparam CH3_LED0 = CH3_LED1 + 12;//13	// Blue
	
	localparam CH4_LED2 = CH3_LED0 + 12;//11	// Red
	localparam CH4_LED1 = CH4_LED2 + 12;//12	// Green
	localparam CH4_LED0 = CH4_LED1 + 12;//13	// Blue
	
	// LED Registers
	reg [193:2] data_reg0;
	reg [193:2] data_reg1;
	reg [193:2] data_reg2;
	reg [193:2] data_reg3;
	
	// TLC5940 wrapper instantiations
	tlc5940_wrap_slow_pwm_2of5 TLC5940_WRAPPERS (
						.clk(fpga_clk),
						.rst_n(rst_n),
						.sin(tlc5940_sin),
						.xlat(tlc5940_xlat),
						.blank(tlc5940_blank),
						.dcprg(tlc5940_dcprg),
						.vprg(tlc5940_vprg),
						.gsclk(tlc5940_gsclk),
						.sclk(tlc5940_sclk),
						.data_register0(data_reg0),
						.data_register1(data_reg1),
						.data_register2(data_reg2),
						.data_register3(data_reg3)
						);

	// Data bus
	reg drive_arm_d;
	reg [31:0] arm_d;
	assign arm_data = drive_arm_d ? arm_d : 32'bz;

	// Memory Region/Register File
	always @(posedge fpga_clk) begin
		drive_arm_d <= 1'b0; // Default
		
		if (~rst_n) begin
			drive_arm_d <= 1'b0;
			data_reg0 <= 192'b0;
			data_reg1 <= 192'b0;
			data_reg2 <= 192'b0;
			data_reg3 <= 192'b0;
		end
		else begin
			// RS and WS must be mutually exclusive
			// ############## READ STROBES ##############
			// ###            Data Reg 0              ###
			if (~rs_vector[0]) begin
				drive_arm_d <= 1'b1;
				arm_d <= {8'b0,
							data_reg0[CH0_LED2+7:CH0_LED2],
							data_reg0[CH0_LED1+7:CH0_LED1],
							data_reg0[CH0_LED0+7:CH0_LED0]};
				//arm_d <= 32'hDEAD;      
			end                          
			if (~rs_vector[1]) begin     
				drive_arm_d <= 1'b1;      
				arm_d <= {8'b0,           
							data_reg0[CH1_LED2+7:CH1_LED2],
							data_reg0[CH1_LED1+7:CH1_LED1],
							data_reg0[CH1_LED0+7:CH1_LED0]};
			end                          
			if (~rs_vector[2]) begin     
				drive_arm_d <= 1'b1;      
				arm_d <= {8'b0,           
							data_reg0[CH2_LED2+7:CH2_LED2],
							data_reg0[CH2_LED1+7:CH2_LED1],
							data_reg0[CH2_LED0+7:CH2_LED0]};
			end                          
			if (~rs_vector[3]) begin     
				drive_arm_d <= 1'b1;      
				arm_d <= {8'b0,           
							data_reg0[CH3_LED2+7:CH3_LED2],
							data_reg0[CH3_LED1+7:CH3_LED1],
							data_reg0[CH3_LED0+7:CH3_LED0]};
			end                          
			if (~rs_vector[4]) begin     
				drive_arm_d <= 1'b1;      
				arm_d <= {8'b0,           
							data_reg0[CH4_LED2+7:CH4_LED2],
							data_reg0[CH4_LED1+7:CH4_LED1],
							data_reg0[CH4_LED0+7:CH4_LED0]};
			end                          
			                             
			// ###            Data Reg 1              ###
			if (~rs_vector[5]) begin     
				drive_arm_d <= 1'b1;      
				arm_d <= {8'b0,           
							data_reg1[CH0_LED2+7:CH0_LED2],
							data_reg1[CH0_LED1+7:CH0_LED1],
							data_reg1[CH0_LED0+7:CH0_LED0]};
			end                          
			if (~rs_vector[6]) begin     
				drive_arm_d <= 1'b1;      
				arm_d <= {8'b0,           
							data_reg1[CH1_LED2+7:CH1_LED2],
							data_reg1[CH1_LED1+7:CH1_LED1],
							data_reg1[CH1_LED0+7:CH1_LED0]};
			end                          
			if (~rs_vector[7]) begin     
				drive_arm_d <= 1'b1;      
				arm_d <= {8'b0,           
							data_reg1[CH2_LED2+7:CH2_LED2],
							data_reg1[CH2_LED1+7:CH2_LED1],
							data_reg1[CH2_LED0+7:CH2_LED0]};
			end                          
			if (~rs_vector[8]) begin     
				drive_arm_d <= 1'b1;      
				arm_d <= {8'b0,           
							data_reg1[CH3_LED2+7:CH3_LED2],
							data_reg1[CH3_LED1+7:CH3_LED1],
							data_reg1[CH3_LED0+7:CH3_LED0]};
			end                          
			if (~rs_vector[9]) begin     
				drive_arm_d <= 1'b1;      
				arm_d <= {8'b0,           
							data_reg1[CH4_LED2+7:CH4_LED2],
							data_reg1[CH4_LED1+7:CH4_LED1],
							data_reg1[CH4_LED0+7:CH4_LED0]};
			end                          
			                             
			// ###            Data Reg 2              ###
			if (~rs_vector[10]) begin    
				drive_arm_d <= 1'b1;      
				arm_d <= {8'b0,           
							data_reg2[CH0_LED2+7:CH0_LED2],
							data_reg2[CH0_LED1+7:CH0_LED1],
							data_reg2[CH0_LED0+7:CH0_LED0]};
			end                          
			if (~rs_vector[11]) begin    
				drive_arm_d <= 1'b1;      
				arm_d <= {8'b0,           
							data_reg2[CH1_LED2+7:CH1_LED2],
							data_reg2[CH1_LED1+7:CH1_LED1],
							data_reg2[CH1_LED0+7:CH1_LED0]};
			end                          
			if (~rs_vector[12]) begin    
				drive_arm_d <= 1'b1;      
				arm_d <= {8'b0,           
							data_reg2[CH2_LED2+7:CH2_LED2],
							data_reg2[CH2_LED1+7:CH2_LED1],
							data_reg2[CH2_LED0+7:CH2_LED0]};
			end                          
			if (~rs_vector[13]) begin    
				drive_arm_d <= 1'b1;      
				arm_d <= {8'b0,           
							data_reg2[CH3_LED2+7:CH3_LED2],
							data_reg2[CH3_LED1+7:CH3_LED1],
							data_reg2[CH3_LED0+7:CH3_LED0]};
			end                          
			if (~rs_vector[14]) begin    
				drive_arm_d <= 1'b1;      
				arm_d <= {8'b0,           
							data_reg2[CH4_LED2+7:CH4_LED2],
							data_reg2[CH4_LED1+7:CH4_LED1],
							data_reg2[CH4_LED0+7:CH4_LED0]};
			end                          
			                             
			// ###            Data Reg 3              ###
			if (~rs_vector[15]) begin    
				drive_arm_d <= 1'b1;      
				arm_d <= {8'b0,           
							data_reg3[CH0_LED2+7:CH0_LED2],
							data_reg3[CH0_LED1+7:CH0_LED1],
							data_reg3[CH0_LED0+7:CH0_LED0]};
			end                          
			if (~rs_vector[16]) begin    
				drive_arm_d <= 1'b1;      
				arm_d <= {8'b0,           
							data_reg3[CH1_LED2+7:CH1_LED2],
							data_reg3[CH1_LED1+7:CH1_LED1],
							data_reg3[CH1_LED0+7:CH1_LED0]};
			end                          
			if (~rs_vector[17]) begin    
				drive_arm_d <= 1'b1;      
				arm_d <= {8'b0,           
							data_reg3[CH2_LED2+7:CH2_LED2],
							data_reg3[CH2_LED1+7:CH2_LED1],
							data_reg3[CH2_LED0+7:CH2_LED0]};
			end                          
			if (~rs_vector[18]) begin    
				drive_arm_d <= 1'b1;      
				arm_d <= {8'b0,           
							data_reg3[CH3_LED2+7:CH3_LED2],
							data_reg3[CH3_LED1+7:CH3_LED1],
							data_reg3[CH3_LED0+7:CH3_LED0]};
			end                          
			if (~rs_vector[19]) begin    
				drive_arm_d <= 1'b1;      
				arm_d <= {8'b0,           
							data_reg3[CH4_LED2+7:CH4_LED2],
							data_reg3[CH4_LED1+7:CH4_LED1],
							data_reg3[CH4_LED0+7:CH4_LED0]};
			end
			
			// ############## WRITE STROBES ##############
			// ###             Data Reg 0              ###
			if (~ws_vector[0]) begin				
				data_reg0[CH0_LED0+7:CH0_LED0] <= arm_data[7:0];
				data_reg0[CH0_LED1+7:CH0_LED1] <= arm_data[15:8];
				data_reg0[CH0_LED2+7:CH0_LED2] <= arm_data[23:16];
				/*data_reg0[CH0_LED0+7:CH0_LED0] <= 8'hEF;
				data_reg0[CH0_LED1+7:CH0_LED1] <= 8'hCD;
				data_reg0[CH0_LED2+7:CH0_LED2] <= 8'hAB;*/
			end                  
			if (~ws_vector[1]) begin			
				data_reg0[CH1_LED0+7:CH1_LED0] <= arm_data[7:0];
				data_reg0[CH1_LED1+7:CH1_LED1] <= arm_data[15:8];
				data_reg0[CH1_LED2+7:CH1_LED2] <= arm_data[23:16];
			end                  
			if (~ws_vector[2]) begin			
				data_reg0[CH2_LED0+7:CH2_LED0] <= arm_data[7:0];
				data_reg0[CH2_LED1+7:CH2_LED1] <= arm_data[15:8];
				data_reg0[CH2_LED2+7:CH2_LED2] <= arm_data[23:16];
			end                  
			if (~ws_vector[3]) begin			
				data_reg0[CH3_LED0+7:CH3_LED0] <= arm_data[7:0];
				data_reg0[CH3_LED1+7:CH3_LED1] <= arm_data[15:8];
				data_reg0[CH3_LED2+7:CH3_LED2] <= arm_data[23:16];
			end                  
			if (~ws_vector[4]) begin			
				data_reg0[CH4_LED0+7:CH4_LED0] <= arm_data[7:0];
				data_reg0[CH4_LED1+7:CH4_LED1] <= arm_data[15:8];
				data_reg0[CH4_LED2+7:CH4_LED2] <= arm_data[23:16];
			end
			
			// ###             Data Reg 1              ###
			if (~ws_vector[5]) begin				
				data_reg1[CH0_LED0+7:CH0_LED0] <= arm_data[7:0];
				data_reg1[CH0_LED1+7:CH0_LED1] <= arm_data[15:8];
				data_reg1[CH0_LED2+7:CH0_LED2] <= arm_data[23:16];
			end                  
			if (~ws_vector[6]) begin			
				data_reg1[CH1_LED0+7:CH1_LED0] <= arm_data[7:0];
				data_reg1[CH1_LED1+7:CH1_LED1] <= arm_data[15:8];
				data_reg1[CH1_LED2+7:CH1_LED2] <= arm_data[23:16];
			end                  
			if (~ws_vector[7]) begin			
				data_reg1[CH2_LED0+7:CH2_LED0] <= arm_data[7:0];
				data_reg1[CH2_LED1+7:CH2_LED1] <= arm_data[15:8];
				data_reg1[CH2_LED2+7:CH2_LED2] <= arm_data[23:16];
			end                  
			if (~ws_vector[8]) begin			
				data_reg1[CH3_LED0+7:CH3_LED0] <= arm_data[7:0];
				data_reg1[CH3_LED1+7:CH3_LED1] <= arm_data[15:8];
				data_reg1[CH3_LED2+7:CH3_LED2] <= arm_data[23:16];
			end                  
			if (~ws_vector[9]) begin			
				data_reg1[CH4_LED0+7:CH4_LED0] <= arm_data[7:0];
				data_reg1[CH4_LED1+7:CH4_LED1] <= arm_data[15:8];
				data_reg1[CH4_LED2+7:CH4_LED2] <= arm_data[23:16];
			end
			
			// ###             Data Reg 2              ###
			if (~ws_vector[10]) begin				
				data_reg2[CH0_LED0+7:CH0_LED0] <= arm_data[7:0];
				data_reg2[CH0_LED1+7:CH0_LED1] <= arm_data[15:8];
				data_reg2[CH0_LED2+7:CH0_LED2] <= arm_data[23:16];
			end                  
			if (~ws_vector[11]) begin			
				data_reg2[CH1_LED0+7:CH1_LED0] <= arm_data[7:0];
				data_reg2[CH1_LED1+7:CH1_LED1] <= arm_data[15:8];
				data_reg2[CH1_LED2+7:CH1_LED2] <= arm_data[23:16];
			end                  
			if (~ws_vector[12]) begin			
				data_reg2[CH2_LED0+7:CH2_LED0] <= arm_data[7:0];
				data_reg2[CH2_LED1+7:CH2_LED1] <= arm_data[15:8];
				data_reg2[CH2_LED2+7:CH2_LED2] <= arm_data[23:16];
			end                  
			if (~ws_vector[13]) begin			
				data_reg2[CH3_LED0+7:CH3_LED0] <= arm_data[7:0];
				data_reg2[CH3_LED1+7:CH3_LED1] <= arm_data[15:8];
				data_reg2[CH3_LED2+7:CH3_LED2] <= arm_data[23:16];
			end                  
			if (~ws_vector[14]) begin			
				data_reg2[CH4_LED0+7:CH4_LED0] <= arm_data[7:0];
				data_reg2[CH4_LED1+7:CH4_LED1] <= arm_data[15:8];
				data_reg2[CH4_LED2+7:CH4_LED2] <= arm_data[23:16];
			end
			
			// ###             Data Reg 3              ###
			if (~ws_vector[15]) begin				
				data_reg3[CH0_LED0+7:CH0_LED0] <= arm_data[7:0];
				data_reg3[CH0_LED1+7:CH0_LED1] <= arm_data[15:8];
				data_reg3[CH0_LED2+7:CH0_LED2] <= arm_data[23:16];
			end                  
			if (~ws_vector[16]) begin			
				data_reg3[CH1_LED0+7:CH1_LED0] <= arm_data[7:0];
				data_reg3[CH1_LED1+7:CH1_LED1] <= arm_data[15:8];
				data_reg3[CH1_LED2+7:CH1_LED2] <= arm_data[23:16];
			end                  
			if (~ws_vector[17]) begin			
				data_reg3[CH2_LED0+7:CH2_LED0] <= arm_data[7:0];
				data_reg3[CH2_LED1+7:CH2_LED1] <= arm_data[15:8];
				data_reg3[CH2_LED2+7:CH2_LED2] <= arm_data[23:16];
			end                  
			if (~ws_vector[18]) begin			
				data_reg3[CH3_LED0+7:CH3_LED0] <= arm_data[7:0];
				data_reg3[CH3_LED1+7:CH3_LED1] <= arm_data[15:8];
				data_reg3[CH3_LED2+7:CH3_LED2] <= arm_data[23:16];
			end                  
			if (~ws_vector[19]) begin			
				data_reg3[CH4_LED0+7:CH4_LED0] <= arm_data[7:0];
				data_reg3[CH4_LED1+7:CH4_LED1] <= arm_data[15:8];
				data_reg3[CH4_LED2+7:CH4_LED2] <= arm_data[23:16];
			end
		end
	end

endmodule
