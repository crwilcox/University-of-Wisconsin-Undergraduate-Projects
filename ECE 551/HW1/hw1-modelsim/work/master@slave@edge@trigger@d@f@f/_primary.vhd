library verilog;
use verilog.vl_types.all;
entity masterSlaveEdgeTriggerDFF is
    port(
        q               : out    vl_logic;
        q_n             : out    vl_logic;
        d               : in     vl_logic;
        clk             : in     vl_logic
    );
end masterSlaveEdgeTriggerDFF;
