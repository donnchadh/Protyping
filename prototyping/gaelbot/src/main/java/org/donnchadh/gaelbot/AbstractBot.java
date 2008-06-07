package org.donnchadh.gaelbot;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
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
import org.osjava.norbert.NoRobotClient;
import org.osjava.norbert.NoRobotException;

public abstract class AbstractBot implements Runnable {

    protected static final class RobotsChecker {
            private final Map<String, String> robots = new HashMap<String, String>();
            
            public RobotsChecker() {
            }
            
            public boolean checkRobots(URL url) throws IOException, IllegalStateException, IllegalArgumentException {
                String host = url.getHost();
                if (host.equals("www.google.ie")) {
                    return true;
                }
                String protocol = url.getProtocol();
                int port = url.getPort();
                NoRobotClient noRobotClient;
                try {
                    noRobotClient = checkRobots(host, protocol, port);
                } catch (NoRobotException e) {
                    return true;
                }
                return noRobotClient.isUrlAllowed(url);
            }
    
            public NoRobotClient checkRobots(String host, String protocol, int port) throws MalformedURLException, IOException, NoRobotException {
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
                if (robots.get(host).isEmpty()) {
                    return new NoRobotClient("Mozilla"){@Override
                    public boolean isUrlAllowed(URL url) throws IllegalStateException, IllegalArgumentException {
                        return true;
                    }};
                }
                NoRobotClient noRobotClient = new NoRobotClient("Mozilla");
                noRobotClient.parse(new URL(protocol, host, port, "/"));
    //            noRobotClient.parseText(robots.get(host));
                return noRobotClient;
            }
        }

    protected static class LinkVisitorTask implements Runnable {
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
                    Parser parser = buildParser(url);
                    if (parser != null) {
                        NodeFilter linkFilter = new NodeFilter() {
                            
                            public boolean accept(Node node) {
                                return node instanceof LinkTag;
                            }
                        };
                        NodeList top = parser.parse(new NodeFilter(){
                            public boolean accept(Node arg0) {
                                return true;
                            }});
                        processDocument(top);
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

            protected Parser buildParser(URL url) throws IOException, ParserException {
                Parser parser;
                HttpURLConnection connection = openConnection(url);
                if (connection.getResponseCode() == 200) {
                    Parser parser1 = new Parser(connection);
                    parser = parser1;
                } else {
                    parser = null;
                }
                return parser;
            }

            protected HttpURLConnection openConnection(URL url) throws IOException {
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestProperty("User-Agent", "Mozilla");
                // connection.setFollowRedirects(true);
                connection.connect();
                return connection;
            }

            protected void processDocument(NodeList top) {
            }

        }

    protected static final String UTF_8 = "UTF-8";

    public static String buildGoogleUrl(String... focail) throws UnsupportedEncodingException {
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

}
