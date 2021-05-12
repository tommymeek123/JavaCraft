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
    private Semaphore hungryMiners;

    /**
     * Constructor for the Foreman.
     */
    public Foreman(Semaphore mutex) {
        this.hungryMiners = mutex;
    }

    /**
     * Drops off food at drop off location
     */
    public void drop() {
        Food[] supplies = Food.pickTwo();
        supplies[0].dropOff();
        supplies[1].dropOff();
    }

    /**
     * Method to listen for the Miners
     */
    private void listenForMiners() {
        try {
            this.hungryMiners.acquire();
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }
    }

    /**
     * Runs the Foreman
     */
    @Override
    public void run() {
        while ( ! Thread.currentThread().isInterrupted()) {
            this.drop();
            this.listenForMiners();
        }
    }
}