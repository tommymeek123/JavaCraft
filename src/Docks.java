/**
 * 
 *
 * @author Brett Dale
 * @author Tommy Meek
 * @version May, 2021
 */
public class Docks {
    /** A place to store the foodstuffs. */
    private Ingredient[] supplies;

    public Docks() {
        this.supplies = new Ingredient[2];
    }

    public void drop(Ingredient[] supplies) {
        this.supplies = supplies;
    }
}