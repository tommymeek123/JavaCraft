import java.util.concurrent.Semaphore;

/**
 * 
 *
 * @author Brett Dale
 * @author Tommy Meek
 * @version May, 2021
 */
public enum Messenger implements Runnable {

    BREAD(Ingredient.BREAD), CHEESE(Ingredient.CHEESE), BOLOGNA(Ingredient.BOLOGNA);

    /** The miner's guild this messenger works with. */
    private Ingredient job;

    /**  */
    private Semaphore signal;

    private Messenger(Ingredient job) {
        this.job = job;
        this.signal = new Semaphore(0);
    }

    @Override
    public void run() {
        //this.job.take();
    }
}