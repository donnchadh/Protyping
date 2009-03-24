package org.donnchadh.gaelbot.simple.droid;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.droids.api.Link;
import org.apache.droids.api.TaskMaster;
import org.apache.droids.api.TaskQueue;
import org.apache.droids.exception.InvalidTaskException;
import org.apache.droids.impl.MultiThreadedTaskMaster;
import org.apache.droids.impl.SimpleTaskQueue;
import org.apache.droids.robot.crawler.CrawlingDroid;
import org.donnchadh.gaelbot.crawler.AbstractBot;
import org.donnchadh.gaelbot.robots.RobotsChecker;
import org.donnchadh.gaelbot.tasks.RobotsCheckingTask;
import org.donnchadh.gaelbot.tasks.UrlQueueProcessingTask;

public class GaelDroid extends CrawlingDroid implements Runnable {
    private static final String[] focail = { "agus", "tá", "bhí", "beidh", "sé", "sí", "níos", "mó", "ná", "chéile",
            "slán", "cúpla", "focal", "raibh", "maith", "agat", "ní", "níl", "mé", "tú", "bíonn", "bhíonn", "baineann",
            "má", "gaeilge" };

	private final ExecutorService executor = Executors.newFixedThreadPool(50);
	private final Queue<String> hostQueue = new ConcurrentLinkedQueue<String>();

    public GaelDroid(TaskQueue<Link> queue, TaskMaster<Link> taskMaster) {
        super(queue, taskMaster);
    }

	/**
     * @param args
     */
    public static void main(String[] args) {
        MultiThreadedTaskMaster<Link> taskMaster = new MultiThreadedTaskMaster<Link>();
        taskMaster.setMaxThreads( 3 );
        
        TaskQueue<Link> queue = new SimpleTaskQueue<Link>();
        
        Collection<String> locations = new ArrayList<String>();
        locations.add( args[0] );

        GaelDroid simple = new GaelDroid( queue, taskMaster );
        simple.run();
    }

    public void run() {
//    	final RobotsChecker robotsChecker = startRobotsChecker();
    	final Queue<String> urlQueue = buildGoogleUrls();
//        processUrls(robotsChecker, urlQueue);
        setInitialLocations( urlQueue );
        try {
            init();
        } catch (InvalidTaskException e) {
            throw new RuntimeException(e);
        }
        start();  
        try {
            getTaskMaster().awaitTermination(0, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

	private void processUrls(final RobotsChecker robotsChecker,
			final Queue<String> urlQueue) {
		final Set<String> processed = Collections.synchronizedSet(new HashSet<String>());
        new UrlQueueProcessingTask(urlQueue, executor, processed, robotsChecker, hostQueue).run();
	}

	protected RobotsChecker startRobotsChecker() {
		final RobotsChecker robotsChecker = new RobotsChecker();
        executor.execute(new RobotsCheckingTask(robotsChecker, hostQueue));
		return robotsChecker;
	}

	private Queue<String> buildGoogleUrls() {
		final Queue<String> urlQueue = new ConcurrentLinkedQueue<String>();
        addGoogleUrlsToQueue(urlQueue);
		return urlQueue;
	}

	private void addGoogleUrlsToQueue(final Queue<String> urlQueue) {
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
	}

}
