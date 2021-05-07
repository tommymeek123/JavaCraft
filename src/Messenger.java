import java.util.ArrayList;
import java.util.concurrent.Semaphore;

/**
 * 
 *
 * @author Brett Dale
 * @author Tommy Meek
 * @version May, 2021
 */
public class Messenger implements Runnable {

    /** The miner's guild this messenger works with. */
    private Ingredient guild;

    /** The ingredients this messenger is waiting on. */
    private Ingredient[] needed;

    private Semaphore dinnerBell;

    /** The docks. */
    private Docks docks;

    /**  */
    private static ArrayList<Ingredient> sharedFood = new ArrayList<>();

    public Messenger(Ingredient guild, Docks docks) {
        this.guild = guild;
        this.docks = docks;
        this.dinnerBell = new Semaphore(0);
    }

    public void waitForFood() {
        try {
            this.dinnerBell.acquire();
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }
    }

    @Override
    public void run() {
        System.out.println(this.guild + " (" + Thread.currentThread().getId() + ") WANT " + this.needed[0]);
        if (this.docks.pickUp(this.needed[0])) {
            if (sharedFood.remove(this.needed[1])) {
                this.dinnerBell.release();
            } else {
                sharedFood.add(needed[0]);
            }
        }
        System.out.println(this.guild + " (" + Thread.currentThread().getId() + ") WANT " + this.needed[1]);
        this.docks.pickUp(this.needed[1]);
    }
}