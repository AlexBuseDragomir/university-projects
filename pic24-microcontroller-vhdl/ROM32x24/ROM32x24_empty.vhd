----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date:    19:00:35 03/31/2018 
-- Design Name: 
-- Module Name:    ROM32x24 - Behavioral 
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

entity ROM32x24 is
    Port ( 
			  Addr : in  STD_LOGIC_VECTOR (4 downto 0);
           Data : out  STD_LOGIC_VECTOR (23 downto 0)
			  );
end ROM32x24;

architecture ROM32x24_arch of ROM32x24 is

    type tROM is array(0 to 31) of std_logic_vector(23 downto 0);
    
	 constant ROM : tROM :=      (
												x"000001",  -- 0
												x"000002",  -- 1
 												x"000004",  -- 2
												x"000008",  -- 3
												x"000010",  -- 4                                
												x"000020",  -- 5
												x"000040",  -- 6
												x"000080",  -- 7
												x"000100",  -- 8
												x"000200",  -- 9
												x"000400",  -- 10
												x"000800",  -- 11
												x"001000",  -- 12
												x"002000",  -- 13
												x"004000",  -- 14
												x"008000",  -- 15
												x"010000",  -- 16
												x"020000",  -- 17
												x"040000",  -- 18
												x"080000",  -- 19
												x"100000",  -- 20
												x"200000",  -- 21
												x"400000",  -- 22
												x"800000",  -- 23
												x"000001",  -- 24
												x"000002",  -- 25
												x"000004",  -- 26
												x"000008",  -- 27
												x"000010",  -- 28
												x"000020",  -- 29
												x"000040",  -- 30
												x"000080"   -- 31
											);  
begin

	Data <= ROM(conv_integer(Addr));
end ROM32x24_arch;




