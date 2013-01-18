module DX_latch(
		input clk,rst,en,
		input D_createdump,
		output X_createdump,

		input [2:0] D_rf_wr_addr,	
		input [1:0] D_alu_in1_sel,
		input [2:0] D_alu_in2_sel,
	//	input [2:0] D_pc_next_sel,
		input [1:0] D_rf_wr_data_sel,
		input D_rf_wr,
		input [2:0] D_alu_ctrl_op, 
		input D_alu_cin,D_alu_inv1,D_alu_inv2,
		input [14:0] D_pc_inc, 
		input [15:0] D_instr,D_rf_rd1,D_rf_rd2,
		input D_dmem_en, D_dmem_wr,
		input [1:0] D_opcode_bottom2,
		input D_pc_from_alu,
		input [1:0] D_fwd1_sel,D_fwd2_sel,
		
		output [2:0] X_rf_wr_addr,	
		output X_pc_from_alu,
		output [1:0] X_alu_in1_sel,
		output [2:0] X_alu_in2_sel,
		input [1:0] X_opcode_bottom2,
	//	output [2:0] X_pc_next_sel,
		output [1:0] X_rf_wr_data_sel,
		output X_rf_wr,
		output [2:0] X_alu_ctrl_op, 
		output X_alu_cin,X_alu_inv1,X_alu_inv2,
		output [14:0] X_pc_inc, 
		output [1:0] X_fwd1_sel,X_fwd2_sel,
		output [15:0] X_instr,X_rf_rd1,X_rf_rd2,
		output X_dmem_en, X_dmem_wr
		);

	wire [2:0] D_rf_wr_addr_d;
	wire [1:0] D_alu_in1_sel_d;
	wire [2:0] D_alu_in2_sel_d;
	//wire [2:0] D_pc_next_sel_d;
	wire [1:0] D_rf_wr_data_sel_d;
	wire [2:0] D_alu_ctrl_op_d;
	wire [15:0] D_instr_d;
	wire [15:0] D_rf_rd1_d;
	wire [15:0] D_rf_rd2_d;
	wire [14:0] D_pc_inc_d;
	wire [1:0] D_opcode_bottom2_d;
	wire [1:0] D_fwd1_sel_d,D_fwd2_sel_d;

	dff DX_rf_wr_addr[2:0](.d(D_rf_wr_addr_d),.q(X_rf_wr_addr),.clk(clk),.rst(rst));
	assign D_rf_wr_addr_d = en ? D_rf_wr_addr : X_rf_wr_addr;	

	dff DX_fwd1_sel[1:0](.d(D_fwd1_sel_d),.q(X_fwd1_sel),.clk(clk),.rst(rst));
	assign D_fwd1_sel_d = en ? D_fwd1_sel : X_fwd1_sel;	

	dff DX_fwd2_sel[1:0](.d(D_fwd2_sel_d),.q(X_fwd2_sel),.clk(clk),.rst(rst));
	assign D_fwd2_sel_d = en ? D_fwd2_sel : X_fwd2_sel;	

	dff DX_alu_in1_sel[1:0](.d(D_alu_in1_sel_d),.q(X_alu_in1_sel),.clk(clk),.rst(rst));
	assign D_alu_in1_sel_d = en ? D_alu_in1_sel : X_alu_in1_sel;	

	dff DX_alu_in2_sel[2:0](.d(D_alu_in2_sel_d),.q(X_alu_in2_sel),.clk(clk),.rst(rst));
	assign D_alu_in2_sel_d = en ? D_alu_in2_sel : X_alu_in2_sel;	

//	dff DX_pc_next_sel[2:0](.d(D_pc_next_sel_d),.q(X_pc_next_sel),.clk(clk),.rst(rst));
//	assign D_pc_next_sel_d = en ? D_pc_next_sel : X_pc_next_sel;	

	dff DX_rf_wr_data_sel[1:0](.d(D_rf_wr_data_sel_d),.q(X_rf_wr_data_sel),.clk(clk),.rst(rst));
	assign D_rf_wr_data_sel_d = en ? D_rf_wr_data_sel: X_rf_wr_data_sel;	
	
	dff DX_opcode_bottom2[1:0](.d(D_opcode_bottom2_d),.q(X_opcode_bottom2),.clk(clk),.rst(rst));
	assign D_opcode_bottom2_d = en ? D_opcode_bottom2: X_opcode_bottom2;	

	dff DX_rf_wr(.d(D_rf_wr_d),.q(X_rf_wr),.clk(clk),.rst(rst));
	assign D_rf_wr_d = en ? D_rf_wr: X_rf_wr;	

	dff DX_alu_ctrl_op[2:0](.d(D_alu_ctrl_op_d),.q(X_alu_ctrl_op),.clk(clk),.rst(rst));
	assign D_alu_ctrl_op_d = en ? D_alu_ctrl_op: X_alu_ctrl_op;	

	dff DX_alu_cin(.d(D_alu_cin_d),.q(X_alu_cin),.clk(clk),.rst(rst));
	assign D_alu_cin_d = en ? D_alu_cin: X_alu_cin;	

	dff DX_alu_inv1(.d(D_alu_inv1_d),.q(X_alu_inv1),.clk(clk),.rst(rst));
	assign D_alu_inv1_d = en ? D_alu_inv1: X_alu_inv1;	

	dff DX_alu_inv2(.d(D_alu_inv2_d),.q(X_alu_inv2),.clk(clk),.rst(rst));
	assign D_alu_inv2_d = en ? D_alu_inv2: X_alu_inv2;	

	dff DX_pc_from_alu(.d(D_pc_from_alu_d),.q(X_pc_from_alu),.clk(clk),.rst(rst));
	assign D_pc_from_alu_d = en ? D_pc_from_alu: X_pc_from_alu;	

	dff DX_createdump (.d(D_createdump_d),.q(X_createdump),.clk(clk),.rst(rst));
	assign D_createdump_d = en ? D_createdump: X_createdump;	

	dff DX_dmem_en(.d(D_dmem_en_d),.q(X_dmem_en),.clk(clk),.rst(rst));
	assign D_dmem_en_d = en ? D_dmem_en: X_dmem_en;	

	dff DX_dmem_wr (.d(D_dmem_wr_d),.q(X_dmem_wr),.clk(clk),.rst(rst));
	assign D_dmem_wr_d = en ? D_dmem_wr: X_dmem_wr;	

	dff DX_instr [15:0](.d(D_instr_d),.q(X_instr),.clk(clk),.rst(rst));
	assign D_instr_d = en ? D_instr: X_instr;	

	dff DX_pc_inc [14:0](.d(D_pc_inc_d),.q(X_pc_inc),.clk(clk),.rst(rst));
	assign D_pc_inc_d = en ? D_pc_inc: X_pc_inc;	

	dff DX_rf_rd1 [15:0](.d(D_rf_rd1_d),.q(X_rf_rd1),.clk(clk),.rst(rst));
	assign D_rf_rd1_d = en ? D_rf_rd1: X_rf_rd1;	

	dff DX_rf_rd2 [15:0](.d(D_rf_rd2_d),.q(X_rf_rd2),.clk(clk),.rst(rst));
	assign D_rf_rd2_d = en ? D_rf_rd2: X_rf_rd2;	

endmodule
