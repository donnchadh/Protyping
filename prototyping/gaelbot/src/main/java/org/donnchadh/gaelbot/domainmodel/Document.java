package org.donnchadh.gaelbot.domainmodel;

import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;

public class Document {
    private RepositoryDocument repositoryDocument;
    private URL originalUrl;
    private URI uri;
    private Language language;
    private Charset characterSet;
    
    public Document(RepositoryDocument repositoryDocument, URL originalUrl, URI uri, Language language, Charset characterSet) {
        this.repositoryDocument = repositoryDocument;
        this.originalUrl = originalUrl;
        this.uri = uri;
        this.language = language;
        this.characterSet = characterSet;
    }
}
