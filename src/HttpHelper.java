/** HttpHelper
 *  Provides these methods:
 *   retrieve - given a URL, attemptles to retrieve the corresponding webpage
 *   extractLinks - returns all web links fround in a webpage
 *   isImage - checks if the link is an image file
 */
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Helpder class with methods for reading html pages and extracting links; also detects image file
 */
public class HttpHelper {
    private static final Pattern MATCH_HREF = Pattern.compile(
            "<a.*?href\\s*=\\s*['\"](.*?)['\"].*?>(.*?)</a>");

//    public HashMap<String, String> hyperlink = new HashMap<>();
    /**
     * @param urlStr
     * @return String contents of a URL or null in case of an error.
     */


    public String retrieve(String urlStr) {
        try {
            System.err.println("retrieving " + urlStr);
            URL url = new URL(urlStr);
            InputStream in = (InputStream) url.openConnection().getContent();
            StringBuffer result = new StringBuffer();
            while (true) {
                int c = in.read();
                if (c < 0) break;
                result.append((char) c);
            }
            return result.toString();

        } catch(IOException e) {
            System.err.print("Broken links: ");
            if (SharedSpiderData.hyperlink.containsKey(urlStr)) {
                System.err.println(SharedSpiderData.hyperlink.get(urlStr));
            }
            else System.err.println(urlStr);
            return null;
        }
    }
    public List<String> extractLinks(String baseUrl, String html) {
        if (!baseUrl.endsWith("/")) baseUrl += "/";
        List<String> links = new ArrayList<>();
        Matcher m = MATCH_HREF.matcher(html);
        while (m.find()) {
            String url = m.group(1);
            String textURL = m.group(2);
            // if URL is not a word hyperlink
            if (textURL.contains("<"))
                continue;
            if (!(url.startsWith("http://") || url.startsWith("https://"))) {
                url = baseUrl + url;
            }
            // if the link is found in multiple embedded texts
            if (SharedSpiderData.hyperlink.containsKey(url)) {
                String txt = SharedSpiderData.hyperlink.get(url);
                if (!txt.equals(textURL)) SharedSpiderData.hyperlink.put(url, txt + " + " + textURL);
            }
            else SharedSpiderData.hyperlink.put(url, textURL);
            links.add(url);
        }
        return links;

    }
    /**
     * Returns true iff the URL corresponds to an image.
     * @param  url
     * @return true or false
     */
    public boolean isImage(String url) {
        url = url.toLowerCase();
        return (url.endsWith(".png") ||
                url.endsWith(".jpg") ||
                url.endsWith(".gif") ||
                url.endsWith(".jpeg"));
    }
}
