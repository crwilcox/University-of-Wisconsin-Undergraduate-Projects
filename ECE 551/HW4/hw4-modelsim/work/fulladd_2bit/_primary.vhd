library verilog;
use verilog.vl_types.all;
entity fulladd_2bit is
    port(
        a               : in     vl_logic_vector(1 downto 0);
        b               : in     vl_logic_vector(1 downto 0);
        c_in            : in     vl_logic;
        c_out           : out    vl_logic;
        sum             : out    vl_logic_vector(1 downto 0)
    );
end fulladd_2bit;
