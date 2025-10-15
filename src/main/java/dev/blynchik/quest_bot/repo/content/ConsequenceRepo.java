package dev.blynchik.quest_bot.repo.content;

import dev.blynchik.quest_bot.model.content.consequence.ConsequenceStore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsequenceRepo extends JpaRepository<ConsequenceStore, Long> {
}
