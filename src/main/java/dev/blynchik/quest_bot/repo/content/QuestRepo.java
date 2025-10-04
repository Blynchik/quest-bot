package dev.blynchik.quest_bot.repo.content;

import dev.blynchik.quest_bot.model.content.quest.QuestStore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface QuestRepo extends JpaRepository<QuestStore, Long> {
    @Query(value = "SELECT * FROM quest ORDER BY RANDOM() LIMIT 1", nativeQuery = true)
    Optional<QuestStore> findRandom();
}
