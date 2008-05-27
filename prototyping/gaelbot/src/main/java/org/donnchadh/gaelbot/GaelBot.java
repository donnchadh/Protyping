package org.donnchadh.gaelbot;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
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
import org.osjava.norbert.NoRobotClient;
import org.osjava.norbert.NoRobotException;

public class GaelBot implements Runnable {

    private final class LinkVisitorTask implements Runnable {
        private final String newLink;

        private final Set<String> processed;

        private final Queue<String> urlQueue;

        private final Queue<String> hostQueue;

        private LinkVisitorTask(String newLink, Set<String> processed, Queue<String> urlQueue, Queue<String> hostQueue) {
            this.newLink = newLink;
            this.processed = processed;
            this.urlQueue = urlQueue;
            this.hostQueue = hostQueue;
        }

        public void run() {

            try {
            NodeList links = visitUrl(newLink);
            links.visitAllNodesWith(new NodeVisitor() {
        @Override
        public void visitTag(Tag tag) {
            if (tag instanceof LinkTag) {
                String linkUrl = ((LinkTag) tag).extractLink();
                boolean isNotGoogleUrl = !linkUrl.contains(".google.ie/") && !linkUrl.contains(".google.com/")
                        && !linkUrl.contains("/search?q=cache:")
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
    }

    private static final String UTF_8 = "UTF-8";

    private static final String[] focail = { "agus", "tá", "bhí", "beidh", "sé", "sí", "níos", "mó", "ná", "chéile",
            "slán", "cúpla", "focal", "raibh", "maith", "agat", "ní", "níl", "mé", "tú", "bíonn", "bhíonn", "baineann",
            "má", "gaeilge" };

    private final Map<String, String> robots = new HashMap<String, String>();
    
    /**
     * @param args
     */
    public static void main(String[] args) {
        new GaelBot().run();
    }

    public void run() {
        final ExecutorService executor = Executors.newFixedThreadPool(50);
        final Queue<String> urlQueue = new ConcurrentLinkedQueue<String>();
        final Queue<String> hostQueue = new ConcurrentLinkedQueue<String>();
        final Set<String> processed = Collections.synchronizedSet(new HashSet<String>());
        for (String focal : focail) {
            for (String focal2 : focail) {
                for (String focal3 : focail) {
                    String newLink;
                    try {
                        newLink = buildGoogleUrl(focal, focal2, focal3);
                        urlQueue.add(newLink);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        executor.execute(new Runnable() {
            public void run() {
                while (true) {
                    while (hostQueue.isEmpty()) {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                    if (!hostQueue.isEmpty()) {
                        try {
                            checkRobots(hostQueue.remove(), "http", 80);
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (NoRobotException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
        while (!urlQueue.isEmpty()) {
            final String newLink = urlQueue.remove();
            try {
                hostQueue.add(new URL(newLink).getHost());
            } catch (MalformedURLException e1) {
                e1.printStackTrace();
            }
            executor.execute(new LinkVisitorTask(newLink, processed, urlQueue, hostQueue));
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
            boolean canCrawl = true;
            try {
                canCrawl = checkRobots(url);
            } catch (IllegalStateException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (NoRobotException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            if (!canCrawl) {
                return new NodeList();
            }
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

    private boolean checkRobots(URL url) throws IOException, IllegalStateException, IllegalArgumentException, NoRobotException {
        String host = url.getHost();
        if (host.equals("www.google.ie")) {
            return true;
        }
        String protocol = url.getProtocol();
        int port = url.getPort();
        return checkRobots(host, protocol, port).isUrlAllowed(url);
    }

    private NoRobotClient checkRobots(String host, String protocol, int port) throws MalformedURLException, IOException, NoRobotException {
        synchronized (robots) {
            if (!robots.containsKey(host)) { 
                URL newUrl = new URL(protocol, host, port, "/robots.txt");
                HttpURLConnection connection = (HttpURLConnection) newUrl.openConnection();
                connection.setRequestProperty("User-Agent", "Mozilla");
                if (connection.getResponseCode() == 200) {
                    InputStreamReader reader = new InputStreamReader(connection.getInputStream());
                    StringBuilder builder = new StringBuilder();
                    char[] buf = new char[1024];
                    int count;
                    do {
                        count = reader.read(buf);
                        if (count != -1) {
                            builder.append(buf, 0, count);
                        }
                    } while (count != -1);
                    String robots_text = builder.toString();
                    robots.put(newUrl.getHost(), robots_text);
                    System.out.println(robots_text);
                } else {
                    robots.put(newUrl.getHost(), "");
                }
            }
        }
        NoRobotClient noRobotClient = new NoRobotClient("Mozilla");
        noRobotClient.parse(new URL(protocol, host, port, "/"));
//        noRobotClient.parseText(robots.get(host));
        return noRobotClient;
    }
}
