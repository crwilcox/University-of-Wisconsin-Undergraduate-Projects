/* $Author: karu $ */
/* $LastChangedDate: 2009-03-04 23:09:45 -0600 (Wed, 04 Mar 2009) $ */
/* $Rev: 45 $ */
module proc (/*AUTOARG*/
   // Outputs
   err, 
   // Inputs
   clk, rst
   );

   input clk;
   input rst;

   output err;

   // None of the above lines can be modified

   // OR all the err ouputs for every sub-module and assign it as this
   // err output
   
   // As desribed in the homeworks, use the err signal to trap corner
   // cases that you think are illegal in your statemachines
   
   
   /* your code here */
	wire [15:0] X_rf_rd1,X_rf_rd2;
	wire [1:0] X_alu_in1_sel,X_opcode_bottom2;
	wire [2:0] X_alu_in2_sel;
	wire [2:0] X_alu_ctrl_op;
	wire [14:0] F_pc_inc, D_pc_inc, D_target_pc;
	wire [15:0] D_instr,D_rf_rd1,D_rf_rd2;
	wire [2:0] D_alu_ctrl_op,W_rf_wr_addr;
	

	wire [15:0] X_rf_rd2_fwd;

	wire [4:0] D_opcode;
	wire [1:0] D_alu_in1_sel, D_rf_wr_data_sel;
	wire [2:0] D_alu_in2_sel;

	wire [3:0] fwd_code;
	wire [1:0] D_fwd1_sel,D_fwd2_sel,X_fwd1_sel,X_fwd2_sel;

	wire [15:0] W_alu_out,W_dmem_out,W_rf_wr_data;
	wire [14:0] W_pc_inc,X_pc_inc,M_pc_inc;
	wire [1:0] W_rf_wr_data_sel,M_rf_wr_data_sel,X_rf_wr_data_sel,D_pc_next_sel;

	wire [15:0] M_rf_rd2,M_alu_out,M_dmem_out;
	wire [15:0] X_alu_out,F_instr,X_instr;
	wire [2:0] D_rf_wr_addr,X_rf_wr_addr,M_rf_wr_addr,D_rf_rd1_sel,D_rf_rd2_sel;

///////////////////////////////////////////////////////////////////////////////
//F stage logic

	
	F_logic flog(.clk(clk),.rst(rst),.W_createdump(W_createdump), .D_branch(D_branch),
			.stall_decode(stall_decode),.stall_global(stall_global),.X_pc_from_alu(X_pc_from_alu),
			.X_alu_out(X_alu_out), .D_target_pc(D_target_pc), .F_pc_inc(F_pc_inc),.F_instr(F_instr),
			.F_imem_align_err(F_imem_align_err),.F_imem_stall(F_imem_stall),.F_imem_done(F_imem_done));

//F stage logic
///////////////////////////////////////////////////////////////////////////////

///////////////////////////////////////////////////////////////////////////////
//FD Latch

	FD_latch FD ( .clk(clk),.rst(rst),.en(~stall_decode & ~stall_global),
			//inputs from the F stage
			.F_instr(F_instr), //F_instr comes from the Imem
			.F_flush_n(F_flush_n & ~ X_pc_from_alu),
		        .F_pc_inc(F_pc_inc),
			//outputs into the D stage
			.D_instr(D_instr),			
			.D_flush_n(D_flush_n),
		        .D_pc_inc(D_pc_inc));

//FD Latch
///////////////////////////////////////////////////////////////////////////////


///////////////////////////////////////////////////////////////////////////////
//D stage logic

	D_logic dlog(.clk(clk),.rst(rst),.err(dlog_err),.D_pc_inc(D_pc_inc),.D_instr(D_instr), .D_branch(D_branch),
			.D_flush_n(D_flush_n),.D_pc_from_alu(D_pc_from_alu),.D_createdump(D_createdump),
			.D_rf_rd1(D_rf_rd1),.D_rf_rd2(D_rf_rd2),.W_rf_wr(W_rf_wr),.W_rf_wr_data(W_rf_wr_data),
			.W_rf_wr_addr(W_rf_wr_addr),.D_target_pc(D_target_pc),.D_opcode(D_opcode),
			.D_alu_cin(D_alu_cin),.D_alu_inv1(D_alu_inv1),.D_alu_inv2(D_alu_inv2),.D_alu_ctrl_op(D_alu_ctrl_op),
			.D_alu_in1_sel(D_alu_in1_sel),.D_alu_in2_sel(D_alu_in2_sel),.D_rf_wr_data_sel(D_rf_wr_data_sel)
			,.D_rf_wr_addr(D_rf_wr_addr),.D_rf_rd1_sel(D_rf_rd1_sel),.D_rf_rd2_sel(D_rf_rd2_sel),.D_rf_wr(D_rf_wr),
			.D_dmem_en(D_dmem_en),.D_dmem_wr(D_dmem_wr),.D_pc_next_sel(D_pc_next_sel),

			.M_set(M_set),.M_alu_out_zero(M_alu_out_zero),.M_rf_wr_data_sel(M_rf_wr_data_sel),.fwdW1(fwd_code[2]));

//D stage logic
///////////////////////////////////////////////////////////////////////////////


///////////////////////////////////////////////////////////////////////////////
//DX latch
	DX_latch DX (	 .clk(clk), .rst(rst),.en(~stall_global),
				//inputs
				.D_rf_wr_addr(D_rf_wr_addr),
				.D_alu_in2_sel(D_alu_in2_sel),	
				.D_alu_in1_sel(D_alu_in1_sel),	
				.D_rf_wr_data_sel(D_rf_wr_data_sel),
				.D_rf_wr(D_rf_wr & D_squash_n),
				.D_alu_ctrl_op(D_alu_ctrl_op),
				.D_alu_cin(D_alu_cin),
				.D_alu_inv1(D_alu_inv1),
				.D_alu_inv2(D_alu_inv2),
				.D_pc_inc(D_pc_inc[14:0]),
				.D_instr(D_instr),
				.D_pc_from_alu(D_pc_from_alu & D_squash_n),
				.D_rf_rd1(D_rf_rd1),
				.D_rf_rd2(D_rf_rd2),
				.D_createdump(D_createdump),
				.D_dmem_en(D_dmem_en & D_squash_n),
				.D_dmem_wr(D_dmem_wr & D_squash_n),
				.D_opcode_bottom2(D_opcode[1:0]),
				
				.D_fwd1_sel(D_fwd1_sel), //comes from the fwd unit
				.D_fwd2_sel(D_fwd2_sel),

				//outputs
				.X_dmem_en(X_dmem_en),
				.X_dmem_wr(X_dmem_wr),
				.X_rf_rd1(X_rf_rd1),
				.X_rf_rd2(X_rf_rd2),
				.X_pc_from_alu(X_pc_from_alu),
				.X_pc_inc(X_pc_inc),
				.X_rf_wr_addr(X_rf_wr_addr),
				.X_rf_wr_data_sel(X_rf_wr_data_sel),
				.X_rf_wr(X_rf_wr),
				.X_alu_in2_sel(X_alu_in2_sel),	
				.X_alu_in1_sel(X_alu_in1_sel),	
				.X_alu_ctrl_op(X_alu_ctrl_op),
				.X_alu_cin(X_alu_cin),
				.X_alu_inv1(X_alu_inv1),
				.X_alu_inv2(X_alu_inv2),
				.X_instr(X_instr),
				
				.X_fwd1_sel(X_fwd1_sel),
				.X_fwd2_sel(X_fwd2_sel),//go to the X alu in muxes


				.X_opcode_bottom2(X_opcode_bottom2),//this goes into set
				.X_createdump(X_createdump)
			);

//DX latch
///////////////////////////////////////////////////////////////////////////////


///////////////////////////////////////////////////////////////////////////////
//X stage logic


	
	X_logic xlog(.X_rf_rd1(X_rf_rd1),.X_rf_rd2(X_rf_rd2),.X_alu_in1_sel(X_alu_in1_sel), .X_pc_inc(X_pc_inc),
			.X_alu_in2_sel(X_alu_in2_sel),.X_alu_inv1(X_alu_inv1),.X_alu_inv2(X_alu_inv2),
			.X_alu_cin(X_alu_cin),.X_alu_ctrl_op(X_alu_ctrl_op),.X_instr(X_instr),.X_opcode_bottom2(X_opcode_bottom2),
			.X_alu_out(X_alu_out),.err(xlog_err),.X_set(X_set),.fwd1_sel(X_fwd1_sel),
			.fwd2_sel(X_fwd2_sel),.M_alu_out(M_alu_out),.W_alu_out(W_alu_out),
			.W_rf_wr_data_sel(W_rf_wr_data_sel),.W_dmem_out(W_dmem_out),.X_rf_rd2_fwd(X_rf_rd2_fwd),
			.M_set(M_set),.W_set(W_set),.M_rf_wr_data_sel(M_rf_wr_data_sel));


//X stage logic
///////////////////////////////////////////////////////////////////////////////


///////////////////////////////////////////////////////////////////////////////
//XM latch 
	wire M_dmem_wr, M_dmem_en;

/*	wire [15:0] X_fixed_rf_rd2;
	assign X_fixed_rf_rd2 = (fwd_code[1]) ? 
				(M_dmem_en & ~M_dmem_wr) ? M_dmem_out : M_alu_out 
				:(fwd_code[0] ? W_rf_wr_data : X_rf_rd2);
*/
	XM_latch XM( .clk(clk),.rst(rst),.en(~stall_global),
			//Inputs
			.X_alu_out(X_alu_out),
			.X_rf_rd2(X_rf_rd2_fwd),
			.X_dmem_en(X_dmem_en),
			.X_dmem_wr(X_dmem_wr),
			.X_rf_wr_addr(X_rf_wr_addr),
			.X_rf_wr_data_sel(X_rf_wr_data_sel),
			.X_rf_wr(X_rf_wr),
			.X_createdump(X_createdump),
			.X_set(X_set),
			.X_pc_inc(X_pc_inc),

			//outputs
			.M_createdump(M_createdump),
			.M_set(M_set),
			.M_alu_out(M_alu_out),
			.M_rf_rd2(M_rf_rd2),
			.M_rf_wr_addr(M_rf_wr_addr),
			.M_rf_wr_data_sel(M_rf_wr_data_sel),
			.M_rf_wr(M_rf_wr),
			.M_dmem_en(M_dmem_en),
			.M_pc_inc(M_pc_inc),
			.M_dmem_wr(M_dmem_wr)
			);
//XM latch 
///////////////////////////////////////////////////////////////////////////////

///////////////////////////////////////////////////////////////////////////////
//M stage logic 


	M_logic mlog(.clk(clk),.rst(rst),.W_createdump(W_createdump),
			.M_dmem_out(M_dmem_out),.M_dmem_wr(M_dmem_wr),.M_dmem_en(M_dmem_en),
			.M_rf_rd2(M_rf_rd2),.M_alu_out(M_alu_out),.M_dmem_align_err(M_dmem_align_err),
			.M_alu_out_zero(M_alu_out_zero),.M_dmem_stall(M_dmem_stall),.M_dmem_done(M_dmem_done));

//M stage logic 
///////////////////////////////////////////////////////////////////////////////


///////////////////////////////////////////////////////////////////////////////
//MW latch  
	MW_latch MW(.clk(clk),.rst(rst),.en(~stall_global),
			//INPUTS	
			.M_createdump(M_createdump | M_dmem_align_err),
			.M_alu_out(M_alu_out),
			.M_dmem_out(M_dmem_out),
			.M_rf_wr_addr(M_rf_wr_addr),
			.M_rf_wr_data_sel(M_rf_wr_data_sel),
			.M_rf_wr(M_rf_wr & ~M_dmem_align_err),
			.M_set(M_set),
			.M_pc_inc(M_pc_inc),

			//OUTPUTS
			.W_createdump(W_createdump),
			.W_dmem_out(W_dmem_out),
			.W_alu_out(W_alu_out),
			.W_rf_wr_addr(W_rf_wr_addr),
			.W_rf_wr_data_sel(W_rf_wr_data_sel),
			.W_rf_wr(W_rf_wr),
			.W_set(W_set),
			.W_pc_inc(W_pc_inc)
			);
//MW latch  
///////////////////////////////////////////////////////////////////////////////


///////////////////////////////////////////////////////////////////////////////
//W stage logic  


	W_logic wlog(.W_set(W_set),.rf_err2(rf_err2),.W_rf_wr_data(W_rf_wr_data),.W_rf_wr_data_sel(W_rf_wr_data_sel),
			.W_alu_out(W_alu_out),.W_dmem_out(W_dmem_out),.W_pc_inc(W_pc_inc));

//W stage logic  
///////////////////////////////////////////////////////////////////////////////


///////////////////////////////////////////////////////////////////////////////
//Forward and Hazard detect units

	fwd_unit fwd(.fwd_code(fwd_code),.fwd1_sel(D_fwd1_sel),.fwd2_sel(D_fwd2_sel));

	haz_det haz(	//outputs
			.fwd_code(fwd_code),
			.stall_global(stall_global),
			.stall_decode(stall_decode),
			.F_flush_n(F_flush_n),
			.D_squash_n(D_squash_n),

/*			.M_squash_n(M_squash_n),
			.W_squash_n(W_squash_n),
*/
			//inputs
			.F_imem_stall(F_imem_stall),
			.M_dmem_stall(M_dmem_stall),
			.X_rf_wr(X_rf_wr),
			.M_rf_wr(M_rf_wr),
			.X_pc_from_alu(X_pc_from_alu),
			.X_dmem_wr(X_dmem_wr),
			.M_dmem_wr(M_dmem_wr),
			.D_rf_rd1_sel(D_rf_rd1_sel),
			.D_rf_rd2_sel(D_rf_rd2_sel),
			.X_rf_wr_addr(X_rf_wr_addr),
			.M_rf_wr_addr(M_rf_wr_addr),
			.D_alu_in1_sel(D_alu_in1_sel),
			.D_alu_in2_sel(D_alu_in2_sel),
			.D_pc_next_sel(D_pc_next_sel),
			.opcode(D_opcode),
//			.X_opcode(X_instr[15:11]),
			.X_dmem_en(X_dmem_en),
			.M_dmem_en(M_dmem_en),
			.halt(D_createdump),
			.D_flush_n(D_flush_n)
			);

//Forward and Hazard detect units
///////////////////////////////////////////////////////////////////////////////

	assign err =  ~rst & (rf_err2 | dlog_err | xlog_err) ;//| F_imem_align_err | M_dmem_align_err);  

   
endmodule // proc
// DUMMY LINE FOR REV CONTROL :0:
