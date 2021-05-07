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

    private Messenger cheeseMessenger;

    private Messenger breadMessenger;

    private Messenger bolognaMessenger;

    public Docks() {
        this.supplies = new ArrayList<>();
        this.cheeseMessenger = Messenger.CHEESE;
        this.breadMessenger = Messenger.BREAD;
        this.bolognaMessenger = Messenger.BOLOGNA;
        this.bell = new Semaphore(0);
        this.messengerSignal = new Semaphore(0);
    }

    public void drop(Ingredient[] newSupplies) {
        this.supplies.add(newSupplies[0]);
        newSupplies[0].make();
        this.supplies.add(newSupplies[1]);
        newSupplies[1].make();
    }

    public void pickUp(Ingredient ingredient) {
        ingredient.take();
        System.out.println(Thread.currentThread().getId() + " IS PICKING UP " + ingredient);
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