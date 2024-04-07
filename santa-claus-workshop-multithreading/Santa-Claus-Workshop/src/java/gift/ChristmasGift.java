package cds.java.project.gift;

/**
 * Implementation of a Christmas gift under the form of a class;
 * This is a simple object structure that is needed in the micro
 * world of the program in order to simulate a North Pole Workshop
 *
 * @author  Buse-Dragomir Alexandru
 * @since   2018-12-31
 */
public class ChristmasGift {

    /**
     * Static member that is an internal class counter;
     * it gives the index for the gift (each gift has a number
     * starting from 0; each time we create a gift, it gets an
     * index greater by one from the previous one; initialised when an object
     * is firstly created, trough the use of a static initializer
     */
    private static int numberOfGifts;
    /**
     * Index of the christmas gift
     */
    private int giftIndex;
    /**
     * boolean member; it tells whether the gift has been packed and is set
     * to true after being processed by a reindeer
     */
    private boolean isPacked;

    static {
        numberOfGifts = 0;
    }

    /**
     * Constructor for gift sets the isPacked property, the gift index from
     * the static member numberOfGifts and increments the latter member
     */
    public ChristmasGift(boolean isPacked) {
        this.isPacked = isPacked;
        this.giftIndex = numberOfGifts;
        numberOfGifts ++;
    }

    /**
     * Getter method for the static member numberOfGifts that return the
     * number of ChristmasGift instances made so far
     */
    public static int getNumberOfGifts() {
        return numberOfGifts;
    }

    /**
     * Getter method for the packing property
     */
    public boolean isPacked() {
        return this.isPacked;
    }

    /**
     * Setter method for the packing property
     */
    public void setIsPacked(boolean isPacked) {
        this.isPacked = isPacked;
    }

    /**
     * Getter method for the Christmas gift index
     */
    public int getGiftIndex() {
        return this.giftIndex;
    }

    /**
     * Setter method for the Christmas gift index
     */
    public void setGiftIndex(int giftIndex) {
        this.giftIndex = giftIndex;
    }

    /**
     * toString() method from the Object class has been overridden in order
     * for it to return, after constructing it with a StringBuilder, a message;
     * the message tells us the number of the gift, and whether it is unpacked or
     * if it has already been packed by a reindeer
     */
    @Override
    public String toString() {
        StringBuilder giftMessage = new StringBuilder();
        giftMessage.append("gift with number ");
        giftMessage.append(this.giftIndex);

        if ( ! this.isPacked) {
            giftMessage.append(" is waiting to be packed");
        } else {
            giftMessage.append(" has been packed");
        }

        return giftMessage.toString();
    }
}
