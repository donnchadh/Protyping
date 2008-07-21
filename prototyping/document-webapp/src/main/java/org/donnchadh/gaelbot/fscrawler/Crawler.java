package org.donnchadh.gaelbot.fscrawler;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;

public class Crawler {
    public Crawler() {
    }
    
    public void crawl(File root, FileHandler handler) throws IOException {
        doCrawl(root.getCanonicalFile(), handler);
    }

    private void doCrawl(File root, FileHandler handler) throws IOException {
        if (isSymLink(root)) {
            return;
        }
        if (root.isDirectory()) {
            for (File file: root.listFiles()) {
                doCrawl(file, handler);
            }
        } else {
            if (handler.accept(root)) {
                handler.handle(root);
            }
        }
    }

    private boolean isSymLink(File root) throws IOException {
        return !root.getAbsolutePath().equals(root.getCanonicalPath());
    }

    static class PdfFilter implements FileFilter {

        public boolean accept(File file) {
            return file.getName().toLowerCase().endsWith(".pdf");
        }
        
    }
}
