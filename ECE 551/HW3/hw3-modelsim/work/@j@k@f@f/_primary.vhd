library verilog;
use verilog.vl_types.all;
entity JKFF is
    port(
        j               : in     vl_logic;
        k               : in     vl_logic;
        clk             : in     vl_logic;
        rst_n           : in     vl_logic;
        q               : out    vl_logic;
        q_n             : out    vl_logic
    );
end JKFF;
