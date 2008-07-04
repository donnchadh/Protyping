/**
 * 
 */
package org.donnchadh.gaelbot;

import java.io.IOException;
import java.net.URL;

import org.donnchadh.gaelbot.documentprocessors.CompositeDocumentProcessor;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

class LinkVisitorUrlProcessor implements UrlProcessor {
    private final DocumentProcessor documentProcessor;

    public LinkVisitorUrlProcessor(DocumentProcessor... documentProcessors) {
        if (documentProcessors.length == 1) {
            documentProcessor = documentProcessors[0];
        } else {
            documentProcessor = new CompositeDocumentProcessor(documentProcessors);
        }
    }

    public void processUrl(URL url) throws IOException {
        processUrl(url, documentProcessor);
    }
    
    protected void processUrl(URL url, DocumentProcessor documentProcessor) throws IOException {
        try {
            Parser parser = LinkVisitorTask.buildParser(url);
            if (parser != null) {
                NodeList top = parser.parse(new NodeFilter() {
                    public boolean accept(Node arg0) {
                        return true;
                    }
                });
                documentProcessor.processDocument(top);
                System.out.println(url.toString());
            }
        } catch (ParserException e) {
            // TODO
        }
    }
}