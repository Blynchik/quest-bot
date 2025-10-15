package dev.blynchik.quest_bot.service.model.content.consequence;

import dev.blynchik.quest_bot.exception.exception.NotFoundException;
import dev.blynchik.quest_bot.model.content.consequence.ConsequenceStore;
import dev.blynchik.quest_bot.repo.content.ConsequenceRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@Slf4j
public class ConsequenceService {
    private final ConsequenceRepo consequenceRepo;

    @Autowired
    public ConsequenceService(ConsequenceRepo consequenceRepo) {
        this.consequenceRepo = consequenceRepo;
    }

    public ConsequenceStore getById(Long id) {
        log.info("Get consequence by id: {}", id);
        return consequenceRepo.findById(id)
                .orElseThrow(
                        () -> new NotFoundException("Последствие не найдено"));
    }
}
