import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.*;

import java.io.IOException;

import java.util.*;

public class ReadingSpeed {


    public static HashMap<String, String> article = new HashMap<>();

    /**
     *
     * @param url
     * @return article content of an URL
     */
    public static String getText(String url) {
        try {
                Document doc = Jsoup.connect(url).get();
                Elements paragraphs = doc.select("p");
                String articleText = paragraphs.text();

                String title = doc.select("title").text();
                article.put(articleText, title);
            return articleText;
        } catch (IOException e) {
            System.err.println("URL not a website");
        }
        return "no";
    }

    /**
     *
     * @param articleText
     * @return reading speed (wpm) of an average adult
     */
    public int readingSpeed(String articleText) {
        int wpm = 225;
        int words = articleText.replace(" ", "").length();
        return words/wpm;
    }

}
