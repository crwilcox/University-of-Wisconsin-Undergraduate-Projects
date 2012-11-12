`timescale 1ns / 1ns
module t_reg32_tworead();
    
reg clk = 1'b0;
reg [31:0] d;
reg wr_en,rd_enA,rd_enB;
wire [31:0] a;
wire [31:0] b;
reg32_tworead DUT( .a(a), .b(b), .D(d), .clk(clk), .rd_enA(rd_enA), .rd_enB(rd_enB), .wr_en(wr_en) );
always begin
    #5 clk = ~clk;
end

//In the middle it checks the rd_enB and A signals

//Test 0>0>1>1>0 Then try 1 without wr_en and then try 0 without wr_en
initial begin
    wr_en = 1'b1;
    rd_enB = 1'b1;
    rd_enA = 1'b0;
    #15 d = 32'h0;
    @(posedge clk)
    if (b!= 32'h0) begin
       $display("FAIL");
       $stop;
   end
  @(negedge clk)
  d = 32'hFFFFFFFF;
  @(posedge clk)
    #1;
    if (b!= 32'hFFFFFFFF) begin
       $display("FAIL");
       $stop;
   end
   @(negedge clk)
  d = 32'hA5A5A5A5;
  @(posedge clk)
    #1;
    if (b!= 32'hA5A5A5A5) begin
       $display("FAIL");
       $stop;
   end 
   
   //Look For High Z
   #1 rd_enB = 1'b0;
   rd_enA = 1'b0;
   #1;
    if ((b!= 32'hZZZZZZZZ)&&(a!= 32'hZZZZZZZZ)) begin
       $display("FAIL");
       $stop;
   end 
   #1; rd_enA = 1'b1;
   rd_enB = 1'b0;
      #1;
    if ((b!= 32'hZZZZZZZZ)&&(a!= 32'hA5A5A5A5)) begin
       $display("FAIL");
       $stop;
   end 
#1; rd_enB = 1'b1;
   rd_enA = 1'b0;
      #1;
    if ((a!= 32'hZZZZZZZZ)&&(b!= 32'hA5A5A5A5)) begin
       $display("FAIL");
       $stop;
   end 
   
   #1; rd_enB = 1'b1;
   rd_enA = 1'b1;
      #1;
    if ((a!= 32'hA5A5A5A5)&&(b!= 32'hA5A5A5A5)) begin
       $display("FAIL");
       $stop;
   end 
   
   
  @(negedge clk)
  d = 32'h0;
  rd_enA = 1'b1;
  @(posedge clk)
  #1;
 if (a!= 32'h0) begin
    $display("FAIL");
    $stop;
   end
 
 //Check WR_EN
 @(negedge clk)
 d= 32'hFFFFFFFF;
 wr_en = 1'b0;
 rd_enA = 1'b1;
 @(posedge clk)
 #1;
 if (a!= 32'h0) begin
    $display("FAIL");
    $stop;
 end
 @(negedge clk)
 wr_en = 1'b1;
 @(posedge clk)
    #1;
    if (a!= 32'hFFFFFFFF) begin
       $display("FAIL");
       $stop;
   end
 @(negedge clk)
 wr_en = 1'b0;
 d = 32'h0;
 @(posedge clk)
    #1;
    if (a!= 32'hFFFFFFFF) begin
       $display("FAIL");
       $stop;
  end
   
   //Test Complete
   $display("PASS");
   $stop;
end //End initial
endmodule
