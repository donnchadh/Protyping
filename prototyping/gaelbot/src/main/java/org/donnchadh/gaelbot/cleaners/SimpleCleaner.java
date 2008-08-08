package org.donnchadh.gaelbot.cleaners;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.nodes.TextNode;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.SimpleNodeIterator;

public class SimpleCleaner extends AbstractCleaner {
    private static final class TextNodeFilter implements NodeFilter {
		public boolean accept(Node node) {
		    return node instanceof TextNode;
		}
	}

	public String clean(NodeList top) {
        NodeList nodes =  top.extractAllNodesThatMatch(new TextNodeFilter());
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
