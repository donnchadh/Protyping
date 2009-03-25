package org.donnchadh.gaelbot.oireachtas.droid;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import org.apache.droids.api.Link;
import org.apache.droids.api.TaskMaster;
import org.apache.droids.api.TaskQueue;
import org.apache.droids.exception.InvalidTaskException;
import org.apache.droids.helper.factories.HandlerFactory;
import org.apache.droids.impl.MultiThreadedTaskMaster;
import org.apache.droids.impl.SimpleTaskQueue;
import org.apache.droids.robot.crawler.CrawlingDroid;
import org.donnchadh.gaelbot.dublincore.WordCountingDublinCoreDocumentProcessor;
import org.donnchadh.gaelbot.handlers.impl.HandlerDocumentProcessorAdapter;
import org.donnchadh.gaelbot.handlers.impl.HandlerUrlProcessorAdapter;
import org.donnchadh.gaelbot.urlprocessors.impl.FileCachingUrlProcessor;
import org.donnchadh.gaelbot.util.ReverseIntegerComparator;

import com.csvreader.CsvReader;

public class OireachtoDroid extends CrawlingDroid implements Runnable {
    private String targetLanguage = "ga";
    private String rootUrl = "http://achtanna.oireachtas.ie/ga.toc.decade.html";
	private int maxWords = 18000;
    final Map<String, Integer> wordCounts = new ConcurrentHashMap<String, Integer>();

    public OireachtoDroid(TaskQueue<Link> queue, TaskMaster<Link> taskMaster) {
        super(queue, taskMaster);
    }
    
    public OireachtoDroid(TaskQueue<Link> queue, TaskMaster<Link> taskMaster, String rootUrl, String targetLanguage, int maxWords) {
        super(queue, taskMaster);
        this.rootUrl = rootUrl;
        this.targetLanguage = targetLanguage;
        this.maxWords = maxWords;
    }
    
    /**
     * @param args
     */
    public static void main(String[] args) {
        MultiThreadedTaskMaster<Link> taskMaster = new MultiThreadedTaskMaster<Link>();
        taskMaster.setMaxThreads( 3 );
        
        TaskQueue<Link> queue = new SimpleTaskQueue<Link>();
        
        Collection<String> locations = new ArrayList<String>();
        locations.add( args[0] );

        OireachtoDroid simple = new OireachtoDroid( queue, taskMaster );
        simple.run();
    }

    public void run() {
        setInitialLocations( Arrays.asList(rootUrl) );
        try {
            init();
        } catch (InvalidTaskException e) {
            throw new RuntimeException(e);
        }
        start();  
        try {
            getTaskMaster().awaitTermination(0, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        printTopNWords(wordCounts, 100);
    }

    @Override
    public void init() throws InvalidTaskException {
        super.init();
        HandlerFactory handlerFactory = new HandlerFactory();
        HandlerUrlProcessorAdapter defaultHandler = new HandlerUrlProcessorAdapter(new FileCachingUrlProcessor(targetLanguage));
        handlerFactory.setMap(new HashMap<String, Object>());
        handlerFactory.getMap().put("default", defaultHandler);
        final Set<String> ignoreWords = readIgnoreWords("commonWords.csv");
        handlerFactory.getMap().put("wordCounter", new HandlerDocumentProcessorAdapter(new WordCountingDublinCoreDocumentProcessor(wordCounts, targetLanguage, ignoreWords)));
        setHandlerFactory(handlerFactory);
    }
    
	private Set<String> readIgnoreWords(String filename) {
		final Set<String> ignoreWords = new HashSet<String>();
        try {
            Set<String> keepWords = new HashSet<String>();
			CsvReader csvReader = new CsvReader(new FileInputStream(filename), Charset.forName("UTF-8"));
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
		return ignoreWords;
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
