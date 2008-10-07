/**
 * 
 */
package org.donnchadh.gaelbot.fscrawler.filehandlers;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;

import org.donnchadh.gaelbot.cleaners.SimpleCleaner;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

public final class HtmlToTextFileHandler extends AbstractTextFileGeneratingFileHandler {

	public HtmlToTextFileHandler(FileFilter filter, File outputRoot,
			File root) {
		super(filter, outputRoot, root);
	}

	protected String convertFileToText(File file) throws IOException {
		NodeList top;
		try {
			Parser parser = new Parser(file.toURI().toURL().openConnection());
//			parser.setEncoding("UTF-8")
			top = parser.parse(new NodeFilter() {
			    public boolean accept(Node arg0) {
			        return true;
			    }
			});
		} catch (ParserException e) {
			throw new RuntimeException(e);
		}
		return new SimpleCleaner().clean(top);
	}
}