library verilog;
use verilog.vl_types.all;
entity prime_detector_structural is
    port(
        in_bit          : in     vl_logic;
        clk             : in     vl_logic;
        rst             : in     vl_logic;
        prime           : out    vl_logic
    );
end prime_detector_structural;
