package org.donnchadh.gaelbot.urlcollector;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.donnchadh.gaelbot.crawler.AbstractBot;
import org.donnchadh.gaelbot.urlprocessors.CompositeUrlProcessor;
import org.donnchadh.gaelbot.urlprocessors.UrlProcessor;
import org.donnchadh.gaelbot.urlprocessors.impl.DocumentHandlingUrlProcessor;
import org.donnchadh.gaelbot.urlprocessors.impl.FileCachingUrlProcessor;
import org.donnchadh.gaelbot.urlprocessors.impl.LinkExtractingDocumentProcessor;
import org.donnchadh.gaelbot.util.CsvLineHandler;
import org.donnchadh.gaelbot.util.CsvReaderHelper;
import org.htmlparser.Tag;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.htmlparser.visitors.NodeVisitor;

public class UrlCollector {
	public static void main(String[] args) {
		String[] urls = new UrlCollector().collectUrls(new File("tuples.csv"));
		for (String url : urls) {
			System.out.println(url);
		}
	}
	
	public String[] collectUrls(File tuples) {
		return collectUrls(readTuples(tuples));
	}
	
	public String[] collectUrls(String[][] tuples) {
		List<String> documentUrls = new ArrayList<String>();
		String[] googleUrls = buildGoogleUrls(tuples);
		PrintWriter writer;
		try {
			writer = new PrintWriter(new File("urls.txt"), "UTF-8");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		try {
			for (String googleUrl : googleUrls) {
	            URL url = new URL(googleUrl);
	            LinkExtractingDocumentProcessor documentProcessor = new LinkExtractingDocumentProcessor();
//	            UrlProcessor urlProcessor = new CompositeUrlProcessor(new FileCachingUrlProcessor("ga"), new DocumentHandlingUrlProcessor(documentProcessor));
	            UrlProcessor urlProcessor = new DocumentHandlingUrlProcessor(documentProcessor);
				urlProcessor.processUrl(url);
				final List<String> newDocumentUrls = new ArrayList<String>();
	            NodeList links = documentProcessor.getLinks();
	            links.visitAllNodesWith(new NodeVisitor() {
	                @Override
	                public void visitTag(Tag tag) {
	                    if (tag instanceof LinkTag) {
	                        String linkUrl = ((LinkTag) tag).extractLink();
	                        boolean isNotGoogleUrl = !linkUrl.contains(".google.ie/")
	                                && !linkUrl.contains(".google.com/") && !linkUrl.contains("/search?q=cache:")
	                                && linkUrl.startsWith("http://");
	                        if (isNotGoogleUrl) {
	                        	newDocumentUrls.add(linkUrl);
	                        }
	                    }
	                }
	            });
	            for (String documentUrl : newDocumentUrls) {
					documentUrls.add(documentUrl);
					System.out.println(documentUrl);
					writer.println(documentUrl);
				}
	            writer.flush();
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (ParserException e) {
			throw new RuntimeException(e);
		} finally {
			writer.close();
		}
		return documentUrls.toArray(new String[documentUrls.size()]);
	}

	private String[] buildGoogleUrls(String[][] tuples) {
		List<String> googleUrls = new ArrayList<String>();
		for (String[] tuple : tuples) {
			try {
				String[] search = new String[tuple.length + 4	];
				search[0] = "allintext:";
				for (int i = 0; i < tuple.length; i++) {
					search[i+1] = tuple[i];
				}
				search[tuple.length+1] = "-and";
				search[tuple.length+2] = "-irish";
//				search[tuple.length+3] = "-if";
//				search[tuple.length+4] = "-but";
//				search[tuple.length+5] = "-irish";
//				search[tuple.length+6] = "-beurla";
//				search[tuple.length+7] = "-gu";
//				search[tuple.length+8] = "filetype:html"; 
				search[tuple.length+3] = "filetype:html"; 
				googleUrls.add(AbstractBot.buildGoogleUrl(search));
			} catch (UnsupportedEncodingException e) {
				throw new RuntimeException(e);
			}
		}
		return googleUrls.toArray(new String[googleUrls.size()]);
	}

	private String[][] readTuples(File wordFile) {
		final List<String[]> words = new ArrayList<String[]>();
		new CsvReaderHelper().readFile(wordFile, new CsvLineHandler(){ public void handle(String[] line) { 
			words.add(line);}}
		);
        return words.toArray(new String[words.size()][]);
	}

}
