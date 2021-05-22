import java.util.*;
public class WordCounter {
    Map<String, Integer> wordMap;
    public WordCounter() {
        wordMap = new HashMap<String, Integer>();
    }

    /**
     * Increments the count of a particular word
     * @param word to be counted
     */
    public void countWord(String word) {
        Integer currentCount = wordMap.get(word);
        if (currentCount == null) {
            currentCount = 0;
        }
        if (currentCount != null) {
            currentCount++;
        }
        wordMap.put(word, currentCount);
    }
}
