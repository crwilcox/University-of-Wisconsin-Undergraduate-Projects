; Filename:     Step.s 
; Author:       Christopher Wilcox & James Nystrom 
; Description:  handles the stepping of the stepping motor
	;The subroutine Step takes two parameters in R0 and R1. If R0 is 
	;positive, the stepper motor to moves one step (or half-step) in a 
	;clockwise direction. If R0 is negative, it drives the stepper motor to move one 
	;step in a counter-clockwise direction. If R0 is zero, nothing is done.
	;If the parameter in R1 is different than the previous invocation,  
	;the step sequence is started at the start of your look-up table.
	;R1 = 2_00 – use “one phase on” stepping.
	;R1 = 2_01 – use “two phase on” stsepping.
	;R1 = 2_10 – use half-steps.
	;R1 = 2_11 – nothing implemented
; Assumes:		This assumes that R0 and R1 hold certain required data
;				R0: Direction to turn
;				R1: stepping mode
;				This also assumes that the stepper motor is addressed using
;				gpio port 2 in the following order
;					2.5:   	PHASE1 (MC10)
;					2.4:	PHASE2 (MC5)	
;					2.3:	I1-1 (MC11)
;					2.2:	I0-1 (MC23)
;					2.1:	I1-2 (MC17)
;					2.0:  	I0-2 (MC4)

	EXPORT  Step
	
	INCLUDE ADuC7026.inc			;MMR definitions

	AREA    FLASH, CODE, READONLY
	ARM

Step		
	PUSH {R0-R1, R3-R5, R9-R12, LR};push all registers that are changed including R0, R1
	;R0		-	 Passed in: positive->CW, negative->ccw, zero->nothing
	;R1		- 	 Passed in: stepping style
	;  
	;R3	    - 	  prevMode
	;R4  	-	  lutCount
	;R5		-	  result of lookup	
	; 
	;R9		-	 =(motorLUT) 
	;R10 	-	 =(prevMode)	
	;R11	-	 =(lutCount)
	;R12	-	 =(GPIO_MMR_BASE)

	;get addresses of needed locations
	LDR		R12, =(GPIO_MMR_BASE) 		;set GPIO base register to R12
	LDR		R11, =(prevMode)  			;get address of prevMode
	LDR		R10, =(lutCount)			;get address of lutCount

	LDRB	R3, [R11]	;load prevMode
	LDRB	R4,[R10]	;load lutCount
	
	;compare R1 to previous mode
	AND		R3, #2_11
	AND		R1, #2_11 
	CMP		R1,	R3
	BEQ		stepsame 
		;if different
			;set prevMode to R1
	STRB	R1, [R11]
			;set lutCount to 0
	MOV 	R4, #0

	
stepsame	
	;inspect R0	& adjust count according to value
	CMP 	R0, #0
	;subtract 1 if negative
	SUBLT	R4,	R4,	#1
	 ;add 1 if positive
	ADDGT	R4,	R4, #1
	;twiddle thumbs otherwise

	;DO BOUNDS CHECKING
	TST 	R1,	 #2_10
	;if count goes negative, make it equal to the highest stage of the lut (3 for 00, 01), (7 for 10)		 
	;if 10 (if bit 1 is 1) and count>7, count = 0
	BEQ		bit1zero
	CMP 	R4,	#8
	MOVEQ	R4,	#0
	CMP		R4, #0
	MOVLT	R4,	#7
	B lookupindex
bit1zero	 ;if 00 or 01 (if bit 1 is zero), and count > 3, count = 0
	CMP		R4,	#4
	MOVEQ	R4,	#0
	CMP		R4, #0
	MOVLT	R4,	#3
lookupindex
   	STRB	R4, [R10] ; store lutCount as we are done manipulating it
	
	CMP		R1, #2_00	;if mode == 00
	LSLEQ	R4, R4, #1	;we need to double the index as the locations 0,2,4,6 apply to one phase
	
	CMP		R1, #2_01	;if mode == 01
	LSLEQ	R4, R4, #1	;we need to double the index (LSL 1), and offset by 1 as the locations 1,3,5,7 apply to two phase
	ADDEQ	R4, R4, #1	; + 1

	;if mode == 10
		;we wish to use the lookup as it is, as it should be configured by default for halfstep
	;if mode == 11
		;do nothing, as this is not implemented

	CMP		R1, #2_11
	BEQ		endStep
	
	;LOOKUP!
	;use offset(modified count) (R4), to do lookup
	;get lookup base address
	LDR		R9, =(motorLUT)		
	;get lookup value using base addrss and offset value stored in R4
	LDRB	R5, [R9,R4]
	;write return to GPIO Port 2 which contains the values of  
	;PHASE1 (MC10), PHASE2 (MC5), I1-1 (MC11), I0-1 (MC23), I1-2 (MC17), I0-2 (MC4)
	STRB	R5,[R12,#GP2DAT+2]
endStep
	POP {R0-R1, R3-R5, R9-R12, PC}

; This lookup table has the value of which to set the following data
;					bit 5:  PHASE1 (MC10)
;					bit 4:	PHASE2 (MC5)	
;					bit 3:	I1-1 (MC11)
;					bit 2:	I0-1 (MC23)
;					bit 1:	I1-2 (MC17)
;					bit 0:  I0-2 (MC4)

motorLUT  DCB	2_010011, 2_010000, 2_011100, 2_110000
		  DCB	2_110011, 2_100000, 2_001100, 2_000000

	AREA SRAM, DATA, READWRITE
lutCount	DCB 0
prevMode	DCB 0

	END