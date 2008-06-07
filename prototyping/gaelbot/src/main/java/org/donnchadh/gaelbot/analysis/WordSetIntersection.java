package org.donnchadh.gaelbot.analysis;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.csvreader.CsvReader;

public class WordSetIntersection {
    public static void main(String[] args) throws IOException {
        Map<String, Integer> words = readWords("results.en.csv");
        Map<String, Integer> words2 = readWords("results.ga.csv");
        
        Set<String> commonWords = new HashSet<String>(words.keySet());
        commonWords.retainAll(words2.keySet());
        
        System.out.print(words.size());
        System.out.print("  ");
        System.out.print(words2.size());
        System.out.print("  ");
        System.out.println(commonWords.size());
        System.out.println("--------");
        int x = 0, ga = 0, en = 0;
        for (String word : commonWords) {
            double correlation = words.get(word).doubleValue() / words2.get(word).doubleValue();
            String language = analyze(correlation);
            if (language.equals("EN")) {
                en++;
            } else if (language.equals("GA")) {
                ga++;
            } else {
                System.out.println(word + " : " + correlation + " - " + language);
                x++;
            }
        }
        System.out.println("--------");
        System.out.println("EN : " + en + " GA : " + ga + " X : " + x);
        System.out.println("--------");
        
    }

    private static String analyze(double correlation) {
        if (correlation > 3.0) {
            return "EN";
        }
        if (correlation < 0.3) {
            return "GA";
        }
        return "X";
    }

    private static Map<String, Integer> readWords(String filename) throws FileNotFoundException, IOException {
        CsvReader csvReader = new CsvReader(filename);
        csvReader.readHeaders();
        String[] header = csvReader.getHeaders();
        Map<String, Integer> words = new HashMap<String, Integer>();
        while (csvReader.readRecord()) {
            String[] values = csvReader.getValues();
            words.put(values[0], Integer.valueOf(values[1]));
        }
        return words;
    }
}
