package org.donnchadh.gaelbot.simple;

import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.donnchadh.gaelbot.crawler.AbstractBot;
import org.donnchadh.gaelbot.robots.RobotsChecker;
import org.donnchadh.gaelbot.tasks.RobotsCheckingTask;
import org.donnchadh.gaelbot.tasks.UrlQueueProcessingTask;

public class GaelBot extends AbstractBot implements Runnable {
    private static final String[] focail = { "agus", "tá", "bhí", "beidh", "sé", "sí", "níos", "mó", "ná", "chéile",
            "slán", "cúpla", "focal", "raibh", "maith", "agat", "ní", "níl", "mé", "tú", "bíonn", "bhíonn", "baineann",
            "má", "gaeilge" };

	private final ExecutorService executor = Executors.newFixedThreadPool(50);
	private final Queue<String> hostQueue = new ConcurrentLinkedQueue<String>();

	/**
     * @param args
     */
    public static void main(String[] args) {
        new GaelBot().run();
    }

    public void run() {
    	final RobotsChecker robotsChecker = startRobotsChecker();
    	final Queue<String> urlQueue = buildGoogleUrls();
        processUrls(robotsChecker, urlQueue);
    }

	private void processUrls(final RobotsChecker robotsChecker,
			final Queue<String> urlQueue) {
		final Set<String> processed = Collections.synchronizedSet(new HashSet<String>());
        new UrlQueueProcessingTask(urlQueue, executor, processed, robotsChecker, hostQueue).run();
	}

	protected RobotsChecker startRobotsChecker() {
		final RobotsChecker robotsChecker = new RobotsChecker();
        executor.execute(new RobotsCheckingTask(robotsChecker, hostQueue));
		return robotsChecker;
	}

	private Queue<String> buildGoogleUrls() {
		final Queue<String> urlQueue = new ConcurrentLinkedQueue<String>();
        addGoogleUrlsToQueue(urlQueue);
		return urlQueue;
	}

	private void addGoogleUrlsToQueue(final Queue<String> urlQueue) {
		for (String focal : focail) {
            for (String focal2 : focail) {
                for (String focal3 : focail) {
                    String newLink;
                    try {
                        newLink = AbstractBot.buildGoogleUrl(focal, focal2, focal3);
                        urlQueue.add(newLink);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
	}

}
