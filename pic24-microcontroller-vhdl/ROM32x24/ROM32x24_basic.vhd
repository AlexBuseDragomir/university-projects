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
												x"808101",  -- MOV 0x1020, W1  --W1 = AAAB
												x"808112",  -- MOV 0x1022, W2  --W2 = 5555
 												x"408182",  -- ADD W1, W2, W3  --W3 = 0000
												x"508202",  -- SUB W1, W2, W4  --W4 = 5556
												x"608282",  -- AND W1, W2, W5  --W5 = 0001                             
												x"708302",  -- IOR W1, W2, W6  --W6 = FFFF
												x"888121",  -- MOV W1, 0x1024  --OUTW0 = AAAB
												x"888122",  -- MOV W2, 0x1024  --OUTW0 = 5555
												x"888123",  -- MOV W3, 0x1024  --OUTW0 = 0000
												x"888124",  -- MOV W4, 0x1024  --OUTW0 = 5556
												x"888125",  -- MOV W5, 0x1024  --OUTW0 = 0001
												x"888126",  -- MOV W6, 0x1024  --OUTW0 = FFFF
												x"37FFF3",  -- BRA 0x2B8       --repeat
												              
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




