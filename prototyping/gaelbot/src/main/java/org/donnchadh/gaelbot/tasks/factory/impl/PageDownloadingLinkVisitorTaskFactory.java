/**
 * 
 */
package org.donnchadh.gaelbot.tasks.factory.impl;

import org.donnchadh.gaelbot.tasks.PageDownloadingLinkVisitorTask;
import org.donnchadh.gaelbot.tasks.factory.LinkVisitorTaskFactory;

public final class PageDownloadingLinkVisitorTaskFactory implements
		LinkVisitorTaskFactory {
	@Override
	public Runnable buildLinkVisitorTask(String newLink) {
		return new PageDownloadingLinkVisitorTask(newLink);
	}
}