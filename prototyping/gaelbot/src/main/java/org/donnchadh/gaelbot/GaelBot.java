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

public class GaelBot extends AbstractBot implements Runnable {
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
        final ExecutorService executor = Executors.newFixedThreadPool(50);
        final Queue<String> urlQueue = new ConcurrentLinkedQueue<String>();
        final Queue<String> hostQueue = new ConcurrentLinkedQueue<String>();
        final Set<String> processed = Collections.synchronizedSet(new HashSet<String>());
        final RobotsChecker robotsChecker = new RobotsChecker();
        for (String focal : focail) {
            for (String focal2 : focail) {
                for (String focal3 : focail) {
                    String newLink;
                    try {
                        newLink = AbstractBot.buildGoogleUrl(focal, focal2, focal3);
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
                            robotsChecker.checkRobots(hostQueue.remove(), "http", 80);
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
