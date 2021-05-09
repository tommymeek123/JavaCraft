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
    private Food guild;

    /** The ingredient this messenger is waiting on from the foreman. */
    private Food priority1;

    /** The docks. */
    private Docks docks;

    /** The other ingredient this messenger is waiting on. */
    private Food priority2;

    // /** Memory shared between all messengers. */
    // private static ArrayList<Ingredient> breakroom = new ArrayList<>();

    // private static Semaphore breakroomKey = new Semaphore(1);

    public Messenger(Food guild, Docks docks) {
        this.guild = guild;
        this.priority1 = guild.getOthers()[0];
        this.priority2 = guild.getOthers()[1];
        this.docks = docks;
    }

    @Override
    public void run() {
        //System.out.println(Thread.currentThread().getId() + ": " + this.guild + " messenger wants " + this.priority1);
        while(true) {
            this.priority1.pickUp();
            if (this.priority2.checkBreakroom()) {
                this.priority2.getFromBreakroom();
                this.guild.deliver();
            } else {
                this.priority1.putInBreakroom();
            }
        }
    }
    
    // private boolean checkForSecondFood() {
    //     return this.guild.checkBreakroom();
    // }

    // private boolean storeFood(Ingredient food) {
    //     boolean result = false;
    //     try {
    //         breakroomKey.acquire();
    //         result = breakroom.add(food);
    //         breakroomKey.release();
    //     } catch (InterruptedException ie) {
    //         ie.printStackTrace();
    //     }
    //     return result;
    // }

    // private boolean takeFood(Ingredient food) {
    //     boolean result = false;
    //     try {
    //         System.out.println(Thread.currentThread().getId() + " blocks waiting for the breakroom key in Messenger.takeFood()");
    //         breakroomKey.acquire();
    //         System.out.println(Thread.currentThread().getId() + " will try removing " + food + " from the break room in Messenger.takeFood()");
    //         result = breakroom.remove(food);
    //         if (result) {
    //             System.out.println(Thread.currentThread().getId() + " removed " + food + " from the breakroom in Messenger.takeFood()");
    //         } else {
    //             System.out.println(Thread.currentThread().getId() + " failed to remove " + food + " from the breakroom in Messenger.takeFood()");
    //         }
    //         System.out.println(Thread.currentThread().getId() + " is about to release the breakroom key in Messenger.takeFood()");
    //         breakroomKey.release();
    //         System.out.println(Thread.currentThread().getId() + " just released the breakroom key in Messenger.takeFood()");
    //     } catch (InterruptedException ie) {
    //         ie.printStackTrace();
    //     }
    //     return result;
    // }

    // private boolean enterBreakroom() {
    //     boolean result = false;
    //     try {
    //         System.out.println(Thread.currentThread().getId() + " blocks waiting for the breakroom key in Messenger.enterBreakroom()");
    //         breakroomKey.acquire();
    //         System.out.println(Thread.currentThread().getId() + " will try removing " + this.priority2 + " from the break room in Messenger.enterBreakroom()");
    //         if (breakroom.remove(this.priority2)) {
    //             System.out.println(Thread.currentThread().getId() + " removed " + this.priority2 + " from the breakroom in Messenger.enterBreakroom()");
    //             result = true;
    //         } else {
    //             System.out.println(Thread.currentThread().getId() + " could not find " + this.priority2 + " in the breakroom in Messenger.enterBreakroom()");
    //             breakroom.add(this.priority1);
    //             System.out.println(Thread.currentThread().getId() + " added " + this.priority1 + " to the breakroom in Messenger.enterBreakroom()");
    //         }
    //         System.out.println(Thread.currentThread().getId() + " is about to release the breakroom key in Messenger.enterBreakroom()");
    //         breakroomKey.release();
    //         System.out.println(Thread.currentThread().getId() + " just released the breakroom key in Messenger.enterBreakroom()");
    //     } catch (InterruptedException ie) {
    //         ie.printStackTrace();
    //     }
    //     return result;
    // }

    // @Override
    // public void run() {
    //     System.out.println(Thread.currentThread().getId() + ": " + this.guild + " messenger wants " + this.job);
    //     while(true) {
    //         if (this.docks.pickUp(this.job)) {
    //             System.out.println(Thread.currentThread().getId() + " successfully picked up " + this.job + " in Messenger.run()");
    //             if (this.enterBreakroom()) {
    //                 this.guild.deliver();
    //             }
    //         }
    //     }
    // }

    // @Override
    // public void run() {
    //     System.out.println(Thread.currentThread().getId() + ": " + this.guild + " messenger wants " + this.job);
    //     while(true) {
    //         if (this.docks.pickUp(this.job)) {
    //             System.out.println(Thread.currentThread().getId() + " successfully picked up " + this.job + " in Messenger.run()");
    //             if (this.takeFood(this.otherNeeded)) {
    //                 System.out.println(Thread.currentThread().getId() + " successfully took " + otherNeeded + " from the breakroom in Messenger.run() and will make his delivery");
    //                 this.guild.deliver();
    //             } else {
    //                 System.out.println(Thread.currentThread().getId() + " didn't find any " + otherNeeded + " in the breakroom in Messenger.run() so he put his " + this.job + " there");
    //                 this.storeFood(this.job);
    //             }
    //         }
    //     }
    // }

}