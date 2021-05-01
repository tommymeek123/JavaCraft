/**
 * 
 *
 * @author Brett Dale
 * @author Tommy Meek
 * @version May, 2021
 */
public class Foreman {
    /** The docks this foreman oversees. */
    private Docks docks;

    /**
     * Constructor for the Foreman.
     */
    public Foreman(Docks docks) {
        this.docks = docks;
    }

    public void drop() {
        Ingredient[] supplies = Ingredient.pickTwo();
        this.docks.drop(supplies);
    }
}