--------------------------------------------------------------------------------
-- Copyright (c) 1995-2007 Xilinx, Inc.  All rights reserved.
--------------------------------------------------------------------------------
--   ____  ____ 
--  /   /\/   / 
-- /___/  \  /    Vendor: Xilinx 
-- \   \   \/     Version : 9.2.04i
--  \   \         Application : sch2vhdl
--  /   /         Filename : pic24sys.vhf
-- /___/   /\     Timestamp : 02/07/2019 19:12:22
-- \   \  /  \ 
--  \___\/\___\ 
--
--Command: "C:\Program Files\Xilinx\bin\nt\sch2vhdl.exe" -intstyle ise -family spartan3 -flat -suppress -w C:/Users/alexa/Desktop/PIC24/pic24sys.sch pic24sys.vhf
--Design Name: pic24sys
--Device: spartan3
--Purpose:
--    This vhdl netlist is translated from an ECS schematic. It can be 
--    synthesis and simulted, but it should not be modified. 
--

library ieee;
use ieee.std_logic_1164.ALL;
use ieee.numeric_std.ALL;
library UNISIM;
use UNISIM.Vcomponents.ALL;

entity pic24sys is
   port ( Clk : in    std_logic; 
          SWA : in    std_logic_vector (3 downto 0); 
          SWB : in    std_logic_vector (3 downto 0); 
          a   : out   std_logic; 
          AN  : out   std_logic_vector (3 downto 0); 
          b   : out   std_logic; 
          c   : out   std_logic; 
          d   : out   std_logic; 
          e   : out   std_logic; 
          f   : out   std_logic; 
          g   : out   std_logic);
end pic24sys;

architecture BEHAVIORAL of pic24sys is
   signal INW0  : std_logic_vector (15 downto 0);
   signal INW1  : std_logic_vector (15 downto 0);
   signal OUTW0 : std_logic_vector (15 downto 0);
   component Iface_s3
      port ( Clk   : in    std_logic; 
             SWA   : in    std_logic_vector (3 downto 0); 
             SWB   : in    std_logic_vector (3 downto 0); 
             OUTW0 : in    std_logic_vector (15 downto 0); 
             a     : out   std_logic; 
             b     : out   std_logic; 
             c     : out   std_logic; 
             d     : out   std_logic; 
             e     : out   std_logic; 
             f     : out   std_logic; 
             g     : out   std_logic; 
             AN    : out   std_logic_vector (3 downto 0); 
             INW0  : out   std_logic_vector (15 downto 0); 
             INW1  : out   std_logic_vector (15 downto 0));
   end component;
   
   component pic24sch
      port ( Clk   : in    std_logic; 
             INW0  : in    std_logic_vector (15 downto 0); 
             INW1  : in    std_logic_vector (15 downto 0); 
             OUTW0 : out   std_logic_vector (15 downto 0));
   end component;
   
begin
   U_iface : Iface_s3
      port map (Clk=>Clk,
                OUTW0(15 downto 0)=>OUTW0(15 downto 0),
                SWA(3 downto 0)=>SWA(3 downto 0),
                SWB(3 downto 0)=>SWB(3 downto 0),
                a=>a,
                AN(3 downto 0)=>AN(3 downto 0),
                b=>b,
                c=>c,
                d=>d,
                e=>e,
                f=>f,
                g=>g,
                INW0(15 downto 0)=>INW0(15 downto 0),
                INW1(15 downto 0)=>INW1(15 downto 0));
   
   U_pic24 : pic24sch
      port map (Clk=>Clk,
                INW0(15 downto 0)=>INW0(15 downto 0),
                INW1(15 downto 0)=>INW1(15 downto 0),
                OUTW0(15 downto 0)=>OUTW0(15 downto 0));
   
end BEHAVIORAL;


