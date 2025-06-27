package utils;
import  java.util.*;

public class WordFrequencyAnalyzer {
    public static void analyze(List<String> titles) {
        Map<String, Integer> wordCount = new HashMap<>();

        for (String title : titles) {
            String lowerTitle = title.toLowerCase();
            String[] words = lowerTitle.split(" ");

            for (String word : words) {
                // ignore short words
                if (word.length() > 2) {
                    if (wordCount.containsKey(word)) {
                        int count = wordCount.get(word);
                        wordCount.put(word, count + 1);
                    } else {
                        wordCount.put(word, 1);
                    }
                }
            }
        }

        // print words that apper more than 2 times
        for (Map.Entry<String, Integer> entry : wordCount.entrySet()) {
            if (entry.getValue() > 2) {
                System.out.println(entry.getKey() + ": " + entry.getValue());
            }
        }
    }


}
