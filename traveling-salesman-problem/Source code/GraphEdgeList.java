// Buse-Dragomir Alexandru
// utility class for creating a graph which represents the TSP map
// the graph is represented by using a list of lists (adjacency list)
// the graph is constructed based on a list of edges and their costs
// nodes are saved by their individual index to simplify the algorithms
// adjacencyList[i] -> list of neighbours (given by indices) for node with index i
// operations for inserting an edge and removing a certain edge

import java.util.ArrayList;

@SuppressWarnings("ALL")
public class GraphEdgeList
{
    private ArrayList<Edge> edgeList;
    private ArrayList<ArrayList<Integer>> adjacencyList;

    public GraphEdgeList(ArrayList<Edge> edgeList)
    {
        this.edgeList = edgeList;

        // initialize the adjacencyList by going trough the edgeList
        for(int index = 0; index < this.edgeList.size(); index ++)
        {
            int firstNodeIndex = edgeList.get(index).getFirstNode().getNodeIndex();
            int secondNodeIndex = edgeList.get(index).getSecondNode().getNodeIndex();

            this.adjacencyList.get(firstNodeIndex).add(secondNodeIndex);
            this.adjacencyList.get(secondNodeIndex).add(firstNodeIndex);
        }
    }

    public ArrayList<ArrayList<Integer>> getAdjacencyList()
    {
        return this.adjacencyList;
    }

    public int getNumberOfNodes()
    {
        return this.adjacencyList.get(1).size() + 1;
    }

    public void setAdjacencyList(ArrayList<ArrayList<Integer>> adjacencyList)
    {
        this.adjacencyList = adjacencyList;
    }

    public void addEdge(Edge toAdd)
    {
        Node firstNode = toAdd.getFirstNode();
        Node secondNode = toAdd.getSecondNode();

        int firstNodeIndex = firstNode.getNodeIndex();
        int secondNodeIndex = secondNode.getNodeIndex();

        this.adjacencyList.get(firstNodeIndex).add(secondNodeIndex);
        this.adjacencyList.get(secondNodeIndex).add(firstNodeIndex);
    }

    public void removeEdge(Edge toRemove)
    {
        Node firstNode = toRemove.getFirstNode();
        Node secondNode = toRemove.getSecondNode();

        int firstNodeIndex = firstNode.getNodeIndex();
        int secondNodeIndex = secondNode.getNodeIndex();

        this.adjacencyList.get(firstNodeIndex).remove(Integer.valueOf(secondNodeIndex));
        this.adjacencyList.get(secondNodeIndex).remove(Integer.valueOf(firstNodeIndex));
    }
}
