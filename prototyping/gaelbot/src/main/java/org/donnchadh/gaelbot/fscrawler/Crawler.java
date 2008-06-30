package org.donnchadh.gaelbot.fscrawler;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;

public class Crawler {
    public Crawler() {
    }
    
    public static void main(String[] args) throws IOException {
        new Crawler().crawl(new File("/home/donnchadh/"), new PdfFilter());
    }
    
    private void crawl(File root, FileFilter filter) throws IOException {
        if (isSymLink(root)) {
            return;
        }
        if (root.isDirectory()) {
            for (File file: root.listFiles()) {
                crawl(file, filter);
            }
        } else {
            if (filter.accept(root)) {
                System.out.println(root.getAbsolutePath());
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
