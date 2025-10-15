package dev.blynchik.quest_bot.repo.content;

import dev.blynchik.quest_bot.model.content.result.ResultStore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResultRepo extends JpaRepository<ResultStore, Long> {
}
