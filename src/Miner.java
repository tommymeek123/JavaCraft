import java.io.PrintStream;
import java.util.Random;
import java.util.concurrent.Semaphore;

/**
 * Class to represent the Miners
 *
 * @author Brett Dale
 * @author Tommy Meek
 * @version May, 2021
 */
public class Miner implements Runnable {
    /** The mining guild with which this miner is affiliated. */
    private Food guild;

    /** The print stream used for logging output. */
    private PrintStream out;

    /** A mutex that ensures only one thread writes to the PrintStream at a time. */
    private Semaphore outputMutex;

    /** A +1 magical horn that, when blown, will summon a level 12 Foreman with a feast. */
    private Semaphore hornOfForemanSummoning;

    /** Maximum sleep time in milliseconds. */
    final static int MAX_SLEEP = 5000;

    /**
     * Constructor for the miner.
     * 
     * @param guild The food this miner specializes in mining.
     * @param out The print stream used for logging output.
     * @param horn A +1 magical horn that, when blown, will summon a level 12 Foreman with a feast.
     * @param outputMutex A mutex that ensures only one thread writes to the PrintStream at a time.
     */
    public Miner(Food guild, PrintStream out, Semaphore outputMutex, Semaphore horn) {
        this.guild = guild;
        this.out = out;
        this.outputMutex = outputMutex;
        this.hornOfForemanSummoning = horn;
    }

    /**
     * Sleeps Miners when making sandwiches
     */
    public void makeFood() {
        this.sleep("MAKING sandwiches");
    }

    /**
     * Sleeps Miners when eating sandwiches
     */
    public void eatFood() {
        this.sleep("EATING sandwiches");
    }

    /**
     * Assigns a random sleep timer then prints message displaying how long
     * they will sleep and what activity they are doing
     * @param activity String displaying if the Miners are EATING or MAKING
     *                 sandwiches
     */
    private void sleep(String activity) {
        Random rand = new Random();
        int sleepTime = rand.nextInt(MAX_SLEEP);
        long id = Thread.currentThread().getId();
        try {
            this.outputMutex.acquire();
            this.out.println(this.guild + " miners:(" + id + ") are " + activity + " Wait(" + sleepTime + ")");
            this.outputMutex.release();
            Thread.sleep(sleepTime);
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }
    }

    /**
     * Run method for Miner
     */
    @Override
    public void run() {
        while ( ! Thread.currentThread().isInterrupted()) {
            this.guild.receive();
            this.hornOfForemanSummoning.release();
            this.makeFood();
            this.eatFood();
        }
    }
}