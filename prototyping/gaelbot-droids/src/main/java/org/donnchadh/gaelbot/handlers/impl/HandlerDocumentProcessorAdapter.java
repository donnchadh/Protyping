package org.donnchadh.gaelbot.handlers.impl;

import org.donnchadh.gaelbot.documentprocessors.DocumentProcessor;
import org.donnchadh.gaelbot.urlprocessors.impl.DocumentHandlingUrlProcessor;

public class HandlerDocumentProcessorAdapter extends HandlerUrlProcessorAdapter {
    
    public HandlerDocumentProcessorAdapter(DocumentProcessor documentProcessor) {
        super(new DocumentHandlingUrlProcessor(documentProcessor));
    }

}
