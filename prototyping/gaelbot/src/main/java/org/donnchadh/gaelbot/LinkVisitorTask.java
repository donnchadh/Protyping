/**
 * 
 */
package org.donnchadh.gaelbot;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Queue;
import java.util.Set;

import org.donnchadh.gaelbot.urlprocessors.CompositeUrlProcessor;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.Tag;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.htmlparser.visitors.NodeVisitor;

class LinkVisitorTask extends AbstractLinkVisitorTask  {
    
    private final String newLink;

    private final Set<String> processed;

    private final Queue<String> urlQueue;

    private final RobotsChecker robotsChecker;

    private final LinkVisitorDocumentProcessor documentProcessor;
    
    
    
    LinkVisitorTask(String newLink, Set<String> processed, Queue<String> urlQueue, RobotsChecker robotsChecker) {
        this(newLink, processed,  urlQueue,  robotsChecker, new LinkVisitorDocumentProcessor());
    }

    private LinkVisitorTask(String newLink, Set<String> processed, Queue<String> urlQueue, RobotsChecker robotsChecker, LinkVisitorDocumentProcessor documentProcessor) {
        this(newLink, processed,  urlQueue,  robotsChecker, documentProcessor, new LinkVisitorUrlProcessor(documentProcessor));
    }
    
    private LinkVisitorTask(String newLink, Set<String> processed, Queue<String> urlQueue, RobotsChecker robotsChecker, LinkVisitorDocumentProcessor documentProcessor, DocumentProcessor otherDP, UrlProcessor otherUP) {
        this(newLink, processed,  urlQueue,  robotsChecker, documentProcessor, new CompositeUrlProcessor(new LinkVisitorUrlProcessor(documentProcessor, otherDP), otherUP));
    }
    
    LinkVisitorTask(String newLink, Set<String> processed, Queue<String> urlQueue, RobotsChecker robotsChecker,
            UrlProcessor urlProcessor, DocumentProcessor documentProcessor) {
        this(newLink, processed,  urlQueue,  robotsChecker, new LinkVisitorDocumentProcessor(), documentProcessor, urlProcessor);
    }

    private LinkVisitorTask(String newLink, Set<String> processed, Queue<String> urlQueue, RobotsChecker robotsChecker,
            LinkVisitorDocumentProcessor documentProcessor, UrlProcessor urlProcessor) {
        super(urlProcessor);
        this.newLink = newLink;
        this.processed = processed;
        this.urlQueue = urlQueue;
        this.robotsChecker = robotsChecker;
        this.documentProcessor = documentProcessor;
    }

    public void run() {

        try {
            visitUrl(newLink);
            NodeList links = documentProcessor.getLinks();
            links.visitAllNodesWith(new NodeVisitor() {
                @Override
                public void visitTag(Tag tag) {
                    if (tag instanceof LinkTag) {
                        String linkUrl = ((LinkTag) tag).extractLink();
                        boolean isNotGoogleUrl = !linkUrl.contains(".google.ie/")
                                && !linkUrl.contains(".google.com/") && !linkUrl.contains("/search?q=cache:")
                                && linkUrl.startsWith("http://") && !processed.contains(linkUrl);
                        if (isNotGoogleUrl) {
                            urlQueue.add(linkUrl);
                            processed.add(linkUrl);
                        }
                    }
                }
            });
        } catch (ParserException e) {
            throw new RuntimeException(e);
        }
    }
    
    protected void visitUrl(String newLink) {
        try {
            URL url = new URL(newLink);
            boolean canCrawl = true;
            try {
                canCrawl = robotsChecker.checkRobots(url);
            } catch (IllegalStateException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            if (canCrawl) {
                processUrl(url);
            }
        } catch (MalformedURLException e) {
            // TODO
        } catch (IOException e) {
            // TODO
        }
    }

    protected static Parser buildParser(URL url) throws IOException, ParserException {
        Parser parser;
        URLConnection connection = openConnection(url);
        if (!(connection instanceof HttpURLConnection) ||
                ((HttpURLConnection)connection).getResponseCode() == 200) {
            parser = new Parser(connection);
        } else {
            parser = null;
        }
        return parser;
    }

    protected static URLConnection openConnection(URL url) throws IOException {
        URLConnection connection =  url.openConnection();
        connection.setRequestProperty("User-Agent", "Mozilla");
        // connection.setFollowRedirects(true);
        connection.connect();
        return connection;
    }

    static class LinkVisitorDocumentProcessor implements DocumentProcessor {
        private NodeList links = new NodeList();
        
        public void processDocument(NodeList top) {
            links = extractLinks(top);
        }

        public NodeList getLinks() {
            return links;
        }
        
        private NodeList extractLinks(NodeList top) {
            NodeList links;
            NodeFilter linkFilter = new NodeFilter() {

                public boolean accept(Node node) {
                    return node instanceof LinkTag;
                }
            };
            links = top.extractAllNodesThatMatch(linkFilter);
            return links;
        }
        
        

    }

}