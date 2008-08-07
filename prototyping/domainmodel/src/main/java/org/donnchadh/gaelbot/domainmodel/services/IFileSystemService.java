package org.donnchadh.gaelbot.domainmodel.services;

import java.io.File;

public interface IFileSystemService {

	byte[] getContent(File root, long id);

	long addContent(File root, byte[] bytes);

}
