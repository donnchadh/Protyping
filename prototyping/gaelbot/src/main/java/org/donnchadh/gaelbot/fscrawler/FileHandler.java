package org.donnchadh.gaelbot.fscrawler;

import java.io.File;
import java.io.FileFilter;

public interface FileHandler extends FileFilter {
    void handle(File file);
}
