/**
 * 
 */
package org.donnchadh.gaelbot.urlprocessors.impl;

import org.donnchadh.gaelbot.documentprocessors.DocumentProcessor;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;

public class LinkExtractingDocumentProcessor implements DocumentProcessor {
    public static class LinkNodeFilter implements NodeFilter {
		public boolean accept(Node node) {
		    return node instanceof LinkTag;
		}
	}

	private NodeList links = new NodeList();
    
    public void processDocument(NodeList top) {
        links = extractLinks(top);
    }

    public NodeList getLinks() {
        return links;
    }
    
    private NodeList extractLinks(NodeList top) {
        NodeList links;
        NodeFilter linkFilter = new LinkNodeFilter();
        links = top.extractAllNodesThatMatch(linkFilter);
        return links;
    }
    
    

}