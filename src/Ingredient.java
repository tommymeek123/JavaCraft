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

    /** Signal. */
    private Semaphore signal;

    /**
     * Constructor for the Ingredient enum,
     */
    private Ingredient(String name) {
        this.name = name;
        this.signal = new Semaphore(0);
    }

    public Ingredient[] getOther() {
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

    public void make() {
        this.signal.release();
        System.out.println("MAKING " + this);
    }

    public void take() {
        try {
            this.signal.acquire();
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }
    }

    public String toString() {
        return this.name;
    }
}
