package org.donnchadh.gaelbot.services;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.URL;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import org.donnchadh.gaelbot.domainmodel.DocumentRepository;
import org.donnchadh.gaelbot.fscrawler.AbstractFileHandler;
import org.donnchadh.gaelbot.fscrawler.Crawler;
import org.donnchadh.gaelbot.persistence.DocumentService;
import org.donnchadh.gaelbot.persistence.JpaDocumentService;

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
    
    public void importDocuments(File root) throws IOException {
        HtmlFilter filter = new HtmlFilter();
        AbstractFileHandler handler = new AbstractFileHandler(filter) {
                    @Override
                    public void handle(File file) {
                        // TODO
                        URL originalUrl = null;
                        documentService.importDocumentFile(file, originalUrl);
                    }
                };
        new Crawler().crawl(root, handler);
    }
    
    static class HtmlFilter implements FileFilter {

        public boolean accept(File file) {
            return file.getName().toLowerCase().endsWith(".html");
        }
        
    }
}
