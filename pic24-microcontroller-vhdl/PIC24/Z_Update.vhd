----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date:    23:26:21 02/04/2019 
-- Design Name: 
-- Module Name:    Z_Update - Behavioral 
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

entity Z_Update is
    Port ( 
				ZData : in  STD_LOGIC;
				Clk : in  STD_LOGIC;
				ZEn : in  STD_LOGIC;
				New_Z : out  STD_LOGIC
			);
end Z_Update;

architecture Behavioral of Z_Update is

begin

New_Z <= ZData when rising_edge(Clk) and ZEn='1';

end Behavioral;

