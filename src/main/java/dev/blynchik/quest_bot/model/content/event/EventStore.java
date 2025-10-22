package dev.blynchik.quest_bot.model.content.event;

import dev.blynchik.quest_bot.model.content.action.ActionStore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "event")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EventStore {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "descr", nullable = false)
    @NotBlank
    @Size(min = 1, max = 5000)
    private String descr;

    @Column(name = "hide_state")
    private boolean hideState = true;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ActionStore> actions = new ArrayList<>();

    public EventStore(String descr) {
        this.descr = descr;
    }
}
