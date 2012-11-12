module D_logic(
		input [14:0] D_pc_inc,
		input [15:0] D_instr,
		input D_flush_n,
		input clk,rst,	

		input [15:0] W_rf_wr_data,
		input [2:0] W_rf_wr_addr,
		input W_rf_wr,

		input M_set,M_alu_out_zero,fwdW1,
		input [1:0] M_rf_wr_data_sel,
	
		output [14:0] D_target_pc,	
		output reg D_pc_from_alu, D_branch,
		output err,
		output D_alu_cin,D_alu_inv1,D_alu_inv2,
		output [2:0] D_alu_ctrl_op,		
		output [15:0] D_rf_rd1,D_rf_rd2,
		output D_createdump,D_rf_wr,D_dmem_en,D_dmem_wr,
		output [4:0] D_opcode,
		output [1:0] D_alu_in1_sel,
		output [2:0] D_alu_in2_sel,
		output [2:0] D_rf_rd2_sel,D_rf_rd1_sel,
		output [1:0] D_rf_wr_data_sel,
		output [1:0] D_pc_next_sel,
		output reg [2:0] D_rf_wr_addr);


	`include "localparam.inc"

	reg  cin,pc_err,alu_op_sel_err,rf_err;
	reg [15:0] pc_inc_b;
	reg [2:0] D_alu_op;
	wire [15:0] D_bform_imm_sext, D_jform_imm_sext, pc_add_out;
  	wire [1:0] D_rf_wr_sel; 
	wire [2:0] D_alu_op_sel;

	//next PC logic
	cla_16 pc_inc (.A({1'b0, D_pc_inc}),.B(pc_inc_b),.Cin(1'b0),.Cout(),.SUM(pc_add_out));	

        assign D_target_pc = pc_add_out[14:0];
   
	always @* casex (D_pc_next_sel)
		    NEXT:  begin  pc_inc_b = 16'hxxxx; pc_err = 1'b0; D_pc_from_alu = 1'b0; D_branch = 0; end 	
		    J_IMM: begin  pc_inc_b = {D_jform_imm_sext[15], D_jform_imm_sext[15:1]};D_pc_from_alu = 1'b0; pc_err = 1'b0; D_branch = 1; end	
		    B_IMM: begin  pc_inc_b = {D_bform_imm_sext[15], D_bform_imm_sext[15:1]};D_pc_from_alu = 1'b0; pc_err = 1'b0; D_branch = 1; end	
		    REG: begin  pc_inc_b = 16'hxxxx; pc_err = 1'b0; D_pc_from_alu = 1'b1; D_branch = 0; end	
		    default: begin  pc_inc_b = 16'hxxxx; pc_err = 1'b1; D_pc_from_alu = 1'b0; D_branch = 0; end	 
	endcase



	aluControl alu_ctrl (.alu_op(D_alu_op),.Cin(D_alu_cin),.inv1(D_alu_inv1),.inv2(D_alu_inv2),.err(alu_ctrl_err),.Op(D_alu_ctrl_op));

	always @* casex (D_alu_op_sel) 
		IFORMAT: begin D_alu_op = {D_instr[14],D_instr[12:11]}; alu_op_sel_err = 1'b0; end
		RFORMAT: begin D_alu_op = {D_instr[11],D_instr[1:0]}; alu_op_sel_err = 1'b0; end
		ADD:	 begin D_alu_op = 3'b100 ; alu_op_sel_err = 1'b0; end
		OR: 	begin D_alu_op =  3'b110; alu_op_sel_err = 1'b0; end
		SUB: 	begin D_alu_op =  3'b101; alu_op_sel_err = 1'b0; end
		3'b101: begin D_alu_op = 3'bxxx; alu_op_sel_err = 1'b1; end     
		3'b110: begin D_alu_op = 3'bxxx; alu_op_sel_err = 1'b1; end
		3'b111:	 begin D_alu_op = 3'bxxx; alu_op_sel_err = 1'b1; end
		default: begin D_alu_op = 3'bxxx; alu_op_sel_err = 1'b1; end
	endcase


	assign D_bform_imm_sext = {{8{D_instr[7]}},D_instr[7:0]};		
	assign D_jform_imm_sext = {{5{D_instr[10]}},D_instr[10:0]};

	rf_bypass RF (	//outputs
			.read1data(D_rf_rd1),.read2data(D_rf_rd2),	
			//inputs
			.read1regsel(D_rf_rd1_sel),.read2regsel(D_rf_rd2_sel),.writeregsel(W_rf_wr_addr),.writedata(W_rf_wr_data),.write(W_rf_wr),
			.clk(clk),.rst(rst),.err(rf_err3));
	
	always @* casex (D_rf_wr_sel)
		RD_I: begin D_rf_wr_addr = D_instr[7:5]; rf_err = 1'b0; end
		RD_R: begin D_rf_wr_addr = D_instr[4:2]; rf_err = 1'b0; end
		RS: begin  D_rf_wr_addr = D_instr[10:8]; rf_err = 1'b0; end
		R7: begin  D_rf_wr_addr = 3'b111; rf_err = 1'b0; end
		default: begin  D_rf_wr_addr = 3'bxxx; rf_err = 1'b1; end
	endcase


	assign D_opcode = D_instr[15:11]; //output to control
	assign br_instr = (D_opcode[4:1] == 4'b0110); //tells if the instr in decode is a branch


	assign D_rf_rd1_sel = D_instr[10:8];//rs, address bits of rf value sent to alu_in_1
	
	assign D_rf_rd2_sel = ((D_alu_in2_sel == RF_R7) ? 3'b111 : D_instr[7:5]); //rt  address 
 
	
  	assign D_rs_zero =  (fwdW1 & br_instr)? 
				(M_rf_wr_data_sel == SET)? ~M_set : M_alu_out_zero 
				: ~|D_rf_rd1; //for BEQZ and BNEZ. read by control logic

	control ctrl (.clk(clk),.rst(rst),.err(ctrl_err),
			//inputs
			.D_opcode(D_opcode),
			.D_rs_zero(D_rs_zero),
			.D_flush_n(D_flush_n),
			//outputs
			.D_pc_next_sel(D_pc_next_sel),
			.D_alu_op_sel(D_alu_op_sel),
			.D_alu_in1_sel(D_alu_in1_sel),
			.D_alu_in2_sel(D_alu_in2_sel),
			.D_rf_wr_data_sel(D_rf_wr_data_sel),
			.D_rf_wr_sel(D_rf_wr_sel),
			.D_rf_wr(D_rf_wr),
			.D_dmem_en(D_dmem_en),
			.D_dmem_wr(D_dmem_wr),
			.D_createdump(D_createdump)
			 );
	

	assign err = pc_err | alu_op_sel_err | alu_ctrl_err | rf_err | ctrl_err;
endmodule

