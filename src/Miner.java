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

    /** Used to randomize the wait periods. */
    private Random rand;

    /** Where to log this miner's progress. */
    private PrintStream out;

    /** The mining guild with which this miner is affiliated. */
    private String guild;

    /** Maximum sleep time in miliseconds. */
    final static int MAX_SLEEP = 5000;

    /**
     * Constructor for the miner.
     */
    public Miner(Ingredient ingredient, PrintStream out) {
        this.ingredient = ingredient;
        this.guild = ingredient + " miners";
        this.rand = new Random();
        this.out = out;
    }

    public void makeFood() {
        int sleepTime = this.rand.nextInt(MAX_SLEEP);
        out.println(this.guild + ":(" + Thread.currentThread().getId() + ") are MAKING sandwiches. Wait(" + sleepTime + ")");
        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException ie) {
            ie.printStackTrace();
            System.exit(1);
        }
        
    }

    public void eatFood() {

    }
}