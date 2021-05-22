
/**
 * The spider that crawls the webpage
 */
public class ConcurrentSpider implements Runnable{
    private String beginningURL;

    /**
     * Helps download and parse the webpages
     */
    private HttpHelper helper = new HttpHelper();
    private int maxUrls = 6;

    // To continue the 'pattern' from RunThreadedSpider, have this shared data
    // be passed into the constructor from the single place where it was originally created.
    private SharedSpiderData sharedData;

    public HttpHelper getHelper() {
        return helper;
    }

    /**
     * Create a new spider with access to the shared data by passing it a reference.
     * This constructor could be called many times.
     * @param data
     */
    public ConcurrentSpider(SharedSpiderData data) {
        sharedData = data;
    }
    /**
     * Create a new spider with access to the shared data by passing it a reference.
     * Also give it the starting point to begin scraping.  This constructor should be called
     * once.
     * @param data
     * @param startURL
     */
    public ConcurrentSpider(SharedSpiderData data, String startURL) {
        sharedData = data;
        beginningURL = startURL;
    }

    /**
     * method will be executed with "start()" a thread
     * displaying the thread behavior
     */
    private int urlCount = 0;
    public void run() {
        if (beginningURL != null) {
            try {
                sharedData.getWork().put(beginningURL);
            } catch (InterruptedException e) {
                System.out.println("Error putting data into work queue");
                e.printStackTrace();
            }
        }
        while (!sharedData.getWork().isEmpty() && urlCount <= maxUrls) {
            String url = sharedData.getWork().remove();
            if (!sharedData.getWork().contains(url)) {
                processPage(url);
                sharedData.getFinished().add(url);
            }
            urlCount++;
        }
    }
    public void processPage(String url) {
        String html = helper.retrieve(url);
        if (html == null) return;
        for (String url2: helper.extractLinks(url, html)) {
            if (!helper.isImage(url2)) {
                try {
                    sharedData.getWork().put(url2);
                } catch (InterruptedException e) {
                    System.out.println("Error putting data into work queue");
                }
            }
        }
    }
}
