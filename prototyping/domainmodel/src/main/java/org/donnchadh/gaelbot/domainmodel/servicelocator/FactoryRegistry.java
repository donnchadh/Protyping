package org.donnchadh.gaelbot.domainmodel.servicelocator;

import org.donnchadh.gaelbot.domainmodel.servicelocator.documentrepostory.DocumentRepositoryFactory;
import org.donnchadh.gaelbot.domainmodel.services.IFileSystemService;

public class FactoryRegistry {
    private DocumentRepositoryFactory documentRepositoryFactory;
    
    private IFileSystemService fileSystemService;

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
    
    public void setFileSystemService(IFileSystemService fileSystemService) {
        this.fileSystemService = fileSystemService;
    }
    
    public IFileSystemService getFileSystemService() {
        return fileSystemService;
    }
}
