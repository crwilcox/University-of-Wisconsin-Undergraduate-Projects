
`timescale 1ns / 1ns
module t_ppgen_32();
    wire [31:0] Z;
    reg ppgen_en;
    reg [31:0] A;
    reg B;
    ppgen_32 DUT( .Z(Z), .A(A), .B(B), .ppgen_en(ppgen_en));
    
    
    always begin
        A = $random;
        #5;
    end
    
    initial begin
       ppgen_en = 1'b0;
       B = 1'b0;
       #20 B= 1'b1;
       #20 ppgen_en = 1'b1;
       #20 B=1'b0;
       #20 $stop;
    end
    
    always @ (*) begin
       #1;
       if (ppgen_en) begin
         if (Z!=(A&{32{B}}))
            $stop;
       end else begin
          if (Z!=32'hZZZZZZZZ) 
             $stop; 
       end
    end
endmodule
