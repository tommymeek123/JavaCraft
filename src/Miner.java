import java.io.PrintStream;
import java.util.Random;

/**
 * 
 *
 * @author Brett Dale
 * @author Tommy Meek
 * @version May, 2021
 */
public class Miner implements Runnable {
    /**  The ingredient this miner mines.  */
    private Ingredient ingredient;

    /** The ingredients these miners need to make food. */
    private Ingredient[] needed;

    /** Where to log this miner's progress. */
    private PrintStream out;

    /** The mining guild with which this miner is affiliated. */
    private String guild;

    /** The docks. */
    private Docks docks;

    /** Maximum sleep time in milliseconds. */
    final static int MAX_SLEEP = 5000;

    /**
     * Constructor for the miner.
     * 
     * @param ingredient The ingredient this miner specializes in mining.
     * @param out The print stream used for logging output.
     */
    public Miner(Ingredient ingredient, PrintStream out, Docks docks) {
        this.ingredient = ingredient;
        this.needed = ingredient.getOther();
        this.guild = ingredient + " miners";
        this.out = out;
        this.docks = docks;
    }

    public void makeFood() {
        this.sleep("MAKING sandwiches");
    }

    public void eatFood() {
        this.sleep("EATING sandwiches");
    }

    private void sleep(String activity) {
        Random rand = new Random();
        int sleepTime = rand.nextInt(MAX_SLEEP);
        long id = Thread.currentThread().getId();
        this.out.println(this.guild + ":(" + id + ") are " + activity + " Wait(" + sleepTime + ")");
        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException ie) {
            ie.printStackTrace();
            System.exit(1);
        }
    }

    @Override
    public void run() {
        this.docks.pickUp(this.needed[0]);
        this.docks.pickUp(this.needed[1]);
        this.makeFood();
        this.eatFood();
        this.docks.callForeman();
    }
}