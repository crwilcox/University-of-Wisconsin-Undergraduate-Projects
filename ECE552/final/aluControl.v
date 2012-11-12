module aluControl(alu_op, Cin, Op, inv1, inv2, err);

input[2:0] alu_op;
output reg Cin, inv1, inv2, err;
output reg [2:0] Op;

/* This is the way in which alu_op must be passed in
* ADD: 100
* SUB: 101
* OR:  110
* AND: 111
* SLL: 001
* ROL: 000
* ROR: 010
* SRA: 011
*/

	
	always@ *
	casex (alu_op[2:0])
		3'b000 : begin // ROL
			Op   = 3'b000;
			Cin  = 0; 
			inv1 = 0;
			inv2 = 0;
			err  = 0;
		end
		3'b001 : begin // SLL
			Op   = 3'b001;
			Cin  = 0; 
			inv1 = 0; 
			inv2 = 0; 
			err  = 0;
		end
		3'b010 : begin // ROR
			Op   = 3'b010;
			Cin  = 0; 
			inv1 = 0; 
			inv2 = 0; 
			err  = 0;
		end
		3'b011 : begin // SRA
			Op   = 3'b011;
			Cin  = 0; 
			inv1 = 0; 
			inv2 = 0; 
			err  = 0;
		end
		3'b100 : begin // ADD
			Op   = 3'b100;
			Cin  = 0; 
			inv1 = 0; 
			inv2 = 0; 
			err  = 0;
		end
		3'b101 : begin // SUB
			Op   = 3'b100;
			Cin  = 1;
			inv1 = 1;
			inv2 = 0;
			err  = 0;
		end
		3'b110 : begin // OR
			Op   = 3'b101;
			Cin  = 0;
			inv1 = 0;
			inv2 = 0;
			err  = 0;
		end
		3'b111 : begin // AND
			Op   = 3'b111;
			Cin  = 0;
			inv1 = 0;
			inv2 = 0;
			err  = 0;
		end

		default: begin
			err = 1;
		end

	endcase // case (in[2:0])

endmodule

