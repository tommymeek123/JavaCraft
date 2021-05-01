import java.io.FileNotFoundException;
import java.io.PrintStream;

/**
 * A driver to start the FoodCraft mining operation.
 *
 * @author Brett Dale
 * @author Tommy Meek
 * @version May, 2021
 */
public class Driver {
    /**
     * Entry point into the program.
     *
     * @param args Command line arguments to the program. There are exactly 2. <br>
     *             args[0] - The amount of time before the driver ends the distribution operation 
     *                       If the amount is 0 or negative, the operation continues until the main 
     *                       thread is killed (ctrl-C), which in turn kills all the child threads. 
     *                       <br>
     *             args[1] - 'T' or 'F'. 'T' indicates to append output from the threads to a single 
     *                       file, 'F' means all output should be printed to the screen. If you are 
     *                       logging output to a file, then the time until the operation is stopped 
     *                       must be a positive value. Call the output file <em>log.txt</em>. <b>All 
     *                       threads are writing to the same file, concurrently.</b>
     */
    public static void main(String[] args) {
        validateArgs(args);
        final String LOG_FILE = "log.txt";
        float time = Float.parseFloat(args[0]);
        PrintStream out = null;
        if (args[1].toLowerCase().equals("t")) {
            try {
                out = new PrintStream(LOG_FILE);
            } catch (FileNotFoundException fnfe) {
                fnfe.printStackTrace();
                System.exit(1);
            }
        } else {
            out = System.out;
        }
        FoodCraft sim = new FoodCraft(time, out);
        sim.go();
    }

    private static void validateArgs(String[] args) {
        if (args.length == 0 || args.length > 2) {
            usage();
        }
        float time = 0;
        try {
            time = Float.parseFloat(args[0]);
        } catch (NumberFormatException nfe) {
            usage();
        }
        if (!(args[1].toLowerCase().equals("t") || args[1].toLowerCase().equals("f"))) {
            usage();
        }
        if (args[1].toLowerCase() == "t" && time <= 0) {
            System.err.println("Error. Cannot log to file infinitely.");
            System.exit(1);
        }
    }

    private static void usage() {
        System.err.println("Usage: java Driver [runtime] [T|F]");
        System.exit(1);
    }
}