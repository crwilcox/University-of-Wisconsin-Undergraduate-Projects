module MW_latch(
		input clk,rst,en,
		
		input [14:0] M_pc_inc,//M_pc_out,
		input [15:0] M_alu_out,	M_dmem_out,
		input [2:0] M_rf_wr_addr,
		input [1:0] M_rf_wr_data_sel,
		input M_rf_wr,M_createdump,M_set,
		
		output [14:0] W_pc_inc,//W_pc_out,
		output [15:0] W_alu_out,W_dmem_out,
		output [2:0] W_rf_wr_addr,
		output [1:0] W_rf_wr_data_sel,
		output W_rf_wr,W_createdump,W_set
		);
		wire [14:0] M_pc_inc_d;
		wire [15:0] M_alu_out_d;
		wire [15:0] M_dmem_out_d;
		wire [1:0] M_rf_wr_data_sel_d; 
		wire [2:0] M_rf_wr_addr_d;

		dff MW_pc_inc[14:0] (.d(M_pc_inc_d),.q(W_pc_inc),.clk(clk),.rst(rst));
		assign M_pc_inc_d = en ? M_pc_inc : W_pc_inc;

		//dff MW_pc_out[14:0] (.d(M_pc_out_d),.q(W_pc_out),.clk(clk),.rst(rst));
	//	assign M_pc_out_d = en ? M_pc_out : W_pc_out;

		dff MW_alu_out[15:0] (.d(M_alu_out_d),.q(W_alu_out),.clk(clk),.rst(rst));
		assign M_alu_out_d = en ? M_alu_out : W_alu_out;

		dff MW_dmem_out[15:0] (.d(M_dmem_out_d),.q(W_dmem_out),.clk(clk),.rst(rst));
		assign M_dmem_out_d = en ? M_dmem_out : W_dmem_out;

		dff MW_rf_wr_addr[2:0] (.d(M_rf_wr_addr_d),.q(W_rf_wr_addr),.clk(clk),.rst(rst));
		assign M_rf_wr_addr_d = en ? M_rf_wr_addr : W_rf_wr_addr;

		dff MW_rf_wr_data_sel[1:0] (.d(M_rf_wr_data_sel_d),.q(W_rf_wr_data_sel),.clk(clk),.rst(rst));
		assign M_rf_wr_data_sel_d = en ? M_rf_wr_data_sel : W_rf_wr_data_sel;

		dff MW_rf_wr (.d(M_rf_wr_d),.q(W_rf_wr),.clk(clk),.rst(rst));
		assign M_rf_wr_d = en ? M_rf_wr: W_rf_wr;

		dff MW_set (.d(M_set_d),.q(W_set),.clk(clk),.rst(rst));
		assign M_set_d = en ? M_set: W_set;

		dff MW_createdump (.d(M_createdump_d),.q(W_createdump),.clk(clk),.rst(rst));
		assign M_createdump_d = en ? M_createdump: W_createdump;

endmodule
