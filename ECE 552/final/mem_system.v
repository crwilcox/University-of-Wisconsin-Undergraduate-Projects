module mem_system(
   // Outputs
   DataOut, Done, Stall, CacheHit, err, 
   // Inputs
   Addr, DataIn, Rd, Wr, createdump, clk, rst
   );
   
   input [15:0] Addr;
   input [15:0] DataIn;
   input        Rd;
   input        Wr;
   input        createdump;
   input        clk;
   input        rst;
   
   output [15:0] DataOut;
   output reg Done;
   output reg Stall;
   output reg CacheHit;
   output err;

   reg 	  mem_rd, mem_wr, valid_in, comp, ctrl_c_en0, ctrl_c_wr,
	  offset_sel, c_data_in_sel, c_rd_sel, c_wr_sel, state_err,
	  ctrl_c_en1, next_victim, c_data_out_sel, next_target;
   
   
   reg [15:0] mem_addr;
   reg [3:0] next_state;
   reg [1:0] wib;
   
   
   wire valid0, valid1, dirty0, dirty1, hit0, hit1, c_err0, c_err1, mem_err;
   wire [4:0] tag_in;
   wire [7:0] index;
   wire [2:0] offset;
   wire [4:0] tag_out0, tag_out1, tag_out;
   wire [3:0] state;
   wire [15:0] c_data_in, mem_data_out, c_data_out0, c_data_out1;
   
   /* data_mem = 1, inst_mem = 0 *
    * needed for cache parameter */
   parameter mem_type = 0;
   // You must pass the mem_type parameter 
   // and createdump inputs to the 
   // cache modules

/////////////
// Defines //
/////////////
   localparam EXT = 0;
   localparam CTRL = 1;
   localparam IDLE = 4'h0;
   localparam HIT = 4'h1;
   localparam RETURN = 4'h2;
   localparam WB0 = 4'h3;
   localparam WB1 = 4'h4;
   localparam WB2 = 4'h5;
   localparam WB3 = 4'h6;
   localparam MISS0 = 4'h7;
   localparam MISS1 = 4'h8;
   localparam MISS2 = 4'h9;
   localparam MISS3 = 4'ha;
   localparam MISS4 = 4'hb;
   localparam MISS5 = 4'hc;
   localparam MISS6 = 4'hd;
         
   
/////////////   
// Modules //
/////////////

   cache #(0 + mem_type) c0(//inputs
			   .enable(c_en0),
			   .clk(clk),
			   .rst(rst),
			   .createdump(createdump),
			   .tag_in(tag_in),//4:0
			   .index(index),//7:0
			   .offset(offset),//2:0
			   .data_in(c_data_in),//15:0
			   .comp(comp),
			   .write(c_wr),
			   .valid_in(valid_in),
			   //outputs
			   .tag_out(tag_out0),//4:0
			   .data_out(c_data_out0),//15:0
			   .hit(hit0),
			   .dirty(dirty0),
			   .valid(valid0),
			   .err(c_err0)
			   );
   
   cache #(2 + mem_type) c1(//inputs
			   .enable(c_en1),
			   .clk(clk),
			   .rst(rst),
			   .createdump(createdump),
			   .tag_in(tag_in),//4:0
			   .index(index),//7:0
			   .offset(offset),//2:0
			   .data_in(c_data_in),//15:0
			   .comp(comp),
			   .write(c_wr),
			   .valid_in(valid_in),
			   //outputs
			   .tag_out(tag_out1),//4:0
			   .data_out(c_data_out1),//15:0
			   .hit(hit1),
			   .dirty(dirty1),
			   .valid(valid1),
			   .err(c_err1)
			   );
   
   four_bank_mem mem(//inputs
		     .clk(clk),
		     .rst(rst),
		     .createdump(createdump),
		     .addr(mem_addr),//15:0
		     .data_in(DataOut),//15:0
		     .wr(mem_wr),
		     .rd(mem_rd),
		     //ouputs
		     .data_out(mem_data_out),
		     .stall(),
		     .busy(),//3:0
		     .err(mem_err)
		     );

   dff state_reg[3:0](.clk(clk), .rst(rst), .d(next_state[3:0]), .q(state[3:0]));
   dff victim(.clk(clk), .rst(rst), .d(next_victim), .q(victimway));
   dff choice(.clk(clk), .rst(rst), .d(next_target), .q(target));
 
   
/////////////
// Assigns //
/////////////
   assign tag_in = Addr[15:11];
   assign index = Addr[10:3];
   assign offset = (offset_sel) ? {wib[1:0], 1'b0} : Addr[2:0];
   assign c_data_in = (c_data_in_sel) ? mem_data_out : DataIn[15:0];
   assign DataOut_sel = (c_data_out_sel) ? target : (hit1 & valid1);
   assign DataOut = (DataOut_sel) ? c_data_out1 : c_data_out0;
   assign c_en = (Rd | Wr);
   assign c_en0 = (c_rd_sel) ? ctrl_c_en0 : (Rd | Wr);
   assign c_en1 = (c_rd_sel) ? ctrl_c_en1 : (Rd | Wr);
   assign c_wr = (c_wr_sel) ? ctrl_c_wr : Wr;
   assign err = c_err0 | c_err1 | mem_err | state_err;
 
   assign choose_target = (valid0 & valid1) ? (victimway ^ 1'b1):
			  (valid0 ^ valid1) ? valid0 :
			  1'b0;

   assign miss = c_en & ~((hit0 & valid0) | (hit1 & valid1));
   assign full = (choose_target) ? (~hit1 & valid1 & dirty1) : (~hit0 & valid0 & dirty0);
   assign hit = c_en & ((hit0 & valid0) | (hit1 & valid1));
   assign tag_out = (target) ? tag_out1 : tag_out0;
   
   
/////////////////
// State Logic //
/////////////////
     always@*
       casex(state)
	 IDLE: begin
	    next_state = hit ? HIT :
			 full ? WB0 :
			 miss ? MISS1 :
			 IDLE;
	    
    	c_rd_sel = EXT;
	    c_wr_sel = EXT;
	    c_data_in_sel = EXT;
	    offset_sel = EXT;
	    c_data_out_sel = EXT; 
			
	    comp = 1;
	    Stall = miss | full;
	    Done = 0;
	    CacheHit = 0;
	    	    
	    mem_rd = ~full & miss;
	    mem_wr = 0;
	    mem_addr = {tag_in[4:0], index[7:0], 2'h0, 1'b0};

	    wib = 2'hx;
	    ctrl_c_en0 = 1'bx;
	    ctrl_c_en1 = 1'bx;
	    ctrl_c_wr = 1'bx;
	    
	    next_victim = victimway ^ (hit | miss | full);
	    next_target = choose_target;
	    
	    valid_in = 1'bx;	    
	    state_err = 0;
	 end
	 HIT: begin
	    next_state = hit ? HIT :
			 full ? WB0 :
			 miss ? MISS1 :
			 IDLE;
	    
    	c_rd_sel = EXT;
	    c_wr_sel = EXT;
	    c_data_in_sel = EXT;
	    offset_sel = EXT;
	    c_data_out_sel = EXT; 
			
	    comp = 1;
	    Stall = miss | full;
	    Done = 1;
	    CacheHit = 1;

	    mem_rd = ~full & miss;
	    mem_wr = 0;
	    mem_addr = {tag_in[4:0], index[7:0], 2'h0, 1'b0};

	    wib = 2'hx;
	    ctrl_c_en0 = 1'bx;
	    ctrl_c_en1 = 1'bx;
	    ctrl_c_wr = 1'bx;
	    
	    next_victim = victimway ^ (hit | miss | full);
	    next_target = choose_target;
	    
	    valid_in = 1'bx;	    
	    state_err = 0;
	 end
	 RETURN: begin
	    next_state = hit ? HIT :
			 full ? WB0 :
			 miss ? MISS1 :
			 IDLE;
	    
    	c_rd_sel = EXT;
	    c_wr_sel = EXT;
	    c_data_in_sel = EXT;
	    offset_sel = EXT;
	    c_data_out_sel = EXT; 
			
	    comp = 1;
	    Stall = miss | full;
	    Done = 1;
	    CacheHit = 0;
	    	    
	    mem_rd = ~full & miss;
	    mem_wr = 0;
	    mem_addr = {tag_in[4:0], index[7:0], 2'h0, 1'b0};

	    wib = 2'hx;
	    ctrl_c_en0 = 1'bx;
	    ctrl_c_en1 = 1'bx;
	    ctrl_c_wr = 1'bx;
	    
	    next_victim = victimway ^ (hit | miss | full);
	    next_target = choose_target;
	    
	    valid_in = 1'bx;	    
	    state_err = 0;
	 end
	 MISS0: begin
	    next_state = MISS1;

	    c_rd_sel = CTRL;
	    c_wr_sel = CTRL;
	    c_data_in_sel = CTRL;
	    offset_sel = CTRL;
	    c_data_out_sel = CTRL;
	    
	    wib = 2'hx;
	    ctrl_c_en0 = 0;
	    ctrl_c_en1 = 0;
	    ctrl_c_wr = 0;
	    comp = 1'bx;	    
	    valid_in = 1'bx;
	    
	    mem_rd = 1;
	    mem_wr = 0;
	    mem_addr = {tag_in[4:0], index[7:0], 2'h0, 1'b0};
	    
	    next_victim = victimway;
	    next_target = target;
	    	    
	    Stall = 1;
	    Done = 0;
	    CacheHit = 0;
	    state_err = 0;
	 end
	 MISS1: begin
	    next_state = MISS2;

	    c_rd_sel = CTRL;
	    c_wr_sel = CTRL;
	    c_data_in_sel = CTRL;
	    offset_sel = CTRL;
	    c_data_out_sel = CTRL;
	    
	    wib = 2'hx;
	    ctrl_c_en0 = 0;
	    ctrl_c_en1 = 0;
	    ctrl_c_wr = 0;
	    comp = 1'bx;	    
	    valid_in = 1'bx;
	    
	    mem_rd = 1;
	    mem_wr = 0;
	    mem_addr = {tag_in[4:0], index[7:0], 2'h1, 1'b0};

	    next_victim = victimway;
	    next_target = target;
	    
	    Stall = 1;
	    Done = 0;
	    CacheHit = 0;
	    state_err = 0;
	 end
	 MISS2: begin
	    next_state = MISS3;

	    c_rd_sel = CTRL;
	    c_wr_sel = CTRL;
	    c_data_in_sel = CTRL;
	    offset_sel = CTRL;
	    c_data_out_sel = CTRL;
	    
	    wib = 2'h0;
	    ctrl_c_en0 = ~target;
	    ctrl_c_en1 = target;
	    ctrl_c_wr = 1;
	    comp = 0;	    
	    valid_in = 0;
	    
	    mem_rd = 1;
	    mem_wr = 0;
	    mem_addr = {tag_in[4:0], index[7:0], 2'h2, 1'b0};

	    next_victim = victimway;
	    next_target = target;
	    
	    Stall = 1;
	    Done = 0;
	    CacheHit = 0;
	    state_err = 0;
	 end
	 MISS3: begin
	    next_state = MISS4;

	    c_rd_sel = CTRL;
	    c_wr_sel = CTRL;
	    c_data_in_sel = CTRL;
	    offset_sel = CTRL;
	    c_data_out_sel = CTRL;
	    
	    wib = 2'h1;
	    ctrl_c_en0 = ~target;
	    ctrl_c_en1 = target;
     	ctrl_c_wr = 1;
	    comp = 0;	    
	    valid_in = 0;
	    
	    mem_rd = 1;
	    mem_wr = 0;
	    mem_addr = {tag_in[4:0], index[7:0], 2'h3, 1'b0};

	    next_victim = victimway;
	    next_target = target;
	    
	    Stall = 1;
	    Done = 0;
	    CacheHit = 0;
	    state_err = 0;
	 end
	 MISS4: begin
	    next_state = MISS5;

	    c_rd_sel = CTRL;
	    c_wr_sel = CTRL;
	    c_data_in_sel = CTRL;
	    offset_sel = CTRL;
	    c_data_out_sel = CTRL;
	    
	    wib = 2'h2;
	    ctrl_c_en0 = ~target;
	    ctrl_c_en1 = target;
	    ctrl_c_wr = 1;
	    comp = 0;	    
	    valid_in = 0;
	    
	    mem_rd = 0;
	    mem_wr = 0;
	    mem_addr = 16'hxxxx;
	    
	    next_victim = victimway;
	    next_target = target;
	    
	    Stall = 1;
	    Done = 0;
	    CacheHit = 0;
	    state_err = 0;
	 end
	 MISS5: begin
	    // I believe going to MISS6 was a test issue for hw5 //
      // next_state = (Wr) ? MISS6 : RETURN;
      next_state = RETURN;

	    c_rd_sel = CTRL;
	    c_wr_sel = CTRL;
	    c_data_in_sel = CTRL;
	    offset_sel = CTRL;
	    c_data_out_sel = CTRL;
	    
	    wib = 2'h3;
	    ctrl_c_en0 = ~target;
	    ctrl_c_en1 = target;
	    ctrl_c_wr = 1;
	    comp = 0;	    
	    valid_in = 1;
	    	    
	    mem_rd = 0;
	    mem_wr = 0;
	    mem_addr = 16'hxxxx;
	    
	    next_victim = victimway;
	    next_target = target;
	    
	    Stall = 1;
	    Done = 0;	 
	    CacheHit = 0;
	    state_err = 0;  
	 end
	 MISS6: begin
	    next_state = RETURN;

	    c_rd_sel = EXT;
	    c_wr_sel = EXT;
	    c_data_in_sel = EXT;
	    offset_sel = EXT;
	    c_data_out_sel = EXT;
	    
	    wib = 2'hx;
	    ctrl_c_en0 = 1'bx;
	    ctrl_c_en1 = 1'bx;
	    ctrl_c_wr = 1'bx;
	    comp = 1;	    
	    valid_in = 1'bx;
	    	    
	    mem_rd = 0;
	    mem_wr = 0;
	    mem_addr = 16'hxxxx;

	    next_victim = victimway;
	    next_target = target;
	    
	    Stall = 1;
	    Done = 0;	 
	    CacheHit = 0;
	    state_err = 0;  
	 end
	 WB0: begin
	    next_state = WB1;
 
	    c_rd_sel = CTRL;
	    c_wr_sel = CTRL;
	    c_data_in_sel = CTRL;
	    offset_sel = CTRL;
	    c_data_out_sel = CTRL;
	    
	    wib = 2'h0;
	    ctrl_c_en0 = ~target;
	    ctrl_c_en1 = target;
	    ctrl_c_wr = 0;
	    comp = 0;	    
	    valid_in = 0;
	    	    
	    Stall = 1;
	    Done = 0;	 
	    CacheHit = 0;   

	    mem_wr = 1;
	    mem_rd = 0;
	    mem_addr = {tag_out[4:0], index[7:0], 2'h0, 1'b0};
	    	    
	    next_victim = victimway;
	    next_target = target;
	    
	    state_err = 0;  
	 end
	 WB1: begin
	    next_state = WB2;

	    c_rd_sel = CTRL;
	    c_wr_sel = CTRL;
	    c_data_in_sel = CTRL;
	    offset_sel = CTRL;
	    c_data_out_sel = CTRL;
	    
	    wib = 2'h1;
	    ctrl_c_en0 = ~target;
	    ctrl_c_en1 = target;
	    ctrl_c_wr = 0;
	    comp = 0;	    
	    valid_in = 0;
	    	    
	    mem_wr = 1;
	    mem_rd = 0;
	    mem_addr = {tag_out[4:0], index[7:0], 2'h1, 1'b0};

	    next_victim = victimway;
	    next_target = target;
	    
	    Stall = 1;
	    Done = 0;	    
	    CacheHit = 0;
	    state_err = 0;
	 end
	 WB2: begin
	    next_state = WB3;

	    c_rd_sel = CTRL;
	    c_wr_sel = CTRL;
	    c_data_in_sel = CTRL;
	    offset_sel = CTRL;
	    c_data_out_sel = CTRL;
	    
	    wib = 2'h2;
	    ctrl_c_en0 = ~target;
	    ctrl_c_en1 = target;
	    ctrl_c_wr = 0;
	    comp = 0;	    
	    valid_in = 0;
	    	    
	    mem_wr = 1;
	    mem_rd = 0;
	    mem_addr = {tag_out[4:0], index[7:0], 2'h2, 1'b0};

	    next_victim = victimway;
	    next_target = target;
	    
	    Stall = 1;
	    Done = 0;	    
	    CacheHit = 0;
	    state_err = 0;
	 end
	 WB3: begin
	    next_state = MISS0;

	    c_rd_sel = CTRL;
	    c_wr_sel = CTRL;
	    c_data_in_sel = CTRL;
	    offset_sel = CTRL;
	    c_data_out_sel = CTRL;
	    
	    wib = 2'h3;
	    ctrl_c_en0 = ~target;
	    ctrl_c_en1 = target;
	    ctrl_c_wr = 0;
	    comp = 0;	    
	    valid_in = 0;
	    
	    next_victim = victimway;
	    next_target = target;
	    
	    mem_wr = 1;
	    mem_rd = 0;
	    mem_addr = {tag_out[4:0], index[7:0], 2'h3, 1'b0};

	    Stall = 1;
	    Done = 0;
	    CacheHit = 0;
	    state_err = 0;
	 end
	 default: begin
	    next_state = 4'hx;

	    c_rd_sel = CTRL;
	    c_wr_sel = CTRL;
	    c_data_in_sel = CTRL;
	    offset_sel = CTRL;
	    c_data_out_sel = CTRL;
	    
	    wib = 2'hx;
	    ctrl_c_en0 = 1'bx;
	    ctrl_c_en1 = 1'bx;
	    ctrl_c_wr = 0;
	    comp = 0;	    
	    valid_in = 0;
	    	    
	    mem_wr = 0;
	    mem_rd = 0;
	    mem_addr = 16'hxxxx;

	    next_victim = victimway;
	    next_target = target;
	    
	    Stall = 1;
	    Done = 0;
	    CacheHit = 0;
	    state_err = 1;
	 end

       endcase // casex (state)
   
endmodule // mem_system

