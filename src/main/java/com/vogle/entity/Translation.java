package com.vogle.entity;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(
        name = "translations",
        uniqueConstraints = @UniqueConstraint(columnNames = {"entry_id", "target_language"})
)
public class Translation {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "entry_id", nullable = false)
    private Entry entry;

    @Column(name = "target_language", nullable = false)
    private String targetLanguage;

    @Column(nullable = false)
    private String translation;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Entry getEntry() {
        return entry;
    }

    public void setEntry(Entry entry) {
        this.entry = entry;
    }

    public String getTargetLanguage() {
        return targetLanguage;
    }

    public void setTargetLanguage(String targetLanguage) {
        this.targetLanguage = targetLanguage;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }
}
