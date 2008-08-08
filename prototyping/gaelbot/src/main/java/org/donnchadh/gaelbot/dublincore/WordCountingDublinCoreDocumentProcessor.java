/**
 * 
 */
package org.donnchadh.gaelbot.dublincore;

import java.util.Map;
import java.util.Set;

import org.donnchadh.gaelbot.cleaners.SimpleCleaner;
import org.donnchadh.gaelbot.documentprocessors.DocumentProcessor;
import org.donnchadh.gaelbot.wordcounting.WordCounter;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.tags.MetaTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.SimpleNodeIterator;

public class WordCountingDublinCoreDocumentProcessor implements DocumentProcessor {

    private final String targetLanguage;
    private final Set<String> ignoreWords;
    private final Map<String, Integer> wordCounts;

    public WordCountingDublinCoreDocumentProcessor(Map<String, Integer> wordCounts, String targetLanguage, Set<String> ignoreWords) {
        this.targetLanguage = targetLanguage;
        this.ignoreWords = ignoreWords;
        this.wordCounts = wordCounts;
    }

    public void processDocument(NodeList top) {
        NodeList metaTags = top.extractAllNodesThatMatch(new NodeFilter() {

            public boolean accept(Node node) {
                return node instanceof MetaTag;
            }

        });
        String language = null;
        SimpleNodeIterator metaTagElements = metaTags.elements();
        while (metaTagElements.hasMoreNodes()) {
            MetaTag tag = (MetaTag) metaTagElements.nextNode();

            if (isDublinCoreMetaTag(tag)) {
                if (tag.getMetaTagName().equalsIgnoreCase("DC.Language")) {
                    language = tag.getMetaContent();
                }
            }
        }
        if (targetLanguage.equalsIgnoreCase(language)) {
            new WordCounter(targetLanguage, ignoreWords).countWords(new SimpleCleaner().clean(top), wordCounts);
        }
    }
    
    private boolean isDublinCoreMetaTag(MetaTag tag) {
        return tag.getMetaTagName() != null &&  tag.getMetaTagName().startsWith("DC.");
    }
    
}