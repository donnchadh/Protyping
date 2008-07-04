package org.donnchadh.gaelbot;

import java.io.IOException;
import java.net.URL;

public interface UrlProcessor {
    void processUrl(URL url) throws IOException;
}
