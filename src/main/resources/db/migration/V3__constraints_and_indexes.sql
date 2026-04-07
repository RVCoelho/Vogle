-- UNIQUE CONSTRAINTS
ALTER TABLE entries
    ADD CONSTRAINT unique_term_language UNIQUE (term, language);

ALTER TABLE translations
    ADD CONSTRAINT unique_entry_target UNIQUE (entry_id, target_language);

-- INDEXES
CREATE INDEX idx_entries_term_lang ON entries(term, language);

CREATE INDEX idx_translations_entry_target
    ON translations(entry_id, target_language);

CREATE INDEX idx_user_entries_user
    ON user_entries(user_id);