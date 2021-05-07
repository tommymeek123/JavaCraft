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

    private Semaphore messengerSignal;

    public Docks() {
        this.supplies = new ArrayList<>();
        this.bell = new Semaphore(0);
        this.messengerSignal = new Semaphore(0);
    }

    public void drop(Ingredient[] newSupplies) {
        this.supplies.add(newSupplies[0]);
        newSupplies[0].make();
        this.supplies.add(newSupplies[1]);
        newSupplies[1].make();
    }

    public boolean pickUp(Ingredient ingredient) {
        ingredient.take();
        System.out.println(Thread.currentThread().getId() + " IS TRYING TO PICK UP " + ingredient);
        return this.supplies.remove(ingredient);
    }

    public void callForeman() {
        this.bell.release();
    }

    // public void dinnerTime() {
    //     try {
    //         this.bell.acquire();
    //     } catch (InterruptedException ie) {
    //         ie.printStackTrace();
    //     }
    // }

    // public void waitForForeman() {
    //     try {
    //         this.messengerSignal.acquire();
    //     } catch (InterruptedException ie) {
    //         ie.printStackTrace();
    //     }
    // }

    // public void sendMessenger(Ingredient ingredient) {
    //     switch(ingredient) {
    //         case CHEESE:
    //             this.cheeseMessenger
                
    //     }
    // }
}