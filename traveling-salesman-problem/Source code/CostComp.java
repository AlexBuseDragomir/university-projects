// Buse-Dragomir Alexandru
// essentially just a java comparator that will be used in order
// to sort a priority queue by specific fields (still a min heap)
// we will use this to create a priority queue for heuristic costs

import java.util.Comparator;

public class CostComp implements Comparator<Node>
{
    public int compare(Node node1, Node node2)
    {
        if(node1.getTotalCost() > node2.getTotalCost())
        {
            return 1;
        }

        else if(node1.getTotalCost() < node2.getTotalCost())
        {
            return -1;
        }

        return 0;
    }
}
