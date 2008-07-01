package org.donnchadh.gaelbot.fscrawler;

import java.io.File;
import java.io.FileFilter;

public class AbstractFileHandler implements FileHandler {

    private final FileFilter filter;

    public AbstractFileHandler(FileFilter filter) {
        this.filter = filter;
    }
    
    public void handle(File file) {
    }

    public boolean accept(File file) {
        return filter.accept(file);
    }

}
