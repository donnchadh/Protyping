package org.donnchadh.gaelbot.handlers.impl;

import java.io.IOException;
import java.net.URI;

import org.apache.droids.api.ContentEntity;
import org.apache.droids.api.Handler;
import org.apache.droids.exception.DroidsException;
import org.donnchadh.gaelbot.urlprocessors.UrlProcessor;

public class HandlerUrlProcessorAdapter implements Handler {
    
    private final UrlProcessor urlProcessor;

    public HandlerUrlProcessorAdapter(UrlProcessor urlProcessor) {
        this.urlProcessor = urlProcessor;
    }

    @Override
    public void handle(URI uri, ContentEntity entity) throws IOException, DroidsException {
        urlProcessor.processUrl(uri.toURL());
    }

}
