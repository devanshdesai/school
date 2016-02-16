***********************************************
* README for JrMIPS Processor
* Devansh M. Desai
* dmd113@pitt.edu
* University of Pittsburgh
* CS 0447 - Spring 2015
***********************************************


**********************************************
********         INTRODUCTION         ********
**********************************************
The majority of this processor is based off of Lab 11 and from the datapath
dicussed during lecture; however, I did add a few more controls to make the wiring less
crowded around the main control area such as JumpControl, BranchControl,
ShiftControl, and ALUControl.

Besides these control subcircuits, the processor is very straightforward and is
almost the same as the datapath described in class.


**********************************************
**********         CIRCUITS         **********
**********************************************
main - This is the main circuit. It has the clock, ROM, RAM, and LED display
      which will be used for checking output.

Registers - This is the register subcircuit which is an extension of Lab 11.
      It now features eight registers instead of two. The outputs for Registers
      are as follows:
            ReadData1 - Data from $rs
            ReadData2 - Data from $rt

ALU - This is the aritmetic logic unit. It has four operations: AND (00), NOR
      (01), ADD (10), and SUB (11). It also checks for branch less than, greater
      than, and equal to zero. The outputs for ALU are as follows:
            Result - 16-bit result of the ALu
            BranchEqZero - 1 if $rs is equal to 0
            BranchGreater - 1 if $rs is greater than 0
            BranchLess - 1 if $rs is less than 0

Control - This is the decoder/control. It breaks down the instruction and outputs
      values telling other parts of the program what to do. The Control outputs are
      as follows:
            ALUSrc - 0 for using value from register, 1 for immediate
            MemToReg - 1 only for lw, Sets register write data to RAM output
            RegWrite - 1 when the instruction requires writing to a register
            MemRead - 1 only for lw, Sets RAM to read mode
            MemWrite - 1 only for sw, Sets RAM to store mode
            Branch - 1 only for bp, bn, bx, or bz instructions
            Halt - 1 only for halt
            Put - 1 only for put
            Addi - 1 only for the signed addi instruction
            Shift - 1 only for sll, srl, sllv, and srlv instructions

BranchControl - This checks the opcode to see if the instruction is bp, bn,
      bx, or bz. The BranchControl outputs are as follows:
            bp - 1 for bp
            bn - 1 for bn
            bx - 1 for bx
            bz - 1 for bz

JumpControl - This checks the opcode to see if the instruction is jr, jal,
      or j. The outputs for JumpControl are as follows:
            Output
                  000 - non-jump instructions
                  100 - jr
                  010 - jal
                  001 - j

ALUControl - This checks the opcode and subop to set the ALU to the appropriate ALUOp.
      The outputs for ALUControl are as follows:
            ALUOp
                  00 - AND
                  01 - NOR
                  10 - ADD
                  11 - SUB

ShiftControl - This checks the opcode and subop to see if the instruction is sll,
      srl, sllv, or srlv.
            LeftOrRight
                  0 - Left shift
                  1 - Right shift
            ShamtOrReg
                  0 - Use shamt
                  1 - Use register's least significant nibble


**********************************************
***********         TESTING        ***********
**********************************************
This processor runs each of the all of the following test cases given with the
project without error:
            addi.asm
            addshift.asm
            addui.asm
            bn.asm
            bp.asm
            bz.asm
            function.asm
            increment.asm
            jal.asm
            jr.asm
            load.asm
            loadstore.asm
            lwsw.asm
            put.asm
            put2.asm
            sll.asm
            sllv.asm
            srl.asm


**********************************************
***********         ERRORS         ***********
**********************************************
I found no errors with the program. Everything seems to be working fine.
