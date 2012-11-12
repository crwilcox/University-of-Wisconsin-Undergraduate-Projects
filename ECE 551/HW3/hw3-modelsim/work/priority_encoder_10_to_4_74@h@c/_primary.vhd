library verilog;
use verilog.vl_types.all;
entity priority_encoder_10_to_4_74HC is
    port(
        A0_n            : in     vl_logic;
        A1_n            : in     vl_logic;
        A2_n            : in     vl_logic;
        A3_n            : in     vl_logic;
        A4_n            : in     vl_logic;
        A5_n            : in     vl_logic;
        A6_n            : in     vl_logic;
        A7_n            : in     vl_logic;
        A8_n            : in     vl_logic;
        Y3_n            : out    vl_logic;
        Y2_n            : out    vl_logic;
        Y1_n            : out    vl_logic;
        Y0_n            : out    vl_logic
    );
end priority_encoder_10_to_4_74HC;
