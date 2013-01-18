; Filename:     main.s 
; Author:       James Nystrom & Christopher Wilcox 
; Description:  Using PB1, PB2, DIP SWITCHES and DIGIT 4, 
;				Display a digit on a 7 segment display 
;				from its 4 bit binary input as selected from
;				the 8 DIP swtiches
;				if neither button is pushed, display lower
;				if PB1, display higher
;				if PB2, display 1's complement, lower
; 				if PB1&PB2, display 1's complement higher

	AREA    FLASH, CODE, READONLY
	ARM

	INCLUDE ADuC7026.inc			;MMR definitions
	EXPORT  __main

; main program start
__main

	LDR		R7, =(GPIO_MMR_BASE)	
	MOV		R0, #0						;set port P4 to GPIO	
   	STR		R0,[R7, #GP4CON]
	MOV		R0, #0xFF					;set P4.7-0 to outputs
   	STRB	R0,[R7, #GP4DAT+3]

	MOV		R0, #0						;set port P3 to GPIO	
   	STR		R0,[R7, #GP3CON]
	MOV		R0, #0x00					;set P3.7-0 to inputs
   	STRB	R0,[R7, #GP3DAT+3]

	MOV		R0, #0						;set port P2 to GPIO	
   	STR		R0,[R7, #GP2CON]
	MOV		R0, #0x00					;set P2.1-0 to inputs
   	STRB	R0,[R7, #GP2DAT+3]
;
;
main_loop

	LDRB R0,[R7,#GP3DAT]
	EOR R0,R0,#0xFF
	LDRB R2,[R7,#GP2DAT]
	AND R2,R2, #2_11

	CMP R2, #2_11 			;if neither button is pushed, display lower
	ANDEQ R0, R0, #2_1111

	CMP R2, #2_10			;if PB1, display higher
	ANDEQ R0, R0, #2_11110000
	ROREQ R0, R0, #4

	CMP R2, #2_01			;if PB2, display 1's complement, lower
	ANDEQ R0,R0, #2_1111
	EOREQ R0,R0,#0xF

	CMP R2, #2_00		 	; if PB1&PB2, display 1's complement higher
	ANDEQ R0,R0, #2_11110000
	ROREQ R0, R0, #4
	EOREQ R0,R0,#0xF

	CMP R0, #15 ;check if it is greater than 15
	MOVHI R0, #0x00 ;if so, make blank (all off)
	LDR R1, =(seglut) ;otherwise, do the look-up
	LDR R0, [R1, R0]

	STRB R0,[R7, #GP4DAT+2]

	B main_loop

; Lookup table for segment patterns 0-9, A-F
seglut  DCB 2_00111111, 2_00000110, 2_01011011, 2_01001111
		DCB 2_01100110, 2_01101101, 2_01111101, 2_00000111
		DCB 2_01111111, 2_01101111, 2_01110111, 2_01111100
		DCB 2_00111001, 2_01011110, 2_01111001, 2_01110001

	END
