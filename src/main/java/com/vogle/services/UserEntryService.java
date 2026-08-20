package com.vogle.services;

import com.vogle.dto.UserEntryRequest;
import com.vogle.dto.UserEntryResponse;
import com.vogle.entity.Entry;
import com.vogle.entity.LearningLanguage;
import com.vogle.entity.User;
import com.vogle.entity.UserEntry;
import com.vogle.exception.DuplicateResourceException;
import com.vogle.repository.UserEntryRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class UserEntryService {

    private final UserEntryRepository userEntryRepository;

    public UserEntryService(UserEntryRepository userEntryRepository) {
        this.userEntryRepository = userEntryRepository;
    }

    @Transactional
    public UserEntryResponse save(UserEntryRequest request) {

        boolean exists = userEntryRepository
                .existsByUserIdAndEntryIdAndLearningLanguageId(
                        request.getUserId(),
                        request.getEntryId(),
                        request.getLearningLanguageId()
                );

        if (exists) {
            throw new DuplicateResourceException("Word already saved for this language");
        }

        User user = new User();
        user.setId(request.getUserId());

        Entry entry = new Entry();
        entry.setId(request.getEntryId());

        LearningLanguage learningLanguage = new LearningLanguage();
        learningLanguage.setId(request.getLearningLanguageId());

        UserEntry entity = UserEntry.builder()
                .user(user)
                .entry(entry)
                .learningLanguage(learningLanguage)
                .notes(request.getNotes())
                .build();

        UserEntry saved = userEntryRepository.save(entity);

        return new UserEntryResponse(
                saved.getId(),
                saved.getEntry().getId(),
                saved.getLearningLanguage().getId(),
                saved.getNotes(),
                saved.getDateAdded()
        );
    }
}