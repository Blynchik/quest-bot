package dev.blynchik.quest_bot.model.content.event;

import dev.blynchik.quest_bot.model.content.action.ActionStore;
import dev.blynchik.quest_bot.model.content.quest.QuestStore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "event")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventStore {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "descr", nullable = false)
    @NotNull
    private String descr;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ActionStore> actions = new ArrayList<>();

    @ManyToMany(mappedBy = "events", fetch = FetchType.LAZY)
    private List<QuestStore> quests = new ArrayList<>();

    public EventStore(String descr) {
        this.descr = descr;
    }
}
