/**
 * 
 */
package org.donnchadh.gaelbot.oireachtas;

import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ExecutorService;

import org.donnchadh.gaelbot.robots.RobotsChecker;
import org.donnchadh.gaelbot.tasks.DCLinkVisitorTask;
import org.donnchadh.gaelbot.tasks.UrlQueueProcessingTask;

final class OireachtasUrlProcessingTask extends UrlQueueProcessingTask {
	private final Set<String> ignoreWords;
	private final Map<String, Integer> wordCounts;
	private final String targetLanguage;
    private final int maxWords;

	OireachtasUrlProcessingTask(Set<String> processed,
			Queue<String> urlQueue, Set<String> ignoreWords,
			Map<String, Integer> wordCounts, ExecutorService executor,
			Queue<String> hostQueue, RobotsChecker robotsChecker, 
			String targetLanguage, int maxWords) {
		super(urlQueue, executor, processed, robotsChecker, hostQueue);
		this.ignoreWords = ignoreWords;
		this.wordCounts = wordCounts;
		this.targetLanguage = targetLanguage;
		this.maxWords = maxWords;
	}

	public void run() {
        while (!isUrlQueueEmpty()) {
            processNextUrlAndWaitForMore();
            if (wordCounts.size() >= maxWords) {
                break;
            }
        }
    }

	protected DCLinkVisitorTask buildLinkVisitorTask(final String newLink) {
		return new DCLinkVisitorTask(newLink, getProcessed(), getUrlQueue(), getRobotsChecker(), wordCounts, targetLanguage, ignoreWords);
	}
}