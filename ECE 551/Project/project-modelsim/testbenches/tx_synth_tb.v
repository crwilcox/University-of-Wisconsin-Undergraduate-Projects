//Team 12 (The Fighting Mongooses)
//Figure 1
//TX Testbench

`timescale 1ns/10ps
module tx_tb();

	//this is the testbench for the tx module independently
	reg clk, Rst_n, TxData_Valid;
	reg[31:0] TxData;
   	reg	Error_Ack, Rx_Ready, Rx_Error;
	wire Tx_Ready, Tx_Error, S_Data;
    wire Tx_Ready_net, Tx_Error_net, S_Data_net;

	tx DUT (
			.Clk_s(clk), .Rst_n(Rst_n), .TxData_Valid(TxData_Valid), .TxData(TxData), .Error_Ack(Error_Ack), .Rx_Ready(Rx_Ready), .Rx_Error(Rx_Error),  //INPUTS
			.Tx_Ready(Tx_Ready), .Tx_Error(Tx_Error), .S_Data(S_Data) //OUTPUTS
		   );
									
    tx_synth netDUT (
		            .Clk_s(clk), .Rst_n(Rst_n), .TxData_Valid(TxData_Valid), .TxData(TxData), .Error_Ack(Error_Ack), .Rx_Ready(Rx_Ready), .Rx_Error(Rx_Error),  //INPUTS
		            .Tx_Ready(Tx_Ready_net), .Tx_Error(Tx_Error_net), .S_Data(S_Data_net) //OUTPUTS
		           );

     reg Tx_Ready_Error, Tx_Error_Error, S_Data_Error, Global_Error;     
     always @ (negedge clk) begin
     	Tx_Ready_Error = (Tx_Ready != Tx_Ready_net);
     	Tx_Error_Error = (Tx_Error != Tx_Error_net);
     	S_Data_Error = (S_Data != S_Data_net);
 	 	Global_Error = (Tx_Ready_Error || Tx_Error_Error || S_Data_Error);
 	 end

	reg failedTestCase = 1'b0;
	/*
	reg nextErr = 1'b0;
	always @(posedge clk) nextErr = failedTestCase ? 0 : failedTestCase;
	always @(posedge clk) failedTestCase = nextErr;
	*/
	//establish a clk
	always #5 clk = !clk;

	reg[5:0] count_1 = 6'd0;
	initial begin
	//start by giving tx a few cycles to 'warm up'
	//assert and deassert reset
		clk = 1'b0; 
	   	Rst_n = 1'b0;
		TxData_Valid = 1'b0;
		Error_Ack = 1'b0;
		Rx_Ready = 1'b0;
		Rx_Error = 1'b0;
		#10
		
		//release reset
		Rst_n = 1'b1; 	
	
	//now lets have some fun.  We will send a signal from the tb core and
	//watch it send the message.  We will watch each bit and confirm that it
	//is the expected operation...
		Rx_Ready = 1'b1;
		@(negedge clk) TxData = 32'b_1111_0010_0101_0101_1100_1100_1100_1100;
		@(negedge clk) TxData_Valid = 1'b1;//We have now told the Tx Module it can go to town...  If Rx is ready it will go...
			
		//Here we should see Tx_Ready Drop //40ns
		//if(Tx_Ready != 1'b0) failedTestCase = 1'b1;
		@(negedge Tx_Ready) 
		//hold valid a little after to make sure synth version sees it
		@(posedge clk) @(posedge clk)
		TxData_Valid = 1'b0;//when Tx_Ready goes low, TxData_Valid can go down as well...

		//We should now see the signal come as 00000
		@(negedge S_Data) //when S_Data goes low, the start sequence should start
		for(count_1 = 6'd0; count_1 < 6'd5; count_1 = count_1 + 1'b1) begin
			
			@(negedge clk)if(S_Data != 1'b0) failedTestCase = 1	'b1;
		end
		Rx_Ready = 1'b0;//simulate the fact that Rx would have seen the start sequence
		//Then as 1111 1111 0101 etc.
		for(count_1 = 6'd32; count_1 > 0; count_1 = count_1 - 1'b1) begin
			
			@(negedge clk) if(S_Data != TxData[count_1-1'b1]) failedTestCase = 1'b1;
			
		end
		
		@(negedge clk) if(S_Data != ~^(TxData)) failedTestCase = 1'b1;

		//Tx_Ready should go high as soon as the data is transmitted
		//(Regardless of error)
		@(negedge clk) if(Tx_Ready != 1'b1)  failedTestCase = 1'b1;

		//After the full signal transmits, the Rx_Error line will stay low or
		//go high depending on if there was an error, for the purpose of this
			//tb, we will assert the error signal
		@(posedge clk)
		Rx_Error = 1'b1;
		Rx_Ready = 1'b1; //Set High, as Rx is done with that packet.

		@(posedge clk)
		Rx_Error = 1'b0; // Deassert after a cycle
		//we should now see the Tx_Error go high a cycle later, and then see
		//the Error_Ack Signal go high, followed by the Tx_Error signal
		//going low
		@(posedge clk)
		if(Tx_Error != 1'b1) failedTestCase = 1'b1;
		Error_Ack = 1'b1;
		@(posedge clk)
		#1
		if(Tx_Error != 1'b0) failedTestCase = 1'b1;
		Error_Ack = 1'b0;

		//hold for a bit so we see everything
		#20

		$stop;
	end
endmodule
