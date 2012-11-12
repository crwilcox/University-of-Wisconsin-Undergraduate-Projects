; Filename:     FIQ_Handler.s 
; Author:       Christopher Wilcox & James Nystrom 
; Description:  exception handler for FIQ
;				FIQ is called at intervals and used to measure time (in seconds)
;				This is crucial to the functioning of a timer, as we need a way
;				to know how much time has passed.  This also takes care of the display

	EXPORT  FIQ_Handler
	IMPORT  DisplayData
	IMPORT  timerAlarm
	INCLUDE ADuC7026.inc			;MMR definitions

	AREA    FLASH, CODE, READONLY
	ARM

FIQ_Handler					;FIQ exception
	PUSH {R0-R9, R11-R12}
	;R0		-	DisplayData Content, seglut Output
	;R1		-   CurrentDigit Content
	;R2		- 	NextDigit (to be written to memory)
	;R3		-  5000 (0x1388)
	;R4	    -  DipSwitch 0 (SW0)
	;R5		-  TimerCount
	;R6		-  =(TimerCount)
	;R7	   	-  =(DisplayData)
	;R8   	-  =(CurrentDigit)
	;R9		-  =(seglut)
	;R10
	;R11   	-  =(TIMER_MMR_BASE)
	;R12	-  used for Mem I/O Interfacing
	
	AREA SRAM, DATA, READWRITE
TimerCount 	 DCW 0
CurrentDigit DCB 0

	AREA    FLASH, CODE, READONLY

	LDR		R12, =(GPIO_MMR_BASE)

; clear timer interrupt
	LDR 	R11, =(TIMER_MMR_BASE)
	STRB 	R0, [R11, #T1CLRI] ;reset timer1 interrupt, any R0 value will d

	
	LDR 	R7, =(DisplayData)
	LDRH 	R0, [R7]		;get DisplayData
	LDR 	R8, =(CurrentDigit)
	LDRB 	R1, [R8]	   	;get CurrentDigit

	;if Display Data is 0, dont decrment it, SetOff Alarm
	CMP 	R0, #0
	BNE 	noAlarm 
	
	MOV		R12, #0x10000000; EVEN ADDRESS (FOR READING DIP SWITCH)
	LDRB	R4, [R12]
   ;CHECK IF THE Alarm is running, if so ALARM!
	TST		R4, #2_10000; test bit 4 of area(SW0)
	PUSH	{LR};Save LR as to return properly
	BLEQ	timerAlarm;
	POP		{LR}

noAlarm


;CHECK IF A SECOND HAS PASSED, IF SO, DECREMENT DisplayData
	LDR		R6, =(TimerCount)
	LDRH	R5, [R6]	
	ADD		R5, #1;
	MOV		R3, #0x1300
	ORR		R3, #0x0088
	CMP		R5, R3;
	BNE		EndTimeCheck
	MOV		R5, #0
	

	MOV		R12, #0x10000000; EVEN ADDRESS (FOR READING DIP SWITCH)
	LDRB	R4, [R12]

	;LOOK AT PB2.  IF PRESSED, Reset Display Data
	TST		R4, #2_100000; test bit 5 (PB2), if 0, reset DisplayData
	BNE		notReset
	MOV		R0, #0
notReset

   ;CHECK IF THE Alarm Timer should be running
	;look at dip switch 0, if 0, jump to EndTimeCheck
	TST		R4, #2_10000; test bit 4 of area(SW0)
	BNE 	EndTimeCheck; if bit4 is 0, end check and make R5 0, as we are stopped
	 
	;if Display Data is 0, dont decrment it
	CMP 	R0, #0
	BEQ		EndTimeCheck

	SUB		R0, #1		;decrement DisplayData
EndTimeCheck
	STR		R0, [R7]	;store modified DisplayData
	STRH	R5, [R6]


;GET APPROPRIATE BYTE OF DisplayData, and Appropriate CurrentDigit
	;if CurrentDigit == 1000
	CMP 	R1, #2_1000
	LSREQ 	R0, #12;shift displaydata(R0)
	LDREQ 	R2, =(2_0001);currentDigit = 0001
	BEQ 	endInputClean
	;else if CurrentDigit == 0100
	CMP 	R1, #2_0100
	LSREQ 	R0, #8;shift displaydata(R0)
	LDREQ 	R2, =(2_1000);currentDigit = 1000
	BEQ 	endInputClean
	;else if CurrentDigit == 0010
	CMP 	R1, #2_0010
	LSREQ 	R0, #4;shift displaydata(R0)
	LDREQ 	R2, =(2_0100);currentDigit = 0100
	BEQ 	endInputClean
	; else (CurrentDigit = 0001) (or an unknown value)
		;also used as else block to bring CurrentDigit to range
	LDR 	R2, =(2_0010);currentDigit = 0010
endInputClean 	
	AND 	R0, R0, #0xF ; Mask value, and make sure it is from 0-15 (0-F)

;DO LOOKUP, AND STORE RESULT TO PORT 4	
	MOV		R12, #0x10000000 ; EVEN ADDRESS: WRITE SEGMENTS
	LDR 	R9, =(seglut) ;do the look-up
	LDRB 	R0, [R9, R0]
	STRB 	R0,[R12]	 ;Write Segment LUT Out to P4

;WRITE CurrentDigit TO UPPER NIBBLE OF PORT 1
	STRB 	R2, [R8]   ;store updated currentDigit (R2)to Memory
	MOV		R12, #0x10000001 ; ODD ADDRESS: WRITE DIGITS
	STRB 	R1, [R12];CHANGE DIGIT (in R1)

;CONTEXT RESTORE
	POP 	{R0-R9, R11-R12}
	SUBS 	PC, LR, #4 ; return

; LUT for 7-segment display: segment patterns 0-9, A-F
seglut  DCB 2_00111111, 2_00000110, 2_01011011, 2_01001111
		DCB 2_01100110, 2_01101101, 2_01111101, 2_00000111
		DCB 2_01111111, 2_01101111, 2_01110111, 2_01111100
		DCB 2_00111001, 2_01011110, 2_01111001, 2_01110001

	END