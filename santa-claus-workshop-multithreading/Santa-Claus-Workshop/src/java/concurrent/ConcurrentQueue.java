package cds.java.project.concurrent;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * A particular implementation of a first in / first out (FIFO)
 * queue that works correctly in a situation in which multiple
 * threads try to access it at the same time, either for adding
 * new values or for removing already added ones
 * Two methods have been implemented, the enq(), for adding a new
 * value at the end (tail) of the queue and the deq(), for removing the
 * first element (head) of the queue
 * This is a generic class, meaning that the "T" value can be replaced
 * with an Integer, String, Character, RandomCharacter (and so on) instance
 *
 * @author  Buse-Dragomir Alexandru
 * @since   2018-12-31
 */
@SuppressWarnings("ALL")
public class ConcurrentQueue<T> {

    /**
     * The current head of our concurrent queue
     * The volatile keyword  guarantees that any
     * thread that reads this member will see the most
     * recently written value
     */
    private volatile int head;
    /**
     * The current tail of our concurrent queue
     * The volatile keyword  guarantees that any
     * thread that reads this member will see the most
     * recently written value
     */
    private volatile int tail;
    /**
     * The generic array that is the actual concurrent queue in which all
     * operations will be performed (values added and then consumed)
     */
    private T[] queueValues;
    /**
     * An instance of the ReentrantLock -implementation of the Lock
     * interface-, the queueLock is fundamental in ensuring the synchronization
     * of the queue operations performed by the producers and consumers threads
     */
    private ReentrantLock queueLock;
    /**
     * A condition variable belonging to the reentrant lock, used in order to
     * perform await(), signal(), signalAll and so on, operations used in order
     * to ensure that we manage with the cases in which the queue is either empty
     * or full
     */
    private Condition queueCondition;
    /**
     * A member which holds the maximum length of the queue, so the number
     * of elements that can fit inside the queue until it is full
     */
    private int queueMaxSize;

    /**
     * The constructor gets the maximum dimension of the queue;
     * it initialises the head and the tail as being 0, so no
     * element is present in the queue in this moment
     * It creates a new reentrant lock instance and uses its
     * newCondition() method in order to obtain a condition
     * variable; we create a generic queue that will hold a
     * maximum number of queueMaxSize elements; created as an
     * array of Object instances, it is then casted to the generic
     * type T
     */
    public ConcurrentQueue(int queueMaxSize) {
        this.head = 0;
        this.tail = 0;
        this.queueLock = new ReentrantLock();
        this.queueCondition = this.queueLock.newCondition();
        this.queueMaxSize = queueMaxSize;
        this.queueValues = (T[]) new Object[this.queueMaxSize];
    }

    /**
     * A method that adds a new value to the end of the queue
     * At first, it tries to acquire the lock; if it succeeds,
     * it tries the following: if the queue is full and no elements
     * can be added at the moment, it awaits in a while loop;
     * once it gets past the while loop (there is now room to add a
     * new value), it calculates the position in which the new element
     * must be inserted and then adds it, increasing afterwards the
     * value of the tail so that it indicates now to the next position
     * in which a value can be inserted
     * A message is printed on the console, indicating the value that
     * has been added
     * Then, as the queue has been modified, it notifies all other
     * processes so that they will get their chance at performing an
     * action on the concurrent queue
     * In a finally block, we call the unlock() method so we will be sure
     * that, whatever happens in the try block, the lock will be available
     * for other processes to take and use it, avoiding deadlock
     */
    public void pushIntoConcurrentQueue(T newValue) {
        this.queueLock.lock();

        try {
            while (isQueueFull()) {
                try {
                    this.queueCondition.await();
                } catch (InterruptedException ie) {
                    ie.printStackTrace();
                }
            }

            int lastPosition = this.tail % this.queueValues.length;
            this.queueValues[lastPosition] = newValue;
            this.tail ++;
            this.queueCondition.signalAll();
        } finally {
            this.queueLock.unlock();
        }
    }

    /**
     * A method that removes a value from the head of the queue
     * At first, it tries to acquire the lock; if it succeeds,
     * it tries the following: if the queue is empty and no elements
     * can be removed at the moment, it awaits in a while loop;
     * once it gets past the while loop (there is at least a value in the queue),
     * it calculates the position of the first element (the head index)
     * and then removes it form the queue, increasing afterwards the
     * value of the head so that it indicates now to the next position
     * in which the first valid element can be found
     * A message is printed on the console, indicating the value that
     * has been removed and consumed
     * Then, as the queue has been modified, it notifies all other
     * processes so that they will get their chance at performing an
     * action on the concurrent queue
     * In a finally block, we call the unlock() method so we will be sure
     * that, whatever happens in the try block, the lock will be available
     * for other processes to take and use it, avoiding deadlock
     */
    public T popFromConcurrentQueue() {
        this.queueLock.lock();

        try {
            while (isQueueEmpty()) {
                try {
                    this.queueCondition.await();
                } catch (InterruptedException ie) {
                    ie.printStackTrace();
                }
            }

            int firstPosition = this.head % this.queueValues.length;
            T firstElement = this.queueValues[firstPosition];
            this.head ++;
            this.queueCondition.signalAll();

            return firstElement;
        } finally {
            this.queueLock.unlock();
        }
    }

    /**
     * A getter method that returns the inner queue used in order
     * to modelate the ConcurrentQueue class
     */
    public T[] getQueueValues() {
        return this.queueValues;
    }

    /**
     * A simple boolean method that checks if the queue is full,
     * by verifying if the difference between the tail and the head
     * is equal with the maximum capacity of our queue;
     * if it is not the case, the method returns false
     */
    private boolean isQueueFull() {
        if(this.tail - this.head == this.queueMaxSize) {
            return true;
        }
        return false;
    }

    /**
     * A simple boolean method that checks if the queue is empty,
     * by verifying if the tail is equal with the head (meaining
     * that there are no elements left)
     * if it is not the case, the method returns false
     */
    private boolean isQueueEmpty() {
        if (this.tail == this.head) {
            return true;
        }
        return false;
    }
}
