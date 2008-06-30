package org.donnchadh.gaelbot.fscrawler;

import java.io.File;
import java.io.IOException;

public class Crawler {
    public Crawler() {
    }
    
    public static void main(String[] args) throws IOException {
        new Crawler().crawl(new File("/home/donnchadh/"));
    }
    
    private void crawl(File root) throws IOException {
        if (isSymLink(root)) {
            return;
        }
        if (root.isDirectory()) {
            for (File file: root.listFiles()) {
                crawl(file);
            }
        } else if (isPdf(root)) {
            System.out.println(root.getAbsolutePath());
        }
    }

    private boolean isSymLink(File root) throws IOException {
        return !root.getAbsolutePath().equals(root.getCanonicalPath());
    }

    private boolean isPdf(File root) {
        return root.getName().toLowerCase().endsWith(".pdf");
    }
}
