package dev.blynchik.quest_bot.repo.content;

import dev.blynchik.quest_bot.model.content.action.ActionStore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActionRepo extends JpaRepository<ActionStore, Long> {
}
