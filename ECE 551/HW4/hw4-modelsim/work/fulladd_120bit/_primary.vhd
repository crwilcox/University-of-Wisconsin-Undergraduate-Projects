library verilog;
use verilog.vl_types.all;
entity fulladd_120bit is
    port(
        c_in            : in     vl_logic;
        c_out           : out    vl_logic;
        a               : in     vl_logic_vector(119 downto 0);
        b               : in     vl_logic_vector(119 downto 0);
        sum             : out    vl_logic_vector(119 downto 0)
    );
end fulladd_120bit;
