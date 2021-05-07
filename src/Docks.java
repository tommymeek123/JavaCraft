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
    /** A place to store the foodstuffs. */
    private ArrayList<Ingredient> supplies;

    /** Dinner bell. */
    private Semaphore bell;

    public Docks() {
        this.supplies = new ArrayList<>();
        this.bell = new Semaphore(1);
    }

    public void drop(Ingredient[] newSupplies) {
        this.supplies.add(newSupplies[0]);
        newSupplies[0].make();
        this.supplies.add(newSupplies[1]);
        newSupplies[1].make();
    }

    public void pickUp(Ingredient ingredient) {
        ingredient.take();
        this.supplies.remove(ingredient);
    }

    public void callForeman() {
        this.bell.release();
    }

    public void dinnerTime() {
        try {
            this.bell.acquire();
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }
    }
}