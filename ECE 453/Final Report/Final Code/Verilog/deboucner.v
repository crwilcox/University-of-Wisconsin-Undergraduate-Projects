module debouncer(input clk, rst, bouncer, output reg value, sig_event);
    reg [10:0] samples;
    
	 reg prev_stable1;
	 reg prev_stable0;

    assign stable1 = &samples;    //True if all samples are 1
    assign stable0 = ~(|samples); //True if all samples are 0
	 
	 // On the rising edge of either of these signals, we have an event to deal with.
	 assign next_event = (~prev_stable1 && stable1) || (~prev_stable0 && stable0);
    assign next_value = (next_event ? stable1 : value);

    always @(posedge clk) begin
        if(rst) begin
				if(bouncer)
					samples = 11'hFFF;
				else
					samples = 0;
            value = bouncer;
            sig_event = 0;
				prev_stable1 = bouncer;
				prev_stable0 = ~bouncer;
        end else begin
            samples = {bouncer, samples[10:1]};
            value = next_value;
            sig_event = next_event;
				prev_stable1 = stable1;
				prev_stable0 = stable0;
        end
    end
endmodule
