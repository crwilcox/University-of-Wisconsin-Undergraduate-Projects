#!/bin/bash

vlib work

#vlog ../../rx.v ../../tx.v ../../core_link_control.v ../../core_node_manager.v ../../core_packet_queue.v ../../core_routing_state_machine.v ../../core.v ../../CoreWithTxRx.v CoreWithTxRx_tb.v
#vsim -c -do run_CoreWithTxRx_tb.do CoreWithTxRx_tb -wlf CoreWithTxRx_tb.wlf

vlog ../../netlists/CoreWithTxRx_Post.v CoreWithTxRx_Post_tb.v
vsim -c -do run_CoreWithTxRx_Post_tb.do CoreWithTxRx_Post_tb -wlf CoreWithTxRx_Post_tb.wlf
