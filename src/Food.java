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

    /** The name of the ingredient. */
    private String name;

    /** Signal that the foreman sends to the messengers to indicate food has been dropped off. */
    private Semaphore foremanToMessengerSignal;

    /** Signal that the messengers send to the miners to indicate food has been delivered. */
    private Semaphore messengerToMinerSignal;

    private Semaphore breakroom;

    /**
     * Constructor for the Ingredient enum,
     */
    private Food(String name) {
        this.name = name;
        this.foremanToMessengerSignal = new Semaphore(0);
        this.messengerToMinerSignal = new Semaphore(0);
        this.breakroom = new Semaphore(0);
    }

    public Food[] getOthers() {
        Food[] ingredients = new Food[2];
        switch(this) {
            case BREAD:
                ingredients[0] = CHEESE;
                ingredients[1] = BOLOGNA;
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

    public Food getOtherOne(Food second) {
        Food result = null;
        if (this == CHEESE && second == BREAD || this == BREAD && second == CHEESE) {
            result = BOLOGNA;
        } else if (this == BREAD && second == BOLOGNA || this == BOLOGNA && second == BREAD) {
            result = CHEESE;
        } else {
            result = BREAD;
        }
        return result;
    }

    public static Food[] pickTwo() {
        Food[] ingredients = new Food[2];
        Random rand = new Random();
        int randomNum = rand.nextInt(3);
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
        //System.out.println(Thread.currentThread().getId() + " is about to send a release signal in Ingredient.dropOff()");
        this.foremanToMessengerSignal.release(1);
        //System.out.println(Thread.currentThread().getId() + " just sent a release signal in Ingredient.dropOff()");
    }

    public void pickUp() {
        try {
            //System.out.println(Thread.currentThread().getId() + " blocks in Ingredient.pickUp()");
            this.foremanToMessengerSignal.acquire();
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }
        //System.out.println(Thread.currentThread().getId() + " is finishing Ingredient.pickUp()");
    }

    public void deliver() {
        //System.out.println(Thread.currentThread().getId() + " is about to send a release signal in Ingredient.deliver()");
        this.messengerToMinerSignal.release();
        //System.out.println(Thread.currentThread().getId() + " just sent a release signal in Ingredient.deliver()");
    }

    public void receive() {
        try {
            //System.out.println(Thread.currentThread().getId() + " blocks in Ingredient.receive()");
            this.messengerToMinerSignal.acquire();
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }
        //System.out.println(Thread.currentThread().getId() + " is finishing Ingredient.receive()");
    }

    public boolean checkBreakroom() {
        return this.breakroom.tryAcquire();
    }

    public void putInBreakroom() {
        this.breakroom.release();
    }

    public void getFromBreakroom() {
        try {
            this.breakroom.acquire();
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
