package com.vogle.entity;

import jakarta.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(
        name = "entries",
        uniqueConstraints = @UniqueConstraint(columnNames = {"term", "language"})
)
public class Entry {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String term;

    @Column(nullable = false)
    private String language;

    private String grammaticalClass;

    @OneToMany(mappedBy = "entry", cascade = CascadeType.ALL)
    private List<Translation> translations;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getGrammaticalClass() {
        return grammaticalClass;
    }

    public void setGrammaticalClass(String grammaticalClass) {
        this.grammaticalClass = grammaticalClass;
    }

    public List<Translation> getTranslations() {
        return translations;
    }

    public void setTranslations(List<Translation> translations) {
        this.translations = translations;
    }
}
