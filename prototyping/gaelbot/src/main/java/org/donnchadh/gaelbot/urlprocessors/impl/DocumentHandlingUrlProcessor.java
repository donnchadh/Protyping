/**
 * 
 */
package org.donnchadh.gaelbot.urlprocessors.impl;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import org.donnchadh.gaelbot.documentprocessors.CompositeDocumentProcessor;
import org.donnchadh.gaelbot.documentprocessors.DocumentProcessor;
import org.donnchadh.gaelbot.urlprocessors.UrlProcessor;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

public class DocumentHandlingUrlProcessor implements UrlProcessor {
    private final DocumentProcessor documentProcessor;

    public DocumentHandlingUrlProcessor(DocumentProcessor... documentProcessors) {
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
            Parser parser = DocumentHandlingUrlProcessor.buildParser(url);
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

    protected static Parser buildParser(URL url) throws IOException, ParserException {
        Parser parser;
        URLConnection connection = openConnection(url);
        if (!(connection instanceof HttpURLConnection) ||
                ((HttpURLConnection)connection).getResponseCode() == 200) {
            parser = new Parser(connection);
        } else {
            parser = null;
        }
        return parser;
    }

    protected static URLConnection openConnection(URL url) throws IOException {
        URLConnection connection =  url.openConnection();
        connection.setRequestProperty("User-Agent", "Mozilla");
        // connection.setFollowRedirects(true);
        connection.connect();
        return connection;
    }
}