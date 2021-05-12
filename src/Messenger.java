import java.util.concurrent.Semaphore;

/**
 * This class represents our Messengers. They notify their respective miners
 * when food is ready.
 *
 * @author Brett Dale
 * @author Tommy Meek
 * @version May, 2021
 */
public class Messenger implements Runnable {

    /** The miner's guild this messenger works with. */
    private Food guild;

    /** The food this messenger waits on at the docks. */
    private Food priority1;

    /** The food this messenger looks for in the messenger common room. */
    private Food priority2;

    /** 
     * The key to the messenger common room. The common room is where the messengers count their
     * supplies and check for any left behind food orders. It is a small room so only one messenger 
     * is allowed in at a time.
     */
    private Semaphore key;

    /**
     * Constructor for the Messenger class.
     * 
     * @param guild The miner's guild this messenger works with.
     * @param key The key to the messenger common room.
     */
    public Messenger(Food guild, Semaphore key) {
        this.guild = guild;
        this.priority1 = guild.getOthers()[0];
        this.priority2 = guild.getOthers()[1];
        this.key = key;
    }

    /**
     * This is helper method to guide the Messengers so they can get or drop
     * off food for others
     */
    private void coordinateDelivery() {
        try {
            this.key.acquire();
            if (this.priority2.find()) {
                this.guild.deliver();
            } else {
                this.priority1.putInCommonRoom();
                if (this.guild.findInCommonRoom()) {
                    this.guild.dropOff();
                }
            }
            this.key.release();
        } catch (InterruptedException ie) {
            //ie.printStackTrace();
            //System.out.println("Messengers Interrupted");
            System.exit(0);
        }
    }

    /**
     * This method tells our Messengers to try and get food until the thread
     * is interrupted
     */
    @Override
    public void run() {
        while ( ! Thread.currentThread().isInterrupted()) {
            this.priority1.pickUp();
            this.coordinateDelivery();
        }
    }
}