package dev.blynchik.quest_bot.repo.content;

import dev.blynchik.quest_bot.model.content.expression.condition.ConditionStore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConditionRepo extends JpaRepository<ConditionStore, Long> {
}
