	.file	"button.c"
__SREG__ = 0x3f
__SP_H__ = 0x3e
__SP_L__ = 0x3d
__CCP__ = 0x34
__tmp_reg__ = 0
__zero_reg__ = 1
	.text
.global	set_PORTB_bit
	.type	set_PORTB_bit, @function
set_PORTB_bit:
/* prologue: function */
/* frame size = 0 */
/* stack size = 0 */
.L__stack_usage = 0
	cp r22,__zero_reg__
	cpc r23,__zero_reg__
	brne .L2
	in r25,56-32
	ldi r18,lo8(1)
	ldi r19,hi8(1)
	rjmp 2f
1:	lsl r18
	rol r19
2:	dec r24
	brpl 1b
	com r18
	and r18,r25
	out 56-32,r18
	rjmp .L3
.L2:
	in r25,56-32
	ldi r18,lo8(1)
	ldi r19,hi8(1)
	rjmp 2f
1:	lsl r18
	rol r19
2:	dec r24
	brpl 1b
	or r25,r18
	out 56-32,r25
.L3:
	ldi r24,lo8(1)
	ldi r25,hi8(1)
/* epilogue start */
	ret
	.size	set_PORTB_bit, .-set_PORTB_bit
.global	main
	.type	main, @function
main:
/* prologue: function */
/* frame size = 0 */
/* stack size = 0 */
.L__stack_usage = 0
	ldi r24,lo8(16)
	out 55-32,r24
.L8:
	sbis 54-32,3
	rjmp .L6
	ldi r24,lo8(4)
	ldi r25,hi8(4)
	ldi r22,lo8(1)
	ldi r23,hi8(1)
	rjmp .L9
.L6:
	ldi r24,lo8(4)
	ldi r25,hi8(4)
	ldi r22,lo8(0)
	ldi r23,hi8(0)
.L9:
	rcall set_PORTB_bit
	rjmp .L8
	.size	main, .-main
