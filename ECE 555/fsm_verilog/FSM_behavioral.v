module FSM_behavioral(
	input CLK,rst,start,
	output reg done,left_right,ppgen_en,add_en,shift_en,
	output reg[4:0] wr_en,rd_enA,rd_enB);

	reg[3:0] state, next_state;
	reg count16_init,count16_inc;
	wire [3:0] count16_out;

	//localparam IDLE= 4'h0;
	localparam LATCH_MCAND_IDLE= 4'h1;
	localparam LATCH_MPLIER= 4'h2;
	localparam ZERO_R4= 4'h3;
	localparam INIT_R5= 4'h4;
	localparam PPGEN= 4'h5;
	localparam ACCUM= 4'h6;
	localparam SHIFT_MCAND= 4'h7;
	localparam SHIFT_MPLIER= 4'h8;
	localparam DONE= 4'h9;


	count16_behavioral counter(.clk(CLK),.rst(rst),.count16_inc(count16_inc),
			.count16_init(count16_init),.count16_out(count16_out));



	always @(posedge CLK) begin
		//PUT I ASYNC RESET!
		if (rst == 1'b1) begin
			state <= 4'h1;
		end
		if (rst == 1'b0) begin
			state <= next_state;
		end
	end	

	always @(*) begin

		done = 0;
		left_right = 0;
		ppgen_en = 0;
		add_en=0;
		shift_en=0;
		count16_inc =0;
		count16_init = 0;
		wr_en=5'h00;
		rd_enA=5'h00;
		rd_enB=5'h00;

		case (state) 
		//IDLE: next_state = start ? LATCH_MCAND : IDLE;
		LATCH_MCAND_IDLE: begin
			wr_en[0] = start ? 1:0;
			next_state = start? LATCH_MPLIER : LATCH_MCAND_IDLE;
			end
		LATCH_MPLIER: begin
			wr_en[1]=1;
			next_state = ZERO_R4;
			end
		ZERO_R4: begin	
			wr_en[3]=1;
			next_state = INIT_R5;
			end
			
		INIT_R5: begin
			wr_en[4] = 1;
			count16_init = 1;
			next_state = PPGEN;
			end
		PPGEN: begin
			wr_en[2] = 1;
			rd_enA[0] = 1;
			rd_enB[1] = 1;
			ppgen_en = 1;
			count16_inc = 1;
			next_state = ACCUM;
			end
		ACCUM: begin
			wr_en[3]=1;
			rd_enA[2]=1;	
			rd_enB[3]=1;
			add_en = 1;
			next_state = SHIFT_MCAND;
			end
		SHIFT_MCAND: begin
			wr_en[1]=1;
			rd_enA[4]=1;
			rd_enB[1]=1;
			shift_en=1;
			left_right=1;
			next_state=SHIFT_MPLIER;
			end
		SHIFT_MPLIER: begin
			wr_en[0]=1;
			rd_enA[4]=1;
			rd_enB[0]=1;
			shift_en=1;
			//left_right already set to 0
			next_state= &count16_out ? DONE: PPGEN;
			end
		DONE: begin
			done = 1;
			rd_enA[3]=1;
			wr_en[0]= start ? 1 : 0;
			next_state = start ? LATCH_MPLIER:LATCH_MCAND_IDLE;
			end
		endcase
	end//end combinational always

endmodule


module count16_behavioral(input clk,rst,count16_inc,count16_init,output [3:0] count16_out);

	reg [3:0] count, next_count;	


	always @(posedge clk) begin
		//RESET!
		if (rst == 1'b1) begin
			count <= 4'h0;
		end
		if (rst == 1'b0) begin
			count <= next_count;
		end
	end

	always @(*) begin
		case ({count16_inc,count16_init})
		2'b00: next_count = count;
		2'b01: next_count = 4'h0;
		2'b10: next_count = count + 1;
		2'b11: next_count = 4'h0;//not used
		endcase
	end
	
	assign count16_out = count;
endmodule
