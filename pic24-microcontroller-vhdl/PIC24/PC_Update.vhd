----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date:    16:00:08 03/31/2018 
-- Design Name: 
-- Module Name:    PC_Update - Behavioral 
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

entity PC_Update is
    Port ( 
				PC : in  STD_LOGIC_VECTOR (22	downto 0);
            New_PC : out  STD_LOGIC_VECTOR (22 downto 0);
			   Offset : in  STD_LOGIC_VECTOR (15 downto 0);
			   Branch : in  STD_LOGIC
			);
end PC_Update;

architecture Behavioral of PC_Update is

signal lcl_offset: std_logic_vector(22 downto 0); 

begin
	lcl_offset <= b"111111" & (Offset & '0') when Offset(15) = '1' else
					b"000000" & (Offset & '0');
	
	New_PC <= (PC + 2) + lcl_offset when Branch ='1' else
				 PC + 2;
				
	

end Behavioral;

