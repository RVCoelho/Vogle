package com.vogle.repository;

import com.vogle.entity.UserEntry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface UserEntryRepository extends JpaRepository<UserEntry, UUID> {

    boolean existsByUserIdAndEntryIdAndLearningLanguageId(
            UUID userId,
            UUID entryId,
            UUID learningLanguageId
    );

    List<UserEntry> findByUserId(UUID userId);
}