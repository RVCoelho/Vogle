package com.vogle.dto;

public record TranslationResponse(
        String originalText,
        String translatedText,
        String sourceLanguage,
        String targetLanguage,
        boolean fromCache
) {}