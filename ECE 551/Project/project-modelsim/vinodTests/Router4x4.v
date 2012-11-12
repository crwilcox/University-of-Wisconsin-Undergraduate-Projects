`timescale 1 ns / 10 ps
module Router4x4  (input Clk_r,Clk_s,Rst_n,
                   input [1:0] faultlevel,
                   input  [0:15] Packet_From_Node_Valid,
                   input  [0:(32*16)-1] Packet_From_Node,
                   output [0:(32*16)-1] Packet_To_Node,
                   output [0:15] Packet_To_Node_Valid,
                   output [0:15] Core_Load_Ack,
                   
                   input  [0:3] East_Tx_Rx_Ready,West_Tx_Rx_Ready,South_Tx_Rx_Ready,North_Tx_Rx_Ready,
                   input  [0:3] East_Tx_Rx_Error,West_Tx_Rx_Error,South_Tx_Rx_Error,North_Tx_Rx_Error,
                   output [0:3] East_Tx_S_Data,West_Tx_S_Data,South_Tx_S_Data,North_Tx_S_Data,
                   
                   input  [0:3] East_Rx_S_Data, West_Rx_S_Data,South_Rx_S_Data,North_Rx_S_Data,
                   output [0:3] East_Rx_Rx_Ready,West_Rx_Rx_Ready,South_Rx_Rx_Ready,North_Rx_Rx_Ready,
                   output [0:3] East_Rx_Rx_Error,West_Rx_Rx_Error,South_Rx_Rx_Error,North_Rx_Rx_Error
                                    
                  );

wire [31:0] pkt_fr_node [0:15];
wire [31:0] pkt_to_node [0:15];

assign Packet_To_Node = { pkt_to_node[0],pkt_to_node[1],pkt_to_node[2],pkt_to_node[3],
                       pkt_to_node[4],pkt_to_node[5],pkt_to_node[6],pkt_to_node[7],
                       pkt_to_node[8],pkt_to_node[9],pkt_to_node[10],pkt_to_node[11],
                       pkt_to_node[12],pkt_to_node[13],pkt_to_node[14],pkt_to_node[15] };
                       

  wire  [0:3] E_Tx_Rx_Ready [0:3],W_Tx_Rx_Ready [0:3],S_Tx_Rx_Ready [0:3],N_Tx_Rx_Ready [0:3];
  wire  [0:3] E_Tx_Rx_Error [0:3],W_Tx_Rx_Error [0:3],S_Tx_Rx_Error [0:3],N_Tx_Rx_Error [0:3];
  wire  [0:3] E_Tx_S_Data [0:3],W_Tx_S_Data [0:3],S_Tx_S_Data [0:3],N_Tx_S_Data [0:3];

  //Rx
  wire  [0:3] E_Rx_Rx_Ready [0:3],W_Rx_Rx_Ready [0:3],S_Rx_Rx_Ready [0:3],N_Rx_Rx_Ready [0:3];
  wire  [0:3] E_Rx_Rx_Error [0:3],W_Rx_Rx_Error [0:3],S_Rx_Rx_Error [0:3],N_Rx_Rx_Error [0:3];
  wire  [0:3] E_Rx_S_Data [0:3],W_Rx_S_Data [0:3],S_Rx_S_Data [0:3],N_Rx_S_Data [0:3];
                          
/////////////////////////////////////
/// Instantiate 4x4 (16) Routers
/////////////////////////////////////
// South I/O
assign S_Rx_S_Data[0][0] = South_Rx_S_Data[0];
assign S_Rx_S_Data[1][0] = South_Rx_S_Data[1];
assign S_Rx_S_Data[2][0] = South_Rx_S_Data[2];
assign S_Rx_S_Data[3][0] = South_Rx_S_Data[3];
assign South_Rx_Rx_Ready = {S_Rx_Rx_Ready[0][0],S_Rx_Rx_Ready[1][0],S_Rx_Rx_Ready[2][0],S_Rx_Rx_Ready[3][0]};
assign South_Rx_Rx_Error = {S_Rx_Rx_Error[0][0],S_Rx_Rx_Error[1][0],S_Rx_Rx_Error[2][0],S_Rx_Rx_Error[3][0]};

assign South_Tx_S_Data     = {S_Tx_S_Data[0][0],S_Tx_S_Data[1][0],S_Tx_S_Data[2][0],S_Tx_S_Data[3][0]};
assign S_Tx_Rx_Ready[0][0] = South_Tx_Rx_Ready[0];
assign S_Tx_Rx_Ready[1][0] = South_Tx_Rx_Ready[1];
assign S_Tx_Rx_Ready[2][0] = South_Tx_Rx_Ready[2];
assign S_Tx_Rx_Ready[3][0] = South_Tx_Rx_Ready[3];
assign S_Tx_Rx_Error[0][0] = South_Tx_Rx_Error[0];
assign S_Tx_Rx_Error[1][0] = South_Tx_Rx_Error[1];
assign S_Tx_Rx_Error[2][0] = South_Tx_Rx_Error[2];
assign S_Tx_Rx_Error[3][0] = South_Tx_Rx_Error[3];

// North I/O
assign N_Rx_S_Data[0][3] = North_Rx_S_Data[0];
assign N_Rx_S_Data[1][3] = North_Rx_S_Data[1];
assign N_Rx_S_Data[2][3] = North_Rx_S_Data[2];
assign N_Rx_S_Data[3][3] = North_Rx_S_Data[3];
assign North_Rx_Rx_Ready = {N_Rx_Rx_Ready[0][3],N_Rx_Rx_Ready[1][3],N_Rx_Rx_Ready[2][3],N_Rx_Rx_Ready[3][3]};
assign North_Rx_Rx_Error = {N_Rx_Rx_Error[0][3],N_Rx_Rx_Error[1][3],N_Rx_Rx_Error[2][3],N_Rx_Rx_Error[3][3]};

assign North_Tx_S_Data     = {N_Tx_S_Data[0][3],N_Tx_S_Data[1][3],N_Tx_S_Data[2][3],N_Tx_S_Data[3][3]};
assign N_Tx_Rx_Ready[0][3] = North_Tx_Rx_Ready[0];
assign N_Tx_Rx_Ready[1][3] = North_Tx_Rx_Ready[1];
assign N_Tx_Rx_Ready[2][3] = North_Tx_Rx_Ready[2];
assign N_Tx_Rx_Ready[3][3] = North_Tx_Rx_Ready[3];
assign N_Tx_Rx_Error[0][3] = North_Tx_Rx_Error[0];
assign N_Tx_Rx_Error[1][3] = North_Tx_Rx_Error[1];
assign N_Tx_Rx_Error[2][3] = North_Tx_Rx_Error[2];
assign N_Tx_Rx_Error[3][3] = North_Tx_Rx_Error[3];

//West I/O
assign W_Rx_S_Data[0]   = West_Rx_S_Data;
assign West_Rx_Rx_Ready = W_Rx_Rx_Ready[0];
assign West_Rx_Rx_Error = W_Rx_Rx_Error[0];

assign West_Tx_S_Data     = W_Tx_S_Data[0];
assign W_Tx_Rx_Ready[0]   = West_Tx_Rx_Ready;
assign W_Tx_Rx_Error[0]   = West_Tx_Rx_Error;

//East I/O
assign E_Rx_S_Data[3]   = East_Rx_S_Data;
assign East_Rx_Rx_Ready = E_Rx_Rx_Ready[3];
assign East_Rx_Rx_Error = E_Rx_Rx_Error[3];

assign East_Tx_S_Data     = E_Tx_S_Data[3];
assign E_Tx_Rx_Ready[3]   = East_Tx_Rx_Ready;
assign E_Tx_Rx_Error[3]   = East_Tx_Rx_Error;

wire S_Data_Faulty_0,S_Data_Faulty_1,S_Data_Faulty_2;

FaultyTx Faultych0(.Clk_s,.Rst_n,
                   .faultlevel,                     //00 no faults //50% faults //90% faults
                   .S_Data(E_Tx_S_Data[0][2]),      //(0,2) E
                   .S_Data_Faulty(S_Data_Faulty_0) );

FaultyTx Faultych1(.Clk_s,.Rst_n,
                   .faultlevel,                     //00 no faults //50% faults //90% faults
                   .S_Data(N_Tx_S_Data[2][2]),      //(2,2) N
                   .S_Data_Faulty(S_Data_Faulty_1) );

FaultyTx Faultych2(.Clk_s,.Rst_n,
                   .faultlevel,                     //00 no faults //50% faults //90% faults
                   .S_Data(S_Tx_S_Data[1][1]),      //(1,1) S
                   .S_Data_Faulty(S_Data_Faulty_2) );
                                        
genvar i,j;
generate 
  
for (i=0;i<4;i=i+1) begin
  for (j=0;j<3;j=j+1) begin
    if ((i==2) && (j == 0)) assign W_Rx_S_Data[j+1][i] =  S_Data_Faulty_0;
    else                    assign W_Rx_S_Data[j+1][i] = E_Tx_S_Data[j][i];       //E_Tx[0] -> W_Rx[1], E_Rx[0] <- W_Tx[1]
      
      assign E_Tx_Rx_Ready[j][i] = W_Rx_Rx_Ready[j+1][i];
      assign E_Tx_Rx_Error[j][i] = W_Rx_Rx_Error[j+1][i];
   
    
    assign E_Rx_S_Data[j][i]     = W_Tx_S_Data[j+1][i];
    assign W_Tx_Rx_Ready[j+1][i] = E_Rx_Rx_Ready[j][i];
    assign W_Tx_Rx_Error[j+1][i] = E_Rx_Rx_Error[j][i];
  end
end

for (i=0;i<3;i=i+1) begin
  for (j=0;j<4;j=j+1) begin
    if ((i==2) && (j==2))  assign S_Rx_S_Data[j][i+1] = S_Data_Faulty_1; 
    else                   assign S_Rx_S_Data[j][i+1] = N_Tx_S_Data[j][i];
      
    assign N_Tx_Rx_Ready[j][i] = S_Rx_Rx_Ready[j][i+1];
    assign N_Tx_Rx_Error[j][i] = S_Rx_Rx_Error[j][i+1];
    
    if ((i==0) && (j==1))  assign N_Rx_S_Data[j][i]     = S_Data_Faulty_2;
    else                   assign N_Rx_S_Data[j][i]     = S_Tx_S_Data[j][i+1];
      
    assign S_Tx_Rx_Ready[j][i+1] = N_Rx_Rx_Ready[j][i];
    assign S_Tx_Rx_Error[j][i+1] = N_Rx_Rx_Error[j][i];
  end
end
    

  for (i=0;i<16;i=i+1) begin
  assign pkt_fr_node[i] = Packet_From_Node[(i*32):(i*32)+31];
 end  

  for (i=0;i<4;i=i+1) begin  : Corei
    for (j=0;j<4;j=j+1) begin  : Corej  // j WEPOS , i NSPOS
CoreWithTxRx_Post
      Coreinst( .Clk_r,.Clk_s,.Rst_n,
              //Node
              .Packet_From_Node_Valid (Packet_From_Node_Valid[(i*4)+j]),
              .Packet_From_Node       (pkt_fr_node           [(i*4)+j]),
              .Packet_To_Node         (pkt_to_node           [(i*4)+j]),  
              .Packet_To_Node_Valid   (Packet_To_Node_Valid  [(i*4)+j]) ,
              .Core_Load_Ack          (Core_Load_Ack         [(i*4)+j]),

              //TX
              .E_Tx_Rx_Ready          (E_Tx_Rx_Ready[j][i]),
              .W_Tx_Rx_Ready          (W_Tx_Rx_Ready[j][i]),
              .S_Tx_Rx_Ready          (S_Tx_Rx_Ready[j][i]),
              .N_Tx_Rx_Ready          (N_Tx_Rx_Ready[j][i]),
              .E_Tx_Rx_Error          (E_Tx_Rx_Error[j][i]),
              .W_Tx_Rx_Error          (W_Tx_Rx_Error[j][i]),
              .S_Tx_Rx_Error          (S_Tx_Rx_Error[j][i]),
              .N_Tx_Rx_Error          (N_Tx_Rx_Error[j][i]),
              .E_Tx_S_Data            (E_Tx_S_Data[j][i]),
              .W_Tx_S_Data            (W_Tx_S_Data[j][i]),
              .S_Tx_S_Data            (S_Tx_S_Data[j][i]),
              .N_Tx_S_Data            (N_Tx_S_Data[j][i]),

              //RX
              .E_Rx_S_Data            (E_Rx_S_Data[j][i]),
              .W_Rx_S_Data            (W_Rx_S_Data[j][i]),
              .S_Rx_S_Data            (S_Rx_S_Data[j][i]),
              .N_Rx_S_Data            (N_Rx_S_Data[j][i]),
              .E_Rx_Rx_Ready          (E_Rx_Rx_Ready[j][i]),
              .W_Rx_Rx_Ready          (W_Rx_Rx_Ready[j][i]),
              .S_Rx_Rx_Ready          (S_Rx_Rx_Ready[j][i]),
              .N_Rx_Rx_Ready          (N_Rx_Rx_Ready[j][i]),
              .E_Rx_Rx_Error          (E_Rx_Rx_Error[j][i]),
              .W_Rx_Rx_Error          (W_Rx_Rx_Error[j][i]),
              .S_Rx_Rx_Error          (S_Rx_Rx_Error[j][i]),
              .N_Rx_Rx_Error          (N_Rx_Rx_Error[j][i])
               
              );
    end
  end              
  
endgenerate

endmodule