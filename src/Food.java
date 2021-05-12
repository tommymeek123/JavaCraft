import java.util.Random;
import java.util.concurrent.Semaphore;

/**
 * An enum representing an ingredient for making a sandwich.
 *
 * @author Brett Dale
 * @author Tommy Meek
 * @version May, 2021
 */
public enum Food {
    BREAD("Bread"), CHEESE("Cheese"), BOLOGNA("Bologna");

    /** The name of the food. */
    private String name;

    /** Signal that the foreman sends to the messengers to indicate food has been dropped off. */
    private Semaphore foremanToMessengerSignal;

    /** Signal that the messengers send to the miners to indicate food has been delivered. */
    private Semaphore messengerToMinerSignal;

    /** Signal that messengers give each other when they get food they cannot use themselves. */
    private Semaphore commonRoom;

    /**
     * Constructor for the Ingredient enum,
     */
    private Food(String name) {
        this.name = name;
        this.foremanToMessengerSignal = new Semaphore(0);
        this.messengerToMinerSignal = new Semaphore(0);
        this.commonRoom = new Semaphore(0);
    }

    /**
     * This method just gets the other types of food that the messengers are
     * @return Food array of other types of Food
     */
    public Food[] getOthers() {
        Food[] food = new Food[2];
        switch(this) {
            case BREAD:
                food[0] = CHEESE;
                food[1] = BOLOGNA;
                break;
            case CHEESE:
                food[0] = BOLOGNA;
                food[1] = BREAD;
                break;
            case BOLOGNA:
                food[0] = BREAD;
                food[1] = CHEESE;
                break;
        }
        return food;
    }

    /**
     * This method gets the other type of Food that messenger doesn't need
     * @param second other Food of messenger
     * @return other Food messenger doesn't need
     */
    public Food getOtherOne(Food second) {
        Food otherFood = null;
        if (this == CHEESE && second == BREAD || this == BREAD && second == CHEESE) {
            otherFood = BOLOGNA;
        } else if (this == BREAD && second == BOLOGNA || this == BOLOGNA && second == BREAD) {
            otherFood = CHEESE;
        } else {
            otherFood = BREAD;
        }
        return otherFood;
    }

    /**
     * Method to pick two Food types
     * @return Array of two different Food types
     */
    public static Food[] pickTwo() {
        Food[] food = new Food[2];
        Random rand = new Random();
        int randomNum = rand.nextInt(3);
        switch(randomNum) {
            case 0:
                food[0] = CHEESE;
                food[1] = BOLOGNA;
                break;
            case 1:
                food[0] = BREAD;
                food[1] = BOLOGNA;
                break;
            case 2:
                food[0] = BREAD;
                food[1] = CHEESE;
                break;
        }
        return food;
    }

    /**
     * Method to send signal to messenger
     */
    public void dropOff() {
        this.foremanToMessengerSignal.release();
    }

    /**
     * Method to send signal to messenger then try and go in
     */
    public void pickUp() {
        try {
            this.foremanToMessengerSignal.acquire();
        } catch (InterruptedException ie) {
            System.exit(0);
        }
    }

    /**
     * Method to signal Miners to eat
     */
    public void deliver() {
        this.messengerToMinerSignal.release();
    }

    /**
     * Method to signal Miners
     */
    public void receive() {
        try {
            this.messengerToMinerSignal.acquire();
        } catch (InterruptedException ie) {
            System.exit(0);
        }
    }

    /**
     * Checks for food at docks and common room
     * @return true if Messengers find food
     */
    public boolean find() {
        return this.foremanToMessengerSignal.tryAcquire() || this.commonRoom.tryAcquire();
    }

    /**
     * Method to get Food in common room if it is there
     * @return true if food was found, false otherwise
     */
    public boolean findInCommonRoom() {
        return this.commonRoom.tryAcquire();
    }

    /**
     * puts Food in common room
     */
    public void putInCommonRoom() {
        this.commonRoom.release();
    }

    /**
     * Another method to get Food from common room
     */
    public void getFromCommonRoom() {
        try {
            this.commonRoom.acquire();
        } catch (InterruptedException ie) {
            System.exit(0);
        }
    }

    /**
     * Generates a String representation of this enum.
     * 
     * @return a String representation of this enum.
     */
    public String toString() {
        return this.name;
    }
}
