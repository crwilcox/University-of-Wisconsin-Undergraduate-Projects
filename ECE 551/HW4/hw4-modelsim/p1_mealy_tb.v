module p1_mealy_tb();

	reg A, clk, rst_n;
	wire Y;

	p1_mealy DUT (.A(A), .clk(clk), .rst_n(rst_n), .Y(Y));
always #5 clk = ~clk;

initial begin

	//initialize
	clk = 1'b1;
	rst_n = 1'b0;
	A = 1'b0;

	#10
	rst_n = 1'b1; 

	#10
	//A stays 0
	
	#10
	//A stays 0

	#10
	A = 1;
	
	#10
	A = 0; // Y should go high...

	#10
	A = 1; // will go to s_recov

	#10
	A = 1; // it doesnt matter if this is 0 or 1 takes to s_exec and outs 1

	#10
	A = 1; //sends us to s_rst

	#10 
	A = 1; //sends us to s_exec and then outs 1

	#10 
	A = 0;// sends us to s_error

	#20
	$stop;
end


endmodule
