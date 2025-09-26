package dev.blynchik.quest_bot.model.content.action;

import dev.blynchik.quest_bot.model.content.condition.expression.Expression;
import dev.blynchik.quest_bot.model.content.result.ResultStore;
import io.hypersistence.utils.hibernate.type.json.JsonBinaryType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "action")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActionStore {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "descr", nullable = false)
    @NotNull
    private String descr;

    @Type(JsonBinaryType.class)
    @Column(name = "condition", columnDefinition = "jsonb")
    private Expression condition;

    @Column(name = "hide_improbable", nullable = false)
    @NotNull
    private Boolean hideImprobable = true;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "action_result",
            joinColumns = @JoinColumn(name = "action_id"),
            inverseJoinColumns = @JoinColumn(name = "result_id")
    )
    private List<ResultStore> results = new ArrayList<>();

    public ActionStore(String descr, Expression condition) {
        this.descr = descr;
        this.condition = condition;
    }

    public ActionStore(String descr, Boolean hideImprobable, Expression condition) {
        this.descr = descr;
        this.hideImprobable = hideImprobable;
        this.condition = condition;
    }
}
