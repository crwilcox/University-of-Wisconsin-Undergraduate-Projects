; Filename: main.s
; Author: Christopher Wilcox & James Nystrom
; Description: Lab #2 - Keypad Interfacing
	AREA    FLASH, CODE, READONLY
	ARM

	INCLUDE ADuC7026.inc			;MMR definitions
	EXPORT  __main
	IMPORT DisplayData
	IMPORT Count
	
;CONSTANTS USED IN TIMER1 CONFIGURATION	
T1LD_VALUE 		EQU 5222		;interrupt needed at ~8 kHz with 41.7792 MHz clock
								; using below formula (pg 76 ADUC7026 Datasheet)
								; Interval = {(TxLD) * Prescale} / 41.7792MHz
								; 1/8kHz   = {(TxLD) * Prescale} / 41.7792MHz
								; TxLD = 5222 	Prescale=1
								; Interrupt Rate = 8000.613 Hz = ~8kHz
T1_IRQEN_VALUE  EQU 0x00000008	;timer1 interrupt
T1CON_VALUE 	EQU 0x000000C0	;T1 enabled, periodic, binary, Prescale=1 (Source/1)		

T0LD_VALUE 		EQU 1632		;interrupt needed every 10ms 
								;at 100Hz with 41.7792 MHz clock		
								; TxLD = 1632	Prescale=256
								; Interrupt Rate = 100Hz
T0_FIQEN_VALUE  EQU 0x00000004	;timer0 interrupt enabled
T0CON_VALUE 	EQU 0x000000C8	;T0 enabled, periodic, binary, Prescale=256

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

; TIMER0 CONFIGURATION
	LDR		R7, =(TIMER_MMR_BASE)
	LDR 	R0, =(T0LD_VALUE)
	STR 	R0, [R7, #T0LD] ;timer period config
	LDR 	R0, =(T0CON_VALUE)
	STR 	R0, [R7, #T0CON]

; INTERRUPT CONFIGURATION
	LDR 	R7, =(IRQCON_MMR_BASE)
	LDR	 	R0, [R7, #FIQEN]
	ORR 	R0, R0, #T0_FIQEN_VALUE
	STR 	R0, [R7, #FIQEN]

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

	;Connected to the Keypad
	;KEYPAD0-3 are Columns (WRITE)
	;KEYPAD4-7 are Rows (READ)			   
	LDR		R7, =(GPIO_MMR_BASE)
	MOV		R0, #0						;set port P2 to GPIO	
   	STR		R0,[R7, #GP2CON]
	MOV		R0, #0x0F					;set P2.3-0 to outputs
										;set P2.7-4 to inputs
										
   	STRB	R0,[R7, #GP2DAT+3]
	
	MOV		R0, #0
	;Make Display All 0
	LDR 	R7, =(DisplayData)
	STR 	R0, [R7]		;STORE DisplayData
	;Initialize Count to 0 (For FIQ)
	LDR 	R7, =(Count)
	STR		R0, [R7]
main_loop
	;
	B main_loop



	END
