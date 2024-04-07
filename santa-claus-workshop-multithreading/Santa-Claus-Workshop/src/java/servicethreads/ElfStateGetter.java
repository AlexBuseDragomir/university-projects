package cds.java.project.servicethreads;

import cds.java.project.actors.ElfWorker;
import cds.java.project.random.RandomGenerator;
import cds.java.project.workshop.ToyFactory;

import java.util.List;
import java.util.concurrent.Semaphore;

/**
 * Service Thread called ElfStateGetter that has a very clear task:
 * it performs a number of queries at random intervals (1, 2 seconds)
 * trough which he asks elves from all factories about their current
 * position; the method that does this is synchronized by using a binary semaphore
 * because the reindeer must not be able to get presents from the factories
 * when they are being questioned by the ElfStateGetter; so reindeer must firstly acquire
 * the semaphore in order to have access to the factory concurrent queue; and there
 * they will have to acquire the queue lock in order not to perform an extraction
 * in the same moment in which an elf is pushing a newly created gift into it;
 * in this way, reindeer cannot get gifts when either the elves are pushing new ones
 * in the queue or when the service thread is querying the elves trough the toy factories
 *
 * @author  Buse-Dragomir Alexandru
 * @since   2018-12-31
 */
public class ElfStateGetter extends Thread {

    /**
     * List of all toy factories
     */
    private List<ToyFactory> toyFactoryList;
    /**
     * Service thread semaphore that coordinates the
     * relation between the elf state getter and the reindeer
     */
    private Semaphore serviceThreadSemaphore;
    /**
     * Boolean member which tells us if the semaphore has been
     * acquired in order to release it;
     * I do this because putting the semaphore in a try finally
     * like in the case of locks is not okay because, a semaphore.acquire
     * can throw and Exception and, in that case, if we call semaphore.release
     * in the finally block, the semaphore will get a bonus permit; because
     * a permit has not been given so we must not try to release;
     * that is why, if the acquire succeeds, I make the member true and,
     * after finishing the job, I test it in order to see if a release
     * is necessary
     */
    private boolean hasAcquiredSemaphore;

    /**
     * Constructor gets as parameters a list of toy factories
     * and the semaphore share by both the service thread and by the reindeer;
     * the corresponding values are set and the hasAcquiredSemaphore gets the value of false
     */
    public ElfStateGetter(List<ToyFactory> toyFactoryList, Semaphore serviceThreadSemaphore) {
        this.toyFactoryList = toyFactoryList;
        this.serviceThreadSemaphore = serviceThreadSemaphore;
        this.hasAcquiredSemaphore = false;
    }

    /**
     * run() method from the Thread class is overridden in order to achieve
     * the proper behavior needed in our implementation;
     * the thread runs for a minute, because this is the maximum time allocated
     * for the run of the whole program (or until all gifts are created);
     * all this method does is call the private method askElvesAboutTheirPosition()
     * for each toy factory in a while loop
     */
    @Override
    public void run() {
        long startTime = System.currentTimeMillis();
        long maxDurationInMilliseconds = 60 * 1000;

        while (System.currentTimeMillis() < startTime + maxDurationInMilliseconds) {
            for (ToyFactory toyFactory: this.toyFactoryList) {
                this.askElvesAboutTheirPosition();
            }
        }
    }

    /**
     * Getter for the list of toy factories
     */
    public List<ToyFactory> getToyFactoryList() {
        return this.toyFactoryList;
    }

    /**
     * Setter for the list of toy factories
     */
    public void setToyFactoryList(List<ToyFactory> toyFactoryList) {
        this.toyFactoryList = toyFactoryList;
    }

    /**
     * Getter for the service thread semaphore that coordinates the relation
     * between reindeer and the elf state getter
     */
    public Semaphore getServiceThreadSemaphore() {
        return this.serviceThreadSemaphore;
    }

    /**
     * Setter for the service thread semaphore that coordinates the relation
     * between reindeer and the elf state getter
     */
    public void setServiceThreadSemaphore(Semaphore serviceThreadSemaphore) {
        this.serviceThreadSemaphore = serviceThreadSemaphore;
    }

    /**
     * Getter method for the boolean value which tells if the semaphore
     * was acquired
     */
    public boolean isHasAcquiredSemaphore() {
        return this.hasAcquiredSemaphore;
    }

    /**
     * Setter method for the boolean value which tells if the semaphore
     * was acquired
     */
    public void setHasAcquiredSemaphore(boolean hasAcquiredSemaphore) {
        this.hasAcquiredSemaphore = hasAcquiredSemaphore;
    }

    /**
     * Private method that performs the main action of the elf state
     * getter thread: gets the status of the elves from each toy factory;
     * first of all, it tries to acquires a permit from the semaphore;
     * if it succeeds, it sets the hasAcquiredSemaphore flag to true and moves on
     * to the next phase in which, for each factory in the list of toy factories,
     * it gets the list of currently working elves and, for each of them, it prints
     * the current state; afterwards, the method sleeps for a random time period
     * between one and two seconds); in the end, the value of the hasAcquiredSemaphore
     * is tested and, in the case in which the semaphore has been acquired, it is released
     * and the flag member is set to false; otherwise, no action is needed
     */
    private void askElvesAboutTheirPosition() {
        try {
            this.serviceThreadSemaphore.acquire();
            this.hasAcquiredSemaphore = true;
            for (ToyFactory toyFactory: this.toyFactoryList) {
                List<ElfWorker> listOfWorkingElves =  toyFactory.getWorkingElvesList();

                for (ElfWorker elfWorker: listOfWorkingElves) {
                    System.out.println(elfWorker);
                }
            }
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }

        RandomGenerator randomGenerator = new RandomGenerator();
        int randomNumberOfSeconds = randomGenerator.returnRandomValue(100, 200);
        try {
            sleep(10 * randomNumberOfSeconds);
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }

        if (this.hasAcquiredSemaphore) {
            this.serviceThreadSemaphore.release();
            this.hasAcquiredSemaphore = false;
        }
    }
}
