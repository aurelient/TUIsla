	.file	"blink.c"
__SREG__ = 0x3f
__SP_H__ = 0x3e
__SP_L__ = 0x3d
__CCP__ = 0x34
__tmp_reg__ = 0
__zero_reg__ = 1
	.text
.global	main
	.type	main, @function
main:
/* prologue: function */
/* frame size = 0 */
/* stack size = 0 */
.L__stack_usage = 0
	ldi r24,lo8(8)
	out 55-32,r24
.L2:
	out 56-32,r24
	 ldi r18,lo8(1599999)
    ldi r19,hi8(1599999)
    ldi r20,hlo8(1599999)
    1:subi r18,1
    sbci r19,0
    sbci r20,0
    brne 1b
	rjmp .
	nop
	out 56-32,__zero_reg__
	 ldi r18,lo8(1599999)
    ldi r19,hi8(1599999)
    ldi r20,hlo8(1599999)
    1:subi r18,1
    sbci r19,0
    sbci r20,0
    brne 1b
	rjmp .
	nop
	rjmp .L2
	.size	main, .-main
