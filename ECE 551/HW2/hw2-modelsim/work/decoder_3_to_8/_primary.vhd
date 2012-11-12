library verilog;
use verilog.vl_types.all;
entity decoder_3_to_8 is
    port(
        enable_hi       : in     vl_logic;
        A0              : in     vl_logic;
        A1              : in     vl_logic;
        A2              : in     vl_logic;
        O0              : out    vl_logic;
        O1              : out    vl_logic;
        O2              : out    vl_logic;
        O3              : out    vl_logic;
        O4              : out    vl_logic;
        O5              : out    vl_logic;
        O6              : out    vl_logic;
        O7              : out    vl_logic
    );
end decoder_3_to_8;
