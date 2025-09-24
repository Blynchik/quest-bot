package dev.blynchik.quest_bot.repo;

import dev.blynchik.quest_bot.model.chat.ChatStore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChatStoreRepo extends JpaRepository<ChatStore, Long> {
    Optional<ChatStore> getByTgChatId(Long id);
}
