; Filename:     timerAlarm.s
; Author:       Christopher Wilcox & James Nystrom 
; Description:  This subroutine is accessed if the alarm is 
;				going off.  It handles the outputing of a tone to 
;				the audio port.  This way the user is notified
;				that the timer has reached 0 and is on...  

	EXPORT  timerAlarm
	
	INCLUDE ADuC7026.inc			;MMR definitions

	AREA    FLASH, CODE, READONLY
	ARM
;DAC counts of average value of the sinusoid (1.25V)
SINE_AVG EQU 2048

timerAlarm		
	PUSH {R0, R2, R7, LR};push all registers that are changed including R0, R1

	LDR R7, =(DACIndex)
	LDRB R0, [R7]
	ADD R0, #1
	AND R0, #31 ; 0-31 is one full sine wave period
	STRB R0, [R7] ; save new value of DACIndex

	; DACIndex[4] indicates negation
	; DACIndex[3] gives look-up direction
	; DACIndex[2:0] used for look-up
	AND R2, R0, #7 ; get bits 2:0 for look-up
	TST R0, #2_1000 ; look-up direction?
	RSBNE R2, R2, #8 ; doing reverse look-up
	ADR R7, QuarterSineLUT
	LSL R2, #1 ; multiply index for halfword look-up
	LDRH R2, [R7, R2] ; do halfword look-up
	TST R0, #2_10000
	RSBNE R2, R2, #0 ; negate if needed
	
	LDR R7, =(DAC_MMR_BASE) ; update DAC
	ADD R2, R2, #SINE_AVG ; add in required offset
	LSL R2, R2, #16 ; DAC value is in upper halfword of MMR
	STR R2, [R7, #DAC0DAT] ; store DAC value
	
endTimerAlarm
	POP {R0, R2, R7, PC}

QuarterSineLUT DCD 0, 320, 627, 910, 1158, 1362, 1513, 1607, 1638

	AREA SRAM, DATA, READWRITE
DACIndex SPACE 1

	END