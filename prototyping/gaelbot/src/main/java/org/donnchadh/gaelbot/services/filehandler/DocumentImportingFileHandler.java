/**
 * 
 */
package org.donnchadh.gaelbot.services.filehandler;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.donnchadh.gaelbot.fscrawler.AbstractFileHandler;
import org.donnchadh.gaelbot.persistence.DocumentService;

public final class DocumentImportingFileHandler extends
		AbstractFileHandler {
	private final File root;
	
	private DocumentService documentService; 

	public DocumentImportingFileHandler(DocumentService documentService, FileFilter filter, File root) {
		super(filter);
		this.documentService = documentService;
		this.root = root;
	}

	@Override
	public void handle(File file) {
	    URL originalUrl = buildUrlFromFile(root, file);
	    documentService.importDocumentFile(file, originalUrl);
	}

	public static URL buildUrlFromFile(final File root, File file) {
		File pathComponent = file;
	    StringBuilder path = new StringBuilder();
	    String hostname = null;
	    try {
	        while (hostname == null) {
	            if (pathComponent.getParentFile().getCanonicalFile().equals(root.getCanonicalFile())) {
	                hostname = pathComponent.getName();
	            } else {
	            	if (path.length() == 0) {
	            		path.append(pathComponent.getName());
	            	} else {
						path.insert(0, pathComponent.getName() + "/");
					}
	                pathComponent= pathComponent.getParentFile();
	            }
	        }
	    } catch (IOException e) {
	        throw new RuntimeException(e);
	    }
	    // TODO
	    URL originalUrl;
	    try {
	        originalUrl = new URL("http", hostname, 80, path.toString());
	    } catch (MalformedURLException e) {
	        throw new RuntimeException(e);
	    }
		return originalUrl;
	}
}