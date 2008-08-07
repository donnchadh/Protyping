package org.donnchadh.gaelbot.domainmodel;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Language {
    @Id
    @GeneratedValue
    private Long id;
    
    private String name;
    
    private String code;
}
