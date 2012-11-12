; Filename:     main.s 
; Author:       Christopher Wilcox, James Nystron 
; Description:  main file for lab 5/6.	
;				A time will be entered by the user via the keypad.  If desired the user 
;				can also use the DIP switches to vary the tone(s) the timer outputs when 
;				finished.  The tones will be generated using the ADUC's onboard DAC.  
;				The user will then press PB1 to begin the timer's countdown.  As it runs, 
;				the display will display a countdown.  When the specified amount of time has 
;				passed, the timer will output audio via the ADUC's Digital to Analog Converter 
;				to signal to the user that the timer has indeed finished. The user may then press 
;				PB2, which will reset the timer/clear the display.  

	AREA    FLASH, CODE, READONLY
	ARM

	INCLUDE ADuC7026.inc			;MMR definitions
	EXPORT  __main

;CONSTANTS USED IN TIMER1 CONFIGURATION	
T1LD_VALUE 		EQU 8356		;interrupt needed at ~5 kHz with 41.7792 MHz clock
								; we will use this to count and to update the display
								; using below formula (pg 76 ADUC7026 Datasheet)
								; Interval = {(TxLD) * Prescale} / 41.7792MHz
								; 1/5kHz   = {(TxLD) * Prescale} / 41.7792MHz
								; TxLD = 8356 	Prescale=1
								
T1_FIQEN_VALUE  EQU 0x00000008	;timer1 interrupt
T1CON_VALUE 	EQU 0x000000C0	;T0 enabled, periodic, binary, Prescale=1 (Source/1)		

;#################################
;DAC - update every HCLK, internal ref.
DAC0CON_VAL EQU 0x12


; main program start
__main

;###SET XMCFG to 1 to enable external memory interface
	LDR		R7, =(XM_MMR_BASE);CHECK
	MOV		R0, #1
	STRH	R0, [R7, #XMCFG]
;###ENABLE MS0
	MOV		R0, #2_01;Set Data Bus Width to 8 Bit, Enable Memory Region 0
	STRB	R0, [R7, #XM0CON]
;###ADD WAIT STATES 
;SET THE LOWEST BYTE OF XM0PAR TO ADD 1 WAIT STATE TO EACH RD and WR
	MOV		R0, #0x11
	STRB	R0, [R7, #XM0PAR]

;###Set the Port pins of P2 P3 to be used for external memory (10)
	LDR		R7, =(GPIO_MMR_BASE)
	MOV	 	R0, #0x22000000
	MOV  	R1, #0x00220000	
	ORR		R0, R0, R1
	MOV		R1, #0x00002200
	ORR		R0, R0, R1
	MOV		R1, #0x00000022
	ORR		R0, R0, R1
	;set port 2 to be used for external memory
   	STR		R0,[R7, #GP2CON]
	;set port 3 to be used for external memory
	STR		R0,[R7, #GP3CON]
	;set port 0.7 to be eclock for oscilloscope reading set pin 4 to be IRQ0
	MOV		R1, #0x10000000
	STR		R1,[R7, #GP0CON]

;### setup DAC0
	LDR R7, =(DAC_MMR_BASE) ;register base
	MOV R0, #DAC0CON_VAL
	STR R0, [R7, #DAC0CON] ;configure DAC0

;###TIMER1 CONFIGURATION
	LDR		R7, =(TIMER_MMR_BASE)
	LDR 	R0, =(T1LD_VALUE)
	STR 	R0, [R7, #T1LD] ;timer period config
	LDR 	R0, =(T1CON_VALUE)
	STR 	R0, [R7, #T1CON]
;### INTERRUPT CONFIGURATION
	LDR 	R7, =(IRQCON_MMR_BASE)
	LDR	 	R0, [R7, #FIQEN]
	ORR 	R0, R0, #T1_FIQEN_VALUE
	STR 	R0, [R7, #FIQEN]

;###INTERRUPT CONFIGURATION
	LDR 	R7, =(IRQCON_MMR_BASE)
	LDR	 	R0, [R7, #IRQEN]
	MOV		R0, #0x8000;ENABLE INTERRUPT BY IRQ0 for KEYPAD
	STR 	R0, [R7, #IRQEN]

main_loop
	B		main_loop
	END