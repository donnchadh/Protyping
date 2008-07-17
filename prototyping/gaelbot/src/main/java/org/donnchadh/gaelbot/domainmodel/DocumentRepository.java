package org.donnchadh.gaelbot.domainmodel;

import java.io.File;
import java.io.IOException;

import org.donnchadh.gaelbot.services.FileSystemService;

public class DocumentRepository {
    private File root;
    private final FileSystemService fileSystemService;
    
    public DocumentRepository(String path, FileSystemService fileSystemService) throws IOException {
        root = new File(path);
        if (!root.exists()) {
            throw new IllegalArgumentException("Path doesn't exist: " + root.getCanonicalPath());
        }
        this.fileSystemService = fileSystemService;
    }
    
    public RepositoryDocument getDocument(long id) {
        return new RepositoryDocument(this, id);
    }
    
    public byte[] getContent(long id) {
        return fileSystemService.getContent(root, id);
    }

    public RepositoryDocument importDocument(File file) {
        // TODO get bytes from file
        byte[] bytes = new byte[]{};
        long id = fileSystemService.addContent(root, bytes);
        return new RepositoryDocument(this, id);
    }
    
    public String getRepositoryPath() {
        return root.getPath();
    }
}
