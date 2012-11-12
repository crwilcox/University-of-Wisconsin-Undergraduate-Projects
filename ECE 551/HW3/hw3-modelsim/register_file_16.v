module register_file_16(
		input clk, //Clock signal
		input wr_en_1, //Active-high write enable for WR_ADDR_1
		input wr_en_2, //Active-high write enable for WR_ADDR_2
		input rst, //Asynchronous active-low reset (clears entire file)
		input [7:0] data_in_1, //Input data for WR_ADDR_1
		input [7:0] data_in_2, //Input data for WR_ADDR_2
		input [2:0] wr_addr_1, //Write Address 1 can write to entries 0 ~ 7 of reg file
		input [2:0] wr_addr_2, //Write Address 2 can write to entries 8 ~ 15 of reg file
		input [3:0] rd_addr, //Read address can read entries 0 ~ 15
		output reg [7:0] data_out //Output data from RD_ADDR.
		);
	//Implements a register file consisting of 16 entries with an 
	//8-bit register size, a single read port, and dual write ports.
	//The register’s read function should be asynchronous. Data_out 
	//changes after rd_addr is modified, without waiting for a clock edge.
	
	reg [7:0] registers [0:15];//16, 8-bit registers
	
	integer i;
	//A block to write entries
	always @(posedge clk, negedge rst) 
	begin
		if(!rst) begin
			//reset registers 0-15
			for(i=0; i<16; i = i+1) begin
				registers[i] <= 8'b0000_0000;
			end
		end
		else
		begin
			//writing to registers 0-7 controlled bye wr_en_1 and wr_addr_1
			if(wr_en_1) registers[wr_addr_1] <= data_in_1;	
			
			//writing to registers 8-15 controlled bye wr_en_2 and wr_addr_2
			if(wr_en_2) registers[wr_addr_2 + 8] <= data_in_2;
		end
	end

	//A block to read entries 0 ~ 15
	//this block is triggered by rd_addr as it should be updated when rd_addr changes
	//it is also triggered by posedge clock, so it is updated when data changes
	always @(rd_addr, posedge clk)
	begin
		data_out <= registers[rd_addr];
	end
	
endmodule // register_file_16