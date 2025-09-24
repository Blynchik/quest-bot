package dev.blynchik.quest_bot.repo;

import dev.blynchik.quest_bot.model.player.PlayerStore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerStoreRepo extends JpaRepository<PlayerStore, Long> {
}
