package dev.blynchik.quest_bot.service.model;

import dev.blynchik.quest_bot.exception.exception.NotFoundException;
import dev.blynchik.quest_bot.model.content.event.EventStore;
import dev.blynchik.quest_bot.model.player.PlayerCustom;
import dev.blynchik.quest_bot.model.player.PlayerStore;
import dev.blynchik.quest_bot.repo.PlayerStoreRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@Slf4j
public class PlayerService {
    private final PlayerStoreRepo playerRepo;

    @Autowired
    public PlayerService(PlayerStoreRepo playerRepo) {
        this.playerRepo = playerRepo;
    }

    public Optional<PlayerStore> getByIdOpt(Long id) {
        log.info("Get optional player by id: {}", id);
        return playerRepo.findById(id);
    }

    public PlayerStore getById(Long id) {
        log.info("Get player by id: {}", id);
        return playerRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Игрок не найден"));
    }

    @Transactional
    public PlayerStore create(PlayerStore player) {
        log.info("Create new player: {}", player);
        return playerRepo.save(player);
    }

    @Transactional
    public PlayerStore updateEvent(Long id, EventStore event) {
        log.info("Update player's id: {} event id: {}", id, event.getId());
        PlayerStore player = playerRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Игрок не найден"));
        player.setEvent(event);
        return playerRepo.save(player);
    }

    @Transactional
    public PlayerStore updateCustom(Long id, PlayerCustom custom) {
        log.info("Update player's id: {} custom: {}", id, custom);
        PlayerStore player = playerRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Игрок не найден"));
        player.setCustom(custom);
        return playerRepo.save(player);
    }

    @Transactional
    public PlayerStore updateOffer(Long id, Map<Long, PlayerCustom> offer) {
        log.info("Update player's id: {} offer: {}", id, offer);
        PlayerStore player = playerRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Игрок не найден"));
        player.setOffer(offer);
        return playerRepo.save(player);
    }

    @Transactional
    public PlayerStore clearOffer(Long id) {
        log.info("Clear player's id: {} offer", id);
        PlayerStore player = playerRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Игрок не найден"));
        player.setOffer(null);
        return playerRepo.save(player);
    }
}
