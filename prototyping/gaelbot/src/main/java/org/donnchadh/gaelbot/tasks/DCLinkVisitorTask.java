/**
 * 
 */
package org.donnchadh.gaelbot.tasks;

import java.util.Map;
import java.util.Queue;
import java.util.Set;

import org.donnchadh.gaelbot.dublincore.WordCountingDublinCoreDocumentProcessor;
import org.donnchadh.gaelbot.robots.RobotsChecker;
import org.donnchadh.gaelbot.urlprocessors.impl.FileCachingUrlProcessor;


public final class DCLinkVisitorTask extends LinkVisitorTask {

    public DCLinkVisitorTask(String newLink, Set<String> processed, Queue<String> urlQueue,
            RobotsChecker robotsChecker, Map<String, Integer> wordCounts, String targetLanguage,
            Set<String> ignoreWords) {
        super(newLink, processed, urlQueue, robotsChecker, new FileCachingUrlProcessor(targetLanguage), new WordCountingDublinCoreDocumentProcessor(wordCounts, targetLanguage, ignoreWords));
    }
}