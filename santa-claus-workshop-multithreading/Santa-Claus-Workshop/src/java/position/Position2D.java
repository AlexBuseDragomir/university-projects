package cds.java.project.position;

import cds.java.project.grid.SimpleBooleanGrid;

/**
 * Implementation of a Position2D pair under the form of a class;
 * This is a simple object structure that is needed in the micro
 * world of the program in order to simulate a North Pole Workshop;
 * it contains two coordinates, the a position on a X axis and a
 * position on a Y axis; it also has getters and setters for those
 * and a few utility methods for handling positions and generating
 * new ones with certain properties
 *
 * @author  Buse-Dragomir Alexandru
 * @since   2018-12-31
 */
public class Position2D {

    /**
     * Coordinate on the X axis
     */
    private int positionX;
    /**
     * Coordinate on the Y axis
     */
    private int positionY;

    /**
     * Constructor for the Position2D class that takes as parameters
     * the X and Y coordinates and sets the members accordingly
     */
    public Position2D(int positionX, int positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
    }

    /**
     * Method that generates a new Position2D object starting
     * from the current form of the current one;
     * it gets the position in north of the current one by adding
     * one to the Y coordinate
     * finally, it returns a new Position2D object containing the
     * modified values
     */
    public Position2D getPositionUp() {
        int newPositionX = this.positionX;
        int newPositionY = this.positionY + 1;
        return new Position2D(newPositionX, newPositionY);
    }

    /**
     * Method that generates a new Position2D object starting
     * from the current form of the current one;
     * it gets the position in south of the current one by subtracting
     * one from the Y coordinate
     * finally, it returns a new Position2D object containing the
     * modified values
     */
    public Position2D getPositionDown() {
        int newPositionX = this.positionX;
        int newPositionY = this.positionY - 1;
        return new Position2D(newPositionX, newPositionY);
    }

    /**
     * Method that generates a new Position2D object starting
     * from the current form of the current one;
     * it gets the position in west of the current one by subtracting
     * one from the X coordinate
     * finally, it returns a new Position2D object containing the
     * modified values
     */
    public Position2D getPositionLeft() {
        int newPositionX = this.positionX - 1;
        int newPositionY = this.positionY;
        return new Position2D(newPositionX, newPositionY);
    }

    /**
     * Method that generates a new Position2D object starting
     * from the current form of the current one;
     * it gets the position in east of the current one by adding
     * one to the X coordinate
     * finally, it returns a new Position2D object containing the
     * modified values
     */
    public Position2D getPositionRight() {
        int newPositionX = this.positionX + 1;
        int newPositionY = this.positionY;
        return new Position2D(newPositionX, newPositionY);
    }

    /**
     * Method that checks if a given position is valid in the given grid
     * by checking if both X and Y coordinates are less than or equat to
     * the grid size - 1 and if the coordinates are positive (grid has positions
     * from 0 to grid size - 1)
     */
    public boolean isPositionValidInGrid(SimpleBooleanGrid testForGrid) {
        return (this.positionX <= testForGrid.getLength() - 1) &&
                (this.positionY <= testForGrid.getHeight() - 1) &&
                this.positionX >= 0 && this.positionY >= 0;
    }

    /**
     * Method that checks whether a certain position has a value of true
     * in a given SimpleBooleanGrid
     */
    public boolean isPositionTrueInGrid(SimpleBooleanGrid testForGrid) {
        boolean[][] matrixToTestFor = testForGrid.getGridMatrix();
        return matrixToTestFor[this.getPositionX()][this.getPositionY()];
    }

    /**
     * Method that checks whether a certain position has a value of false
     * in a given SimpleBooleanGrid
     */
    public boolean isPositionFalseInGrid(SimpleBooleanGrid testForGrid) {
        boolean[][] matrixToTestFor = testForGrid.getGridMatrix();
        return ! matrixToTestFor[this.getPositionX()][this.getPositionY()];
    }

    /**
     * Getter method for the X coordinate
     */
    public int getPositionX() {
        return this.positionX;
    }

    /**
     * Setter method for the X coordinate
     */
    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    /**
     * Getter method for the Y coordinate
     */
    public int getPositionY() {
        return this.positionY;
    }

    /**
     * Setter method for the X coordinate
     */
    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }

    /**
     * toString() method has been overridden in order for it to return
     * the position object formatted in a easy to understand way
     */
    @Override
    public String toString() {
        return "position is (" + this.positionX + "," + this.positionY + ")";
    }
}