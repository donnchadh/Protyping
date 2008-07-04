/**
 * 
 */
package org.donnchadh.gaelbot;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

class FileCachingUrlProcessor implements UrlProcessor {
    private final String targetLanguage;

    public FileCachingUrlProcessor(String targetLanguage) {
        this.targetLanguage = targetLanguage;
    }

    public void processUrl(URL url) throws IOException {
        File file = new File(new File(new File(targetLanguage), url.getHost()), url.getPath());
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            file.createNewFile();
            URLConnection connection = DocumentHandlingUrlProcessor.openConnection(url);
            InputStream s = connection.getInputStream();
            byte[] b = new byte[16384];
            int c = 0;
            FileOutputStream o = new FileOutputStream(file);
            try {
                do {
                    o.write(b, 0, c);
                    c = s.read(b);
                } while (c > 0);
                o.flush();
            } finally {
                o.close();
            }
            s.close();
        }
    }
}