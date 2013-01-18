module FD_latch(
		input clk,rst,en,
		input [14:0] F_pc_inc,
		output [14:0] D_pc_inc,
		input [15:0] F_instr,
		input F_flush_n,//active low
		output [15:0] D_instr,
		output D_flush_n);


	//when the en input is high, the next D value is latched in at next posedge clock
	//when it is low, its value will hold.
	wire [15:0] F_instr_d; 
	wire [14:0] F_pc_inc_d; 

	dff FD_instr[15:0] (.d(F_instr_d),.q(D_instr),.clk(clk),.rst(rst));
	assign F_instr_d = en ? F_instr : D_instr;
	
	dff FD_flush_n (.d(F_flush_n_d),.q(D_flush_n),.clk(clk),.rst(rst));
	assign F_flush_n_d = en ? F_flush_n : D_flush_n;

	dff FD_pc_inc[14:0] (.d(F_pc_inc_d),.q(D_pc_inc),.clk(clk),.rst(rst));
	assign F_pc_inc_d = en ? F_pc_inc : D_pc_inc;

endmodule
