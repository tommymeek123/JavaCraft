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
    private PrintStream out;

    /**
     * Constructor for the FoodCraft class.
     * 
     * @param time The amount of time this simulation will run before terminating.
     * @param out The print stream used for logging output.
     */
    public FoodCraft(float time, PrintStream out) {
        this.time = time;
        this.out = out;
    }

    /**
     * Controller method for this simulation.
     */
    public void go() {
        Semaphore hungryMiners = new Semaphore(0);
        Semaphore outputMutex = new Semaphore(1);
        Semaphore docksKey = new Semaphore(1);
        Thread foreman = new Thread(new Foreman(hungryMiners));
        Thread breadMessenger = new Thread(new Messenger(Food.BREAD, docksKey));
        Thread cheeseMessenger = new Thread(new Messenger(Food.CHEESE, docksKey));
        Thread bolognaMessenger = new Thread(new Messenger(Food.BOLOGNA, docksKey));
        Thread breadMiner = new Thread(new Miner(Food.BREAD, this.out, outputMutex, hungryMiners));
        Thread cheeseMiner = new Thread(new Miner(Food.CHEESE, this.out, outputMutex, hungryMiners));
        Thread bolognaMiner = new Thread(new Miner(Food.BOLOGNA, this.out, outputMutex, hungryMiners));
        foreman.start();
        breadMessenger.start();
        cheeseMessenger.start();
        bolognaMessenger.start();
        breadMiner.start();
        cheeseMiner.start();
        bolognaMiner.start();
    }
}
