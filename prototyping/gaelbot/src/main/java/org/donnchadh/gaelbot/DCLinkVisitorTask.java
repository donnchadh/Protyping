/**
 * 
 */
package org.donnchadh.gaelbot;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.tags.MetaTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.htmlparser.util.SimpleNodeIterator;

final class DCLinkVisitorTask extends LinkVisitorTask {
    private final Map<String, Integer> wordCounts;
    private final String targetLanguage;
    private final Set<String> ignoreWords;

    DCLinkVisitorTask(String newLink, Set<String> processed, Queue<String> urlQueue,
            RobotsChecker robotsChecker, Map<String, Integer> wordCounts, String targetLanguage,
            Set<String> ignoreWords) {
        super(newLink, processed, urlQueue, robotsChecker);
        this.wordCounts = wordCounts;
        this.targetLanguage = targetLanguage;
        this.ignoreWords = ignoreWords;
    }

    @Override
    protected void processDocument(NodeList top) {
        NodeList metaTags = top.extractAllNodesThatMatch(new NodeFilter(){

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
            new WordCounter(targetLanguage, ignoreWords).countWords(new OireachtoCleaner().clean(top), wordCounts);
        }
    }

    private boolean isDublinCoreMetaTag(MetaTag tag) {
        return tag.getMetaTagName() != null &&  tag.getMetaTagName().startsWith("DC.");
    }
    
    @Override
    protected Parser buildParser(URL url) throws ParserException, IOException {
        File file = new File(new File(new File(targetLanguage), url.getHost()), url.getPath());
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            file.createNewFile();
            URLConnection connection = openConnection(url);
            InputStream s = connection.getInputStream();
            byte[] b = new byte[16384];
            int c = 0;
            FileOutputStream o = new FileOutputStream(file);
            try {
                do {
                    o.write(b, 0, c);
                    c = s.read(b);
                } while (c > 0);
                o.flush();
            } finally {
                o.close();
            }
            s.close();
        }
        return super.buildParser(url);
    }
}