package org.donnchadh.gaelbot.tasks.factory;

public interface LinkVisitorTaskFactory {
	Runnable buildLinkVisitorTask(String newLink);
}
