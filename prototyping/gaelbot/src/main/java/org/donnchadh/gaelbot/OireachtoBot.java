package org.donnchadh.gaelbot;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.osjava.norbert.NoRobotException;

public class OireachtoBot extends AbstractBot implements Runnable {
    /**
     * @param args
     */
    public static void main(String[] args) {
        new OireachtoBot().run();
    }

    public void run() {
        final ExecutorService executor = Executors.newFixedThreadPool(50);
        final Queue<String> urlQueue = new ConcurrentLinkedQueue<String>();
        final Queue<String> hostQueue = new ConcurrentLinkedQueue<String>();
        final Set<String> processed = Collections.synchronizedSet(new HashSet<String>());
        final RobotsChecker robotsChecker = new RobotsChecker();
        urlQueue.add("http://acts.oireachtas.ie/ga.toc.decade.html");
        while (!urlQueue.isEmpty()) {
            final String newLink = urlQueue.remove();
            try {
                hostQueue.add(new URL(newLink).getHost());
            } catch (MalformedURLException e1) {
                e1.printStackTrace();
            }
            executor.execute(new LinkVisitorTask(newLink, processed, urlQueue, robotsChecker));
            while (urlQueue.isEmpty()) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

}
