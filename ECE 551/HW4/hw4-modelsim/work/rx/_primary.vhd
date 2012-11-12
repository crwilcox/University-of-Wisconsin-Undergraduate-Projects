library verilog;
use verilog.vl_types.all;
entity rx is
    port(
        Clk_s           : in     vl_logic;
        Rst_n           : in     vl_logic;
        Core_Rcv_Ready  : in     vl_logic;
        S_Data          : in     vl_logic;
        RxData_Valid    : out    vl_logic;
        RxData          : out    vl_logic_vector(31 downto 0);
        Rx_Ready        : out    vl_logic;
        Rx_Error        : out    vl_logic
    );
end rx;
