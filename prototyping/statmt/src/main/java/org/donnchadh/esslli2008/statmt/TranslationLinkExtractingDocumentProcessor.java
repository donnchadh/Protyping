package org.donnchadh.esslli2008.statmt;

import org.donnchadh.gaelbot.urlprocessors.impl.LinkExtractingDocumentProcessor;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;


public class TranslationLinkExtractingDocumentProcessor extends
		LinkExtractingDocumentProcessor {
	private final String linkString;

	public TranslationLinkExtractingDocumentProcessor(String linkString) {
		this.linkString = linkString;
	}

	protected NodeFilter buildFilter() {
		NodeFilter linkFilter = new LinkNodeFilter() {
			@Override
			public boolean accept(Node node) {
				return super.accept(node) && node.getFirstChild() != null && node.getFirstChild().getText().equals(linkString);
			}
		};
		return linkFilter;
	}

}
