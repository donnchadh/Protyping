package org.donnchadh.gaelbot.fscrawler;

import java.io.File;
import java.io.IOException;

import org.donnchadh.gaelbot.fscrawler.filehandlers.HtmlToTextFileHandler;
import org.donnchadh.gaelbot.services.HtmlFilter;

public class ConvertHtmlFilesToText {

    public static void main(String[] args) throws IOException {
        final File root = new File("ga");
        final File outputRoot = new File("text");
        AbstractFileHandler handler = new HtmlToTextFileHandler(new HtmlFilter(), outputRoot, root);
        new Crawler().crawl(root, handler);
    }

}
