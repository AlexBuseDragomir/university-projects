/// Buse-Dragomir Alexandru

#include <iostream>
#include <string>
#include "header.h"

using namespace std;

int main()
{
    int numberOfNodes;
    int maxCostPerEdge;
    char character;

    cout << "Please insert the number of nodes : ";
    cin >> numberOfNodes;
    cout << "Now insert the maximum cost for an edge : ";
    cin >> maxCostPerEdge;
    cout << '\n';

    generateNodes(numberOfNodes, maxCostPerEdge);

    cout << "The input was generated. Press any key to exit..";
    cout << '\n';
    cin.get(character);

    return 0;
}
