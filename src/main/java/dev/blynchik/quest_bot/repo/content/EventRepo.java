package dev.blynchik.quest_bot.repo.content;

import dev.blynchik.quest_bot.model.content.event.EventStore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepo extends JpaRepository<EventStore, Long> {
}
