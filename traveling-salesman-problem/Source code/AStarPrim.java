// Buse-Dragomir Alexandru
// a priority queue in which we have the nodes sorted in ascending order by their cost
// a list that informs us if we have or have not already visited a certain node
// a list of cities that have been visited in the visiting order to recreate the path
// a total traveling cost variable to store the total cost of the chosen way
// we also have a cost matrix which tells us what is the cost from vertex i to vertex j (matrix[i][j] -> cost)
// and a starting node which is set in the constructor of the class
// this class is practically very similar with the Dijkstra class, but instead of sorting the priority queue
// after the edge costs, we use the total cost (i.e. the cost of the edges + heuristic cost)

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

@SuppressWarnings("ALL")
// implementation is using Prim's algorithm as a heuristic
public class AStarPrim
{
    private PriorityQueue<Node> pQueue;
    private Comparator<Node> costComparator;
    private ArrayList<Boolean> isVisited;
    private ArrayList<Integer> visitedNodes;
    private int travelingCost;
    private int costMatrix[][];
    private Node startNode;
    private int numberOfNodes;

    public AStarPrim(GraphCostMatrix myGraph, Node startNode)
    {
        this.costMatrix = myGraph.getCostMatrix();
        this.numberOfNodes = myGraph.getNumberOfNodes();
        this.costComparator = new CostComp();
        this.pQueue = new PriorityQueue<Node>(numberOfNodes, costComparator);

        // we will save information in the ArrayLists from the position 1
        // in order to logically retrieve information from them
        // ex: isVisited.get(1) will say if city with number 1 was visited
        // because the cities have indices from 1 to "numberOfNodes"
        this.isVisited.set(0, false);

        for(int index = 1; index <= numberOfNodes; index ++)
        {
            this.isVisited.set(index, false);
        }

        this.visitedNodes = new ArrayList<Integer>();
        this.travelingCost = 0;
        this.startNode = startNode;
    }

    public ArrayList<Integer> getVisitedNodes()
    {
        return this.visitedNodes;
    }

    public ArrayList<Boolean> getIsVisited()
    {
        return this.isVisited;
    }

    public void solveProblem()
    {
        // we are in the initial town at the start, so we mark it as visited
        int currentNodeIndex = startNode.getNodeIndex();
        this.isVisited.set(currentNodeIndex, true);

        // and we add it to the visitedNodes list
        this.visitedNodes.add(currentNodeIndex);

        Node currentNode = startNode;
        // we save in a variable the total number of visited nodes, initially 1
        int visitedNodesNumber = 1;

        // we visit nodes until all nodes (cities) have been visited
        while(visitedNodesNumber <= this.numberOfNodes)
        {
            // we take each node, except the current one, and check if it is visited
            // we don't have to check if there is an edge from one to another because
            // the graph is complete
            for(int neighbourIndex = 1; neighbourIndex <= numberOfNodes; neighbourIndex++)
            {
                // we take all neighbours (we do not have self loops)
                if (neighbourIndex != currentNodeIndex)
                {
                    // we check if the neighbour is visited or not
                    if (!this.isVisited.get(neighbourIndex))
                    {
                        // for each neighbour which is not visited, we calculate the totalCost with
                        // one of the two heuristics, using a Minimum Spanning Tree (Prim Algorithm)
                        Prim minimumSpanningTree = new Prim(this.costMatrix, this.isVisited, this.numberOfNodes);
                        // we get the total cost on the edges of the spanning tree and save it
                        // this is the heuristic value for the current node
                        int heuristicCost = minimumSpanningTree.getMinSpanTreeCost();
                        // we calculate the total cost by adding the heuristic cost
                        // and the cost on the edge to this node
                        int finalCost = heuristicCost + this.costMatrix[currentNodeIndex][neighbourIndex];
                        // now we create the node and we save it in the priorityQueue
                        Node newNode = new Node(neighbourIndex, heuristicCost, finalCost);
                        // and we put it in the priority queue
                        // in other words, like in the case of Dijkstra algorithm, we add in the priority queue
                        // all the neighbours of the current node which are not visited
                        this.pQueue.add(newNode);
                    }
                }
            }

            // now we update the currentNode and mark it as visited
            // poll is a method which extracts the node and deletes it from the queue
            currentNode = this.pQueue.poll();
            currentNodeIndex = currentNode.getNodeIndex();
            // marking node as visited in the visited list
            this.isVisited.set(currentNodeIndex, true);
            // and then we add it to the visitedNodes list
            this.visitedNodes.add(currentNodeIndex);
            // increase visitedNodesNumber
            visitedNodesNumber = visitedNodesNumber + 1;
        }
    }

    // we get the total cost of the tour for the travel trough all nodes
    public int getTravelingCost()
    {
        // we iterate trough the visitedNodes list and sum the costs of the path
        for (int index = 1; index < this.numberOfNodes; index++)
        {
            // we get from costMatrix the cost for adjacent edges and add them
            this.travelingCost = this.travelingCost + costMatrix[index][index + 1];
        }

        return this.travelingCost;
    }
}
