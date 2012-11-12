module CoreWithTxRx(
  input Clk_r,Clk_s,Rst_n,

  //Node I/O
  input         Packet_From_Node_Valid,
  input [31:0]  Packet_From_Node,
  output [31:0] Packet_To_Node,
  output        Packet_To_Node_Valid,Core_Load_Ack,

  // East bound 
        //TX I/O
          input  E_Tx_Rx_Ready, E_Tx_Rx_Error,
          output E_Tx_S_Data,

        //RX I/O
           input  E_Rx_S_Data,
           output E_Rx_Rx_Ready,E_Rx_Rx_Error,   

  // West bound 
        //TX I/O
          input  W_Tx_Rx_Ready, W_Tx_Rx_Error,
          output W_Tx_S_Data,

        //RX I/O
           input  W_Rx_S_Data,
           output W_Rx_Rx_Ready,W_Rx_Rx_Error,   
  
  // South bound 
        //TX I/O
          input  S_Tx_Rx_Ready, S_Tx_Rx_Error,
          output S_Tx_S_Data,

        //RX I/O
           input  S_Rx_S_Data,
           output S_Rx_Rx_Ready,S_Rx_Rx_Error,   

  // North bound 
        //TX I/O
          input  N_Tx_Rx_Ready, N_Tx_Rx_Error,
          output N_Tx_S_Data,

        //RX I/O
           input  N_Rx_S_Data,
           output N_Rx_Rx_Ready,N_Rx_Rx_Error
);


//INTERNALLY HANDLED
wire N_TxData_Valid, S_TxData_Valid, W_TxData_Valid, E_TxData_Valid;
wire N_RxData_Valid, S_RxData_Valid, W_RxData_Valid, E_RxData_Valid;
wire N_Core_Rcv_Ready, S_Core_Rcv_Ready, W_Core_Rcv_Ready, E_Core_Rcv_Ready;
wire[31:0] N_RxData, S_RxData, W_RxData, E_RxData;
wire[31:0] N_TxData, S_TxData, W_TxData, E_TxData;
wire N_Tx_Ready, S_Tx_Ready, W_Tx_Ready, E_Tx_Ready;
wire N_Tx_Error, S_Tx_Error, W_Tx_Error, E_Tx_Error;
wire N_Error_Ack, S_Error_Ack, W_Error_Ack, E_Error_Ack;


core routerCore (
              	    //INPUTS
                    .Clk_r(Clk_r), .Rst_n(Rst_n),
                    .N_Tx_Ready(N_Tx_Ready), .N_Tx_Error(N_Tx_Error), .N_RxData_Valid(N_RxData_Valid), .N_RxData(N_RxData),
                    .S_Tx_Ready(S_Tx_Ready), .S_Tx_Error(S_Tx_Error), .S_RxData_Valid(S_RxData_Valid), .S_RxData(S_RxData),
                    .W_Tx_Ready(W_Tx_Ready), .W_Tx_Error(W_Tx_Error), .W_RxData_Valid(W_RxData_Valid), .W_RxData(W_RxData),
                    .E_Tx_Ready(E_Tx_Ready), .E_Tx_Error(E_Tx_Error), .E_RxData_Valid(E_RxData_Valid), .E_RxData(E_RxData),
                    .Packet_From_Node(Packet_From_Node), .Packet_From_Node_Valid(Packet_From_Node_Valid),

                    //OUTPUTS
                    .N_TxData_Valid(N_TxData_Valid), .N_TxData(N_TxData), .N_Error_Ack(N_Error_Ack), .N_Core_Rcv_Ready(N_Core_Rcv_Ready),
                    .S_TxData_Valid(S_TxData_Valid), .S_TxData(S_TxData), .S_Error_Ack(S_Error_Ack), .S_Core_Rcv_Ready(S_Core_Rcv_Ready),
                    .W_TxData_Valid(W_TxData_Valid), .W_TxData(W_TxData), .W_Error_Ack(W_Error_Ack), .W_Core_Rcv_Ready(W_Core_Rcv_Ready),
                    .E_TxData_Valid(E_TxData_Valid), .E_TxData(E_TxData), .E_Error_Ack(E_Error_Ack), .E_Core_Rcv_Ready(E_Core_Rcv_Ready),
                    .Core_Load_Ack(Core_Load_Ack), .Packet_To_Node(Packet_To_Node), .Packet_To_Node_Valid(Packet_To_Node_Valid)
                    );
// East bound 					
	tx E_tx (
				.Clk_s(Clk_s), .Rst_n(Rst_n), .TxData_Valid(E_TxData_Valid), .TxData(E_TxData), .Error_Ack(E_Error_Ack), .Rx_Ready(E_Tx_Rx_Ready), .Rx_Error(E_Tx_Rx_Error), 	//INPUTS
				.Tx_Ready(E_Tx_Ready), .Tx_Error(E_Tx_Error), .S_Data(E_Tx_S_Data) 											//OUTPUTS
			);
	rx E_rx (
				.Clk_s(Clk_s), .Rst_n(Rst_n), .Core_Rcv_Ready(E_Core_Rcv_Ready), .S_Data(E_Rx_S_Data), 		//INPUTS
				.RxData_Valid(E_RxData_Valid), .RxData(E_RxData), .Rx_Ready(E_Rx_Rx_Ready), .Rx_Error(E_Rx_Rx_Error) 	//OUTPUTS
			);
// West bound 						
	tx W_tx (
				.Clk_s(Clk_s), .Rst_n(Rst_n), .TxData_Valid(W_TxData_Valid), .TxData(W_TxData), .Error_Ack(W_Error_Ack), .Rx_Ready(W_Tx_Rx_Ready), .Rx_Error(W_Tx_Rx_Error), 	//INPUTS
				.Tx_Ready(W_Tx_Ready), .Tx_Error(W_Tx_Error), .S_Data(W_Tx_S_Data) 											//OUTPUTS
			);
	rx W_rx (
				.Clk_s(Clk_s), .Rst_n(Rst_n), .Core_Rcv_Ready(W_Core_Rcv_Ready), .S_Data(W_Rx_S_Data), 		//INPUTS
				.RxData_Valid(W_RxData_Valid), .RxData(W_RxData), .Rx_Ready(W_Rx_Rx_Ready), .Rx_Error(W_Rx_Rx_Error) 	//OUTPUTS
			);					
// South bound			
	tx S_tx (
				.Clk_s(Clk_s), .Rst_n(Rst_n), .TxData_Valid(S_TxData_Valid), .TxData(S_TxData), .Error_Ack(S_Error_Ack), .Rx_Ready(S_Tx_Rx_Ready), .Rx_Error(S_Tx_Rx_Error), 	//INPUTS
				.Tx_Ready(S_Tx_Ready), .Tx_Error(S_Tx_Error), .S_Data(S_Tx_S_Data) 											//OUTPUTS
			);
	rx S_rx (
				.Clk_s(Clk_s), .Rst_n(Rst_n), .Core_Rcv_Ready(S_Core_Rcv_Ready), .S_Data(S_Rx_S_Data), 		//INPUTS
				.RxData_Valid(S_RxData_Valid), .RxData(S_RxData), .Rx_Ready(S_Rx_Rx_Ready), .Rx_Error(S_Rx_Rx_Error) 	//OUTPUTS
			);
// North bound 
	tx N_tx (
				.Clk_s(Clk_s), .Rst_n(Rst_n), .TxData_Valid(N_TxData_Valid), .TxData(N_TxData), .Error_Ack(N_Error_Ack), .Rx_Ready(N_Tx_Rx_Ready), .Rx_Error(N_Tx_Rx_Error), 	//INPUTS
				.Tx_Ready(N_Tx_Ready), .Tx_Error(N_Tx_Error), .S_Data(N_Tx_S_Data) 											//OUTPUTS
			);
	rx N_rx (
				.Clk_s(Clk_s), .Rst_n(Rst_n), .Core_Rcv_Ready(N_Core_Rcv_Ready), .S_Data(N_Rx_S_Data), 		//INPUTS
				.RxData_Valid(N_RxData_Valid), .RxData(N_RxData), .Rx_Ready(N_Rx_Rx_Ready), .Rx_Error(N_Rx_Rx_Error) 	//OUTPUTS
			);

endmodule

