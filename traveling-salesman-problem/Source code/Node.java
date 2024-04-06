// Buse-Dragomir Alexandru
// utility class for creating a Node with a heuristic cost and a name
// a name because each Node represents a city from the map
// heuristicCost represents the h() function from the AIMA book
// totalCost represents the f() = g() + h() function from AIMA book
// nodeIndex is a particular Integer identifier for each node

public class Node
{
    private int heuristicCost;
    private int nodeIndex;
    private int totalCost;

    public Node()
    {
        this.nodeIndex = -1;
        this.heuristicCost = Integer.MAX_VALUE;
        this.totalCost = Integer.MAX_VALUE;
    }

    public Node(int nodeIndex)
    {
        this.nodeIndex = nodeIndex;
        this.heuristicCost = Integer.MAX_VALUE;
        this.totalCost = Integer.MAX_VALUE;
    }

    public Node(int nodeIndex, int heuristicCost)
    {
        this.nodeIndex = nodeIndex;
        this.heuristicCost = heuristicCost;
        this.totalCost = this.heuristicCost;
    }

    public Node(int nodeIndex, int heuristicCost, int totalCost)
    {
        this.nodeIndex = nodeIndex;
        this.heuristicCost = heuristicCost;
        this.totalCost = totalCost;
    }

    public int getHeuristicCost()
    {
        return this.heuristicCost;
    }

    public int getNodeIndex()
    {
        return this.nodeIndex;
    }

    public int getTotalCost()
    {
        return this.totalCost;
    }

    public void setHeuristicCost(int heuristicCost)
    {
        this.heuristicCost = heuristicCost;
    }

    public void setNodeIndex(int nodeIndex)
    {
        this.nodeIndex = nodeIndex;
    }

    public void setTotalCost(int totalCost)
    {
        this.totalCost = totalCost;
    }
}
