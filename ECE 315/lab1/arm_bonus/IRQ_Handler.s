; Filename:     IRQ_Handler.s 
; Author:       Christopher Wilcox & James Nystrom 
; Description:  exception handler for IRQ

	EXPORT  IRQ_Handler
	EXPORT Intensity
	IMPORT  DisplayData
	
	INCLUDE ADuC7026.inc			;MMR definitions

	AREA    FLASH, CODE, READONLY
	ARM

IRQ_Handler					;IRQ exception
	PUSH {R0-R4, R7-R9, R11-R12}
	;R0		-	DisplayData Content, seglut Output
	;R1		-   CurrentDigit Content
	;R2		- 	NextDigit (to be written to memory)
	;R7	   	-  =(DisplayData)
	;R8   	-  =(CurrentDigit)
	;R9		-  =(seglut)
	;R11   	-  =(TIMER_MMR_BASE)
	;R12	-  =(GPIO_MMR_BASE)
	
	AREA SRAM, DATA, READWRITE
CurrentDigit DCB 0
	AREA    FLASH, CODE, READONLY

	LDR		R12, =(GPIO_MMR_BASE)

; clear timer interrupt
   	LDR 	R11, =(TIMER_MMR_BASE)
	STRB 	R0, [R11, #T1CLRI] ;reset timer1 interrupt, any R0 value will do
	
	LDR 	R7, =(Intensity)
	LDRB 	R4, [R7]		;get Intensity
	AND 	R4, R4, #0xF
	LDR 	R7, =(Count)
	LDRB 	R3, [R7]		;get Count
	AND 	R3, R3, #0xF
			
	CMP 	R4, R3
	BGT		leaveOnLED

	MOV 	R0, #0
	MOV		R1, #0
			 ;zero out all segments and ports
	STRB 	R0,[R12, #GP4DAT+2]	
	STRB 	R1, [R12, #GP1DAT+2]

	B		endOfInterrupt
	
leaveOnLED

	LDR 	R7, =(DisplayData)
	LDR 	R0, [R7]		;get DisplayData
	LDR 	R8, =(CurrentDigit)
	LDRB 	R1, [R8]	   	;get CurrentDigit

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
	LDR 	R9, =(seglut) ;do the look-up
	LDRB 	R0, [R9, R0]
	STRB 	R0,[R12, #GP4DAT+2]	 ;Write Segment LUT Out to P4

;WRITE CurrentDigit TO UPPER NIBBLE OF PORT 1
	STRB 	R2, [R8]   ;store updated currentDigit to Memory
	LSL 	R1, R1, #4	;shift to upper nibble
	STRB 	R1, [R12, #GP1DAT+2]

endOfInterrupt
   	ADD		R3, R3, #1 ; increment Count
	LDR 	R7, =(Count)
   	STRB 	R3, [R7]   ;store updated count to Memory
	

;CONTEXT RESTORE
	POP 	{R0-R4, R7-R9, R11-R12}
	SUBS 	PC, LR, #4 ; return

; LUT for 7-segment display: segment patterns 0-9, A-F
seglut  DCB 2_00111111, 2_00000110, 2_01011011, 2_01001111
		DCB 2_01100110, 2_01101101, 2_01111101, 2_00000111
		DCB 2_01111111, 2_01101111, 2_01110111, 2_01111100
		DCB 2_00111001, 2_01011110, 2_01111001, 2_01110001
	AREA SRAM, DATA, READWRITE
Count DCB 0
Intensity DCB 0
	END