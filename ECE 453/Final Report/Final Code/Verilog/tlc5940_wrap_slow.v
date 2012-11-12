`timescale	1ns	/	1ps
//////////////////////////////////////////////////////////////////////////////////
//	Company: University of Wisconsin Madison - ECE 453 - Group 8
//	Engineer: David Hoese
//	
//	Create	Date: 22:30:11	03/21/2011	
//	Design	Name: TLC5940 Wrapper
//	Module	Name: tlc5940_wrap	
//	Project	Name: Beirut Table
//	Target	Devices:	
//	Tool	versions:	
//	Description:	
//
//	Dependencies:	
//
//	Revision:	
//	Revision	0.01	-	File	Created
//	Additional	Comments:	
//
//////////////////////////////////////////////////////////////////////////////////
module tlc5940_wrap_slow(
	input clk,
	input rst_n,
	input [193:2] data_register0,
	input [193:2] data_register1,
	input [193:2] data_register2,
	input [193:2] data_register3,
	output reg [3:0] sin,				//	Serial	input	to	the	TLC5940
	output reg xlat,						//	Latch	trigger	(level	sensitive	-	active	high)
	output reg blank,						//	Output	enable	(active	low)/GS	count	reset	(active	high)
	output dcprg,							//	H	for	DC	Register,	L	for	EEPROM
	output vprg,							//	H	for	Dot	correction,	L	for	Grayscale
	output reg gsclk,						//	Grayscale	clock	(MAX	30	MHz)
	output reg sclk						//	System	clock	(MAX	30	MHz)
	//output reg [3:0] serial_state,
	//output reg [15:0] sclk_count,				//	Counts	to	SCLK_PERIOD/2	for	falling	to	rising	edge,	or	vice	versa
	//output reg [7:0] gsclk_count			//	Counts	to	GSCLK_PERIOD/2
	);

	// State parameters
	parameter STATE_CLK_POS = 4'd0;		//	Positive edge of the produced gsclk
	parameter STATE_CLK_NEG = 4'd1;		// Negative edge of the produced gsclk
	parameter STATE_RST     = 4'd2;		//	Reset	State	(Outputs	disabled)
	parameter STATE_SEND_GS = 4'd3;		//	Put	a	serial	bit	on	the	s_in	line
	parameter STATE_XLAT0   = 4'd4;		//	Set	blank	to	high
	parameter STATE_XLAT1   = 4'd5;		//	Set	XLAT	to	high	and	blank	high
	parameter STATE_XLAT2   = 4'd6;		//	Keep	XLAT	high	and	blank	high
	parameter STATE_XLAT3   = 4'd7;		//	Keep	XLAT	high	and	blank	high	again
	parameter STATE_XLAT4   = 4'd8;		//	Lower	XLAT	but	keep	BLANK	high
	parameter STATE_XLAT5   = 4'd9;		//	Lower	Blank
	
	// Counting parameters
	parameter GS_BITS       = 8'd192;	//	Total	of	192	Grayscale	bits	are	sent
	parameter BLANK_WAIT    = 12'hFFF;	//	4096	PWM	count	until	blank	must	go	high	to	clear	count
	//parameter SCLK_PERIOD   = 13'd88;	//	88*10ns	=	880ns	sclk	clock	period	(~1.14	MHz)
	parameter SCLK_PERIOD   = 32'd8800;	//	4400*10ns	=	44us	sclk	clock	period	(~22.7 KHz) => (~118.4 Hz PWM)
	//parameter GSCLK_PERIOD  = 3'd4;		//	4*10ns	=	40ns	gsclk	clock	period	(~25	MHz)
	parameter GSCLK_PERIOD  = 32'd400;		//	200*10ns	=	2us	gsclk	clock	period	(~500	KHz) => (~ 122 Hz PWM)
	
	parameter GS_DUTY 		= 8'd10; // 10*10ns = 100ns on time (Duty cycle can not be more than 50%)
	parameter S_DUTY 		= 8'd10; // 10*10ns = 100ns on time (Duty cycle can not be more than 50%)
	
	// Next register for outputs
	reg [3:0] next_sin;
	reg next_xlat;
	reg next_blank;
	reg next_gsclk;
	reg next_sclk;

	// Constant signals
	assign dcprg = 1'b0;				// EEPROM not DC Register
	assign vprg = 1'b0;					// Grayscale not Dot Correction

	//	State	Registers
	reg	[3:0]	serial_state;			// State register for sending of serial data
	reg	[3:0]	next_serial_state;
	
	//	Count	Registers
	reg	[31:0]	sclk_count;				//	Counts	to	SCLK_PERIOD/2	for	falling	to	rising	edge,	or	vice	versa
	reg	[31:0]	next_sclk_count;
	reg	[31:0]	gsclk_count;			//	Counts	to	GSCLK_PERIOD/2
	reg	[31:0]	next_gsclk_count;
	reg	[7:0]	serial_data_count;
	reg	[7:0]	next_serial_data_count;
	
	reg sclk_orig;								// What would have been the clock if there wasn't duty cycle operations
	reg gsclk_orig;							// What would have been the clock if there wasn't duty cycle operations
	
	reg [193:2] latched_data0;
	reg [193:2] latched_data1;
	reg [193:2] latched_data2;
	reg [193:2] latched_data3;
	reg [193:2] next_latched_data0;
	reg [193:2] next_latched_data1;
	reg [193:2] next_latched_data2;
	reg [193:2] next_latched_data3;
	
	reg [7:0] next_gs_duty_count,  next_s_duty_count;
	reg [7:0] gs_duty_count, s_duty_count;
	reg next_gs_duty_clk, next_s_duty_clk;
	
	// Set duty cycle of clocks (Can't be more than 50% duty)
	always @(*) begin
		// GSCLK
		if (next_gsclk == 1'b1) begin
			if (gs_duty_count < GS_DUTY) begin
				next_gs_duty_count = gs_duty_count + 1;
				next_gs_duty_clk = next_gsclk;
			end
			else begin
				next_gs_duty_count = gs_duty_count;
				next_gs_duty_clk = 1'b0;
			end
		end
		else begin
			next_gs_duty_count = 0;
			next_gs_duty_clk = next_gsclk;
		end
		
		// SCLK
		if (next_sclk == 1'b1) begin
			if (s_duty_count < S_DUTY) begin
				next_s_duty_count = s_duty_count + 1;
				next_s_duty_clk = next_sclk;
			end
			else begin
				next_s_duty_count = s_duty_count;
				next_s_duty_clk = 1'b0;
			end
		end
		else begin
			next_s_duty_count = 0;
			next_s_duty_clk = next_sclk;
		end
	end
	
	// Assign duty variables
	always @(posedge clk, negedge rst_n) begin
		if (~rst_n) begin
			gs_duty_count <= 0;
			s_duty_count <= 0;
		end
		else begin
			gs_duty_count <= next_gs_duty_count;
			s_duty_count <= next_s_duty_count;
		end
	end
	
	// Assign next values
	always @(posedge clk, negedge rst_n) begin
		if (~rst_n)	begin
			// Outputs
			sin <= 4'b0;
			xlat <= 1'b0;
			blank <= 1'b1;				//	Disable	Outputs
			gsclk <= 1'b0;
			sclk <= 1'b0;
			sclk_orig <= 1'b0;
			gsclk_orig <= 1'b0;

			// Others
			serial_state      <= STATE_RST;
			sclk_count        <= 0;
			gsclk_count       <= 0;
			serial_data_count <= GS_BITS + 1; // Start at most significant bit
			latched_data0     <= data_register0;
			latched_data1     <= data_register1;
			latched_data2     <= data_register2;
			latched_data3     <= data_register3;
		end
		else begin
			// Outputs
			sin <= next_sin;
			xlat <= next_xlat;
			blank <= next_blank;
			gsclk <= next_gs_duty_clk;//next_gsclk;
			sclk <= next_s_duty_clk;//next_sclk;
			sclk_orig <= next_sclk;
			gsclk_orig <= next_gsclk;
			
			// Others
			serial_state <= next_serial_state;
			sclk_count <= next_sclk_count;
			gsclk_count <= next_gsclk_count;
			serial_data_count <= next_serial_data_count;
			latched_data0     <= next_latched_data0;
			latched_data1     <= next_latched_data1;
			latched_data2     <= next_latched_data2;
			latched_data3     <= next_latched_data3;
		end
	end
	
	always	@(*)	begin
		case	(serial_state)
			STATE_CLK_POS:	begin
				next_sin	=	sin;
				next_xlat	=	xlat;
				next_blank	=	blank;
				next_serial_data_count	=	serial_data_count;
				next_latched_data0 = latched_data0;
				next_latched_data1 = latched_data1;
				next_latched_data2 = latched_data2;
				next_latched_data3 = latched_data3;

				//	Make	decisions	about	the	sclk
				//	First	time	this	clock	pulse	is	starting	(currently	zero)
				if	(gsclk_count == 0 && (sclk_count == 0 || sclk_count == SCLK_PERIOD/2 || sclk_count == SCLK_PERIOD)) begin
					next_serial_state = STATE_CLK_POS;
					next_gsclk_count = gsclk_count + 1;
					next_gsclk = 1'b1;
					
					// Next edge of the sclk
					next_sclk = ~sclk_orig;
					if (sclk_count == SCLK_PERIOD) begin
						// Reset count if we've reached the end
						next_sclk_count = 1;
					end
					else begin
						// Continue counting if we are half way through
						next_sclk_count = sclk_count + 1;
					end
					
				end
				else if (gsclk_count >= GSCLK_PERIOD/2) begin
					// Clock count is currently at 2 or 3 (should be 2)
					// STATE_SEND_GS should handle the count when it is 3
					// Done with posedge
					next_serial_state = STATE_CLK_NEG;
					next_gsclk_count = gsclk_count + 1;
					next_gsclk = 1'b0;
					
					next_sclk = sclk_orig;
					next_sclk_count = sclk_count + 1;
				end
				else begin
					next_serial_state = STATE_CLK_POS;
					next_gsclk_count = gsclk_count + 1;
					next_gsclk = 1'b1;
					
					// Second count of posedge
					next_sclk = sclk_orig;
					next_sclk_count = sclk_count + 1;
				end
			end
			
			STATE_CLK_NEG: begin
				next_sin	=	sin;
				next_xlat	=	xlat;
				next_blank	=	blank;
				next_serial_data_count	=	serial_data_count;
				next_latched_data0 = latched_data0;
				next_latched_data1 = latched_data1;
				next_latched_data2 = latched_data2;
				next_latched_data3 = latched_data3;

				//	Make	decisions	about	the	sclk
				//	First	time	this	clock	pulse	is	starting	(currently	zero)
				if (gsclk_count >= GSCLK_PERIOD - 2) begin // Was GSCLK_PERIOD - 1
					// Done with posedge
					next_serial_state = STATE_SEND_GS;
					next_gsclk_count = gsclk_count + 1;
					next_gsclk = 1'b0;
					
					next_sclk = sclk_orig;
					next_sclk_count = sclk_count + 1;
				end
				else begin
					next_serial_state = STATE_CLK_NEG;
					next_gsclk_count = gsclk_count + 1;
					next_gsclk = 1'b0;
					
					// Second count of posedge
					next_sclk = sclk_orig;
					next_sclk_count = sclk_count + 1;
				end
			end
			
			STATE_RST:	begin
				next_sin	=	1'b0;
				next_xlat	=	1'b0;
				next_blank	=	1'b1; // Start by not displaying outputs
				next_gsclk	=	1'b0;
				next_sclk	=	1'b0;
				next_latched_data0 = latched_data0;
				next_latched_data1 = latched_data1;
				next_latched_data2 = latched_data2;
				next_latched_data3 = latched_data3;
				
				next_serial_state	=	STATE_SEND_GS;
				next_sclk_count	=	0;
				next_gsclk_count = 0;
				next_serial_data_count	=	serial_data_count;
			end
			
			STATE_SEND_GS:	begin
				//next_sin	=	sin;
				next_xlat	=	xlat;
				next_blank	=	blank;
				next_gsclk	=	gsclk_orig;
				next_sclk	=	sclk_orig;
				next_latched_data0 = latched_data0;
				next_latched_data1 = latched_data1;
				next_latched_data2 = latched_data2;
				next_latched_data3 = latched_data3;
				next_gsclk_count = 0;
				
				// If data count == 0 (we haven't sent any new data yet)
				// Need to do most significant bit first
				if (serial_data_count == GS_BITS + 1) begin // Was 0
					// No data sent yet
					next_serial_state = STATE_CLK_POS;
					next_serial_data_count = serial_data_count - 1; // Correct?
					next_sclk_count = 0;
					next_sin[0] = latched_data0[serial_data_count];
					next_sin[1] = latched_data1[serial_data_count];
					next_sin[2] = latched_data2[serial_data_count];
					next_sin[3] = latched_data3[serial_data_count];
				end
				else if (serial_data_count == 1) begin
					// Tell TLC5940 to shift in data
					// We have sent the entire 192 bits of data
					if (sclk_count == (SCLK_PERIOD/2 - 1)) begin
						next_serial_state = STATE_CLK_POS;
						next_serial_data_count = serial_data_count - 1;
						next_sclk_count = sclk_count + 1;
						next_sin = sin;
					end
					else begin
						next_serial_state = STATE_CLK_POS;
						next_serial_data_count = serial_data_count;
						next_sclk_count = sclk_count + 1;
						next_sin = sin;
					end
				end
				else if (serial_data_count == 0) begin
					if (sclk_count == (SCLK_PERIOD - 1)) begin
						// Right before a normal posedge
						// But instead pulse the xlat signal
						next_serial_state = STATE_XLAT0;
						next_serial_data_count = GS_BITS + 1; // Start with MSB
						next_sclk_count = 0;
						next_sin = sin;
						//next_sclk = 1'b0;
					end
					else begin
						next_serial_state = STATE_CLK_POS;
						next_serial_data_count = serial_data_count;
						next_sclk_count = sclk_count + 1;
						next_sin = sin;
					end
				end
				else begin
					// In between data bits, but still counting GS clocks
					if (sclk_count == (SCLK_PERIOD/2 - 1)) begin // XXX: Does this have to be exact/precise? // Could be changed to S/2 + 5 or something
						// Put next data bit on the line
						next_serial_state = STATE_CLK_POS;
						next_serial_data_count = serial_data_count - 1;
						next_sclk_count = sclk_count + 1;
						next_sin[0] = latched_data0[serial_data_count];
						next_sin[1] = latched_data1[serial_data_count];
						next_sin[2] = latched_data2[serial_data_count];
						next_sin[3] = latched_data3[serial_data_count];
					end
					else begin
						next_serial_state = STATE_CLK_POS;
						next_serial_data_count = serial_data_count;
						next_sclk_count = sclk_count + 1;
						next_sin = sin;
					end
				end
			end
				
			STATE_XLAT0:	begin
				next_sin	=	sin;
				//next_xlat	=	xlat;
				//next_blank	=	blank;
				next_gsclk	=	gsclk_orig;
				next_sclk	=	sclk_orig;
				//next_serial_state	=	STATE_SEND_GS;
				next_sclk_count	=	sclk_count;
				next_serial_data_count	=	serial_data_count;
				next_gsclk_count	=	gsclk_count;
				next_latched_data0 = latched_data0;
				next_latched_data1 = latched_data1;
				next_latched_data2 = latched_data2;
				next_latched_data3 = latched_data3;
			
				next_blank	=	1'b1;
				next_xlat	=	1'b0;
				next_serial_state	=	STATE_XLAT1;
			end
			
			STATE_XLAT1:	begin
				next_sin	=	sin;
				//next_xlat	=	xlat;
				//next_blank	=	blank;
				next_gsclk	=	gsclk_orig;
				next_sclk	=	sclk_orig;
				//next_serial_state	=	STATE_SEND_GS;
				next_sclk_count	=	sclk_count;
				next_serial_data_count	=	serial_data_count;
				next_gsclk_count	=	gsclk_count;
				next_latched_data0 = latched_data0;
				next_latched_data1 = latched_data1;
				next_latched_data2 = latched_data2;
				next_latched_data3 = latched_data3;
			
				next_blank	=	blank;
				next_xlat	=	1'b1;
				next_serial_state	=	STATE_XLAT2;
			end
			
			STATE_XLAT2:	begin
				next_sin	=	sin;
				//next_xlat	=	xlat;
				//next_blank	=	blank;
				next_gsclk	=	gsclk_orig;
				next_sclk	=	sclk_orig;
				//next_serial_state	=	STATE_SEND_GS;
				next_sclk_count	=	sclk_count;
				next_serial_data_count	=	serial_data_count;
				next_gsclk_count	=	gsclk_count;
				next_latched_data0 = latched_data0;
				next_latched_data1 = latched_data1;
				next_latched_data2 = latched_data2;
				next_latched_data3 = latched_data3;
				
				next_blank	=	blank;
				next_xlat	=	xlat;
				next_serial_state	=	STATE_XLAT3;
			end
			
			STATE_XLAT3:	begin
				next_sin	=	sin;
				//next_xlat	=	xlat;
				//next_blank	=	blank;
				next_gsclk	=	gsclk_orig;
				next_sclk	=	sclk_orig;
				//next_serial_state	=	STATE_SEND_GS;
				next_sclk_count	=	sclk_count;
				next_serial_data_count	=	serial_data_count;
				next_gsclk_count	=	gsclk_count;
				next_latched_data0 = latched_data0;
				next_latched_data1 = latched_data1;
				next_latched_data2 = latched_data2;
				next_latched_data3 = latched_data3;
				
				next_blank	=	blank;
				next_xlat	=	xlat;
				next_serial_state	=	STATE_XLAT4;
			end
			
			STATE_XLAT4:	begin
				next_sin	=	sin;
				//next_xlat	=	xlat;
				//next_blank	=	blank;
				next_gsclk	=	gsclk_orig;
				next_sclk	=	sclk_orig;
				//next_serial_state	=	STATE_SEND_GS;
				next_sclk_count	=	sclk_count;
				next_serial_data_count	=	serial_data_count;
				next_gsclk_count	=	gsclk_count;
				next_latched_data0 = latched_data0;
				next_latched_data1 = latched_data1;
				next_latched_data2 = latched_data2;
				next_latched_data3 = latched_data3;
				
				next_blank	=	blank;
				next_xlat	=	1'b0;
				next_serial_state	=	STATE_XLAT5;
			end
			
			STATE_XLAT5:	begin
				next_sin	=	sin;
				//next_xlat	=	xlat;
				//next_blank	=	blank;
				next_gsclk	=	gsclk_orig;
				next_sclk	=	sclk_orig;
				//next_serial_state	=	STATE_SEND_GS;
				next_sclk_count	=	sclk_count;
				next_serial_data_count	=	serial_data_count;
				next_gsclk_count	=	gsclk_count;
				next_latched_data0 = data_register0;
				next_latched_data1 = data_register1;
				next_latched_data2 = data_register2;
				next_latched_data3 = data_register3;
				
				next_blank	=	1'b0;
				next_xlat	=	xlat;
				next_serial_state	=	STATE_SEND_GS;
			end
		endcase
	end

endmodule
