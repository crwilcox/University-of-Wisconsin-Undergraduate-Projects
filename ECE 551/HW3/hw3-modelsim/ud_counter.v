module ud_counter(input clk, rst_count, load_en, mode, input [2:0] upper_limit, output reg [2:0] count);

//This module implements a programmable up/down 3-bit counter.
//Below you will find a more in-depth description.

//This module contains a 3-bit register called count_limit. 

//If mode = 0, the counter counts up to count_limit, goes back to 0, and repeats. 
//If mode = 1, it counts down to 0, goes back to count_limit, and repeats. 

//Changing mode does not reset the current count value.
//If load_en = 1, count_limit is synchronously updated with the value of the upper_limit input. 

//It is assumed that upper_limit is unsigned and will never be set to 0.

//rst_count is a synchronous, high-active reset signal that resets the current count to 0. It does
//not reset the value of count_limit.

reg [2:0] count_limit;

always @(posedge clk) begin
	if(load_en == 1'b1) count_limit <= upper_limit;
	
	if(rst_count == 1'b1) count <= 1'b0;
	else begin
		case (mode)
			1'b0: 
				begin // count up to count_limit, back to zero, repeat
					//special case in case user changed upper_limit when count was out of range
					if(count >= count_limit) count <= 1'b0; 
					else count <= count + 1; 
				end
			
			1'b1: 
				begin // count down to zero, go back to count_limit, and repeat
					//special case in case user changed upper_limit when count was out of range
					if(count > count_limit || count == 1'b0) count <= count_limit;
					else count <= count - 1;
				end 
		endcase
	end
end

endmodule // ud_counter