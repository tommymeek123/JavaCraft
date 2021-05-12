import java.io.PrintStream;
import java.util.concurrent.Semaphore;

/**
 * A thread safe output stream guarded by a mutex. 
 *
 * @author Brett Dale
 * @author Tommy Meek
 * @version May, 2021
 */
public class ProtectedOutputStream {
    /** The print stream used for logging output. */
    private PrintStream out;

    /** A mutex that ensures only one thread writes to the PrintStream at a time. */
    private Semaphore outputMutex;

    /**
     * Constructor for the ProtectedOutputStream class
     * 
     * @param out The PrintStream we are writing to.
     */
    public ProtectedOutputStream(PrintStream out) {
        this.out = out;
        this.outputMutex = new Semaphore(1);
    }

    /**
     * Prints safely to this output stream.
     * 
     * @param message Message to be printed.
     */
    public void print(String message) {
        try {
            this.outputMutex.acquire();
            this.out.print(message);
            this.outputMutex.release();
        } catch (InterruptedException ie) {
            System.exit(0);
        }
    }

    /**
     * Prints safely to this output stream with a newline at the end.
     * 
     * @param message Message to be printed.
     */
    public void println(String message) {
        try {
            this.outputMutex.acquire();
            this.out.println(message);
            this.outputMutex.release();
        } catch (InterruptedException ie) {
            System.exit(0);
        }
    }
}