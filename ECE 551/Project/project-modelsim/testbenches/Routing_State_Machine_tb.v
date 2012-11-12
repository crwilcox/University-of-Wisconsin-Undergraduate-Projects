module Routing_State_Machine_tb();
	//use 8 queues to mock up connections
	//force feed config
	
	reg Clk_r, Rst_n;
	reg Tx_N_Read_Ack, Tx_S_Read_Ack, Tx_W_Read_Ack, Tx_E_Read_Ack;

	reg  Rx_N_Write_Ack, Rx_S_Write_Ack, Rx_W_Write_Ack, Rx_E_Write_Ack;
	
	reg[31:0] Rx_N_Packet_In, Rx_S_Packet_In, Rx_W_Packet_In, Rx_E_Packet_In, NodeToSend_Packet_In;

	reg NodeToSend_Write_Ack;
	

	reg[7:0] Router_Address;
	reg[3:0] Link_Config;
	
	wire [31:0] Tx_N_Packet_Out, Tx_S_Packet_Out, Tx_W_Packet_Out, Tx_E_Packet_Out;//controlled by queue
	wire [31:0] Rx_N_Packet_Out, Rx_S_Packet_Out, Rx_W_Packet_Out, Rx_E_Packet_Out;//controlled by queue
	wire [31:0] NodeToSend_Packet_Out;//controlled by queue

	wire Tx_N_Write_Ack, Tx_S_Write_Ack, Tx_W_Write_Ack, Tx_E_Write_Ack;//Controlled by fsm
	wire Rx_N_Read_Ack, Rx_S_Read_Ack, Rx_W_Read_Ack, Rx_E_Read_Ack, NodeToSend_Read_Ack;//Controlled by fsm	
	wire[31:0] Tx_N_Packet_In, Tx_S_Packet_In, Tx_W_Packet_In, Tx_E_Packet_In, Packet_To_Node;  //written by fsm  



	Packet_Queue Tx_N (.Clk_r(Clk_r), .Rst_n(Rst_n), .Packet_In(Tx_N_Packet_In), .Read_Ack(Tx_N_Read_Ack), .Write_Ack(Tx_N_Write_Ack), 
					.Packet_Out(Tx_N_Packet_Out), .Empty(Tx_N_Empty), .Full(Tx_N_Full)); 
	Packet_Queue Rx_N (.Clk_r(Clk_r), .Rst_n(Rst_n), .Packet_In(Rx_N_Packet_In), .Read_Ack(Rx_N_Read_Ack), .Write_Ack(Rx_N_Write_Ack), 
	               .Packet_Out(Rx_N_Packet_Out), .Empty(Rx_N_Empty), .Full(Rx_N_Full)); 

	Packet_Queue Tx_S (.Clk_r(Clk_r), .Rst_n(Rst_n), .Packet_In(Tx_S_Packet_In), .Read_Ack(Tx_S_Read_Ack), .Write_Ack(Tx_S_Write_Ack), 
	               .Packet_Out(Tx_S_Packet_Out), .Empty(Tx_S_Empty), .Full(Tx_S_Full)); 
	Packet_Queue Rx_S (.Clk_r(Clk_r), .Rst_n(Rst_n), .Packet_In(Rx_S_Packet_In), .Read_Ack(Rx_S_Read_Ack), .Write_Ack(Rx_S_Write_Ack), 
					.Packet_Out(Rx_S_Packet_Out), .Empty(Rx_S_Empty), .Full(Rx_S_Full)); 

	Packet_Queue Tx_W (.Clk_r(Clk_r), .Rst_n(Rst_n), .Packet_In(Tx_W_Packet_In), .Read_Ack(Tx_W_Read_Ack), .Write_Ack(Tx_W_Write_Ack), 
					.Packet_Out(Tx_W_Packet_Out), .Empty(Tx_W_Empty), .Full(Tx_W_Full)); 
	Packet_Queue Rx_W (.Clk_r(Clk_r), .Rst_n(Rst_n), .Packet_In(Rx_W_Packet_In), .Read_Ack(Rx_W_Read_Ack), .Write_Ack(Rx_W_Write_Ack), 
					.Packet_Out(Rx_W_Packet_Out), .Empty(Rx_W_Empty), .Full(Rx_W_Full)); 

	Packet_Queue Tx_E (.Clk_r(Clk_r), .Rst_n(Rst_n), .Packet_In(Tx_E_Packet_In), .Read_Ack(Tx_E_Read_Ack), .Write_Ack(Tx_E_Write_Ack), 
					.Packet_Out(Tx_E_Packet_Out), .Empty(Tx_E_Empty), .Full(Tx_E_Full)); 
	Packet_Queue Rx_E (.Clk_r(Clk_r), .Rst_n(Rst_n), .Packet_In(Rx_E_Packet_In), .Read_Ack(Rx_E_Read_Ack), .Write_Ack(Rx_E_Write_Ack), 
					.Packet_Out(Rx_E_Packet_Out), .Empty(Rx_E_Empty), .Full(Rx_E_Full)); 

	Packet_Queue NodeToSend (.Clk_r(Clk_r), .Rst_n(Rst_n), .Packet_In(NodeToSend_Packet_In), .Read_Ack(NodeToSend_Read_Ack), .Write_Ack(NodeToSend_Write_Ack), 
					.Packet_Out(NodeToSend_Packet_Out), .Empty(NodeToSend_Empty), .Full(NodeToSend_Full)); 

	Routing_State_Machine DUT (.Clk_r(Clk_r), .Rst_n(Rst_n), .Router_Address(Router_Address), .Link_Config(Link_Config),
			.Packet_From_Link_N(Rx_N_Packet_Out), .Packet_From_Link_S(Rx_S_Packet_Out), 
			.Packet_From_Link_W(Rx_W_Packet_Out), .Packet_From_Link_E(Rx_E_Packet_Out), .Packet_From_Node(NodeToSend_Packet_Out),
			.RxQueue_Empty_N(Rx_N_Empty), .RxQueue_Empty_S(Rx_S_Empty), .RxQueue_Empty_W(Rx_W_Empty), .RxQueue_Empty_E(Rx_E_Empty),
			.TxQueue_Full_N(Tx_N_Full), .TxQueue_Full_S(Tx_S_Full), .TxQueue_Full_W(Tx_W_Full), .TxQueue_Full_E(Tx_E_Full),  
			.Node_Queue_Empty(NodeToSend_Empty),
			
			.Packet_To_Link_N(Tx_N_Packet_In), .Packet_To_Link_S(Tx_S_Packet_In), .Packet_To_Link_W(Tx_W_Packet_In), 
			.Packet_To_Link_E(Tx_E_Packet_In), .Packet_To_Node(Packet_To_Node),
			.TxQueue_Write_N(Tx_N_Write_Ack), .TxQueue_Write_S(Tx_S_Write_Ack), .TxQueue_Write_W(Tx_W_Write_Ack), .TxQueue_Write_E(Tx_E_Write_Ack),
			.RxQueue_Read_N(Rx_N_Read_Ack), .RxQueue_Read_S(Rx_S_Read_Ack), .RxQueue_Read_W(Rx_W_Read_Ack), .RxQueue_Read_E(Rx_E_Read_Ack),
			.Node_Read(NodeToSend_Read_Ack)	
);
	
	//when we want to check for sending back to the processor node, we can
	//just check the signal
	
	always #5 Clk_r = ~Clk_r;

	initial begin
	Link_Config <= 4'b1111;
	Router_Address <= {4'd3 , 4'd3};	
	Tx_N_Read_Ack <= 1'b0;
	Tx_S_Read_Ack <= 1'b0;
	Tx_W_Read_Ack <= 1'b0;
	Tx_E_Read_Ack <= 1'b0;
	Rx_N_Write_Ack <= 1'b0;
	Rx_S_Write_Ack <= 1'b0;
	Rx_W_Write_Ack <= 1'b0;
	Rx_E_Write_Ack <= 1'b0;
	Clk_r <= 1'b0;
	Rst_n <= 1'b0;
	#10
	Rst_n <= 1'b1;

	//now to try and route some packets	

	//put a packet in the node to send queue.  say it needs to go one north
		NodeToSend_Packet_In = { 4'd3, 4'd4, 8'd0, 16'hDEAD };// send to 3,4 or one straight north the packet DEAD
		NodeToSend_Write_Ack = 1'b1;
		#10
		NodeToSend_Write_Ack = 1'b0;
		//now this should send this packet to wherever we want
		#150

	//put a packet in the node to send to queue.  say it needs to go one west
		NodeToSend_Packet_In = { 4'd1, 4'd1, 8'd0, 16'hBEEF };// send to 3,4 or one straight north the packet DEAD
		NodeToSend_Write_Ack = 1'b1;
		#10
		NodeToSend_Write_Ack = 1'b0;
		//now this should send this packet to wherever we want
		#150
		
	//put a packet in the south, needs to go one north
		Rx_S_Packet_In = { 4'd3, 4'd4, 8'd0, 16'hCAFE };// send to 3,4 or one straight north the packet DEAD
		Rx_S_Write_Ack = 1'b1;
		#10
		Rx_S_Write_Ack = 1'b0;
		//now this should send this packet to wherever we want
		#150
	
	//put a packet in the west, needs to go one east
		Rx_W_Packet_In = { 4'd4, 4'd3, 8'd0, 16'hFEED };// send to 3,4 or one straight north the packet DEAD
		Rx_W_Write_Ack = 1'b1;
		#10
		Rx_W_Write_Ack = 1'b0;
		//now this should send this packet to wherever we want
		#150
	
	//put a packet in the east.  say it should go south
		Rx_E_Packet_In = { 4'd3, 4'd2, 8'd0, 16'hFACE };// send to 3,4 or one straight north the packet DEAD
		Rx_E_Write_Ack = 1'b1;
		#10
		Rx_E_Write_Ack = 1'b0;
		//now this should send this packet to wherever we want
		#150
	
	//put a packet in the east.  say this is the destination
		Rx_E_Packet_In = { 4'd3, 4'd3, 8'd0, 16'hDEED };// send to 3,4 or one straight north the packet DEAD
		Rx_E_Write_Ack = 1'b1;
		#10
		Rx_E_Write_Ack = 1'b0;
		//now this should send this packet to wherever we want
		#150
	#300	

	//now to do the same thing, but faster/more things in queues at the same time.  Result should be the same
	//put a packet in the node to send queue.  say it needs to go one north
		NodeToSend_Packet_In = { 4'd3, 4'd4, 8'd0, 16'hDAED };// send to 3,4 or one straight north the packet DEAD
		NodeToSend_Write_Ack = 1'b1;
		#10
		NodeToSend_Write_Ack = 1'b0;
	//put a packet in the node to send to queue.  say it needs to go one west
		NodeToSend_Packet_In = { 4'd2, 4'd3, 8'd0, 16'hFEEB };// send to 3,4 or one straight north the packet DEAD
		NodeToSend_Write_Ack = 1'b1;
		#10
		NodeToSend_Write_Ack = 1'b0;
	//put a packet in the south, needs to go one north
		Rx_S_Packet_In = { 4'd3, 4'd4, 8'd0, 16'hEFAC };// send to 3,4 or one straight north the packet DEAD
		Rx_S_Write_Ack = 1'b1;
		#10
		Rx_S_Write_Ack = 1'b0;
	//put a packet in the west, needs to go one east
		Rx_W_Packet_In = { 4'd4, 4'd3, 8'd0, 16'hDEEF };// send to 3,4 or one straight north the packet DEAD
		Rx_W_Write_Ack = 1'b1;
		#10
		Rx_W_Write_Ack = 1'b0;
	//put a packet in the east.  say it should go south
		Rx_E_Packet_In = { 4'd3, 4'd2, 8'd0, 16'hECAF };// send to 3,4 or one straight north the packet DEAD
		Rx_E_Write_Ack = 1'b1;
		#10
		Rx_E_Write_Ack = 1'b0;
	//put a packet in the east.  say this is the destination
		Rx_E_Packet_In = { 4'd3, 4'd3, 8'd0, 16'hEDDE };// send to 3,4 or one straight north the packet DEAD
		Rx_E_Write_Ack = 1'b1;
		#10
		Rx_E_Write_Ack = 1'b0;
	#500
	$stop;
	end	
endmodule
