package labs.lab11;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Driver {
    public static void main(String[] args) {
        HashMap<String, Integer> wordCounts = new HashMap<>();
        
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new URL(
                "http://www.technology.heartland.edu/faculty/johnnyt/content/CSCI131"
                + "/Labs/Tree_Map/HalloweenStories.txt").openStream())))
        {
            reader.lines().forEach(line -> {
                String[] words = line.split("[^\\p{L}0-9']+");
                for (String word : words) {
                    Integer oldValue = wordCounts.putIfAbsent(word, 1);
                    if (oldValue != null)
                        wordCounts.put(word, oldValue + 1);
                }
            });

            Map<String, Integer> sortedWordCounts = sortMapByValue(wordCounts, true);

            System.out.println("\nNumber of unique words: " + sortedWordCounts.size());
            System.out.println("Number of words: "
                + sortedWordCounts.values().stream().mapToInt(Number::intValue).sum());
            
            System.out.println("\nCount\tWord\n-------------");
            sortedWordCounts.forEach((word, count) -> {
                System.out.println(count + "\t" + word);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static <K extends Comparable<? super K>, V extends Comparable<? super V>> 
                            Map<K, V> sortMapByValue(Map<K, V> map, boolean reversed) {
        List<Map.Entry<K, V>> list = new LinkedList<>(map.entrySet());

        Collections.sort(list, new Comparator<Map.Entry<K, V>>() {
            @Override
            public int compare(Map.Entry<K, V> entry1, Map.Entry<K, V> entry2) {
                int diff = entry2.getValue().compareTo(entry1.getValue());
                if (diff == 0)
                    return entry1.getKey().compareTo(entry2.getKey());
                return diff;
            }
        });
    
        Map<K, V> sortedMap = new LinkedHashMap<>();
        list.forEach(entry -> sortedMap.put(entry.getKey(), entry.getValue()));
 
	    return sortedMap;
    }
}