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
        Food[] supplies = Food.pickTwo();
        //System.out.println("\nForeman picks " + supplies[0] + " and " + supplies[1]);
        this.docks.drop(supplies);
    }

    @Override
    public void run() {
        //System.out.println(Thread.currentThread().getId() + ": Foreman");
        while (true) {
            this.drop();
            this.docks.waitForMiners();
        }
    }
}