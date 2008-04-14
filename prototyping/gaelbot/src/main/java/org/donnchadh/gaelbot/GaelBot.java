package org.donnchadh.gaelbot;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.Tag;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.htmlparser.visitors.NodeVisitor;

public class GaelBot implements Runnable {

    private final class LinkVisitorTask implements Runnable {
        private final String newLink;

        private final Set<String> processed;

        private final Queue<String> urlQueue;

        private LinkVisitorTask(String newLink, Set<String> processed, Queue<String> urlQueue) {
            this.newLink = newLink;
            this.processed = processed;
            this.urlQueue = urlQueue;
        }

        public void run() {

            try {
            NodeList links = visitUrl(newLink);
            links.visitAllNodesWith(new NodeVisitor() {
        @Override
        public void visitTag(Tag tag) {
            if (tag instanceof LinkTag) {
                String linkUrl = ((LinkTag) tag).extractLink();
                if (!linkUrl.contains(".google.ie/") && !linkUrl.contains(".google.com/")
                        && !linkUrl.contains("/search?q=cache:")
                        && linkUrl.startsWith("http://") && !processed.contains(linkUrl)) {
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
    }

    private static final String UTF_8 = "UTF-8";

    private static final String[] focail = { "agus", "tá", "bhí", "beidh", "sé", "sí", "níos", "mó", "ná", "chéile",
            "slán", "cúpla", "focal", "raibh", "maith", "agat", "ní", "níl", "mé", "tú", "bíonn", "bhíonn", "baineann",
            "má", "gaeilge" };

    /**
     * @param args
     */
    public static void main(String[] args) {
        new GaelBot().run();
    }

    public void run() {
        final ExecutorService executor = Executors.newFixedThreadPool(25);
        final Queue<String> urlQueue = new ConcurrentLinkedQueue<String>();
        final Set<String> processed = Collections.synchronizedSet(new HashSet<String>());
        for (String focal : focail) {
            for (String focal2 : focail) {
                for (String focal3 : focail) {
                    String newLink;
                    try {
                        newLink = buildGoogleUrl(focal, focal2, focal3);
                        urlQueue.add(newLink);
                    } catch (UnsupportedEncodingException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        }
        while (!urlQueue.isEmpty()) {
            final String newLink = urlQueue.remove();
            executor.execute(new LinkVisitorTask(newLink, processed, urlQueue));
            while (urlQueue.isEmpty()) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    private String buildGoogleUrl(String... focail) throws UnsupportedEncodingException {
        String newLink = "http://www.google.ie/search?q=";
        for (int i = 0; i < focail.length; i++) {
            if (i != 0) {
                newLink += "+";
            }
            newLink += URLEncoder.encode(focail[i], UTF_8);
        }
        newLink += "&num=100";
        return newLink;
    }

    private NodeList visitUrl(String newLink) {
        NodeList links;
        try {
            URL url = new URL(newLink);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("User-Agent", "Mozilla");
            // connection.setFollowRedirects(true);
            connection.connect();
            Parser parser = new Parser(connection);
            NodeFilter linkFilter = new NodeFilter() {

                public boolean accept(Node node) {
                    return node instanceof LinkTag;
                }
            };
            if (connection.getResponseCode() == 200) {
                NodeList top = parser.parse(linkFilter);
                links = top.extractAllNodesThatMatch(linkFilter);
                System.out.println(newLink);
            } else {
                links = new NodeList();
            }
        } catch (ParserException e) {
            links = new NodeList();
        } catch (MalformedURLException e) {
            links = new NodeList();
        } catch (IOException e) {
            links = new NodeList();
        }
        return links;
    }
}
