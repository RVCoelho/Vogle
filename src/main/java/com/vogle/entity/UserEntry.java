package com.vogle.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.*;

/**
 * Represents a vocabulary item saved by a user.
 * It links a user to an Entry within one of their learning languages
 * and stores user-specific information such as notes and save date.
 */

@Entity
@Table(
        name = "user_entries",
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"user_id", "entry_id", "learning_language_id"}
        )
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "id")
public class UserEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    // User who saved the vocabulary item.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Saved vocabulary entry.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "entry_id", nullable = false)
    private Entry entry;

    // Learning language to which this vocabulary belongs.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "learning_language_id", nullable = false)
    private LearningLanguage learningLanguage;

    private String notes;

    @Column(name = "date_added", nullable = false, updatable = false)
    private LocalDateTime dateAdded;

    @PrePersist
    void onCreate() {
        if (dateAdded == null) {
            dateAdded = LocalDateTime.now();
        }
    }

}