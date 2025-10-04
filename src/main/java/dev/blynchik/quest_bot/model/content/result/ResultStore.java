package dev.blynchik.quest_bot.model.content.result;

import dev.blynchik.quest_bot.model.content.consequence.ConsequenceStore;
import dev.blynchik.quest_bot.model.content.expression.Expression;
import io.hypersistence.utils.hibernate.type.json.JsonBinaryType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
@Builder
public class ResultStore {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Type(JsonBinaryType.class)
    @Column(name = "condition", columnDefinition = "jsonb")
    private Expression condition;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ConsequenceStore> consequences = new ArrayList<>();

    public ResultStore(Expression condition) {
        this.condition = condition;
    }
}
