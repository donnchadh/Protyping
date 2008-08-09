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

public class UrlQueueProcessingTask extends AbstractUrlQueueProcessingTask {
	private final Set<String> processed;
	private final RobotsChecker robotsChecker;
	private final Queue<String> hostQueue;

	public UrlQueueProcessingTask(Queue<String> urlQueue,
			ExecutorService executor, Set<String> processed,
			RobotsChecker robotsChecker, Queue<String> hostQueue) {
		super(urlQueue, executor);
		this.processed = processed;
		this.robotsChecker = robotsChecker;
		this.hostQueue = hostQueue;
	}

	protected void beforeProcessUrl(final String newLink) {
		try {
		    hostQueue.add(new URL(newLink).getHost());
		} catch (MalformedURLException e1) {
		    e1.printStackTrace();
		}
	}

	public LinkVisitorTask buildLinkVisitorTask(final String newLink) {
		return new LinkVisitorTask(newLink, getProcessed(), getUrlQueue(), getRobotsChecker());
	}
	
	protected Set<String> getProcessed() {
		return processed;
	}
	
	protected RobotsChecker getRobotsChecker() {
		return robotsChecker;
	}
}