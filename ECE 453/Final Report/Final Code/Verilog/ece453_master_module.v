`timescale 1ns / 1ps
//////////////////////////////////////////////////////////////////////////////////
// Company: 		UW-Madison, ECE 453
// Engineer: 		M.G.Morrow
// 
// Create Date:    	09:19:05 03/27/2008 
// Design Name: 	ECE453 TLL5000 master project
// Module Name:    	ece453_master_module 
// Description: 	Second-level module for use with TLL5000 board
//					All student code to be in this module or below
//					Do NOT alter the ports names or directions
//
// Revision: 
// Revision 0.01 - File Created
//
//////////////////////////////////////////////////////////////////////////////////
module ece453_master_module(
	// system reset and clocks
	input 	SYS_RST_N,	
	input 	SYS_CLK, FPGA_CLK_BANK_0, FPGA_CLK_BANK_1, FPGA_CLK_BANK_5, // 24MHz clocks
	input 	FPGA_CLK1, FPGA_CLK2, FPGA_CLK3, //100MHz clocks
	
	// RS-232 level translators
	input 	RS232_RX, RS232_RTS,		
	output	RS232_TX, RS232_CTS,
	// LEDs and switches
	output 	[7:0] LED,		
	input 	[7:0] DIP_SW,	
	input 	[4:0] PB,		
	// character LCD module
	output	LCD_BACK, LCD_E, LCD_RS, LCD_RW,
	output	[3:0] LCD_DATA,
	// PS2 mouse and keyboard
	output	PS2_ENABLED,
	inout	PS2_KEYB_CLK, PS2_KEYB_DATA, PS2_MOUSE_CLK, PS2_MOUSE_DATA,
	
	// AC97 codec
	input 	AC97_BIT_CLK, AC97_SDATA_IN,
	output	AC97_SDATA_OUT, AC97_SYNC, AC97_RESET_N, AC97_ID_0, AC97_ID_1,

	// VGA output
	output	[7:0] VGA_B, VGA_G, VGA_R,
	output	VGA_HSYNC, VGA_VSYNC, VGA_PIX_CLK, VGA_BLANK, VGA_SYNC, VGA_PSAVE,
	
	// SPI to SD card socket
	input	SPI_MISO,		// MMC_I04
	output	SPI_MOSI,		// MMC_I00
	output	SPI_SCK, 		// MMC_I05
	output	SPI_SS, 		// MMC_I02

	// flash memory
	output	[22:0] FLASH_ADDR,
	inout	[15:0] FLASH_DATA,
	output	FLASH_NBYTE, FLASH_NCE, FLASH_NOE_E, FLASH_NRESET, FLASH_NWE, FLASH_NWP,
	input	FLASH_NRYBY, 

	// video encoder
	output	[7:0] VE_DATA,
	output	VE_TTX, VE_SCRESET_RTC, VE_RESET, VE_PAL_NTSC,
	input	VE_CLK, VE_TTXREQ, VE_CLAMP, VE_VSO, VE_CSO_HSO,
	inout	VE_HSYNC, VE_FIELD_VSYNC, VE_BLANK,

	// i2c lines to mezzanines
	inout	I2C_SCL, I2C_SDA,

	// video decoder
	input	[7:0] VD_DATA_DEC,
	input	VD_VS_FIELD, VD_HS, VD_INTRQ, VD_SFL, VD_LLC,
	output	VD_NRESET, VD_NPWRDWN,
	
	// Ethernet mac (not included)
	// *********************************************************
	
	// TLL7D4820m prototyping card
	input	[47:0] PIO_IN,
	output	[47:0] PIO_OUT,
	output	PIO_DIR0, PIO_DIR8, PIO_DIR16, PIO_DIR24, PIO_DIR32, PIO_DIR40, 
	output	PIO_OE_B0, PIO_OE_B8, PIO_OE_B16, PIO_OE_B24, PIO_OE_B32, PIO_OE_B40, 
	inout	[67:48] PIO,

	// TLL6219 ARM926
    input	[23:0] ARM_A,
    inout	[31:0] ARM_D,
    input	[3:0] ARM_BE_B,
    output	ARM_DTACK, ARM_IRQ,
	input	ARM_CS0_B, ARM_CS1_B, ARM_CS2_B, ARM_CS3_B, ARM_CS5_B,
	input	ARM_OE_B, ARM_RW, ARM_RESET_OUT,
	input	ARM_CLK0, CPLD_AS, CPLD_NFIO4, CPLD_NFIO5, 
	input	CPLD_RS1_B, CPLD_WS1_B, CPLD_RS5_B, CPLD_WS5_B, 

	// accessory connector
	output [42:9] ACC_PORT_PIN
);
	assign ARM_DTACK = 1;
	
	// Direction
	assign PIO_DIR0 = 1'b1;
	assign PIO_DIR8 = 1'b1;
	assign PIO_DIR16 = 1'b0; // 1 => Outputs
	assign PIO_DIR24 = 1'b1;
	assign PIO_DIR32 = 1'b1;
	assign PIO_DIR40 = 1'b0;
	
	// Output Enable
	assign PIO_OE0 = 1'b0;
	assign PIO_OE8 = 1'b0;
	assign PIO_OE16 = 1'b1; // Active-low -> 0 = Enabled
	assign PIO_OE24 = 1'b0;
	assign PIO_OE32 = 1'b0;
	assign PIO_OE40 = 1'b1;
	// XXX: Need the OE signals?
	// XXX: Not used, auto-assigned to 0 (good since they are active low)
	
	wire B1_XERR0;
	wire B1_XERR1;
	wire B2_XERR0;
	wire B2_XERR1;
	wire B1_SOUT0;
	wire B1_SOUT1;
	wire B2_SOUT0;
	wire B2_SOUT1;
	
	assign B2_XERR1 = PIO_IN[16];
	assign B2_SOUT1 = PIO_IN[17];
	assign B2_XERR0 = PIO_IN[18];
	
	assign B1_SOUT0 = PIO_IN[40];
	assign B2_SOUT0 = PIO_IN[41];
	assign B1_XERR1 = PIO_IN[42];
	assign B1_SOUT1 = PIO_IN[43];
	assign B1_XERR0 = PIO_IN[44];
	
	wire VPRG, GSCLK, DCPRG, BLANK, XLAT, SCLK, B1_SIN_0;
	wire B1_SIN_1;
	
	wire B2_SIN_0;
	wire B2_SIN_1;
	
	/*  NOT USED
	//assign PIO_OUT[0]  = //NO CONNECTION
	//assign PIO_OUT[1]  = //NO CONNECTION
	assign PIO_OUT[2]  = GSCLK;
	assign PIO_OUT[3]  = DCPRG;
	assign PIO_OUT[4]  = BLANK;
	assign PIO_OUT[5]  = XLAT;
	assign PIO_OUT[6]  = SCLK;
	assign PIO_OUT[7]  = B2_SIN_1;
	//assign PIO_OUT[8]  = //NO CONNECTION
	assign PIO_OUT[9]  = GSCLK;
	assign PIO_OUT[10] = DCPRG;
	assign PIO_OUT[11] = BLANK;
	assign PIO_OUT[12] = XLAT;
	assign PIO_OUT[13] = SCLK;
	assign PIO_OUT[14] = B2_SIN_0;
	assign PIO_OUT[15] = VPRG;
	
	/* INPUTS
	assign PIO_OUT[16] = 
	assign PIO_OUT[17] = 
	assign PIO_OUT[18] = 
	assign PIO_OUT[19] = 
	assign PIO_OUT[20] = 
	assign PIO_OUT[21] = 
	assign PIO_OUT[22] = 
	assign PIO_OUT[23] = 
	*/

	wire enable, event_sig;
	debouncer dbncr ( .clk(SYS_CLK), .rst(~SYS_RST_N), .bouncer(DIP_SW[0]), .value(enable), .sig_event(event_sig) );
	
	assign PIO_OUT[24] = enable ? GSCLK : 1'b0;
	assign PIO_OUT[25] = 1'b0;
	assign PIO_OUT[26] = enable ? VPRG : 1'b0;
	assign PIO_OUT[27] = 1'b0;
	assign PIO_OUT[28] = enable ? SCLK : 1'b0;
	assign PIO_OUT[29] = 1'b0;
	assign PIO_OUT[30] = enable ? BLANK : 1'b0;
	assign PIO_OUT[31] = 1'b0;
	assign PIO_OUT[32] = enable ? B1_SIN_0 : 1'b0;
	assign PIO_OUT[33] = enable ? B1_SIN_1 : 1'b0;
	assign PIO_OUT[34] = enable ? B2_SIN_0 : 1'b0;
	assign PIO_OUT[35] = enable ? B2_SIN_1 : 1'b0;
	assign PIO_OUT[36] = enable ? DCPRG : 1'b0;
	assign PIO_OUT[37] = 1'b0;
	assign PIO_OUT[38] = enable ? XLAT : 1'b0;
	assign PIO_OUT[39] = 1'b0;
	
	assign ACC_PORT_PIN[16:9] = {B1_SIN_1,B1_SIN_0,VPRG,XLAT,BLANK,DCPRG,SCLK,GSCLK};
	
	/*assign LED[7] = enable;
	assign LED[6] = 0;
	assign LED[5] = enable;
	assign LED[4] = 0;
	assign LED[3] = enable;
	assign LED[2] = 0;
	assign LED[1] = enable;
	assign LED[0] = 0;*/

	
	BIU bus_interface (
		.fpga_clk(FPGA_CLK1),
		.rst_n(SYS_RST_N),
		.cpld_rs(CPLD_RS5_B),
		.cpld_ws(CPLD_WS5_B),
		.arm_a(ARM_A),
		.arm_data(ARM_D),
		.tlc5940_sin({B2_SIN_1,B2_SIN_0,B1_SIN_1,B1_SIN_0}), // SOUT3,SOUT2,SOUT1,SOUT0
		.tlc5940_xlat(XLAT),
		.tlc5940_blank(BLANK),
		.tlc5940_dcprg(DCPRG),
		.tlc5940_vprg(VPRG),
		.tlc5940_gsclk(GSCLK),
		.tlc5940_sclk(SCLK),
		.rc_port(PIO[67:48]) // Header is in order B1[0]=PIO[48] and B2[9]=PIO[67])
	);
	
	//assign PIO[67:48] = 20'bz;
	
	// Semi-pointless LED effects
	reg [31:0] led_count;
	reg [7:0] led_regs, led_regs2;
	assign LED = led_regs2;
	always @(posedge FPGA_CLK1, negedge SYS_RST_N) begin
		if (~SYS_RST_N) begin
			led_count <= 0;
			led_regs <= 8'b1;
		end
		else begin
			if (enable) begin
				if (led_count < 32'd50000000) begin // About half a second
					//led_regs <= led_regs;
					led_count <= led_count + 1;
					if (led_regs[0] == 1'b1) begin
						led_regs2 <= {6'b0, ~led_regs2[1], led_regs[0]};
					end
					else if (led_regs[7] == 1'b1) begin
						led_regs2 <= {led_regs[7], ~led_regs2[6], 6'b0};
					end
					else if (led_regs[6] == 1'b1) begin
						led_regs2 <= {~led_regs2[7], led_regs[6], ~led_regs2[5], 5'b0};
					end
					else if (led_regs[5] == 1'b1) begin
						led_regs2 <= {1'b0, ~led_regs2[6], led_regs[5], ~led_regs2[4], 4'b0};
					end
					else if (led_regs[4] == 1'b1) begin
						led_regs2 <= {2'b0, ~led_regs2[5], led_regs[4], ~led_regs2[3], 3'b0};
					end
					else if (led_regs[3] == 1'b1) begin
						led_regs2 <= {3'b0, ~led_regs2[4], led_regs[3], ~led_regs2[2], 2'b0};
					end
					else if (led_regs[2] == 1'b1) begin
						led_regs2 <= {4'b0, ~led_regs2[3], led_regs[2], ~led_regs2[1], 1'b0};
					end
					else if (led_regs[1] == 1'b1) begin
						led_regs2 <= {5'b0, ~led_regs2[2], led_regs[1], ~led_regs2[0]};
					end
				end
				else begin
					if (led_regs[7:6] == 2'b10 || led_regs[7:6] == 2'b11) begin
						led_regs <= 1;
						led_count <= 0;
					end
					else begin
						led_regs <= led_regs << 1;
						led_count <= 0;
					end
				end
			end
			else begin
				led_regs <= 8'b00000001;
			end
		end
	end
endmodule
