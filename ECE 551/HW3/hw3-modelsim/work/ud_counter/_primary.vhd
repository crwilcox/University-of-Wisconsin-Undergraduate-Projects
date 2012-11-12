library verilog;
use verilog.vl_types.all;
entity ud_counter is
    port(
        clk             : in     vl_logic;
        rst_count       : in     vl_logic;
        load_en         : in     vl_logic;
        mode            : in     vl_logic;
        upper_limit     : in     vl_logic_vector(2 downto 0);
        count           : out    vl_logic_vector(2 downto 0)
    );
end ud_counter;
