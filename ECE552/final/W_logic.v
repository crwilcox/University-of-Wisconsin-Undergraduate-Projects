module W_logic(
		input [1:0]  W_rf_wr_data_sel,
		input [15:0] W_alu_out,W_dmem_out,
		input [14:0] W_pc_inc,
		input W_set,
		output reg [15:0] W_rf_wr_data,
		output reg rf_err2
	);

	
`include "localparam.inc"

	always @* casex (W_rf_wr_data_sel)
		ALUOUT: begin W_rf_wr_data= W_alu_out; rf_err2 = 1'b0; end
		DMEMOUT:begin W_rf_wr_data= W_dmem_out; rf_err2 = 1'b0;  end
		PC_INC:begin W_rf_wr_data= {W_pc_inc,1'b0}; rf_err2 = 1'b0;  end
		SET:begin  W_rf_wr_data = {15'd0,W_set} ; rf_err2 = 1'b0;  end
		default: begin  W_rf_wr_data = {16'hxxxx} ; rf_err2 = 1'b1; end
	endcase
endmodule

