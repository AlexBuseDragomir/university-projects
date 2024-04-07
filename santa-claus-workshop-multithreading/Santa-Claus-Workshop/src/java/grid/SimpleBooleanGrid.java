package cds.java.project.grid;

import cds.java.project.position.Position2D;

import java.util.Arrays;

/**
 * Implementation of a Simple boolean grid structure under the form of a class;
 * This is a simple object structure that is needed in the micro
 * world of the program in order to simulate a North Pole Workshop;
 * it wraps a boolean matrix that must play the role of the place in which
 * elves reside and do their work by moving and creating gifts
 *
 * @author  Buse-Dragomir Alexandru
 * @since   2018-12-31
 */
public class SimpleBooleanGrid {

    /**
     * Boolean matrix that has a value of true in the position [x][y] if
     * there is currently an elf in the position (x, y); false otherwise
     */
    private boolean[][] gridMatrix;
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
     * order of the matrix and a new background matrix structure is created and
     * filled with false (in the case of the problem, no elves in in at the moment)
     */
    public SimpleBooleanGrid(int orderOfMatrix) {
        this.length = orderOfMatrix;
        this.height = orderOfMatrix;
        this.gridMatrix = new boolean[orderOfMatrix][orderOfMatrix];
        this.fillMatrixWithDefaultValue();
    }

    /**
     * Constructor gets as a parameter the length and height dimensions of the matrix;
     * it sets the length and height accordingly and creates the matrix structure
     * it will be filled with false
     */
    public SimpleBooleanGrid(int length, int height) {
        this.length = length;
        this.height = height;
        this.gridMatrix = new boolean[length][height];
        this.fillMatrixWithDefaultValue();
    }

    /**
     * Method that sets a position in the matrix to true;
     * the position is given trough a parameter of type Position2D
     */
    public void setGridPositionToTrue(Position2D positionToSet) {
        int coordinateX = positionToSet.getPositionX();
        int coordinateY = positionToSet.getPositionY();
        this.gridMatrix[coordinateX][coordinateY] = true;
    }

    /**
     * Method that sets a position in the matrix to false;
     * the position is given trough a parameter of type Position2D
     */
    public void setGridPositionToFalse(Position2D positionToSet) {
        int coordinateX = positionToSet.getPositionX();
        int coordinateY = positionToSet.getPositionY();
        this.gridMatrix[coordinateX][coordinateY] = false;
    }

    /**
     * Getter method for the underlying matrix structure
     */
    public boolean[][] getGridMatrix() {
        return this.gridMatrix;
    }

    /**
     * Setter method for the underlying matrix structure
     */
    public void setGridMatrix(boolean[][] gridMatrix) {
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
     * Private method that fills the matrix with a value of false;
     * it makes use of an enhanced for loop and a method from the Arrays
     * class that resides in the java.util package
     */
    private void fillMatrixWithDefaultValue() {
        for (boolean[] row: this.gridMatrix) {
            Arrays.fill(row, false);
        }
    }

    /**
     * toString() method from the Object class has been overridden in order for it
     * to return the length and height of the boolean grid
     */
    @Override
    public String toString() {
        return "simple boolean grid with a length of " + this.getLength() +
                " and a height of " + this.getHeight();
    }
}