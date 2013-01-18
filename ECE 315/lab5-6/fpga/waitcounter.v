//WAIT COUNTER MODULE
//Christopher Wilcox & James Nystrom
//This module is a hardware counter to help to take load off of the processor
module waiter (clock ,reset , enable , out);
input clock ;
input reset ;
input enable ;

output reg out ;

wire clock ;
wire reset ;
wire enable ;

reg [15:0] counter_out ;

always @ (posedge clock)
begin
  if (reset == 1'b1) begin
    counter_out <= #1 16'h0000;
  end
  // If enable, counter + 1
  else if (enable == 1'b1) begin
    counter_out <= #1 counter_out + 1;
  end
  if (counter_out == 4'hFFFF) out = 1;
  else if (counter_out != 4'hFFFF) out = 0;
end 

endmodule


