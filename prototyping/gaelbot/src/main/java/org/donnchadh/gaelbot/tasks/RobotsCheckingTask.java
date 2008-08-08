/**
 * 
 */
package org.donnchadh.gaelbot.tasks;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Queue;

import org.donnchadh.gaelbot.robots.RobotsChecker;
import org.osjava.norbert.NoRobotException;

public final class RobotsCheckingTask implements Runnable {
	private final RobotsChecker robotsChecker;
	private final Queue<String> hostQueue;

	public RobotsCheckingTask(RobotsChecker robotsChecker,
			Queue<String> hostQueue) {
		this.robotsChecker = robotsChecker;
		this.hostQueue = hostQueue;
	}

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
}