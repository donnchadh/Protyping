package org.donnchadh.gaelbot.domainmodel;

import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

@Entity
public class Document {

    @Id
    @GeneratedValue
    private Long id;
    
    @ManyToOne
    private RepositoryDocument repositoryDocument;
    private URL originalUrl;
    private URI uri;
    @ManyToOne
    private Language language;
    @Transient
    private Charset characterSet = Charset.forName("UTF-8");
    private String title;
    private String description;
    
    public Document(RepositoryDocument repositoryDocument, URL originalUrl, URI uri, Language language, Charset characterSet) {
        this.repositoryDocument = repositoryDocument;
        this.originalUrl = originalUrl;
        this.uri = uri;
        this.language = language;
        this.characterSet = characterSet;
    }
    
    public RepositoryDocument getRepositoryDocument() {
        return repositoryDocument;
    }
    
    public URL getOriginalUrl() {
        return originalUrl;
    }
    
    public URI getUri() {
        return uri;
    }
    
    public Language getLanguage() {
        return language;
    }
    
    public Charset getCharacterSet() {
        return characterSet;
    }
    
    public String getTitle() {
        return title;
    }
    
    public String getDescription() {
        return description;
    }
}
