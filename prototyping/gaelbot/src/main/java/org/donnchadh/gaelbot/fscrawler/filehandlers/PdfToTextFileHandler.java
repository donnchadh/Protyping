/**
 * 
 */
package org.donnchadh.gaelbot.fscrawler.filehandlers;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;

import org.pdfbox.pdmodel.PDDocument;
import org.pdfbox.util.PDFTextStripper;

public final class PdfToTextFileHandler extends AbstractTextFileGeneratingFileHandler {

	public PdfToTextFileHandler(FileFilter filter, File outputRoot,
			File root) {
		super(filter, outputRoot, root);
	}

	protected String convertFileToText(File file) throws IOException {
		PDDocument pdfDocument = PDDocument.load(file);
		String text;
		try {
			text = new PDFTextStripper().getText(pdfDocument);
		} finally {
			pdfDocument.close();
		}
		return text;
	}
}