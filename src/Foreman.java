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

    private Semaphore foodRequest;

    /**
     * Constructor for the Foreman.
     */
    public Foreman(Docks docks) {
        this.docks = docks;
        this.foodRequest = new Semaphore(0);
    }

    public void drop() {
        Ingredient[] supplies = Ingredient.pickTwo();
        System.out.println("FOREMAN PICKED " + supplies[0]);
        System.out.println("FOREMAN PICKED " + supplies[1]);
        this.docks.drop(supplies);
    }

    public void getFood() {
        this.foodRequest.release();
    }

    @Override
    public void run() {
        while (true) {
            this.drop();
            try {
                this.foodRequest.acquire();
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
        }
    }
}