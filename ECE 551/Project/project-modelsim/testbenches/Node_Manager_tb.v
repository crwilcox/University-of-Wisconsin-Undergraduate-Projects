module Node_Manager_tb();
	
	reg clk;
	reg rst;

	//Inputs
	reg [31:0] Packet_From_Node;
	reg Packet_From_Node_Valid;
	reg [31:0] Packet_From_Core;
	reg Read_Packet;
	
	//Outputs
	wire [7:0] Router_Address;
	wire [3:0] Link_Config;
	wire [31:0] Packet_To_Core;
	wire Core_Load_Ack;
	wire [31:0] Packet_To_Node;
	wire Packet_To_Node_Valid;
	wire Queue_Empty;
	
	

	Node_Manager DUT(.Clk_r(clk), .Rst_n(rst), 
					 .Packet_From_Core(Packet_From_Core), 
					 .Packet_From_Node(Packet_From_Node), .Packet_From_Node_Valid(Packet_From_Node_Valid),
					 .Read_Packet(Read_Packet),
					 .Router_Address(Router_Address), .Link_Config(Link_Config),
					 .Packet_To_Core(Packet_To_Core),
					 .Packet_To_Node(Packet_To_Node), .Packet_To_Node_Valid(Packet_To_Node_Valid), .Core_Load_Ack(Core_Load_Ack),
					 .Queue_Empty(Queue_Empty));
	
	integer i;

always
	#5  clk = ~clk;

initial
begin
	$display("Set up initial values");
	//Need to control following variables
	//clk, rst
	//[31:0]Packet_From_Core
	//[31:0]Packet_From_Node
	//Packet_From_Node_Valid
	//Read_Packet
	
	#10
	
	clk = 1'b0; 
	rst = 1'b0;
	Packet_From_Core = 32'b0;
	Packet_From_Node = 32'b0;
	Packet_From_Node_Valid = 1'b0;
	Read_Packet = 1'b0;
	
	#10
	rst = 1'b1; //disable reset
	
	#10
	/* Send Link Configuration Packet */
	Packet_From_Node = 32'hFF01_FFF9;
	Packet_From_Node_Valid = 1'b1;
	#10
	Packet_From_Node_Valid = 1'b0;
	
	#10
	/* Send Address Assignemnt Packet */
	Packet_From_Node = 32'hFF00_FF79;
	Packet_From_Node_Valid = 1'b1;
	#10
	Packet_From_Node_Valid = 1'b0;
	
	#10
	/* Send Invalid Command Packet */
	Packet_From_Node = 32'hFF12_FF79;
	Packet_From_Node_Valid = 1'b1;
	#10
	Packet_From_Node_Valid = 1'b0;
	#10
	
	/* Send Command packets in rapid succession */
	Packet_From_Node = 32'hFF01_FFF8;
	Packet_From_Node_Valid = 1'b1;
	#10
	Packet_From_Node = 32'hFF00_FF78;
	#10
	Packet_From_Node = 32'h1200_AAAA;
	#10
	Packet_From_Node_Valid = 1'b0;
	#10
	
	
	#10
	/* Send Message Packet */
	Packet_From_Node = 32'h1200_AAAA;
	Packet_From_Node_Valid = 1'b1;
	#10
	Packet_From_Node_Valid = 1'b0;
	#10
	Packet_From_Node = 32'h1200_AABA;
	Packet_From_Node_Valid = 1'b1;
	#10
	Packet_From_Node_Valid = 1'b0;
	#10
	Packet_From_Node = 32'h1200_AAAB;
	Packet_From_Node_Valid = 1'b1;
	#10
	Packet_From_Node_Valid = 1'b0;
	#10
	Packet_From_Node = 32'h1200_AABB;
	Packet_From_Node_Valid = 1'b1;
	#10
	Packet_From_Node_Valid = 1'b0;
	#10
	Packet_From_Node = 32'h1200_AAAC;
	Packet_From_Node_Valid = 1'b1;
	#10
	Packet_From_Node_Valid = 1'b0;
	#10
	Packet_From_Node = 32'h1200_AAAD;
	Packet_From_Node_Valid = 1'b1;
	#10
	
	#10
	/* Read Packet from Queue */
	Read_Packet = 1'b1;
	#10
	Read_Packet = 1'b0;
	#10
	Packet_From_Node_Valid = 1'b0;
	Read_Packet = 1'b1;
	#10
	Read_Packet = 1'b0;
	#10
	Read_Packet = 1'b1;
	#10
	Read_Packet = 1'b0;
	#10
	Read_Packet = 1'b1;
	#10
	Read_Packet = 1'b0;
	#10
	
	/* Send packets in rapid succession */
	Packet_From_Node = 32'h1200_AAAA;
	Packet_From_Node_Valid = 1'b1;
	#10
	Packet_From_Node = 32'h1200_AABA;
	#10
	Packet_From_Node = 32'h1200_AAAB;
	#10
	Packet_From_Node = 32'h1200_AABB;
	#10
	Packet_From_Node = 32'h1200_AABC;
	#10
	Packet_From_Node = 32'h1200_AABD;
	#10
	Packet_From_Node_Valid = 1'b0;
	#10
	
	#10
	/* Send Packets to Node */
	Packet_From_Core = 32'h1200_AAAA;
	#10
	Packet_From_Core = 32'h1200_AABA;
	#10
	Packet_From_Core = 32'h1200_AAAB;
	#10
	
	#30
	$stop;

end

endmodule
