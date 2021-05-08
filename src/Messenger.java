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

    /** The ingredient this messenger is waiting on. */
    private Ingredient job;

    /** The docks. */
    private Docks docks;

    /** Memory shared between al messengers. */
    private static ArrayList<Ingredient> breakroom = new ArrayList<>();

    private static Semaphore breakroomKey = new Semaphore(1);

    public Messenger(Ingredient guild, Ingredient job, Docks docks) {
        this.guild = guild;
        this.job = job;
        this.docks = docks;
    }

    private boolean storeFood(Ingredient food) {
        boolean result = false;
        try {
            breakroomKey.acquire();
            result = breakroom.add(food);
            breakroomKey.release();
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }
        return result;
    }

    private boolean takeFood(Ingredient food) {
        boolean result = false;
        try {
            breakroomKey.acquire();
            result = breakroom.remove(food);
            breakroomKey.release();
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }
        return result;
    }

    @Override
    public void run() {
        while(true) {
            //System.out.println(this.guild + " (" + Thread.currentThread().getId() + ") WANT " + this.needed[0]);
            if (this.docks.pickUp(this.job)) {
                Ingredient otherNeeded = this.guild.getOtherOne(this.job);
                if (this.takeFood(otherNeeded)) {
                    this.guild.deliver();
                } else {
                    this.storeFood(this.job);
                }
            }
            //System.out.println(this.guild + " (" + Thread.currentThread().getId() + ") WANT " + this.needed[1]);
        }
    }
}