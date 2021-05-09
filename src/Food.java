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

    public void dropOff() {
        this.foremanToMessengerSignal.release();
    }

    public void pickUp() {
        try {
            this.foremanToMessengerSignal.acquire();
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }
    }

    public void deliver() {
        this.messengerToMinerSignal.release();
    }

    public void receive() {
        try {
            this.messengerToMinerSignal.acquire();
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }
    }

    public boolean find() {
        return this.foremanToMessengerSignal.tryAcquire() || this.commonRoom.tryAcquire();
    }

    public boolean findInCommonRoom() {
        return this.commonRoom.tryAcquire();
    }

    public void putInCommonRoom() {
        this.commonRoom.release();
    }

    public void getFromCommonRoom() {
        try {
            this.commonRoom.acquire();
        } catch (InterruptedException ie) {
            ie.printStackTrace();
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
