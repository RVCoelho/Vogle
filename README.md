# Vogle API

Backend API for a personal multilingual dictionary application focused
on structured vocabulary learning and efficient translation reuse.

------------------------------------------------------------------------

##  Getting Started

### Prerequisites

-   Java 17+
-   Maven
-   PostgreSQL (or Supabase)

------------------------------------------------------------------------

###  Running the Project

1.  Clone the repository:

``` bash
git clone https://github.com/RVCoelho/vogle-api.git
cd vogle-api
```

2.  Configure environment variables (recommended):

Create a config file (e.g., `application-local.yml`) or use environment
variables:

``` yaml
spring:
  datasource:
    url: YOUR_DB_URL
    username: YOUR_DB_USER
    password: YOUR_DB_PASSWORD
```

3.  Run the application:

``` bash
mvn spring-boot:run
```

------------------------------------------------------------------------

###  Database

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
-   PostgreSQL (Supabase)
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
