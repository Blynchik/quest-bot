package dev.blynchik.quest_bot.model.user;

import dev.blynchik.quest_bot.model.chat.ChatStore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "bot_user",
        indexes = {
                @Index(name = "idx_user_tg_id", columnList = "tg_id")
        })
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserStore {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tg_id", unique = true)
    @NotNull
    private Long tgUserId;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "chat", referencedColumnName = "tg_id")
    private ChatStore chat;

    public UserStore(Long tgUserId) {
        this.tgUserId = tgUserId;
    }

    public UserStore(Long tgUserId, ChatStore chat) {
        this.tgUserId = tgUserId;
        this.chat = chat;
    }
}
