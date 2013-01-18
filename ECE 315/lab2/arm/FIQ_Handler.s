; Filename:     FIQ_Handler.s 
; Author:       Christopher Wilcox & James Nystrom 
; Description:  exception handler for FIQ
; REGISTER USAGE:
;	R0:		Xfer Register
;   R1:		Xfer Register
;   R2:		KeyMatrix as loaded at start of routine... will use to compare with current for debouncing reasons
;   R3:		Current-KeyMatrix
;   R4:		DisplayData
;   R5:		Holds 0xF (2_1111)
;   R6:
;   R7:		=KeyMatrixLut 
;   R8:		=DisplayData
;   R9:		=Count
;   R10:	=KeyMatrix
;   R11:	=TIMER_MMR_BASE
;   R12:	=GPIO_MMR_BASE


	EXPORT  FIQ_Handler
	EXPORT  DisplayData
	EXPORT  Count
	
	INCLUDE ADuC7026.inc			;MMR definitions

	AREA    FLASH, CODE, READONLY
	ARM

FIQ_Handler					;FIQ exception
	PUSH {R0-R5, R7-R12}
	LDR		R7,  =(KeyMatrixLut)
	LDR		R8,  =(DisplayData)
	LDR		R9,  =(Count)
	LDR		R10, =(KeyMatrix)
	LDR 	R11, =(TIMER_MMR_BASE)
	LDR		R12, =(GPIO_MMR_BASE)

; clear timer interrupt
	STRB 	R0, [R11, #T0CLRI] ;reset timer1 interrupt, any R0 value will do
	LDR 	R2, [R10]		;get KeyMatrix, and hang onto for previous press comparison
;get keypad presses...
	MOV		R5, #2_1111	  ;used to store to P2.0-3 to allow charging

	;write 0111	to P2.0-3
	MOV		R0, #2_0111
	STRB	R0, [R12,#GP2DAT+2]
	STRB	R5, [R12,#GP2DAT+2]	;write 1111 to P2.0-3 to allow charging
	;check row (read P2.3-7)(A741), add to R3
	LDRB	R1, [R12,#GP2DAT]
	AND		R1, R1, #0xF0													  
	;Shift to Correct Position  (LSL 8)
	LSL		R1, R1, #8
	MOV		R3, R1

	;write 1011 to P2.0-3
	MOV		R0, #2_1011
	STRB	R0, [R12,#GP2DAT+2]
	STRB	R5, [R12,#GP2DAT+2]	;write 1111 to P2.0-3 to allow charging
	;check rows (read P2.3-7)(0852), add to R3
	LDRB	R1, [R12,#GP2DAT]
	AND		R1, R1, #0xF0
	;Shift to Correct Position (LSL 4)
	LSL		R1, R1, #4
	ORR		R3, R3, R1

	;write 1101 to P2.0-3
	MOV		R0, #2_1101
	STRB	R0, [R12,#GP2DAT+2]
	STRB	R5, [R12,#GP2DAT+2]	;write 1111 to P2.0-3 to allow charging
	;check rows	(read P2.3-7)(B963), add to R3
	LDRB	R1, [R12,#GP2DAT]
	AND		R1, R1, #0xF0
	;dont shift as it is in correct position
	ORR		R3, R3, R1

	;write 1110 to P2.0-3
	MOV		R0, #2_1110
	STRB	R0, [R12,#GP2DAT+2]
	STRB	R5, [R12,#GP2DAT+2]	;write 1111 to P2.0-3 to allow charging
	;check rows	(read P2.3-7)(FEDC), add to R3 
	LDRB	R1, [R12,#GP2DAT]
	AND		R1, R1, #0xF0

	;shift row to lowest nibble (LSR 4)
	LSR	 	R1, R1, #4
	ORR 	R3, R3, R1

	;write all of the rows to memory as KeyMatrix
	STR		R3, [R10]

;Need to make sure these are debounced
	;beginifblock
	;Compare previousPress(R2), with currentPress R3
	CMP 	R2, R3
	MOVNE	R0, #0	 ;set count equal to zero if not equal
	STRBNE 	R0, [R9]
	BNE 	fiq_notsame
	;if same, 
		;these must have been held for a loop, so write to displayData

;This loop looks at all the keys, and if they were pressed,
;it outputs them to display data (only writes back to display
;data if it passes the count
	LDR 	R4, [R8] ;Get Display Data
	MOV 	R1, #0 	 ;R1 will be our index
keyProcessLoop_start
	LDRB	R0, [R7, R1]
	TST R3, #0x1
	LSLEQ 	R4, #4
	ORREQ	R4, R0

	ADD		R1, R1, #1
	LSR		R3, #1
	CMP 	R1, #16
	BLT		keyProcessLoop_start
keyProcessLoop_end

	;USE COUNT TO SEE IF BUTTON IS FIRST BEING HELD, 
	;OR HAS BEEN HELD FOR 1sec, Write to 
	;DisplayData Appropriately
	LDR 	R0, [R9]
	CMP 	R0, #0	 ;if count==0,
	STREQ 	R4, [R8] ;STORE TO DISPLAY DATA
	ADD		R0, #1	 ;increment count
	CMP		R0, #100	 ;if 100, set to 0
	MOVEQ	R0, #0	
	STR 	R0, [R9]

fiq_notsame

;CONTEXT RESTORE
	POP 	{R0-R5, R7-R12}
	SUBS 	PC, LR, #4 ; return

KeyMatrixLut  
		DCB 0xF, 0xE, 0xD, 0xC
		DCB 0xB, 0x9, 0x6, 0x3
		DCB 0x0, 0x8, 0x5, 0x2
		DCB 0xA, 0x7, 0x4, 0x1
		
	
	AREA SRAM, DATA, READWRITE
KeyMatrix			DCD 0
DisplayData 		DCD 0
Count				DCB 0
	END