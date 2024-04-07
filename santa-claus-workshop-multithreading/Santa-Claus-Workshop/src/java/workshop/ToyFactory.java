package cds.java.project.workshop;

import cds.java.project.actors.ElfWorker;
import cds.java.project.concurrent.ConcurrentQueue;
import cds.java.project.gift.ChristmasGift;
import cds.java.project.grid.SimpleBooleanGrid;
import cds.java.project.position.Position2D;
import cds.java.project.random.RandomGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Implementation of a Santa Claus Toy Factory from the Workshop;
 * this is achieved by extending the Thread class;
 * this class does a lot of work: it spawns the elves, it decides their moves
 * and supervises the gift creation process that is done by the elves;
 * it contains a boolean grid, it uses a ReentrantLock to ensure that the movement
 * of elves is correct and has a concurrent queue that plays the role of the deposit
 * of gifts where all presents created by elves are stored awaiting for elves to take
 * and afterwards pack and send them to Santa Claus trough a pipe; I  have chosen to let
 * each factory know the number of reindeer that will take gifts from them, and, to simplify
 * a bit the structure of the program, the number of reindeer is 10 for each toy factory
 *
 * @author  Buse-Dragomir Alexandru
 * @since   2018-12-31
 */
@SuppressWarnings("WeakerAccess")
public class ToyFactory extends Thread {

    /**
     * Constant that stores the maximum size of the gift queue
     */
    private static final int QUEUE_MAX_SIZE = 25000;
    /**
     * Index of the current factory
     */
    private int factoryIndex;
    /**
     * Size of the toy factory grid
     */
    private int factoryGridSize;
    /**
     * Member that counts the number of elves that are currently working
     * in the factory
     */
    private int numberOfWorkingElves;
    /**
     * An instance of the SimpleBooleanGrid class, the grid of
     * the toy factory
     */
    private SimpleBooleanGrid factoryGrid;
    /**
     * List of all elves that are working in the factory (they are added
     * immediately after spawning)
     */
    private List<ElfWorker> workingElvesList;
    /**
     * A reentrant lock that blocks the access to the grid when moving an
     * elf in order to avoid synchronization problems
     */
    private ReentrantLock gridLock;
    /**
     * Concurrent queue that holds all the presents that have been
     * created by elves; this is the place from which reindeer take afterwards
     * the presents and pack them
     */
    private ConcurrentQueue<ChristmasGift> giftQueue;
    /**
     * Number of reindeer assigned to communicate with the factory
     * in order to take presents from the queue and to pack them
     */
    private int numberOfAssignedReindeer;

    /**
     * First constructor of the Toy Factory has a parameter represented
     * by the factory index that is thereby set; it also sets the factory grid
     * size to a default value of 100 (minimum accepted), the number of working
     * elves to 0, creates an empty factory grid, an empty working elves list,
     * creates the lock that will be used later for synchronization and
     * the concurrent queue of gifts;
     * it also sets the number of reindeer to 10 for the current factory
     */
    public ToyFactory(int factoryIndex) {
        this.factoryIndex = factoryIndex;
        this.factoryGridSize = 100;
        this.numberOfWorkingElves = 0;
        this.factoryGrid = new SimpleBooleanGrid(this.factoryGridSize);
        this.workingElvesList = new ArrayList<>();
        this.gridLock = new ReentrantLock();
        this.giftQueue = new ConcurrentQueue<>(QUEUE_MAX_SIZE);
        this.numberOfAssignedReindeer = 10;
    }

    /**
     * Second constructor of the Toy Factory has two parameters represented
     * by the factory index and the factory grid size; it sets the factory grid
     * size, the number of working elves to 0, it creates an empty factory grid,
     * an empty working elves list, the lock that will be used later for synchronization
     * and the concurrent queue of gifts;
     * it also sets the number of reindeer to 10 for the current factory
     */
    public ToyFactory(int factoryIndex, int factoryGridSize) {
        this.factoryIndex = factoryIndex;
        this.factoryGridSize = factoryGridSize;
        this.numberOfWorkingElves = 0;
        this.factoryGrid = new SimpleBooleanGrid(factoryGridSize);
        this.workingElvesList = new ArrayList<>();
        this.gridLock = new ReentrantLock();
        this.giftQueue = new ConcurrentQueue<>(QUEUE_MAX_SIZE);
        this.numberOfAssignedReindeer = 10;
    }

    /**
     * run() method from Thread is overridden in order to achieve the needed behavior;
     * the toy factory spawns elves until their number is equal to half of the grid matrix
     * order; it has a random spawning time between 500 milliseconds and 1 second;
     * first of all, it finds a valid position to spawn an elf; then it creates an elf
     * and gives him an index that is unique in the factory, tells him that he will work in
     * this facility and puts it in the position that was empty; after creating the elf,
     * he will notify factory of its position, in this way ensuring us that the process completed
     * in a correct way and therefore that he can be now started;
     * then it adds it to the list of working elves, increases the numbers of workers and starts
     * the elf thread so that he can do its job; then it performs a sleep before continuing
     * with the next elf spawning
     */
    @Override
    public void run() {

        RandomGenerator randomTime = new RandomGenerator();
        int randomElfSpawnTime = randomTime.returnRandomValue(500, 1000);
        int elfWorkerIndex = 0;

        while (this.numberOfWorkingElves <= this.factoryGridSize) {
            Position2D elfSpawnPosition = this.findValidPositionToSpawnElf();
            ElfWorker newElfWorker = new ElfWorker(elfWorkerIndex, this, elfSpawnPosition);
            newElfWorker.notifyFactoryOfSpawnPosition();
            this.workingElvesList.add(newElfWorker);
            this.numberOfWorkingElves ++;
            elfWorkerIndex ++;
            newElfWorker.start();
            try {
                sleep(randomElfSpawnTime);
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
        }
    }

    /**
     * Method that performs the move command of an elf on the factory grid;
     * first of all, this must be a synchronized action so that no two
     * elves will try to move at the same time on the same position and creating
     * a conflict;
     * therefore, if lock for the movement has been taken, we get the current position
     * from the elf that must be moved;
     * then we get a list of available moving positions for the current elf (list of Strings
     * of form "DOWN", "UP", "RIGHT", "LEFT")';
     * if there is no available moving position, the elf will sleep for 10 to 50 milliseconds;
     * if, however, the elf can move, it gets a random direction from the list of available ones
     * and performs a move towards that certain point; the movement is done by a method from the
     * class and implies setting a new position, notifying the factory about the new position,
     * creating a gift, sending it towards the queue and then resting for ashort while
     */
    public void moveElfOnGrid(ElfWorker elfToMove) {
        this.gridLock.lock();

        try {
            Position2D currentElfPosition = elfToMove.getCurrentPosition();
            List<String> availableMovingPositions;
            availableMovingPositions = this.getListOfAvailableMovingPositions(currentElfPosition);

            if (availableMovingPositions.size() == 0) {
                elfToMove.sleepWhenSurrounded();
            } else {
                int numberOfMovingOptions = availableMovingPositions.size();
                RandomGenerator randomGenerator = new RandomGenerator();
                int randomMove = randomGenerator.returnRandomValue(0, numberOfMovingOptions - 1);
                String randomDirection = availableMovingPositions.get(randomMove);

                switch (randomDirection) {
                    case "UP":
                        this.performAnElfMoveUp(elfToMove);
                        break;
                    case "DOWN":
                        this.performAnElfMoveDown(elfToMove);
                        break;
                    case "LEFT":
                        this.performAnElfMoveLeft(elfToMove);
                        break;
                    case "RIGHT":
                        this.performAnElfMoveRight(elfToMove);
                        break;
                    default:
                        System.err.println("ERROR: Unrecognised direction");
                }
            }
        } finally {
            this.gridLock.unlock();
        }
    }

    /**
     * Getter method for the factory index
     */
    public int getFactoryIndex() {
        return this.factoryIndex;
    }

    /**
     * Setter method for the factory index
     */
    public void setFactoryIndex(int factoryIndex) {
        this.factoryIndex = factoryIndex;
    }

    /**
     * Getter method for the factory grid size
     */
    public int getFactoryGridSize() {
        return this.factoryGridSize;
    }

    /**
     * Setter method for the factory grid size
     */
    public void setFactoryGridSize(int factoryGridSize) {
        this.factoryGridSize = factoryGridSize;
    }

    /**
     * Getter method for the factory grid
     */
    public SimpleBooleanGrid getFactoryGrid() {
        return this.factoryGrid;
    }

    /**
     * Setter method for the factory grid
     */
    public void setFactoryGrid(SimpleBooleanGrid factoryGrid) {
        this.factoryGrid = factoryGrid;
    }

    /**
     * Getter method for the factory list of working elves
     */
    public List<ElfWorker> getWorkingElvesList() {
        return this.workingElvesList;
    }

    /**
     * Setter method for the factory list of working elves
     */
    public void setWorkingElvesList(List<ElfWorker> workingElvesList) {
        this.workingElvesList = workingElvesList;
    }

    /**
     * Getter method that returns the number of elves that are working
     * in the factory at the moment of call
     */
    public int getNumberOfWorkingElves() {
        return this.numberOfWorkingElves;
    }

    /**
     * Setter method for the number of elves in the factory
     */
    public void setNumberOfWorkingElves(int numberOfWorkingElves) {
        this.numberOfWorkingElves = numberOfWorkingElves;
    }

    /**
     * Getter method for the grid reentrant lock
     */
    public ReentrantLock getGridLock() {
        return this.gridLock;
    }

    /**
     * Setter method for the grid reentrant lock
     */
    public void setGridLock(ReentrantLock gridLock) {
        this.gridLock = gridLock;
    }

    /**
     * Getter method for the concurrent queue in which all created
     * gifts are stored until reindeer take them
     */
    public ConcurrentQueue<ChristmasGift> getGiftQueue() {
        return this.giftQueue;
    }

    /**
     * Setter method for the concurrent queue in which all created
     * gifts are stored until reindeer take them
     */
    public void setGiftQueue(ConcurrentQueue<ChristmasGift> giftQueue) {
        this.giftQueue = giftQueue;
    }

    /**
     * Getter method for the number of assigned reindeer for the
     * current toy factory
     */
    public int getNumberOfAssignedReindeer() {
        return this.numberOfAssignedReindeer;
    }

    /**
     * Setter method for the number of assigned reindeer for the
     * current toy factory
     */
    public void setNumberOfAssignedReindeer(int numberOfAssignedReindeer) {
        this.numberOfAssignedReindeer = numberOfAssignedReindeer;
    }

    /**
     * toString() method from the Object class has been overridden in order for it
     * to return the number of the factory and the grid size of the underlying matrix
     */
    @Override
    public String toString() {
        return "Toy factory number " + this.factoryIndex + "with a grid of size " +
                this.factoryGridSize;
    }

    /**
     * Method that returns a list of the available moving positions for an elf;
     * we firstly generate all cardinal points from the current elf position;
     * then, for each of them, we test if it is on the grid surface and if it
     * is occupied by another elf or not; if a position is valid in both of the above
     * checks, a string with the direction is saved in the array list;
     */
    private List<String> getListOfAvailableMovingPositions(Position2D currentPosition) {
        List<String> availablePositionList = new ArrayList<>();
        int currentX = currentPosition.getPositionX();
        int currentY = currentPosition.getPositionY();

        Position2D newPositionUp = new Position2D(currentX, currentY - 1);
        Position2D newPositionDown = new Position2D(currentX, currentY + 1);
        Position2D newPositionLeft = new Position2D(currentX - 1, currentY);
        Position2D newPositionRight = new Position2D(currentX + 1, currentY);

        if (newPositionUp.isPositionValidInGrid(this.factoryGrid) &&
                newPositionUp.isPositionFalseInGrid(this.factoryGrid)) {
            availablePositionList.add("UP");
        }
        if (newPositionDown.isPositionValidInGrid(this.factoryGrid) &&
                newPositionDown.isPositionFalseInGrid(this.factoryGrid)) {
            availablePositionList.add("DOWN");
        }
        if (newPositionLeft.isPositionValidInGrid(this.factoryGrid) &&
                newPositionLeft.isPositionFalseInGrid(this.factoryGrid)) {
            availablePositionList.add("LEFT");
        }
        if (newPositionRight.isPositionValidInGrid(this.factoryGrid) &&
                newPositionRight.isPositionFalseInGrid(this.factoryGrid)) {
            availablePositionList.add("RIGHT");
        }

        return availablePositionList;
    }

    /**
     * Method that moves an elf to the north;
     * it firstly saves the new position by calling the notifyFactoryOfMoveUp() method,
     * then it creates a new gift and passes it to the factory (it is added in the queue);
     * afterwards, the elf gets to rest for a random time in milliseconds and his number
     * of produced toys is increased by one
     */
    private void performAnElfMoveUp(ElfWorker elfToMove) {
        elfToMove.notifyFactoryOfMoveUp();
        this.createNewGiftInFactory();
        elfToMove.restAfterCreatingGift();
        elfToMove.incrementNumberOfToysProduced();
    }

    /**
     * Method that moves an elf to the south;
     * it firstly saves the new position by calling the notifyFactoryOfMoveDown() method,
     * then it creates a new gift and passes it to the factory (it is added in the queue);
     * afterwards, the elf gets to rest for a random time in milliseconds and his number
     * of produced toys is increased by one
     */
    private void performAnElfMoveDown(ElfWorker elfToMove) {
        elfToMove.notifyFactoryOfMoveDown();
        this.createNewGiftInFactory();
        elfToMove.restAfterCreatingGift();
        elfToMove.incrementNumberOfToysProduced();
    }

    /**
     * Method that moves an elf to the west;
     * it firstly saves the new position by calling the notifyFactoryOfMoveLeft() method,
     * then it creates a new gift and passes it to the factory (it is added in the queue);
     * afterwards, the elf gets to rest for a random time in milliseconds and his number
     * of produced toys is increased by one
     */
    private void performAnElfMoveLeft(ElfWorker elfToMove) {
        elfToMove.notifyFactoryOfMoveLeft();
        this.createNewGiftInFactory();
        elfToMove.restAfterCreatingGift();
        elfToMove.incrementNumberOfToysProduced();
    }

    /**
     * Method that moves an elf to the east;
     * it firstly saves the new position by calling the notifyFactoryOfMoveRight() method,
     * then it creates a new gift and passes it to the factory (it is added in the queue);
     * afterwards, the elf gets to rest for a random time in milliseconds and his number
     * of produced toys is increased by one
     */
    private void performAnElfMoveRight(ElfWorker elfToMove) {
        elfToMove.notifyFactoryOfMoveRight();
        this.createNewGiftInFactory();
        elfToMove.restAfterCreatingGift();
        elfToMove.incrementNumberOfToysProduced();
    }

    /**
     * Method that creates a new gift in the factory, sets its packing member
     * to false and then pushes it into the concurrent queue
     */
    private void createNewGiftInFactory() {
        ChristmasGift newGift = new ChristmasGift(false);
        this.giftQueue.pushIntoConcurrentQueue(newGift);
    }

    /**
     * Method that searches the grid for a position in which there is no elf
     * and returns it so that a worker can be put there to perform its job;
     * we start in a random position (we generate a random coordinate X and a
     * random coordinate Y) and search to the end of the matrix;
     * if we find a good value, we mark it as we will put an elf in it and
     * then we return it;
     * if no valid posiution has been found, we start another search, from the start
     * of the matrix towards the random position;
     * we have the certitude that we will finally get an empty position because the number
     * of elves is way smaller than the number of positions in a troy factory grid
     */
    private Position2D findValidPositionToSpawnElf() {
        boolean[][] factoryMatrix = this.factoryGrid.getGridMatrix();
        RandomGenerator randomGenerator = new RandomGenerator();
        int startSearchX = randomGenerator.returnRandomValue(0, this.factoryGridSize - 1);
        int startSearchY = randomGenerator.returnRandomValue(0, this.factoryGridSize - 1);

        for (int rowIndex = startSearchX; rowIndex < this.factoryGridSize; rowIndex ++) {
            for (int columnIndex = startSearchY; columnIndex < this.factoryGridSize; columnIndex ++) {
                if ( ! factoryMatrix[rowIndex][columnIndex]) {
                    factoryMatrix[rowIndex][columnIndex] = true;
                    return new Position2D(rowIndex, columnIndex);
                }
            }
        }
        for (int rowIndex = 0; rowIndex < startSearchX; rowIndex ++) {
            for (int columnIndex = 0; columnIndex < startSearchY; columnIndex++) {
                if (!factoryMatrix[rowIndex][columnIndex]) {
                    factoryMatrix[rowIndex][columnIndex] = true;
                    return new Position2D(rowIndex, columnIndex);
                }
            }
        }
        return null;
    }
}