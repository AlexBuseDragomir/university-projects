# Traveling Salesman Problem using RBFS and A*

A college project aimed at solving the Traveling Salesman Problem (TSP) using C++ and Java. Please note that this project is currently **not complete**.

## Overview

This project tackles the TSP using two primary search algorithms:
* Recursive Best First Search (RBFS)
* A* Search

It incorporates C++ for utility programs and Java for the core problem-solving logic.

## C++ Utility Programs

Two C++ source files (written without OOP for simplicity) were created for utility purposes:

1.  **Homework Number Determination:** This program uses a hash function (provided by the professor) to determine the homework number.
2.  **Input Generation:** This program generates input data for the TSP problem.

## Input Generation Details

* The TSP graph is considered **complete**.
* The primary variables are the **distances (costs) between towns** (graph edges).
* **User Input:**
    * Number of towns (`n`)
    * Maximum accepted cost for an edge (`maxCost`)
* **Output:** The program generates `n * (n - 1) / 2` edges with costs randomly assigned within the interval `[1, maxCost]`. The data in the provided input files were created using this program.

## Java Implementation

The main problem-solving logic is written in Java and consists of **14 classes**.

### Search Algorithms Implemented:

* **RBFS (Recursive Best First Search):**
    * Implementation using Dijkstra's shortest path heuristic.
    * Implementation using Prim's Minimum Spanning Tree (MST) cost heuristic.
* **A* Search:**
    * Implementation using Dijkstra's shortest path heuristic.
    * Implementation using Prim's MST cost heuristic.

### Heuristics Used:

1.  **Dijkstra's Shortest Path:** Used as a heuristic within both RBFS and A*.
2.  **Prim's Minimum Spanning Tree Cost:** Used as a heuristic within both RBFS and A*.

### Auxiliary Classes:

Separate classes were created for:
* Dijkstra's algorithm implementation.
* Prim's algorithm implementation.
* Auxiliary data structures:
    * `Node`
    * `Edge`
    * `Graph`
    * Comparators for priority queues
    * Pairs of Integers, etc.

## Current Status

* The project is **not finished**.
* A `MainApp` class is still required.
* Thorough debugging is needed to identify and fix errors.
* However, all the core **Artificial Intelligence related code** (RBFS, A*, heuristics) is present, and the central idea is considered applicable.

## Additional Files

Included in the repository:
* **Documentation:** Created using ShareLaTeX.
* **Input Test Files:** 10 sample input files located in the `input tests` folder.
