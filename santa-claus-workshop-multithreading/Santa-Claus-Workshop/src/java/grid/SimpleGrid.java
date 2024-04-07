package cds.java.project.grid;

import java.util.Arrays;

/**
 * Implementation of a SimpleGrid template structure under the form of a class;
 * This was the basic form for the grid that I intended to use, but I decided
 * to stick with a simpler implementation (left this here as it is a way better way
 * of working with a grid and, I might use it in a future rework);
 * it wraps a template matrix that must play the role of the place in which
 * elves reside and do their work by moving and creating gifts
 *
 * @author  Buse-Dragomir Alexandru
 * @since   2018-12-31
 */
@SuppressWarnings({"unchecked", "WeakerAccess"})
public class SimpleGrid<T> {

    /**
     * Template underlying matrix that represents the actual grid
     */
    private T[][] gridMatrix;
    /**
     * The length of the grid matrix
     */
    private int length;
    /**
     * The height of the grid matrix
     */
    private int height;

    /**
     * Constructor gets as a parameter the order of the matrix; this is the
     * one that will be used in the problem, as all matrices are square ones with
     * of a given order; it sets the length and height to be both equal to the
     * order of the matrix and a new background matrix structure is created
     */
    public SimpleGrid(int orderOfMatrix) {
        this.length = orderOfMatrix;
        this.height = orderOfMatrix;
        this.gridMatrix = (T[][])(new Object[orderOfMatrix][orderOfMatrix]);
    }

    /**
     * Constructor gets as a parameter the length and height dimensions of the matrix;
     * it sets the length and height accordingly and creates the matrix structure
     */
    public SimpleGrid(int length, int height) {
        this.length = length;
        this.height = height;
        this.gridMatrix = (T[][])(new Object[length][height]);
    }

    /**
     * Getter method for the underlying matrix structure
     */
    public T[][] getGridMatrix() {
        return this.gridMatrix;
    }

    /**
     * Setter method for the underlying matrix structure
     */
    public void setGridMatrix(T[][] gridMatrix) {
        this.gridMatrix = gridMatrix;
    }

    /**
     * Getter method for the matrix length
     */
    public int getLength() {
        return this.length;
    }

    /**
     * Setter method for the matrix length
     */
    public void setLength(int length) {
        this.length = length;
    }

    /**
     * Getter method for the matrix height
     */
    public int getHeight() {
        return this.height;
    }

    /**
     * Setter method for the matrix height
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * Private method that fills the matrix with a default value given as a parameter;
     * it makes use of an enhanced for loop and a method from the Arrays
     * class that resides in the java.util package
     */
    private void fillMatrixWithValue(T defaultValue) {
        for (T[] row: this.gridMatrix) {
            Arrays.fill(row, defaultValue);
        }
    }

    /**
     * toString() method from the Object class has been overridden in order for it
     * to return the length and height of the generic grid
     */
    @Override
    public String toString() {
        return "simple generic grid with a length of " + this.getLength() +
                " and a height of " + this.getHeight();
    }
}
