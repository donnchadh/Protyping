package org.donnchadh.gaelbot.domainmodel;

public class RepositoryDocument {
    private DocumentRepository repository;
    
    private long fileId;
    
    public RepositoryDocument(DocumentRepository repository, long fileId) {
        this.repository = repository;
        this.fileId = fileId;
    }
    
    public byte[] getContent() {
        return repository.getContent(fileId);
    }
}
