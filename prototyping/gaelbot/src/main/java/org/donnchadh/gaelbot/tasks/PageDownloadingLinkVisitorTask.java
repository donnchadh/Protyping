/**
 * 
 */
package org.donnchadh.gaelbot.tasks;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.donnchadh.gaelbot.urlprocessors.UrlProcessor;
import org.donnchadh.gaelbot.urlprocessors.impl.FileCachingUrlProcessor;

public class PageDownloadingLinkVisitorTask extends AbstractLinkVisitorTask {

	private final String newLink;

	private PageDownloadingLinkVisitorTask(String newLink, UrlProcessor urlProcessor) {
		super(urlProcessor);
		this.newLink = newLink;
	}

	public PageDownloadingLinkVisitorTask(String newLink) {
		this(newLink, new FileCachingUrlProcessor("ga"));
	}

	public void run() {
		visitUrl(newLink);
	}

	protected void visitUrl(String newLink) {
		try {
			URL url = new URL(newLink);
			processUrl(url);
		} catch (MalformedURLException e) {
			// TODO
		} catch (IOException e) {
			// TODO
		}
	}

}