# Vogle API

Backend API for a personal multilingual dictionary application focused
on structured vocabulary learning and efficient translation reuse.

------------------------------------------------------------------------

##  Getting Started

### Prerequisites

-   Docker
-   Docker Compose
-   A DeepL API key

------------------------------------------------------------------------

###  Running the Project

1.  Clone the repository:

``` bash
git clone https://github.com/RVCoelho/vogle-api.git
cd vogle-api
```

2.  Configure the DeepL API key:

Create a `.env` file in the project root:

``` env
DEEPL_API_KEY=your_deepl_api_key
```

3.  Build and start the application and database:

```bash
docker compose up --build
```

The API is available at `http://localhost:8080`.

4.  Stop the services:

```bash
docker compose down
```

------------------------------------------------------------------------

###  Database

-   PostgreSQL runs in the `vogle-postgres` container
-   Database credentials are configured by `compose.yaml`
-   Data is persisted in the `postgres-data` Docker volume
-   Managed via Flyway
-   Migrations run automatically on startup

------------------------------------------------------------------------

##  API Usage

###  Translate Text

Endpoint:

POST /translations

Request:

{ "text": "Haus", "sourceLanguage": "de", "targetLanguage": "en-US" }

Response:

{ "originalText": "haus", "translatedText": "house", "sourceLanguage":
"de", "targetLanguage": "en-US", "fromCache": true }

------------------------------------------------------------------------

###  Behavior

-   First request → calls external API (cache miss)
-   Subsequent requests → served from database (cache hit)
-   Concurrency-safe (prevents duplicate entries)

------------------------------------------------------------------------

##  Core Features

-   Translation endpoint with cache-first strategy
-   External API integration (DeepL - currently mockable)
-   Normalized relational database design
-   Concurrency-safe insert handling
-   Flyway database versioning
-   Clean layered architecture (Controller / Service / Repository)

------------------------------------------------------------------------

##  Tech Stack

-   Java + Spring Boot
-   PostgreSQL
-   Docker Compose
-   JPA / Hibernate
-   Flyway

------------------------------------------------------------------------

##  Future Improvements

###  Core Product Evolution

-   User vocabulary management (user_entries)
-   Authentication (JWT-based)
-   Pagination and filtering
-   Search functionality

------------------------------------------------------------------------

###  AI & Language Intelligence

-   AI-generated meanings
-   Example sentences
-   Context-aware translations
-   Phrase parsing (sentence → word breakdown)
-   Support for compound and separable verbs (e.g., German)

------------------------------------------------------------------------

###  Learning Features

-   Integration with spaced repetition systems (e.g., Anki)
-   Personalized vocabulary tracking
-   Advanced linguistic metadata

------------------------------------------------------------------------

###  Media & Input

-   Text-to-Speech (TTS) for pronunciation
-   Image-based word recognition

------------------------------------------------------------------------

###  Platform Expansion

-   Mobile app (React Native with Expo)
-   Web version (React Native Web)
-   Sync across devices
