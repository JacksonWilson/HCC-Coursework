package labs.lab9;

import java.util.ArrayList;

/**
 * An implementation of a set for words.
 * 
 * @author Jackson Wilson
 */
public class WordSet {
    private ArrayList<String> wordSet;
    private int totalAttempts;

    /**
     * Initializes an ArrayList of Strings for the backbone of this set.
     */
    public WordSet() {
        wordSet = new ArrayList<>();
        totalAttempts = 0;
    }

    /**
     * Adds a word to the set if the set does not already contain the word.
     * <p> Keeps track of the number of attempts to add a word to the set.
     * 
     * @param word A word to attempt to be added.
     */
    public void add(String word) {
        if (!wordSet.contains(word))
            wordSet.add(word);
        totalAttempts++;
    }

    /**
     * Gets the set of words.
     * 
     * @return The Set of words.
     */
    public ArrayList<String> getWordSet() {
        return wordSet;
    }

    /**
     * Gets the total attempts to add a word to the set.
     * 
     * @return The total number of attempts.
     */
    public int getTotalAttempts() {
        return totalAttempts;
    }
}