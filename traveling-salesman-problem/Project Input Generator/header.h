/// Buse-Dragomir Alexandru

#include <iostream>
#include <stdlib.h>
#include <time.h>
#include <string>
#include <fstream>

using namespace std;

ofstream file("test.out");

/// generates edges with values between 1 and maxCostPerEdge
/// they are saved in a costMatrix and then copied in an output file
/// in order not to have a stack overflow, I restricted the number of vertices to 100
void generateNodes(int numberOfNodes, int maxCostPerEdge)
{
    int costOfEdge = 0;
    int costMatrix[101][101] = {0};

    srand(time(NULL));

    for(int row = 1; row <= numberOfNodes; row ++)
    {
        for(int column = 1; column <= numberOfNodes; column ++)
        {
            if(row < column)
            {
                costOfEdge = rand() % maxCostPerEdge + 1;

                costMatrix[row][column] = costOfEdge;

                costMatrix[column][row] = costOfEdge;
            }
        }
    }

    for(int row = 1; row <= numberOfNodes; row ++)
    {
        for(int column = 1; column <= numberOfNodes; column ++)
        {
            file << costMatrix[row][column] << " ";
        }

        file << '\n';
    }

    file.close();
}
