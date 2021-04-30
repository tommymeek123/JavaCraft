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
        System.out.println("You must construct additional Pylons.");
    }
}