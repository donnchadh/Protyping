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

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.Tag;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.htmlparser.visitors.NodeVisitor;

class LinkVisitorTask implements Runnable, DocumentProcessor, UrlProcessor  {
    private final String newLink;

    private final Set<String> processed;

    private final Queue<String> urlQueue;

    private final RobotsChecker robotsChecker;
    
    LinkVisitorTask(String newLink, Set<String> processed, Queue<String> urlQueue, RobotsChecker robotsChecker) {
        this.newLink = newLink;
        this.processed = processed;
        this.urlQueue = urlQueue;
        this.robotsChecker = robotsChecker;
    }

    public void run() {

        try {
            NodeList links = visitUrl(newLink);
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
    
    private NodeList visitUrl(String newLink) {
        NodeList links;
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
            if (!canCrawl) {
                return new NodeList();
            }
            links = processUrl(url); 
        } catch (MalformedURLException e) {
            links = new NodeList();
        } catch (IOException e) {
            links = new NodeList();
        }
        return links;
    }

    public NodeList processUrl(URL url) throws IOException {
        NodeList links;
        try {
            Parser parser = buildParser(url);
            if (parser != null) {
                NodeList top = parser.parse(new NodeFilter(){
                    public boolean accept(Node arg0) {
                        return true;
                    }});
                links = processDocument(top);
                System.out.println(url.toString());
            } else {
                links = new NodeList();
            }
        } catch (ParserException e) {
            links = new NodeList();
        }
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

    protected Parser buildParser(URL url) throws IOException, ParserException {
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

    protected URLConnection openConnection(URL url) throws IOException {
        URLConnection connection =  url.openConnection();
        connection.setRequestProperty("User-Agent", "Mozilla");
        // connection.setFollowRedirects(true);
        connection.connect();
        return connection;
    }

    public NodeList processDocument(NodeList top) {
        return extractLinks(top);
    }

}