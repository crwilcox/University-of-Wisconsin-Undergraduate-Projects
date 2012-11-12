`timescale 1ns / 1ns
module t_dff();
    
reg clk = 1'b0;
reg d;
wire q;
dff DUT( .Q(q), .D(d), .clk(clk) );
always begin
    #5 clk = ~clk;
end

//Test 0>0>1>1>0
initial begin
    #15 d = 1'b0;
    @(posedge clk)
    if (q!= 1'b0) begin
       $display("FAIL");
       $stop;
   end
   @(negedge clk)
   @(posedge clk)
   #1;
  if (q!= 1'b0) begin
       $display("FAIL");
       $stop;
   end
  @(negedge clk)
  d = 1'b1;
  @(posedge clk)
    #1;
    if (q!= 1'b1) begin
       $display("FAIL");
       $stop;
   end
   @(negedge clk)
   @(posedge clk)
   #1;
  if (q!= 1'b1) begin
       $display("FAIL");
       $stop;
   end
  @(negedge clk)
  d = 1'b0;
  @(posedge clk)
  #1;
 if (q!= 1'b0) begin
    $display("FAIL");
    $stop;
   end
   $display("PASS");
   $stop;
   end //End initial
endmodule
