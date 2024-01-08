package mod.jeje.voicerecognition.wordCounter;

import java.util.*;

public class WordCounter {
    private Map<String, Integer> wordOccurrences;

    public WordCounter() {
        this.wordOccurrences = new HashMap<>();
    }

    public void addWord(String word) {
        // Update the count for the given word
        wordOccurrences.put(word, wordOccurrences.getOrDefault(word, 0) + 1);
    }

    public int getOccurrences(String word) {
        return wordOccurrences.getOrDefault(word, 0);
    }

    public Map<String, Integer> getWordOccurrences() {
        // Return the entire map
        return new HashMap<>(wordOccurrences);
    }

    public List<Map.Entry<String, Integer>> getMostUsedWords(int n) {
        // Sort entries by value (occurrences) in descending order
        List<Map.Entry<String, Integer>> sortedEntries = new ArrayList<>(wordOccurrences.entrySet());
        sortedEntries.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));

        // Get the first n entries
        return sortedEntries.subList(0, Math.min(n, sortedEntries.size()));
    }
}
