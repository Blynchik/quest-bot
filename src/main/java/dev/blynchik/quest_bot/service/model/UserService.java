package dev.blynchik.quest_bot.service.model;

import dev.blynchik.quest_bot.exception.exception.NotFoundException;
import dev.blynchik.quest_bot.exception.exception.UniqueConstraintException;
import dev.blynchik.quest_bot.model.chat.ChatStore;
import dev.blynchik.quest_bot.model.user.UserStore;
import dev.blynchik.quest_bot.repo.UserStoreRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@Slf4j
public class UserService {

    private final UserStoreRepo userRepo;
    private final ChatService chatService;

    @Autowired
    public UserService(UserStoreRepo userRepo,
                       ChatService chatService) {
        this.userRepo = userRepo;
        this.chatService = chatService;
    }

    public UserStore getByTgId(Long tgId) {
        log.info("Get user by tg id: {}", tgId);
        return userRepo.getByTgUserId(tgId)
                .orElseThrow(() -> new NotFoundException("Пользователь id: %s не найден.".formatted(tgId)));
    }

    public Optional<UserStore> getByTgIdOpt(Long tgId) {
        log.info("Get optional user by tg id: {}", tgId);
        return userRepo.getByTgUserId(tgId);
    }

    @Transactional
    public UserStore createIfNotExist(UserStore user, ChatStore chat) {
        log.info("Create user id: {} if not exist", user.getTgUserId());
        Optional<UserStore> mbUser = getByTgIdOpt(user.getTgUserId());
        if (mbUser.isPresent()) {
            chatService.updateTgId(mbUser.get().getChat().getTgChatId(), chat.getTgChatId());
            throw new UniqueConstraintException("Пользователь id: %s уже существует. Чат успешно обновлен.".formatted(user.getTgUserId()));
        }
        user.setChat(chat);
        return userRepo.save(user);
    }
}
