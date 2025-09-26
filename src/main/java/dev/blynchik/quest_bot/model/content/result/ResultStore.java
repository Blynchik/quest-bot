package dev.blynchik.quest_bot.model.content.result;

import dev.blynchik.quest_bot.model.content.action.ActionStore;
import dev.blynchik.quest_bot.model.content.condition.expression.Expression;
import dev.blynchik.quest_bot.model.content.consequence.ConsequenceStore;
import io.hypersistence.utils.hibernate.type.json.JsonBinaryType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "result")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultStore {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Type(JsonBinaryType.class)
    @Column(name = "condition", columnDefinition = "jsonb")
    private Expression condition;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "result_consequence",
            joinColumns = @JoinColumn(name = "result_id"),
            inverseJoinColumns = @JoinColumn(name = "consequence_id")
    )
    private List<ConsequenceStore> consequences = new ArrayList<>();

    @ManyToMany(mappedBy = "results", fetch = FetchType.LAZY)
    private List<ActionStore> actions = new ArrayList<>();

    public ResultStore(Expression condition) {
        this.condition = condition;
    }
}
