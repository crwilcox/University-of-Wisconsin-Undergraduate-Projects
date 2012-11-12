; Filename:     ADuC702x.s 
; Author:       ECE 315 Staff 
; Description:  Start-up code for ADuC702x CPUs

	EXPORT 	Reset_Handler
    IMPORT  __main
	IMPORT  Undef_Handler
	IMPORT  SWI_Handler
	IMPORT  PAbt_Handler
	IMPORT  DAbt_Handler
	IMPORT  IRQ_Handler
	IMPORT  FIQ_Handler

	INCLUDE ADuC7026.inc	;MMR definitions

; standard definitions of Mode bits and Interrupt (I & F) flags in PSRs
Mode_USR        EQU     0x10
Mode_FIQ        EQU     0x11
Mode_IRQ        EQU     0x12
Mode_SVC        EQU     0x13
Mode_ABT        EQU     0x17
Mode_UND        EQU     0x1B
Mode_SYS        EQU     0x1F

I_Bit           EQU     0x80      ; when I bit is set, IRQ is disabled
F_Bit           EQU     0x40      ; when F bit is set, FIQ is disabled

UND_Stack_Size  EQU     0x00000000
SVC_Stack_Size  EQU     0x00000008
ABT_Stack_Size  EQU     0x00000000
FIQ_Stack_Size  EQU     0x00000040
IRQ_Stack_Size  EQU     0x00000040
USR_Stack_Size  EQU     0x00000400

Stack_Size      EQU     (UND_Stack_Size + SVC_Stack_Size + ABT_Stack_Size + \
                         FIQ_Stack_Size + IRQ_Stack_Size + USR_Stack_Size)

                AREA    SRAM, NOINIT, READWRITE, ALIGN=3
Stack_Mem       SPACE   Stack_Size

Stack_Top       EQU     Stack_Mem + Stack_Size

; Area Definition and Entry Point
	AREA    Reset, CODE, READONLY
	ARM

; Exception Vectors mapped to Address 0.
; Absolute addressing mode must be used.
Vectors 
	LDR     PC, Reset_Addr         
	LDR     PC, Undef_Addr
	LDR     PC, SWI_Addr
	LDR     PC, PAbt_Addr
	LDR     PC, DAbt_Addr
	NOP                            ; Reserved Vector 
	LDR     PC, IRQ_Addr
	LDR     PC, FIQ_Addr

Reset_Addr      DCD     Reset_Handler
Undef_Addr      DCD     Undef_Handler
SWI_Addr        DCD     SWI_Handler
PAbt_Addr       DCD     PAbt_Handler
DAbt_Addr       DCD     DAbt_Handler
IRQ_Addr        DCD     IRQ_Handler
FIQ_Addr        DCD     FIQ_Handler

Reset_Handler   

;setup PLL and power control
	LDR     R1, =PLL_MMR_BASE
	MOV		R0, #0xAA	;key1
	STR		R0, [R1, #PLLKEY1]
	MOV		R0, #0x01	;external oscillator, using PLL
	STR		R0, [R1, #PLLCON]
	MOV		R0, #0x55	;key2
	STR		R0, [R1, #PLLKEY2]
	MOV		R0, #0x01	;key1
	STR		R0, [R1, #POWKEY1]
	MOV		R0, #0x00	;active, CD=0 (41.78MHz)
	STR		R0, [R1, #POWCON]
	MOV		R0, #0xF4	;key2
	STR		R0, [R1, #POWKEY2]


; Setup Stack for each mode
	LDR     R0, =Stack_Top
;  Enter Undefined Instruction Mode and set its Stack Pointer
	MSR     CPSR_c, #Mode_UND:OR:I_Bit:OR:F_Bit
	MOV     SP, R0
	SUB     R0, R0, #UND_Stack_Size
;  Enter Abort Mode and set its Stack Pointer
	MSR     CPSR_c, #Mode_ABT:OR:I_Bit:OR:F_Bit
	MOV     SP, R0
	SUB     R0, R0, #ABT_Stack_Size
;  Enter FIQ Mode and set its Stack Pointer
	MSR     CPSR_c, #Mode_FIQ:OR:I_Bit:OR:F_Bit
	MOV     SP, R0
	SUB     R0, R0, #FIQ_Stack_Size
;  Enter IRQ Mode and set its Stack Pointer
	MSR     CPSR_c, #Mode_IRQ:OR:I_Bit:OR:F_Bit
	MOV     SP, R0
	SUB     R0, R0, #IRQ_Stack_Size
;  Enter Supervisor Mode and set its Stack Pointer
	MSR     CPSR_c, #Mode_SVC:OR:I_Bit:OR:F_Bit
	MOV     SP, R0
	SUB     R0, R0, #SVC_Stack_Size
;  Enter User Mode and set its Stack Pointer
	MSR     CPSR_c, #Mode_USR		;FIQ/IRQ are enabled!
	MOV     SP, R0
	SUB     SL, SP, #USR_Stack_Size
; jump to user code
	B      __main

	END
