package strings;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * Implement the class WordCounter that counts the number of occurrences
 * of each word in a given piece of text.
 * Feel free to use existing java classes.
 */
public class WordCounter implements Iterable<String> {

    private Map<String,Integer> wordCounts;

    public WordCounter() {
        this.wordCounts = new TreeMap<>();
    }

    /**
     * Add the word so that the counter of the word is increased by 1
     */
    public void addWord(String word) {
        if (wordCounts.containsKey(word)){
            wordCounts.put(word,wordCounts.get(word)+1);
        }
        else {
            wordCounts.put(word,1);
        }
    }

    /**
     * Return the number of times the word has been added so far
     */
    public int getCount(String word) {
        if (wordCounts.containsKey(word)) {
            return wordCounts.get(word);
        } else return 0;
    }

    // iterate over the words in ascending lexicographical order
    @Override
    public Iterator<String> iterator() {
        return wordCounts.keySet().iterator();
    }
}