Devansh Desai
CS 0447
Project 1 - Concentrate
3/4/15


      First, I started off by getting the timer to work. I used syscall 30 to find the current time
and then I converted it to seconds and added 180, for 3 mins and stored it in $s0. Then, I drew 3:00
on the screen and then I drew cards faced over on the screen. Next, I created the cursor, pointing it to
the top left card when it firsts begins. Then I implemented the movement. I used register $s4 to keep
track of where the cursor was on the board. $s4 ranged from 0-39 - 0 for the first card and 39 for the
last card. Finally, I created the Cards array and used a randomizing algorithm to shuffle the cards.
The algorithm went across the array of cards, from 0-39, and swapped the card from a random location in
the array that was found from using syscall 42. The seed used was the lower bits of time that is obtained
from syscall 30 in $a0. Some of my main functions included drawSquare, getAddress, and drawNumber. drawSquare
was used to erase the current cursor by setting it to the off LED position and then draw the new cursor by
drawing a square around wherever the cursor was, which was tracked by $s4. The getAddress function returned
the address of any of the 40 card positions on the screen. It used branch statements to see which card was
inputted in $a0. The drawNumber function had arguments $a0 as the number desired (0-9, smiley, etc.) and
$a1 as the location of where to draw the number. This location was retrieved using the getAddress function.
The smiley card is implemented by initially drawing a random card with the smiley and storing its card value
in $s2. If $s2 equals one of the cards matched, I increment the timer and choose a different random card, if
all of them haven't been matched already.

Problem with my program:
The only problem with my program so far is that when a card is selected, the red box around the number breaks
up into just the horizontal lines above and below the number. This is because drawing the number overwrites the
red square's vertical lines. I felt that this was insignificant so I didn't pursue solving this issue. Everything
else in my program works flawlessly and I don't believe there are any more bugs as I have played and have had friends
play the game multiple times to debug it.
