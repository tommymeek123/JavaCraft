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
        Thread foreman = new Thread(new Foreman(hungryMiners));
        Thread breadMessenger = new Thread(new Messenger(Food.BREAD, docksKey));
        Thread cheeseMessenger = new Thread(new Messenger(Food.CHEESE, docksKey));
        Thread bolognaMessenger = new Thread(new Messenger(Food.BOLOGNA, docksKey));
        Thread breadMiner = new Thread(new Miner(Food.BREAD, this.out, hungryMiners));
        Thread cheeseMiner = new Thread(new Miner(Food.CHEESE, this.out, hungryMiners));
        Thread bolognaMiner = new Thread(new Miner(Food.BOLOGNA, this.out, hungryMiners));

        foreman.start();
        breadMessenger.start();
        cheeseMessenger.start();
        bolognaMessenger.start();
        breadMiner.start();
        cheeseMiner.start();
        bolognaMiner.start();

        try {
            System.out.println("Main Thread gets slept");
            //multiplying time by 1000 because sleep() wants milliseconds
            Thread.sleep((long) (this.time * 1000));
            System.out.println("Main Thread is awake");
        } catch (InterruptedException e) {
            System.out.println("Main thread woke up");
            e.printStackTrace();
        }

        foreman.interrupt();
        breadMessenger.interrupt();
        cheeseMessenger.interrupt();
        bolognaMessenger.interrupt();
        breadMiner.interrupt();
        cheeseMiner.interrupt();
        bolognaMiner.interrupt();
    }
}
