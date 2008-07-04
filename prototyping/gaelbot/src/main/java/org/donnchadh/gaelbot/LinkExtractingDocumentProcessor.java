/**
 * 
 */
package org.donnchadh.gaelbot;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;

class LinkExtractingDocumentProcessor implements DocumentProcessor {
    private NodeList links = new NodeList();
    
    public void processDocument(NodeList top) {
        links = extractLinks(top);
    }

    public NodeList getLinks() {
        return links;
    }
    
    private NodeList extractLinks(NodeList top) {
        NodeList links;
        NodeFilter linkFilter = new NodeFilter() {

            public boolean accept(Node node) {
                return node instanceof LinkTag;
            }
        };
        links = top.extractAllNodesThatMatch(linkFilter);
        return links;
    }
    
    

}