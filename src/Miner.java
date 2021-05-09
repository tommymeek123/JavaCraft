import java.util.Random;

/**
 * 
 *
 * @author Brett Dale
 * @author Tommy Meek
 * @version May, 2021
 */
public class Miner implements Runnable {
    /** The mining guild with which this miner is affiliated. */
    private Food guild;

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
    public Miner(Food guild, Docks docks) {
        this.guild = guild;
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
        this.docks.log(this.guild + " miners:(" + id + ") are " + activity + " Wait(" + sleepTime + ")");
        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException ie) {
            ie.printStackTrace();
            System.exit(1);
        }
    }

    @Override
    public void run() {
        //System.out.println(Thread.currentThread().getId() + ": " + this.guild + " miners");
        while (true) {
            this.guild.receive();
            this.docks.callForeman();
            this.makeFood();
            this.eatFood();
        }
        // this.docks.pickUp(this.needed[0]);
        // System.out.println(this.guild + " (" + Thread.currentThread().getId() + ") WANT " + this.needed[1]);
        // this.docks.pickUp(this.needed[1]);

    }
}