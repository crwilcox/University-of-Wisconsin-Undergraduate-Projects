; Filename:     exceptions.s 
; Author:       Christopher Wilcox & James Nystrom 
; Description:  exception handlers

	EXPORT  Undef_Handler
	EXPORT  SWI_Handler
	EXPORT  PAbt_Handler
	EXPORT  DAbt_Handler
	;EXPORT  IRQ_Handler
	EXPORT  FIQ_Handler
	IMPORT  DisplayData
	INCLUDE ADuC7026.inc			;MMR definitions

	AREA    FLASH, CODE, READONLY
	ARM

; handlers for exception modes
Undef_Handler				;undefined instruction exception
	MOVS	PC, LR			;return
;
SWI_Handler					;SWI exception
	MOVS	PC, LR			;return
;
PAbt_Handler				;prefetch abort exception
	B	PAbt_Handler		;infinite loop
;	SUBS	PC, LR, #4		;return
;
DAbt_Handler				;data abort exception
	B	DAbt_Handler		;infinite loop
;	SUBS	PC, LR, #8		;return
;
;IRQ_Handler					;IRQ exception
;	MOVED TO IRQ_Handler.s	
;	SUBS PC, LR, #4 ; return
;

FIQ_Handler					;FIQ exception
							;add your code here
	SUBS	PC, LR, #4		;return
;

	END
