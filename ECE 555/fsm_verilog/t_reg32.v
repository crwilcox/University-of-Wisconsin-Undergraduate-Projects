
`timescale 1ns / 1ns
module t_reg32();
    
reg clk = 1'b0;
reg [31:0] d;
reg wr_en;
wire [31:0] q;
reg32 DUT( .Q(q), .D(d), .clk(clk), .wr_en(wr_en));
always begin
    #5 clk = ~clk;
end

//Test 0>0>1>1>0 Then try 1 without wr_en and then try 0 without wr_en
initial begin
    wr_en = 1'b1;
    #15 d = 32'h0;
    @(posedge clk)
    if (q!= 32'h0) begin
       $display("FAIL");
       $stop;
   end
   @(negedge clk)
   @(posedge clk)
   #1;
  if (q!= 32'h0) begin
       $display("FAIL");
       $stop;
   end
  @(negedge clk)
  d = 32'hFFFFFFFF;
  @(posedge clk)
    #1;
    if (q!= 32'hFFFFFFFF) begin
       $display("FAIL");
       $stop;
   end
   @(negedge clk)
   @(posedge clk)
   #1;
  if (q!= 32'hFFFFFFFF) begin
       $display("FAIL");
       $stop;
   end
  @(negedge clk)
  d = 32'hA5A5A5A5;
  @(posedge clk)
    #1;
    if (q!= 32'hA5A5A5A5) begin
       $display("FAIL");
       $stop;
   end 
   
  @(negedge clk)
  d = 32'h0;
  @(posedge clk)
  #1;
 if (q!= 32'h0) begin
    $display("FAIL");
    $stop;
   end
 
 //Check WR_EN
 @(negedge clk)
 d= 32'hFFFFFFFF;
 wr_en = 1'b0;
 @(posedge clk)
 #1;
 if (q!= 32'h0) begin
    $display("FAIL");
    $stop;
 end
 @(negedge clk)
 wr_en = 1'b1;
 @(posedge clk)
    #1;
    if (q!= 32'hFFFFFFFF) begin
       $display("FAIL");
       $stop;
   end
 @(negedge clk)
 wr_en = 1'b0;
 d = 32'h0;
 @(posedge clk)
    #1;
    if (q!= 32'hFFFFFFFF) begin
       $display("FAIL");
       $stop;
  end
   
   //Test Complete
   $display("PASS");
   $stop;
   end //End initial
endmodule
