package cds.java.project.actors;

import cds.java.project.concurrent.ConcurrentQueue;
import cds.java.project.gift.ChristmasGift;
import cds.java.project.random.RandomGenerator;
import cds.java.project.workshop.ToyFactory;

import java.util.concurrent.Semaphore;

/**
 * Implementation of a reindeer under the form of a thread;
 * the reindeer has access to a certain factory alongside with
 * another nine of his kind (I have chosen to put a number of
 * ten reindeer per toy factory in order not to over complicate
 * things up; the reindeer waits at the factory in order to get
 * the gifts that have been produced by elves; it has the task to pack
 * them and then put them in a pipe that goes all the way to Santa's
 * workplace; they are not allowed to get gifts when an elf is putting
 * them in the list of the factory and also when the factory asks its
 * elves about their positions trough a service thread
 *
 * @author  Buse-Dragomir Alexandru
 * @since   2018-12-31
 */
@SuppressWarnings("WeakerAccess")
public class Reindeer extends Thread {

    /**
     * Index of current working reindeer
     */
    private int reindeerIndex;
    /**
     * Current gift being processed by the reindeer; this can
     * be null if the reindeer has not yet taken a gift from the
     * factory in which he works
     */
    private ChristmasGift currentGift;
    /**
     * Factory in which the reindeer works at taking and
     * packing presents
     */
    private ToyFactory assignedFactory;
    /**
     * Pipe that goes between the reindeer and Santa Claus; all reindeer
     * put their packed gifts in it, but Santa Claus has to take them one
     * by one and add them in his bag
     */
    private ConcurrentQueue<ChristmasGift> christmasGiftPipe;
    /**
     * Queue in which presents are stored at the factory level;
     * the reindeer takes raw presents from it and then packs them
     */
    private ConcurrentQueue<ChristmasGift> factoryGiftQueue;
    /**
     * Flag that is set to true in the constructor; as long as it is true,
     * the reindeer will continue to do its job; it is set to false after one minute
     * or when the reindeer packed his part from the total number of produced gifts
     */
    private boolean runningFlag;
    /**
     * Binary semaphore shared by the serviceThread that asks elves from all factories
     * about their position and by all reindeer; it ensures that reindeer do not take
     * gifts from the factories when these are giving information about their elves to
     * the service thread (the elf state getter thread)
     */
    private Semaphore serviceThreadSemaphore;
    /**
     * Boolean value that is set to false when the reindeer must wait for another reindeer to finish
     * putting a gift in the queue or for the service thread to finish its asking job; it is true
     * when the reindeer has acquired the semaphore
     */
    private boolean hasAcquiredSemaphore;

    /**
     * Constructor of Reindeer; setting the reindeer index, the toy factory in which he will work,
     * the pipe that goes to Santa and the Semaphore shared with the service thread; the
     * current gift is set to null, the running flag is true and the boolean for the semaphore is
     * false because the reindeer did not yet acquire it
     */
    public Reindeer(int reindeerIndex, ToyFactory assignedFactory,
                    ConcurrentQueue<ChristmasGift> christmasGiftPipe, Semaphore serviceThreadSemaphore) {
        this.reindeerIndex = reindeerIndex;
        this.currentGift = null;
        this.assignedFactory = assignedFactory;
        this.christmasGiftPipe = christmasGiftPipe;
        this.factoryGiftQueue = this.getAssignedFactory().getGiftQueue();
        this.runningFlag = true;
        this.serviceThreadSemaphore = serviceThreadSemaphore;
        this.hasAcquiredSemaphore = false;
    }

    /**
     * run() method from Thread has been overridden in order to have a behavior for the
     * reindeer thread; we use a private method to calculate the maximum number of
     * gifts that can be produced in the assigned toy factory in order to set a fair share of
     * the work for the reindeer; this is based on the assumption that no more than N / 2
     * elves can reside in a factory (where N is the order of the matrix) and on the decided
     * number of 10 reindeer that work on a factory; the reindeer works until a minute passes or
     * until it finished its part of work; the process is simple: he takes a gift from the factory
     * by acquiring the binary semaphore (the concurrentQueue ensures that no elf is putting a gift
     * in the queue too), he packs the gift, puts it in the pipe towards Santa Claus and rests for a
     * short while; if it has finished its assigned number of gifts to process, he sets the runnable to
     * false and finishes its work
     */
    @Override
    public void run() {
        int numberOfGiftsToProcess = this.calculateNumberOfGiftsToProcess();

        long startTime = System.currentTimeMillis();
        long maxDurationInMilliseconds = 60 * 1000;

        while (System.currentTimeMillis() < startTime + maxDurationInMilliseconds
                    && this.runningFlag) {
            this.takeGiftFromFactory();
            this.packChristmasGift();
            this.putGiftIntoPipe();
            this.delayBetweenGiftTakingActions();
            numberOfGiftsToProcess --;

            if (numberOfGiftsToProcess == 0) {
                this.runningFlag = false;
            }
        }
    }

    /**
     * Getter for the index of the reindeer
     */
    public int getReindeerIndex() {
        return reindeerIndex;
    }

    /**
     * Setter for the index of the reindeer
     */
    public void setReindeerIndex(int reindeerIndex) {
        this.reindeerIndex = reindeerIndex;
    }

    /**
     * Getter for the assigned factory to work in
     */
    public ToyFactory getAssignedFactory() {
        return assignedFactory;
    }

    /**
     * Setter for the assigned factory to work in
     */
    public void setAssignedFactory(ToyFactory assignedFactory) {
        this.assignedFactory = assignedFactory;
    }

    /**
     * Getter for the pipe that connects it to Santa's work room
     */
    public ConcurrentQueue<ChristmasGift> getChristmasGiftPipe() {
        return this.christmasGiftPipe;
    }

    /**
     * Setter for the pipe that connects it to Santa's work room
     */
    public void setChristmasGiftPipe(ConcurrentQueue<ChristmasGift> christmasGiftPipe) {
        this.christmasGiftPipe = christmasGiftPipe;
    }

    /**
     * Getter for the queue in which the assigned factory stores the gifts made by elves
     */
    public ConcurrentQueue<ChristmasGift> getFactoryGiftQueue() {
        return this.factoryGiftQueue;
    }

    /**
     * Setter for the queue in which the assigned factory stores the gifts made by elves
     */
    public void setFactoryGiftQueue(ConcurrentQueue<ChristmasGift> factoryGiftQueue) {
        this.factoryGiftQueue = factoryGiftQueue;
    }

    /**
     * Getter for the flag that tells the reindeer if he must continue working
     */
    public boolean isRunningFlag() {
        return this.runningFlag;
    }

    /**
     * Setter for the flag that tells the reindeer if he must continue working
     */
    public void setRunningFlag(boolean runningFlag) {
        this.runningFlag = runningFlag;
    }

    /**
     * Getter for the binary semphore shared by both reindeer and the service thread
     */
    public Semaphore getServiceThreadSemaphore() {
        return this.serviceThreadSemaphore;
    }

    /**
     * Setter for the binary semphore shared by both reindeer and the service thread
     */
    public void setServiceThreadSemaphore(Semaphore serviceThreadSemaphore) {
        this.serviceThreadSemaphore = serviceThreadSemaphore;
    }

    /**
     * Getter for the boolean member which tells if the current reindeer
     * has acquired or not the semaphore
     */
    public boolean isHasAcquiredSemaphore() {
        return this.hasAcquiredSemaphore;
    }

    /**
     * Setter for the boolean member which tells if the current reindeer
     * has acquired or not the semaphore
     */
    public void setHasAcquiredSemaphore(boolean hasAcquiredSemaphore) {
        this.hasAcquiredSemaphore = hasAcquiredSemaphore;
    }

    /**
     * Getter for current gift; if the reindeer has not taken one yet from the factory,
     * an error will indicate this situation; if everything goes well, it returns the
     * current gift being processed
     */
    public ChristmasGift getCurrentGift() {
        if (this.currentGift != null) {
            return this.currentGift;
        } else {
            System.err.println("ERROR: The reindeer " + this.reindeerIndex +
                    " did not get any gift from the factory yet");
        }

        return null;
    }

    /**
     * Setter for the current gift that is being packed by the reindeer
     */
    public void setCurrentGift(ChristmasGift currentGift) {
        this.currentGift = currentGift;
    }

    /**
     *  toString() method has been overridden in order to return the number of
     *  the reindeer and the toy factory from which it takes gifts in order
     *  to pack them and send them to Santa
     */
    @Override
    public String toString() {
        return "reindeer with number " + this.reindeerIndex +
                "is assigned to the Toy Factory with index " +
                this.assignedFactory.getFactoryIndex();
    }

    /**
     * Method trough which the reindeer tries to take a gift from the factory queue;
     * its first task is to try to acquire the semaphore; itv waits to get it and then
     * it pops a gift from the toy factory queue (it is a concurrent queue so it is ensured
     * that at that time, no elf is trying to put a gift inside of it); finally, if he has
     * finished packing the gift, it releases the semaphore
     */
    private void takeGiftFromFactory() {
        try {
            this.serviceThreadSemaphore.acquire();
            this.currentGift = this.factoryGiftQueue.popFromConcurrentQueue();
            this.hasAcquiredSemaphore = true;
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }

        if (this.hasAcquiredSemaphore) {
            this.serviceThreadSemaphore.release();
            this.hasAcquiredSemaphore = false;
        }
    }

    /**
     * Simple method that performs a sleep on the reindeer thread for a random
     * amount of time between 20 and 50 milliseconds
     */
    private void delayBetweenGiftTakingActions() {
        RandomGenerator randomGenerator = new RandomGenerator();
        int delayTimeBetweenActions = randomGenerator.returnRandomValue(20, 50);
        try {
            sleep(delayTimeBetweenActions);
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }
    }

    /**
     * Method that ensures the packing process that is represented by a call
     * to a ChristmasGift setter that marks the gift as being packed;
     * it also prints to the screen the gift when it arrives unpacked and then
     * when it leaves packed
     */
    private void packChristmasGift() {
        System.out.println(this.currentGift);
        this.currentGift.setIsPacked(true);
        System.out.println(this.currentGift);
    }

    /**
     * Method that puts the packed gift in the pipe; if the reindeer tries to put an unpacked
     * gif, an error message is displayed, but this does not happen in the current problem state
     * because the reindeer firstly packs the gift and then sends it
     */
    private void putGiftIntoPipe() {
        if (this.currentGift.isPacked()) {
            this.christmasGiftPipe.pushIntoConcurrentQueue(this.currentGift);
        } else {
            System.err.println("ERROR: Gift must pe packed in order to send it to Santa");
        }
    }

    /**
     * Method that calculates, based on a few suppositions, the maximum number of gifts that a reindeer
     * must pack such that all kids will get their presents
     */
    private int calculateNumberOfGiftsToProcess() {
        int maximumNumberOfElvesOnFactory = this.assignedFactory.getFactoryGridSize() / 2;
        return maximumNumberOfElvesOnFactory * 100 /
                this.assignedFactory.getNumberOfAssignedReindeer();
    }
}
