package com.vogle.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import lombok.*;

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
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String term;

    @Column(nullable = false)
    private String language;

    private String grammaticalClass;

    @Builder.Default
    @OneToMany(
            mappedBy = "entry",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private List<Translation> translations = new ArrayList<>();
}