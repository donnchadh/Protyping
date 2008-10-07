package org.donnchadh.gaelbot.services;

import java.io.File;
import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import org.donnchadh.gaelbot.domainmodel.DocumentRepository;
import org.donnchadh.gaelbot.fscrawler.AbstractFileHandler;
import org.donnchadh.gaelbot.fscrawler.Crawler;
import org.donnchadh.gaelbot.persistence.DocumentService;
import org.donnchadh.gaelbot.persistence.JpaDocumentService;
import org.donnchadh.gaelbot.services.filehandler.DocumentImportingFileHandler;

public class DocumentImportService {
    private DocumentService documentService;
    
    public DocumentImportService(DocumentService documentService) {
        this.documentService = documentService;
    }
    
    public static void main(String[] args) throws IOException {
        String repositoryPath = "repository";
        new File(repositoryPath).mkdirs();
        DocumentRepository repository = new DocumentRepository(repositoryPath, new FileSystemService());
        JpaDocumentService documentService = new JpaDocumentService(repository);
        EntityManager entityManager =  Persistence.createEntityManagerFactory("documentDatabase").createEntityManager();
        documentService.setEntityManager(entityManager);
        DocumentImportService documentImportService = new DocumentImportService(documentService);
        documentImportService.importDocuments(new File("en"));
    }
    
    public void importDocuments(final File root) throws IOException {
        HtmlFilter filter = new HtmlFilter();
        AbstractFileHandler handler = new DocumentImportingFileHandler(documentService, filter, root);
        new Crawler().crawl(root, handler);
    }
}
