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

    public void drop() {
        Food[] supplies = Food.pickTwo();
        supplies[0].dropOff();
        supplies[1].dropOff();
    }

    private void listenForMiners() {
        try {
            this.hungryMiners.acquire();
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (true) {
            this.drop();
            this.listenForMiners();
        }
    }
}