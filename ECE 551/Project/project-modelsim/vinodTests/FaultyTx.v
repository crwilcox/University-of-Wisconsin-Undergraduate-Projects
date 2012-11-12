

`timescale 1 ns / 10 ps

module FaultyTx(input Clk_s,Rst_n,
          input [1:0] faultlevel,  //00 no faults //50% faults //90% faults
          input S_Data, 
          output reg S_Data_Faulty);
  
  localparam  IDLE                    = 3'b000,
              RX_DATABITS             = 3'b001,
              CHK_RXERROR             = 3'b010,
              WAIT_RCVREADY           = 3'b011;
           //   WAIT_RCVREADY_TOGO_LOW  = 3'b100; 
          
  reg [2:0]   zero_cntr;
  reg [5:0]   bit_cntr;
  reg [2:0]   state,nxt_state;
  reg [32:0]  rxpkt_reg;
  reg [31:0]  RxData;
  reg         clr_zerocntr,en_zerocntr,clr_bitcntr,en_bitcntr,
              shift_rxpktreg, nxt_Rx_Error,Rx_Error_delayed,clr_Rx_Error,RxData_Valid,en_errors_issued;
  reg [9:0]  pkts_issued;
  reg [4:0] ind;  //32 pkts is the limit
  reg [31:0] rcvdpkts [0:31];
  reg incr_pkts_issued;
  
  always @(posedge Clk_s, negedge Rst_n) begin
    if (!Rst_n) begin
        zero_cntr <= 3'b0;
        bit_cntr  <= 6'b0;
        rxpkt_reg <= 33'b0;
        state     <= IDLE;
        pkts_issued <= 0;
        ind         <= 0;
    end    
    else  begin
      state       <= nxt_state;
      
      if(clr_Rx_Error)  Rx_Error_delayed = 1'b0;
      else              Rx_Error_delayed = nxt_Rx_Error; 
        
      if (clr_zerocntr)       zero_cntr <= 3'b0;
      else if (en_zerocntr)   zero_cntr <= zero_cntr + 1;
      
      if (clr_bitcntr)        bit_cntr  <= 6'b0;
      else if (en_bitcntr)    bit_cntr  <= bit_cntr + 1;
        
      if(incr_pkts_issued)    pkts_issued <= pkts_issued + 1;
        
      if (shift_rxpktreg)rxpkt_reg <= {rxpkt_reg[31:0],S_Data};
      
       //if(incr_rcvdpkts) begin
       // rcvdpkts[ind] <= rxpkt_reg[32:1];
       // ind <= ind + 1;
       // $strobe("%t Rcvd Pkt: %h", $time, rxpkt_reg[32:1]);
      // end   
    end
  end
  
//assign Rx_Error = nxt_Rx_Error | Rx_Error_delayed;
    //assign Rx_Error = nxt_Rx_Error;
  //assign Rx_Error_out = Rx_Error;
  //assign Rx_Ready_out = Rx_Ready;
  
  always @ (*)  begin
    nxt_state     = state;
    clr_zerocntr  = 1'b0;
    en_zerocntr   = 1'b0;
    clr_bitcntr   = 1'b0;
    en_bitcntr    = 1'b0;
    shift_rxpktreg= 1'b0;
    //Rx_Ready      = 1'b0;   
    RxData        = 33'b0;
    nxt_Rx_Error  = 1'b0;
    RxData_Valid  = 1'b0;
    clr_Rx_Error  = 1'b0;
    en_errors_issued = 1'b0;
    incr_pkts_issued  = 0;
    S_Data_Faulty    = S_Data;
     
    case (state)
          IDLE: begin
                //  Rx_Ready  = 1'b1;
                  if (!S_Data) en_zerocntr = 1'b1;
                  else         clr_zerocntr = 1'b1;
                  if ((zero_cntr == 3'd4) && (!S_Data)) begin //5th zero detected correctly    
                    nxt_state = RX_DATABITS;
                    clr_zerocntr = 1'b1;
                    clr_bitcntr  = 1'b1; 
                  end  
                end
             RX_DATABITS: begin                           
                          shift_rxpktreg = 1'b1;
                          en_bitcntr = 1'b1;
                          
                          if(zero_cntr == 3'd4) begin        //5th bit after 4 zeroes is a stuff bit 
                            shift_rxpktreg = 1'b0;
                            en_bitcntr = 1'b0;
                          end
    
                          if (!S_Data)  en_zerocntr = 1'b1;  //count the no of zeros transmitted
                          else          clr_zerocntr = 1'b1;
                            
                          //33bit can be stuffed bit or parity bit.. move to CHK_RXERR on receiving parity bit  
                          if ((bit_cntr == 6'd32) && (shift_rxpktreg)) begin 
                            nxt_state = CHK_RXERROR;
                            clr_zerocntr = 1'b1;
                            clr_bitcntr = 1'b1;
                            
                            incr_pkts_issued = 1'b1;
                            if (faultlevel == 2'd1) begin
                             if ((pkts_issued%10) == 0)
                               S_Data_Faulty = ^(rxpkt_reg[31:0]); //issue  1%faults
                            end   
                            else if (faultlevel == 2'd2) begin
                              if ((pkts_issued%2) != 0)
                               S_Data_Faulty = ^(rxpkt_reg[31:0]); //issue  50%faults
                            end
                           else if (faultlevel == 2'd3) begin
                             if ((pkts_issued%10) != 0)
                               S_Data_Faulty = ^(rxpkt_reg[31:0]); //issue  90%faults
                          end            
                         end  //parity bit check if
                         
                        end
                      
            CHK_RXERROR: begin
                          if (rxpkt_reg[0] != ~^(rxpkt_reg[32:1])) begin
                            nxt_Rx_Error = 1'b1; end
                            //nxt_Rx_Error = $random;
                            //nxt_state = IDLE;
                          //end
                          //else begin
                           if (nxt_Rx_Error) en_errors_issued = 1'b1;
                           nxt_state = WAIT_RCVREADY;
                          //end 
                        end                         
                                       
            WAIT_RCVREADY: begin
                            //if (Core_Rcv_Ready) begin
                              RxData_Valid = 1'b1;
                              RxData       = rxpkt_reg[32:1];
                              //nxt_state    = WAIT_RCVREADY_TOGO_LOW;
                              nxt_state    = IDLE;
                              clr_Rx_Error  = 1'b1;
                            //  incr_rcvdpkts = 1'b1;
                            //end  
                        end 
            /*WAIT_RCVREADY_TOGO_LOW: begin
                                      RxData_Valid = 1'b1;
                                      RxData       = rxpkt_reg[32:1];
                                      if (!Core_Rcv_Ready) begin
                                        RxData_Valid = 1'b0;
                                        nxt_state    = IDLE;
                                      end  
                                    end
            */                        
      endcase
  end
  
endmodule    



