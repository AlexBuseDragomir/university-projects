package cds.java.project.actors;

import cds.java.project.grid.SimpleBooleanGrid;
import cds.java.project.position.Position2D;
import cds.java.project.random.RandomGenerator;
import cds.java.project.workshop.ToyFactory;

/**
 * Implementation of an elf worker as a thread; elves are placed on a grid
 * and belong to one toy factory only; they work until the day of Christmas
 * and produce gifts by moving in one direction; in the case in which they are
 * surrounded, they sleep for a bit and, after moving they also rest, but in
 * the rest of the time they just move all over the map; their number will increase
 * until it reaches the order of the grid matrix divided by 2
 *
 * @author  Buse-Dragomir Alexandru
 * @since   2018-12-31
 */
@SuppressWarnings("WeakerAccess")
public class ElfWorker extends Thread {

    /**
     * Index of current working elf
     */
    private int elfWorkerIndex;
    /**
     * Elf's assigned factory; they know the place in
     * which they work after being assigned to it
     */
    private ToyFactory assignedFactory;
    /**
     * Every elf will count the number of toys it produced
     */
    private int numberOfToysProduced;
    /**
     * Current position in which this elf can be found
     * on the grid matrix
     */
    private Position2D currentPosition;
    /**
     * Flag that is true as long as the elf must go on
     * with its work; when made false, the elf stops its work
     */
    private boolean runningFlag;

    /**
     * Constructor that gets the assigned factory and the current position;
     * number of toys produced is made 0 and the running flag is true
     */
    public ElfWorker(int elfWorkerIndex, ToyFactory assignedFactory, Position2D currentPosition) {
        this.elfWorkerIndex = elfWorkerIndex;
        this.assignedFactory = assignedFactory;
        this.currentPosition = currentPosition;
        this.numberOfToysProduced = 0;
        this.runningFlag = true;
    }

    /**
     * run() method from the Thread class is overridden;
     * in order not to force him too much, if after one minute the
     * elf is still working, he will be stopped; or, if it is a
     * hard and quick worker, after he has produced a number of
     * 100 gifts; in the run method, we just move it in one direction
     * and check if the number of gifts has reached the target value
     */
    @Override
    public void run() {
        long startTime = System.currentTimeMillis();
        long maxDurationInMilliseconds = 60 * 1000;

        while (System.currentTimeMillis() < startTime +
                maxDurationInMilliseconds && runningFlag) {
            this.assignedFactory.moveElfOnGrid(this);

            if (this.numberOfToysProduced == 100) {
                this.runningFlag = false;
            }
        }
    }

    /**
     * Method that rests an elf for 30 milliseconds after creating a gift
     */
    public void restAfterCreatingGift() {
        try {
            sleep(30);
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }
    }

    /**
     * Method that ensures that, if surrounded, an elf will wait
     * for some time (random between 10 and 50 milliseconds) and then
     * try again to move and create gifts
     */
    public void sleepWhenSurrounded() {
        RandomGenerator randomGenerator = new RandomGenerator();
        int sleepTime = randomGenerator.returnRandomValue(10, 50);

        try {
            sleep(sleepTime);
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }
    }

    /**
     * Method that increments the number of toys produced
     * by the current elf
     */
    public void incrementNumberOfToysProduced() {
        this.numberOfToysProduced ++;
    }

    /**
     * Method that notifies the assigned factory about the fact that the current
     * elf has produced a toy; the value in the grid corresponding with the elf's new position
     * is properly set to true; method is synchronized so that only one elf can notify factory
     * at a time after creation
     */
    public synchronized void notifyFactoryOfSpawnPosition() {
        SimpleBooleanGrid factoryGrid = this.assignedFactory.getFactoryGrid();
        factoryGrid.setGridPositionToTrue(this.currentPosition);
    }

    /**
     * Method that notifies factory after a certain type of move, in this case
     * a move to the north; the current elf position is saved and marked with false in the
     * factory grid because the elf is about to move from that point; using a method from
     * Position2D, a new Position2D object, pointing to the position in the north is generated;
     * this new position is set to true in the grid because the elf will stay there now;
     * finally, the currentPosition field in the elf class is updated
     */
    public void notifyFactoryOfMoveUp() {
        Position2D currentElfPosition = this.getCurrentPosition();
        SimpleBooleanGrid factoryGrid = this.assignedFactory.getFactoryGrid();
        factoryGrid.setGridPositionToFalse(currentElfPosition);
        Position2D newElfPosition = currentElfPosition.getPositionUp();
        factoryGrid.setGridPositionToTrue(newElfPosition);
        this.setCurrentPosition(newElfPosition);
    }

    /**
     * Method that notifies factory after a certain type of move, in this case
     * a move to the south; the current elf position is saved and marked with false in the
     * factory grid because the elf is about to move from that point; using a method from
     * Position2D, a new Position2D object, pointing to the position in the south is generated;
     * this new position is set to true in the grid because the elf will stay there now;
     * finally, the currentPosition field in the elf class is updated
     */
    public void notifyFactoryOfMoveDown() {
        Position2D currentElfPosition = this.getCurrentPosition();
        SimpleBooleanGrid factoryGrid = this.assignedFactory.getFactoryGrid();
        factoryGrid.setGridPositionToFalse(currentElfPosition);
        Position2D newElfPosition = currentElfPosition.getPositionDown();
        factoryGrid.setGridPositionToTrue(newElfPosition);
        this.setCurrentPosition(newElfPosition);
    }

    /**
     * Method that notifies factory after a certain type of move, in this case
     * a move to the west; the current elf position is saved and marked with false in the
     * factory grid because the elf is about to move from that point; using a method from
     * Position2D, a new Position2D object, pointing to the position in the west is generated;
     * this new position is set to true in the grid because the elf will stay there now;
     * finally, the currentPosition field in the elf class is updated
     */
    public void notifyFactoryOfMoveLeft() {
        Position2D currentElfPosition = this.getCurrentPosition();
        SimpleBooleanGrid factoryGrid = this.assignedFactory.getFactoryGrid();
        factoryGrid.setGridPositionToFalse(currentElfPosition);
        Position2D newElfPosition = currentElfPosition.getPositionLeft();
        factoryGrid.setGridPositionToTrue(newElfPosition);
        this.setCurrentPosition(newElfPosition);
    }

    /**
     * Method that notifies factory after a certain type of move, in this case
     * a move to the east; the current elf position is saved and marked with false in the
     * factory grid because the elf is about to move from that point; using a method from
     * Position2D, a new Position2D object, pointing to the position in the east is generated;
     * this new position is set to true in the grid because the elf will stay there now;
     * finally, the currentPosition field in the elf class is updated
     */
    public void notifyFactoryOfMoveRight() {
        Position2D currentElfPosition = this.getCurrentPosition();
        SimpleBooleanGrid factoryGrid = this.assignedFactory.getFactoryGrid();
        factoryGrid.setGridPositionToFalse(currentElfPosition);
        Position2D newElfPosition = currentElfPosition.getPositionRight();
        factoryGrid.setGridPositionToTrue(newElfPosition);
        this.setCurrentPosition(newElfPosition);
    }

    /**
     * Getter method for elf index
     */
    public int getElfWorkerIndex() {
        return this.elfWorkerIndex;
    }

    /**
     * Setter method for elf index
     */
    public void setElfWorkerIndex(int elfWorkerIndex) {
        this.elfWorkerIndex = elfWorkerIndex;
    }

    /**
     * Getter method for elf assigned factory
     */
    public ToyFactory getAssignedFactory() {
        return this.assignedFactory;
    }

    /**
     * Setter method for elf assigned factory
     */
    public void setAssignedFactory(ToyFactory assignedFactory) {
        this.assignedFactory = assignedFactory;
    }

    /**
     * Getter method for the number of toys produced by the current elf
     */
    public int getNumberOfToysProduced() {
        return this.numberOfToysProduced;
    }

    /**
     * Setter method for the number of toys produced by the current elf
     */
    public void setNumberOfToysProduced(int numberOfToysProduced) {
        this.numberOfToysProduced = numberOfToysProduced;
    }

    /**
     * Getter method for the elf's current position on the grid matrix,
     * in the toy factory
     */
    public Position2D getCurrentPosition() {
        return this.currentPosition;
    }

    /**
     * Setter method for the elf's current position on the grid matrix,
     * in the toy factory
     */
    public void setCurrentPosition(Position2D currentPosition) {
        this.currentPosition = currentPosition;
    }

    /**
     * Getter method for the running flag of the elf
     */
    public boolean isRunningFlag() {
        return this.runningFlag;
    }

    /**
     * Setter method for the running flag of the elf
     */
    public void setRunningFlag(boolean runningFlag) {
        this.runningFlag = runningFlag;
    }

    /**
     * toString() method from the Object class is overridden in order to return
     * the index of the factory where the elf is working, the number of the elf
     * and the current position in which he resides
     */
    @Override
    public String toString() {
        return "Elf worker in Toy Factory(" + this.assignedFactory.getFactoryIndex() +
        ") " + "with number " + this.elfWorkerIndex + ", on the position: " + this.currentPosition;
    }
}
