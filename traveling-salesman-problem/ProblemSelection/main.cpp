// Buse-Dragomir Alexandru
// program used in order to determine the problem for the assignment

#include <iostream>

using namespace std;

int main()
{
    std::string myName;

	int sumOfASCII = 0;

    cout << "Insert your name here: ";

	/// I inserted the string "Buse-DragomirAlexandru"

	cin >> myName;

    for(int index = 0; index < myName.size(); index++)
    {
        sumOfASCII = sumOfASCII + (int)(myName[index]);
    }

    cout << "Your problem: ";
    cout << sumOfASCII % 5 + 1 << std::endl;

    return 0;
}
