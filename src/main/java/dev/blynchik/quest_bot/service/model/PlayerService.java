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

import java.util.*;

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
        if (player.getId() != null)
            throw new IllegalArgumentException("При создании игрока используется уже созданный игрок id: %s".formatted(player.getId()));
        return playerRepo.save(player);
    }

    @Transactional
    public PlayerStore updateEvent(PlayerStore player, EventStore event) {
        if (player.getId() == null) throw new IllegalArgumentException("Игрок не существует");
        log.info("Update player's id: {} event id: {}", player.getId(), event.getId());
        player.setEvent(event);
        return playerRepo.save(player);
    }

    @Transactional
    public PlayerStore updateCustom(PlayerStore player, PlayerCustom custom) {
        if (player.getId() == null) throw new IllegalArgumentException("Игрок не существует");
        log.info("Update player's id: {} custom: {}", player.getId(), custom);
        player.setCustom(custom);
        return playerRepo.save(player);
    }

    @Transactional
    public PlayerStore updateOffer(PlayerStore player, Map<Long, PlayerCustom> offer) {
        if (player.getId() == null) throw new IllegalArgumentException("Игрок не существует");
        log.info("Update player's id: {} offer: {}", player.getId(), offer);
        player.setOffer(offer);
        return playerRepo.save(player);
    }

    @Transactional
    public PlayerStore clearOffer(PlayerStore player) {
        if (player.getId() == null) throw new IllegalArgumentException("Игрок не существует");
        log.info("Clear player's id: {} offer", player.getId());
        player.setOffer(null);
        return playerRepo.save(player);
    }

    @Transactional
    public PlayerStore updateExpectedCallback(PlayerStore player, List<String> expectedCallback) {
        if (player.getId() == null) throw new IllegalArgumentException("Игрок не существует");
        log.info("Update player's id: {} expected callback: {}", player.getId(), expectedCallback);
        player.setExpectedCallback(expectedCallback);
        return playerRepo.save(player);
    }

    @Transactional
    public PlayerStore finishQuest(PlayerStore player) {
        if (player.getId() == null) throw new IllegalArgumentException("Игрок не существует");
        log.info("Finish player's id: {} quest", player.getId());
        player.setEvent(null);
        player.setExpectedCallback(new ArrayList<>());
        player.setOffer(new HashMap<>());
        player.setCustom(new PlayerCustom());
        return player;
    }
}
