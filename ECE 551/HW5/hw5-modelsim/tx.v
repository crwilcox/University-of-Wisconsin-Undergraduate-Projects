/*
* Filename: tx.v
* Author: Christopher Wilcox and Dan Drusch (The Fighting Mongooses)
* Date: 2010-10-27
* Description:
*/
module tx(
			Clk_s, Rst_n, TxData_Valid, TxData, Error_Ack, Rx_Ready, Rx_Error, 	//INPUTS
			Tx_Ready, Tx_Error, S_Data 											//OUTPUTS
		);

// Global Interface 
input Clk_s;		// The global TX/RX clock. This clock is synchronized between 
					//transmitter and receiver. The Router Core does not necessarily 
					//use the same clock.
input Rst_n;		// The global asynchronous active-low reset signal.

// TX / Router Core Interface
input TxData_Valid;	// This signal serves as part of the TX/Core handshake. When TxData_Valid 
					// is high, it means that the input TxData has valid data available on it.
input [31:0] TxData;// This signal represents a 32-bit packet for transmission.
input Error_Ack;	// This signal acknowledges that the Core has seen that Tx_Error was asserted.
output reg Tx_Ready;	// This signal serves as part of the TX/Core handshake. When Tx_Ready is high, it means that the TX is ready to accept a packet from the Core.  
output reg Tx_Error;	// This signal is used to tell the Core that an error occurred while transmitting a packet on this link.

// TX/RX Interface (TX side)
output reg S_Data; 		// The serial data channel.
input Rx_Ready;		// This signal tells the transmitter it can begin sending data on the serial channel.
input Rx_Error;		// This signal is asserted for one cycle when the parity check on a received packet fails.


////////////////////////
/* ERROR HANDLING FSM */
////////////////////////
// Rx_Error: FROM RX: use to set Tx_Error
// Tx_Error: Set High a cycle after the Rx Error says a problem
// Error_Ack: FROM CORE: Use this to set Tx_Error Back down
reg Tx_Error_Next;
always @(negedge Clk_s, negedge Rst_n) begin
	if(!Rst_n) begin
		Tx_Error_Next <= 1'b0;
	end
	else begin 
		if(Rx_Error) begin
			Tx_Error_Next <= 1'b1;
		end
		else if(Tx_Error && Error_Ack) begin
			Tx_Error_Next <= 1'b0;
		end
		else begin
			Tx_Error_Next <= Tx_Error;
		end
	end
end
always @(posedge Clk_s, negedge Rst_n) begin
	if(!Rst_n) Tx_Error <= 1'b0; //if we are in reset, send the err signal low
	else Tx_Error <= Tx_Error_Next;	
end


reg DataTxInProg;
reg startSequence; //a bit to determine if we are calculating the start sequence	
reg[5:0] bitCount;
///////////////	
/* TX / Core */
///////////////
reg[32:0] transmissionPacket; //This packet will hold the 32 bit long data and its parity bit...
//reg transmissionPacketReady;
	/* HANDSHAKING WITH CORE AND GETTING DATA */
	//RULES TO LIVE BY
	//Never raise READY unless VALID is low.
	//Never drop READY unless VALID is high.
	always @(posedge Clk_s, negedge Rst_n) begin
		if(Rst_n == 1'b0) begin //RESET STATE
			Tx_Ready <= 1'b1;
			transmissionPacket[32:0] <= 33'b0;
		end
			//Tx_Ready is set High off of reset, and if we are not transmitting.  TxData_Valid will come high when they are ready for us
			//when TxDataValid is high, get data from TxData and store it in a register
		else if(Tx_Ready && TxData_Valid) begin //THIS BLOCK WILL ADJUST SIGNALS AS DATA IS GRABBED FOR TRANSMISSION
			transmissionPacket[32:1] <= TxData[31:0]; 
			transmissionPacket[0] <= ~^(TxData[31:0]);  // Compute the odd parity bit for data.  (110 would have parity bit = 1)
			Tx_Ready <= 1'b0; 	//deAssert Tx_Ready as we won't be able to work on new stuff... (Valid should go Low as governed by core)
								//Never drop READY unless VALID is high. (Satisfied by the initial If)
		end
			
		else if( bitCount == 6'd33 && TxData_Valid == 1'b0) begin 
			Tx_Ready <= 1'b1; //Set the Tx_Ready signal since we are done transmitting
			transmissionPacket[32:0] <= transmissionPacket[32:0];//Could probably set to 0's but no harm in this
		  	//Never raise READY unless VALID is low.
		end		
	end

/////////////		
/* TX / RX */
/////////////	
	/* HANDSHAKING WITH RX and Start Sequence Transmission*/
	//This is our start case.  If Rx_Ready, We need to send the start sequence of 5 Zeros.
	//hold a startSequence flag high for 5 clocks to cause this
	reg[2:0] startSequenceCount; //3 bit count to go 0 to 5
	always @(posedge Clk_s, negedge Rst_n) begin// may not need this extra always block
		if(Rst_n == 1'b0) begin
			startSequence <= 1'b0;
			startSequenceCount <= 3'b000;//On Reset, we are not really in the start sequence
		end
		else if( startSequenceCount < 3'd5 && Rx_Ready && !Tx_Ready) begin //send the start sequence 00000 for the first 5 clks
			startSequence <= 1'b1;
			startSequenceCount <= startSequenceCount + 1'b1;
		end
		else if (Tx_Ready) begin //after the start sequence we can start transmitting data
			startSequence <= 1'b0;
			startSequenceCount <= 3'd0;
		end
		else begin
			startSequence <= 1'b0;
		   	startSequenceCount <= startSequenceCount;
		end
	end	
	

	/* TX DATA TRANSMISSION TO RX */
	//Send Data MSb first, parity at the end, and bitstuffing to avoid the start sequence of 00000
	reg zeroPatternDetected;
	reg[1:0] zeroCount;

	// This State Machine goes sends the data serially, and if the pattern is
	// detected by the zero pattern detector, a 1 is sent to bitstuff
	// This block output S_Data
	always @(posedge Clk_s, negedge Rst_n) begin
		if(Rst_n == 1'b0) begin bitCount <= 6'b0; S_Data <= 1'b1; DataTxInProg <= 1'b0; end	// Reset State
		else if(zeroPatternDetected) begin bitCount <= bitCount; S_Data <= 1'b1; DataTxInProg <= DataTxInProg; end	// output a 1 for bitstuffing, and dont increment count to output from data register
		else if(startSequence)  begin bitCount <= 6'b0; S_Data <= 1'b0; DataTxInProg <= 1'b1; end	// output a 0, as we are in the start sequence, and do not increment count
	
		 // output from the data register and update the count
		else if( bitCount <= 32 && DataTxInProg) begin // We need to transmit more data yet
			S_Data <= transmissionPacket[6'd32 - bitCount]; //S_Data gets the transmissionPacket bits in order MSb to LSb
			bitCount <= bitCount + 1'b1; //need to increment the bitCount
			DataTxInProg <= 1'b1; // Transmission is in progress
		end
		else begin  //WE ARE DONE TRANSMITTING! YAY... CAN SET TxInProg as we are done...
			S_Data <= 1'b1;//hold S_Data at 1 when we are not transmitting to avoid triggering the reciever
			bitCount <= 6'b0;
			DataTxInProg <= 1'b0;
		end			
	end

	//Count to 4 with a pattern detect.  If You see 4 zeros in a row, set a flag.  This is used for bitstuffing...
	always @(posedge Clk_s, negedge Rst_n) begin	
		if(!Rst_n) 				begin zeroCount <= 2'd0; zeroPatternDetected <= 1'b0; end //Reset state
		else if(!DataTxInProg) 	begin zeroCount <= 2'd0; zeroPatternDetected <= 1'b0; end // not transmitting
		else if(startSequence) 	begin zeroCount <= 2'd0; zeroPatternDetected <= 1'b0; end //startsequence
		
		else if(zeroPatternDetected) begin 
			zeroCount <= zeroCount;
			zeroPatternDetected <= 1'b0; //if It was detected, flip it off as we have stuffed already
		end
			
		else if(transmissionPacket[6'd32 - bitCount] == 1'b1) begin zeroPatternDetected <= 1'b0; zeroCount <= 2'd0; end //received packet bit was 1, reset count for zeroPattern
		
		else if(zeroCount == 2'd3) begin zeroPatternDetected <= 1'b1; zeroCount <= 2'd0;	end //input was a 0, and Zero pattern detected
		
		else begin zeroPatternDetected <= 1'b0;	zeroCount <= zeroCount + 2'd1; end //No pattern detected, but input was a 0 increment		
	end
endmodule
