; Filename:     main.s 
; Author:       Christopher Wilcox, James Nystron 
; Description:  main file for lab 3.  
;				Program Uses memory interfacing to operate a rotary encoder 
;				and to write to the LED bar based on the result 

	AREA    FLASH, CODE, READONLY
	ARM

	INCLUDE ADuC7026.inc			;MMR definitions
	EXPORT  __main

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

;###TIMER1 CONFIGURATION
	LDR		R7, =(TIMER_MMR_BASE)
	LDR 	R0, =(T1LD_VALUE)
	STR 	R0, [R7, #T1LD] ;timer period config
	LDR 	R0, =(T1CON_VALUE)
	STR 	R0, [R7, #T1CON]
	
;###INTERRUPT CONFIGURATION
	LDR 	R7, =(IRQCON_MMR_BASE)
	LDR	 	R0, [R7, #IRQEN]
	ORR 	R0, R0, #T1_IRQEN_VALUE
	STR 	R0, [R7, #IRQEN]

;###SET XMCFG to 1 to enable external memory interface
	LDR		R7, =(XM_MMR_BASE);CHECK
	MOV		R0, #1
	STRH	R0, [R7, #XMCFG]
;###ENABLE MS0
	MOV		R0, #2_01;Set Data Bus Width to 8 Bit, Enable Memory Region 0
	;MOV		R0, #2_11;Set Data Bus Width to 16 Bit
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
	;set port 0.7 to be eclock for oscilloscope reading
	MOV		R1, #0x10000000
	STR		R1,[R7, #GP0CON]

main_loop
	B		main_loop

	END
