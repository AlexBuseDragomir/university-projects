----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date:    14:12:05 02/04/2019 
-- Design Name: 
-- Module Name:    ALU - Behavioral 
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

entity ALU is
    Port ( 
				RdData1 : in  STD_LOGIC_VECTOR (15 downto 0);
				RdData2 : in  STD_LOGIC_VECTOR (15 downto 0);
				ALUOP : in  STD_LOGIC_VECTOR (1 downto 0);
				NegFlag: in STD_LOGIC;
				Y : out  STD_LOGIC_VECTOR (15 downto 0);
				Z : out  STD_LOGIC
			 );
end ALU;

architecture Behavioral of ALU is
	signal OP1		: std_logic_vector(15 downto 0);
	signal OP2		: std_logic_vector(15 downto 0);
	signal sY		: std_logic_vector(15 downto 0);
begin

		OP1 <= RdData1;
		OP2 <= RdData2;
				
		sY <= OP1 + OP2 when ALUOP="00" and NegFlag = '0' else 
				OP1 - OP2 when ALUOP="01" and NegFlag = '0' else 
				OP1 and OP2 when ALUOP="10" and NegFlag = '0'  else 
				OP1 or OP2 when ALUOP="11" and NegFlag = '0' else
				not(OP2) + 1 when NegFlag = '1' else
				x"0000";
		
		Y <= sY;
		Z <= '1' when sY = x"0000" else '0';

end Behavioral;

