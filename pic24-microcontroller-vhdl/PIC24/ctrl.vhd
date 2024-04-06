----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date:    15:41:48 02/04/2019 
-- Design Name: 
-- Module Name:    ctrl - Behavioral 
-- Project Name: 
-- Target Devices: 
-- Tool versions: 
-- Description: 
--
-- Dependencies: 
--
-- Revision: 
-- Revision 0.01 - File Created
-- Additional Comments: 
--
----------------------------------------------------------------------------------
library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.STD_LOGIC_ARITH.ALL;
use IEEE.STD_LOGIC_UNSIGNED.ALL;

---- Uncomment the following library declaration if instantiating
---- any Xilinx primitives in this code.
--library UNISIM;
--use UNISIM.VComponents.all;

entity ctrl is
    Port ( 
				OP : in  STD_LOGIC_VECTOR (4 downto 0);
				BrType: in STD_LOGIC;
				Z: in STD_LOGIC;	
				ALUOP : out  STD_LOGIC_VECTOR (1 downto 0);
				MemWr : out  STD_LOGIC;
				Mem2Reg : out  STD_LOGIC;
				RegWr : out  STD_LOGIC;
				Branch : out  STD_LOGIC;
				ZEn: out STD_LOGIC;
				RegDest : out  STD_LOGIC;
				NegFlag : out STD_LOGIC
			 );
			  
end ctrl;

architecture Behavioral of ctrl is

begin
	MemWr <= '1' when OP = b"10001" else '0';
	Mem2Reg <= '1' when OP = b"10000" else '0';
	RegWr <= '0' when OP = b"10001" else 
				'0' when OP = b"00110" else
				'1';
				
	RegDest <= '1' when OP = b"10000"
					else '0';
	
	Branch <= '1' when (OP = b"00110" and BrType = '1') or 
				(OP = b"00110" and BrType = '0' and Z = '1') else 
				'0';	
				
	NegFlag <= '1' when OP = b"11101" 
					else '0';
	
	ZEn <= '1' when OP = "01000" else
			 '1' when OP = "01010" else
			 '1' when OP = "01100" else
			 '1' when OP = "01110" else
		 	 '1' when OP = "11101" else
			 '0';				
				  
	ALUOP <=	b"00" when OP = b"01000" else
				b"01" when OP = b"01010" else
				b"10" when OP = b"01100" else
				b"11" when OP = b"01110" else
				b"00";

end Behavioral;

