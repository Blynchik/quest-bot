package dev.blynchik.quest_bot.service.model;

import dev.blynchik.quest_bot.model.player.PlayerStore;
import dev.blynchik.quest_bot.repo.PlayerStoreRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@Slf4j
public class PlayerService {
    private final PlayerStoreRepo playerRepo;

    @Autowired
    public PlayerService(PlayerStoreRepo playerRepo) {
        this.playerRepo = playerRepo;
    }

    @Transactional
    public PlayerStore create(PlayerStore player) {
        log.info("Create new player: {}", player);
        return save(player);
    }

    @Transactional
    public PlayerStore save(PlayerStore player) {
        log.info("Save player: {}", player);
        return playerRepo.save(player);
    }
}
