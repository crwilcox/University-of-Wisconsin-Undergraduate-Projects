; Filename:     IRQ_Handler.s 
; Author:       Christopher Wilcox & James Nystrom 
; Description:  exception handler for IRQ

	EXPORT  IRQ_Handler
	
	INCLUDE ADuC7026.inc			;MMR definitions

	AREA    FLASH, CODE, READONLY
	ARM

IRQ_Handler					;IRQ exception
	PUSH {R0-R2, R4-R12}
	;R0		-	 TEMP
	;R1		- 	 TEMP
	;R2		-	 TEMP
	;R3	    - 	 UNUSED
	;R4  	-	segCount 0-9, Says the Location of the Out Segment
	;R5		-	Previous ENCB / ENCA, LEDBAR RETURN COUNT	
	;R6		-   Current  ENCB / ENCA
	;R7		-	=(segCount)	
	;R8		-	=(rotaryLUT)
	;R9		-   =(LEDBar)
	;R10 	-	=(PrevENC)
	;R11	-	=(TIMER_MMR_BASE)
	;R12	-	=(GPIO_MMR_BASE)
	
	AREA SRAM, DATA, READWRITE
LEDBar		DCW 0
PrevENC 	DCB 0
segCount	DCB 0
	AREA    FLASH, CODE, READONLY

	LDR		R12, =(GPIO_MMR_BASE)

; clear timer interrupt
   	LDR 	R11, =(TIMER_MMR_BASE)
	STRB 	R0, [R11, #T1CLRI] ;reset timer1 interrupt, any R0 value will do

;Get Addresses of Needed Memory
	LDR		R9,	=(LEDBar)	
	LDR 	R10,=(PrevENC)
	LDR		R8, =(rotaryLUT)
	LDR		R7, =(segCount)
	
;FIGURE OUT THE NEW LED BAR
;get the values from P3_0(ENC_A) P3_1(ENC_B) (CurrENC)
	MOV		R0, #0x10000001 ; ODD ADDRESS
	LDRB 	R6, [R0]
	LSR		R6, #6
	AND 	R6, R6, #2_11
;Get PREVIOUS ENC
	LDRB 	R5, [R10]		;get PrevENC
	AND		R5, R5, #2_11

	;put prev and currENC together for LUT
	LSL		R5, R5, #2
	ORR 	R6, R6, R5	  ; R6 Holds prevENC(1:0) and currENC{1:0)

	;Do Lookup
	;use R6 to do lookup, get countoffset, and store in R0
	LDRB	R1,	[R8, R6]
	LDRB	R0, [R7]   ;Returns Count as Stored
	
	CMP		R1, #0xFF
	ADDEQ	R0, #-1

	CMP		R1, #1
	ADDEQ	R0, #1

	;if count is 10, set to 0
	CMP		R0, #9
	MOVGT	R0, #0

	;if count is < 0, set to 9
	CMP		R0, #0
	MOVLT	R0, #9

;Store Findings LEDBar... The lower 10 bits 9:0 are the values of the bar on the display
	;Have all 0's all segments are on...
	MOV		R1, #0x000
	;Have a single segment off, we will move this to correct position
	MOV		R2, #0x001
	;MOVE SEGMENT TO CORRECT LOCATION USING COUNT + OFFSET
	LSL		R2, R2, R0

	STRH 	R2, [R9]	;write to LEDBar
   	STRB	R0, [R7]	;STORE Count + Offset

	STRB 	R6, [R10]		;STORE currENC as prevENC for FUTURE USE


;###### TO ACCESS LEDS
;### WRITE bits 1:7 of LEDBAR
;SET P3_0:8 TO THE INFORMATION WE WANT WRITTEN TO THE LED BAR 0:7
	LDR		R0, [R9]
	MOV		R1, #0x10000000 ; EVEN ADDRESS 
	STRB	R0, [R1]


;### WRITE bits 8:9 of LEDBAR
;SET P3_0:1 TO THE INFORMATION WE WANT WRITTEN TO THE LED BAR 8:9
	LDR		R0, [R9]		 
	LSR 	R0, R0, #8
	AND 	R0, R0, #2_11
	MOV		R1, #0x10000001 ; ODD ADDRESS 
	STRB	R0, [R1]


;CONTEXT RESTORE
	POP 	{R0-R2, R4-R12}
	SUBS 	PC, LR, #4 ; return

;####HOW TO MOVE THE ITEMS
;	PREV	CURR
;	00	   	00		- NOCHANGE - 0
;	00		01		- CCW --> Move Left	  +1
;	00		10		- CW  --> Move Right  -1
;	00		11		- ILLEGAL - 0

;	01		00		- CW  --> Move Right
;	01		01		- NOCHANGE - 0
;	01		10		- ILLEGAL - 0
;	01		11		- CCW --> Move Left

;	10		00		- CCW --> Move Left
;	10		01		- ILLEGAL - 0
;	10		10		- NOCHANGE - 0
;	10		11		- CW  --> Move Right

;	11		00		- ILLEGAL - 0
;	11		01		- CW  --> Move Right
;	11		10		- CCW --> Move Left
;	11		11		- NOCHANGE - 0
rotaryLUT	DCB		0,	+1,	-1,	0
			DCB		-1,	0,	0,	+1
			DCB		+1,	0,	0,	-1
			DCB		0,	-1,	+1,	0

	END