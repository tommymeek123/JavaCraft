import java.util.concurrent.Semaphore;

/**
 * 
 *
 * @author Brett Dale
 * @author Tommy Meek
 * @version May, 2021
 */
public class Foreman implements Runnable {
    /** The docks this foreman oversees. */
    private Docks docks;

    /**
     * Constructor for the Foreman.
     */
    public Foreman(Docks docks) {
        this.docks = docks;
    }

    public void drop() {
        Ingredient[] supplies = Ingredient.pickTwo();
        // System.out.println("FOREMAN PICKED " + supplies[0]);
        // System.out.println("FOREMAN PICKED " + supplies[1]);
        this.docks.drop(supplies);
    }

    @Override
    public void run() {
        while (true) {
            this.drop();
            this.docks.waitForMiners();
        }
    }
}