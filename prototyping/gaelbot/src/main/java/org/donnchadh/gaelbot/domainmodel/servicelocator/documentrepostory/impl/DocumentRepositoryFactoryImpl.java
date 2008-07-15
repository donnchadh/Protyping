package org.donnchadh.gaelbot.domainmodel.servicelocator.documentrepostory.impl;

import org.donnchadh.gaelbot.domainmodel.DocumentRepository;
import org.donnchadh.gaelbot.domainmodel.servicelocator.documentrepostory.DocumentRepositoryFactory;
import org.donnchadh.gaelbot.services.FileSystemService;

public class DocumentRepositoryFactoryImpl implements DocumentRepositoryFactory {
    private FileSystemService fileSystemService;
    
    public DocumentRepository getDocumentRepository(String path) {
        // TODO add caching
        return new DocumentRepository(path, fileSystemService);
    }

    public void setFileSystemService(FileSystemService fileSystemService) {
        this.fileSystemService = fileSystemService;
    }
}
