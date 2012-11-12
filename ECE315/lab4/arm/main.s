; Filename:     main.s 
; Author:       ECE 315 student 
; Description:  sample main program

	AREA    FLASH, CODE, READONLY
	ARM

	INCLUDE ADuC7026.inc			;MMR definitions
	EXPORT  __main
	IMPORT StepCount
	IMPORT	StepMode
;CONSTANTS USED IN TIMER1 CONFIGURATION	
T1LD_VALUE 		EQU 8356		;interrupt needed at ~5 kHz with 41.7792 MHz clock
								; using below formula (pg 76 ADUC7026 Datasheet)
								; Interval = {(TxLD) * Prescale} / 41.7792MHz
								; 1/5kHz   = {(TxLD) * Prescale} / 41.7792MHz
								; TxLD = 8356 	Prescale=1
								
T1_IRQEN_VALUE  EQU 0x00000008	;timer1 interrupt
T1CON_VALUE 	EQU 0x000000C0	;T1 enabled, periodic, binary, Prescale=1 (Source/1)		

; main program start
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
	LDR		R7, =(GPIO_MMR_BASE)	
	MOV		R0, #0						;set port P1 to GPIO	
   	STR		R0,[R7, #GP1CON]
	MOV		R0, #2_11					;set P1.0-1 to outputs
   	STRB	R0,[R7, #GP1DAT+3]

	MOV		R0, #0						;set port P2 to GPIO	
   	STR		R0,[R7, #GP2CON]
	MOV		R0, #2_00111111				;set P2.5-0 to outputs, set P2.7-6 to inputs
   	STRB	R0,[R7, #GP2DAT+3]

	MOV		R0, #0						;set port P3 to GPIO	
   	STR		R0,[R7, #GP3CON]
	MOV		R0, #0x00					;set P3.7-0 to outputs
   	STRB	R0,[R7, #GP3DAT+3]

	MOV		R0, #0						;set port P4 to GPIO	
   	STR		R0,[R7, #GP4CON]
	MOV		R0, #0xFF					;set P4.7-0 to outputs
   	STRB	R0,[R7, #GP4DAT+3]

main_loop
	;R0 - Port2 Value
	;R1 - prevValue
	;R2 - Dip Switches
	;R3 - New_StepMode
	;R4 - New_StepCount
	;R5 - 

	;R7 - =GPIO_MMR_BASE
	;R8 - =(prevValue)
	;R9 - =(StepMode)
	;R10 - =(StepCount)

	LDRB	R0,	[R7, #GP2DAT]; Load Port2
	LSR		R0, R0, #7; shift the 7 bit to 0 bit
	AND 	R0, R0, #1;Mask to the single bit

	LDR		R8, =(prevValue)
	LDRB	R1, [R8]
	AND		R1, #1;Mask to single bit

	CMP		R0, #0 ;PB1 Active low Check to see if button pressed
	BNE		notDebounced
	CMP 	R0, R1
	BNE		notDebounced
debounced

	LDR 	R9, =(StepMode)
	LDR 	R10, =(StepCount)	
	;if debounced, update the data in StepCount and StepMode with what is in the DIP switches
	LDRB	R2, [R7, #GP3DAT];Load Dip Switches
	MVN		R2, R2 		; Invert the DIP switches
	 
	LSR		R3,	 R2, #6	 	;Grab Step Mode from upper 2 bits
	AND		R3,	 R3, #2_11
	STRB	R3, [R9] 		;Save StepMode to Memory
	 
	AND		R4, R2, #0x3F	;Grab Step Count from lower 6 bits	

	STR		R4, [R10];Store StepCount as word (helps IRQ know if negative/positive)	
	
notDebounced
	STRB	R0, [R8];store the curr value to prevValue

	B		main_loop

	AREA SRAM, DATA, READWRITE
prevValue	DCB 0

	END
