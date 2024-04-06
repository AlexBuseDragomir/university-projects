# Traveling-Salesman-Problem-RBFS-A*

College project, not complete, solving the Traveling Salesman Problem by using C++ and Java.

The project contains two sources written in C++, as it was easier for me to use it, without OOP, for the utility programs.  
The first C++ source was used in order to determine the number of the homework (using the hash function given by our professor).  
The second C++ source will be used in order to generate inputs for the problem (data in the input file has been created using this program).  
Taking into consideration that in the case of the Traveling Salesman Problem, the graph can be considered complete, we have as variables just the distances between towns (represented as the edges of the graph).
After the user inputs the number of towns from the keyboard (let's consider it n) and the maximum accepted cost for an edge (let's consider it maxCost), the program generates n*(n-1)/ 2 edges with costs in the interval [1, maxCost].  
The main program has been written in Java and it is composed of 14 classes which are necessary in order to model and then solve the problem.  
There are 2 classes that implement Recursive Best First Search (Dijkstra's shortest path heuristic and Prim's Minimum Spanning Tree cost heuristic) and 2 classes that implement A* (same two heuristics).
Likewise, I had to create separate classes for the Dijkstra's and Prim's algorithms, and other auxiliary structures like Nodes, Edges, Graphs, Comparators for priority queues, Pairs of Integers etc.    
The project is not finished; it is required a MainApp class and a thoughtful debug in order to find and solve all errors. Still, it contains all the Artificial Intelligence related code and I think that the central idea is an applicable one.    
In this repository, there are also the documentation for this project, created using ShareLaTeX and some possible inputs written in test files (the input tests folder contains 10 such files).
