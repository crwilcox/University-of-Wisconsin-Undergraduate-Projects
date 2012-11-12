library verilog;
use verilog.vl_types.all;
entity modulo_6 is
    port(
        original_value  : in     vl_logic_vector(2 downto 0);
        mod6_value      : out    vl_logic_vector(2 downto 0)
    );
end modulo_6;
