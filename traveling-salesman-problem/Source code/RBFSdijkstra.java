// Buse-Dragomir Alexandru
// implementation of recursive best first search
// is using Dijkstra's algorithm as a heuristic

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

@SuppressWarnings("ALL")
public class RBFSdijkstra
{
    private int visitedNodesNumber;
    private PriorityQueue<Node> pQueue;
    private Comparator<Node> costComparator;
    private ArrayList<Boolean> isVisited;
    private ArrayList<Integer> visitedNodes;
    private int travelingCost;
    private int costMatrix[][];
    private int numberOfNodes;

    public RBFSdijkstra(GraphCostMatrix myGraph, Node currentNode)
    {
        this.costMatrix = myGraph.getCostMatrix();
        this.numberOfNodes = myGraph.getNumberOfNodes();
        this.costComparator = new CostComp();
        this.pQueue = new PriorityQueue<Node>(numberOfNodes, costComparator);
        this.isVisited = new ArrayList<Boolean>();

        // we will save information in the ArrayLists from the position 1
        // in order to logically retrieve information from them
        // ex: isVisited.get(1) will say if city with number 1 was visited
        // because the cities have indices from 1 to "numberOfNodes"
        this.isVisited.set(0, false);

        // we initialise the isVisited list with false
        for(int index = 1; index <= numberOfNodes; index ++)
        {
            this.isVisited.set(index, false);
        }

        this.visitedNodes = new ArrayList<Integer>();
        // the traveling cost will be 0 at the start of the tour
        this.travelingCost = 0;
        this.visitedNodesNumber = Integer.MIN_VALUE;
    }

    // function returns true if we found a way to visit nodes at a new level
    // returning false means that we must go back
    // the final path can be recreated by seeing the content of the visitedNodes
    public boolean solveRBFS(Node currentNode, int fLimit)
    {
        // we save in a variable the total number of visited nodes, initially 1
        // we have this in an if because we want to initialise it only once, in
        // the first call of the recursive function
        if(visitedNodesNumber == Integer.MIN_VALUE)
        {
            visitedNodesNumber = 1;
        }

        // this is the point in which we know that the tour was found
        // and we must get out of the recursion
        // we went trough all the nodes of the graph
        if(visitedNodesNumber == this.numberOfNodes)
        {
            return true;
        }

        int currentNodeIndex = currentNode.getNodeIndex();
        // the current node is marked as visited because we are working with it
        this.isVisited.set(currentNodeIndex, true);

        // we go trough all neighbours of the current node
        for(int neighbourIndex = 1; neighbourIndex <= this.numberOfNodes; neighbourIndex++)
        {
            // except the node itself
            if(neighbourIndex != currentNodeIndex)
            {
                // we apply Dijkstra's algorithm to the graph (only the unvisited nodes)
                // see constructor of Dijkstra.java (there are kept only the edge costs for unvisited nodes)
                Dijkstra shortestPath = new Dijkstra(this.costMatrix, this.isVisited,
                        this.numberOfNodes, currentNode);
                // we retrieve the heuristic cost - the cost of all the edges of the shortest path
                int heuristicCost = shortestPath.getShortestPathCost();
                // final cost = edge cost + heuristic cost
                int finalCost = heuristicCost + this.costMatrix[currentNodeIndex][neighbourIndex];
                // we create a node object for the neighbour and we add it to the queue
                Node newNode = new Node(neighbourIndex, heuristicCost, finalCost);
                this.pQueue.add(newNode);
            }
        }

        // while we can go down into the recursion, we search for a path
        // it goes back if we find a bad heuristic and we unwind to get to the
        // second best node which is upper in the search graph
        // each return from the recursion gets up a level in the search
        // because each new recursion goes to the neighbours of a node, so "deeper"
        while(true)
        {
            // "peek()" is a priority queue method which gives us the top node
            // without removing it from the queue
            Node minimumNode = this.pQueue.peek();
            // we also save the index of this node
            int minimumIndex = minimumNode.getNodeIndex();

            // if the heuristic value of the main node is worst that the fLimit
            // then we must go back and choose the alternative node (second minimum)
            if(minimumNode.getHeuristicCost() > fLimit)
            {
                // we update the costs while going back to all parents of the node
                // which had a worse fLimit, until we reach the alternative
                currentNode.setHeuristicCost(minimumNode.getHeuristicCost());
                // we mark the current node as unvisited because it is no longer a good option
                this.isVisited.set(currentNodeIndex, false);
                this.visitedNodes.remove(Integer.valueOf(currentNodeIndex));
                // go back
                return false;
            }

            // in order not to modify the original pQueue, we save it into an auxiliary one
            // we remove the top value and peek the other one (we get the second smallest value)
            // so this is the alternative node (we return here depending on the fLimit value)
            PriorityQueue<Node> auxiliaryQueue = this.pQueue;
            auxiliaryQueue.poll();
            Node secondMinNode = auxiliaryQueue.peek();

            int secondMinIndex = secondMinNode.getNodeIndex();

            // we check if the neighbour with the minimum cost is good
            if(minimumNode.getHeuristicCost() <= fLimit)
            {
                // if so, we go deeper in the recursion (one level down)
                boolean isSolution = solveRBFS(currentNode, fLimit);

                // if the neighbour represents a good option, we add it to the visited list
                if(isSolution)
                {
                    this.visitedNodes.add(currentNodeIndex);
                    // we mark it as visited
                    this.isVisited.set(currentNodeIndex, true);
                    // we continue to search trough its neighbours
                    return true;
                }
            }

            // we can't go any further, it's a dead end, so we go back
            else
            {
                // on the way back, we update the heuristic value
                currentNode.setHeuristicCost(minimumNode.getHeuristicCost());
                // we mark the current node as unvisited because it is no longer a good option
                this.isVisited.set(currentNodeIndex, false);
                this.visitedNodes.remove(Integer.valueOf(currentNodeIndex));
                // go back
                return false;
            }
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
