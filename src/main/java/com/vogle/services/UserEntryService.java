package com.vogle.services;

import com.vogle.dto.UserEntryRequest;
import com.vogle.dto.UserEntryResponse;
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

        UserEntry entity = UserEntry.builder()
                .userId(request.getUserId())
                .entryId(request.getEntryId())
                .learningLanguageId(request.getLearningLanguageId())
                .notes(request.getNotes())
                .build();

        UserEntry saved = userEntryRepository.save(entity);

        return new UserEntryResponse(
                saved.getId(),
                saved.getEntryId(),
                saved.getLearningLanguageId(),
                saved.getNotes(),
                saved.getDateAdded()
        );
    }
}