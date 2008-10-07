/**
 * 
 */
package org.donnchadh.gaelbot.fscrawler.filehandlers;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;

import org.donnchadh.gaelbot.fscrawler.AbstractFileHandler;
import org.donnchadh.gaelbot.services.filehandler.DocumentImportingFileHandler;
import org.donnchadh.gaelbot.urlprocessors.impl.FileCachingUrlProcessor;
import org.pdfbox.pdmodel.PDDocument;
import org.pdfbox.util.PDFTextStripper;

public abstract class AbstractTextFileGeneratingFileHandler extends AbstractFileHandler {
	private final File outputRoot;
	private final File root;

	public AbstractTextFileGeneratingFileHandler(FileFilter filter, File outputRoot,
			File root) {
		super(filter);
		this.outputRoot = outputRoot;
		this.root = root;
	}

	@Override
	public void handle(File file) {
		URL url = DocumentImportingFileHandler.buildUrlFromFile(root, file);
		File path = FileCachingUrlProcessor.buildFileFromUrl(url, outputRoot);
		File parentFolder = path.getParentFile();
		parentFolder.mkdirs();
		path = new File(parentFolder, path.getName()+".txt");
		if (!path.exists()) {
		    System.out.println(path);
			try {
				String text = convertFileToText(file);
		        path.createNewFile();
		        PrintWriter writer = new PrintWriter(path, "UTF-8");
		        try {
		        	writer.println(text);
		        } finally {
		        	writer.close();
		        }
			} catch (IOException e) {
				// Move on to next file
				e.printStackTrace();
			} catch (NullPointerException e) {
				e.printStackTrace();
			} catch (RuntimeException e) {
				e.printStackTrace();
			}
		}
	}

	protected abstract String convertFileToText(File file) throws IOException;
}