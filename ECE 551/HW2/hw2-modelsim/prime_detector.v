module prime_detector (input in_bit, clk, rst, output prime);
	//this module looks at the last 3 input bits and computes if the number is prime.
	
	//declare all wires to be used
	wire s0,s1,s2,s3,s0_n,s1_n,s2_n,s3_n,in_bit_n;
	wire dff3_inbit_and0, dff3_inbit_and1, dff3_inbit_eqns, dff3_in;
	wire dff2_inbit_and0, dff2_inbit_and1, dff2_inbit_eqns, dff2_inbit_n_and0, dff2_inbit_n_and1, dff2_inbit_n_and2, dff2_inbit_n_eqns, dff2_inbit, dff2_inbit_n, dff2_in;
	wire dff1_inbit_eqns, dff1_inbit_n_and0, dff1_inbit_n_and1, dff1_inbit_n_and2, dff1_inbit_n_eqns, dff1_inbit, dff1_inbit_n, dff1_in;
	wire dff0_inbit_and0, dff0_inbit_and1, dff0_inbit_eqns, dff0_inbit_n_and0, dff0_inbit_n_eqns, dff0_inbit, dff0_inbit_n, dff0_in;

	wire prime_and0;
	
	not	(in_bit_n, in_bit);
	not	(s0_n, s0);
	not	(s1_n, s1);
	not	(s2_n, s2);
	not	(s3_n, s3);
	
	
//dff3_in = newState_3 = in_bit( (s3_n & s2) + (s3 & s2_n & s1_n) )
	and (dff3_inbit_and0, s3_n, s2);
	and (dff3_inbit_and1, s3, s2_n, s1_n);
	or (dff3_inbit_eqns, dff3_inbit_and0, dff3_inbit_and1);
	
	and (dff3_in, in_bit, dff3_inbit_eqns);
	
	dff_async_rst dff3 (clk, dff3_in, rst, s3);
	
//dff2_in = newState_2 = 	in_bit ( (s0 & s3_n & s2_n) + (s3_n & s2_n & s1)) 
//							+ in_bit_n ( (s3_n & s2 & s0_n) + (s3_n & s1 & s0_n) + (s3 & s2_n & s1_n))
	//dff2_inbit work
	and (dff2_inbit_and0, s0, s3_n, s2_n);
	and (dff2_inbit_and1, s3_n, s2_n, s1);
	or (dff2_inbit_eqns, dff2_inbit_and0, dff2_inbit_and1);
	and (dff2_inbit, in_bit, dff2_inbit_eqns);
	
	//dff2_inbit_n work
	and (dff2_inbit_n_and0, s3_n, s2, s0_n);
	and (dff2_inbit_n_and1, s3_n, s1, s0_n);
	and (dff2_inbit_n_and2, s3, s2_n, s1_n);
	or (dff2_inbit_n_eqns, dff2_inbit_n_and0, dff2_inbit_n_and1, dff2_inbit_n_and2);
	and (dff2_inbit_n, in_bit_n, dff2_inbit_n_eqns);
	
	//or the two together for a result
	or (dff2_in, dff2_inbit, dff2_inbit_n);
	
	dff_async_rst dff2 (clk, dff2_in, rst, s2);
	
//dff1_in = newState_1 = 	in_bit ( s3_n & s2_n & s0_n ) 
//							+ in_bit_n ( (s3 & s2_n & s1_n & s0_n) + (s3_n & s0) + (s3_n & s2 & s1_n))
	//dff1_inbit work
	and (dff1_inbit_eqns, s3_n, s2_n, s0_n);
	and (dff1_inbit, in_bit, dff1_inbit_eqns);
	
	//dff1_inbit_n work
	and (dff1_inbit_n_and0, s3, s2_n, s1_n, s0_n);
	and (dff1_inbit_n_and1, s3_n, s0);
	and (dff1_inbit_n_and2, s3_n, s2, s1_n);
	or (dff1_inbit_n_eqns, dff1_inbit_n_and0, dff1_inbit_n_and1, dff1_inbit_n_and2);
	and (dff1_inbit_n, in_bit_n, dff1_inbit_n_eqns);
	
	//or the two together for a result
	or (dff1_in, dff1_inbit, dff1_inbit_n);
	
	dff_async_rst dff1 (clk, dff1_in, rst, s1);
	
//dff0_in = newState_0 = 	in_bit ((s3_n & s2 & s0_n) + (s3 & s2_n & s1_n)) 
//							+ in_bit_n ( s3_n + (s2_n & s1_n))
	//dff0_inbit work
	and (dff0_inbit_and0, s3_n, s2, s0_n);
	and (dff0_inbit_and1, s3, s2_n, s1_n);
	or (dff0_inbit_eqns, dff0_inbit_and0, dff0_inbit_and1);
	and (dff0_inbit, in_bit, dff0_inbit_eqns);
	
	//dff0_inbit_n work
	and (dff0_inbit_n_and0, s2_n, s1_n);
	or (dff0_inbit_n_eqns, s3_n, dff0_inbit_n_and0);
	and (dff0_inbit_n, in_bit_n, dff0_inbit_n_eqns);
	
	//or the two together for a result
	or (dff0_in, dff0_inbit, dff0_inbit_n);
	
	dff_async_rst dff0 (clk, dff0_in, rst, s0);
	
//prime = s3 + (s2 & s1 & s0)
	and (prime_and0, s2, s1, s0);
	or	(prime, s3, prime_and0);
endmodule
