ALTER TABLE users
    ALTER COLUMN name DROP NOT NULL;

ALTER TABLE entries
    ADD COLUMN language TEXT;

UPDATE entries
SET language = source_language
WHERE language IS NULL;

ALTER TABLE entries
    ALTER COLUMN language SET NOT NULL;

CREATE TABLE translations (
                              id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                              entry_id UUID REFERENCES entries(id) ON DELETE CASCADE,
                              target_language TEXT NOT NULL,
                              translation TEXT NOT NULL
);

INSERT INTO translations (entry_id, target_language, translation)
SELECT id, target_language, translation
FROM entries;

DROP INDEX IF EXISTS idx_entry_lookup;

CREATE INDEX idx_entries_lookup
    ON entries (term, language);

CREATE INDEX idx_translations_lookup
    ON translations (entry_id, target_language);

ALTER TABLE entries
    DROP COLUMN source_language,
    DROP COLUMN target_language,
    DROP COLUMN translation;
