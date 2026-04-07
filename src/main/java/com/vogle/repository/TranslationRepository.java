package com.vogle.repository;

import com.vogle.entity.Translation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface TranslationRepository extends JpaRepository<Translation, UUID> {

    Optional<Translation> findByEntryIdAndTargetLanguage(UUID entryId, String targetLanguage);
}
