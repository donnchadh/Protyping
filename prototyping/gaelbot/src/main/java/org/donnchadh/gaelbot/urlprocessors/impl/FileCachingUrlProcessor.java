	/**
 * 
 */
package org.donnchadh.gaelbot.urlprocessors.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import org.donnchadh.gaelbot.urlprocessors.UrlProcessor;

public class FileCachingUrlProcessor implements UrlProcessor {
    private final String targetLanguage;

    public FileCachingUrlProcessor(String targetLanguage) {
        this.targetLanguage = targetLanguage;
    }

    public void processUrl(URL url) throws IOException {
    	File parent = new File(targetLanguage);
        File file = buildFileFromUrl(url, parent);
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

	protected File buildFileFromUrl(URL url, File parent) {
		String path = url.getPath();
        if (url.getQuery() != null) {
	        if (!path.endsWith("/")) {
	        	path += "/";
	        }
	        path += url.getQuery() + ".html";
        } else {
	        if (path.endsWith("/")) {
	        	path += "index.html";
	        } else if (path.lastIndexOf('.') < (path.length() - 5)) {
	        	path += ".html";
	        }
        }
		File file = new File(new File(parent, url.getHost()), path);
		return file;
	}
}