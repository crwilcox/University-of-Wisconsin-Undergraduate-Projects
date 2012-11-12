module X_logic(
		input [15:0] X_rf_rd1,X_rf_rd2,
		input [1:0] X_alu_in1_sel,
		input [2:0] X_alu_in2_sel,
		input X_alu_inv1,X_alu_inv2,X_alu_cin,
		input [2:0] X_alu_ctrl_op,
		input [15:0] X_instr,
		input [1:0] X_opcode_bottom2,
		input [14:0] X_pc_inc,
		input [1:0] W_rf_wr_data_sel,M_rf_wr_data_sel,
		input M_set,W_set,

		//fwd stuff
		input [1:0] fwd1_sel,fwd2_sel,
		input [15:0] M_alu_out,W_alu_out,W_dmem_out,
		//fwd stuff

		output [15:0] X_alu_out,
		output reg [15:0] X_rf_rd2_fwd,
		output err,X_set
		);
	`include "localparam.inc"

	wire [15:0] X_bform_imm_sext,X_bform_imm_zext,X_iform_imm_sext,X_iform_imm_zext;
	reg [15:0] X_alu_in1,X_alu_in2;
	reg alu_err2,alu_err;
	reg [15:0] X_rf_rd1_fwd;//these are what actually go into the ALU, post fwd muxes


	alu_16 alu( 	//outputs 
			.Zero(X_alu_zero), .OFL(X_alu_ofl), .Out(X_alu_out),
			//inputs
			.invA(X_alu_inv1),.invB(X_alu_inv2),.Cin(X_alu_cin),.Op(X_alu_ctrl_op),
			.A(X_alu_in1),.B(X_alu_in2),.Cout(X_alu_cout));

	assign X_alu_neg = X_alu_out[15]; 
	//used by set in combination with alu_zero to do signed greater than and less than

	//muxout the inputs to the alu based on alu_in1_sel and alu_in2sel
	always @* casex (X_alu_in1_sel) 
		RF_RD1: begin  X_alu_in1 = X_rf_rd1_fwd; alu_err2 = 1'b0; end
		RF_RD1_LSL:begin   X_alu_in1 = {X_rf_rd1_fwd[7:0],8'h00}; alu_err2 = 1'b0; end
		ZERO:begin   X_alu_in1 = 16'd0; alu_err2 = 1'b0; end
		TWO:begin   X_alu_in1 = 16'd2; alu_err2 = 1'b0; end
		default:begin X_alu_in1 =  16'hxxxx; alu_err2 = 1'b1; end
	endcase
	always @* casex (X_alu_in2_sel) 
		RF_RD2:begin  X_alu_in2 = X_rf_rd2_fwd; alu_err = 1'b0; end
		RF_R7: begin X_alu_in2 = X_rf_rd2_fwd;alu_err = 1'b0; end
		IFORMAT_SEXT:begin  X_alu_in2 = X_iform_imm_sext; alu_err = 1'b0; end
		IFORMAT_ZEXT: begin  X_alu_in2 = X_iform_imm_zext; alu_err = 1'b0; end
		BFORMAT_SEXT: begin  X_alu_in2 = X_bform_imm_sext; alu_err = 1'b0; end
		BFORMAT_ZEXT: begin  X_alu_in2 = X_bform_imm_zext; alu_err = 1'b0; end
		PC_OUT: begin  X_alu_in2 = {X_pc_inc,1'b0}; alu_err = 1'b0; end
		3'b011: begin  X_alu_in2 = 16'hxxxx; alu_err = 1'b0; end
		default:begin  X_alu_in2 = 16'hxxxx; alu_err = 1'b1; end
	endcase

	assign X_bform_imm_sext = {{8{X_instr[7]}},X_instr[7:0]};		
	assign X_bform_imm_zext = {8'h00,X_instr[7:0]};
	assign X_iform_imm_sext = {{11{X_instr[4]}},X_instr[4:0]};
	assign X_iform_imm_zext = {11'd0,X_instr[4:0]};


	set set(//out
		.X_set(X_set),
		.err(set_err),
		//in
		.opcode(X_opcode_bottom2),
		.X_alu_zero(X_alu_zero),
		.X_alu_ofl(X_alu_ofl),
		.X_alu_cout(X_alu_cout),
		.X_alu_neg(X_alu_neg)
		);


	reg fwd_err1,fwd_err2; 
	//alu forwarding to the X stage from M and W
	always @* casex(fwd1_sel) 
		IDLATCH: begin X_rf_rd1_fwd = X_rf_rd1; fwd_err1 = 1'b0; end
		M: begin 
			 X_rf_rd1_fwd = (M_rf_wr_data_sel == SET)?{15'h0000,M_set}:M_alu_out;
			 fwd_err1= 1'b0; 
		end
		W: begin 
			X_rf_rd1_fwd = (W_rf_wr_data_sel==ALUOUT) ? W_alu_out:
					(W_rf_wr_data_sel == SET)?{15'h0000,W_set}: W_dmem_out;
			fwd_err1= 1'b0; 
		end
		2'b11: begin X_rf_rd1_fwd = 16'hXXXX; fwd_err1= 1'b1; end  //unused
		default: begin X_rf_rd1_fwd = 16'hXXXX; fwd_err1= 1'b1; end	
	endcase

	always @* casex(fwd2_sel) 
		IDLATCH: begin X_rf_rd2_fwd = X_rf_rd2; fwd_err2= 1'b0; end
		M: begin 
			X_rf_rd2_fwd = (M_rf_wr_data_sel == SET)?{15'h0000,M_set}:M_alu_out;
			fwd_err2= 1'b0; 
		end
		W: begin 
			X_rf_rd2_fwd = (W_rf_wr_data_sel == ALUOUT)?W_alu_out: 
				(W_rf_wr_data_sel == SET)?{15'h0000,W_set}:W_dmem_out; 
			fwd_err2= 1'b0; 
		end
		2'b11: begin X_rf_rd2_fwd = 16'hXXXX; fwd_err2= 1'b1; end  //unused
		default: begin X_rf_rd2_fwd = 16'hXXXX; fwd_err2= 1'b1; end	
	endcase 
	//alu forwarding to the X stage from M and W


	assign err = alu_err2 | alu_err | set_err | fwd_err1 | fwd_err2;

endmodule
