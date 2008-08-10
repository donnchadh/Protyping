package org.donnchadh.gaelbot.simple;

import java.io.File;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.donnchadh.gaelbot.crawler.AbstractBot;
import org.donnchadh.gaelbot.tasks.SimpleUrlQueueProcessingTask;
import org.donnchadh.gaelbot.tasks.factory.impl.PageDownloadingLinkVisitorTaskFactory;
import org.donnchadh.gaelbot.util.CsvLineHandler;
import org.donnchadh.gaelbot.util.CsvReaderHelper;

public class UrlDownloadingBot extends AbstractBot implements Runnable {
	private final ExecutorService executor = Executors.newFixedThreadPool(50);

	/**
     * @param args
     */
    public static void main(String[] args) {
        new UrlDownloadingBot().run();
    }

    public void run() {
    	final Queue<String> urlQueue = readUrls(new File("urls_su.txt"));
        processUrls(urlQueue);
    }

	private Queue<String> readUrls(File file) {
		final ConcurrentLinkedQueue<String> urls = new ConcurrentLinkedQueue<String>();
		new CsvReaderHelper().readFile(file, new CsvLineHandler(){ public void handle(String[] line) { 
	            urls.add(line[0]);}}
		);
		return urls;
	}

	private void processUrls(final Queue<String> urlQueue) {
        new SimpleUrlQueueProcessingTask(urlQueue, executor, new PageDownloadingLinkVisitorTaskFactory()).run();
	}

}
