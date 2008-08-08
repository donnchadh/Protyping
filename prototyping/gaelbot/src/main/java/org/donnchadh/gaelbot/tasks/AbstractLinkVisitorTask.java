package org.donnchadh.gaelbot.tasks;

import java.io.IOException;
import java.net.URL;

import org.donnchadh.gaelbot.urlprocessors.CompositeUrlProcessor;
import org.donnchadh.gaelbot.urlprocessors.UrlProcessor;

public abstract class AbstractLinkVisitorTask implements Runnable {
    private final UrlProcessor urlProcessor;

    public AbstractLinkVisitorTask(UrlProcessor... urlProcessors) {
        if (urlProcessors.length == 1) { 
            urlProcessor = urlProcessors[0];
        } else {
            urlProcessor = new CompositeUrlProcessor(urlProcessors);
        }
    }
    
    public void processUrl(URL url) throws IOException {
        urlProcessor.processUrl(url);
    }
    
}
