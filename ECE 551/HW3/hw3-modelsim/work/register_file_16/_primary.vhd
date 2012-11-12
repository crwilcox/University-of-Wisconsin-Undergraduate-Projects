library verilog;
use verilog.vl_types.all;
entity register_file_16 is
    port(
        clk             : in     vl_logic;
        wr_en_1         : in     vl_logic;
        wr_en_2         : in     vl_logic;
        rst             : in     vl_logic;
        data_in_1       : in     vl_logic_vector(7 downto 0);
        data_in_2       : in     vl_logic_vector(7 downto 0);
        wr_addr_1       : in     vl_logic_vector(2 downto 0);
        wr_addr_2       : in     vl_logic_vector(2 downto 0);
        rd_addr         : in     vl_logic_vector(3 downto 0);
        data_out        : out    vl_logic_vector(7 downto 0)
    );
end register_file_16;
