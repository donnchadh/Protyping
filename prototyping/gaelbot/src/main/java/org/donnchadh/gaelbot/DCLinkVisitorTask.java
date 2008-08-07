/**
 * 
 */
package org.donnchadh.gaelbot;

import java.util.Map;
import java.util.Queue;
import java.util.Set;


public final class DCLinkVisitorTask extends LinkVisitorTask {

    public DCLinkVisitorTask(String newLink, Set<String> processed, Queue<String> urlQueue,
            RobotsChecker robotsChecker, Map<String, Integer> wordCounts, String targetLanguage,
            Set<String> ignoreWords) {
        super(newLink, processed, urlQueue, robotsChecker, new FileCachingUrlProcessor(targetLanguage), new WordCountingDublinCoreDocumentProcessor(wordCounts, targetLanguage, ignoreWords));
    }
}