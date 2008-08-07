package org.donnchadh.gaelbot.domainmodel.servicelocator.documentrepostory;

import org.donnchadh.gaelbot.domainmodel.DocumentRepository;

public interface DocumentRepositoryFactory {
    DocumentRepository getDocumentRepository(String path);

    DocumentRepository getDefaultDocumentRepository();
}
