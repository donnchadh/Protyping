/**
 * 
 */
package org.donnchadh.gaelbot;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.osjava.norbert.NoRobotClient;
import org.osjava.norbert.NoRobotException;

final class RobotsChecker {
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