/**
 * 
 */
package org.donnchadh.gaelbot.tasks;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ExecutorService;

import org.donnchadh.gaelbot.robots.RobotsChecker;

public class UrlQueueProcessingTask implements Runnable {
	private final Queue<String> urlQueue;
	private final ExecutorService executor;
	private final Set<String> processed;
	private final RobotsChecker robotsChecker;
	private final Queue<String> hostQueue;

	public UrlQueueProcessingTask(Queue<String> urlQueue,
			ExecutorService executor, Set<String> processed,
			RobotsChecker robotsChecker, Queue<String> hostQueue) {
		this.urlQueue = urlQueue;
		this.executor = executor;
		this.processed = processed;
		this.robotsChecker = robotsChecker;
		this.hostQueue = hostQueue;
	}

	public void run () {
	    while (!urlQueue.isEmpty()) {
	        processNextUrlAndWaitForMore();
	    }
    }

	protected void processNextUrlAndWaitForMore() {
		final String newLink = urlQueue.remove();
		try {
		    hostQueue.add(new URL(newLink).getHost());
		} catch (MalformedURLException e1) {
		    e1.printStackTrace();
		}
		executor.execute(buildLinkVisitorTask(newLink));
		while (isUrlQueueEmpty()) {
		    try {
		        Thread.sleep(1000);
		    } catch (InterruptedException e) {
		        Thread.currentThread().interrupt();
		    }
		}
	}

	protected boolean isUrlQueueEmpty() {
		return getUrlQueue().isEmpty();
	}

	protected LinkVisitorTask buildLinkVisitorTask(final String newLink) {
		return new LinkVisitorTask(newLink, getProcessed(), getUrlQueue(), getRobotsChecker());
	}
	
	protected Set<String> getProcessed() {
		return processed;
	}
	
	protected Queue<String> getUrlQueue() {
		return urlQueue;
	}
	
	protected RobotsChecker getRobotsChecker() {
		return robotsChecker;
	}
}