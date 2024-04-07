package cds.java.project.random;

import java.util.Random;

/**
 * Implementation of a random number generator class which has some
 * easy to use methods for getting a random value, a random value
 * that is less than a given one, or a random value between a minimum
 * and a maximum
 * This is a simple object structure that is needed in the micro
 * world of the program in order to simulate a North Pole Workshop;
 * this is a functional class, that contains just utility methods;
 * all classes have the same name, but different signatures, so they are
 * overloaded in order to provide an easy to use form
 *
 * @author  Buse-Dragomir Alexandru
 * @since   2018-12-31
 */
public class RandomGenerator {

    /**
     * Method that does not take any parameter and returns a random
     * integer value
     */
    public int returnRandomValue() {
        Random newRandomValue = new Random();
        return Math.abs(newRandomValue.nextInt());
    }

    /**
     * Method that takes one parameter and returns a random integer
     * value that is smaller than or equal with the maximumValue parameter
     */
    public int returnRandomValue(int maximumValue) {
        Random newRandomValue = new Random();
        return newRandomValue.nextInt(maximumValue + 1);
    }

    /**
     * Method that takes two parameters and returns a random integer
     * value that is smaller than or equal with the maximumValue parameter,
     * but greater than or equal with the minimumValue parameter
     */
    public int returnRandomValue(int minimumValue, int maximumValue) {
        Random newRandomValue = new Random();
        return newRandomValue.nextInt(maximumValue - minimumValue + 1) + minimumValue;
    }
}
