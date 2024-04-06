// Buse-Dragomir Alexandru
// utility class that implements Prim's algorithm for minimum spanning trees
import java.util.ArrayList;

@SuppressWarnings("ALL")
public class Prim
{
    // costMST is the new matrix cost (we are interested only in vertices
    // which have not been visited already in the graph search algorithm
    private int costMST[][];
    private int numberOfNodes;
    private int minSpanTreeCost;

    public Prim(int costMatrix[][], ArrayList<Boolean> isVisited, int numberOfNodes)
    {
        // we parse the matrix and update the costs in the new matrix costMST
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
                        costMST[index1][index2] = Integer.MAX_VALUE;
                    }
                }
            }

            // if the node was visited, we update the cost towards all its neighbours to a big value
            else if(isVisited.get(index1) == true)
            {
                for(int index2 = 1; index2 <= numberOfNodes; index2 ++)
                {
                    costMST[index1][index2] = Integer.MAX_VALUE;
                }
            }
        }

        this.numberOfNodes = numberOfNodes;
        this.minSpanTreeCost = 0;
    }

    // implementation of MST Prim without priority queue
    // time complexity of O(n^2) instead of O(n*logn)
    // case for a min heap priority queue
    public void solveMinSpanTree(int costMST[][])
    {
        // array to store the minimum spanning tree
        int parentArray[] = new int[this.numberOfNodes + 1];

        // key values are used to take the edge with the minimum cost
        int keyArray[] = new int[this.numberOfNodes + 1];

        // array that marks with true the vertices included in the minimum spanning tree
        // and with false the vertices which have not been included in the MST
        boolean minSpanTreeArray[] = new boolean[this.numberOfNodes + 1];

        // initialize all keys with a big value
        for (int index = 1; index <= this.numberOfNodes; index++)
        {
            keyArray[index] = Integer.MAX_VALUE;
            // no node is at the moment in the minimum spanning tree
            minSpanTreeArray[index] = false;
        }

        // we include in the MST the first vertex of the graph
        // by setting a very low value, we ensure that this vertex will be picked first
        keyArray[1] = Integer.MIN_VALUE;
        // the first node has no parent
        parentArray[1] = Integer.MIN_VALUE;

        // we parse all the vertices of the minimum spanning tree
        for (int mainIndex = 1; mainIndex <= this.numberOfNodes; mainIndex++)
        {
            // pick the minimum key vertex from the set of vertices
            // it must not be visited (check in the minSpanTreeArray its value)
            int minimumValue = Integer.MAX_VALUE;
            int minimumIndex = Integer.MIN_VALUE;

            for (int nodeIndex = 1; nodeIndex <= this.numberOfNodes; nodeIndex++)
            {
                // if the vertex has not been picked already and its key is the smallest, we take it out
                if (minSpanTreeArray[nodeIndex] == false && keyArray[nodeIndex] < minimumValue)
                {
                    minimumValue = keyArray[nodeIndex];
                    minimumIndex = nodeIndex;
                }
            }

            int positionOfNode = minimumIndex;

            // we now mark the picked vertex as visited in the minimum spanning tree array
            minSpanTreeArray[positionOfNode] = true;

            // now we have to update key value and parent index of the adjacent nodes
            // but only those vertices which are not already included in the minimum spanning tree
            for (int neighbourIndex = 1; neighbourIndex <= this.numberOfNodes; neighbourIndex++)
            {
                // we check if costMST[mainIndex + 1][neighbourIndex + 1] is non zero
                // to see all the neighbours of the node (although the main graph is complete,
                // we do not care in the Prim algorithm about the vertices which have been visited
                // during the A* search
                // we check the value of minSpanTree[neighbourIndex] to get non visited neighbours
                if (costMST[positionOfNode][neighbourIndex] != 0 && minSpanTreeArray[neighbourIndex] == false
                        && costMST[positionOfNode][neighbourIndex] < keyArray[neighbourIndex])
                {
                    // we update the keyArray only ig the costMST[mainIndex][neighbourIndex]
                    // is smaller than the value in the keyArray for the neighbour
                    // (just like for the Dijkstra, excepting the fact that here we do not add the cost
                    // of the mainNode, but only the cost of the vertex that connectes them
                    parentArray[neighbourIndex] = positionOfNode;
                    keyArray[neighbourIndex] = costMST[positionOfNode][neighbourIndex];
                }
            }
        }

        // we now compute the total cost by going trough the parentArray nodes
        // we add to the minSpanTreeCost the cost of the edge going from parent[vertexIndex] to vertexIndex
        for(int vertexIndex = 1; vertexIndex <= this.numberOfNodes; vertexIndex ++)
        {
            this.minSpanTreeCost = this.minSpanTreeCost + costMST[parentArray[vertexIndex]][vertexIndex];
        }
    }

    public int getMinSpanTreeCost()
    {
        return this.minSpanTreeCost;
    }

    public int getNumberOfNodes()
    {
        return this.numberOfNodes;
    }

    public int[][] getCostMST()
    {
        return this.costMST;
    }

    public void setMinSpanTreeCost(int minSpanTreeCost)
    {
        this.minSpanTreeCost = minSpanTreeCost;
    }

    public void setNumberOfNodes(int numberOfNodes)
    {
        this.numberOfNodes = numberOfNodes;
    }

    public void setCostMST(int costMST[][])
    {
        this.costMST = costMST;
    }
}
