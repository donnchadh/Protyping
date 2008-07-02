package org.donnchadh.gaelbot;

import java.io.IOException;
import java.net.URL;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.nodes.TextNode;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.htmlparser.util.SimpleNodeIterator;

public class OireachtoCleaner extends AbstractCleaner {
    public String clean(NodeList top) {
        NodeList nodes =  top.extractAllNodesThatMatch(new NodeFilter(){
            public boolean accept(Node node) {
                return node instanceof TextNode;
            }});
        StringBuilder builder = new StringBuilder();
        boolean lastWasSpace = false;
        for (SimpleNodeIterator i = nodes.elements(); i.hasMoreNodes();) {
            Node n = i.nextNode();
            if (!n.getText().trim().isEmpty()) {
                builder.append(n.getText());
                builder.append(" ");
                lastWasSpace = false;
            } else {
                if (!lastWasSpace) {
                    builder.append(n.getText());
                    lastWasSpace = true;
                } 
            }
        }
        return builder.toString();
    }
}
