package com.vogle.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TranslationRequest {

    private String text;

    @JsonAlias("sourceLang")
    private String sourceLanguage;

    @JsonAlias("targetLang")
    private String targetLanguage;
}