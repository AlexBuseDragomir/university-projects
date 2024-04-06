// Buse-Dragomir Alexandru
// an easier for this problem implementation of a graph
// consists in the number of vertices and a cost matrix
// because we know that in the TSP we have either a complete graph
// or we do not change the solution if we add edges with a very large cost
// between vertices that are not connected
// so in each cell of the matrix, we will have a non null value

import java.util.ArrayList;

public class GraphCostMatrix
{
    private int costMatrix[][];
    private int numberOfNodes;

    public GraphCostMatrix(int costMatrix[][], int numberOfNodes)
    {
        this.costMatrix = costMatrix;
        this.numberOfNodes = numberOfNodes;

    }

    public int[][] getCostMatrix()
    {
        return this.costMatrix;
    }

    public int getNumberOfNodes()
    {
        return this.numberOfNodes;
    }

    public void setCostMatrix(int costMatrix[][])
    {
        this.costMatrix = costMatrix;
    }

    public void setNumberOfNodes(int numberOfNodes)
    {
        this.numberOfNodes = numberOfNodes;
    }
}
