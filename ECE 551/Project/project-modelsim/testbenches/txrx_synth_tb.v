module txrx_tb();

	//this is the testbench for the tx module independently
	reg clk, Rst_n, TxData_Valid;
	reg[31:0] TxData;
   	reg	Error_Ack;
   	wire Rx_Ready_synth, Rx_Error_synth;
   	wire Rx_Ready, Rx_Error;
	wire Tx_Ready_synth, Tx_Error_synth, S_Data_synth;
	wire Tx_Ready, Tx_Error, S_Data;
	
		// RX/Core Wires
	   reg Core_Rcv_Ready = 1'b0;
	   wire RxData_Valid;
	   wire [31:0] RxData;
	   wire RxData_Valid_synth;
	   wire [31:0] RxData_synth;
	tx DUT (
			.Clk_s(clk), .Rst_n(Rst_n), .TxData_Valid(TxData_Valid), .TxData(TxData), .Error_Ack(Error_Ack), .Rx_Ready(Rx_Ready), .Rx_Error(Rx_Error),  //INPUTS
			.Tx_Ready(Tx_Ready), .Tx_Error(Tx_Error), .S_Data(S_Data) //OUTPUTS
		   );
	tx_synth txNetDUT (
			.Clk_s(clk), .Rst_n(Rst_n), .TxData_Valid(TxData_Valid), .TxData(TxData), .Error_Ack(Error_Ack), .Rx_Ready(Rx_Ready), .Rx_Error(Rx_Error),  //INPUTS
			.Tx_Ready(Tx_Ready_synth), .Tx_Error(Tx_Error_synth), .S_Data(S_Data_synth) //OUTPUTS
		   );
	rx DUTrx (
	      .Clk_s(clk), .Rst_n(Rst_n), .Core_Rcv_Ready(Core_Rcv_Ready), .S_Data(S_Data), //Inputs
         .RxData_Valid(RxData_Valid), .RxData(RxData), .Rx_Ready(Rx_Ready), .Rx_Error(Rx_Error) //Outputs
         );
	rx_synth DUTrxNet (
	      .Clk_s(clk), .Rst_n(Rst_n), .Core_Rcv_Ready(Core_Rcv_Ready), .S_Data(S_Data), //Inputs
         .RxData_Valid(RxData_Valid_synth), .RxData(RxData_synth), .Rx_Ready(Rx_Ready_synth), .Rx_Error(Rx_Error_synth) //Outputs
         );
								
	reg Rx_Ready_Error, Rx_Error_Error, RxData_Error, RxData_Valid_Error, Tx_Ready_Error, Tx_Error_Error, S_Data_Error;
	reg Synth_Error;
	always @ (negedge clk) begin
		Rx_Ready_Error = (Rx_Ready != Rx_Ready_synth);
		Rx_Error_Error = (Rx_Error != Rx_Error_synth);
		RxData_Error = (RxData != RxData_synth);
		RxData_Valid_Error = (RxData_Valid != RxData_Valid_synth);
		Tx_Ready_Error = (Tx_Ready != Tx_Ready_synth);
		Tx_Error_Error = (Tx_Error != Tx_Error_synth);
		S_Data_Error = (S_Data != S_Data_synth);
		Synth_Error = (Rx_Ready_Error || Rx_Error_Error || RxData_Error || RxData_Valid_Error || Tx_Ready_Error || Tx_Error_Error || S_Data_Error);
	end
	reg GlobalErr = 1'b0;
	/*
	reg nextErr = 1'b0;
	always @(posedge clk) nextErr = GlobalErr ? 0 : GlobalErr;
	always @(posedge clk) GlobalErr = nextErr;
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
		#10
		
		//release reset
		Rst_n = 1'b1; 	
	
	//now lets have some fun.  We will send a signal from the tb core and
	//watch it send the message.  We will watch each bit and confirm that it
	//is the expected operation...
		#5
		TxData = 32'b0000_1100_0001_0101_1100_1100_1100_1100;
		#20
		TxData_Valid = 1'b1;//We have now told the Tx Module it can go to town...  If Rx is ready it will go...
		#5	
		//Here we should see Tx_Ready Drop
		if(Tx_Ready != 1'b0) GlobalErr = 1'b1;
		TxData_Valid = 1'b0;

      #420
	   Core_Rcv_Ready = 1'b1;
	   #10
	   Core_Rcv_Ready = 1'b0;	   #10
      $display("Expected RxData:\t%b", TxData);
      $display("Actual RxData:\t\t%b", RxData);

		#5
		TxData = 32'hFFFF_FFFF;
		#20
		TxData_Valid = 1'b1;//We have now told the Tx Module it can go to town...  If Rx is ready it will go...
		#5	
		//Here we should see Tx_Ready Drop
		if(Tx_Ready != 1'b0) GlobalErr = 1'b1;
		TxData_Valid = 1'b0;

      #420
	   Core_Rcv_Ready = 1'b1;
	   #10
	   Core_Rcv_Ready = 1'b0;	   #10
      $display("Expected RxData:\t%b", TxData);
      $display("Actual RxData:\t\t%b", RxData);


		$stop;
	end
endmodule
