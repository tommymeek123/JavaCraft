import java.io.PrintStream;
import java.util.Random;

/**
 * 
 *
 * @author Brett Dale
 * @author Tommy Meek
 * @version May, 2021
 */
public class Miner {
    /**  The ingredient this miner mines.  */
    private Ingredient ingredient;

    /** Where to log this miner's progress. */
    private PrintStream out;

    /** The mining guild with which this miner is affiliated. */
    private String guild;

    /** Maximum sleep time in milliseconds. */
    final static int MAX_SLEEP = 5000;

    /**
     * Constructor for the miner.
     * 
     * @param ingredient The ingredient this miner specializes in mining.
     * @param out The print stream used for logging output.
     */
    public Miner(Ingredient ingredient, PrintStream out) {
        this.ingredient = ingredient;
        this.guild = ingredient + " miners";
        this.out = out;
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
}