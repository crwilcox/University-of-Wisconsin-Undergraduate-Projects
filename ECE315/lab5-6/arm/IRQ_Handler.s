; Filename:     IRQ_Handler.s 
; Author:       Christopher Wilcox & James Nystrom 
; Description:  exception handler for IRQ
;				This IRQ is used to handle key presses
;				the user ends up in this IRQ if the hardware 
;				generates an interupt.  We find out which keys
;				were pressed, and output them to the correct 
;				memory location.
; REGISTER USAGE:
;	R0:		Xfer Register
;   R1:		Xfer Register
;   R2:		KeyMatrix as loaded at start of routine... will use to compare with current for debouncing reasons
;   R3:		Current-KeyMatrix
;   R4:		DisplayData
;   R5:		Holds 0xF (2_1111)
;   R6:		=KeyMatrixLut 
;   R7:		=DisplayData
;   R8:		=Count
;   R9:		=KeyMatrix
;   R10:	=TIMER_MMR_BASE, holds #0x10000000 ; EVEN ADDRESS (FOR READING MEMORY I/O)
;   R11:	holds #0x10000001 ; ODD ADDRESS (FOR WRITING MEMORY I/0 KEYPAD0:3) 
;   R12:	used to acces mem I/O


	EXPORT  IRQ_Handler
	EXPORT  DisplayData
	EXPORT  Count
	
	INCLUDE ADuC7026.inc			;MMR definitions

	AREA    FLASH, CODE, READONLY
	ARM

IRQ_Handler					;IRQ exception
	PUSH {R0-R12}
	LDR		R6,  =(KeyMatrixLut)
	LDR		R7,  =(DisplayData)
	LDR		R8,  =(Count)
	LDR		R9, =(KeyMatrix)

; clear timer interrupt
	LDR 	R10, =(TIMER_MMR_BASE)
	STRB 	R0, [R10, #T0CLRI] ;reset timer0 interrupt, any R0 value will do

;clear interrupt
; externally clear the reset for the keypad (a flip flop reset)

	LDR 	R2, [R9]		;get KeyMatrix, and hang onto for previous press comparison

;LOOK AT SW0... IF SET, IS RUNNING... DONT ALLOW KEPAD PRESS UPDATES
	MOV		R1, #0x10000000; EVEN ADDRESS (FOR READING DIP SWITCH)
	LDRB	R0, [R1]
	TST		R0, #2_10000; test bit 4 of area(SW0)
	BEQ	    timerRunning; if bit4 is 1, DONT EXCEPT KEYS
	 
;get keypad presses...
	MOV		R5, #2_11110000	  ;used to store to P2.0-3 to allow charging
	MOV		R11, #0x10000001 ; ODD ADDRESS  (FOR WRITING MEMORY I/0 KEYPAD0:3)
	MOV		R10, #0x10000000 ; EVEN ADDRESS (FOR READING MEMORY I/O KEYPAD4:7)

	;write 0111	to P2.0-3
	MOV		R0, #2_01110000
	STRB	R0, [R11]
	;check row (read P2.3-7)(A741), add to R3
	LDRB	R1, [R10]
	STRB	R5, [R11]	;write 1111 to P2.0-3 to allow charging
	AND		R1, R1, #0xF													  
	;Shift to Correct Position  (LSL 12)
	LSL		R1, R1, #12
	MOV		R3, R1

	;write 1011 to P2.0-3
	MOV		R0, #2_10110000
	STRB	R0, [R11]
	;check rows (read P2.3-7)(0852), add to R3
	LDRB	R1, [R10]
	STRB	R5, [R11]	;write 1111 to P2.0-3 to allow charging
	AND		R1, R1, #0xF
	;Shift to Correct Position (LSL 8)
	LSL		R1, R1, #8
	ORR		R3, R3, R1

	;write 1101 to P2.0-3
	MOV		R0, #2_11010000
	STRB	R0, [R11]
	;check rows	(read P2.3-7)(B963), add to R3
	LDRB	R1, [R10]
	STRB	R5, [R11]	;write 1111 to P2.0-3 to allow charging
	AND		R1, R1, #0xF
	;Shift to Correct Position (LSL 4)
	LSL		R1, R1, #4
	ORR		R3, R3, R1

	;write 1110 to P2.0-3
	MOV		R0, #2_11100000
	STRB	R0, [R11]
	;check rows	(read P2.3-7)(FEDC), add to R3 
	LDRB	R1, [R10]
	STRB	R5, [R11]	;write 1111 to P2.0-3 to allow charging
	AND		R1, R1, #0xF

	
	ORR 	R3, R3, R1

	;write all of the rows to memory as KeyMatrix
	STR		R3, [R9]

;Need to make sure these are debounced
	;beginifblock
	;Compare previousPress(R2), with currentPress R3
	CMP 	R2, R3
	MOVNE	R0, #0	 ;set count equal to zero if not equal
	STRBNE 	R0, [R8]
	BNE 	fiq_notsame
	;if same, 
		;these must have been held for a loop, so write to displayData

;This loop looks at all the keys, and if they were pressed,
;it outputs them to display data (only writes back to display
;data if it passes the count
	LDR 	R4, [R7] ;Get Display Data
	MOV 	R1, #0 	 ;R1 will be our index
keyProcessLoop_start
	LDRB	R0, [R6, R1]
	TST 	R3, #0x1
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
	LDRB 	R0, [R8]
	CMP 	R0, #0	 ;if count==0,
	STREQ 	R4, [R7] ;STORE TO DISPLAY DATA	   ;was strEQ
	ADD		R0, #1	 ;increment count
	CMP		R0, #100	 ;if 100, set to 0
	MOVGT	R0, #0	
	STR 	R0, [R8]

fiq_notsame
timerRunning
;CONTEXT RESTORE
	POP 	{R0-R12}
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