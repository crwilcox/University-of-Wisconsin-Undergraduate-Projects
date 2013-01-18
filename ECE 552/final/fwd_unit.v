/*DEFINITION OF fwd_code

one hot encoding signifies forwarding
bit0 = fwd W to alu in 2
bit1 = fwd M to alu in 2
bit2 = fwd W to alu in 1
bit3 = fwd M to alu in 1

{3,2,1,0}
*/


module fwd_unit(input [3:0] fwd_code,output reg [1:0] fwd1_sel,fwd2_sel );

`include "localparam.inc"

	always @* casex (fwd_code[3:2]) 
	//fwd1_sel	
		2'b00: fwd1_sel = IDLATCH;	
		2'b01: fwd1_sel = W;	
		2'b10: fwd1_sel = M;	
		2'b11: fwd1_sel = M;	
		default:fwd1_sel = 2'bXX;
	endcase

	always @* casex (fwd_code[1:0]) 
	//fwd2_sel	
		2'b00: fwd2_sel = IDLATCH;	
		2'b01: fwd2_sel = W;	
		2'b10: fwd2_sel = M;	
		2'b11: fwd2_sel = M;	
		default:fwd2_sel = 2'bXX;
	endcase

endmodule
