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

    /**
     * Constructor for the Docks.
     * 
     * @param out The print stream used for logging output.
     */
    public Docks(PrintStream out) {
        this.supplies = new ArrayList<>();
        this.hungryMinerAlert = new Semaphore(0);
        this.outputMutex = new Semaphore(1);
    }

    public void drop(Ingredient[] newSupplies) {
        this.supplies.add(newSupplies[0]);
        newSupplies[0].dropOff();
        this.supplies.add(newSupplies[1]);
        newSupplies[1].dropOff();
    }

    public boolean pickUp(Ingredient ingredient) {
        ingredient.pickUp();
        //System.out.println(Thread.currentThread().getId() + " IS TRYING TO PICK UP " + ingredient);
        return this.supplies.remove(ingredient);
    }

    public void callForeman() {
        this.hungryMinerAlert.release();
    }

    public void waitForMiners() {
        try {
            this.hungryMinerAlert.acquire();
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }
    }

    public void log(String logEntry) {
        try {
            this.outputMutex.acquire();
            this.out.println(logEntry);
            this.outputMutex.release();
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }
    }
}