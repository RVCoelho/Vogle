package com.vogle.entity;

import jakarta.persistence.*;
import java.util.UUID;

import lombok.*;

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
    @GeneratedValue
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "entry_id", nullable = false)
    private Entry entry;

    @Column(name = "target_language", nullable = false)
    private String targetLanguage;

    @Column(nullable = false)
    private String translation;
}