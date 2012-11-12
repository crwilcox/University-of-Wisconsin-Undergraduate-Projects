
`timescale 1ns / 1ns 

module t_FA_SUMBAR(); 

wire cout,sum;
reg [2:0]in = 3'b000;
FA_SUMBAR DUT(.cout(cout),.SUM_BAR(sum),.a(in[2]), .b(in[1]), .cin(in[0]));

always begin
   #5 in = in + 1'b1;
   if (in==3'b111)
      #5 $stop; 
end
endmodule