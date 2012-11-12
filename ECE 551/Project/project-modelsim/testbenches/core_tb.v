module core_tb();
	
//routerCore (Overall Router) Testbench
	//INPUTS	
	reg Clk_r, Rst_n;

	reg	N_Tx_Ready, S_Tx_Ready, W_Tx_Ready, E_Tx_Ready;
	reg	N_Tx_Error, S_Tx_Error, W_Tx_Error, E_Tx_Error;
	reg	N_RxData_Valid, S_RxData_Valid, W_RxData_Valid, E_RxData_Valid; 
	reg [31:0] N_RxData, S_RxData, W_RxData, E_RxData;
	reg [31:0] Packet_From_Node;
	reg 	Packet_From_Node_Valid;

	//OUTPUTS
	wire N_TxData_Valid, S_TxData_Valid, W_TxData_Valid, E_TxData_Valid;
	wire [31:0] N_TxData, S_TxData, W_TxData, E_TxData;
	wire N_Error_Ack, S_Error_Ack, W_Error_Ack, E_Error_Ack;
	wire N_Core_Rcv_Ready, S_Core_Rcv_Ready, W_Core_Rcv_Ready, E_Core_Rcv_Ready;
	
	wire Core_Load_Ack;
	wire [31:0] Packet_To_Node;
	wire Packet_To_Node_Valid;

	core_synth DUT (
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

	always #5 Clk_r = ~Clk_r;

	initial begin
	//INITIALIZE VALUES
		Clk_r = 1'b0;
		Rst_n = 1'b0;
		N_Tx_Ready= 1'b0; S_Tx_Ready= 1'b0; W_Tx_Ready= 1'b0; E_Tx_Ready = 1'b0;
		N_Tx_Error= 1'b0; S_Tx_Error= 1'b0; W_Tx_Error= 1'b0; E_Tx_Error = 1'b0;
		N_RxData_Valid= 1'b0; S_RxData_Valid= 1'b0; W_RxData_Valid= 1'b0; E_RxData_Valid = 1'b0;
		N_RxData = 32'b0; S_RxData = 32'b0; W_RxData = 32'b0; E_RxData = 32'b0;
		Packet_From_Node = 32'b0;
		Packet_From_Node_Valid = 1'b0;
	//Disable RESET
	#10
	Rst_n = 1'b1;
	#10 //wait a tick	

	//START TEST
		//Configure Address
		Packet_From_Node = 32'b1111_1111__0000_0000__0000_0000_0011_0011;//Address Configuration of 3,3
		Packet_From_Node_Valid = 1'b1;
		#10 //wait a cycle
		Packet_From_Node_Valid = 1'b0;//now deassert packetfromnodevalid
		#10 
		
		//Configure Links
		Packet_From_Node = 32'b1111_1111__0000_0001__0000_0000_0000_1111;//Link Config - All Enabled
		Packet_From_Node_Valid = 1'b1;
		#10 //wait a cycle
		Packet_From_Node_Valid = 1'b0;//now deassert packetfromnodevalid
		#10
		
		
		E_ReceivePacket(32'h63_00_0004);
		E_ReceivePacket(32'h03_00_0004);
		E_ReceivePacket(32'h36_00_0004);
		E_ReceivePacket(32'h30_00_0004);
		E_ReceivePacket(32'h33_00_0001);		
		
		W_ReceivePacket(32'h63_00_0003);
		W_ReceivePacket(32'h03_00_0003);
		W_ReceivePacket(32'h36_00_0003);
		W_ReceivePacket(32'h30_00_0003);
		W_ReceivePacket(32'h33_00_0001);
		
		S_ReceivePacket(32'h63_00_0002);
		S_ReceivePacket(32'h03_00_0002);
		S_ReceivePacket(32'h36_00_0002);
		S_ReceivePacket(32'h30_00_0002);
		S_ReceivePacket(32'h33_00_0001);	
		
		N_ReceivePacket(32'h63_00_0001);
		N_ReceivePacket(32'h03_00_0001);
		N_ReceivePacket(32'h36_00_0001);
		N_ReceivePacket(32'h30_00_0001);
		N_ReceivePacket(32'h33_00_0001);	
		
				//Now send some packets....
		Packet_From_Node = 32'h63_00_0000;//Send straight east
		Packet_From_Node_Valid = 1'b1;
		@(negedge Core_Load_Ack) begin
			Packet_From_Node = 32'h03_00_0000;//Send straight west
		end
		
		@(negedge Core_Load_Ack) begin
			Packet_From_Node = 32'h36_00_0000;//Send straight north
		end

		@(negedge Core_Load_Ack) begin
			Packet_From_Node = 32'h30_00_0000;//Send straight south
		end
		
		@(negedge Core_Load_Ack) begin
			Packet_From_Node_Valid = 1'b0; //now deassert packetfromnodevalid
		end
		
		
		N_SendPacket();
		S_SendPacket();
		W_SendPacket();
		E_SendPacket();
		
		N_SendPacket();
		S_SendPacket();
		W_SendPacket();
		E_SendPacket();
		
		N_SendPacket();
		S_SendPacket();
		W_SendPacket();
		E_SendPacket();
		
		N_SendPacket();
		S_SendPacket();
		W_SendPacket();
		E_SendPacket();
		
		N_SendPacket();
		S_SendPacket();
		W_SendPacket();
		E_SendPacket();
		
		#5000
		
		
		$stop;
		
	end


	task N_SendPacket;
	begin
		N_Tx_Ready = 1'b1;
		@(posedge N_TxData_Valid) begin
			N_Tx_Ready = 1'b0;
		end
	end
	endtask

	task S_SendPacket;
	begin	
		S_Tx_Ready = 1'b1;
		@(posedge S_TxData_Valid) begin
			S_Tx_Ready = 1'b0;
		end
	end
	endtask

	task W_SendPacket;
	begin
		W_Tx_Ready = 1'b1;
		@(posedge W_TxData_Valid) begin
			W_Tx_Ready = 1'b0;
		end
	end
	endtask

	task E_SendPacket;
	begin
		E_Tx_Ready = 1'b1;
		@(posedge E_TxData_Valid) begin
			E_Tx_Ready = 1'b0;
		end
	end
	endtask

	task N_ReceivePacket;
	input [31:0] packet;
	begin
		N_RxData = packet;	
		if(N_Core_Rcv_Ready) begin
			N_RxData_Valid = 1'b1;
		end 
		else begin
			@(posedge N_Core_Rcv_Ready) begin
				N_RxData_Valid = 1'b1;
			end
		end
	
		@(negedge N_Core_Rcv_Ready) begin
			N_RxData_Valid = 1'b0;
		end
	end
	endtask

	task S_ReceivePacket;
	input [31:0] packet;
	begin
		S_RxData = packet;	
		if(S_Core_Rcv_Ready) begin
			S_RxData_Valid = 1'b1;
		end 
		else begin
			@(posedge S_Core_Rcv_Ready) begin
				S_RxData_Valid = 1'b1;
			end
		end
	
		@(negedge S_Core_Rcv_Ready) begin
			S_RxData_Valid = 1'b0;
		end
	end
	endtask

	task W_ReceivePacket;
	input [31:0] packet;
	begin
		W_RxData = packet;	
		if(W_Core_Rcv_Ready) begin
			W_RxData_Valid = 1'b1;
		end 
		else begin
			@(posedge W_Core_Rcv_Ready) begin
				W_RxData_Valid = 1'b1;
			end
		end
	
		@(negedge W_Core_Rcv_Ready) begin
			W_RxData_Valid = 1'b0;
		end
	end
	endtask

	task E_ReceivePacket;
	input [31:0] packet;
	begin
		E_RxData = packet;	
		if(E_Core_Rcv_Ready) begin
			E_RxData_Valid = 1'b1;
		end 
		else begin
			@(posedge E_Core_Rcv_Ready) begin
				E_RxData_Valid = 1'b1;
			end
		end
	
		@(negedge E_Core_Rcv_Ready) begin
			E_RxData_Valid = 1'b0;
		end
	end
	endtask
endmodule