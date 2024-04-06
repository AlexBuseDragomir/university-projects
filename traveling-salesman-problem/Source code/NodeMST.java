// Buse-Dragomir Alexandru
// a node structure used for the minimum spanning tree algorithm
// this is a simple class with a node "index" and a "cost" which represents
// the distance/cost from the closest node in the MSP to the current node

public class NodeMST
{
    private int index;
    private int cost;

    public NodeMST(int index, int cost)
    {
        this.index = index;
        this.cost = cost;
    }

    public int getIndex()
    {
        return this.index;
    }

    public int getCost()
    {
        return this.cost;
    }

    public void setIndex(int index)
    {
        this.index = index;
    }

    public void setCost(int cost)
    {
        this.cost = cost;
    }
}
