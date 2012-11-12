module partial_counter_tb();
   // This is a testbench for the partial_counter module.
   // It allows the counter to run through its cycles, then asserts reset, and allowas it to run for a little longer
	
	reg clk;
	reg rst;
	wire [3:0] count;

   partial_counter DUT(
				.clk(clk),
				.rst(rst),
				.count(count[3:0])
				);
				
//Establish a clk				
always 
 #5  clk =  ! clk;
	
always @(posedge clk)
	$monitor("Simulation Time: %-4d Count: %-4d Current State: %-4d Reset: %-4d", $time, count, DUT.state, rst);
	
initial 
begin 
	clk = 1'b0; 
	rst = 1'b1;
	#10
	
	//release reset
	rst = 1'b0; 
	
	#120
	
	#5
	rst = 1'b1;
	#5
	rst = 1'b0;
	
	#60
	
	$stop;
end 
    

	
endmodule