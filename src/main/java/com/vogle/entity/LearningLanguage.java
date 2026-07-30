package com.vogle.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

/**
 * Represents a language a user is learning.
 * Each learning language has its own independent vocabulary list.
 */

@Setter
@Getter
@Entity
@Table(
        name = "learning_languages",
        uniqueConstraints = @UniqueConstraint(
                name = "unique_user_learning_language",
                columnNames = {
                        "user_id",
                        "language"
                }
        )
)
public class LearningLanguage {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    // Owner of this learning language.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String language;

    // Vocabulary saved for this learning language.
    @OneToMany(mappedBy = "learningLanguage")
    private List<UserEntry> userEntries;
}
