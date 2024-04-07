package cds.java.project.workshop;

import cds.java.project.actors.Reindeer;
import cds.java.project.actors.SantaClaus;
import cds.java.project.concurrent.ConcurrentQueue;
import cds.java.project.gift.ChristmasGift;
import cds.java.project.random.RandomGenerator;
import cds.java.project.servicethreads.ElfStateGetter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

/**
 * Implementation of a Santa Claus Workshop located on the North Pole as a thread;
 * this is the point in our program in which all other threads are started;
 * it can be called a control point or a point of orchestration;
 * here is the place where the christmasGiftPipe shared by Santa Claus and the
 * reindeer is created, the place in which all Reindeer, SantaClaus, Toy Factories
 * and the service threads are started,
 * the point in which the lists of factories and reindeer are located;
 * we also have here the instance of the Semaphore used for the synchronization
 * of the Reindeer and ElfStateGetter thread
 *
 * @author  Buse-Dragomir Alexandru
 * @since   2018-12-31
 */
public class NorthPoleWorkshop extends Thread{

    /**
     * Constant that holds the maximum size of the gift pipe
     */
    private static final int MAX_GIFT_PIPE_SIZE = 125000;
    /**
     * List of all reindeer that take gifts from the toy factories
     * and send them trough the pipe to Santa Claus
     */
    private List<Reindeer> listOfReindeer;
    /**
     * List of all toy factories in the application
     */
    private List<ToyFactory> listOfToyFactories;
    /**
     * Member that holds the total number of reindeer
     */
    private int numberOfReindeer;
    /**
     * Member that holds the total number of toy factories
     */
    private int numberOfToyFactories;
    /**
     * Concurrent queue that plays the role of the christmas gift pipe
     * that represents the mean of communication between the reindeer
     * and Santa Claus
     */
    private ConcurrentQueue<ChristmasGift> christmasGiftPipe;
    /**
     * Instance of the Santa Claus object
     */
    private SantaClaus santaClaus;
    /**
     * Instance of the elfStateGetter service thread which gets
     * the position of elves trough factories at certain moments
     */
    private ElfStateGetter elfStateGetter;
    /**
     * Semaphore instance used in order to synchronize the elfStateGetter
     * thread and the reindeer because they are not allowed to get gifts
     * when the service thread performs a query
     */
    private Semaphore serviceThreadSemaphore;

    /**
     * Constructor of the NorthPoleWorkshop takes no parameters;
     * it creates a new fair binary semaphore, generates a random number
     * (between 2 and 5) of toy factories, sets the total number of reindeer
     * as the number of factories multiplied by 10 (we suppose that there are
     * 10 reindeer on each toy factory);
     * it also creates the lists of reindeer and of factories and the concurrent
     * queue that will represent the communication pipe between Santa Claus and
     * the reindeer;
     * then we generate a new Santa Claus instance, a new elfStateGetter, and use
     * another two methods to create all toy factories and all reindeer and store them
     * in the appropriate lists
     */
    public NorthPoleWorkshop() {
        this.serviceThreadSemaphore = new Semaphore(2, true);
        RandomGenerator randomGenerator = new RandomGenerator();
        this.numberOfToyFactories = randomGenerator.returnRandomValue(2, 5);
        this.numberOfReindeer = this.numberOfToyFactories * 10;
        this.listOfReindeer = new ArrayList<>();
        this.listOfToyFactories = new ArrayList<>();
        this.christmasGiftPipe = new ConcurrentQueue<>(MAX_GIFT_PIPE_SIZE);
        this.santaClaus = this.generateNewSantaClausInstance();
        this.elfStateGetter = new ElfStateGetter(this.listOfToyFactories, this.serviceThreadSemaphore);
        this.createAllToyFactories();
        this.createAllReindeer();
    }

    /**
     * run() method from the Thread class has been overridden in order to obtain
     * the requested functionality;
     * In this method, we start all toy factories which will, on their turn, start to
     * create elves and add them to the grids to work;
     * this is the place in which all reindeer and Santa Claus are also started;
     */
    @Override
    public void run() {
        for (ToyFactory toyFactory: this.listOfToyFactories) {
            toyFactory.start();
        }
        this.elfStateGetter.start();
        for (Reindeer reindeer: this.listOfReindeer) {
            reindeer.start();
        }
        this.santaClaus.start();
    }

    /**
     * Getter method for the list of reindeer
     */
    public List<Reindeer> getListOfReindeer() {
        return this.listOfReindeer;
    }

    /**
     * Setter method for the list of reindeer
     */
    public void setListOfReindeer(List<Reindeer> listOfReindeer) {
        this.listOfReindeer = listOfReindeer;
    }

    /**
     * Getter method for the list of toy factories
     */
    public List<ToyFactory> getListOfToyFactories() {
        return this.listOfToyFactories;
    }

    /**
     * Setter method for the list of toy factories
     */
    public void setListOfToyFactories(List<ToyFactory> listOfToyFactories) {
        this.listOfToyFactories = listOfToyFactories;
    }

    /**
     * Getter method for the number of reindeer
     */
    public int getNumberOfReindeer() {
        return this.numberOfReindeer;
    }

    /**
     * Setter method for the number of reindeer
     */
    public void setNumberOfReindeer(int numberOfReindeer) {
        this.numberOfReindeer = numberOfReindeer;
    }

    /**
     * Getter method for the number of toy factories
     */
    public int getNumberOfToyFactories() {
        return this.numberOfToyFactories;
    }

    /**
     * Setter method for the number of toy factories
     */
    public void setNumberOfToyFactories(int numberOfToyFactories) {
        this.numberOfToyFactories = numberOfToyFactories;
    }

    /**
     * Getter method for the christmas gift pipe (concurrent queue
     * between Santa Claus and reindeer)
     */
    public ConcurrentQueue<ChristmasGift> getChristmasGiftPipe() {
        return this.christmasGiftPipe;
    }

    /**
     * Setter method for the christmas gift pipe (concurrent queue
     * between Santa Claus and reindeer)
     */
    public void setChristmasGiftPipe(ConcurrentQueue<ChristmasGift> christmasGiftPipe) {
        this.christmasGiftPipe = christmasGiftPipe;
    }

    /**
     * Getter method for the Santa Claus instance
     */
    public SantaClaus getSantaClaus() {
        return this.santaClaus;
    }

    /**
     * Setter method for the Santa Claus instance
     */
    public void setSantaClaus(SantaClaus santaClaus) {
        this.santaClaus = santaClaus;
    }

    /**
     * Getter method for the elfStateGetter instance of the service thread
     */
    public ElfStateGetter getElfStateGetter() {
        return this.elfStateGetter;
    }

    /**
     * Setter method for the elfStateGetter instance of the service thread
     */
    public void setElfStateGetter(ElfStateGetter elfStateGetter) {
        this.elfStateGetter = elfStateGetter;
    }

    /**
     * Getter method for the binary semaphore that synchronizes the
     * access to the gifts in the factory when the service thread is
     * performing a query
     */
    public Semaphore getServiceThreadSemaphore() {
        return this.serviceThreadSemaphore;
    }

    /**
     * Setter method for the binary semaphore that synchronizes the
     * access to the gifts in the factory when the service thread is
     * performing a query
     */
    public void setServiceThreadSemaphore(Semaphore serviceThreadSemaphore) {
        this.serviceThreadSemaphore = serviceThreadSemaphore;
    }

    /**
     * Method that generates a toy factory with a given index and a
     * random grid size
     */
    private ToyFactory generateToyFactory(int toyFactoryIndex) {
        RandomGenerator randomGenerator = new RandomGenerator();
        int toyFactoryGridSize = randomGenerator.returnRandomValue(100, 500);
        return new ToyFactory(toyFactoryIndex, toyFactoryGridSize);
    }

    /**
     * Method that generates all toy factories by using repetitive calls to the
     * other method, generateToyFactory(int toyFactoryIndex);
     * it creates a random number of toy factories (between 2 and 5) because the
     * member numberOfToyFactories has been randomly generated in the constructor
     * of the class;
     * then it adds all these factories in a list represented by a class member
     */
    private void createAllToyFactories() {
        for (int index = 0; index < this.numberOfToyFactories; index ++) {
            ToyFactory newToyFactory = this.generateToyFactory(index);
            this.listOfToyFactories.add(newToyFactory);
        }
    }

    /**
     * Method that generates a reindeer with a given index and
     * an assigned factory to communicate with in order to get
     * gifts and to pack them
     */
    private Reindeer generateReindeer(int reindeerIndex, ToyFactory assignedFactory) {
        return new Reindeer(reindeerIndex, assignedFactory, this.christmasGiftPipe,
                this.serviceThreadSemaphore);
    }

    /**
     * Method that generates all reindeer by using repetitive calls to the
     * other method, generateReindeer(int reindeerIndex, ToyFactory assignedFactory);
     * it creates sets of reindeer (the number of sets is equal with the number of
     * factories) and in set there are 10 reindeer that will all communicate
     * with an assigned factory;
     * finally, all factories are added in the list represented by a class member
     */
    private void createAllReindeer() {
        for (int factoryNumber = 0; factoryNumber < this.numberOfToyFactories; factoryNumber ++) {
            ToyFactory currentFactory = this.listOfToyFactories.get(factoryNumber);
            for (int index = 0; index < 10; index ++) {
                Reindeer newReindeer = this.generateReindeer(index, currentFactory);
                this.listOfReindeer.add(newReindeer);
            }
        }
    }

    /**
     * Method that calculates, based on a few suppositions, the total number
     * of gifts that can be produced in the factories under the current setup;
     * each toy factory has a maximum number of elves that is equal with half
     * of its grid size and each elf is set to produce at most 100 gifts;
     * the result is returned and represents the number of gifts that must get
     * to Santa Claus
     */
    private int calculateTotalNumberOfGifts() {
        int totalNumberOfGifts = 0;

        for (ToyFactory toyFactory: this.listOfToyFactories) {
            int factorySize = toyFactory.getFactoryGridSize();
            int numberOfElvesInFactory = factorySize / 2;
            totalNumberOfGifts = totalNumberOfGifts + numberOfElvesInFactory * 100;
        }

        return totalNumberOfGifts;
    }

    /**
     * Method that generates a new instance of the class Santa Claus;
     * Santa Claus must get the number of kids (calculated trough the
     * use of the private method calculateTotalNumberOfGifts() as each kid
     * will get one gift) and the concurrent queue trough which he communicates
     * with the reindeer; a new Santa Claus with these properties is returned
     */
    private SantaClaus generateNewSantaClausInstance() {
        int numberOfKids = this.calculateTotalNumberOfGifts();
        return new SantaClaus(numberOfKids, this.christmasGiftPipe);
    }
}
