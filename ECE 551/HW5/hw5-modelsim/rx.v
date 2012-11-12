/*
* Filename: rx.v
* Author: Christopher Wilcox and Dan Drusch (The Fighting Mongooses)
* Date: 2010-10-27
* Description: 
*/
module rx(
			Clk_s, Rst_n, Core_Rcv_Ready, S_Data, 		//INPUTS
			RxData_Valid, RxData, Rx_Ready, Rx_Error 	//OUTPUTS
		);

// Global Interface
input Clk_s;				// The global TX/RX clock. This clock is synchronized between transmitter and receiver. The Router Core does not necessarily use the same clock.
input Rst_n;				// The global asynchronous active-low reset signal.

// RX / Router Core Interface 
input Core_Rcv_Ready;	// This signal serves as part of the RX/Core handshake. When Core_Rcv_Ready is high, it means that the Core is capable of accepting a packet from the receiver.
output reg RxData_Valid;		// This signal serves as part of the RX/Core handshake. When RxData_Valid is high, it means that the input RxData has valid data available on it.
output reg[31:0] RxData;	// This signal represents a 32-bit packet which has been received from the transmitter, de-serialized, and is now presented to the Core.

// TX/RX Interface (RX side) 
input S_Data;			// The serial data channel.
output reg Rx_Ready;			// This signal tells the transmitter it can begin sending data on the serial channel.
output reg Rx_Error;			// This signal is asserted for one cycle when the parity check on a received packet fails.

/////////////		
/* TX / RX */
/////////////

/* 0 SEQUENCE COUNTER */
//Detect patterns of 0s for bit stuffing and start pattern

reg [2:0] zeroCounter;
reg bitStuff;			//Enable for acknowledging a four-0 sequence
reg startSeq;			//Acknowledges start sequence
reg[5:0] receivedCounter; 	//Counter for number of bits received
always @(posedge Clk_s, negedge Rst_n) begin 
	if(Rst_n == 1'b0) begin
		zeroCounter <= 3'b0;
		bitStuff <= 1'b0;
		startSeq <= 1'b0;
	end
	else begin
		if(S_Data == 1'b0) begin
			//This is the 4th 0 in a row, next bit will be stuffed or complete start seq
			if (zeroCounter == 3 && receivedCounter < 6'd32) bitStuff <= 1'b1;
			else bitStuff <= 0;
			
			//This is the 5th 0 in a row, indicates start sequence
			if (zeroCounter >= 4) begin 
				zeroCounter <= 3'b0;
				startSeq <= 1'b1;
			end
			else begin
				zeroCounter <= zeroCounter + 1;
				startSeq <= 1'b0;
			end
		end
		else begin  //1 was recieved, reset all counts
			zeroCounter <= 3'b0;
			bitStuff <= 1'b0;
			startSeq <= 1'b0;
		end
	end
end

/* RX DATA RECEIVING FROM TX */
//Receive data MSb first, parity at the end, and bit stuffed 1 after 0000

reg[32:0] receivedPacket;	//Receiver for 33 bits (32 data, 1 parity) of serial data
reg dataRx;					//Status if currently receiving data
reg bufferFull;				//Indicates if the data in RxData has not been read by the Core

always @(posedge Clk_s, negedge Rst_n) begin
	if(Rst_n == 1'b0) begin
		receivedPacket <= 33'b0;
		receivedCounter <= 6'b0;
		dataRx <= 1'b0;
		Rx_Ready <= 1'b0;
		Rx_Error <= 1'b0;
		RxData <= 32'b0;
	end
	else begin
		//Hold Rx_Ready high if not receiving data and start sequence hasn't been received
		if(dataRx == 0 && startSeq != 1'b1) begin 
			Rx_Ready <= 1;
		end
		else begin
			Rx_Ready <= 0;
		end
		
		//Begin receiving data if start sequence detected
		if(startSeq) begin
			dataRx <= 1'b1;
			receivedCounter <= 6'b1;
			receivedPacket <= {32'b0,S_Data};
			Rx_Error <= 1'b0;
			RxData <= RxData;
		end
		else if(dataRx) begin
			if(bitStuff) begin  //Ignore incoming bit
				receivedPacket <= receivedPacket;
				dataRx <= dataRx;
				receivedCounter <= receivedCounter;
				Rx_Error <= 1'b0;
				RxData <= RxData;
			end
			else begin
				if (receivedCounter >= 6'd33) begin //All bits recieved, check validity and wait for Core transmit
					if(^receivedPacket == 1'b1 && bufferFull == 1'b0) begin //check parity and make sure buffer free
						//move to good data register and set alert
						Rx_Error <= 1'b0;
						RxData <= receivedPacket[32:1];
						//clear all other entries
						receivedPacket <= 33'b0;
						receivedCounter <= 6'b0;
						dataRx <= 1'b0;
					end
					else if (bufferFull == 1'b1) begin  //Hold values if buffer is not free
						Rx_Error <= 1'b0;
						RxData <= RxData;
						//maintain all other entries
						receivedPacket <= receivedPacket;
						receivedCounter <= receivedCounter;
						dataRx <= dataRx;
					end
					else begin
						//assert error if parity is not satisfied.
						Rx_Error <= 1'b1;
						RxData <= RxData;
						//clear all other entries
						receivedPacket <= 33'b0;
						receivedCounter <= 6'b0;
						dataRx <= 1'b0;
					end		
				end
				else begin
					receivedCounter <= receivedCounter + 1;
					dataRx <= 1'b1;
					receivedPacket <= {receivedPacket[31:0],S_Data}; //Shift Register
					Rx_Error <= 1'b0;
					RxData <= RxData;
				end
			end
		end
		else begin
			dataRx <= 1'b0;
			receivedPacket <= 33'b0;
			receivedCounter <= 6'b0;
			Rx_Error <= 1'b0;
			RxData <= RxData;
		end		
	end
end

///////////////		
/* RX / CORE */
///////////////

/* RX / CORE HANDSHAKE */
//Dectect if conditions are right to move data into RxData register
//Sets RxData_Valid

always @(posedge Clk_s, negedge Rst_n) begin
	if(Rst_n == 1'b0) begin
		RxData_Valid <= 1'b0;
		bufferFull <= 0;
	end
	else begin
		if (receivedCounter >= 6'd33 && ^receivedPacket == 1'b1 && bufferFull == 1'b0) begin
			bufferFull <= 1'b1;
			RxData_Valid <= 1'b0;
		end
		else begin 
			if(bufferFull == 1'b1 && Core_Rcv_Ready == 1'b1) begin 
				//If there is unread data in the Buffer and the core is ready, assert data valid
				RxData_Valid <= 1'b1; 
				bufferFull <= 1'b1;
			end
			else if(RxData_Valid == 1'b1 && Core_Rcv_Ready == 1'b0) begin
				//Core indicates that it is no longer ready, means data recieved
				RxData_Valid <= 1'b0;
				bufferFull <= 1'b0;
			end
			else begin
				//Handshake remains in holding pattern if not getting or sending data.
				RxData_Valid <= RxData_Valid;
				bufferFull <= bufferFull;
			end
		end
	end
end

endmodule


























