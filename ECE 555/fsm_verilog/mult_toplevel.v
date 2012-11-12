

`timescale 1ns / 1ns 

module mult_toplevel(output [31:0] A,B,output done, input [31:0] Z, input clk,rst,start);


	wire [4:0] rd_enA,rd_enB,wr_en;

	
	fsm FSM(.Q7(Q7),.Q6(Q6),.Q7orQ8(Q7orQ8),.Q8(Q8),.Q9(Q9),.Q5orQ7(Q5orQ7),.wren0(wren0),
			.Q2orQ7(Q2orQ7),.Q5(Q5),.Q3orQ6(Q3orQ6),.Q4(Q4),.clk(clk),
			.rst(rst),.start(start));

	assign	wr_en = {Q4,Q3orQ6,Q5,Q2orQ7,wren0};	
	assign	left_right = Q7;
	assign	ppgen_en = Q5;
	assign	shift_en = Q7orQ8;
	assign	add_en = Q6;
	assign	done = Q9;
	assign	rd_enA= {Q7orQ8,Q9,Q6,1'b0,Q5};
	assign	rd_enB= {1'b0,Q6,1'b0,Q5orQ7,Q8};
	
	ppgen_32 PPGEN(.Z(Z),.A(A),.B(B[0]),.ppgen_en(ppgen_en));

	RCA_32 RCA(.Z(Z),.a(A),.b(B),.add_en(add_en));

	shifter SHIFTER(.Z(Z),.A(A),.B(B),.left_right(left_right),.shift_en(shift_en));
	
	regfile RF(.a(A),.b(B),.clk(clk),.Z(Z),.rd_enA(rd_enA),.rd_enB(rd_enB),.wr_en(wr_en));




endmodule
