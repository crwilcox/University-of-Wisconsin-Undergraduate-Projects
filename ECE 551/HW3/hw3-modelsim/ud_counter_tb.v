module ud_counter_tb();
	// This is a testbench for the ud_counter module
   
	//This testbench demonstrates correct performance of ud_counter. 
	//A $monitor statement is used to print output. 
   
	reg clk, rst_count, load_en, mode;
	reg [2:0] upper_limit;
	wire [2:0] count;
	
	ud_counter DUT(.clk(clk), 
					.rst_count(rst_count), 
					.load_en(load_en), 
					.mode(mode), 
					.upper_limit(upper_limit), 
					.count(count));
				
//Establish a clk				
always 
 #5  clk =  ! clk;

always @(posedge clk)
	$monitor("SimTime: %-4d Count: %-3d Mode: %-1d CountLimit: %-3d UpperLimit: %-3d LoadEn: %-1d RstCnt: %-1d", $time, count, mode, DUT.count_limit, upper_limit, load_en, rst_count);
 
initial 
begin 
	$display("Initializing Testbench");
	clk = 1'b0; 
	rst_count = 1'b1;
	load_en = 1'b1;
	mode = 1'b0;
	upper_limit = 3'b111;
	
	#10
	$display("release reset and load enable");
	rst_count = 1'b0; 
	load_en = 1'b0;
	
	//let it count
	#120
	
	$display("Change the upper limit to something below where count is while it is counting up");
	upper_limit = 3'b011;
	load_en = 1'b1;
	#10
	load_en = 1'b0;
	
	//let it count	
	#30
	
	$display("Change the mode to count down");
	mode = 1'b1;
	
	//let it count
	#30
	
	$display("Change the upper limit");
	upper_limit = 3'b110;
	#20
	$display("Enable the new limit");
	load_en = 1'b1;
	#10
	load_en = 1'b0;
	
	//let it count	
	#40
	
	$display("Assert rst_count");
	rst_count = 1'b1;
	#10
	rst_count = 1'b0;
	
	//let it count	
	#40
	
	$stop;
end 

endmodule