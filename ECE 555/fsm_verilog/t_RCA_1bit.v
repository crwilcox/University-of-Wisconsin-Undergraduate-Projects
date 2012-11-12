
`timescale 1ns / 1ns 

module t_RCA_1bit(); 

wire cout,sum;
reg [3:0]in = 4'b0000;
RCA_1bit DUT(.cout(cout),.Z(sum),.a(in[2]), .b(in[1]), .cin(in[0]), .add_en(in[3]), .add_en_BAR(~in[3]));


always begin
   #5 in = in + 1'b1;
   if (in==4'b1111)
      #5 $stop; 
end
endmodule