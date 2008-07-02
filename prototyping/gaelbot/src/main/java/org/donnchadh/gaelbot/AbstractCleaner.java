package org.donnchadh.gaelbot;

import java.io.IOException;
import java.net.URL;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

public abstract class AbstractCleaner {
    public abstract String clean(NodeList top);
    
    public String clean(String input) {
        Parser parser = Parser.createParser(input, "UTF-8");
        return clean(parser);
    }

    public String clean(URL input) {
        Parser parser;
        try {
            parser = new Parser(input.openConnection());
        } catch (ParserException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return clean(parser);
    }
    
    public String clean(Parser parser) {
        NodeList top;
        try {
            top = parser.parse(new NodeFilter(){
                public boolean accept(Node arg0) {
                    return true;
                }});
        } catch (ParserException e) {
            throw new RuntimeException(e);
        }
        return clean(top);
    }

}
