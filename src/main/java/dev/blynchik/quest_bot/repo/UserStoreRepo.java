package dev.blynchik.quest_bot.repo;

import dev.blynchik.quest_bot.model.user.UserStore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserStoreRepo extends JpaRepository<UserStore, Long> {
    Optional<UserStore> getByTgUserId(Long id);

    Optional<UserStore> getByChatId(Long id);
}
