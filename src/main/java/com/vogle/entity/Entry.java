package com.vogle.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import lombok.*;

/**
 * Represents a vocabulary term in a specific language.
 * An Entry is shared between users and can have translations
 * into multiple target languages.
 */

@Entity
@Table(
        name = "entries",
        uniqueConstraints = @UniqueConstraint(columnNames = {"term", "language"})
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "id")
public class Entry {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String term;

    @Column(nullable = false)
    private String language;

    private String grammaticalClass;

    // Cached translations of this term.
    @OneToMany(
            // specifies the field in the Translation entity that owns the relationship
            // This indicates that the "entry" field in Translation is the owning side of the bidirectional relationship
            mappedBy = "entry",
//            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private List<Translation> translations = new ArrayList<>();

    // Users who have saved this entry.
    @OneToMany(mappedBy = "entry")
    private List<UserEntry> userEntries = new ArrayList<>();

}