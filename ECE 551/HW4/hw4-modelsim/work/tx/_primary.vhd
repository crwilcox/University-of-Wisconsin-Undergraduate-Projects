library verilog;
use verilog.vl_types.all;
entity tx is
    port(
        Clk_s           : in     vl_logic;
        Rst_n           : in     vl_logic;
        TxData_Valid    : in     vl_logic;
        TxData          : in     vl_logic_vector(31 downto 0);
        Error_Ack       : in     vl_logic;
        Rx_Ready        : in     vl_logic;
        Rx_Error        : in     vl_logic;
        Tx_Ready        : out    vl_logic;
        Tx_Error        : out    vl_logic;
        S_Data          : out    vl_logic
    );
end tx;
