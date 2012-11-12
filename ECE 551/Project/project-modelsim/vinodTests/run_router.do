vsim +notimingchecks -L /afs/engr.wisc.edu/apps/eda/modelsim_techlib/6.3.i386_linux26/flexstream.3.4/verilog/gflxp -L /afs/engr.wisc.edu/apps/eda/modelsim_techlib/6.3.i386_linux26/flexstream.3.4/verilog/udps -t ns -novopt work.tb_RouterCore4x4
add wave sim:/RouterCore4x4/*
run -all
quit -f
