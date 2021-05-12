import java.io.PrintStream;
import java.util.concurrent.Semaphore;

/**
 * The controller class for the FoodCraft mining operation.
 *
 * @author Brett Dale
 * @author Tommy Meek
 * @version May, 2021
 */
public class FoodCraft {
    /** 
     * The amount of time this simulation will run before terminating. A non-positive value 
     * indicates it will run indefinitely.
     */
    private float time;

    /** The print stream used for logging output. */
    private ProtectedOutputStream out;

    /**
     * Constructor for the FoodCraft class.
     * 
     * @param time The amount of time this simulation will run before terminating.
     * @param out The print stream used for logging output.
     */
    public FoodCraft(float time, PrintStream out) {
        this.time = time;
        this.out = new ProtectedOutputStream(out);
    }

    /**
     * Controller method for this simulation.
     */
    public void go() {
        Semaphore hungryMiners = new Semaphore(0);
        Semaphore docksKey = new Semaphore(1);
        Thread foreman = new Thread(new Foreman(hungryMiners, this.out));
        Thread breadMessenger = new Thread(new Messenger(Food.BREAD, docksKey));
        Thread cheeseMessenger = new Thread(new Messenger(Food.CHEESE, docksKey));
        Thread bolognaMessenger = new Thread(new Messenger(Food.BOLOGNA, docksKey));
        Thread breadMiner = new Thread(new Miner(Food.BREAD, this.out, hungryMiners));
        Thread cheeseMiner = new Thread(new Miner(Food.CHEESE, this.out, hungryMiners));
        Thread bolognaMiner = new Thread(new Miner(Food.BOLOGNA, this.out, hungryMiners));

        this.out.println("Food distribution will continue for: " + this.time + " seconds");

        // Start all threads.
        foreman.start();
        breadMessenger.start();
        cheeseMessenger.start();
        bolognaMessenger.start();
        breadMiner.start();
        cheeseMiner.start();
        bolognaMiner.start();

        if (this.time > 0) {
            // wait the amount of time the user specified and terminate the threads.
            try {
                //multiplying time by 1000 because sleep() wants milliseconds
                Thread.sleep((long) (this.time * 1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.exit(1);
            }
            this.out.println("Foreman is stopping.");
            foreman.interrupt();
            this.out.println("Bread messenger is stopping.");
            breadMessenger.interrupt();
            this.out.println("Cheese messenger is stopping.");
            cheeseMessenger.interrupt();
            this.out.println("Bologna messenger is stopping.");
            bolognaMessenger.interrupt();
            this.out.println("Bread miner is stopping.");
            breadMiner.interrupt();
            this.out.println("Cheese miner is stopping.");
            cheeseMiner.interrupt();
            this.out.println("Bologna miner is stopping.");
            bolognaMiner.interrupt();
        }
    }
}
