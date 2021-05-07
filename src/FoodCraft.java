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
        Docks docks = new Docks();
        Thread foreman = new Thread(new Foreman(docks));
        Thread breadMiner = new Thread(new Miner(Ingredient.BREAD, this.out, docks));
        Thread cheeseMiner = new Thread(new Miner(Ingredient.CHEESE, this.out, docks));
        Thread bolognaMiner = new Thread(new Miner(Ingredient.BOLOGNA, this.out, docks));
        foreman.start();
        breadMiner.start();
        cheeseMiner.start();
        bolognaMiner.start();
    }

}
