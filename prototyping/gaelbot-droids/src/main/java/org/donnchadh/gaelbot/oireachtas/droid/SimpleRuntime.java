package org.donnchadh.gaelbot.oireachtas.droid;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.apache.droids.robot.crawler.CrawlingDroid;
import org.apache.droids.api.Link;
import org.apache.droids.delay.SimpleDelayTimer;
import org.apache.droids.exception.InvalidTaskException;
import org.apache.droids.handle.Sysout;
import org.apache.droids.helper.factories.DroidFactory;
import org.apache.droids.helper.factories.HandlerFactory;
import org.apache.droids.helper.factories.ParserFactory;
import org.apache.droids.helper.factories.ProtocolFactory;
import org.apache.droids.helper.factories.URLFiltersFactory;
import org.apache.droids.impl.DefaultTaskExceptionHandler;
import org.apache.droids.impl.SequentialTaskMaster;
import org.apache.droids.impl.SimpleTaskQueue;
import org.apache.droids.net.RegexURLFilter;
import org.apache.droids.parse.html.HtmlParser;
import org.apache.droids.protocol.http.DroidsHttpClient;
import org.apache.droids.protocol.http.HttpProtocol;
import org.apache.http.HttpVersion;
import org.apache.http.conn.params.ConnManagerParamBean;
import org.apache.http.conn.params.ConnPerRouteBean;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParamBean;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParamBean;
import org.apache.http.protocol.HTTP;

/**
 * Simple Droids runtime that wires various components together in Java code
 * without using a DI framework.
 * 
 */
public class SimpleRuntime {

    private final String targetURL;

    private SimpleRuntime(String targetURL) {
        this.targetURL = targetURL;
    }

    private void run() throws IOException, InvalidTaskException, InterruptedException {
        // Create parser factory. Support basic HTML markup only
        ParserFactory parserFactory = new ParserFactory();
        HtmlParser htmlParser = new HtmlParser();
        htmlParser.setElements(new HashMap<String, String>());
        htmlParser.getElements().put("a", "href");
        htmlParser.getElements().put("link", "href");
        htmlParser.getElements().put("img", "src");
        htmlParser.getElements().put("script", "src");
        parserFactory.setMap(new HashMap<String, Object>());
        parserFactory.getMap().put("text/html", htmlParser);

        // Create protocol factory. Support HTTP/S only.
        ProtocolFactory protocolFactory = new ProtocolFactory();
        
        // Create and configure HTTP client 
        HttpParams params = new BasicHttpParams(); 
        HttpProtocolParamBean hppb = new HttpProtocolParamBean(params); 
        HttpConnectionParamBean hcpb = new HttpConnectionParamBean(params); 
        ConnManagerParamBean cmpb = new ConnManagerParamBean(params); 
        
        // Set protocol parametes
        hppb.setVersion(HttpVersion.HTTP_1_1);
        hppb.setContentCharset(HTTP.ISO_8859_1);
        hppb.setUseExpectContinue(true);
        // Set connection parameters
        hcpb.setStaleCheckingEnabled(false);
        // Set connection manager parameters
        ConnPerRouteBean connPerRouteBean = new ConnPerRouteBean();
        connPerRouteBean.setDefaultMaxPerRoute(2);
        cmpb.setConnectionsPerRoute(connPerRouteBean);
        
        DroidsHttpClient httpclient = new DroidsHttpClient(params);
        
        HttpProtocol httpProtocol = new HttpProtocol(httpclient);
        protocolFactory.setMap(new HashMap<String, Object>());
        protocolFactory.getMap().put("http", httpProtocol);
        protocolFactory.getMap().put("https", httpProtocol);
        
        // Create URL filter factory.
        URLFiltersFactory filtersFactory = new URLFiltersFactory();
        RegexURLFilter defaultURLFilter = new RegexURLFilter();
        defaultURLFilter.setFile("classpath:/regex-urlfilter.txt");
        filtersFactory.setMap(new HashMap<String, Object>());
        filtersFactory.getMap().put("default", defaultURLFilter);
        
        // Create handler factory. Provide sysout handler only.
        HandlerFactory handlerFactory = new HandlerFactory();
        Sysout defaultHandler = new Sysout();
        handlerFactory.setMap(new HashMap<String, Object>());
        handlerFactory.getMap().put("default", defaultHandler);
        
        // Create droid factory. Leave it empty for now.
        DroidFactory<Link> droidFactory = new DroidFactory<Link>();
        droidFactory.setMap(new HashMap<String, Object>());
        
        // Create default droid 
        SimpleDelayTimer simpleDelayTimer = new SimpleDelayTimer();
        simpleDelayTimer.setDelayMillis(100);
        
        SimpleTaskQueue<Link> simpleQueue = new SimpleTaskQueue<Link>();
       // simpleQueue.setMaxDepth(3);
       // simpleQueue.setMaxSize(-1);

        SequentialTaskMaster<Link> taskMaster = new SequentialTaskMaster<Link>();
        taskMaster.setDelayTimer( simpleDelayTimer );
        taskMaster.setExceptionHandler( new DefaultTaskExceptionHandler() );
        
        CrawlingDroid helloCrawler = new CrawlingDroid( simpleQueue, taskMaster );
        helloCrawler.setFiltersFactory(filtersFactory);
        helloCrawler.setParserFactory(parserFactory);
        helloCrawler.setProtocolFactory(protocolFactory);
        helloCrawler.setHandlerFactory(handlerFactory);
        
        Collection<String> initialLocations = new ArrayList<String>();
        initialLocations.add( targetURL );
        helloCrawler.setInitialLocations(initialLocations);
        
        // Initialize and start the crawler
        helloCrawler.init();
        helloCrawler.start();
        
        // Await termination
        helloCrawler.getTaskMaster().awaitTermination(0, TimeUnit.MILLISECONDS);
        // Shut down the HTTP connection manager
        httpclient.getConnectionManager().shutdown();
    }

    public static void main(String[] args) throws Exception {

        if (args.length < 1) {
            System.out.println("Please specify a URL to crawl");
            System.exit(-1);
        }
        String targetURL = args[0];
        new SimpleRuntime(targetURL).run();
    }

}
