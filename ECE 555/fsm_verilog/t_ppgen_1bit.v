`timescale 1ns / 1ns
module t_ppgen_1bit();
    wire Z;
    reg [2:0] in = 3'b000;
    ppgen_1bit DUT( .Z(Z), .A(in[0]), .B(in[1]), .ppgen_en(in[2]), .ppgen_en_BAR(~in[2]) );
    
    always begin
       #5 in = in + 1'b1; 
    end
endmodule
