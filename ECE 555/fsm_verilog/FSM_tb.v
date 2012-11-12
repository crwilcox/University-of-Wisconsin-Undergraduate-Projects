module FSM_tb();


	reg clk,rst,start;
	wire error;
	wire [4:0] wr_en1,wr_en2,rd_enA1,rd_enA2,rd_enB1,rd_enB2;
	wire left_right1,left_right2,ppgen_en1,ppgen_en2,add_en1,add_en2,shift_en1,shift_en2,done1,done2;


	FSM_behavioral DUT1(.CLK(clk),.rst(rst),.start(start),.done(done1),
				.left_right(left_right1),.ppgen_en(ppgen_en1),
				.add_en(add_en1),.shift_en(shift_en1),.wr_en(wr_en1),
				.rd_enA(rd_enA1),.rd_enB(rd_enB1));

	/*
	this comment is needed because Verilog-XL is dumb

	wren2[4:0] = {Q4,Q3orQ6,Q5,Q2orQ7,Q1orQ8}		
	left_right2 = Q7;
	ppgen_en2 = Q5;
	shift_en2 = Q7orQ8;
	add_en2 = Q6;
	done2 = Q9;
	rd_enA2[4:0]= {Q7orQ8,Q9,Q6,1'b0,Q5};
	rd_enB2[4:0]= {1'b0,Q6,1'b0,Q5orQ7,Q8};
	*/
	fsm	DUT2(.clk(clk),.rst(rst),.start(start),
			.Q7(Q7),.Q6(Q6),.Q9(Q9),
			.Q7orQ8(Q7orQ8),.Q8(Q8),.Q5orQ7(Q5orQ7),
			.wren0(wren0),.Q2orQ7(Q2orQ7),.Q5(Q5),
			.Q3orQ6(Q3orQ6),.Q4(Q4));
	
	assign	wr_en2[4:0] = {Q4,Q3orQ6,Q5,Q2orQ7,wren0};	
	assign	left_right2 = Q7;
	assign	ppgen_en2 = Q5;
	assign	shift_en2 = Q7orQ8;
	assign	add_en2 = Q6;
	assign	done2 = Q9;
	assign	rd_enA2[4:0]= {Q7orQ8,Q9,Q6,1'b0,Q5};
	assign	rd_enB2[4:0]= {1'b0,Q6,1'b0,Q5orQ7,Q8};
	


	initial begin
		clk = 1'b0;
		forever #5 clk = ~clk;
	end


	initial begin
		rst = 1'b1;
		start = 1'b0;

		#20 rst = 1'b0;
		//assert start, let it do it's thing, watch for done.
	
		#20 start = 1'b1;

		//FSM does it's thing!

		@(posedge done1) #20 start=1'b0; //end of first cycle, directly into state 1 from 9
		@(posedge done1) #20 $stop; //end of second cycle, state 9 to state 0
	end

	assign error =left_right1 ^ left_right2 ||
			ppgen_en1 ^ ppgen_en2 ||
			add_en1 ^ add_en2 ||
			shift_en1 ^ shift_en2 ||
			done1 ^ done2 ||
			|(wr_en1 ^ wr_en2) ||
			|(rd_enB1 ^ rd_enB2) ||
			|(rd_enA1 ^ rd_enA2);		
	

endmodule
