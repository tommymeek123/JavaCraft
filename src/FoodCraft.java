import java.io.PrintStream;

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
     * Controller method for the simulation.
     */
    public void go() {
        Docks docks = new Docks();
        Foreman foreman = new Foreman(docks);
        Miner breadMiner = new Miner(Ingredient.BREAD, this.out);
        Miner cheeseMiner = new Miner(Ingredient.CHEESE, this.out);
        Miner bolognaMiner = new Miner(Ingredient.BOLOGNA, this.out);
        cheeseMiner.makeFood();
    }

}
