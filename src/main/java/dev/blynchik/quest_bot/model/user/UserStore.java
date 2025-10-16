package dev.blynchik.quest_bot.model.user;

import dev.blynchik.quest_bot.model.content.event.EventStore;
import io.hypersistence.utils.hibernate.type.json.JsonBinaryType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Column(name = "tg_id", unique = true, nullable = false)
    @NotNull
    private Long tgUserId;

    @Column(name = "chat_id", nullable = false)
    private Long chatId;

    @Enumerated(EnumType.STRING)
    @Column(name = "state", nullable = false)
    @NotNull
    private UserState state = UserState.WAITING_NAME;

    @Column(name = "name")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    private EventStore event;

    @Type(JsonBinaryType.class)
    @Column(name = "expected_callback", columnDefinition = "jsonb")
    private List<String> expectedCallback = new ArrayList<>();

    @Type(JsonBinaryType.class)
    @Column(name = "offer", columnDefinition = "jsonb")
    private Map<Long, PlayerCustom> offer = new HashMap<>();

    @Type(JsonBinaryType.class)
    @Column(name = "custom", columnDefinition = "jsonb")
    private PlayerCustom custom;

    public UserStore(Long tgUserId, Long chatId) {
        this.tgUserId = tgUserId;
        this.chatId = chatId;
    }
}
