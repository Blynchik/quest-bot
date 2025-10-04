package dev.blynchik.quest_bot.model.content.expression.condition;

import dev.blynchik.quest_bot.model.content.expression.condition.params.AlwaysParams;
import dev.blynchik.quest_bot.model.content.expression.condition.params.ConditionParams;
import io.hypersistence.utils.hibernate.type.json.JsonBinaryType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "condition")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConditionStore {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    @NotNull
    private ConditionType type = ConditionType.ALWAYS;

    @Type(JsonBinaryType.class)
    @Column(name = "params", columnDefinition = "jsonb", nullable = false)
    @NotNull
    private ConditionParams params = new AlwaysParams();

    public ConditionStore(ConditionParams params) {
        this.type = params.getType();
        this.params = params;
    }
}
