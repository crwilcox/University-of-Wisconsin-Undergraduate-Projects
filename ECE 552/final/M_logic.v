module M_logic(
		input [15:0] M_rf_rd2,M_alu_out,
		input M_dmem_en,clk,rst,W_createdump,M_dmem_wr,
		output [15:0] M_dmem_out,
		output M_dmem_align_err,M_alu_out_zero,M_dmem_stall,M_dmem_done
		);

	assign M_alu_out_zero = ~|M_alu_out;
   assign M_dmem_align_err = M_alu_out[0] & M_dmem_en;
      
   mem_system #(.mem_type(1)) //param for dmem 
		dmem (.DataIn(M_rf_rd2),.Addr(M_alu_out),.Rd(M_dmem_en&~M_alu_out[0]),
			.Wr(M_dmem_wr),.clk(clk),.createdump(W_createdump),
			.rst(rst),.DataOut(M_dmem_out),.err(),
			.Stall(M_dmem_stall),.Done(M_dmem_done),.CacheHit(M_cache_hit));

endmodule
