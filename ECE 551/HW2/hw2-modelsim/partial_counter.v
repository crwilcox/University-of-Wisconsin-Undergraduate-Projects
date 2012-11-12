module partial_counter (input clk, rst, output [3:0] count);

	//This module implements a partial counter that has a synchronous reset and counts from 4'd3 to 4'd12
	
	wire [3:0] state;
	wire [3:0] nextState;
	
	//by clocking the reset, we can make it synchronous
	dff_async_rst dff_rst (clk, rst, 1'b0, clkd_rst);
	
	//this bank of flip flops will store the state we are in
	dff_async_rst dff0 (clk, nextState[0], clkd_rst, state[0]);
	dff_async_rst dff1 (clk, nextState[1], clkd_rst, state[1]);
	dff_async_rst dff2 (clk, nextState[2], clkd_rst, state[2]);
	dff_async_rst dff3 (clk, nextState[3], clkd_rst, state[3]);

	//here is the logic for determining what count should be based on current state
	//count[3] = state[3] + state[2]state[0] + state[2]state[1]
	assign count[3] = (state[3]) || (state[2] && state[0]) || (state[2] && state[1]);
	//count[2] = state[2](!state[1])(!state[0]) + state[3]state[0] + (!state[2])state[0] + (!state[2])state[1]
	assign count[2] = (state[2] && (!state[1]) && (!state[0])) || (state[3] && state[0]) || ((!state[2]) && state[0]) || ((!state[2]) && state[1]);
	//count[1] = state[1]state[0] + (!state[1])(!state[0])
	assign count[1] = (state[1] && state[0]) || ((!state[1]) && (!state[0]));
	//count[0] = (!state[0])
	assign count[0] = (!state[0]);
	
	
	//here is the logic for determining the next state based on the current state
	//nextState[3] = state[3](!state[0]) + state[2]state[1]state[0]
	assign nextState[3] = (state[3] && (!state[0])) || (state[2] && state[1] && state[0]);
	//nextState[2] = state[2](!state[1]) + state[2](!state[0]) + (!state[2])state[1]state[0]
	assign nextState[2] = (state[2] && (!state[1])) || (state[2] && (!state[0])) || ((!state[2]) && state[1] && state[0]);
	//nextState[1] = (!state[3])(!state[1])state[0]  + state[1](!state[0])
	assign nextState[1] = ((!state[3]) && (!state[1]) && state[0]) || (state[1] && (!state[0]));
	//nextState[0] = (!state[0])
	assign nextState[0] = (!state[0]);
	
endmodule
