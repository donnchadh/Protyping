package org.donnchadh.gaelbot.domainmodel.servicelocator.documentrepostory.impl;

import java.io.IOException;

import org.donnchadh.gaelbot.domainmodel.DocumentRepository;
import org.donnchadh.gaelbot.domainmodel.servicelocator.documentrepostory.DocumentRepositoryFactory;
import org.donnchadh.gaelbot.services.FileSystemService;

public class DocumentRepositoryFactoryImpl implements DocumentRepositoryFactory {
    private final FileSystemService fileSystemService;
    
    private final String defaultRepository;

    public DocumentRepositoryFactoryImpl(FileSystemService fileSystemService, String defaultRepository) {
        this.fileSystemService = fileSystemService;
        this.defaultRepository = defaultRepository;
    }
    
    public DocumentRepository getDocumentRepository(String path) {
        // TODO add caching
        try {
            return new DocumentRepository(path, fileSystemService);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public DocumentRepository getDefaultDocumentRepository() {
        return getDocumentRepository(defaultRepository);
    }

}
