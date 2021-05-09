import java.io.PrintStream;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;

/**
 * 
 *
 * @author Brett Dale
 * @author Tommy Meek
 * @version May, 2021
 */
public class Docks {
    /** The print stream used for logging output. */
    private PrintStream out;

    /** A place to store the foodstuffs. */
    private ArrayList<Ingredient> supplies;

    /** The foreman waits on this signal to drop food off at the docks. */
    private Semaphore hungryMinerAlert;

    private Semaphore outputMutex;

    private Semaphore supplyKey;

    /**
     * Constructor for the Docks.
     * 
     * @param out The print stream used for logging output.
     */
    public Docks(PrintStream out) {
        this.out = out;
        this.supplies = new ArrayList<>();
        this.hungryMinerAlert = new Semaphore(0);
        this.outputMutex = new Semaphore(1);
        this.supplyKey = new Semaphore(1);
    }

    public void drop(Ingredient[] newSupplies) {
        try {
            System.out.println(Thread.currentThread().getId() + " waits for the supply key in Docks.drop()");
            this.supplyKey.acquire();
            System.out.println(Thread.currentThread().getId() + " just got the supply key in Docks.drop()");
            this.supplies.add(newSupplies[0]);
            newSupplies[0].dropOff();
            this.supplies.add(newSupplies[1]);
            newSupplies[1].dropOff();
            System.out.println(Thread.currentThread().getId() + " is about to send a release signal in Docks.drop()");
            this.supplyKey.release();
            System.out.println(Thread.currentThread().getId() + " just sent a release signal in Docks.drop()");
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }
    }

    public boolean pickUp(Ingredient ingredient) {
        boolean result = false;
        ingredient.pickUp();
        try {
            System.out.println(Thread.currentThread().getId() + " waits for the supply key in Docks.pickUp()");
            this.supplyKey.acquire();
            System.out.println(Thread.currentThread().getId() + " just got the supply key in Docks.pickUp()");
            result = this.supplies.remove(ingredient);
            if (result) {
                System.out.println(Thread.currentThread().getId() + " removed " + ingredient + " from the supply chest in Docks.pickUp()");
            } else {
                System.out.println(Thread.currentThread().getId() + " failed to remove " + ingredient + " from the supply chest in Docks.pickUp()");
            }
            System.out.println(Thread.currentThread().getId() + " is about to release the supply key in Docks.pickUp()");
            this.supplyKey.release();
            System.out.println(Thread.currentThread().getId() + " just released the supply key in Docks.pickUp()");
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }
        return result;
    }

    public void callForeman() {
        System.out.println(Thread.currentThread().getId() + " is about to summon the foreman in Docks.callForeman()");
        this.hungryMinerAlert.release();
        System.out.println(Thread.currentThread().getId() + " just summoned the foreman in Docks.callForeman()");
    }

    public void waitForMiners() {
        try {
            System.out.println(Thread.currentThread().getId() + " blocks in Docks.waitForMiners()");
            this.hungryMinerAlert.acquire();
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }
        System.out.println(Thread.currentThread().getId() + " is finishing Docks.waitForMiners()");
    }

    public void log(String logEntry) {
        try {
            System.out.println(Thread.currentThread().getId() + " blocks in Docks.log()");
            this.outputMutex.acquire();
            System.out.println(Thread.currentThread().getId() + " finished blocking in Docks.log()");
            this.out.println(logEntry);
            System.out.println(Thread.currentThread().getId() + " is about to release in Docks.log()");
            this.outputMutex.release();
            System.out.println(Thread.currentThread().getId() + " just released in Docks.log()");
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }
    }
}