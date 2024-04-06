// Buse-Dragomir Alexandru
// this java comparator will be used in the minimum spanning three algorithm
// we will sort by the cost feature of a node (the distance to the closest node already in the MSP)

import java.util.Comparator;

public class MSTComp implements Comparator<NodeMST>
{
    public int compare(NodeMST node1, NodeMST node2)
    {
        if(node1.getCost() > node2.getCost())
        {
            return 1;
        }

        else if(node1.getCost() < node2.getCost())
        {
            return -1;
        }

        return 0;
    }
}
