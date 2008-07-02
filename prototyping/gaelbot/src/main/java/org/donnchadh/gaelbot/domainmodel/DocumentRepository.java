package org.donnchadh.gaelbot.domainmodel;

import java.io.File;

import org.donnchadh.gaelbot.services.FileSystemService;

public class DocumentRepository {
    private File root;
    private final FileSystemService fileSystemService;
    
    public DocumentRepository(String path, FileSystemService fileSystemService) {
        root = new File(path);
        if (!root.exists()) {
            throw new IllegalArgumentException("Path doesn't exist");
        }
        this.fileSystemService = fileSystemService;
    }
    
    public RepositoryDocument getDocument(long id) {
        return new RepositoryDocument(this, id);
    }
    
    public byte[] getContent(long id) {
        return fileSystemService.getContent(root, id);
    }
}
