Pitt ID: dmd113

The first number is the sum of all of the individual digits of the session number.
The second number is any number that is less than the first digit of the session number.
The third number is any number that is less than the last digit of the session number.
The fourth number is the result of shifting the bits of the second digit of the session number left by that same number.
The fifth number is the result of adding the second digit of the session number to itself (or multiplying that number by 2).

Example:
Session Number: 7846242265
Number 1: 46        (7+8+4+6+2+4+2+2+6+5 = 46)
Number 2: 6         (6 < 7)
Number 3: 0         (0 < 5)
Number 4: 2048      (8 << 8 = 2048)
Number 5: 16        (8 + 8 = 16)
