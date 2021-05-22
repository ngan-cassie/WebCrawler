import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class SharedSpiderData {
    private static final int QUEUE_SIZE = 8000;

    /**
     * Urls waiting to be scraped
     */
    private BlockingQueue<String> work =  new ArrayBlockingQueue<String>(QUEUE_SIZE);

    /**
     * Urls that have already been retrieved
     */
    private Queue<String> finished = new ConcurrentLinkedQueue<>();

    /**
     * Urls embedded with links and text display
     */
    public static HashMap<String, String> hyperlink = new HashMap<>();

    /**
     * @return URLS to work on
     */
    public BlockingQueue<String> getWork() {
        return work;
    }

    /**
     * finished data
     */
    public Queue<String> getFinished() {
        return finished;
    }
}
