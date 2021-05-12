import java.util.concurrent.Semaphore;

/**
 * Class to represent the Foreman
 *
 * @author Brett Dale
 * @author Tommy Meek
 * @version May, 2021
 */
public class Foreman implements Runnable {
    /** Signal that the Miners want food. */
    private Semaphore hungryMiners;

    /** The print stream used for logging output. */
    private ProtectedOutputStream out;

    /**
     * Constructor for the Foreman.
     */
    public Foreman(Semaphore mutex, ProtectedOutputStream out) {
        this.hungryMiners = mutex;
        this.out = out;
    }

    /**
     * Drops off food at drop off location
     */
    public void drop() {
        Food[] supplies = Food.pickTwo();
        this.out.println("---------------------\nDropping off: " + supplies[0] + "\nDropping off: "
                + supplies[1] + "\n---------------------");
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
            System.exit(0);
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