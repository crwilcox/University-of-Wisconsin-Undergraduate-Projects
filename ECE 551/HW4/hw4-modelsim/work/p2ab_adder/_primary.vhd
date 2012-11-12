library verilog;
use verilog.vl_types.all;
entity p2ab_adder is
    generic(
        BITWIDTH        : integer := 64
    );
    port(
        c_in            : in     vl_logic;
        a               : in     vl_logic_vector;
        b               : in     vl_logic_vector;
        sum             : out    vl_logic_vector;
        c_out           : out    vl_logic
    );
end p2ab_adder;
