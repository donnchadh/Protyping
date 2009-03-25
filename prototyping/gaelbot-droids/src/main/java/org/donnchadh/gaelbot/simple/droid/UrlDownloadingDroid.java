package org.donnchadh.gaelbot.simple.droid;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;

import org.apache.droids.api.Link;
import org.apache.droids.api.TaskMaster;
import org.apache.droids.api.TaskQueue;
import org.apache.droids.exception.InvalidTaskException;
import org.apache.droids.helper.factories.HandlerFactory;
import org.apache.droids.impl.MultiThreadedTaskMaster;
import org.apache.droids.impl.SimpleTaskQueue;
import org.apache.droids.robot.crawler.CrawlingDroid;
import org.donnchadh.gaelbot.handlers.impl.HandlerUrlProcessorAdapter;
import org.donnchadh.gaelbot.urlprocessors.impl.FileCachingUrlProcessor;
import org.donnchadh.gaelbot.util.CsvLineHandler;
import org.donnchadh.gaelbot.util.CsvReaderHelper;

public class UrlDownloadingDroid extends CrawlingDroid implements Runnable {

    public UrlDownloadingDroid(TaskQueue<Link> queue, TaskMaster<Link> taskMaster) {
        super(queue, taskMaster);
    }

	/**
     * @param args
     */
    public static void main(String[] args) {
        MultiThreadedTaskMaster<Link> taskMaster = new MultiThreadedTaskMaster<Link>();
        taskMaster.setMaxThreads( 3 );
        
        TaskQueue<Link> queue = new SimpleTaskQueue<Link>();
        
//        Collection<String> locations = new ArrayList<String>();
//        locations.add( args[0] );

        UrlDownloadingDroid simple = new UrlDownloadingDroid( queue, taskMaster );
        HandlerFactory handlerFactory = new HandlerFactory();
        HandlerUrlProcessorAdapter defaultHandler = new HandlerUrlProcessorAdapter(new FileCachingUrlProcessor("ga"));
        handlerFactory.setMap(new HashMap<String, Object>());
        handlerFactory.getMap().put("default", defaultHandler);
        simple.setHandlerFactory(handlerFactory);
        simple.run();
    }

    public void run() {
    	final Queue<String> urlQueue = readUrls(new File("urls_su.txt"));
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

	private Queue<String> readUrls(File file) {
		final ConcurrentLinkedQueue<String> urls = new ConcurrentLinkedQueue<String>();
		new CsvReaderHelper().readFile(file, new CsvLineHandler(){ public void handle(String[] line) { 
	            urls.add(line[0]);}}
		);
		return urls;
	}

}
