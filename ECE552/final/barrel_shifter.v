module barrel_shifter(In, Cnt, Op, Out);
   input [15:0] In;
   input [3:0] 	Cnt;
   input [1:0] 	Op;
   output [15:0] Out;

   wire [15:0] 	 In_s1, In_s2, In_s4, In_s8;

   assign In_s1 = Cnt[0] ?
		  Op == 2'b00 ? {In[14:0], In[15]} :
		  Op == 2'b01 ? {In[14:0], 1'b0} :
		  Op == 2'b10 ? {In[0], In[15:1]} :
		  {In[15], In[15:1]} : 
		  In[15:0];

   assign In_s2 = Cnt[1] ?
		  Op == 2'b00 ? {In_s1[13:0], In_s1[15:14]} :
		  Op == 2'b01 ? {In_s1[13:0], 2'b00} :
		  Op == 2'b10 ? {In_s1[1:0], In_s1[15:2]} : 
		  {{2{In_s1[15]}}, In_s1[15:2]} : 
		  In_s1[15:0];

   assign In_s4 = Cnt[2] ?
		  Op == 2'b00 ? {In_s2[11:0], In_s2[15:12]} :
		  Op == 2'b01 ? {In_s2[11:0], 4'h0} :
		  Op == 2'b10 ? {In_s2[3:0], In_s2[15:4]} : 
		  {{4{In_s2[15]}}, In_s2[15:4]} :
		  In_s2[15:0];
   
   assign In_s8 = Cnt[3] ?
		  Op == 2'b00 ? {In_s4[7:0], In_s4[15:8]} :
		  Op == 2'b01 ? {In_s4[7:0], 8'h00} :
		  Op == 2'b10 ? {In_s4[7:0], In_s4[15:8]} :
		  {{8{In_s4[15]}}, In_s4[15:8]} : 
		  In_s4[15:0];
   
   assign Out = In_s8;
   
endmodule // barrel_shifter

   
