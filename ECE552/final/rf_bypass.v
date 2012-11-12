module rf_bypass(
		input [15:0] writedata,
		output [15:0] read1data, read2data,
		input [2:0] read1regsel,read2regsel,writeregsel,
		input write,clk,rst,
		output err);

		wire [15:0] read1out,read2out;	
	
	rf regfile (.read1data(read1out),.read2data(read2out),.read1regsel(read1regsel),.read2regsel(read2regsel),
			.writeregsel(writeregsel),.writedata(writedata),.write(write),.clk(clk),.rst(rst),.err(err));	


		
	//case of when read1regsel is the same as writeregsel
	assign read1data = (writeregsel == read1regsel) & write ? writedata : read1out;


	//case of when read2regsel is the same as writeregsel

	assign read2data = (writeregsel == read2regsel) & write ? writedata : read2out;



endmodule
