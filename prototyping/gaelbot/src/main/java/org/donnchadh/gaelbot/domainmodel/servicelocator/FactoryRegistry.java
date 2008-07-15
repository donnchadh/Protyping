package org.donnchadh.gaelbot.domainmodel.servicelocator;

import org.donnchadh.gaelbot.domainmodel.servicelocator.documentrepostory.DocumentRepositoryFactory;
import org.donnchadh.gaelbot.services.FileSystemService;

public class FactoryRegistry {
    private DocumentRepositoryFactory documentRepositoryFactory;
    
    private FileSystemService fileSystemService;

	private static final FactoryRegistry INSTANCE = new FactoryRegistry(); 
    
    public static FactoryRegistry getInstance() {
        return INSTANCE;
    }

    public DocumentRepositoryFactory getDocumentRepositoryFactory() {
    	return documentRepositoryFactory;
    }
    
    public void setDocumentRepositoryFactory(DocumentRepositoryFactory documentRepositoryFactory) {
		this.documentRepositoryFactory = documentRepositoryFactory;
	}
    
    public void setFileSystemService(FileSystemService fileSystemService) {
        this.fileSystemService = fileSystemService;
    }
    
    public FileSystemService getFileSystemService() {
        return fileSystemService;
    }
}
