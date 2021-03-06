import java.util.*;

public class RunThreadedSpider {
    private static String BEGINNING_URL;
    private static int NUM_THREADS = 3;

    private static SharedSpiderData sharedData = new SharedSpiderData();

    public RunThreadedSpider(String startUrl, int num_threads){
        BEGINNING_URL = startUrl;
        NUM_THREADS = num_threads;
    }
    public static void main(String[] args) {
        System.out.print("Paste in the article URL: ");
        Scanner sc = new Scanner(System.in);
        BEGINNING_URL = sc.nextLine();
       Thread threads[] = new Thread[NUM_THREADS];
        for (int i=0; i < NUM_THREADS; i++) {
            threads[i] = startThread(i);
            System.out.println("Thread " + i + " started");
        }

        // wait until all threads complete
        for (int i=0; i < NUM_THREADS; i++ ) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        /**
         * Display reading speed of each site
         */
        HashSet<String> displayReadingSpeed = new HashSet<>();
        for (String url: SharedSpiderData.hyperlink.keySet()) {
            ReadingSpeed speedCalculator = new ReadingSpeed();
            String article = speedCalculator.getText(url);
            String txt = speedCalculator.article.get(article);
            String entry = String.format("%100s: %2s minutes", txt,  Integer.toString(speedCalculator.readingSpeed(article)));
            if (txt != null) displayReadingSpeed.add(entry);
        }
        for (String res: displayReadingSpeed) {
            System.out.println(res);
        }
    }
    public static Thread startThread(int threadNum) {
        Thread t;
        if (threadNum != 0) {
            t = new Thread(new ConcurrentSpider(sharedData));
        } else t = new Thread(new ConcurrentSpider(sharedData, BEGINNING_URL));
        t.start();
        return t;
    }
}
