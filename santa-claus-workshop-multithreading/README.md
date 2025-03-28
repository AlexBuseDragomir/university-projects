# Santa Claus Workshop Project

**NOTE:** This was never fully implemented, still a work in progress. 
It contains the main code infrastructure, but it is not properly syncronized.

## I. Introduction & Problem Statement

Christmas is just around the corner! Santa Klaus and his staff (**Elves** and **Reindeers**) are preparing for another wonderful Christmas that brings joy and gifts to all children around the world.

But Santa Klaus is in need of some help!

He owns a workshop that may contain multiple **Factories** (he recreates them every year). He has **Elves** in the factories creating toys, and **Reindeers** awaiting the toys to wrap them up as gifts. He desperately needs a workshop running because his older workshop manager left.

The workshop plan has some rules to ensure all gifts are created on time and no child is left without a present. He heard you are experts in Java concurrency and ready to help him implement a factory plan.

Below is the information Santa provided about the workshop and the tasks involved.

## II. Details from Santa

### 1. Toy Factories

* A random number of **Factories** (between 2-5) will create toys. The exact number isn't known until the plan runs.
* All factories are structured like a matrix (random N x N, where N is between 100-500).
* Each factory contains a list of its **Elves**.
* Every few seconds, the factory will query all its **Elves** for their current position.
* There can be no more than N/2 **Elves** in a single factory.

### 2. Elves

**Elves** are crucial as they create the toys.

* **Elves** spawn randomly in each factory at a random location and at a random time (between 500-1000 milliseconds).
* When an **Elf** spawns, it must register itself with its factory.
* No two **Elves** can occupy the same position.
* Each **Elf** works independently.
* **Elves** create gifts by:
    * Moving one step in a direction (left, right, up, down).
    * When hitting a factory wall, they change direction (any direction except towards the wall).
    * A gift is created after each move.
    * If an **Elf** is surrounded by other **Elves** and cannot move, it pauses for a random time (between 10-50 milliseconds).
    * When a gift is created, the **Elf** notifies its factory about the gift. The factory then asks all **Elves** for their locations to report to Santa.
    * After creating a gift, the **Elf** rests for a short while (30 milliseconds).
        * *They are really fast!*
* **Elves** work continuously (until December 25th, so they don't need an explicit stop condition in the main simulation).

### 3. Reindeers

* **Reindeers** are in the workshop, waiting to receive gifts from the **Factories**.
* They can always read the number and type of gifts from the **Factories**, *except* when **Elves** are notifying the factory about new gifts or when the factory is actively querying **Elves**.
* A maximum of 10 **Reindeers** can read from a single factory simultaneously.
* There must be a random time interval between consecutive readings from the same factory by a **Reindeer**.
* **Reindeers** put all collected gifts through a "pipe" to Santa.
* Multiple **Reindeers** can send gifts concurrently, while Santa reads them individually.
* The number of **Reindeers** is known from the start (more than 8, for backup).

### 4. Main Santa's Workshop

* The main workshop entity contains all the **Factories**.
* It is responsible for creating the **Factories**.
* It also spawns **Elves** and assigns them to a random factory. (Remember: each **Elf** is responsible for registering itself with its assigned factory).

## III. Concurrency Considerations

* The control flow is subtle. **Elf** movement and reporting are initiated independently by each **Elf** (running in its own thread).
* Different **Elves** may move and report to the factory concurrently.
* Concurrent factory reporting *must* be synchronized.
* Calling the factory's `report` method also triggers calls to the individual `report` methods of all registered **Elves**. This interaction needs careful handling, especially since both `report` and `move` methods are likely `synchronized`, preventing concurrent calls *on the same object*.

## IV. Technical Hints

Santa suggests using the following Java concurrency tools:

* **Semaphores**, **Monitors**, and **Locks** (use them appropriately).
* **Threads** are essential. Each **Elf** can be implemented as a separate thread.
* **Communication:** **Elves** produce gifts, **Reindeers** consume them and pass them to Santa.
* **Gift Delivery:** Santa wants the **Reindeers** to deliver the gifts to his office via **TCP/IP**. Ensure no gifts are missed so Santa can create his list.

## V. Extra Tasks

### 1. Retire an Elf

* Add a thread that retires **Elves** at random times and in random order.
* Retiring means an **Elf**'s `run` method should terminate normally (do *not* use deprecated `stop()` methods).
* The retired **Elf** must be removed from the factory in a thread-safe manner.
* The garbage collector will eventually reclaim the memory.
* **Hint:** Use a **Semaphore** (initially 0). **Elves** can periodically `tryAcquire` this semaphore to get "permission to retire". A separate thread releases the semaphore randomly.
* *Optional:* Modify the program so retired **Elves** are respawned after some random time.

### 2. Sleeping Elves (Using Semaphores)

* **(Start from the original implementation)** Modify the program so that when an **Elf** finds itself on the factory's diagonal (where its x-coordinate is close to its y-coordinate) after a move, it "goes to sleep" (stops moving).
* *Note:* An **Elf** jumping *over* the diagonal in one move does not trigger sleep.
* When *all* **Elves** in a factory are asleep on the diagonal, they *all* wake up simultaneously and resume moving.
* This cycle repeats forever.
* **Hint:** Recognize this as barrier synchronization. Implement it using N+1 **Semaphores**:
    * One common barrier semaphore (released by **Elves** reaching the barrier).
    * An array of 'continue' semaphores (one per **Elf**), acquired by **Elves** to proceed past the barrier.
    * A special barrier synchronization process/thread is needed to repeatedly acquire the barrier semaphore N times and then release all 'continue' semaphores.

### 3. Sleeping Elves Revisited (Using `java.util.concurrent.CyclicBarrier`)

* Rewrite the solution from Extra Task 2 using the `java.util.concurrent.CyclicBarrier` class instead of manual semaphore implementation.

### 4. Your Own Cyclic Barrier

* **(Harder Exercise)** Implement your own simplified `CyclicBarrier` class with the following structure:

    ```java
    public class CyclicBarrier {
        public CyclicBarrier(int parties);
        public void await();
    }
    ```

* `parties` is the number of threads that must reach the barrier.
* `await()` causes a thread to wait until all `parties` threads have called `await()`.
* Use this custom class to solve the sleeping elf problem again.
* **Hint:** You might use a counter for arrived threads (protected by a mutex **Semaphore**) and a single gate **Semaphore** on which threads wait.
* **Question:** Why can't you use Java's `synchronized` keyword instead of the mutex **Semaphore** for protecting the counter in this specific implementation pattern? (Think about where threads need to block and what `synchronized` locks).
