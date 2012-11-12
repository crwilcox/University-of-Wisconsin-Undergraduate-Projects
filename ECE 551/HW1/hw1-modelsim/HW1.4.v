module masterSlaveEdgeTriggerDFF (q, q_n, d, clk);
	//This module implements a master-slave edge-trigger 
	//D flip-flop using only structural verilog and NAND gates
	
	input d, clk;
	output q, q_n;
	wire nand0, nand1, nand2, nand3, nand4, nand5;
	
	nand (clk_n, clk, clk);
	
	nand (nand0, d, clk);
	nand (nand1, nand0, clk);
	
	nand (nand2, nand0, nand3);
	nand (nand3, nand2, nand1);
	
	nand (nand4, nand2, clk_n);
	nand (nand5, nand3, clk_n);
	
	nand (q, nand4, q_n);
	nand (q_n, q, nand5);
	
endmodule
