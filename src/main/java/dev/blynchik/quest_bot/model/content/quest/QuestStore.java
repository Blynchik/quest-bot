package dev.blynchik.quest_bot.model.content.quest;

import dev.blynchik.quest_bot.model.content.event.EventStore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "quest")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestStore {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    @NotNull
    private String title;

    @Column(name = "descr", nullable = false)
    @NotNull
    private String descr;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "first_event", referencedColumnName = "id")
    private EventStore firstEvent;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "quest_event",
            joinColumns = @JoinColumn(name = "quest_id"),
            inverseJoinColumns = @JoinColumn(name = "event_id")
    )
    private List<EventStore> events = new ArrayList<>();

    public QuestStore(String title, String descr) {
        this.title = title;
        this.descr = descr;
    }
}
