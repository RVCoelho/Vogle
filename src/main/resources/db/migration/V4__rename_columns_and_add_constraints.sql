-- Rename columns

ALTER TABLE users
    RENAME COLUMN native_lang TO native_language;

ALTER TABLE translations
    RENAME COLUMN translation TO translated_term;

-- Required relationships

ALTER TABLE learning_languages
    ALTER COLUMN user_id SET NOT NULL;

ALTER TABLE user_entries
    ALTER COLUMN user_id SET NOT NULL,
    ALTER COLUMN entry_id SET NOT NULL,
    ALTER COLUMN learning_language_id SET NOT NULL;

ALTER TABLE translations
    ALTER COLUMN entry_id SET NOT NULL;

-- Unique constraints

ALTER TABLE learning_languages
    ADD CONSTRAINT unique_user_learning_language
        UNIQUE (user_id, language);

ALTER TABLE user_entries
    ADD CONSTRAINT unique_user_entry_learning_language
        UNIQUE (user_id, entry_id, learning_language_id);

-- Additional timestamp metadata

ALTER TABLE translations
    ADD COLUMN created_at TIMESTAMP NOT NULL DEFAULT NOW();