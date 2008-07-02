package org.donnchadh.gaelbot.services;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;

import org.donnchadh.gaelbot.AbstractBot;
import org.donnchadh.gaelbot.domainmodel.Document;
import org.donnchadh.gaelbot.domainmodel.DocumentRepository;
import org.donnchadh.gaelbot.domainmodel.Language;
import org.donnchadh.gaelbot.domainmodel.RepositoryDocument;
import org.donnchadh.gaelbot.fscrawler.AbstractFileHandler;
import org.donnchadh.gaelbot.fscrawler.Crawler;

public class DocumentImportService {
    private static final Charset UTF_8 = Charset.forName(AbstractBot.UTF_8);
    private final DocumentRepository documentRepository;

    public DocumentImportService(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }
    
    public static void main(String[] args) throws IOException {
        DocumentImportService documentImportService = new DocumentImportService(new DocumentRepository("repository", new FileSystemService()));
        documentImportService.importDocuments(new File("en"));
    }
    
    public void importDocuments(File root) throws IOException {
        HtmlFilter filter = new HtmlFilter();
        AbstractFileHandler handler = new AbstractFileHandler(filter) {
                    @Override
                    public void handle(File file) {
                        importDocumentFile(file);
                    }
                };
        new Crawler().crawl(root, handler);
    }
    
    protected Document importDocumentFile(File file) {
        RepositoryDocument repositoryDocument = documentRepository.importDocument(file);
        Charset characterSet = UTF_8;
        // TODO
        URL originalUrl = null;
        URI uri = null;
        Language language = null;
        Document document = new Document(repositoryDocument, originalUrl, uri, language, characterSet);
        return document;
    }
    
    static class HtmlFilter implements FileFilter {

        public boolean accept(File file) {
            return file.getName().toLowerCase().endsWith(".html");
        }
        
    }
}
