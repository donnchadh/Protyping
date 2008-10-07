/**
 * 
 */
package org.donnchadh.gaelbot.services;

import java.io.File;
import java.io.FileFilter;

public class HtmlFilter implements FileFilter {

    public boolean accept(File file) {
        return file.getName().toLowerCase().endsWith(".html");
    }
    
}