module control(
	       input clk, rst,
	       input [4:0] D_opcode,
	       input D_rs_zero, D_flush_n,
	       output reg [1:0] D_pc_next_sel,
	       output reg [2:0] D_alu_op_sel,D_alu_in2_sel,
	       output reg [1:0] D_alu_in1_sel, D_rf_wr_data_sel,
	       output reg [1:0] D_rf_wr_sel,
	       output reg D_rf_wr, D_dmem_en, D_dmem_wr,
	       output err, D_createdump
	   );

   reg 		      err_decode, halt;

   assign err = err_decode;
   assign D_createdump = halt;

//`include "/u/l/a/langenfe/private/cs552/proj/part1/localparam.inc.v"
//`include "/afs/cs.wisc.edu/u/c/h/chanson/private/cs552/project/part1/localparam.inc.v"
`include "localparam.inc"
   
////////////
// Decode //
////////////
   always @* 
     casex({D_flush_n, D_opcode})
       6'b100000 : begin //HALT
	  D_alu_op_sel = 3'bxxx;
	  D_alu_in1_sel = ZERO;
	  D_alu_in2_sel = BFORMAT_ZEXT;
	  D_rf_wr_sel = 2'bxx;
	  D_rf_wr_data_sel = 2'bxx;
	  D_rf_wr = 0;
	 
	  D_dmem_en = 1'b0;
	  D_dmem_wr = 0;

	  D_pc_next_sel= 2'bxx;
	  halt = 1;
	  err_decode = 0;
       end
       6'b100001:begin //NOP
	  D_alu_op_sel = 3'bxxx;
	  D_alu_in1_sel = ZERO;
	  D_alu_in2_sel = BFORMAT_ZEXT;
	  D_rf_wr_sel = 2'bxx;
	  D_rf_wr_data_sel = 2'bxx;
	  D_rf_wr = 0;
	 
	  D_dmem_en = 1'b0;
	  D_dmem_wr = 0;

	 D_pc_next_sel= NEXT;
	  halt = 0;
	  err_decode = 0;
       end
       6'b100010: begin //siic(NOP)
	  D_alu_op_sel = 3'bxxx;
	  D_alu_in1_sel = ZERO;
	  D_alu_in2_sel = BFORMAT_ZEXT;
	  D_rf_wr_sel = 2'bxx;
	  D_rf_wr_data_sel = 2'bxx;
	  D_rf_wr = 0;
	 
	  D_dmem_en = 1'b0;
	  D_dmem_wr = 0;

	 D_pc_next_sel= NEXT;
	  halt = 0;
	  err_decode = 0;
       end
       6'b100011: begin //NOP/RTI
	  D_alu_op_sel = 3'bxxx;
	  D_alu_in1_sel = ZERO;
	  D_alu_in2_sel = BFORMAT_ZEXT;
	  D_rf_wr_sel = 2'bxx;
	  D_rf_wr_data_sel = 2'bxx;
	  D_rf_wr = 0;
	 
	  D_dmem_en = 1'b0;
	  D_dmem_wr = 0;

	 D_pc_next_sel= NEXT;
	  halt = 0;
	  err_decode = 0;
       end
       6'b100100: begin //J
 	  D_alu_op_sel = 3'bxxx;
	  D_alu_in1_sel = ZERO;
	  D_alu_in2_sel = BFORMAT_ZEXT;
	  D_rf_wr_sel = 2'bxx;
	  D_rf_wr_data_sel = 2'bxx;
	  D_rf_wr = 0;
	  
	  D_dmem_en = 1'b0;
	  D_dmem_wr = 0;

	 D_pc_next_sel= J_IMM;
	  halt = 0;
	  err_decode = 0;
       end
       6'b100101: begin //JR
	  D_alu_op_sel = ADD;
	  D_alu_in1_sel = RF_RD1;
	  D_alu_in2_sel = BFORMAT_SEXT;
	  D_rf_wr_sel = 2'bxx;
	  D_rf_wr_data_sel = 2'bxx;
	  D_rf_wr = 0;
	 
	  D_dmem_en = 1'b0;
	  D_dmem_wr = 0;

	 D_pc_next_sel= REG;
	  halt = 0;
	  err_decode = 0;
       end
       6'b100110: begin //JAL
	  D_alu_op_sel = ADD;
	  D_alu_in1_sel = ZERO;
	  D_alu_in2_sel = PC_OUT;
	  D_rf_wr_sel = R7;
	  D_rf_wr_data_sel = ALUOUT;
	  D_rf_wr = 1;

	  D_dmem_en = 1'b0;
	  D_dmem_wr = 0;

	 D_pc_next_sel= J_IMM;
	  halt = 0;
	  err_decode = 0;
       end
       6'b100111: begin //JALR
	  D_alu_op_sel = ADD;
	  D_alu_in1_sel = RF_RD1;
	  D_alu_in2_sel = BFORMAT_SEXT;
	  D_rf_wr_sel = R7;
	  D_rf_wr_data_sel = PC_INC;
	  D_rf_wr = 1;
	 
	  D_dmem_en = 1'b0;
	  D_dmem_wr = 0;

	  D_pc_next_sel= REG;
	  halt = 0;
	  err_decode = 0;
       end
       6'b101000: begin //ADDI
	  D_alu_op_sel = IFORMAT;
	  D_alu_in1_sel = RF_RD1;
	  D_alu_in2_sel = IFORMAT_SEXT;
	  D_rf_wr_sel = RD_I;
	  D_rf_wr_data_sel = ALUOUT;
	  D_rf_wr = 1;

	  D_dmem_en = 1'b0;
	  D_dmem_wr = 0;

	 D_pc_next_sel= NEXT;
	  halt = 0;
	  err_decode = 0;
       end
       6'b101001: begin //SUBI
	  D_alu_op_sel = IFORMAT;
	  D_alu_in1_sel = RF_RD1;
	  D_alu_in2_sel = IFORMAT_SEXT;
	  D_rf_wr_sel = RD_I;
	  D_rf_wr_data_sel = ALUOUT;
	  D_rf_wr = 1;
	 
	  D_dmem_en = 1'b0;
	  D_dmem_wr = 0;

	 D_pc_next_sel= NEXT;
	  halt = 0;
	  err_decode = 0;
       end
       6'b101010: begin //ORI
	  D_alu_op_sel = IFORMAT;
	  D_alu_in1_sel = RF_RD1;
	  D_alu_in2_sel = IFORMAT_ZEXT;
	  D_rf_wr_sel = RD_I;
	  D_rf_wr_data_sel = ALUOUT;
	  D_rf_wr = 1;
	 
	  D_dmem_en = 1'b0;
	  D_dmem_wr = 0;

	 D_pc_next_sel= NEXT;
	  halt = 0;
	  err_decode = 0;
       end
       6'b101011: begin //ANDI
	  D_alu_op_sel = IFORMAT;
	  D_alu_in1_sel = RF_RD1;
	  D_alu_in2_sel = IFORMAT_ZEXT;
	  D_rf_wr_sel = RD_I;
	  D_rf_wr_data_sel = ALUOUT;
	  D_rf_wr = 1;
	 
	  D_dmem_en = 1'b0;
	  D_dmem_wr = 0;

	 D_pc_next_sel= NEXT;
	  halt = 0;
	  err_decode = 0;
       end
       6'b101100: begin //BEQZ
	  D_alu_op_sel = 3'bxxx;
	  D_alu_in1_sel = ZERO;
	  D_alu_in2_sel = BFORMAT_ZEXT;
	  D_rf_wr_sel = 2'bxx;
	  D_rf_wr_data_sel = 2'bxx;
	  D_rf_wr = 0;
	 
	  D_dmem_en = 1'b0;
	  D_dmem_wr = 0;

	 D_pc_next_sel= D_rs_zero ? B_IMM : NEXT;
	  halt = 0;
	  err_decode = 0;
       end
       6'b101101: begin //BNEZ
	  D_alu_op_sel = 3'bxxx;
	  D_alu_in1_sel = ZERO;
	  D_alu_in2_sel = BFORMAT_ZEXT;
	  D_rf_wr_sel = 2'bxx;
	  D_rf_wr_data_sel = 2'bxx;
	  D_rf_wr = 0;
	 
	  D_dmem_en = 1'b0;
	  D_dmem_wr = 0;
	  
	 D_pc_next_sel= (~D_rs_zero ? B_IMM : NEXT);
	  halt = 0;
	  err_decode = 0;
       end
       6'b101110: begin //RET
	  D_alu_op_sel = ADD;
	  D_alu_in1_sel = ZERO;
	  D_alu_in2_sel = RF_R7;
	  D_rf_wr_sel = 2'bxx;
	  D_rf_wr_data_sel = 2'bxx;
	  D_rf_wr = 0;
	 
	  D_dmem_en = 1'b0;
	  D_dmem_wr = 0;

	 D_pc_next_sel= REG;
	  halt = 0;
	  err_decode = 0;
       end
       6'b101111: begin //?
	  D_alu_op_sel = 3'bxxx;
	  D_alu_in1_sel = ZERO;
	  D_alu_in2_sel = BFORMAT_ZEXT;
	  D_rf_wr_sel = 2'bxx;
	  D_rf_wr_data_sel = 2'bxx;
	  D_rf_wr = 0;
	 
	  D_dmem_en = 1'b0;
	  D_dmem_wr = 0;

	 D_pc_next_sel= NEXT;
	  halt = 0;
	  err_decode = 0;
       end
       6'b110000: begin //ST
      	  D_alu_op_sel = ADD;
	  D_alu_in1_sel = RF_RD1;
	  D_alu_in2_sel = IFORMAT_SEXT;
	  D_rf_wr_sel = 2'bxx;
	  D_rf_wr_data_sel = 2'bxx;
	  D_rf_wr = 0;
	  
	  D_dmem_en = 1;
	  D_dmem_wr = 1;
	  
	 D_pc_next_sel= NEXT;
	  halt = 0;
	  err_decode = 0;
       end
       6'b110001: begin //LD
	  D_alu_op_sel = ADD;
	  D_alu_in1_sel = RF_RD1;
	  D_alu_in2_sel = IFORMAT_SEXT;
	  D_rf_wr_sel = RD_I;
	  D_rf_wr_data_sel = DMEMOUT;
	  D_rf_wr = 1;
	  
	  D_dmem_en = 1;
	  D_dmem_wr = 0;
	  
	 D_pc_next_sel= NEXT;
	  halt = 0;
	  err_decode = 0;
       end
       6'b110010: begin //SLBI
	  D_alu_op_sel = OR;
	  D_alu_in1_sel = RF_RD1_LSL;
	  D_alu_in2_sel = BFORMAT_ZEXT;
	  D_rf_wr_sel = RS;
	  D_rf_wr_data_sel = ALUOUT;
	  D_rf_wr = 1;
	 
	  D_dmem_en = 1'b0;
	  D_dmem_wr = 0;

	 D_pc_next_sel= NEXT;
	  halt = 0;
	  err_decode = 0;
       end
       6'b110011: begin //STU
       	  D_alu_op_sel = ADD;
	  D_alu_in1_sel = RF_RD1;
	  D_alu_in2_sel = IFORMAT_SEXT;
	  D_rf_wr_sel = RS;
	  D_rf_wr_data_sel = ALUOUT;
	  D_rf_wr = 1;
	  
	  D_dmem_en = 1;
	  D_dmem_wr = 1;
	  
	 D_pc_next_sel= NEXT;
	  halt = 0;
	  err_decode = 0;
       end
       6'b110100: begin //ROLI
	  D_alu_op_sel = IFORMAT;
	  D_alu_in1_sel = RF_RD1;
	  D_alu_in2_sel = SHIFT_IMM;
	  D_rf_wr_sel = RD_I;
	  D_rf_wr_data_sel = ALUOUT;
	  D_rf_wr = 1;
	 
	  D_dmem_en = 1'b0;
	  D_dmem_wr = 0;

	 D_pc_next_sel= NEXT;
	  halt = 0;
	  err_decode = 0;
       end
       6'b110101: begin //SLLI
	  D_alu_op_sel = IFORMAT;
	  D_alu_in1_sel = RF_RD1;
	  D_alu_in2_sel = SHIFT_IMM;
	  D_rf_wr_sel = RD_I;
	  D_rf_wr_data_sel = ALUOUT;
	  D_rf_wr = 1;
	 
	  D_dmem_en = 1'b0;
	  D_dmem_wr = 0;

	 D_pc_next_sel= NEXT;
	  halt = 0;
	  err_decode = 0;
       end
       6'b110110: begin //RORI
	  D_alu_op_sel = IFORMAT;
	  D_alu_in1_sel = RF_RD1;
	  D_alu_in2_sel = SHIFT_IMM;
	  D_rf_wr_sel = RD_I;
	  D_rf_wr_data_sel = ALUOUT;
	  D_rf_wr = 1;
	 
	  D_dmem_en = 1'b0;
	  D_dmem_wr = 0;

	 D_pc_next_sel= NEXT;
	  halt = 0;
	  err_decode = 0;
       end
       6'b110111: begin //SRAI
	  D_alu_op_sel = IFORMAT;
	  D_alu_in1_sel = RF_RD1;
	  D_alu_in2_sel = SHIFT_IMM;
	  D_rf_wr_sel = RD_I;
	  D_rf_wr_data_sel = ALUOUT;
	  D_rf_wr = 1;
	 
	  D_dmem_en = 1'b0;
	  D_dmem_wr = 0;

	 D_pc_next_sel= NEXT;
	  halt = 0;
	  err_decode = 0;
       end
       6'b111000: begin //LBI
	  D_alu_op_sel = OR;
	  D_alu_in1_sel = ZERO;
	  D_alu_in2_sel = BFORMAT_SEXT;
	  D_rf_wr_sel = RS;
	  D_rf_wr_data_sel = ALUOUT;
	  D_rf_wr = 1;
	 
	  D_dmem_en = 1'b0;
	  D_dmem_wr = 0;

	 D_pc_next_sel= NEXT;
	  halt = 0;
	  err_decode = 0;
       end
       6'b111001: begin //
	  D_alu_op_sel = 3'bxxx;
	  D_alu_in1_sel = ZERO;
	  D_alu_in2_sel = BFORMAT_ZEXT;
	  D_rf_wr_sel = 2'bxx;
	  D_rf_wr_data_sel = 2'bxx;
	  D_rf_wr = 0;
	 
	  D_dmem_en = 1'b0;
	  D_dmem_wr = 0;

	 D_pc_next_sel= NEXT;
	  halt = 0;
	  err_decode = 0;
       end
       6'b111010: begin //ALU EX
	  D_alu_op_sel = RFORMAT;
	  D_alu_in1_sel = RF_RD1;
	  D_alu_in2_sel = RF_RD2;
	  D_rf_wr_sel = RD_R;
	  D_rf_wr_data_sel = ALUOUT;
	  D_rf_wr = 1;
	 
	  D_dmem_en = 1'b0;
	  D_dmem_wr = 0;

	  D_pc_next_sel= NEXT;
	  halt = 0;
	  err_decode = 0;
       end
       6'b111011: begin //ALU EX
	  D_alu_op_sel = RFORMAT;
	  D_alu_in1_sel = RF_RD1;
	  D_alu_in2_sel = RF_RD2;
	  D_rf_wr_sel = RD_R;
	  D_rf_wr_data_sel = ALUOUT;
	  D_rf_wr = 1;
	 
	  D_dmem_en = 1'b0;
	  D_dmem_wr = 0;

	  D_pc_next_sel= NEXT;
	  halt = 0;
	  err_decode = 0;
       end
       6'b111100: begin //SEQ
	  D_alu_op_sel = SUB;
	  D_alu_in1_sel = RF_RD1;
	  D_alu_in2_sel = RF_RD2;
	  D_rf_wr_sel = RD_R;
	  D_rf_wr_data_sel = SET;
	  D_rf_wr = 1;
	  
	  D_dmem_en = 1'b0;
	  D_dmem_wr = 0;
	  
	  D_pc_next_sel= NEXT;
	  halt = 0;
	  err_decode = 0;
       end
       6'b111101: begin //SLT
	  D_alu_op_sel = SUB;
	  D_alu_in1_sel = RF_RD1;
	  D_alu_in2_sel = RF_RD2;
	  D_rf_wr_sel = RD_R;
	  D_rf_wr_data_sel = SET;
	  D_rf_wr = 1;
	  
	  D_dmem_en = 1'b0;
	  D_dmem_wr = 0;
	  
	  D_pc_next_sel= NEXT;
	  halt = 0;
	  err_decode = 0;
       end
       6'b111110: begin //SLE
	  D_alu_op_sel = SUB;
	  D_alu_in1_sel = RF_RD1;
	  D_alu_in2_sel = RF_RD2;
	  D_rf_wr_sel = RD_R;
	  D_rf_wr_data_sel = SET;
	  D_rf_wr = 1;
	  
	  D_dmem_en = 1'b0;
	  D_dmem_wr = 0;
	  
	  D_pc_next_sel= NEXT;
	  halt = 0;
	  err_decode = 0;
       end
       6'b111111: begin //SCO
          D_alu_op_sel = ADD;
	  D_alu_in1_sel = RF_RD1;
	  D_alu_in2_sel = RF_RD2;
	  D_rf_wr_sel = RD_R;
	  D_rf_wr_data_sel = SET;
	  D_rf_wr = 1;
	  
	  D_dmem_en = 1'b0;
	  D_dmem_wr = 0;
	  
	  D_pc_next_sel= NEXT;
	  halt = 0;
	  err_decode = 0;
       end
       6'b0?????:begin //flush instr
	  D_alu_op_sel = 3'bxxx;
	  D_alu_in1_sel = ZERO;
	  D_alu_in2_sel = BFORMAT_ZEXT;
	  D_rf_wr_sel = 2'bxx;
	  D_rf_wr_data_sel = 2'bxx;
	  D_rf_wr = 0;
	 
	  D_dmem_en = 1'b0;
	  D_dmem_wr = 0;

	  D_pc_next_sel= NEXT;
	  halt = 0;
	  err_decode = 0;
       end
       default: begin
	  D_alu_op_sel = 3'bxxx;
	  D_alu_in1_sel = ZERO;
	  D_alu_in2_sel = BFORMAT_ZEXT;
	  D_rf_wr_sel = 2'bxx;
	  D_rf_wr_data_sel = 2'bxx;
	  D_rf_wr = 0;
	 
	  D_dmem_en = 1'b0;
	  D_dmem_wr = 0;

	  D_pc_next_sel= NEXT;
	  halt = 0;
	  err_decode = 1;
       end
       
     endcase // case (opcode)
endmodule
