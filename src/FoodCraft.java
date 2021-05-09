import java.io.PrintStream;

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
        Docks docks = new Docks(this.out);
        Thread foreman = new Thread(new Foreman(docks));
        Thread breadMessenger = new Thread(new Messenger(Food.BREAD, docks));
        //Thread breadMessenger2 = new Thread(new Messenger(Ingredient.BREAD, Ingredient.BOLOGNA, docks));
        Thread cheeseMessenger = new Thread(new Messenger(Food.CHEESE, docks));
        //Thread cheeseMessenger2 = new Thread(new Messenger(Ingredient.CHEESE, Ingredient.BREAD, docks));
        Thread bolognaMessenger = new Thread(new Messenger(Food.BOLOGNA, docks));
        //Thread bolognaMessenger2 = new Thread(new Messenger(Ingredient.BOLOGNA, Ingredient.CHEESE, docks));
        Thread breadMiner = new Thread(new Miner(Food.BREAD, docks));
        Thread cheeseMiner = new Thread(new Miner(Food.CHEESE, docks));
        Thread bolognaMiner = new Thread(new Miner(Food.BOLOGNA, docks));
        foreman.start();
        breadMessenger.start();
        //breadMessenger2.start();
        cheeseMessenger.start();
        //cheeseMessenger2.start();
        bolognaMessenger.start();
        //bolognaMessenger2.start();
        breadMiner.start();
        cheeseMiner.start();
        bolognaMiner.start();
    }

}
