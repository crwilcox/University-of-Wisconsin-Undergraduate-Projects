module p1_mealy(input A, clk, rst_n,
				output reg Y);

	localparam S_EXEC	= 3'b000; 
	localparam S_RECOV	= 3'b001;
	localparam S_PURGE	= 3'b010;
	localparam S_ERROR	= 3'b011;
	localparam S_RST	= 3'b111;

	reg[2:0] state;
	reg[2:0] nextState;


	always @(posedge clk, negedge rst_n) begin
		if(!rst_n) state <= S_RST;
		else state <= nextState;
	end


	//NEXT STATE LOGIC AND OUTPUTS
	always @(state, A) begin
		case(state)
			S_EXEC:	begin
				nextState = A ? S_RST : S_ERROR; 
				Y = 1'b0;
			end
			S_RECOV: begin
				nextState = S_EXEC;
				Y = 1'b1;
			end
			S_PURGE: begin
				nextState = A ? S_RECOV : S_PURGE;
				Y = !A;
			end
			S_ERROR: begin
				nextState = A ? S_PURGE : S_ERROR;
				Y = 1'b0;
			end
			S_RST: begin
				nextState = A ? S_EXEC : S_ERROR;
				Y = A;
			end
			default: begin
				nextState = S_RST;
				Y = 1'b0;
			end
		endcase
	end	
endmodule

