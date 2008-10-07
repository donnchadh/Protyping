package org.donnchadh.gaelbot.fscrawler;

import java.io.File;
import java.io.IOException;

import org.donnchadh.gaelbot.fscrawler.filehandlers.PdfToTextFileHandler;


public class ConvertPdfsToText {

    public static void main(String[] args) throws IOException {
        final File root = new File("ga");
        final File outputRoot = new File("text");
        Crawler.PdfFilter filter = new Crawler.PdfFilter();
        AbstractFileHandler handler = new PdfToTextFileHandler(filter, outputRoot, root);
        new Crawler().crawl(root, handler);
    }

}
