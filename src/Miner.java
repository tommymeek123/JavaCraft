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
    private ProtectedOutputStream out;

    /** A +1 magical horn that, when blown, will summon a level 12 Foreman with a feast. */
    private Semaphore hornOfForemanSummoning;

    /** Maximum sleep time in milliseconds. */
    final static int MAX_SLEEP = 5000;

    /**
     * Constructor for the miner.
     * @param guild The food this miner specializes in mining.
     * @param out The print stream used for logging output.
     * @param horn A +1 magical horn that, when blown, will summon a level 12 Foreman with a feast.
     */
    public Miner(Food guild, ProtectedOutputStream out, Semaphore horn) {
        this.guild = guild;
        this.out = out;
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
     * @param activity String displaying if the Miners are EATING or MAKING sandwiches
     */
    private void sleep(String activity) {
        Random rand = new Random();
        int sleepTime = rand.nextInt(MAX_SLEEP);
        long id = Thread.currentThread().getId();
        try {
            this.out.println(this.guild + " miners:(" + id + ") have started " + activity
                    + " Wait(" + sleepTime + ")");
            Thread.sleep(sleepTime);
            this.out.println(this.guild + " miners:(" + id + ") have finished "
                    + activity + " Wait(" + sleepTime + ")");
        } catch (InterruptedException ie) {
            System.exit(0);
        }
    }

    /**
     * Run method for Miner
     */
    @Override
    public void run() {
        while ( ! Thread.currentThread().isInterrupted()) {
            this.out.println("\n" + this.guild + " miners are waiting for food\n");
            this.guild.receive();
            this.out.println(this.guild + " miners got their food and are calling the foreman");
            this.hornOfForemanSummoning.release();
            this.makeFood();
            this.eatFood();
        }
    }
}