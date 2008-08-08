package org.donnchadh.gaelbot.documentprocessors;

import org.htmlparser.util.NodeList;

public interface DocumentProcessor {
    void processDocument(NodeList top);
}
