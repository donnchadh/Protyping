package org.donnchadh.gaelbot.urlprocessors;

import java.io.IOException;
import java.net.URL;

public interface UrlProcessor {
    void processUrl(URL url) throws IOException;
}
