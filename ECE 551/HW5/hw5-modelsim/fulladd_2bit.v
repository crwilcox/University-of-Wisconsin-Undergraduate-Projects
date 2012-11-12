//Dan Drusch
//Figure 4.2.0
//2-bit adder to be used in parts 2.a and 2.c

module fulladd_2bit(c_in, c_out, a, b, sum);
    input c_in;
    input[1:0] a, b;
    output reg c_out;
    output reg[1:0] sum;
    
    always @(a, b, c_in)
        {c_out, sum} = a + b + c_in;
    
endmodule
