package dev.blynchik.quest_bot.service.model;

import dev.blynchik.quest_bot.exception.exception.NotFoundException;
import dev.blynchik.quest_bot.model.chat.ChatStateStore;
import dev.blynchik.quest_bot.model.chat.ChatStore;
import dev.blynchik.quest_bot.model.player.PlayerStore;
import dev.blynchik.quest_bot.repo.ChatStoreRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@Slf4j
public class ChatService {
    private final ChatStoreRepo chatRepo;
    private final PlayerService playerService;

    @Autowired
    public ChatService(ChatStoreRepo chatRepo,
                       PlayerService playerService) {
        this.chatRepo = chatRepo;
        this.playerService = playerService;
    }

    public ChatStore getByTgId(Long tgId) {
        log.info("Get chat by tg id: {}", tgId);
        return getByTgIdOpt(tgId)
                .orElseThrow(() -> new NotFoundException("Chat tg id: %s  not found".formatted(tgId)));
    }

    public Optional<ChatStore> getByTgIdOpt(Long tgId) {
        log.info("Get optional chat by tg id: {}", tgId);
        return chatRepo.getByTgChatId(tgId);
    }

    @Transactional
    public ChatStore save(ChatStore chat) {
        log.info("Save chat: {}", chat);
        return chatRepo.save(chat);
    }

    @Transactional
    public ChatStore createIfNotExist(ChatStore chat) {
        log.info("Create chat tg id: {} if not exist", chat.getTgChatId());
        return getByTgIdOpt(chat.getTgChatId())
                .orElse(save(chat));
    }

    @Transactional
    public void delete(Long id) {
        log.info("Delete chat id: {}", id);
        chatRepo.deleteById(id);
    }

    @Transactional
    public ChatStore updateTgId(Long oldTgId, Long newTgId) {
        log.info("Update chat tg id: {} on new tg id: {}", oldTgId, newTgId);
        ChatStore chat = getByTgId(oldTgId);
        chat.setTgChatId(newTgId);
        return save(chat);
    }

    @Transactional
    public ChatStore updateChatState(Long chatTgId, ChatStateStore chatState) {
        log.info("Update chat's tg id: {} state on: {}", chatTgId, chatState);
        ChatStore chat = getByTgId(chatTgId);
        chat.setChatState(chatState);
        return save(chat);
    }

    @Transactional
    public ChatStore updateChatState(ChatStore chat, ChatStateStore chatState) {
        log.info("Update chat's tg id: {} state on: {}", chat.getTgChatId(), chatState);
        chat.setChatState(chatState);
        return save(chat);
    }

    @Transactional
    public ChatStore updatePlayer(ChatStore chat, PlayerStore player) {
        log.info("Update chat's tg id: {} player: {}", chat.getTgChatId(), player);
        chat.setPlayer(player);
        return save(chat);
    }
}
