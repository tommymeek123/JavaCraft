import java.util.Random;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * 
 *
 * @author Brett Dale
 * @author Tommy Meek
 * @version May, 2021
 */
public class Foreman {
    /** The ingredients available to the forman. */
    private String[] ingredients;

    public Foreman() {
        this.ingredients = new String[] { "cheese", "bread", "bologna" };
    }

    public String[] chooseIngredients() {
        Random rand = new Random();
        int badFoodNum = rand.nextInt(this.ingredients.length);
        System.out.print("BAD FOOD NUM: ");
        System.out.println(badFoodNum);
        String[] crate = new String[this.ingredients.length - 1];
        // int cnt = 0;
        // for (int i = 0; i < this.ingredients.length; i++) {
        //     if (i != badFoodNum) {
        //         crate[cnt] = this.ingredients[i];
        //         cnt++;
        //     }
        // }
        ArrayList<String> manifest = new ArrayList<>(Arrays.asList(this.ingredients));
        manifest.remove(badFoodNum);
        for (int i = 0; i < manifest.size(); i++) {
            crate[i] = manifest.get(i);
        }
        //return manifest.toArray();
        return crate;
    }
}