# Utility for operations with large numbers

A simple project in C, one of the first non-trivial programming projects I have had.
There is no real life utility in this utility, but developing it enabled me to get a better grasp of some C language conepts. 

Concept: Large numbers are numbers that are significantly larger than
those ordinarily used in everyday life, for instance in simple
counting or in monetary transactions. The term typically refers
to large positive integers, or more generally, large positive real
numbers, but it may also be used in other contexts.
Very large numbers often occur in fields such as
mathematics, cosmology, cryptography, and statistical
mechanics. Sometimes people refer to numbers as being
"astronomically large". However, it is easy to mathematically
define numbers that are much larger even than those used in
astronomy. (https://en.wikipedia.org/wiki/Large_numbers)

In order to define and construct functions for the main operations that arise
while handling large numbers, a new data type has been introduced : numar_mare
In fact, I used an array with a finite length (1000 memory locations) to store
these numbers ----> typedef int numar_mare[1000].

The "functions.c" file will contain all the functions used in order to work with
large numbers.
The way in which every function is written and should be analysed is very
simple. There are just a few steps :

1. The large numbers are saved in an array with a finite dimension. In the first position, array[0], we
will save the length of the number and in the positions 1, 2, 3... array[0] every digit of the number. The
only thing to keep in mind is that the number will be saved in a reversed order because, in this way, a
lot of functions will be easier to implement.
2. Every operation, excluding the square root, is implemented in a natural way. As an example, let's
say you want to add two large numbers. My code will do that by simulating the exact thing that each
one of us does. Putting them one under another and making the sum of their digits, one by one, from
the end towards the most significant one. There is also a transport variable that carries a value equal to
the last sum of two digits / 10 and adds it to the current sum. In the case of the square root, we use a
binary search algorithm and replace the trivial operations with functions that do the same thing, but on
large numbers.
3. We work with positive values so, for example, if we want to compute the difference of two
numbers, we must consider the first number greater than or equal to the second (omitting this aspect
will be signaled by an ERROR message).
We are talking about natural numbers that have from 1 to 999 digits (we can change their length by
making the array that stands behind the data type greater, but I considered that this dimension was
enough for my functions).
The "main.c" file will use all of the above functions and incorporate them into
a basic application that will provide information about large numbers and a list
of possible commands like adding two numbers, extracting the square root,
calculating a difference or a product etc.
So, by running the main program, the user will have access to an easy to use
and friendly command panel and will be able to get the result of some basic
operations that involve large numbers and their manipulation. 

In order to test the program, I used a random number generator that takes as
an input a length and returns a random value having the corresponding number
of digits. 
Here I will insert 10 input sets and the results generated by using the main
functions (addition, subtraction, multiplication, dividing and extracting the
square root).
For the last operation, we will provide the square root of the first number (we
read two numbers in order to use the other functions). In fact, it computes the
floor of the square root of the given number because the values that we work
with are natural numbers.
The inputs will be all generated using the above number generator function. I
will start with small numbers and I will increase their length gradually in order
to demonstrate that by saving the numbers in this way, we can operate with any
finite number, being it one digit long or 100 digits long.
The algorithms being homogeneous, (the same rules of calculation being
applied on smaller numbers, as well as on very large ones) I will try to take
decent values because testing the correctness of multiplying two numbers that
have more than 100 digits each can take a very long time (100 x 100 = 10.000
intermediary products by hand calculation).