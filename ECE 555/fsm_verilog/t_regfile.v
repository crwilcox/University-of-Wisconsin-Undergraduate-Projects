`timescale 1ns / 1ns
module t_regfile();
    reg [4:0] wr_en;
    reg [4:0] rd_enA;
    reg [4:0] rd_enB;
    reg clk = 1'b0;
    reg [31:0] Z;
    wire [31:0] a;
    wire [31:0] b;
    regfile DUT(.a(a), .b(b), .Z(Z), .clk(clk), .rd_enA(rd_enA), .rd_enB(rd_enB), .wr_en(wr_en));
    
    always begin
     #5 clk = ~clk;
    end
    
    initial begin
    rd_enA = 5'b00000;
    rd_enB = 5'b00000;
    wr_en = 5'b00000;
    #1
    //Check for Hi_Z on outputs
        if (a!=32'hZZZZZZZZ || b!=32'hZZZZZZZZ)
       $stop;
    
    
    //Write All Zeros
    @(negedge clk)
    wr_en = 5'b11111;
    Z = 32'h0;
    @(posedge clk)
    #1 wr_en = 5'b00000;
    Z = 32'hXXXXXXXX;
    
    //Do the following algorithm for each of the 5 reg's 
    //Read 0 on A, Read 0 on B, Write a value, Read on A, Read on B
    //Move On to Next Reg
    rd_enA = 5'b00001;
    rd_enB = 5'b00001;
    #1;
    if (a!=32'h0 || b!=32'h0)
       $stop;
    @(negedge clk)
    #1 Z=32'ha5a5a5a5;
    wr_en = 5'b00001;
    @(posedge clk)
    #1 wr_en = 5'b00000;
    Z = 32'hXXXXXXXX;
    if (a!=32'ha5a5a5a5 || b!=32'ha5a5a5a5)
       $stop;
       
    //REG1
    rd_enA = 5'b00010;
    rd_enB = 5'b00010;
    #1;
    if (a!=32'h0 || b!=32'h0)
       $stop;
    @(negedge clk)
    #1 Z=32'h5a5a5a5a;
    wr_en = 5'b00010;
    @(posedge clk)
    #1 wr_en = 5'b00000;
    Z = 32'hXXXXXXXX;
    if (a!=32'h5a5a5a5a || b!=32'h5a5a5a5a)
       $stop;
    
    //REG2
    #1 rd_enA = 5'b00100;
    rd_enB = 5'b00100;
    #1;
    if (a!=32'h0 || b!=32'h0)
       $stop;
    @(negedge clk)
    #1 Z=32'h33333333;
    wr_en = 5'b00100;
    @(posedge clk)
    #1 wr_en = 5'b00000;
    Z = 32'hXXXXXXXX;
    if (a!=32'h33333333 || b!=32'h33333333)
       $stop;

    //REG3
    #1 rd_enA = 5'b01000;
    rd_enB = 5'b01000;
    #1;
    if (a!=32'h0 || b!=32'h0)
       $stop;
    @(negedge clk)
    #1 Z=32'hFFFFFFFF;
    wr_en = 5'b01000;
    @(posedge clk)
    #1 wr_en = 5'b00000;
    Z = 32'hXXXXXXXX;
    if (a!=32'hFFFFFFFF || b!=32'hFFFFFFFF)
       $stop;
    
    //REG4
    #1 rd_enA = 5'b10000;
    rd_enB = 5'b10000;
    #1;
    if (a!=32'h0 || b!=32'h0)
       $stop;
    @(negedge clk)
    #1 Z=32'h12345678;
    wr_en = 5'b10000;
    @(posedge clk)
    #1 wr_en = 5'b00000;
    Z = 32'hXXXXXXXX;
    if (a!=32'h12345678 || b!=32'h12345678)
       $stop;
    
    
    //Test Selecting different outputs
    #1 rd_enA = 5'b01000;
    rd_enB = 5'b00100;
    #1;
     if (a!=32'hFFFFFFFF || b!=32'h33333333)
       $stop;
    $display("PASS");
    $stop;
        
end //END initial
endmodule
