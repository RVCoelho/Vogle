package com.vogle.services;

import com.deepl.api.DeepLClient;
import com.deepl.api.DeepLException;
import com.deepl.api.TextResult;
import com.vogle.dto.TranslationRequest;
import com.vogle.dto.TranslationResponse;
import com.vogle.entity.Entry;
import com.vogle.entity.Translation;
import com.vogle.repository.EntryRepository;
import com.vogle.repository.TranslationRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class TranslationService {

    private final EntryRepository entryRepository;
    private final TranslationRepository translationRepository;
    private final DeepLClient deepLClient;

    public TranslationService(EntryRepository entryRepository,
                              TranslationRepository translationRepository,
                              DeepLClient deepLClient) {
        this.entryRepository = entryRepository;
        this.translationRepository = translationRepository;
        this.deepLClient = deepLClient;
    }

    @Transactional
    public TranslationResponse translate(TranslationRequest request) {

        String text = normalize(request.getText());
        String sourceLang = request.getSourceLanguage().toLowerCase();
        String targetLang = request.getTargetLanguage().toLowerCase();

        // 🔹 1. CACHE
        Optional<Entry> entryOpt = entryRepository.findByTermAndLanguage(text, sourceLang);

        if (entryOpt.isPresent()) {
            Entry entry = entryOpt.get();

            Optional<Translation> translationOpt =
                    translationRepository.findByEntryIdAndTargetLanguage(entry.getId(), targetLang);

            if (translationOpt.isPresent()) {
                return buildResponse(
                        text,
                        translationOpt.get().getTranslation(),
                        sourceLang,
                        targetLang,
                        true
                );
            }
        }

        // 🔹 2. CALL API (correto)
        String translatedText = callDeepL(text, sourceLang, targetLang);

        try {
            Entry entry = entryOpt.orElseGet(() ->
                    entryRepository.save(
                            Entry.builder()
                                    .term(text)
                                    .language(sourceLang)
                                    .build()
                    )
            );

            Translation translation = Translation.builder()
                    .entry(entry)
                    .targetLanguage(targetLang)
                    .translation(translatedText)
                    .build();

            translationRepository.save(translation);

        } catch (DataIntegrityViolationException e) {

            Entry entry = entryRepository
                    .findByTermAndLanguage(text, sourceLang)
                    .orElseThrow();

            Translation translation = translationRepository
                    .findByEntryIdAndTargetLanguage(entry.getId(), targetLang)
                    .orElseThrow();

            return buildResponse(
                    text,
                    translation.getTranslation(),
                    sourceLang,
                    targetLang,
                    true
            );
        }

        return buildResponse(text, translatedText, sourceLang, targetLang, false);
    }

    private String callDeepL(String text, String source, String target) {
        try {
            TextResult result = deepLClient.translateText(text, source, target);
            return result.getText();
        } catch (DeepLException | InterruptedException e) {
            throw new RuntimeException("Error calling DeepL API", e);
        }
    }

    private String normalize(String text) {
        return text.trim().toLowerCase();
    }

    private TranslationResponse buildResponse(String original,
                                              String translated,
                                              String source,
                                              String target,
                                              boolean fromCache) {
        return new TranslationResponse(original, translated, source, target, fromCache);
    }
}