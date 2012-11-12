onerror {resume}
quietly WaveActivateNextPane {} 0
add wave -noupdate -format Literal /rx_tb/TransPacket2
add wave -noupdate -format Logic /rx_tb/rst
add wave -noupdate -format Logic /rx_tb/clk
add wave -noupdate -format Literal /rx_tb/TransPacket
add wave -noupdate -format Logic /rx_tb/S_Data
add wave -noupdate -format Literal /rx_tb/i
add wave -noupdate -format Logic /rx_tb/Core_Rcv_Ready
add wave -noupdate -divider Outputs
add wave -noupdate -format Logic /rx_tb/RxData_Valid
add wave -noupdate -format Logic /rx_tb/Rx_Ready
add wave -noupdate -format Literal /rx_tb/RxData
add wave -noupdate -format Logic /rx_tb/Rx_Error
add wave -noupdate -divider nets
add wave -noupdate -format Logic /rx_tb/RxData_Valid_net
add wave -noupdate -format Logic /rx_tb/Rx_Ready_net
add wave -noupdate -format Literal /rx_tb/RxData_net
add wave -noupdate -format Logic /rx_tb/Rx_Error_net
add wave -noupdate -divider {Error Signals}
add wave -noupdate -format Logic /rx_tb/RxData_Valid_Error
add wave -noupdate -format Logic /rx_tb/Rx_Ready_Error
add wave -noupdate -format Logic /rx_tb/RxData_Error
add wave -noupdate -format Logic /rx_tb/Rx_Error_Error
add wave -noupdate -format Logic /rx_tb/Global_Error
TreeUpdate [SetDefaultTree]
WaveRestoreCursors {{Cursor 1} {433715 ps} 0}
configure wave -namecolwidth 244
configure wave -valuecolwidth 100
configure wave -justifyvalue left
configure wave -signalnamewidth 2
configure wave -snapdistance 10
configure wave -datasetprefix 0
configure wave -rowmargin 4
configure wave -childrowmargin 2
configure wave -gridoffset 0
configure wave -gridperiod 1
configure wave -griddelta 40
configure wave -timeline 0
update
WaveRestoreZoom {0 ps} {1456128 ps}
