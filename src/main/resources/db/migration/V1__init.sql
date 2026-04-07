CREATE EXTENSION IF NOT EXISTS pgcrypto;

CREATE TABLE users (
                       id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                       name TEXT NOT NULL,
                       email TEXT UNIQUE NOT NULL,
                       native_lang TEXT NOT NULL
);

CREATE TABLE learning_languages (
                                    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                                    user_id UUID REFERENCES users(id) ON DELETE CASCADE,
                                    language TEXT NOT NULL
);

CREATE TABLE entries (
                         id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                         term TEXT NOT NULL,
                         source_language TEXT NOT NULL,
                         target_language TEXT NOT NULL,
                         translation TEXT NOT NULL,
                         grammatical_class TEXT
);

CREATE INDEX idx_entry_lookup
    ON entries (term, source_language, target_language);

CREATE TABLE user_entries (
                              id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                              user_id UUID REFERENCES users(id) ON DELETE CASCADE,
                              entry_id UUID REFERENCES entries(id) ON DELETE CASCADE,
                              learning_language_id UUID REFERENCES learning_languages(id) ON DELETE CASCADE,
                              date_added TIMESTAMP DEFAULT NOW(),
                              notes TEXT
);
