module F_logic(
	       input clk,rst,W_createdump,
	       input stall_decode,stall_global,
	       input X_pc_from_alu, D_branch,
	       input [15:0] X_alu_out,
	       input [14:0] D_target_pc,
	       
	       
	       output [14:0] F_pc_inc,
	       output [15:0] F_instr,
		output F_imem_align_err,F_imem_done,F_imem_stall
	       );

   wire [14:0] 		     pc_next, pc_next_d, F_pc_out;
   wire [15:0] 		     inc_out;
      
   cla_16 pc_inc(.A({1'b0, F_pc_out}), .B(16'h0000), .Cin(1'b1), .Cout(), .SUM(inc_out));

   assign F_pc_inc = inc_out[14:0];
      
   reg15 PC (.clk(clk),.rst(rst),.q(F_pc_out),.d(pc_next_d));

   assign pc_next_d = (stall_decode | stall_global) ? F_pc_out : pc_next;
   assign pc_next = X_pc_from_alu ? X_alu_out[15:1] :
		    D_branch ? D_target_pc : 
		    F_pc_inc;

   mem_system #(.mem_type(0)) //param for imem 
			imem (.DataIn(16'd0),.Addr({F_pc_out,1'b0}),.Rd(1'b1),.Wr(1'b0),.clk(clk),.createdump(W_createdump),.rst(rst),
			.DataOut(F_instr),.err(F_imem_align_err),.CacheHit(F_cache_hit),.Done(F_imem_done),.Stall(F_imem_stall));

   
   
   
endmodule
