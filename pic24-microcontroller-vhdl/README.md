# PIC24 Microcontroller designed using VHDL

Description of the signals used:

1. ALUOP: a 2-bit signal representing the operation that ALU will perform
2. Branch: set to 1 if a Branch, Expr instruction follows, or if a Branch Z, Expr instruction follows and Z=1
3. ZEn: set to 1 when the current operation can activate the Z flag
4. WrReg: contains the position of the register where the value needs to be written
5. Mem2Reg: selects what will be written to the register (between the operation from ALU and a value read from the data memory)
6. ZData: holds the value of Z for the current operation
7. PC: program counter, points to the next instruction to be executed (from ROM memory)
8. SWA/SWB: the least significant 4 bits from INW0 and INW1
9. BrType: value 1 when BRA, Expr follows, and 0 when BRA Z, Expr follows
10. RdData1/RdData2: contents of the two read registers, which will represent the two operands for ALU
11. Clk: clock pulse for synchronizing the blocks
12. Z: zero flag
13. New_Z: the new value of Z at the output of the Z_Update block
14. Instr: contains the binary form of the instruction from ROM
15. RegDest: indicates whether to write to the source register (Ws) or the destination register (Wd)
16. MemWr: indicates whether the result of the operation will be stored in memory
17. RegWr: indicates whether the result of the operation will be stored in one of the registers (pointed to by WrReg)
18. INWO: I/O marker for data supply (read-only at the RAM memory level)
19. INW1: I/O marker for data supply (read-only at the RAM memory level)
20. OUTW0: I/O marker for observing the contents of memory externally (at the RAM level, write-only)
21. NegFlag: flag set by ctrl and received by ALU, allowing it to determine whether to perform an arithmetic operation or return the complement of the second operand (Ws), which will later be saved in Wd (NEG Ws, Wd operation)

Roles of the blocks:

1. ALU: responsible for performing arithmetic and logical operations (established by decoding ALUOP) on RdData1 and RdData2. ALU transmits the result through Y. Also, it needs to set the Z flag to logical 1 when the result of the operation performed by ALU is zero. Additionally, ALU will be used for the NEG Ws, Wd operation, generating the complement of Ws (operand 2 of ALU).
2. ProgCnt: stores the value from PC_Update on the rising edge of the clock pulse
3. PC_Update: sets a new value for ProgCnt, either by incrementing by 2 or by adding the offset multiplied by 2 (left-shifted by one position) for branch instructions
4. CTRL: given an instruction, it determines the values of all control signals to be used by the other blocks
5. ROM32x24: represents the ROM memory and contains the program instructions; it is organized as 32 vectors, each consisting of 24 bits
6. MUX2V4: selects the destination register address based on the instruction specifications (can be Ws or Wd). Wd for arithmetic and NEG instructions, and Ws for MOV F, WND instructions.
7. DataMem: a RAM memory consisting of 16 locations, each having 16 bits, two read-only locations (INW0 and INW1), and one write-only location (OUTW0)
8. File_Regs: contains all registers and is responsible for storing information at their level, as well as reading from them
9. MUX2V16: determines whether the data to be written to the register comes from the memory block or from ALU (depending on the instruction)
10. Z_Update: handles updating the value of Z if the ZEn signal is active and only on the rising edge of the clock pulse