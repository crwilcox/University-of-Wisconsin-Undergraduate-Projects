`timescale 1ns / 1ns 
module t_RCA_32();

reg add_en;
reg [31:0] a,b;
wire [31:0] Z;
RCA_32 DUT(.Z(Z), .a(a), .add_en(add_en), .b(b) );

    always begin
        a = $random;
        b = $random;
        #5;
    end

 always @ (*) begin
       #1;
       if (add_en) begin
         if (Z!=(a+b))
            $stop;
       end else begin
          if (Z!=32'hZZZZZZZZ) 
             $stop; 
       end
    end
    
    initial begin
        add_en = 1'b0;
        #20 add_en = 1'b1;
        #4000 $stop;
    end
    
endmodule
