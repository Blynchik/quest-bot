package dev.blynchik.quest_bot.model.player;

import dev.blynchik.quest_bot.model.content.event.EventStore;
import io.hypersistence.utils.hibernate.type.json.JsonBinaryType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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
@Table(name = "player")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlayerStore {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    @NotBlank
    @Size(min = 1, max = 20)
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

    public PlayerStore(String name) {
        this.name = name;
    }
}
