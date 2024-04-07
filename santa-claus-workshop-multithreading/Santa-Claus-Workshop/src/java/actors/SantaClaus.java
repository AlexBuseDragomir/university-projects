package cds.java.project.actors;

import cds.java.project.concurrent.ConcurrentQueue;
import cds.java.project.gift.ChristmasGift;
import cds.java.project.random.RandomGenerator;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of Santa Claus under the form of a thread;
 * Santa's job is to get the gifts from the pipe that he shares with
 * all his reindeer; then he has to put it in his bag of presents
 *
 * @author  Buse-Dragomir Alexandru
 * @since   2018-12-31
 */
public class SantaClaus extends Thread {

    /**
     * List that simulates Santa's Christmas bag
     */
    private List<ChristmasGift> santaClausBagOfGifts;
    /**
     * Pipe that goes from the reindeer squad to Santa's place;
     * all reindeer put their packed gifts in it, and on his side,
     * Santa is taking them alone, one by one, and putting them in
     * his big Christmas bag
     */
    private ConcurrentQueue<ChristmasGift> christmasGiftPipe;
    /**
     * Member that saves the current gift that is processed by Santa;
     * it can be null if Santa Claus has not gotten one yet from the pipe
     */
    private ChristmasGift currentGiftBeingProcessed;
    /**
     * An approximation/estimation of the number of kids for this problem
     */
    private int numberOfKids;
    /**
     * Flag that ensures the time length of Santa's work; when true, the thread keeps
     * running; once set to false, Santa finished its job
     */
    private boolean runningFlag;

    /**
     * Constructor for the class that sets the number of kids and the shared pipe between
     * Santa Claus and his reindeer; the running flag is set by default to true
     */
    public SantaClaus(int numberOfKids, ConcurrentQueue<ChristmasGift> christmasGiftPipe) {
        this.christmasGiftPipe = christmasGiftPipe;
        this.santaClausBagOfGifts = new ArrayList<>();
        this.numberOfKids = numberOfKids;
        this.runningFlag = true;
    }

    /**
     * run() method from Thread is overridden in order to ensure that Santa does
     * its work correctly; the thread runs for a minute or until all gifts have been
     * processed and put in the Christmas bag; the method consists of a series of method calls:
     * taking the gift from the pipe, putting the current gift in the Christmas bag and finally
     * resting for a short while before getting the next one; in the end, it is checked if the
     * necessary number of toys has been obtained, case in which the thread will finish running
     */
    @Override
    public void run() {
        long startTime = System.currentTimeMillis();
        long maxDurationInMilliseconds = 60 * 1000;
        int numberOfProcessedGifts = 0;

        while(System.currentTimeMillis() < startTime + maxDurationInMilliseconds &&
                this.runningFlag) {
            this.takeGiftFromPipe();
            this.putCurrentGiftInChristmasBag();
            numberOfProcessedGifts ++;
            this.restAfterProcessingGift();

            if (numberOfProcessedGifts == this.numberOfKids) {
                System.out.println("HURRAY: All kids get their gifts in time");
                this.runningFlag = false;
            }
        }

        if ( ! this.runningFlag) {
            System.err.println("ERROR: Sadly, the time expired before putting all " +
                    " gifts in the bag");
        }
    }

    /**
     * Getter method for the list of all Christmas gifts
     */
    public List<ChristmasGift> getListOfAllChristmasGifts() {
        return this.santaClausBagOfGifts;
    }

    /**
     * Setter method for the list of all Christmas gifts
     */
    public void setListOfAllChristmasGifts(List<ChristmasGift> santaClausBagOfGifts) {
        this.santaClausBagOfGifts = santaClausBagOfGifts;
    }

    /**
     * Getter method for the pipe shared by reindeer and Santa
     */
    public ConcurrentQueue<ChristmasGift> getChristmasGiftPipe() {
        return this.christmasGiftPipe;
    }

    /**
     * Setter method for the pipe shared by reindeer and Santa
     */
    public void setChristmasGiftPipe(ConcurrentQueue<ChristmasGift> christmasGiftPipe) {
        this.christmasGiftPipe = christmasGiftPipe;
    }

    /**
     * Getter method for the current gift that is being processed by Santa;
     * if no gift has been taken yet from the pipe, an error message is printed
     * and null is returned
     */
    public ChristmasGift getCurrentGiftBeingProcessed() {
        if (this.currentGiftBeingProcessed != null) {
            return this.currentGiftBeingProcessed;
        } else {
            System.err.println("ERROR: Santa Claus did not get any gift from the pipe yet");
        }

        return null;
    }

    /**
     * Setter method for the current gift that is being processed by Santa
     */
    public void setCurrentGiftBeingProcessed(ChristmasGift currentGiftBeingProcessed) {
        this.currentGiftBeingProcessed = currentGiftBeingProcessed;
    }

    /**
     * Getter method for the bag of Christmas gifts that Santa owns
     */
    public List<ChristmasGift> getSantaClausBagOfGifts() {
        return this.santaClausBagOfGifts;
    }

    /**
     * Setter method for the bag of Christmas gifts that Santa owns
     */
    public void setSantaClausBagOfGifts(List<ChristmasGift> santaClausBagOfGifts) {
        this.santaClausBagOfGifts = santaClausBagOfGifts;
    }

    /**
     * Getter method for the number of kids that have been kind and deserve a present
     */
    public int getNumberOfKids() {
        return this.numberOfKids;
    }

    /**
     * Setter method for the number of kids that have been kind and deserve a present
     */
    public void setNumberOfKids(int numberOfKids) {
        this.numberOfKids = numberOfKids;
    }

    /**
     * toString() method from Object has been overridden in order to return
     * the number of gifts that Santa has in his bag
     */
    @Override
    public String toString() {
        return "Santa Claus has at the moment, in his Christmas bag, a number of " +
                this.santaClausBagOfGifts.size() + " gifts";
    }

    /**
     * Method that pops a gift from the pipe and stores it in the current gift memeber
     */
    private void takeGiftFromPipe() {
        this.currentGiftBeingProcessed = this.christmasGiftPipe.popFromConcurrentQueue();
    }

    /**
     * Method that puts the current gift that has been processed in the Chrismas bag;
     * the method has to be called after processing at least a gift from reindeer;
     * otherwise, an error message is printed
     */
    private void putCurrentGiftInChristmasBag() {
        if (this.currentGiftBeingProcessed != null) {
            this.santaClausBagOfGifts.add(this.currentGiftBeingProcessed);
            System.out.println(this.currentGiftBeingProcessed + " and added in the bag");
        } else {
            System.err.println("ERROR: You must first take a gift from the pipe");
        }
    }

    /**
     * Method that ensures the resting period for Santa after the processing process
     * and the addition of a new gift to the bag; random short time between 10 and 20
     * milliseconds
     */
    private void restAfterProcessingGift() {
        RandomGenerator randomGenerator = new RandomGenerator();
        int timeBetweenGiftProcessing = randomGenerator.returnRandomValue(10, 20);
        try {
            sleep(timeBetweenGiftProcessing);
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }
    }
}
