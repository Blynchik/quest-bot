package dev.blynchik.quest_bot.model.chat;

import dev.blynchik.quest_bot.model.player.PlayerStore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "chat",
        indexes = {@Index(name = "idx_chat_tg_id", columnList = "tg_id")})
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatStore {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tg_id", unique = true)
    @NotNull
    private Long tgChatId;

    @Enumerated(EnumType.STRING)
    @Column(name = "state")
    @NotNull
    private ChatStateStore chatState;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "player", referencedColumnName = "id")
    private PlayerStore player;

    public ChatStore(Long tgChatId) {
        this.tgChatId = tgChatId;
    }

    public ChatStore(Long tgChatId, ChatStateStore chatState) {
        this.tgChatId = tgChatId;
        this.chatState = chatState;
    }
}
