library verilog;
use verilog.vl_types.all;
entity p1_mealy is
    port(
        A               : in     vl_logic;
        clk             : in     vl_logic;
        rst_n           : in     vl_logic;
        Y               : out    vl_logic
    );
end p1_mealy;
