module set(input [1:0] opcode,
	   input X_alu_zero, X_alu_ofl, X_alu_cout, X_alu_neg,
	   output reg X_set, err);
   
   
   always@*
     casex (opcode[1:0])
       2'b00: begin
	  X_set =X_alu_zero;
	  err = 0;	  
       end
       2'b01: begin
	  X_set = ~(X_alu_zero| X_alu_neg^X_alu_ofl);
	  err = 0;	  
       end
       2'b10: begin
	  X_set = ~X_alu_neg^X_alu_ofl; 
	  err = 0;	  
       end	      
       2'b11: begin
	  X_set =X_alu_cout;
	  err = 0;	  
       end
       default: begin
	  X_set = 1'bx;
	  err = 0;
       end
     endcase // case (opcode[1:0])
endmodule
	   
