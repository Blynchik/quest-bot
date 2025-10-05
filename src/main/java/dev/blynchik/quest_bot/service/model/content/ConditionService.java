package dev.blynchik.quest_bot.service.model.content;

import dev.blynchik.quest_bot.exception.exception.NotFoundException;
import dev.blynchik.quest_bot.model.content.expression.condition.ConditionStore;
import dev.blynchik.quest_bot.repo.content.ConditionRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@Slf4j
public class ConditionService {
    private final ConditionRepo conditionRepo;

    @Autowired
    public ConditionService(ConditionRepo conditionRepo) {
        this.conditionRepo = conditionRepo;
    }

    @Transactional
    public ConditionStore create(ConditionStore condition) {
        log.info("Create condition: {}", condition);
        if (condition.getId() != null)
            throw new IllegalArgumentException("При создании условия используется уже созданное id: %s".formatted(condition.getId()));
        return conditionRepo.save(condition);
    }

    public Optional<ConditionStore> getByIdOpt(Long id) {
        log.info("Get optional condition by id: {}", id);
        return conditionRepo.findById(id);
    }

    public ConditionStore getById(Long id) {
        log.info("Get optional condition by id: {}", id);
        return conditionRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Условие не найдено."));
    }
}
