package org.donnchadh.gaelbot.simple;

import java.io.File;
import java.io.IOException;

import org.pdfbox.pdmodel.PDDocument;
import org.pdfbox.util.PDFText2HTML;
import org.pdfbox.util.PDFTextStripper;

public class PDFBot {
	public static void main(String[] args) throws IOException {
		File file = new File("ga/193.178.1.117/attached_files/Pdf%20files/COMHSHAOILOIDHREACHTAAGUSRIALTAISAITIUIL.pdf");
		PDDocument pdfDocument =  PDDocument.load(file);
		String text = new PDFTextStripper().getText(pdfDocument);
		System.out.println(text);
	}
}
