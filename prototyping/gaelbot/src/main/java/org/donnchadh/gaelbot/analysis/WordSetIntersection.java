package org.donnchadh.gaelbot.analysis;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.donnchadh.gaelbot.ReverseIntegerComparator;

import com.csvreader.CsvReader;

public class WordSetIntersection {
    public static void main(String[] args) throws IOException {
        Map<String, Integer> words = readWords("results.en.csv");
        Map<String, Integer> words2 = readWords("results.ga.csv");

        Set<String> gaWords = new HashSet<String>();
        Set<String> enWords = new HashSet<String>();
        Set<String> xWords = new HashSet<String>();
        CommonWordAnalyzer.readCommonWords(gaWords, enWords, xWords);
        Set<String> oldCommonWords = new HashSet<String>(gaWords);
        oldCommonWords.retainAll(enWords);
        Set<String> oldWords = new HashSet<String>(xWords);
        oldWords.addAll(gaWords);
        oldWords.addAll(enWords);

        Set<String> commonWords = new HashSet<String>(words.keySet());
        commonWords.retainAll(words2.keySet());
        commonWords.removeAll(oldWords);
        
        System.out.print(words.size());
        System.out.print("  ");
        System.out.print(words2.size());
        System.out.print("  ");
        System.out.println(commonWords.size());
        System.out.println("--------");
        int x = 0, ga = 0, en = 0;
        Map<Integer, String> results = new TreeMap<Integer, String>(new ReverseIntegerComparator()); 
        for (String word : commonWords) {
            double correlation = words.get(word).doubleValue() / words2.get(word).doubleValue();
            String language = analyze(correlation);
            if (language.equals("EN")) {
                en++;
            } else if (language.equals("GA")) {
                ga++;
            } else {
                x++;
            }
            int total = words.get(word).intValue() + words2.get(word).intValue();
            String result = word + "," + language + "," + correlation + ", " + total;
            results.put(total, result);
        }
        for (String result : results.values()) {
            System.out.println(result);
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
        CsvReader csvReader = new CsvReader(new FileInputStream(filename), Charset.forName("UTF-8"));
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
