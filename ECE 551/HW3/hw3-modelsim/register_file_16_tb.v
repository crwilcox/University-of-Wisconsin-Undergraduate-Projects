module register_file_16_tb();
   // This is a testbench for the register_file_16 module.
   // It does not exhaustively test, but tests writing, reading, 
   // and resetting of each register
	
	reg clk;
	reg rst;
	reg wr_en_1, wr_en_2;
	
	reg [7:0] data_in_1, data_in_2;
	reg [2:0] wr_addr_1, wr_addr_2;
	reg [3:0] rd_addr;
	wire [7:0] data_out;
	
	integer i;
	
	register_file_16 DUT(.clk(clk), 
						.wr_en_1(wr_en_1), 
						.wr_en_2(wr_en_2), 
						.rst(rst), 
						.data_in_1(data_in_1), 
						.data_in_2(data_in_2), 
						.wr_addr_1(wr_addr_1), 
						.wr_addr_2(wr_addr_2),
						.rd_addr(rd_addr), 
						.data_out(data_out)
						);
				
//Establish a clk				
always 
 #5  clk =  ! clk;
	
initial 
begin 
	$display("Initializing Testbench");
	clk = 1'b0; 
	rst = 1'b0;
	wr_en_1 = 1'b0;
	wr_en_2 = 1'b0;
	data_in_1 = 8'b0000_0000;
	data_in_2 = 8'b0000_0000;
	wr_addr_1 = 3'b000;
	wr_addr_2 = 3'b000;
	rd_addr = 4'b0000;
	
	#5
	//release reset
	rst = 1'b1; 
	
	$display("TESTING: Writing in data to registers 0 through 7");
	#5
	
	data_in_1 = 8'b0000_0001;
	wr_addr_1 = 3'b000;
	wr_en_1 = 1'b1;
	for(i=0; i<8; i=i+1) begin	
		#10
		$display("    Write to reg%d: Expect: %b", i, data_in_1);
		data_in_1 = data_in_1 << 1; //shift this by 1
		wr_addr_1 = wr_addr_1 + 1;
	end
	wr_en_1 = 1'b0;
	
	$display("TESTING: Writing in data to registers 8 through 15");
	data_in_2 = 8'b1111_1111;
	wr_addr_2 = 3'b000;
	wr_en_2 = 1'b1;
	for(i=0; i<8; i=i+1) begin
		#10
		$display("    Write to reg%d: Expect: %b", i, data_in_2);
		data_in_2 = data_in_2 << 1; //shift this by 1
		wr_addr_2 = wr_addr_2 + 1;
	end
	wr_en_2 = 1'b0;
	
	$display("TESTING: Verifying data was written to registers and can be read on the out");
	rd_addr = 4'b0000;
	for(i=0; i<16; i = i +1) begin
		#4
		$display("    Reading reg%d:  Result: %b", rd_addr, data_out);
		rd_addr = rd_addr + 1;
	end
	
	$display("TESTING: Verifying that data cannot be written without the enable being held");
	#10
	data_in_1 = 8'b0001_0001;
	wr_addr_1 = 3'b001;
	wr_en_1 = 1'b0;
	$display("   Attempt to Write to reg0: 8'b0001_0001 with enable off");
	rd_addr = 4'b0001;
	$strobe("       Expect reg0 contains: 8'b0000_0010  Result: %b", data_out);
	
	#10
	data_in_2 = 8'b0001_0001;
	wr_addr_2 = 3'b000;
	wr_en_2 = 1'b0;
	$display("   Attempt to Write to reg0: 8'b0001_0001 with enable off");
	rd_addr = 4'b1000;
	$strobe("       Expect reg8 contains: 8'b1111_1111  Result: %b", data_out);
	
	#10
	$display("TESTING: Verifying that rst is asynchronous and that it clears all entries");
	#10
	#2
	rst = 1'b0;
	#2
	rst = 1'b1;
	
	#10
	rd_addr = 4'b0000;
	for(i=0; i<16; i = i +1) begin
		#4
		$display("    Reading reg%d: Expecting: 00000000 Result: %b", i, data_out);
		rd_addr = rd_addr + 1;
	end
	
	#20
	$stop;
end 

endmodule