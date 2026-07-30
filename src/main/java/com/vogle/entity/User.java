package com.vogle.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

/**
 * Represents an application user.
 * A user has one native language and can learn multiple languages.
 */

@Setter
@Getter
@Entity
@Table(
        name = "users"//,
//        uniqueConstraints = @UniqueConstraint(columnNames = "email")
)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "native_language",nullable = false)
    private String nativeLanguage;

    // Languages currently being learned by the user.
    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<LearningLanguage> learningLanguages;

    // Vocabulary entries saved by the user.
    @OneToMany(mappedBy = "user")
    private List<UserEntry> userEntries;
}