// Buse-Dragomir Alexandru
// utility class that implements Dijkstra's algorithm for single source shortens path

import java.util.ArrayList;

@SuppressWarnings("ALL")
public class Dijkstra
{
    // costDijkstra is the new matrix cost (we are interested only in vertices
    // which have not been visited already in the graph search algorithm
    private int costDijkstra[][];
    private int numberOfNodes;
    private Node currentNode;
    private int shortestPathCost;

    public Dijkstra(int costMatrix[][], ArrayList<Boolean> isVisited, int numberOfNodes, Node currentNode)
    {
        // we parse the matrix and update the costs in the new matrix costDijkstra
        for(int index1 = 1; index1 <= numberOfNodes; index1 ++)
        {
            // if the node is not visited, we keep the values of all its unvisited neighbours the same
            // if the node is not visited, but some neighbours are visited, they will get a very big value
            if(isVisited.get(index1) == false)
            {
                for(int index2 = 1; index2 <= numberOfNodes; index2 ++)
                {
                    if(isVisited.get(index2) == true)
                    {
                        this.costDijkstra[index1][index2] = Integer.MAX_VALUE;
                    }
                }
            }

            // if the node was visited, we update the cost towards all its neighbours to a big value
            else if(isVisited.get(index1) == true)
            {
                for(int index2 = 1; index2 <= numberOfNodes; index2 ++)
                {
                    this.costDijkstra[index1][index2] = Integer.MAX_VALUE;
                }
            }
        }

        // we add the first node of the graph and all its neighbours to the costDijkstra[][]
        // even if it is visited, because we need to get to it again in the end
        for(int index = 1; index <= numberOfNodes; index ++)
        {
            costDijkstra[1][index] = costMatrix[1][index];
        }

        this.numberOfNodes = numberOfNodes;
        this.currentNode = currentNode;
        this.shortestPathCost = 0;
    }

    public void solveShortestPath()
    {
        // this is the array that will hold the distance from the source vertex to each other node
        // distanceArray[nodeIndex] = distance from source vertex to node
        int distanceArray[] = new int[this.numberOfNodes + 1];

        // boolean array that will tell us which nodes are included in the shortest path
        // it is also true for nodes which have already calculated distances
        boolean visitedArray[] = new boolean[this.numberOfNodes + 1];

        // we initialize all positions in the distanceArray with very large values
        // we initialise the visitedArray with false values because no computation has been done
        for (int index = 1; index <= this.numberOfNodes; index++)
        {
            distanceArray[index] = Integer.MAX_VALUE;
            visitedArray[index] = false;
        }

        // we set the distance value for the currentNode to 0 because distance to itself is 0
        distanceArray[this.currentNode.getNodeIndex()] = 0;

        // we go trough each vertice and get the shortest path from the source node to them
        // we need only the distance from the current node to the initial one, but we can easily
        // extract that from the distanceArray
        for (int mainIndex = 1; mainIndex <= this.numberOfNodes; mainIndex++)
        {
            // we take the minimum distance vertex from the list of vertices
            // it must be from those who have not been visited or added
            int minimumValue = Integer.MAX_VALUE;
            int minimumIndex = Integer.MIN_VALUE;

            // we iterate over the list of vertices and pick the lowest valued unvisited one
            for (int nodeIndex = 1; nodeIndex <= this.numberOfNodes; nodeIndex++)
            {
                if (visitedArray[nodeIndex] == false && distanceArray[nodeIndex] <= minimumValue)
                {
                    minimumValue = distanceArray[nodeIndex];
                    minimumIndex = nodeIndex;
                }
            }

            int positionOfNode = minimumIndex;

            // // we now mark the picked vertex as visited in the visitedArray
            visitedArray[positionOfNode] = true;

            // for all vertices which have not been visited yet and are adjacent
            // to the minimum current vertex, we update the ditance value
            for (int neighbourIndex = 1; neighbourIndex <= this.numberOfNodes; neighbourIndex++)
            {
                // we update the distance from the source node to all unvisited neighbours
                // but the conditions are : there must be an edge between the current minimum node
                // and the node we want to update, the weight of the path from source to neighbour
                // that passes trough the current minimum node is better than the current value
                // of the distanceArray[neighbourIndex]

                if (visitedArray[neighbourIndex] == false && this.costDijkstra[positionOfNode][neighbourIndex] != 0 &&
                        distanceArray[positionOfNode] != Integer.MAX_VALUE && distanceArray[positionOfNode]
                        + this.costDijkstra[positionOfNode][neighbourIndex] < distanceArray[neighbourIndex])
                {
                    distanceArray[neighbourIndex] = distanceArray[positionOfNode] +
                            this.costDijkstra[positionOfNode][neighbourIndex];
                }
            }
        }

        // we want to know the shortest path cost from the current node to the first node in the graph
        this.shortestPathCost = distanceArray[1];
    }

    public int getShortestPathCost()
    {
        return this.shortestPathCost;
    }

    public int getNumberOfNodes()
    {
        return this.numberOfNodes;
    }

    public int[][] getCostDijkstra()
    {
        return this.costDijkstra;
    }

    public void setShortestPathCost(int shortestPathCost)
    {
        this.shortestPathCost = shortestPathCost;
    }

    public void setNumberOfNodes(int numberOfNodes)
    {
        this.numberOfNodes = numberOfNodes;
    }

    public void setCostDijkstra(int costDijkstra[][])
    {
        this.costDijkstra = costDijkstra;
    }
}
