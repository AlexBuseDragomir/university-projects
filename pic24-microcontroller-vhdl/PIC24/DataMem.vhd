----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date:    15:26:38 02/04/2019 
-- Design Name: 
-- Module Name:    DataMem - Behavioral 
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

entity DataMem is
   port
	(
      Clk      : in  std_logic;
      INW0     : in  std_logic_vector(15 downto 0);
      INW1     : in  std_logic_vector(15 downto 0);      
      OUTW0    : out std_logic_vector(15 downto 0);
      
      Wr       : in  std_logic;
      Addr     : in  std_logic_vector(4 downto 0);
      DataIn   : in  std_logic_vector(15 downto 0);        
      DataOut  : out std_logic_vector(15 downto 0)  
   );
end DataMem;

architecture Behavioral of DataMem is
   type RAM16x16  is array (0 to 15) of std_logic_vector(15 downto 0); 
   signal RAM     : RAM16x16;
   signal MemData : std_logic_vector(15 downto 0);
begin
   process(Clk)
   begin
      if rising_edge(Clk) then
         if Wr='1' and Addr(3 downto 0) /= "10010" then
            RAM(conv_integer(Addr(3 downto 0))) <= DataIn; 
         end if;
      end if;
   end process;
   
   MemData  <= RAM(conv_integer(Addr(3 downto 0)));
   
   DataOut  <= INW0 when Addr= "10000" else
               INW1 when Addr= "10001" else
               MemData;
               
   OUTW0    <= DataIn when rising_edge(Clk) and Addr="10010" and Wr='1';
                          
               
end Behavioral;

