module prime_detector_behavioral(input in_bit, clk, rst, output reg prime);

//This module implements a prime detector using behavioral verilog

localparam STXXX = 4'b0000;
localparam STXX0 = 4'b0001;
localparam STXX1 = 4'b0010;
localparam ST000 = 4'b0011;
localparam ST001 = 4'b0100;

localparam ST110 = 4'b0101;
localparam STX11 = 4'b0110;
localparam ST010 = 4'b0111;
localparam ST101 = 4'b1000;
localparam ST111 = 4'b1001;

reg [3:0] state;

always @(posedge clk, posedge rst) begin
	if(rst == 1'b1) begin
		state <= STXXX;
		prime <= 1'b0;
	end
	else begin
		case (state) 
			STXXX: begin 
					state <= in_bit ? STXX1 : STXX0;
					prime <= 1'b0;
					end 
			STXX0: begin 
					state <= in_bit ? ST001 : ST000;
					prime <= 1'b0;
					end 
			STXX1: begin 
					state <= in_bit ? STX11 : ST110;
					prime <= 1'b0;
					end 
			ST000: begin 
					state <= in_bit ? ST001 : ST000;
					prime <= 1'b0;
					end 
			ST001: begin 
					state <= in_bit ? ST111 : ST010;
					prime <= 1'b1;
					end 
			ST110: begin 
					state <= in_bit ? ST101 : ST000;
					prime <= in_bit;
					end 
			STX11: begin 
					state <= in_bit ? ST111 : ST110;
					prime <= in_bit;
					end 
			ST010: begin
					state <= in_bit ? ST101 : ST000;
					prime <= in_bit;
					end 
			ST101: begin
					state <= in_bit ? ST111 : ST010;
					prime <= 1'b1;
					end
			ST111: begin
					state <= in_bit ? ST111 : ST110;
					prime <= in_bit;
					end
			default: begin
					state <= STXXX;
					prime <= 1'b0;
					end
		endcase
	end
end
endmodule // prime_detector_behavioral