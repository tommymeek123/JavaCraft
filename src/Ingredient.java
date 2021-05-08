import java.util.Random;
import java.util.concurrent.Semaphore;

/**
 * An enum representing an ingredient for making a sandwich.
 *
 * @author Brett Dale
 * @author Tommy Meek
 * @version May, 2021
 */
public enum Ingredient {
    BREAD("Bread"), CHEESE("Cheese"), BOLOGNA("Bologna");

    /** The name of the ingredient. */
    private String name;

    /** Signal that the foreman sends to the messengers to indicate food has been dropped off. */
    private Semaphore foremanToMessengerSignal;

    /** Signal that the messengers send to the miners to indicate food has been delivered. */
    private Semaphore messengerToMinerSignal;

    /**
     * Constructor for the Ingredient enum,
     */
    private Ingredient(String name) {
        this.name = name;
        this.foremanToMessengerSignal = new Semaphore(0);
    }

    public Ingredient[] getOthers() {
        Ingredient[] ingredients = new Ingredient[2];
        switch(this) {
            case BREAD:
                ingredients[0] = BOLOGNA;
                ingredients[1] = CHEESE;
                break;
            case CHEESE:
                ingredients[0] = BOLOGNA;
                ingredients[1] = BREAD;
                break;
            case BOLOGNA:
                ingredients[0] = BREAD;
                ingredients[1] = CHEESE;
                break;
        }
        return ingredients;
    }

    public Ingredient getOtherOne(Ingredient second) {
        Ingredient result = null;
        if (this == CHEESE && second == BREAD || this == BREAD && second == CHEESE) {
            result = BOLOGNA;
        } else if (this == BREAD && second == BOLOGNA || this == BOLOGNA && second == BREAD) {
            result = CHEESE;
        } else {
            result = BREAD;
        }
        return result;
    }

    public static Ingredient[] pickTwo() {
        Ingredient[] ingredients = new Ingredient[2];
        Random rand = new Random();
        int randomNum = rand.nextInt(2);
        switch(randomNum) {
            case 0:
                ingredients[0] = CHEESE;
                ingredients[1] = BOLOGNA;
                break;
            case 1:
                ingredients[0] = BREAD;
                ingredients[1] = BOLOGNA;
                break;
            case 2:
                ingredients[0] = BREAD;
                ingredients[1] = CHEESE;
                break;
        }
        return ingredients;
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

    /**
     * Generates a String representation of this enum.
     * 
     * @return a String representation of this enum.
     */
    public String toString() {
        return this.name;
    }
}
