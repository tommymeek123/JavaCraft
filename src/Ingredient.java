import java.util.Random;

/**
 * An enum representing an ingredient for making a sandwich.
 *
 * @author Brett Dale
 * @author Tommy Meek
 * @version May, 2021
 */
public enum Ingredient {
    BREAD("Bread"), CHEESE("Cheese"), BOLOGNA("Bologna");

    /** The name of the ingredient. */
    private String name;

    /**
     * Constructor for the Ingredient enum,
     */
    private Ingredient(String name) {
        this.name = name;
    }

    public static Ingredient[] pickTwo() {
        Ingredient[] ingredients = new Ingredient[2];
        Random rand = new Random();
        int num = rand.nextInt(2);
        switch(num) {
            case 0:
                ingredients[0] = CHEESE;
                ingredients[1] = BOLOGNA;
                break;
            case 1:
                ingredients[0] = BREAD;
                ingredients[1] = BOLOGNA;
                break;
            case 2:
                ingredients[0] = BREAD;
                ingredients[1] = CHEESE;
                break;
        }
        return ingredients;
    }

    public String toString() {
        return this.name;
    }
}
