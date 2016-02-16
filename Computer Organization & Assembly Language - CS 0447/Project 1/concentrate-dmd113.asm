# PROPERTY OF DEVANSH M. DESAI
# CS 0447 - SPRING 2015 - 3/2/15

.data
GameOver:	.asciiz	"Game over."
MatchedMsg:	.asciiz "\nNumber of card pairs matched: "
NotMatchedMsg:	.asciiz	"\nNumber of card pairs not matched: "
Cards:		.byte	0, 0, 0, 0, 1, 1, 1, 1, 2, 2, 2, 2, 3, 3, 3, 3, 4, 4, 4, 4, 5, 5, 5, 5, 6, 6, 6, 6, 7, 7, 7, 7, 8, 8, 8, 8, 9, 9, 9, 9
Status:		.byte 	0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0
		##########################
Zero: .half	0xF00F, #--XXXX--   0000 1111 1111 0000    
		0x0C30, #-X----X-   0011 0000 0000 1100    
		0x0C30, #-X----X-   0011 0000 0000 1100    
		0x0C30, #-X----X-   0011 0000 0000 1100    
		0x0C30, #-X----X-   0011 0000 0000 1100    
		0x0C30, #-X----X-   0011 0000 0000 1100    
		0x0C30, #-X----X-   0011 0000 0000 1100    
		0xF00F  #--XXXX--   0000 1111 1111 0000    
		##########################
One: .half	0xC000, #----X---   0000 0000 1100 0000    
		0xC003, #---XX---   0000 0011 1100 0000   
		0xC00C, #--X-X---   0000 1100 1100 0000    
		0xC000, #----X---   0000 0000 1100 0000    
		0xC000, #----X---   0000 0000 1100 0000    
		0xC000, #----X---   0000 0000 1100 0000    
		0xC000, #----X---   0000 0000 1100 0000    
		0xFC3F  #-XXXXXX-   0011 1111 1111 1100   
		##########################
Two: .half	0xF00F, #--XXXX--   0000 1111 1111 0000    
		0x0C30, #-X----X-   0011 0000 0000 1100    
		0x3000, #-----X--   0000 0000 0011 0000   
		0xC000, #----X---   0000 0000 1100 0000    
		0x0003, #---X----   0000 0011 0000 0000    
		0x000C, #--X-----   0000 1100 0000 0000    
		0x0030, #-X------   0011 0000 0000 0000    
		0xFC3F  #-XXXXXX-   0011 1111 1111 1100    
		##########################
Three: .half	0xF03F, #-XXXXX--   0011 1111 1111 0000    
		0x0C00, #------X-   0000 0000 0000 1100    
		0x0C00, #------X-   0000 0000 0000 1100    
		0xF03F, #-XXXXX--   0011 1111 1111 0000    
		0x0C00, #------X-   0000 0000 0000 1100    
		0x0C00, #------X-   0000 0000 0000 1100    
		0x0C00, #------X-   0000 0000 0000 1100    
		0xFC3F  #-XXXXXX-   0011 1111 1111 1100    
		##########################
Four: .half	0x0C00, #------X-   0000 0000 0000 1100    
		0xCC00, #----X-X-   0000 0000 1100 1100    
		0x0C03, #---X--X-   0000 0011 0000 1100    
		0x0C0C, #--X---X-   0000 1100 0000 1100    
		0xFC3F, #-XXXXXX-   0011 1111 1111 1100    
		0x0C00, #------X-   0000 0000 0000 1100    
		0x0C00, #------X-   0000 0000 0000 1100    
		0x0C00  #------X-   0000 0000 0000 1100    
		##########################
Five: .half	0xFC3F, #-XXXXXX-   0011 1111 1111 1100    
		0x0030, #-X------   0011 0000 0000 0000    
		0x0030, #-X------   0011 0000 0000 0000    
		0xF03F, #-XXXXX--   0011 1111 1111 0000    
		0x0C00, #------X-   0000 0000 0000 1100    
		0x0C00, #------X-   0000 0000 0000 1100    
		0x0C00, #------X-   0000 0000 0000 1100    
		0xF03F  #-XXXXX--   0011 1111 1111 0000    
		##########################
Six: .half	0x0C00, #-----X--   0000 0000 0000 1100    
		0x3000, #----X---   0000 0000 0011 0000    
		0x0003, #---X----   0000 0011 0000 0000    
		0xF00F, #--XXXX--   0000 1111 1111 0000
		0x0C30, #-X----X-   0011 0000 0000 1100    	
		0x0C30, #-X----X-   0011 0000 0000 1100    
		0x0C30, #-X----X-   0011 0000 0000 1100    
		0xF00F  #--XXXX--   0000 1111 1111 0000    
		##########################
Seven: .half	0xFC3F, #-XXXXXX-   0011 1111 1111 1100    
		0x0C00, #------X-   0000 0000 0000 1100    
		0x3000, #-----X--   0000 0000 0011 0000    
		0xC000, #----X---   0000 0000 1100 0000    
		0x0003, #---X----   0000 0011 0000 0000    
		0x000C, #--X-----   0000 1100 0000 0000    
		0x0030, #-X------   0011 0000 0000 0000    
		0x0030  #-X------   0011 0000 0000 0000    
		##########################
Eight: .half	0xF00F, #--XXXX--   0000 1111 1111 0000    
		0x0C30, #-X----X-   0011 0000 0000 1100    
		0x0C30, #-X----X-   0011 0000 0000 1100    
		0xF00F, #--XXXX--   0000 1111 1111 0000    
		0x0C30, #-X----X-   0011 0000 0000 1100    
		0x0C30, #-X----X-   0011 0000 0000 1100    
		0x0C30, #-X----X-   0011 0000 0000 1100   
		0xF00F  #--XXXX--   0000 1111 1111 0000    
		##########################
Nine: .half	0xF00F, #--XXXX--   0000 1111 1111 0000    
		0x0C30, #-X----X-   0011 0000 0000 1100    
		0x0C30, #-X----X-   0011 0000 0000 1100   
		0xFC0F, #--XXXXX-   0000 1111 1111 1100    
		0x0C00, #------X-   0000 0000 0000 1100    
		0x0C00, #------X-   0000 0000 0000 1100    
		0x0C00, #------X-   0000 0000 0000 1100    
		0xFC3F  #-XXXXXX-   0011 1111 1111 1100
		########################## (CARD 10)
Full: .half	0xFC3F, #-XXXXXX-   0011 1111 1111 1100
		0xFC3F,	#-XXXXXX-   0011 1111 1111 1100
		0xFC3F,	#-XXXXXX-   0011 1111 1111 1100
		0xFC3F,	#-XXXXXX-   0011 1111 1111 1100
		0xFC3F,	#-XXXXXX-   0011 1111 1111 1100
		0xFC3F,	#-XXXXXX-   0011 1111 1111 1100
		0xFC3F,	#-XXXXXX-   0011 1111 1111 1100
		0xFC3F	#-XXXXXX-   0011 1111 1111 1100
		########################## (CARD 11)
Smiley: .half	0x0000, #--------   0000 0000 0000 0000
		0x3C3C, #-XX--XX-   0011 1100 0011 1100
		0x0000, #--------   0000 0000 0000 0000
	     	0x0C30, #-X----X-   0011 0000 0000 1100
		0x300C, #--X--X--   0000 1100 0011 0000
		0xC003, #---XX---   0000 0011 1100 0000
		0x0000, #--------   0000 0000 0000 0000
		0x0000  #--------   0000 0000 0000 0000
		########################## (CARD 12)
Sad: .half	0x3C3C, #-XX--XX-   0011 1100 0011 1100
		0x0000, #--------   0000 0000 0000 0000
		0xC003, #---XX---   0000 0011 1100 0000
		0x300C, #--x--X--   0000 1100 0011 0000
		0x0C30, #-X----X-   0011 0000 0000 1100
		0x0000, #--------   0000 0000 0000 0000
		0x0000  #--------   0000 0000 0000 0000
		########################## (CARD 13)
Blank: .half	0x0000, #--------   0000 0000 0000 0000
		0x0000, #--------   0000 0000 0000 0000
		0x0000, #--------   0000 0000 0000 0000
		0x0000, #--------   0000 0000 0000 0000
		0x0000, #--------   0000 0000 0000 0000
		0x0000, #--------   0000 0000 0000 0000
		0x0000, #--------   0000 0000 0000 0000
		0x0000  #--------   0000 0000 0000 0000
		########################## (CARD 14)
Colon: .half	0x0000, #--------   0000 0000 0000 0000
		0x0000, #--------   0000 0000 0000 0000
		0xC003, #---XX---   0000 0011 1100 0000
		0x0000, #--------   0000 0000 0000 0000
		0x0000, #--------   0000 0000 0000 0000
		0xC003, #---XX---   0000 0011 1100 0000
		0x0000, #--------   0000 0000 0000 0000
		0x0000  #--------   0000 0000 0000 0000
		
.text
li	$t0, 0xFFFF0008
addi	$t0, $t0, 4
move	$a1, $t0
li	$a0, 3				
jal	drawNumber				# This will draw the starting time 3:00 on the screen.
addi	$t0, $t0, 2
move	$a1, $t0
li	$a0, 14
jal	drawNumber
addi	$t0, $t0, 2
move	$a1, $t0
li	$a0, 0
jal	drawNumber
addi	$t0, $t0, 2
move	$a1, $t0
li	$a0, 0
jal	drawNumber

addi	$t0, $t0, 4
blankcards1:					# This will draw the turned over cards on the screen.
addi	$t0, $t0, 144
blankcards2:
addi	$t3, $t3, 1
addi	$t0, $t0, 2
move	$a1, $t0
li	$a0, 10
jal	drawNumber
beq	$t3, 8, blankcards1
beq	$t3, 16, blankcards1
beq	$t3, 24, blankcards1
beq	$t3, 32, blankcards1
bne	$t3, 40, blankcards2

ShuffleCardsInit:				# This shuffles all of the cards in the array
li	$t0, 0					# Counts how many times the function has run
ShuffleCards:
la	$t1, Cards
li	$v0, 30					# Use time for seed, this way its different every time
syscall
li	$a1, 40					# 39 is max value
li	$v0, 42
syscall
add	$t1, $t1, $t0				# Add to base Cards address, this way it can go through the entire array
lb	$t2, ($t1)				# Loads sorted byte into $t2
la	$t1, Cards
add	$t1, $t1, $a0				# Add random number to Cards address
lb	$t3, ($t1)				# Load byte from random address
sb	$t2, ($t1)				# Store the counter byte to the random address
la	$t1, Cards
add	$t1, $t1, $t0				# Add the counter to the Cards address
sb	$t3, ($t1)				# Store the random address byte to the counter address
addi	$t0, $t0, 1				# Increment counter
beq	$t0, 40, ExitShuffleCards
j	ShuffleCards
ExitShuffleCards:
li	$t0, 0
li	$t1, 0
li	$t2, 0
li	$t3, 0

li	$s4, 0			# $s4 contains card number where the cursor is located
li	$a0, 0			# Set first highlighted card to the top right card
li	$a1, 9
li	$a2, 1
li	$t6, 8
li	$t7, 10
jal	drawSquare


RandomGoldenCard:
li	$v0, 30
syscall
li	$a1, 0
li	$a1, 40
li	$v0, 42
syscall
move	$s2, $a0		# $s2 contains smiley's card number
li	$a1, 0
jal	GetAddress
move	$a1, $t0
li	$t0, 0
li	$a0, 11
jal	drawNumber



##############################################

Start:
lw	$t0, 0xFFFF0000		# Will begin the game when a key is pressed
beqz	$t0, Start

StartTimer:
li	$v0, 30
syscall
div	$s0, $a0, 1000		# Convert ms to s
addi	$s0, $s0, 180
addi	$s1, $s0, -170		# Start timer for smiley card

#######################################################################################################################################

Time:
li	$t4, 0
li	$t5, 0
li	$t6, 0

li	$v0, 30			# Get current time
syscall
div	$t0, $a0, 1000
move	$t6, $t0
sub	$t0, $s0, $t0		# Subtract current time from timer time
div	$t4, $t0, 60		# Put MINs since game started in $t4
mfhi	$t5			# Put 10s in $t5
div	$t5, $t5, 10		
mfhi	$t6			# Put 1s in $t6
bge	$t6, $v1, Pause
AfterPause:
StatusCheckInit:
li	$t1, 0			# Counts position in Status array
li	$t2, 0			# Counts matches
StatusCheck:
la	$t0, Status
add	$t0, $t0, $t1
lb	$t3, ($t0)
beq	$t3, 1, Increment
StatusCheckNext:
addi	$t1, $t1, 1
beq	$t1, 40, AfterStatusCheck
j	StatusCheck
Increment:
addi	$t2, $t2, 1		# Increment matches counter
j	StatusCheckNext
AfterStatusCheck:
beq	$t2, 40, ExitProgram
beq	$t4, $0, jalTime
j	contTime
jalTime:
jal 	CheckTime



##############################################

# This draws a new SECOND number every second
contTime:
li	$t0, 0xFFFF0008
addi	$t0, $t0, 10
move	$a1 $t0
beq	$t6, 9, nineSec
beq	$t6, 8, eightSec
beq	$t6, 7, sevenSec
beq	$t6, 6, sixSec
beq	$t6, 5, fiveSec
beq	$t6, 4, fourSec
beq	$t6, 3, threeSec
beq	$t6, 2, twoSec
beq	$t6, 1, oneSec
beq	$t6, 0, zeroSec
nineSec:
li	$a0, 9
jal	drawNumber
j	contTime2
eightSec:
li	$a0, 8
jal	drawNumber
j	contTime2
sevenSec:
li	$a0, 7
jal	drawNumber
j	contTime2
sixSec:
li	$a0, 6
jal	drawNumber
j	contTime2
fiveSec:
li	$a0, 5
jal	drawNumber
j	contTime2
fourSec:
li	$a0, 4
jal	drawNumber
j	contTime2
threeSec:
li	$a0, 3
jal	drawNumber
j	contTime2
twoSec:
li	$a0, 2
jal	drawNumber
j	contTime2
oneSec:
li	$a0, 1
jal	drawNumber
j	contTime2
zeroSec:
li	$a0, 0
jal	drawNumber
j	contTime2
##############################################
# This draws a TENs number if needed
contTime2:
li	$t0, 0xFFFF0008
addi	$t0, $t0, 8
move	$a1 $t0
beq	$t5, 5, fiveTen
beq	$t5, 4, fourTen
beq	$t5, 3, threeTen
beq	$t5, 2, twoTen
beq	$t5, 1, oneTen
beq	$t5, 0, zeroTen
fiveTen:
li	$a0, 5
jal	drawNumber
j	contTime3
fourTen:
li	$a0, 4
jal	drawNumber
j	contTime3
threeTen:
li	$a0, 3
jal	drawNumber
j	contTime3
twoTen:
li	$a0, 2
jal	drawNumber
j	contTime3
oneTen:
li	$a0, 1
jal	drawNumber
j	contTime3
zeroTen:
li	$a0, 0
jal	drawNumber
j	contTime3
##############################################
# This draws a new MINUTE number if needed
contTime3:
li	$t0, 0xFFFF0008
addi	$t0, $t0, 4
move	$a1 $t0
beq	$t4, 7, sevenMin
beq	$t4, 6, sixMin
beq	$t4, 5, fiveMin
beq	$t4, 4, fourMin
beq	$t4, 3, threeMin
beq	$t4, 2, twoMin
beq	$t4, 1, oneMin
beq	$t4, 0, zeroMin
sevenMin:
li	$a0, 7
jal	drawNumber
j	CheckGoldenCard
sixMin:
li	$a0, 6
jal	drawNumber
j	CheckGoldenCard
fiveMin:
li	$a0, 5
jal	drawNumber
j	CheckGoldenCard
fourMin:
li	$a0, 4
jal	drawNumber
j	CheckGoldenCard
threeMin:
li	$a0, 3
jal	drawNumber
j	CheckGoldenCard
twoMin:
li	$a0, 2
jal	drawNumber
j	CheckGoldenCard
oneMin:
li	$a0, 1
jal	drawNumber
j	CheckGoldenCard
zeroMin:
li	$a0, 0
jal	drawNumber
##############################################

CheckGoldenCard:					# This checks to see if 10s have passed since the last golden card was drawn
beq	$s5, 1, Poll
li	$v0, 30
syscall
li	$a1, 0
div	$a0, $a0, 1000
bge	$a0, $s1, ChangeGoldenCard

j	Poll

ChangeGoldenCard:					# This changes the golden card to something new
move	$a0, $s2
li	$a1, 0
jal	GetAddress
move	$a1, $t0
li	$t0, 0
li	$a0, 10
jal	drawNumber

ChangeGoldenCardLoop:
la	$t0, Status
li	$v0, 30
syscall
li	$a1, 0
li	$a1, 40
li	$v0, 42
syscall
add	$t0, $t0, $a0
lb	$t1, ($t0)
beq	$t1, 0, ChooseCard
j	ChangeGoldenCardLoop
ChooseCard:
move	$s2, $a0			# $s2 contains smiley's new card number
li	$a1, 0
jal	GetAddress
move	$a1, $t0
li	$t0, 0
li	$a0, 11
jal	drawNumber
addi	$s1, $s1, 10



Poll:					# This polls the keys to see if anything was inputted
beq	$s5, 2, NoInput
li	$t6, 0
li	$t0, 0
la	$t5, 0xFFFF0000			# Load byte to see if keypad was pressed
lw	$t0, 0($t5)
beq	$t0, $0, NoInput

lw	$t0, 4($t5)
li	$a2, 0				# Argument for drawSquare function
li	$t6, 8				# Needed for drawSquare function
li	$t7, 10				# Needed for drawSquare function
beq	$t0, 0xE0, UpDirection
beq	$t0, 0xE1, DownDirection
beq	$t0, 0xE2, LeftDirection
beq	$t0, 0xE3, RightDirection
beq	$t0, 0x42, MiddleKey
NextDraw:
addi	$t6, $t6, 1
beq	$t6, 2, NoInput
beq	$t0, 0xE0, UpDirection
beq	$t0, 0xE1, DownDirection
beq	$t0, 0xE2, LeftDirection
beq	$t0, 0xE3, RightDirection
beq	$t0, 0x42, MiddleKey

UpDirection:
addi	$s4, $s4, -8
blt	$s4, $0, NoUp
addi	$s4, $s4, 8
beq	$t6, 1, UpNext
jal	CheckPosition
UpNext:
li	$a2, 1
addi	$s4, $s4, -8
jal	CheckPosition
NoUp:
addi	$s4, $s4, 8
j	NoInput

DownDirection:
addi	$s4, $s4, 8
bgt	$s4, 39, NoDown
addi	$s4, $s4, -8
beq	$t6, 1, DownNext
jal	CheckPosition
DownNext:
li	$a2, 1
addi	$s4, $s4, 8
jal	CheckPosition
NoDown:
addi	$s4, $s4, -8
j	NoInput

LeftDirection:
addi	$s4, $s4, -1
bltz	$s4, NoLeft
beq	$s4, 7, NoLeft
beq	$s4, 15, NoLeft
beq	$s4, 23, NoLeft
beq	$s4, 31, NoLeft
addi	$s4, $s4, 1
beq	$t6, 1, LeftNext
jal	CheckPosition
LeftNext:
li	$a2, 1
addi	$s4, $s4, -1
jal	CheckPosition
NoLeft:
addi	$s4, $s4, 1
j	NoInput

RightDirection:
addi	$s4, $s4, 1
bge	$s4, 40, NoRight
beq	$s4, 8, NoRight
beq	$s4, 16, NoRight
beq	$s4, 24, NoRight
beq	$s4, 32, NoRight
addi	$s4, $s4, -1
beq	$t6, 1, RightNext
jal	CheckPosition
RightNext:
li	$a2, 1
addi	$s4, $s4, 1
jal	CheckPosition
NoRight:
addi	$s4, $s4, -1
j	NoInput

MiddleKey:
la	$t6, Status			# Check to see if card selected is already matched
add	$t6, $t6, $s4
lb	$t7, ($t6)
beq	$t7, 1, NoInput
addi	$s5, $s5, 1			# Increment how many cards selected
la	$t0, Cards
add	$t0, $t0, $s4			# Add location to Cards memory address
beq	$s5, 2,	MiddleKey2
MiddleKey1:
lb	$s6, ($t0)			# First card selected in $s6
move	$t8, $s4
li	$a0, 0
move	$a0, $s4
jal	GetAddress
li	$a0, 0
move	$a0, $s6
li	$a1, 0
move	$a1, $t0
jal	drawNumber
j	NoInput
MiddleKey2:
bne	$s4, $t8, MiddleKey2Set
li	$s5, 1
j	NoInput
MiddleKey2Set:
lb	$s7, ($t0)			# Second card selected in $s7
move	$t9, $s4
li	$a0, 0
move	$a0, $s4
jal	GetAddress
li	$a0, 0
move	$a0, $s7
li	$a1, 0
move	$a1, $t0
jal	drawNumber
MiddleKeyNext:
beq	$s6, $s7, MatchingCards

NotMatching:				# NotMatching adds 3 seconds to $v1 counter, its checked in Pause
li	$v0, 30
syscall
div	$v1, $a0, 1000
addi	$v1, $v1, 3
j	NoInput



MatchingCards:				# Sets matched cards to "matched" in Status array
li	$s5, 0				# Set cars selected to 0
li	$t4, 1				# Status of 1 means MATCHED
la	$t0, Status
add	$t0, $t0, $t8
sb	$t4, ($t0)
la	$t0, Status
add	$t0, $t0, $t9
sb	$t4, ($t0)
beq	$t8, $s2, IncrementTimer	# Checks if any of the two cards is a golden card
beq	$t9, $s2, IncrementTimer	# It increments the timer by 30s if it is
li	$t0, 0
li	$t8, 0
li	$t8, 0
li	$a0, 0
li	$a1, 0

j	NoInput


IncrementTimer:				# Increment timer if one of the cards matched is a golden card
addi	$sp, $sp, -4
sw	$ra, 0($sp)
addi	$s0, $s0, 30
li	$v0, 30		
syscall			
li	$a1, 0
div	$s1, $a0, 1000
addi	$s1, $s1,10			# Start the Smiley card's 10 second timer again

li	$t5, 0
li	$t7, 0
li	$t6, 0
li	$t6, 0
CountMatched:				# This checks to see if all of the cards have been matched. This makes sure the program doesn't end up looping forever.
la	$t6, Status			# $t6 - How many matchd		$t7 - Temp byte		$t5 - Array counter		$t6 - Address
add	$t6, $t6, $t5
lb	$t7, ($t6)
beq	$t7, 1, Add1
ContMatched:
addi	$t5, $t5, 1
beq	$t6, 40, ExitProgram
beq	$t5, 40, ChooseNewGolden
j	CountMatched
Add1:
addi	$t6, $t6, 1	
j	ContMatched

ChooseNewGolden:			# Chooses a new golden card when the timer has incremented
la	$t0, Status
li	$v0, 30
syscall
li	$a1, 40
li	$v0, 42
syscall
add	$t0, $t0, $a0
lb	$t1, ($t0)
beq	$t1, 0, ChooseGolden
j	ChooseNewGolden
ChooseGolden:
move	$s2, $a0			# $s2 contains smiley's new card number
li	$a1, 0
jal	GetAddress
move	$a1, $t0
li	$t0, 0
li	$a0, 11
jal	drawNumber
lw	$ra, 0($sp)
addi	$sp, $sp, 4
jr	$ra

CheckPosition:				# This checks the position of the given $s4 argument. 
beq	$s4, 0, Pos0			# It is used to determine if it is safe to move for the cursor.
beq	$s4, 1, Pos1
beq	$s4, 2, Pos2
beq	$s4, 3, Pos3
beq	$s4, 4, Pos4
beq	$s4, 5, Pos5
beq	$s4, 6, Pos6
beq	$s4, 7, Pos7
beq	$s4, 8, Pos8
beq	$s4, 9, Pos9
beq	$s4, 10, Pos10
beq	$s4, 11, Pos11
beq	$s4, 12, Pos12
beq	$s4, 13, Pos13
beq	$s4, 14, Pos14
beq	$s4, 15, Pos15
beq	$s4, 16, Pos16
beq	$s4, 17, Pos17
beq	$s4, 18, Pos18
beq	$s4, 19, Pos19
beq	$s4, 20, Pos20
beq	$s4, 21, Pos21
beq	$s4, 22, Pos22
beq	$s4, 23, Pos23
beq	$s4, 24, Pos24
beq	$s4, 25, Pos25
beq	$s4, 26, Pos26
beq	$s4, 27, Pos27
beq	$s4, 28, Pos28
beq	$s4, 29, Pos29
beq	$s4, 30, Pos30
beq	$s4, 31, Pos31
beq	$s4, 32, Pos32
beq	$s4, 33, Pos33
beq	$s4, 34, Pos34
beq	$s4, 35, Pos35
beq	$s4, 36, Pos36
beq	$s4, 37, Pos37
beq	$s4, 38, Pos38
beq	$s4, 39, Pos39

NoInput:
j	Time

# End of main loop
################################################################################################################################
# Start of functions


drawNumber:				# This is a method is draw a number on the board
# $a0 is font
# $a1 is location in hex
move 	$t0, $a1			# $t0 has address
beq	$a0, 0, zeroNum
beq	$a0, 1, oneNum
beq	$a0, 2, twoNum
beq	$a0, 3, threeNum
beq	$a0, 4, fourNum
beq	$a0, 5, fiveNum
beq	$a0, 6, sixNum
beq	$a0, 7, sevenNum
beq	$a0, 8, eightNum
beq	$a0, 9, nineNum
beq	$a0, 10, fullNum
beq	$a0, 11, smileyNum
beq	$a0, 12, sadNum
beq	$a0, 13, blankNum
beq	$a0, 14, colonNum

zeroNum:				# These branches load the address of the font desired by the method
la	$t1, Zero			# They also draw the font but jumping to dn
j	dn
oneNum:
la	$t1, One
j	dn
twoNum:
la	$t1, Two
j	dn
threeNum:
la	$t1, Three
j	dn
fourNum:
la	$t1, Four
j	dn
fiveNum:
la	$t1, Five
j	dn
sixNum:
la	$t1, Six
j	dn
sevenNum:
la	$t1, Seven
j	dn
eightNum:
la	$t1, Eight
j	dn
nineNum:
la	$t1, Nine
j	dn
fullNum:
la	$t1, Full
j	dn
smileyNum:
la	$t1, Smiley
j	dn
sadNum:
la	$t1, Sad
j	dn
blankNum:
la	$t1, Blank
j	dn
colonNum:
la	$t1, Colon

dn:						# This draws the given font
lh	$t2, ($t1)
sh	$t2, 0($t0)
addi	$t1, $t1, 2
lh	$t2, ($t1)
sh	$t2, 16($t0)
addi	$t1, $t1, 2
lh	$t2, ($t1)
sh	$t2, 32($t0)
addi	$t1, $t1, 2
lh	$t2, ($t1)
sh	$t2, 48($t0)
addi	$t1, $t1, 2
lh	$t2, ($t1)
sh	$t2, 64($t0)
addi	$t1, $t1, 2
lh	$t2, ($t1)
sh	$t2, 80($t0)
addi	$t1, $t1, 2
lh	$t2, ($t1)
sh	$t2, 96($t0)
addi	$t1, $t1, 2
lh	$t2, ($t1)
sh	$t2, 112($t0)
jr	$ra


drawSquare:						# Copied this drawSquare method from Lab 5. Modified it a bit to be compatible with this program.
# $a0 contains x coordinate of top left corner
# $a1 contains y coordinate of top left corner
# $a2 contains the colors of the square
addi	$sp, $sp, -4
sw	$ra, 0($sp)
jal	drawHorizontalLine
jal	drawVerticalLine
addi	$a1, $a1, 9
jal	drawHorizontalLine
addi	$a1, $a1, -9
addi	$a0, $a0, 7
jal	drawVerticalLine
lw	$ra, 0($sp)
addi	$sp, $sp, 4
jr	$ra
drawHorizontalLine:					# Draws horizontal line at given x,y coordinate and size
# $a0 contains x coordinate of the left end of the line
# $a1 contains y coordinate of the left end of the line
# $a2 contains the size, or length, of the line
# $a3 contains the color of the line
addi	$sp, $sp, -4
sw	$ra, 0($sp)
li	$t5, 0
add	$t5, $a0, $t6
Hori:
jal	setLED
addi	$a0, $a0, 1
beq	$t5, $a0, exitHorizontal
j	Hori
exitHorizontal:
addi	$a0, $a0, -8
lw	$ra, 0($sp)
addi	$sp, $sp, 4
jr	$ra
drawVerticalLine:					# Draws vertical line at given x,y coordinate and size
# $a0 contains x coordinate of the top end of the line
# $a1 contains y coordinate of the top end of the line
# $a2 contains the size, or length, of the line
# $a3 contains the color of the line
addi	$sp, $sp, -4
sw	$ra, 0($sp)
li	$t5, 0
add	$t5, $a1, $t7
Vert:
jal	setLED
addi	$a1, $a1, 1
beq	$t5, $a1, exitVertical
j	Vert
exitVertical:
addi	$a1, $a1, -10
lw	$ra, 0($sp)
addi	$sp, $sp, 4
jr	$ra


Pause:							# This loop runs when the program has paused for 3s on an unmatched card
li	$v0, 30
syscall
li	$a1, 0
div	$a0, $a0, 1000
bge	$a0, $s1, DontChangeBackToGolden		# Branches to see if any of the two cards unmatched were golden
beq	$t8, $s2, ChangeFirstCardToGolden

DontChangeBackToGolden:
move	$a0, $t8
jal	GetAddress
move	$a1, $t0
li	$a0, 10
jal	drawNumber
beq	$t9, $s2, ChangeSecondCardToGolden
NormalSecondCard:
move	$a0, $t9
jal	GetAddress
move	$a1, $t0
li	$a0, 10
jal	drawNumber
li	$v1, 0
li	$s5, 0
li	$t8, 0
li	$t9, 0
li	$s6, 0
li	$s7, 0
j	AfterPause

ChangeFirstCardToGolden:
li	$a0, 0
li	$a1, 0
move	$a0, $t8
jal	GetAddress
move	$a1, $t0
li	$a0, 11
jal	drawNumber
li	$a0, 0
li	$a1, 0
j	NormalSecondCard

ChangeSecondCardToGolden:
li	$a0, 0
li	$a1, 0
move	$a0, $t9
jal	GetAddress
move	$a1, $t0
li	$a0, 11
jal	drawNumber
li	$a0, 0
li	$a1, 0
li	$v1, 0
li	$s5, 0
li	$t8, 0
li	$t9, 0
li	$s6, 0
li	$s7, 0
j	AfterPause


# Not written by me, used by my drawSquare function
setLED:
# arguments: $a0 is x, $a1 is y, $a2 is color
# byte offset into display = y*16 bytes + (x/4)
addi	$sp, $sp, -20
sw	$ra, 0($sp)
sw	$t0, 4($sp)
sw	$t1, 8($sp)
sw	$t2, 12($sp)
sw	$t3, 16($sp)
li	$t0, 0
sll	$t0,$a1,4		# y*16 bytes
srl 	$t1,$a0,2		# x/4
add 	$t0,$t0,$t1		# byte offset into display
li 	$t2,0xffff0008 		# base address of LED display
add 	$t0,$t2,$t0		# address of byte with the LED
# now, compute led position in the	byte and the mask for it
andi 	$t1,$a0,0x3		# remainder is led position in byte
neg 	$t1,$t1			# negate position for subtraction
addi 	$t1,$t1,3			# bit positions	in	reverse	order
sll	$t1,$t1,1			# led is 2 bits
# compute two masks: one to clear field, one to	set new	color
li	$t2,3
sllv 	$t2,$t2,$t1
not	$t2,$t2			# bit mask for clearing	current	color
sllv 	$t1,$a2,$t1		# bit mask for setting	color
# get current LED value, set the new field, store it back to LED
lbu 	$t3,0($t0)			# read current LED value
and 	$t3,$t3,$t2			# clear	the field for the color
or 	$t3,$t3,$t1			# set color field
sb 	$t3,0($t0)			# update display
lw	$ra, 0($sp)
lw	$t0, 4($sp)
lw	$t1, 8($sp)
lw	$t2, 12($sp)
lw	$t3, 16($sp)
addi	$sp, $sp, 20
jr 	$ra
	
	

CheckTime:						# This checks to see if time has run out once the minute value has reached 0.
beq	$t5, $0, ContCheckTime				# If the minute value is 0, this checks to see if the tens value is 0.
jr	$ra
ContCheckTime:						
bne	$t6, $0, ContCheckTime2				# Checks if seconds is also 0.
j	ExitProgram
ContCheckTime2:						
jr	$ra	
	
ExitProgram:						# This exits the program.
li	$t0, 0xFFFF0008
addi	$t0, $t0, 10
move	$a1 $t0
li	$a0, 0
jal	drawNumber					# This draws a 0 in the seconds position to end the timer.

MatchedCounterInit:					# This essentially counts all matches made in the session.
li	$t1, 0						# Counts position in Status array
li	$t2, 0						# Counts matches
MatchedCounter:
la	$t0, Status
add	$t0, $t0, $t1
lb	$t3, ($t0)
beq	$t3, 1, IncrementMatch
MatchedCounterNext:
addi	$t1, $t1, 1
beq	$t1, 40, CountMatches
j	MatchedCounter
IncrementMatch:
addi	$t2, $t2, 1					# Increment matches counter
j	MatchedCounterNext



CountMatches:						# This prints the final ending game messages and shows the number of unmatched and matched pairs.
li	$v0, 4
la	$a0, GameOver
syscall
li	$v0, 4
la	$a0, MatchedMsg
syscall
li	$v0, 1
li	$a0, 0
div	$t2, $t2, 2
move	$a0, $t2
syscall
li	$v0, 4
la	$a0, NotMatchedMsg
syscall
li	$a0, 0
li	$a0, 20
sub	$a0, $a0, $t2
li	$v0, 1
syscall
li	$v0, 10
syscall





Pos0:	li	$a0, 0					# This function is used by the cursor to draw and undraw.
	li	$a1, 9
	jal	drawSquare
	j	NextDraw
Pos1:	li	$a0, 8		
	li	$a1, 9
	jal	drawSquare
	j	NextDraw
Pos2:	li	$a0, 16		
	li	$a1, 9
	jal	drawSquare
	j	NextDraw
Pos3:	li	$a0, 24		
	li	$a1, 9
	jal	drawSquare
	j	NextDraw
Pos4:	li	$a0, 32		
	li	$a1, 9
	jal	drawSquare
	j	NextDraw
Pos5:	li	$a0, 40		
	li	$a1, 9
	jal	drawSquare
	j	NextDraw
Pos6:	li	$a0, 48		
	li	$a1, 9
	jal	drawSquare
	j	NextDraw
Pos7:	li	$a0, 56		
	li	$a1, 9
	jal	drawSquare
	j	NextDraw
Pos8:	li	$a0, 0		
	li	$a1, 19
	jal	drawSquare
	j	NextDraw
Pos9:	li	$a0, 8		
	li	$a1, 19
	jal	drawSquare
	j	NextDraw
Pos10:	li	$a0, 16		
	li	$a1, 19
	jal	drawSquare
	j	NextDraw
Pos11:	li	$a0, 24		
	li	$a1, 19
	jal	drawSquare
	j	NextDraw
Pos12:	li	$a0, 32		
	li	$a1, 19
	jal	drawSquare
	j	NextDraw
Pos13:	li	$a0, 40		
	li	$a1, 19
	jal	drawSquare
	j	NextDraw
Pos14:	li	$a0, 48		
	li	$a1, 19
	jal	drawSquare
	j	NextDraw
Pos15:	li	$a0, 56		
	li	$a1, 19
	jal	drawSquare
	j	NextDraw
Pos16:	li	$a0, 0		
	li	$a1, 29
	jal	drawSquare
	j	NextDraw
Pos17:	li	$a0, 8		
	li	$a1, 29
	jal	drawSquare
	j	NextDraw
Pos18:	li	$a0, 16		
	li	$a1, 29
	jal	drawSquare
	j	NextDraw
Pos19:	li	$a0, 24		
	li	$a1, 29
	jal	drawSquare
	j	NextDraw
Pos20:	li	$a0, 32		
	li	$a1, 29
	jal	drawSquare
	j	NextDraw
Pos21:	li	$a0, 40		
	li	$a1, 29
	jal	drawSquare
	j	NextDraw
Pos22:	li	$a0, 48		
	li	$a1, 29
	jal	drawSquare
	j	NextDraw
Pos23:	li	$a0, 56		
	li	$a1, 29
	jal	drawSquare
	j	NextDraw
Pos24:	li	$a0, 0		
	li	$a1, 39
	jal	drawSquare
	j	NextDraw
Pos25:	li	$a0, 8		
	li	$a1, 39
	jal	drawSquare
	j	NextDraw
Pos26:	li	$a0, 16		
	li	$a1, 39
	jal	drawSquare
	j	NextDraw
Pos27:	li	$a0, 24		
	li	$a1, 39
	jal	drawSquare
	j	NextDraw
Pos28:	li	$a0, 32		
	li	$a1, 39
	jal	drawSquare
	j	NextDraw
Pos29:	li	$a0, 40	
	li	$a1, 39
	jal	drawSquare
	j	NextDraw
Pos30:	li	$a0, 48		
	li	$a1, 39
	jal	drawSquare
	j	NextDraw
Pos31:	li	$a0, 56		
	li	$a1, 39
	jal	drawSquare
	j	NextDraw
Pos32:	li	$a0, 0		
	li	$a1, 49
	jal	drawSquare
	j	NextDraw
Pos33:	li	$a0, 8		
	li	$a1, 49
	jal	drawSquare
	j	NextDraw
Pos34:	li	$a0, 16		
	li	$a1, 49
	jal	drawSquare
	j	NextDraw
Pos35:	li	$a0, 24		
	li	$a1, 49
	jal	drawSquare
	j	NextDraw
Pos36:	li	$a0, 32		
	li	$a1, 49
	jal	drawSquare
	j	NextDraw
Pos37:	li	$a0, 40		
	li	$a1, 49
	jal	drawSquare
	j	NextDraw
Pos38:	li	$a0, 48		
	li	$a1, 49
	jal	drawSquare
	j	NextDraw
Pos39:	li	$a0, 56		
	li	$a1, 49
	jal	drawSquare
	j	NextDraw




GetAddress:						# This funciton gets the hex address of a given card value (0-39) in $a0. Returns it in $t0.
addi	$sp, $sp, -4
sw	$ra, 0($sp)
li	$t0, 0xFFFF0008
addi	$t0, $t0, 160
beq	$a0, 0, Get0
beq	$a0, 1, Get1
beq	$a0, 2, Get2
beq	$a0, 3, Get3
beq	$a0, 4, Get4
beq	$a0, 5, Get5
beq	$a0, 6, Get6
beq	$a0, 7, Get7
beq	$a0, 8, Get8
beq	$a0, 9, Get9
beq	$a0, 10, Get10
beq	$a0, 11, Get11
beq	$a0, 12, Get12
beq	$a0, 13, Get13
beq	$a0, 14, Get14
beq	$a0, 15, Get15
beq	$a0, 16, Get16
beq	$a0, 17, Get17
beq	$a0, 18, Get18
beq	$a0, 19, Get19
beq	$a0, 20, Get20
beq	$a0, 21, Get21
beq	$a0, 22, Get22
beq	$a0, 23, Get23
beq	$a0, 24, Get24
beq	$a0, 25, Get25
beq	$a0, 26, Get26
beq	$a0, 27, Get27
beq	$a0, 28, Get28
beq	$a0, 29, Get29
beq	$a0, 30, Get30
beq	$a0, 31, Get31
beq	$a0, 32, Get32
beq	$a0, 33, Get33
beq	$a0, 34, Get34
beq	$a0, 35, Get35
beq	$a0, 36, Get36
beq	$a0, 37, Get37
beq	$a0, 38, Get38
beq	$a0, 39, Get39
ReturnGetAddress:
lw	$ra, 0($sp)
addi	$sp, $sp, 4
jr	$ra


Get0:	addi $t0, $t0, 0				# These labels get the address of the given card number
	j	ReturnGetAddress
Get1:	addi $t0, $t0, 2
	j	ReturnGetAddress
Get2:	addi $t0, $t0, 4
	j	ReturnGetAddress
Get3:	addi $t0, $t0, 6
	j	ReturnGetAddress
Get4:	addi $t0, $t0, 8
	j	ReturnGetAddress
Get5:	addi $t0, $t0, 10
	j	ReturnGetAddress
Get6:	addi $t0, $t0, 12
	j	ReturnGetAddress
Get7:	addi $t0, $t0, 14
	j	ReturnGetAddress
Get8:	addi $t0, $t0, 160
	j	ReturnGetAddress
Get9:	addi $t0, $t0, 162
	j	ReturnGetAddress
Get10:	addi $t0, $t0, 164
	j	ReturnGetAddress
Get11:	addi $t0, $t0, 166
	j	ReturnGetAddress
Get12:	addi $t0, $t0, 168
	j	ReturnGetAddress
Get13:	addi $t0, $t0, 170
	j	ReturnGetAddress
Get14:	addi $t0, $t0, 172
	j	ReturnGetAddress
Get15:	addi $t0, $t0, 174
	j	ReturnGetAddress
Get16:	addi $t0, $t0, 320
	j	ReturnGetAddress
Get17:	addi $t0, $t0, 322
	j	ReturnGetAddress
Get18:	addi $t0, $t0, 324
	j	ReturnGetAddress
Get19:	addi $t0, $t0, 326
	j	ReturnGetAddress
Get20:	addi $t0, $t0, 328
	j	ReturnGetAddress
Get21:	addi $t0, $t0, 330
	j	ReturnGetAddress
Get22:	addi $t0, $t0, 332
	j	ReturnGetAddress
Get23:	addi $t0, $t0, 334
	j	ReturnGetAddress
Get24:	addi $t0, $t0, 480
	j	ReturnGetAddress
Get25:	addi $t0, $t0, 482
	j	ReturnGetAddress
Get26:	addi $t0, $t0, 484
	j	ReturnGetAddress
Get27:	addi $t0, $t0, 486
	j	ReturnGetAddress
Get28:	addi $t0, $t0, 488
	j	ReturnGetAddress
Get29:	addi $t0, $t0, 490
	j	ReturnGetAddress
Get30:	addi $t0, $t0, 492
	j	ReturnGetAddress
Get31:	addi $t0, $t0, 494
	j	ReturnGetAddress
Get32:	addi $t0, $t0, 640
	j	ReturnGetAddress
Get33:	addi $t0, $t0, 642
	j	ReturnGetAddress
Get34:	addi $t0, $t0, 644
	j	ReturnGetAddress
Get35:	addi $t0, $t0, 646
	j	ReturnGetAddress
Get36:	addi $t0, $t0, 648
	j	ReturnGetAddress
Get37:	addi $t0, $t0, 650
	j	ReturnGetAddress
Get38:	addi $t0, $t0, 652
	j	ReturnGetAddress
Get39:	addi $t0, $t0, 654
	j	ReturnGetAddress
