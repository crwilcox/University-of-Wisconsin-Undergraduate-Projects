module XM_latch (
		input clk,rst,en,

		input [15:0] X_alu_out,X_rf_rd2,
		input [14:0] X_pc_inc,//X_pc_out,
		input X_dmem_en,X_dmem_wr,
		input [2:0] X_rf_wr_addr,	
		input [1:0] X_rf_wr_data_sel,
		input X_rf_wr,X_set,X_createdump,	
	
		output [15:0] M_alu_out,M_rf_rd2,
		output [14:0] M_pc_inc,//X_pc_out,
		output M_dmem_en,M_dmem_wr,
		output [2:0] M_rf_wr_addr,	
		output [1:0] M_rf_wr_data_sel,
		output M_rf_wr,M_set, M_createdump);
		
		wire [1:0] X_rf_wr_data_sel_d;
		wire [15:0] X_alu_out_d;
		wire [15:0] X_rf_rd2_d;
//		wire [14:0] X_pc_out_d;
		wire [14:0] X_pc_inc_d;
		wire [2:0] X_rf_wr_addr_d;

		dff XM_alu_out[15:0] (.d(X_alu_out_d),.q(M_alu_out),.clk(clk),.rst(rst));
		assign X_alu_out_d = en ? X_alu_out : M_alu_out;

		dff XM_rf_rd2[15:0] (.d(X_rf_rd2_d),.q(M_rf_rd2),.clk(clk),.rst(rst));
		assign X_rf_rd2_d = en ? X_rf_rd2 : M_rf_rd2;
		
		dff XM_dmem_en (.d(X_dmem_en_d),.q(M_dmem_en),.clk(clk),.rst(rst));
		assign X_dmem_en_d = en ? X_dmem_en : M_dmem_en;

		dff XM_dmem_wr (.d(X_dmem_wr_d),.q(M_dmem_wr),.clk(clk),.rst(rst));
		assign X_dmem_wr_d = en ? X_dmem_wr : M_dmem_wr;

		dff XM_rf_wr (.d(X_rf_wr_d),.q(M_rf_wr),.clk(clk),.rst(rst));
		assign X_rf_wr_d = en ? X_rf_wr : M_rf_wr;

		dff XM_createdump (.d(X_createdump_d),.q(M_createdump),.clk(clk),.rst(rst));
		assign X_createdump_d = en ? X_createdump : M_createdump;

		dff XM_set (.d(X_set_d),.q(M_set),.clk(clk),.rst(rst));
		assign X_set_d = en ? X_set : M_set;

//		dff XM_pc_out[14:0] (.d(X_pc_out_d),.q(M_pc_out),.clk(clk),.rst(rst));
//		assign X_pc_out_d = en ? X_pc_out : M_pc_out;

		dff XM_pc_inc[14:0] (.d(X_pc_inc_d),.q(M_pc_inc),.clk(clk),.rst(rst));
		assign X_pc_inc_d = en ? X_pc_inc : M_pc_inc;

		dff XM_rf_wr_addr[2:0] (.d(X_rf_wr_addr_d),.q(M_rf_wr_addr),.clk(clk),.rst(rst));
		assign X_rf_wr_addr_d = en ? X_rf_wr_addr : M_rf_wr_addr;

		dff XM_rf_wr_data_sel[1:0] (.d(X_rf_wr_data_sel_d),.q(M_rf_wr_data_sel),.clk(clk),.rst(rst));
		assign X_rf_wr_data_sel_d = en ? X_rf_wr_data_sel : M_rf_wr_data_sel;


endmodule
