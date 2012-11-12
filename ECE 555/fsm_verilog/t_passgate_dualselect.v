`timescale 1ns / 1ns
module t_passgate_dualselect();
    wire a,b;
    reg [2:0] inputs = 3'b000;
    passgate_dualselect DUT( .a(a), .b(b), .in(inputs[2]), .rd_enA(inputs[1]), .rd_enA_BAR(~inputs[1]), .rd_enB(inputs[0]),.rd_enB_BAR(~inputs[0]));
    

always begin
   #5 inputs = inputs + 1'b1; 
end
endmodule
