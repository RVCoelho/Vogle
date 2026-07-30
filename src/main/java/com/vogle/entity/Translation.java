package com.vogle.entity;

import jakarta.persistence.*;
import java.util.UUID;

import lombok.*;

/**
 * Represents the translation of an Entry into a target language.
 * Translations are cached to avoid unnecessary external API calls.
 */

@Entity
@Table(
        name = "translations",
        uniqueConstraints = @UniqueConstraint(columnNames = {"entry_id", "target_language"})
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "id")
public class Translation {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    // Source entry being translated.
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "entry_id", nullable = false)
    private Entry entry;

    @Column(name = "target_language", nullable = false)
    private String targetLanguage;

    @Column(name = "translated_term", nullable = false)
    private String translatedTerm;

    @Column(name = "created_at", nullable = false, updatable = false, insertable = false)
    private java.time.LocalDateTime createdAt;
}