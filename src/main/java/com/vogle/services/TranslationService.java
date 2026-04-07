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
import jakarta.transaction.Transactional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

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
    public TranslationResponse translate(TranslationRequest request) throws DeepLException, InterruptedException {
        validateRequest(request);

        String text = normalize(request.getText());
        String sourceLang = request.getSourceLanguage().toLowerCase();
        String targetLang = request.getTargetLanguage().toLowerCase();

        // 1. search for entry
        Optional<Entry> entryOpt = entryRepository.findByTermAndLanguage(text, sourceLang);

        if (entryOpt.isPresent()) {
            Entry entry = entryOpt.get();

            // 2. Buscar translation (CACHE HIT)
            Optional<Translation> translationOpt =
                    translationRepository.findByEntryIdAndTargetLanguage(entry.getId(), targetLang);

            if (translationOpt.isPresent()) {
                return new TranslationResponse(
                        text,
                        translationOpt.get().getTranslation(),
                        sourceLang,
                        targetLang,
                        true
                );
            }
        }

        // 3. CACHE MISS → call API
        TextResult result = deepLClient.translateText(text, sourceLang, targetLang);
        String translatedText = result.getText();

        try {
            Entry entry = entryOpt.orElseGet(() -> {
                Entry newEntry = new Entry();
                newEntry.setTerm(text);
                newEntry.setLanguage(sourceLang);
                return entryRepository.save(newEntry);
            });

            Translation translation = new Translation();
            translation.setEntry(entry);
            translation.setTargetLanguage(targetLang);
            translation.setTranslation(translatedText);

            translationRepository.save(translation);

        } catch (DataIntegrityViolationException e) {
            //   someone tried to save at the same time → search again
            Entry entry = entryRepository
                    .findByTermAndLanguage(text, sourceLang)
                    .orElseThrow();

            Translation translation = translationRepository
                    .findByEntryIdAndTargetLanguage(entry.getId(), targetLang)
                    .orElseThrow();

            return new TranslationResponse(
                    text,
                    translation.getTranslation(),
                    sourceLang,
                    targetLang,
                    true
            );
        }

        return new TranslationResponse(
                text,
                translatedText,
                sourceLang,
                targetLang,
                false
        );
    }

    private String normalize(String text) {
        return text.trim().toLowerCase();
    }

    private void validateRequest(TranslationRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Request body is required");
        }
        if (request.getText() == null || request.getText().isBlank()) {
            throw new IllegalArgumentException("Field 'text' is required");
        }
        if (request.getSourceLanguage() == null || request.getSourceLanguage().isBlank()) {
            throw new IllegalArgumentException("Field 'sourceLanguage' is required");
        }
        if (request.getTargetLanguage() == null || request.getTargetLanguage().isBlank()) {
            throw new IllegalArgumentException("Field 'targetLanguage' is required");
        }
    }
}
