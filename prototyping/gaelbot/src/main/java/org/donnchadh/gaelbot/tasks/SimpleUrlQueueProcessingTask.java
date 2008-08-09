/**
 * 
 */
package org.donnchadh.gaelbot.tasks;

import java.util.Queue;
import java.util.concurrent.ExecutorService;

import org.donnchadh.gaelbot.tasks.factory.LinkVisitorTaskFactory;

public class SimpleUrlQueueProcessingTask extends AbstractUrlQueueProcessingTask {

	private final LinkVisitorTaskFactory linkVisitorTaskFactory;

	public SimpleUrlQueueProcessingTask(
			Queue<String> urlQueue,
			ExecutorService executor,
			LinkVisitorTaskFactory linkVisitorTaskFactory) {
		super(urlQueue, executor);
		this.linkVisitorTaskFactory = linkVisitorTaskFactory;
	}

	public Runnable buildLinkVisitorTask(final String newLink) {
		return linkVisitorTaskFactory.buildLinkVisitorTask(newLink);
	}
	
}