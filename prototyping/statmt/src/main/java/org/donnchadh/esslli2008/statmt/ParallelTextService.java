package org.donnchadh.esslli2008.statmt;

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.donnchadh.gaelbot.DocumentHandlingUrlProcessor;
import org.donnchadh.gaelbot.WordCounter;
import org.donnchadh.gaelbot.fscrawler.AbstractFileHandler;
import org.donnchadh.gaelbot.fscrawler.Crawler;
import org.htmlparser.Node;
import org.htmlparser.nodes.TextNode;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.tags.TableRow;
import org.htmlparser.util.NodeList;

public class ParallelTextService {
	private final Map<String, String[]> mappings = new HashMap<String, String[]>();
	private final String linkString;
    
    public ParallelTextService(String linkString) {
		this.linkString = linkString;
    }
    
    public static void main(String[] args) throws IOException {
        ParallelTextService parallelTextService = new ParallelTextService("[GA]");
        File root = new File("src/test/data/en");
		parallelTextService.importDocuments(root);
        ParallelTextService parallelTextService2 = new ParallelTextService("[EN]");
        File root2 = new File("src/test/data/ga");
		parallelTextService2.importDocuments(root2);
		String[][][] sentencePairs = generateSentencePairs(parallelTextService.getMappings(), parallelTextService2.getMappings());
		outputSentencePairsToCsv(sentencePairs);
		printSentencePairs(sentencePairs);
    }

	private static void outputSentencePairsToCsv(String[][][] sentencePairs) throws FileNotFoundException, UnsupportedEncodingException {
		File csvFile = new File("sentencePairs.csv");
		PrintWriter csvWriter = new PrintWriter(csvFile, "UTF-8");
		try {
			for (String[][] pair : sentencePairs) {
				for (String[] sentence : pair) {
					for (int i = 0; i < sentence.length; i++) {
						if (i != 0) {
							csvWriter.append(' ');
						}
						csvWriter.append(sentence[i]);
					}
					csvWriter.append(',');
				}
				csvWriter.println();
			}
		} finally {
			csvWriter.close();
		}
	}

	private static void printSentencePairs(String[][][] sentencePairs) {
		for (String[][] pair : sentencePairs) {
			System.out.println(Arrays.toString(pair[0]));
			System.out.println(Arrays.toString(pair[1]));
			System.out.println("==========================");
		}
		System.out.println();
		System.out.println(sentencePairs.length);
	}
    
    private static String[][][] generateSentencePairs(
			Map<String, String[]> mappings2, Map<String, String[]> mappings3) {
    	WordCounter gaWordCounter = new WordCounter("ga");
    	WordCounter enWordCounter = new WordCounter("en");
    	List<String[][]> pairs = new ArrayList<String[][]>();
    	for (Map.Entry<String, String[]> mapping : mappings2.entrySet()) {
    		String[] target = mappings3.get(mapping.getValue()[1]);
    		if (target != null) { 
				String mappedText = target[0];
	    		if (mappedText != null) {
	    			String sourceText = mapping.getValue()[0];
	    			List<String> sourceWords = enWordCounter.getWords(sourceText);
	    			List<String> mappedWords = gaWordCounter.getWords(mappedText);
	    			if (!sourceWords.isEmpty() && !mappedWords.isEmpty()) {
						pairs.add(new String[][]{sourceWords.toArray(new String[sourceWords.size()]), 
								mappedWords.toArray(new String[mappedWords.size()])});
	    			}
	    		}
    		}
    	}
		return pairs.toArray(new String[pairs.size()][][]);
	}

	public void importDocuments(final File root) throws IOException {
        HtmlFilter filter = new HtmlFilter();
        AbstractFileHandler handler = new AbstractFileHandler(filter) {
                    @Override
                    public void handle(File file) {
                    	TranslationLinkExtractingDocumentProcessor linkExtractor = new TranslationLinkExtractingDocumentProcessor(linkString);
						DocumentHandlingUrlProcessor processor = new DocumentHandlingUrlProcessor(linkExtractor);
                    	try {
							processor.processUrl(file.toURI().toURL());
							NodeList links = linkExtractor.getLinks();
							for (int i = 0; i < links.size(); i++) {
								extractInfo((LinkTag)links.elementAt(i), file);
							}
							System.out.println(links.toHtml());
						} catch (MalformedURLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
                    }

					private void extractInfo(LinkTag linkTag, File file) {
						TableRow row = (TableRow)linkTag.getParent().getParent();
						Node paragraph = row.getLastChild().getFirstChild();
						Node anchor = row.getFirstChild().getFirstChild();
						if (anchor instanceof TextNode) {
							anchor = anchor.getNextSibling();
						}
						if (paragraph != null && paragraph.getFirstChild() != null && anchor instanceof LinkTag) {
							String text = paragraph.getFirstChild().getText();
							String srcId = "/" + file.getName() + "#" + ((LinkTag)anchor).getAttribute("name");
							String destId = linkTag.getAttribute("href");
							System.out.println(srcId + ": " + text + "," + destId);
							mappings.put(srcId, new String[]{text, destId});
						}
					}
                };
        new Crawler().crawl(root, handler);
    }
    
    static class HtmlFilter implements FileFilter {
        public boolean accept(File file) {
            return file.getName().toLowerCase().endsWith(".html");
        }
    }
    
    public Map<String, String[]> getMappings() {
		return mappings;
	}
}
