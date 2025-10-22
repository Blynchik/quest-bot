package dev.blynchik.quest_bot.service.model;

import dev.blynchik.quest_bot.exception.exception.NotFoundException;
import dev.blynchik.quest_bot.exception.exception.UniqueConstraintException;
import dev.blynchik.quest_bot.model.content.action.ActionStore;
import dev.blynchik.quest_bot.model.content.event.EventStore;
import dev.blynchik.quest_bot.model.user.PlayerCustom;
import dev.blynchik.quest_bot.model.user.UserStore;
import dev.blynchik.quest_bot.repo.UserStoreRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static dev.blynchik.quest_bot.config.bot.util.CallbackType.ACTION;
import static dev.blynchik.quest_bot.config.bot.util.CallbackUtil.SEPARATOR;
import static dev.blynchik.quest_bot.model.user.UserState.ON_QUEST;
import static dev.blynchik.quest_bot.model.user.UserState.WAITING_QUEST;

@Service
@Transactional(readOnly = true)
@Slf4j
public class UserService {

    private final UserStoreRepo userRepo;

    @Autowired
    public UserService(UserStoreRepo userRepo) {
        this.userRepo = userRepo;
    }

    public UserStore getByTgId(Long tgId) {
        log.info("Get user by tg id: {}", tgId);
        return userRepo.getByTgUserId(tgId)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден."));
    }

    public Optional<UserStore> getByTgIdOpt(Long tgId) {
        log.info("Get optional user by tg id: {}", tgId);
        return userRepo.getByTgUserId(tgId);
    }

    @Transactional
    public UserStore createIfNotExist(UserStore user) {
        log.info("Create user id: {} if not exist", user.getTgUserId());
        if (user.getId() != null)
            throw new IllegalArgumentException("При создании пользователя используется уже созданный пользователь.");
        Optional<UserStore> mbUser = userRepo.getByTgUserId(user.getTgUserId());
        if (mbUser.isPresent()) {
            mbUser.get().setChatId(user.getChatId());
            throw new UniqueConstraintException("Пользователь уже существует. Чат успешно обновлен.");
        }
        return userRepo.save(user);
    }

    public UserStore getByChatId(Long id) {
        log.info("Get user by chat id: {}", id);
        return userRepo.getByChatId(id)
                .orElseThrow(() -> new NotFoundException("Чат id не найден."));
    }

    public Optional<UserStore> getByChatIdOpt(Long tgId) {
        log.info("Get optional chat by tg id: {}", tgId);
        return userRepo.getByChatId(tgId);
    }

    @Transactional
    public UserStore prepareToOfferQuest(UserStore user, Map<Long, PlayerCustom> offer) {
        if (user.getId() == null) throw new IllegalArgumentException("Пользователь не существует.");
        log.info("Prepare to offer quest to user id: {} offer: {}", user.getId(), offer);
        user.setOffer(offer);
        return userRepo.save(user);
    }

    @Transactional
    public UserStore prepareToStartQuest(UserStore user, Long questId, EventStore event, List<ActionStore> actions) {
        if (user.getId() == null) throw new IllegalArgumentException("Пользователь не существует.");
        log.info("Prepare to start quest id: {} user id: {} quest", questId, user.getId());
        if (event.getId() == null) throw new IllegalArgumentException("Событие не существует");
        user.setEvent(event);
        if (!user.getOffer().containsKey(questId)) throw new IllegalArgumentException("Квест не был предложен.");
        user.setCustom(user.getOffer().get(questId));
        user.setOffer(null);
        user.setState(ON_QUEST);
        user.setExpectedCallback(
                getDescrFromActions(actions));
        return userRepo.save(user);
    }

    @Transactional
    public UserStore prepareToFinishQuest(UserStore user) {
        if (user.getId() == null) throw new IllegalArgumentException("Пользователь не существует.");
        log.info("Prepare to finish quest user id: {} quest", user.getId());
        user.setEvent(null);
        user.setExpectedCallback(new ArrayList<>());
        user.setOffer(new HashMap<>());
        user.setCustom(new PlayerCustom());
        user.setState(WAITING_QUEST);
        return userRepo.save(user);
    }

    @Transactional
    public UserStore prepareToNextEvent(UserStore user, EventStore event, List<ActionStore> actions) {
        if (user.getId() == null) throw new IllegalArgumentException("Пользователь не существует.");
        log.info("Prepare to next event id: {} user id: {}", event.getId(), user.getId());
        if (event.getId() == null) throw new IllegalArgumentException("Событие не существует");
        user.setEvent(event);
        user.setExpectedCallback(
                getDescrFromActions(actions));
        return userRepo.save(user);
    }

    @Transactional
    public UserStore createPlayer(UserStore user, String playerName) {
        if (user.getId() == null) throw new IllegalArgumentException("Пользователь не существует.");
        user.setName(playerName);
        user.setState(WAITING_QUEST);
        return userRepo.save(user);
    }

    private List<String> getDescrFromActions(List<ActionStore> actions) {
        return actions.stream()
                .map(a -> {
                    if (a.getId() == null) throw new IllegalArgumentException("Действие не существует.");
                    return ACTION.name() + SEPARATOR.replace() + a.getId();
                })
                .toList();
    }
}
