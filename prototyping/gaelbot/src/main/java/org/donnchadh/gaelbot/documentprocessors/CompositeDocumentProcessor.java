package org.donnchadh.gaelbot.documentprocessors;

import org.htmlparser.util.NodeList;

public class CompositeDocumentProcessor implements DocumentProcessor {

    private final DocumentProcessor[] processors;

    public CompositeDocumentProcessor(DocumentProcessor[] processors) {
        this.processors = processors;
    }
    
    public void processDocument(NodeList top) {
        for (DocumentProcessor processor : processors) {
            processor.processDocument(top);
        }
    }

}
