package com.vogle.repository;

import com.vogle.entity.Entry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface EntryRepository extends JpaRepository<Entry, UUID> {

    Optional<Entry> findByTermAndLanguage(String term, String language);
}
