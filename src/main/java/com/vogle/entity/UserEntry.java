package com.vogle.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.*;

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
    @GeneratedValue
    private UUID id;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(name = "entry_id", nullable = false)
    private UUID entryId;

    @Column(name = "learning_language_id", nullable = false)
    private UUID learningLanguageId;

    private String notes;

    @Column(name = "date_added", nullable = false, updatable = false)
    private LocalDateTime dateAdded;

    @PrePersist
    public void prePersist() {
        this.dateAdded = LocalDateTime.now();
    }
}