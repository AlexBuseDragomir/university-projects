// Buse-Dragomir Alexandru
// utility class for the simulation of an edge between two cities
// it saves the Nodes which represent the starting and ending city
// and the cost (distance on the road) between the two locations
// cost represents the g() function from the AIMA book

public class Edge
{
    private Node firstNode;
    private Node secondNode;
    private int edgeCost;

    public Edge(Node firstNode, Node secondNode, int cost)
    {
        this.firstNode = firstNode;
        this.secondNode = secondNode;
        this.edgeCost = cost;
    }

    public Node getFirstNode()
    {
        return this.firstNode;
    }

    public Node getSecondNode()
    {
        return this.secondNode;
    }

    public int getEdgeCost()
    {
        return this.edgeCost;
    }

    public void setFirstNode(Node firstNode)
    {
        this.firstNode = firstNode;
    }

    public void setSecondNode(Node secondNode)
    {
        this.secondNode = secondNode;
    }

    public void setEdgeCost(int cost)
    {
        this.edgeCost = cost;
    }
}
