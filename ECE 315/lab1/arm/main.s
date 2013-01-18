; Filename: main.s
; Author: Christopher Wilcox & James Nystrom
; Description: Lab #1 - Multiplexed LED Code
	AREA    FLASH, CODE, READONLY
	ARM

	INCLUDE ADuC7026.inc			;MMR definitions
	EXPORT  __main
	EXPORT DisplayData
	
;CONSTANTS USED IN TIMER1 CONFIGURATION	
T1LD_VALUE 		EQU 5222		;interrupt needed at ~8 kHz with 41.7792 MHz clock
								; using below formula (pg 76 ADUC7026 Datasheet)
								; Interval = {(TxLD) * Prescale} / 41.7792MHz
								; 1/8kHz   = {(TxLD) * Prescale} / 41.7792MHz
								; TxLD = 5222 	Prescale=1
								; Interrupt Rate = 8000.613 Hz = ~8kHz
T1_IRQEN_VALUE  EQU 0x00000008	;timer1 interrupt
T1CON_VALUE 	EQU 0x000000C0	;T1 enabled, periodic, binary, Prescale=1 (Source/1)

__main

; TIMER1 CONFIGURATION
	LDR		R7, =(TIMER_MMR_BASE)
	LDR 	R0, =(T1LD_VALUE)
	STR 	R0, [R7, #T1LD] ;timer period config
	LDR 	R0, =(T1CON_VALUE)
	STR 	R0, [R7, #T1CON]

; INTERRUPT CONFIGURATION
	LDR 	R7, =(IRQCON_MMR_BASE)
	LDR	 	R0, [R7, #IRQEN]
	ORR 	R0, R0, #T1_IRQEN_VALUE
	STR 	R0, [R7, #IRQEN]	 

; GPIO CONFIGURATION	
	;used to write DigitLines
	LDR		R7, =(GPIO_MMR_BASE)	
	MOV		R0, #0						;set port P1 to GPIO	
   	STR		R0,[R7, #GP1CON]
	LDRB 	R0,[R7, #GP1DAT+3]
	ORR		R0, R0, #0xF0				;set P1.7-4 to outputs, leave be P1.3-0
   	STRB	R0,[R7, #GP1DAT+3]
	
	;used to write segments
	LDR		R7, =(GPIO_MMR_BASE)	
	MOV		R0, #0						;set port P4 to GPIO	
   	STR		R0,[R7, #GP4CON]
	MOV		R0, #0xFF					;set P4.7-0 to outputs
   	STRB	R0,[R7, #GP4DAT+3]

	;used to read DIP switches
	LDR		R7, =(GPIO_MMR_BASE)
	MOV		R0, #0						;set port P2 to GPIO	
   	STR		R0,[R7, #GP2CON]
	MOV		R0, #0x00					;set P2.7-0 to inputs
   	STRB	R0,[R7, #GP2DAT+3]
	
main_loop
;GPIO PORT2 WILL CONTINUOUSLY READ DIP SWITCHES AND SET THE VALUE OF EACH ELEMENT OF DisplayData
	
	;rightmost pair display the upper and lower nibble of the DIPS
	LDR		R7, =(GPIO_MMR_BASE)
	LDRB 	R3,[R7,#GP2DAT]
	MVN		R3, R3
	AND 	R3,R3, #0xFF

	
	;leftmost display the 2's complement of the dip switch	 	
	RSB 	R4, R3, #0 ; Get twos complement of R3
	AND 	R4, R4, #0xFF
	LSL 	R4, R4, #8

;COMBINE ALL VALUES, AND STORE IN DISPLAY DATA
	ORR 	R3, R3, R4
	LDR 	R8, =(DisplayData)
	STR 	R3, [R8]

	B main_loop

	AREA SRAM, DATA, READWRITE
DisplayData DCD 0

	END
