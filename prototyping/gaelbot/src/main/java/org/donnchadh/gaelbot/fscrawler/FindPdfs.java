package org.donnchadh.gaelbot.fscrawler;

import java.io.File;
import java.io.IOException;

public class FindPdfs {

    public static void main(String[] args) throws IOException {
        File root = new File("/home/donnchadh/");
        Crawler.PdfFilter filter = new Crawler.PdfFilter();
        AbstractFileHandler handler = new AbstractFileHandler(filter) {
                    @Override
                    public void handle(File file) {
                        System.out.println(file.getAbsolutePath());
                    }
                };
        new Crawler().crawl(root, handler);
    }

}
