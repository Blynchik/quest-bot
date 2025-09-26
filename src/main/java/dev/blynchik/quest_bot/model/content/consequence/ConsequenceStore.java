package dev.blynchik.quest_bot.model.content.consequence;

import dev.blynchik.quest_bot.model.content.consequence.params.ConsequenceParams;
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
@Table(name = "consequence")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConsequenceStore {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    @NotNull
    private ConsequenceType type;

    @Type(JsonBinaryType.class)
    @Column(name = "params", columnDefinition = "jsonb", nullable = false)
    @NotNull
    private ConsequenceParams params;

    @ManyToMany(mappedBy = "consequences", fetch = FetchType.LAZY)
    private List<ResultStore> results = new ArrayList<>();

    public ConsequenceStore(ConsequenceParams params) {
        this.type = params.getType();
        this.params = params;
    }
}
