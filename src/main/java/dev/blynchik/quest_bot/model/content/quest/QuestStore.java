package dev.blynchik.quest_bot.model.content.quest;

import dev.blynchik.quest_bot.model.content.event.EventStore;
import dev.blynchik.quest_bot.model.content.quest.rule.Rule;
import io.hypersistence.utils.hibernate.type.json.JsonBinaryType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "quest")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuestStore {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    @NotBlank
    private String title;

    @Column(name = "descr", nullable = false)
    @NotBlank
    @Size(min = 1, max = 5000)
    private String descr;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "first_event", referencedColumnName = "id")
    private EventStore firstEvent;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<EventStore> events = new ArrayList<>();

    @Type(JsonBinaryType.class)
    @Column(name = "rule", columnDefinition = "jsonb")
    private Map<String, Rule> rule = new HashMap<>();

    public QuestStore(String title, String descr) {
        this.title = title;
        this.descr = descr;
    }
}
