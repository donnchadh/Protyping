package org.donnchadh.gaelbot;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


import com.csvreader.CsvReader;

public class OireachtoBot extends AbstractBot implements Runnable {
    private String targetLanguage = "ga";
    private String rootUrl = "http://achtanna.oireachtas.ie/ga.toc.decade.html";
    private int maxWords = 18000;

    public OireachtoBot() {
    }
    
    public OireachtoBot(String rootUrl, String targetLanguage, int maxWords) {
        this.rootUrl = rootUrl;
        this.targetLanguage = targetLanguage;
        this.maxWords = maxWords;
    }
    
    /**
     * @param args
     */
    public static void main(String[] args) {
        new OireachtoBot().run();
    }

    public void run() {
        final Set<String> ignoreWords = new HashSet<String>();
        try {
            Set<String> keepWords = new HashSet<String>();
            CsvReader csvReader = new CsvReader(new FileInputStream("commonWords.csv"), Charset.forName("UTF-8"));
            csvReader.readHeaders();
            String[] header = csvReader.getHeaders();
            while (csvReader.readRecord()) {
                String[] values = csvReader.getValues();
                if (values[1].equalsIgnoreCase(targetLanguage)) {
                    keepWords.add(values[0]);
                } else {
                    ignoreWords.add(values[0]);
                }
            }
            ignoreWords.removeAll(keepWords);
        } catch (IOException e) {
            e.printStackTrace(System.err);
        }
        
        final ExecutorService executor = Executors.newFixedThreadPool(50);
        final Queue<String> urlQueue = new ConcurrentLinkedQueue<String>();
        final Queue<String> hostQueue = new ConcurrentLinkedQueue<String>();
        final Set<String> processed = Collections.synchronizedSet(new HashSet<String>());
        final RobotsChecker robotsChecker = new RobotsChecker();
        final Map<String, Integer> wordCounts = new ConcurrentHashMap<String, Integer>();
        
        SecureRandom secureRandom = new SecureRandom(new SecureRandom(new byte[]{0,0,0,0}).generateSeed(32));
        urlQueue.add(rootUrl);
        while (!urlQueue.isEmpty()) {
            final String newLink = urlQueue.remove();
            try {
                hostQueue.add(new URL(newLink).getHost());
            } catch (MalformedURLException e1) {
                e1.printStackTrace();
            }
            executor.execute(new DCLinkVisitorTask(newLink, processed, urlQueue, robotsChecker, wordCounts, targetLanguage, ignoreWords));
            while (urlQueue.isEmpty()) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            if (wordCounts.size() >= maxWords) {
                break;
            }
        }
        printTopNWords(wordCounts, 100);
        System.exit(0);
    }

    private void printTopNWords(Map<String, Integer> wordCounts, int n) {
        SortedMap<Integer, List<String>> wordsByCount = new TreeMap<Integer, List<String>>(new ReverseIntegerComparator());
        for (Entry<String, Integer> entry : wordCounts.entrySet()) {
            List<String> words;
            if (!wordsByCount.containsKey(entry.getValue())) {
                words = new ArrayList<String>();
                wordsByCount.put(entry.getValue(), words);
            } else {
                words = wordsByCount.get(entry.getValue());
            }
            words.add(entry.getKey());
        }
        System.out.println("=============================");
        int i = 0;
        for (Entry<Integer, List<String>> entry : wordsByCount.entrySet()) {
            printEntry(i, entry);
            i++;
            if (i > n) {
                break;
            }
        }
        i = 0;
        int j = 0;
        int k = 0;
        int l = 0;
        int m = 0;
        System.out.println("=============================");
        boolean printed = false;
        boolean printed2 = false;
        boolean printed3 = false;
        for (Entry<Integer, List<String>> entry : wordsByCount.entrySet()) {
            j += entry.getValue().size();
            k += entry.getKey().intValue()*j;
            if (entry.getValue().size() >= 3) {
                l += entry.getValue().size();
            }
            if (entry.getValue().size() >= 7) {
                m += entry.getValue().size();
            }
            if (j >= 4000 && !printed) {
                System.out.println("4000th entry:");
                printEntry(i, entry);
                System.out.println("Mid-range words: " + m);
                printed = true;
            }
            if (m >= 5000 && !printed3) {
                System.out.println("Xth entry:");
                printEntry(i, entry);
                System.out.println("Mid-range words: " + m);
                printed3 = true;
            }
            if (j >= 8000 && !printed2) {
                System.out.println("8000th entry:");
                printEntry(i, entry);
                System.out.println("Mid-range words: " + l);
                printed2 = true;
            }
            i++;
        }
        System.out.println("=============================");
        System.out.println("Total: " + j + " / " + k);
        File file = new File("results."+targetLanguage+".csv");
        try {
            file.createNewFile();
            PrintWriter printWriter = new PrintWriter(file, "UTF-8");
            printWriter.println("word, count");
            for (Entry<Integer, List<String>> entry : wordsByCount.entrySet()) {
                for (String word : entry.getValue()) {
                    printWriter.print(word);
                    printWriter.print(",");
                    printWriter.print(entry.getKey());
                    printWriter.println();
                }
            }         
            printWriter.flush();
            printWriter.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void printEntry(int i, Entry<Integer, List<String>> entry) {
        System.out.print(i);
        System.out.print(", ");
        System.out.print(entry.getValue().size());
        System.out.print(": ");
        System.out.println(entry);
    }

}
