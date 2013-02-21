// code from: http://imakeprojects.com/Projects/avr-tutorial/

#include <avr/io.h>     
#include <util/delay.h>

// Function prototype
int set_PORTB_bit(int position, int value);

int main(void)
{
  int temp;

  // Set Port B 4 as output (binary 1), 3 as input (binary 0)
  // PORTB bit 3 = physical pin #2 on the ATTINY85
  // PORTB bit 4 = physical pin #3 on the ATTINY85
  DDRB = 0b00010000;

  // Set up a forever loop
  for ( ; 1==1 ; )
  {
    // Bitwise AND the state of the pins of
    // PORT B with 0000 1000 (PB3)
    // In other words, set 'temp' to be 
    // the value of PINB's bit #3.
    // 'temp' will therefore only ever be 0x08 or 0x00.
    temp = (PINB & 0x08);

    // If the button is pushed (i.e. that bit is 0) 
    // then turn the LED on with a function.
    if ( temp == 0b00001000 ) // 0b00001000
    {
        set_PORTB_bit(4,1);  // LED on (LED is on PB4)
    }
    else
    {
        set_PORTB_bit(4,0);  // LED off (LED is on PB4)
    }
  }
  return 1;
}

// Function code
int set_PORTB_bit(int position, int value)
{
  // Sets or clears the bit in position 'position' 
  // either high or low (1 or 0) to match 'value'.
  // Leaves all other bits in PORTB unchanged.
                
  if (value == 0)
  {
    PORTB &= ~(1 << position);      // Set bit position low
  }
  else
  {
    PORTB |= (1 << position);       // Set high, leave others alone
  }

  return 1;
}