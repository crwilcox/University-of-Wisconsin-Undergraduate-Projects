
module rf (
           // Outputs
           read1data, read2data, err,
           // Inputs
           clk, rst, read1regsel, read2regsel, writeregsel, writedata, write
           );
   input clk, rst;
   input [2:0] read1regsel;
   input [2:0] read2regsel;
   input [2:0] writeregsel;
   input [15:0] writedata;
   input        write;
   
   output reg [15:0] read1data;
   output reg [15:0] read2data;
   output        err;

   wire [15:0] 	 in0, in1, in2, in3, in4, in5, in6, in7,
    		 out0, out1, out2, out3, out4, out5, out6, out7;
   
   reg [7:0] 	 wr_sel;
   reg 		 decerr, rd1err, rd2err;
   
   // registers   
   reg16 register0(.q(out0), .d(in0), .clk(clk),.rst(rst));
   reg16 register1(.q(out1), .d(in1), .clk(clk),.rst(rst));
   reg16 register2(.q(out2), .d(in2), .clk(clk),.rst(rst));
   reg16 register3(.q(out3), .d(in3), .clk(clk),.rst(rst));
   reg16 register4(.q(out4), .d(in4), .clk(clk),.rst(rst));
   reg16 register5(.q(out5), .d(in5), .clk(clk),.rst(rst));
   reg16 register6(.q(out6), .d(in6), .clk(clk),.rst(rst));
   reg16 register7(.q(out7), .d(in7), .clk(clk),.rst(rst));
   
   // write select decoder
   always @* casex({write, writeregsel[2:0]})
	       4'b0_??? : begin wr_sel = 8'h00; decerr = 0; end
	       4'b1_000 : begin wr_sel = 8'h01; decerr = 0; end
	       4'b1_001 : begin wr_sel = 8'h02; decerr = 0; end
	       4'b1_010 : begin wr_sel = 8'h04; decerr = 0; end
	       4'b1_011 : begin wr_sel = 8'h08; decerr = 0; end
	       4'b1_100 : begin wr_sel = 8'h10; decerr = 0; end
	       4'b1_101 : begin wr_sel = 8'h20; decerr = 0; end
	       4'b1_110 : begin wr_sel = 8'h40; decerr = 0; end
	       4'b1_111 : begin wr_sel = 8'h80; decerr = 0; end
	       default : decerr = 1;
	 endcase
   
   // write select
   assign in0 = wr_sel[0] ? writedata : out0;
   assign in1 = wr_sel[1] ? writedata : out1;
   assign in2 = wr_sel[2] ? writedata : out2;
   assign in3 = wr_sel[3] ? writedata : out3;
   assign in4 = wr_sel[4] ? writedata : out4;
   assign in5 = wr_sel[5] ? writedata : out5;
   assign in6 = wr_sel[6] ? writedata : out6;
   assign in7 = wr_sel[7] ? writedata : out7; 

   // read 1 mux
   always @* casex(read1regsel)
	       3'h0 : begin read1data = out0; rd1err = 0; end
	       3'h1 : begin read1data = out1; rd1err = 0; end
	       3'h2 : begin read1data = out2; rd1err = 0; end
	       3'h3 : begin read1data = out3; rd1err = 0; end
	       3'h4 : begin read1data = out4; rd1err = 0; end
	       3'h5 : begin read1data = out5; rd1err = 0; end
	       3'h6 : begin read1data = out6; rd1err = 0; end
	       3'h7 : begin read1data = out7; rd1err = 0; end
	       default : rd1err = 1;
	     endcase 
   
   // read 2 mux
   always @* casex(read2regsel)
         3'h0 : begin read2data = out0; rd2err = 0; end
	       3'h1 : begin read2data = out1; rd2err = 0; end
	       3'h2 : begin read2data = out2; rd2err = 0; end
	       3'h3 : begin read2data = out3; rd2err = 0; end
	       3'h4 : begin read2data = out4; rd2err = 0; end
	       3'h5 : begin read2data = out5; rd2err = 0; end
	       3'h6 : begin read2data = out6; rd2err = 0; end
	       3'h7 : begin read2data = out7; rd2err = 0; end
	       default : rd2err = 1;
	     endcase 
   //err
   assign err = (decerr | rd1err | rd2err);
   
endmodule // rf



