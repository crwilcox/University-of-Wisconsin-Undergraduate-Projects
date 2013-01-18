module haz_det(
		input [2:0] D_rf_rd1_sel,
		input [2:0] D_rf_rd2_sel,
		input [1:0] D_alu_in1_sel,
		input [2:0] D_alu_in2_sel,
		input X_rf_wr, M_rf_wr, X_dmem_wr, M_dmem_wr,M_dmem_en,
		input [2:0] X_rf_wr_addr, M_rf_wr_addr,
		input [1:0] D_pc_next_sel,
		input [4:0] opcode,//X_opcode,//opcode is D_opcode
		input X_pc_from_alu,
		input X_dmem_en,
		input halt, D_flush_n,F_imem_stall,M_dmem_stall,
		

	       
		output stall_global, 
	       output stall_decode,


		output D_squash_n,//active low
		output [3:0] fwd_code,
//		output M_squash_n,W_squash_n,
		output F_flush_n
	
		);

`include "localparam.inc"

// NOTE: Need to squash the mem_wr signals of filler instructions
//put bit into DX latch to pull back to Decode, to squash previous
//instruction... this is for use with branch	

/*
* may also need to be concerned with a lw of a sw happening previously
* X_mem_wr M_mem_wr may be useful for this?
*/
   

   assign br_instr = (opcode[4:1] == 4'b0110); //tells if the instr in decode is a branch
  
   assign usingR1 = (D_alu_in1_sel == RF_RD1) | (D_alu_in1_sel == RF_RD1_LSL) 
                    | br_instr;  //or equal to Branch

   assign usingR2 = (D_alu_in2_sel == RF_R7) | (D_alu_in2_sel == RF_RD2) 
		   |(opcode == 5'b10000) | (opcode == 5'b10011); //store instrs
   
   
   assign fwdW2 = ((D_rf_rd2_sel == M_rf_wr_addr) & M_rf_wr & usingR2 );
   assign fwdM2 = ((D_rf_rd2_sel == X_rf_wr_addr) & X_rf_wr & usingR2 );
   assign fwdW1 = ((D_rf_rd1_sel == M_rf_wr_addr) & M_rf_wr & usingR1 );
   assign fwdM1 = ((D_rf_rd1_sel == X_rf_wr_addr) & X_rf_wr & usingR1 );
   
   
	/*
	DEFINITION OF fwd_code
	one hot encoding signifies forwarding
	bit0 = fwd W to alu in 2
	bit1 = fwd M to alu in 2
	bit2 = fwd W to alu in 1
	bit3 = fwd M to alu in 1	
	{3,2,1,0}
	*/

   assign X_dmem_read =(~X_dmem_wr & X_dmem_en); //the instr in the X stage is goign to do a load in M stage
   assign M_dmem_read =(~M_dmem_wr & M_dmem_en); //the instr in the M stage is goign to do a load in M stage

   assign fwd_code = {fwdM1,fwdW1,fwdM2,fwdW2};
  
   assign ld_use = X_dmem_read & (fwdM1 | fwdM2);

   assign ld_branch = (br_instr & fwdM1 & X_dmem_read);  //load immediatly followed by a branch

   assign ld_something_branch = (br_instr & fwdW1 & M_dmem_read);  //load followed by some random instruction, then followed by a branch

   assign set_branch = (br_instr & fwdM1 & ~X_dmem_read);

   //if X_pc_from_alu high then we need to stall decode again
   //assign stall_decode = ((fwdW2 | fwdM2 | fwdW1 | fwdM1) | halt) & D_flush_n;
   assign stall_decode =  (ld_use|ld_branch|ld_something_branch|set_branch|halt) & D_flush_n;

   assign D_squash_n = ~stall_decode;	
   
//   assign M_squash_n = ~stall_global;	
 //  assign W_squash_n = ~stall_global;	

   assign stall_global = M_dmem_stall | F_imem_stall;
   
   //need to assign F_flush_n based on the D_pc_next_sel
   //we will use this to decide to start flushing pipes (aka squash)
   //if D_pc_next_sel != NEXT, go ahead and flush the IF pipe (active low) 
   assign F_flush_n = (D_pc_next_sel == NEXT) | X_pc_from_alu ; 

endmodule
