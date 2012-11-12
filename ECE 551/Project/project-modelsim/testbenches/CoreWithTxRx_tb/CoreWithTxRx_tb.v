module CoreWithTxRx_tb();
	parameter MAX_CYCLES = 10000;
	parameter CLK_S_PERIOD = .5;
	parameter CLK_R_PERIOD = 1;
	parameter HEIGHT = 4; 
	parameter WIDTH = 4;  
	parameter SIZE = HEIGHT * WIDTH;
	integer outFile, inFile, totalCycles, row, rowOut, col, colOut, totalMessages;
	reg Clk_r, Clk_s, Rst_n;
   
	reg [SIZE-1:0] Packet_From_Node_Valid;
	reg [31:0] Packet_From_Node[SIZE-1:0];
	wire [31:0] Packet_To_Node[SIZE-1:0];
	wire [SIZE-1:0] Packet_To_Node_Valid;
	wire [SIZE-1:0] Core_Load_Ack;
	wire [SIZE-1:0] N_Rx_Ready, S_Rx_Ready, E_Rx_Ready, W_Rx_Ready;
	wire [SIZE-1:0] N_Rx_Error, S_Rx_Error, E_Rx_Error, W_Rx_Error;
	wire [SIZE-1:0] N_S_Data, S_S_Data, E_S_Data, W_S_Data;
   
	generate
		genvar genRow, genCol;
		for(genRow = 0; genRow < HEIGHT; genRow = genRow+1) begin : rows
   			for(genCol = 0; genCol < WIDTH; genCol = genCol+1) begin : columns
				if(genCol == (WIDTH -1) && genRow == (HEIGHT -1)) begin : NoEastAndNorth
					CoreWithTxRx coreWithTxRx(
						.Clk_r(Clk_r), .Clk_s(Clk_s), .Rst_n(Rst_n),
	  				//Node I/O
						.Packet_From_Node_Valid(Packet_From_Node_Valid[(genRow*WIDTH+genCol)]),
			  			.Packet_From_Node(Packet_From_Node[(genRow*WIDTH+genCol)]),
			  			.Packet_To_Node(Packet_To_Node[(genRow*WIDTH+genCol)]),              
			  			.Packet_To_Node_Valid(Packet_To_Node_Valid[(genRow*WIDTH+genCol)]),
			 			.Core_Load_Ack(Core_Load_Ack[(genRow*WIDTH+genCol)]),
					// East bound 
						//TX I/O
						.E_Tx_Rx_Ready(1'b0),
						.E_Tx_Rx_Error(1'b0),
						.E_Tx_S_Data(E_S_Data[(genRow*WIDTH+genCol)]),
						//RX I/O
						.E_Rx_S_Data(1'b1),
						.E_Rx_Rx_Ready(E_Rx_Ready[(genRow*WIDTH+genCol)]),
						.E_Rx_Rx_Error(E_Rx_Error[(genRow*WIDTH+genCol)]), 
					// West bound 
						//TX I/O
						.W_Tx_Rx_Ready(E_Rx_Ready[(genRow*WIDTH+genCol)-1]), 
						.W_Tx_Rx_Error(E_Rx_Error[(genRow*WIDTH+genCol)-1]),
						.W_Tx_S_Data(W_S_Data[(genRow*WIDTH+genCol)]),
						//RX I/O
						.W_Rx_S_Data(E_S_Data[(genRow*WIDTH+genCol)-1]),
						.W_Rx_Rx_Ready(W_Rx_Ready[(genRow*WIDTH+genCol)]),
						.W_Rx_Rx_Error(W_Rx_Error[(genRow*WIDTH+genCol)]),  
					// South bound 
						//TX I/O
						.S_Tx_Rx_Ready(N_Rx_Ready[(genRow*WIDTH+genCol)-WIDTH]), 
						.S_Tx_Rx_Error(N_Rx_Error[(genRow*WIDTH+genCol)-WIDTH]),
						.S_Tx_S_Data(S_S_Data[(genRow*WIDTH+genCol)]),
						//RX I/O
						.S_Rx_S_Data(N_S_Data[(genRow*WIDTH+genCol)-WIDTH]),
						.S_Rx_Rx_Ready(S_Rx_Ready[(genRow*WIDTH+genCol)]),
						.S_Rx_Rx_Error(S_Rx_Error[(genRow*WIDTH+genCol)]),
					// North bound 
						//TX I/O
						.N_Tx_Rx_Ready(1'b0),
						.N_Tx_Rx_Error(1'b0),
						.N_Tx_S_Data(N_S_Data[(genRow*WIDTH+genCol)]),
						//RX I/O
						.N_Rx_S_Data(1'b1),
						.N_Rx_Rx_Ready(N_Rx_Ready[(genRow*WIDTH+genCol)]),
						.N_Rx_Rx_Error(N_Rx_Error[(genRow*WIDTH+genCol)])
					);          
				end
				else if(genCol == (WIDTH-1) && genRow == 0) begin : NoEastAndSouth
					CoreWithTxRx coreWithTxRx(
						.Clk_r(Clk_r), .Clk_s(Clk_s), .Rst_n(Rst_n),
		 				//Node I/O
						.Packet_From_Node_Valid(Packet_From_Node_Valid[(genRow*WIDTH+genCol)]),
		  				.Packet_From_Node(Packet_From_Node[(genRow*WIDTH+genCol)]),
		  				.Packet_To_Node(Packet_To_Node[(genRow*WIDTH+genCol)]),              
			  			.Packet_To_Node_Valid(Packet_To_Node_Valid[(genRow*WIDTH+genCol)]),
			 			.Core_Load_Ack(Core_Load_Ack[(genRow*WIDTH+genCol)]),
					// East bound 
						//TX I/O
						.E_Tx_Rx_Ready(1'b0),
						.E_Tx_Rx_Error(1'b0),
						.E_Tx_S_Data(E_S_Data[(genRow*WIDTH+genCol)]),
						//RX I/O
						.E_Rx_S_Data(1'b1),
						.E_Rx_Rx_Ready(E_Rx_Ready[(genRow*WIDTH+genCol)]),
						.E_Rx_Rx_Error(E_Rx_Error[(genRow*WIDTH+genCol)]), 
					// West bound 
						//TX I/O
						.W_Tx_Rx_Ready(E_Rx_Ready[(genRow*WIDTH+genCol)-1]), 
						.W_Tx_Rx_Error(E_Rx_Error[(genRow*WIDTH+genCol)-1]),
						.W_Tx_S_Data(W_S_Data[(genRow*WIDTH+genCol)]),
						//RX I/O
						.W_Rx_S_Data(E_S_Data[(genRow*WIDTH+genCol)-1]),
						.W_Rx_Rx_Ready(W_Rx_Ready[(genRow*WIDTH+genCol)]),
						.W_Rx_Rx_Error(W_Rx_Error[(genRow*WIDTH+genCol)]),  
					// South bound 
						//TX I/O
						.S_Tx_Rx_Ready(1'b0), 
						.S_Tx_Rx_Error(1'b0),
						.S_Tx_S_Data(S_S_Data[(genRow*WIDTH+genCol)]),
						//RX I/O
						.S_Rx_S_Data(1'b1),
						.S_Rx_Rx_Ready(S_Rx_Ready[(genRow*WIDTH+genCol)]),
						.S_Rx_Rx_Error(S_Rx_Error[(genRow*WIDTH+genCol)]),
					// North bound 
						//TX I/O
						.N_Tx_Rx_Ready(S_Rx_Ready[(genRow*WIDTH+genCol)+WIDTH]),
						.N_Tx_Rx_Error(S_Rx_Error[(genRow*WIDTH+genCol)+WIDTH]),
						.N_Tx_S_Data(N_S_Data[(genRow*WIDTH+genCol)]),
						//RX I/O
						.N_Rx_S_Data(S_S_Data[(genRow*WIDTH+genCol)+WIDTH]),
						.N_Rx_Rx_Ready(N_Rx_Ready[(genRow*WIDTH+genCol)]),
						.N_Rx_Rx_Error(N_Rx_Error[(genRow*WIDTH+genCol)])
					);          
				end
				else if(genCol == 0 && genRow == (HEIGHT -1)) begin : NoWestAndNorth 
					CoreWithTxRx coreWithTxRx(
						.Clk_r(Clk_r), .Clk_s(Clk_s), .Rst_n(Rst_n),
	  				//Node I/O
						.Packet_From_Node_Valid(Packet_From_Node_Valid[(genRow*WIDTH+genCol)]),
			  			.Packet_From_Node(Packet_From_Node[(genRow*WIDTH+genCol)]),
			  			.Packet_To_Node(Packet_To_Node[(genRow*WIDTH+genCol)]),              
			  			.Packet_To_Node_Valid(Packet_To_Node_Valid[(genRow*WIDTH+genCol)]),
			 			.Core_Load_Ack(Core_Load_Ack[(genRow*WIDTH+genCol)]),
					// East bound 
						//TX I/O
						.E_Tx_Rx_Ready(W_Rx_Ready[(genRow*WIDTH+genCol)+1]),
						.E_Tx_Rx_Error(W_Rx_Error[(genRow*WIDTH+genCol)+1]),
						.E_Tx_S_Data(E_S_Data[(genRow*WIDTH+genCol)]),
						//RX I/O
						.E_Rx_S_Data(W_S_Data[(genRow*WIDTH+genCol)+1]),
						.E_Rx_Rx_Ready(E_Rx_Ready[(genRow*WIDTH+genCol)]),
						.E_Rx_Rx_Error(E_Rx_Error[(genRow*WIDTH+genCol)]), 
					// West bound 
						//TX I/O
						.W_Tx_Rx_Ready(1'b0), 
						.W_Tx_Rx_Error(1'b0),
						.W_Tx_S_Data(W_S_Data[(genRow*WIDTH+genCol)]),
						//RX I/O
						.W_Rx_S_Data(1'b1),
						.W_Rx_Rx_Ready(W_Rx_Ready[(genRow*WIDTH+genCol)]),
						.W_Rx_Rx_Error(W_Rx_Error[(genRow*WIDTH+genCol)]),  
					// South bound 
						//TX I/O
						.S_Tx_Rx_Ready(N_Rx_Ready[(genRow*WIDTH+genCol)-WIDTH]), 
						.S_Tx_Rx_Error(N_Rx_Error[(genRow*WIDTH+genCol)-WIDTH]),
						.S_Tx_S_Data(S_S_Data[(genRow*WIDTH+genCol)]),
						//RX I/O
						.S_Rx_S_Data(N_S_Data[(genRow*WIDTH+genCol)-WIDTH]),
						.S_Rx_Rx_Ready(S_Rx_Ready[(genRow*WIDTH+genCol)]),
						.S_Rx_Rx_Error(S_Rx_Error[(genRow*WIDTH+genCol)]),
					// North bound 
						//TX I/O
						.N_Tx_Rx_Ready(1'b0),
						.N_Tx_Rx_Error(1'b0),
						.N_Tx_S_Data(N_S_Data[(genRow*WIDTH+genCol)]),
						//RX I/O
						.N_Rx_S_Data(1'b1),
						.N_Rx_Rx_Ready(N_Rx_Ready[(genRow*WIDTH+genCol)]),
						.N_Rx_Rx_Error(N_Rx_Error[(genRow*WIDTH+genCol)])
					);          
				end
				else if(genCol == 0 && genRow == 0) begin : NoWestAndSouth
					CoreWithTxRx coreWithTxRx(
						.Clk_r(Clk_r), .Clk_s(Clk_s), .Rst_n(Rst_n),
	  				//Node I/O
						.Packet_From_Node_Valid(Packet_From_Node_Valid[(genRow*WIDTH+genCol)]),
			  			.Packet_From_Node(Packet_From_Node[(genRow*WIDTH+genCol)]),
			  			.Packet_To_Node(Packet_To_Node[(genRow*WIDTH+genCol)]),              
			  			.Packet_To_Node_Valid(Packet_To_Node_Valid[(genRow*WIDTH+genCol)]),
		 			.Core_Load_Ack(Core_Load_Ack[(genRow*WIDTH+genCol)]),
					// East bound 
						//TX I/O
						.E_Tx_Rx_Ready(W_Rx_Ready[(genRow*WIDTH+genCol)+1]),
						.E_Tx_Rx_Error(W_Rx_Error[(genRow*WIDTH+genCol)+1]),
						.E_Tx_S_Data(E_S_Data[(genRow*WIDTH+genCol)]),
						//RX I/O
						.E_Rx_S_Data(W_S_Data[(genRow*WIDTH+genCol)+1]),
						.E_Rx_Rx_Ready(E_Rx_Ready[(genRow*WIDTH+genCol)]),
						.E_Rx_Rx_Error(E_Rx_Error[(genRow*WIDTH+genCol)]), 
					// West bound 
						//TX I/O
						.W_Tx_Rx_Ready(1'b0), 
						.W_Tx_Rx_Error(1'b0),
						.W_Tx_S_Data(W_S_Data[(genRow*WIDTH+genCol)]),
						//RX I/O
						.W_Rx_S_Data(1'b1),
						.W_Rx_Rx_Ready(W_Rx_Ready[(genRow*WIDTH+genCol)]),
						.W_Rx_Rx_Error(W_Rx_Error[(genRow*WIDTH+genCol)]),  
					// South bound 
						//TX I/O
						.S_Tx_Rx_Ready(1'b0), 
						.S_Tx_Rx_Error(1'b0),
						.S_Tx_S_Data(S_S_Data[(genRow*WIDTH+genCol)]),
						//RX I/O
						.S_Rx_S_Data(1'b1),
						.S_Rx_Rx_Ready(S_Rx_Ready[(genRow*WIDTH+genCol)]),
						.S_Rx_Rx_Error(S_Rx_Error[(genRow*WIDTH+genCol)]),
					// North bound 
						//TX I/O
						.N_Tx_Rx_Ready(S_Rx_Ready[(genRow*WIDTH+genCol)+WIDTH]),
						.N_Tx_Rx_Error(S_Rx_Error[(genRow*WIDTH+genCol)+WIDTH]),
						.N_Tx_S_Data(N_S_Data[(genRow*WIDTH+genCol)]),
						//RX I/O
						.N_Rx_S_Data(S_S_Data[(genRow*WIDTH+genCol)+WIDTH]),
						.N_Rx_Rx_Ready(N_Rx_Ready[(genRow*WIDTH+genCol)]),
						.N_Rx_Rx_Error(N_Rx_Error[(genRow*WIDTH+genCol)])
					);          
				end
				else if(genRow == (HEIGHT-1)) begin : NoNorth
					CoreWithTxRx coreWithTxRx(
						.Clk_r(Clk_r), .Clk_s(Clk_s), .Rst_n(Rst_n),
	  				//Node I/O
						.Packet_From_Node_Valid(Packet_From_Node_Valid[(genRow*WIDTH+genCol)]),
			  			.Packet_From_Node(Packet_From_Node[(genRow*WIDTH+genCol)]),
			  			.Packet_To_Node(Packet_To_Node[(genRow*WIDTH+genCol)]),              
			  			.Packet_To_Node_Valid(Packet_To_Node_Valid[(genRow*WIDTH+genCol)]),
			 			.Core_Load_Ack(Core_Load_Ack[(genRow*WIDTH+genCol)]),
					// East bound 
						//TX I/O
						.E_Tx_Rx_Ready(W_Rx_Ready[(genRow*WIDTH+genCol)+1]),
						.E_Tx_Rx_Error(W_Rx_Error[(genRow*WIDTH+genCol)+1]),
						.E_Tx_S_Data(E_S_Data[(genRow*WIDTH+genCol)]),
						//RX I/O
						.E_Rx_S_Data(W_S_Data[(genRow*WIDTH+genCol)+1]),
						.E_Rx_Rx_Ready(E_Rx_Ready[(genRow*WIDTH+genCol)]),
						.E_Rx_Rx_Error(E_Rx_Error[(genRow*WIDTH+genCol)]), 
					// West bound 
						//TX I/O
						.W_Tx_Rx_Ready(E_Rx_Ready[(genRow*WIDTH+genCol)-1]), 
						.W_Tx_Rx_Error(E_Rx_Error[(genRow*WIDTH+genCol)-1]),
						.W_Tx_S_Data(W_S_Data[(genRow*WIDTH+genCol)]),
	
						//RX I/O
						.W_Rx_S_Data(E_S_Data[(genRow*WIDTH+genCol)-1]),
						.W_Rx_Rx_Ready(W_Rx_Ready[(genRow*WIDTH+genCol)]),
						.W_Rx_Rx_Error(W_Rx_Error[(genRow*WIDTH+genCol)]),  
					// South bound 
						//TX I/O
						.S_Tx_Rx_Ready(N_Rx_Ready[(genRow*WIDTH+genCol)-WIDTH]), 
						.S_Tx_Rx_Error(N_Rx_Error[(genRow*WIDTH+genCol)-WIDTH]),
						.S_Tx_S_Data(S_S_Data[(genRow*WIDTH+genCol)]),
	
						//RX I/O
						.S_Rx_S_Data(N_S_Data[(genRow*WIDTH+genCol)-WIDTH]),
						.S_Rx_Rx_Ready(S_Rx_Ready[(genRow*WIDTH+genCol)]),
						.S_Rx_Rx_Error(S_Rx_Error[(genRow*WIDTH+genCol)]),
					// North bound 
						//TX I/O
						.N_Tx_Rx_Ready(1'b0),
						.N_Tx_Rx_Error(1'b0),
						.N_Tx_S_Data(N_S_Data[(genRow*WIDTH+genCol)]),
						//RX I/O
						.N_Rx_S_Data(1'b1),
						.N_Rx_Rx_Ready(N_Rx_Ready[(genRow*WIDTH+genCol)]),
						.N_Rx_Rx_Error(N_Rx_Error[(genRow*WIDTH+genCol)])
					);          
				end
				else if(genRow == 0) begin : NoSouth
					CoreWithTxRx coreWithTxRx(
						.Clk_r(Clk_r), .Clk_s(Clk_s), .Rst_n(Rst_n),
	  				//Node I/O
						.Packet_From_Node_Valid(Packet_From_Node_Valid[(genRow*WIDTH+genCol)]),
			  			.Packet_From_Node(Packet_From_Node[(genRow*WIDTH+genCol)]),
			  			.Packet_To_Node(Packet_To_Node[(genRow*WIDTH+genCol)]),              
			  			.Packet_To_Node_Valid(Packet_To_Node_Valid[(genRow*WIDTH+genCol)]),
			 			.Core_Load_Ack(Core_Load_Ack[(genRow*WIDTH+genCol)]),
					// East bound 
						//TX I/O
						.E_Tx_Rx_Ready(W_Rx_Ready[(genRow*WIDTH+genCol)+1]),
						.E_Tx_Rx_Error(W_Rx_Error[(genRow*WIDTH+genCol)+1]),
						.E_Tx_S_Data(E_S_Data[(genRow*WIDTH+genCol)]),
						//RX I/O
						.E_Rx_S_Data(W_S_Data[(genRow*WIDTH+genCol)+1]),
						.E_Rx_Rx_Ready(E_Rx_Ready[(genRow*WIDTH+genCol)]),
						.E_Rx_Rx_Error(E_Rx_Error[(genRow*WIDTH+genCol)]), 
					// West bound 
						//TX I/O
						.W_Tx_Rx_Ready(E_Rx_Ready[(genRow*WIDTH+genCol)-1]), 
						.W_Tx_Rx_Error(E_Rx_Error[(genRow*WIDTH+genCol)-1]),
						.W_Tx_S_Data(W_S_Data[(genRow*WIDTH+genCol)]),
						//RX I/O
						.W_Rx_S_Data(E_S_Data[(genRow*WIDTH+genCol)-1]),
						.W_Rx_Rx_Ready(W_Rx_Ready[(genRow*WIDTH+genCol)]),
						.W_Rx_Rx_Error(W_Rx_Error[(genRow*WIDTH+genCol)]),  
					// South bound 
						//TX I/O
						.S_Tx_Rx_Ready(1'b0), 
						.S_Tx_Rx_Error(1'b0),
						.S_Tx_S_Data(S_S_Data[(genRow*WIDTH+genCol)]),
						//RX I/O
						.S_Rx_S_Data(1'b1),
						.S_Rx_Rx_Ready(S_Rx_Ready[(genRow*WIDTH+genCol)]),
						.S_Rx_Rx_Error(S_Rx_Error[(genRow*WIDTH+genCol)]),
					// North bound 
						//TX I/O
						.N_Tx_Rx_Ready(S_Rx_Ready[(genRow*WIDTH+genCol)+WIDTH]),
						.N_Tx_Rx_Error(S_Rx_Error[(genRow*WIDTH+genCol)+WIDTH]),
						.N_Tx_S_Data(N_S_Data[(genRow*WIDTH+genCol)]),
						//RX I/O
						.N_Rx_S_Data(S_S_Data[(genRow*WIDTH+genCol)+WIDTH]),
						.N_Rx_Rx_Ready(N_Rx_Ready[(genRow*WIDTH+genCol)]),
						.N_Rx_Rx_Error(N_Rx_Error[(genRow*WIDTH+genCol)])
					);          
				end
				else if(genCol == 0) begin : NoWest
					CoreWithTxRx coreWithTxRx(
						.Clk_r(Clk_r), .Clk_s(Clk_s), .Rst_n(Rst_n),
	  				//Node I/O
						.Packet_From_Node_Valid(Packet_From_Node_Valid[(genRow*WIDTH+genCol)]),
			  			.Packet_From_Node(Packet_From_Node[(genRow*WIDTH+genCol)]),
			  			.Packet_To_Node(Packet_To_Node[(genRow*WIDTH+genCol)]),              
			  			.Packet_To_Node_Valid(Packet_To_Node_Valid[(genRow*WIDTH+genCol)]),
			 			.Core_Load_Ack(Core_Load_Ack[(genRow*WIDTH+genCol)]),
					// East bound 
						//TX I/O
						.E_Tx_Rx_Ready(W_Rx_Ready[(genRow*WIDTH+genCol)+1]),
						.E_Tx_Rx_Error(W_Rx_Error[(genRow*WIDTH+genCol)+1]),
						.E_Tx_S_Data(E_S_Data[(genRow*WIDTH+genCol)]),
						//RX I/O
						.E_Rx_S_Data(W_S_Data[(genRow*WIDTH+genCol)+1]),
						.E_Rx_Rx_Ready(E_Rx_Ready[(genRow*WIDTH+genCol)]),
						.E_Rx_Rx_Error(E_Rx_Error[(genRow*WIDTH+genCol)]), 
					// West bound 
						//TX I/O
						.W_Tx_Rx_Ready(1'b0), 
						.W_Tx_Rx_Error(1'b0),
						.W_Tx_S_Data(W_S_Data[(genRow*WIDTH+genCol)]),
						//RX I/O
						.W_Rx_S_Data(1'b1),
						.W_Rx_Rx_Ready(W_Rx_Ready[(genRow*WIDTH+genCol)]),
						.W_Rx_Rx_Error(W_Rx_Error[(genRow*WIDTH+genCol)]),  
					// South bound 
						//TX I/O
						.S_Tx_Rx_Ready(N_Rx_Ready[(genRow*WIDTH+genCol)-WIDTH]), 
						.S_Tx_Rx_Error(N_Rx_Error[(genRow*WIDTH+genCol)-WIDTH]),
						.S_Tx_S_Data(S_S_Data[(genRow*WIDTH+genCol)]),
						//RX I/O
						.S_Rx_S_Data(N_S_Data[(genRow*WIDTH+genCol)-WIDTH]),
						.S_Rx_Rx_Ready(S_Rx_Ready[(genRow*WIDTH+genCol)]),
						.S_Rx_Rx_Error(S_Rx_Error[(genRow*WIDTH+genCol)]),
					// North bound 
						//TX I/O
						.N_Tx_Rx_Ready(S_Rx_Ready[(genRow*WIDTH+genCol)+WIDTH]),
						.N_Tx_Rx_Error(S_Rx_Error[(genRow*WIDTH+genCol)+WIDTH]),
						.N_Tx_S_Data(N_S_Data[(genRow*WIDTH+genCol)]),
						//RX I/O
						.N_Rx_S_Data(S_S_Data[(genRow*WIDTH+genCol)+WIDTH]),
						.N_Rx_Rx_Ready(N_Rx_Ready[(genRow*WIDTH+genCol)]),
						.N_Rx_Rx_Error(N_Rx_Error[(genRow*WIDTH+genCol)])
					);          
				end
				else if(genCol == (WIDTH-1)) begin : NoEast
					CoreWithTxRx coreWithTxRx(
						.Clk_r(Clk_r), .Clk_s(Clk_s), .Rst_n(Rst_n),
	  				//Node I/O
						.Packet_From_Node_Valid(Packet_From_Node_Valid[(genRow*WIDTH+genCol)]),
			  			.Packet_From_Node(Packet_From_Node[(genRow*WIDTH+genCol)]),
			  			.Packet_To_Node(Packet_To_Node[(genRow*WIDTH+genCol)]),              
			  			.Packet_To_Node_Valid(Packet_To_Node_Valid[(genRow*WIDTH+genCol)]),
			 			.Core_Load_Ack(Core_Load_Ack[(genRow*WIDTH+genCol)]),
					// East bound 
						//TX I/O
						.E_Tx_Rx_Ready(1'b0),
						.E_Tx_Rx_Error(1'b0),
						.E_Tx_S_Data(E_S_Data[(genRow*WIDTH+genCol)]),
						//RX I/O
						.E_Rx_S_Data(1'b1),
						.E_Rx_Rx_Ready(E_Rx_Ready[(genRow*WIDTH+genCol)]),
						.E_Rx_Rx_Error(E_Rx_Error[(genRow*WIDTH+genCol)]), 
					// West bound 
						//TX I/O
						.W_Tx_Rx_Ready(E_Rx_Ready[(genRow*WIDTH+genCol)-1]), 
						.W_Tx_Rx_Error(E_Rx_Error[(genRow*WIDTH+genCol)-1]),
						.W_Tx_S_Data(W_S_Data[(genRow*WIDTH+genCol)]),
						//RX I/O
						.W_Rx_S_Data(E_S_Data[(genRow*WIDTH+genCol)-1]),
						.W_Rx_Rx_Ready(W_Rx_Ready[(genRow*WIDTH+genCol)]),
						.W_Rx_Rx_Error(W_Rx_Error[(genRow*WIDTH+genCol)]),  
					// South bound 
						//TX I/O
						.S_Tx_Rx_Ready(N_Rx_Ready[(genRow*WIDTH+genCol)-WIDTH]), 
						.S_Tx_Rx_Error(N_Rx_Error[(genRow*WIDTH+genCol)-WIDTH]),
						.S_Tx_S_Data(S_S_Data[(genRow*WIDTH+genCol)]),
						//RX I/O
						.S_Rx_S_Data(N_S_Data[(genRow*WIDTH+genCol)-WIDTH]),
						.S_Rx_Rx_Ready(S_Rx_Ready[(genRow*WIDTH+genCol)]),
						.S_Rx_Rx_Error(S_Rx_Error[(genRow*WIDTH+genCol)]),
					// North bound 
						//TX I/O
						.N_Tx_Rx_Ready(S_Rx_Ready[(genRow*WIDTH+genCol)+WIDTH]),
						.N_Tx_Rx_Error(S_Rx_Error[(genRow*WIDTH+genCol)+WIDTH]),
						.N_Tx_S_Data(N_S_Data[(genRow*WIDTH+genCol)]),
						//RX I/O
						.N_Rx_S_Data(S_S_Data[(genRow*WIDTH+genCol)+WIDTH]),
						.N_Rx_Rx_Ready(N_Rx_Ready[(genRow*WIDTH+genCol)]),
						.N_Rx_Rx_Error(N_Rx_Error[(genRow*WIDTH+genCol)])
					);          
				end
				else begin : AllConnected
					CoreWithTxRx coreWithTxRx(
					    .Clk_r(Clk_r), .Clk_s(Clk_s), .Rst_n(Rst_n),
					//Node I/O
					    .Packet_From_Node_Valid(Packet_From_Node_Valid[(genRow*WIDTH+genCol)]),
					    .Packet_From_Node(Packet_From_Node[(genRow*WIDTH+genCol)]),
					    .Packet_To_Node(Packet_To_Node[(genRow*WIDTH+genCol)]),
					    .Packet_To_Node_Valid(Packet_To_Node_Valid[(genRow*WIDTH+genCol)]),
					    .Core_Load_Ack(Core_Load_Ack[(genRow*WIDTH+genCol)]),
					// East bound 
					    //TX I/O
					    .E_Tx_Rx_Ready(W_Rx_Ready[(genRow*WIDTH+genCol)+1]),
					    .E_Tx_Rx_Error(W_Rx_Error[(genRow*WIDTH+genCol)+1]),
					    .E_Tx_S_Data(E_S_Data[(genRow*WIDTH+genCol)]),
					    //RX I/O
					    .E_Rx_S_Data(W_S_Data[(genRow*WIDTH+genCol)+1]),
					    .E_Rx_Rx_Ready(E_Rx_Ready[(genRow*WIDTH+genCol)]),
					    .E_Rx_Rx_Error(E_Rx_Error[(genRow*WIDTH+genCol)]),
					// West bound 
					    //TX I/O
					    .W_Tx_Rx_Ready(E_Rx_Ready[(genRow*WIDTH+genCol)-1]),
					    .W_Tx_Rx_Error(E_Rx_Error[(genRow*WIDTH+genCol)-1]),
					    .W_Tx_S_Data(W_S_Data[(genRow*WIDTH+genCol)]),
					    //RX I/O
					    .W_Rx_S_Data(E_S_Data[(genRow*WIDTH+genCol)-1]),
					    .W_Rx_Rx_Ready(W_Rx_Ready[(genRow*WIDTH+genCol)]),
					    .W_Rx_Rx_Error(W_Rx_Error[(genRow*WIDTH+genCol)]),
					// South bound 
					    //TX I/O
					    .S_Tx_Rx_Ready(N_Rx_Ready[(genRow*WIDTH+genCol)-WIDTH]),
					    .S_Tx_Rx_Error(N_Rx_Error[(genRow*WIDTH+genCol)-WIDTH]),
					    .S_Tx_S_Data(S_S_Data[(genRow*WIDTH+genCol)]),
					    //RX I/O
					    .S_Rx_S_Data(N_S_Data[(genRow*WIDTH+genCol)-WIDTH]),
					    .S_Rx_Rx_Ready(S_Rx_Ready[(genRow*WIDTH+genCol)]),
					    .S_Rx_Rx_Error(S_Rx_Error[(genRow*WIDTH+genCol)]),
					// North bound 
					    //TX I/O
					    .N_Tx_Rx_Ready(S_Rx_Ready[(genRow*WIDTH+genCol)+WIDTH]),
					    .N_Tx_Rx_Error(S_Rx_Error[(genRow*WIDTH+genCol)+WIDTH]),
					    .N_Tx_S_Data(N_S_Data[(genRow*WIDTH+genCol)]),
					    //RX I/O
					    .N_Rx_S_Data(S_S_Data[(genRow*WIDTH+genCol)+WIDTH]),
					    .N_Rx_Rx_Ready(N_Rx_Ready[(genRow*WIDTH+genCol)]),
					    .N_Rx_Rx_Error(N_Rx_Error[(genRow*WIDTH+genCol)])
					);
				end										
			end
		end
	endgenerate      

   	// S Clock generator
	initial begin
    	Clk_s = 1'b1;
    	forever begin
     		#CLK_S_PERIOD;
           	Clk_s = ~Clk_s;
       	end
   	end
   
   	// R Clock Generator
   	initial begin
       	Clk_r = 1'b1;
       	forever begin
           	#CLK_R_PERIOD;
           	Clk_r = ~Clk_r;
       	end
   	end
   
   	// Perform the test
   	parameter COMMAND_PACKET = 8'b1111_1111;
   	reg [3:0] xPos, yPos, Port_Enable; // Positions in X and Y and NSWE port enabled
   	reg [31:0] packetIn, Packet_Out;
   	initial begin
       	inFile = $fopen("input.txt", "r");
       	if(inFile == 0) begin $display("Could not open input.txt"); $stop; end
       	outFile = $fopen("output.txt", "w");
       	if(outFile == 0) begin $display("Could not open output.txt"); $fclose(inFile); $stop; end
       
       	// assert Reset
       	totalCycles = 0;
       	totalMessages = 0;
       	Rst_n = 1'b0;
       	for(row = 0; row < HEIGHT; row = row+1) begin
           	for(col = 0; col < WIDTH; col = col+1) begin
              	Packet_From_Node[row*WIDTH+col] = 32'b0;
              	Packet_From_Node_Valid[row*WIDTH+col] = 1'b0;
           	end
      	end
       	#(2*CLK_R_PERIOD);
       	totalCycles = totalCycles + 1;
       	Rst_n = 1'b1;
       	#(2*CLK_R_PERIOD);
       	totalCycles = totalCycles + 1;
      	 
       	// Let each core know where it is
       	for(row = 0; row < HEIGHT; row = row+1) begin
       	    for(col = 0; col < WIDTH; col = col+1) begin
       	        xPos = col;
       	        yPos = row;
       	        Packet_From_Node[row*WIDTH+col] = {COMMAND_PACKET, 16'b0, xPos, yPos};
       	        Packet_From_Node_Valid[row*WIDTH+col] = 1'b1;
       	        #(2*CLK_R_PERIOD);
       	        totalCycles = totalCycles + 1;
       	        
       	        while(~Core_Load_Ack[row*WIDTH+col]) begin
       	            #(2*CLK_R_PERIOD);
       	            totalCycles = totalCycles + 1;
       	            if(totalCycles == MAX_CYCLES) begin
       					$display("#################################################\n");
       	       			$display("FAILED: Test failed to complete in required time!\n");
       					$display("#################################################\n");
       	                $fclose(inFile);
       	                $fclose(outFile);
       	                $stop;
       	            end
       	        end
       	        Packet_From_Node_Valid[row*WIDTH+col] = 1'b0;
       	    end
       	end
      	 
       	#(2*CLK_R_PERIOD);
       	totalCycles = totalCycles + 1;
      	 
       	// Let each core know where it is
       	for(row = 0; row < HEIGHT; row = row+1) begin
       	    for(col = 0; col < WIDTH; col = col+1) begin
       	        xPos = col;
       	        yPos = row;
       	        Port_Enable[0] = (col != (WIDTH-1));  // Enable East port if not at right side
       	        Port_Enable[1] = (col != 0);          // Enable West port if not at left side
       	        Port_Enable[2] = (row != 0);          // Enable South port if not at the bottom
       	        Port_Enable[3] = (row != (HEIGHT-1)); // Enable North port if not at the top
       	        Packet_From_Node[row*WIDTH+col] = {COMMAND_PACKET, 8'b1, 12'b0, Port_Enable};
      	        Packet_From_Node_Valid[row*WIDTH+col] = 1'b1;
       	        #(2*CLK_R_PERIOD);
               	totalCycles = totalCycles + 1;
               
               	while(~Core_Load_Ack[row*WIDTH+col]) begin
                   	#(2*CLK_R_PERIOD);
                   	totalCycles = totalCycles + 1;
                   	if(totalCycles == MAX_CYCLES) begin
       					$display("#################################################\n");
              			$display("FAILED: Test failed to complete in required time!\n");
       					$display("#################################################\n");
                       	$fclose(inFile);
                       	$fclose(outFile);
                       	$stop;
                   	end
               	end
               	Packet_From_Node_Valid[row*WIDTH+col] = 1'b0;
        	end
		end
       
       	#10
       
       	// Send all the information given by the test file
       	while($fscanf(inFile, "Core:%h Data:%h\n", {xPos, yPos}, packetIn) != -1) begin
           	Packet_From_Node[yPos*WIDTH+xPos] = packetIn;
           	Packet_From_Node_Valid[yPos*WIDTH+xPos] = 1'b1;
           	#(2*CLK_R_PERIOD);
           	totalCycles = totalCycles + 1;
           	while(~Core_Load_Ack[yPos*WIDTH+xPos]) begin
           	    #(2*CLK_R_PERIOD);
               	totalCycles = totalCycles + 1;
               	if(totalCycles == MAX_CYCLES) begin
       				$display("###################################################################\n");
              		$display("FAILED: Went over cycle limit before finishing the send of packets!\n");
       				$display("###################################################################\n");
                   	$fclose(inFile);
                   	$fclose(outFile);
                   	$stop;
               	end
           	end
           	$fdisplay(outFile, "Sent: %h%h_%h%h_%h%h%h%h   Core: %h_%h   Time: %0t",  
                    	packetIn[31:28], packetIn[27:24], packetIn[23:20], packetIn[19:16], packetIn[15:12], 
						packetIn[11:8], packetIn[7:4], packetIn[3:0],
						xPos, yPos, $time);
           	Packet_From_Node_Valid[yPos*WIDTH+xPos] = 1'b0;
           	totalMessages = totalMessages + 1;
       	end
            
       	// Wait until we recieve all the sent messages
       	while(totalMessages != 0) begin
           	#(2*CLK_R_PERIOD);
           	totalCycles = totalCycles + 1;
           	if(totalCycles == MAX_CYCLES) begin
       			$display("##########################################\n");
              	$display("FAILED: Core did not receieve all packets!\n");
       			$display("##########################################\n");
              	$fclose(inFile);
              	$fclose(outFile);
            	$stop;
           	end
       	end
       
       	$fclose(inFile);
       	$fclose(outFile);
       	$display("###################################################################\n");
		$display("PASSED: This was a triumph!  I'm making a note here... Huge success\n");
       	$display("###################################################################\n");
       	$stop;
   	end	
   
   	// read the output values
   	always@(negedge Clk_r) begin
       	for(rowOut = 0; rowOut < HEIGHT; rowOut = rowOut+1) begin
          	for(colOut = 0; colOut < WIDTH; colOut = colOut+1) begin
             	if(Packet_To_Node_Valid[rowOut*WIDTH+colOut]) begin
                 	Packet_Out = Packet_To_Node[rowOut*WIDTH+colOut];
                	$fdisplay(outFile, "Rcvd: %h%h_%h%h_%h%h%h%h   Core: %h_%h   Time: %0t", 
                   	Packet_Out[31:28], Packet_Out[27:24], Packet_Out[23:20], Packet_Out[19:16], Packet_Out[15:12], 
					Packet_Out[11:8], Packet_Out[7:4], Packet_Out[3:0],
					colOut[3:0], rowOut[3:0], $time); 
                	totalMessages = totalMessages - 1;
             	end
          	end
      	end
  	end
endmodule
