package dev.blynchik.quest_bot.service.message.customizer;

import dev.blynchik.quest_bot.model.user.PlayerCustom;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
@Slf4j
public class TextCustomizer {
    private final Pattern placeholder = Pattern.compile("\\{([A-Za-z0-9_]+)\\}");

    public String customizeEvent(String eventDescr, List<String> actionsDescr, PlayerCustom custom) {
        log.info("Customize reply");
        String text = eventDescr +
                "\n\n" + "Варианты\n=============================\n" +
                IntStream.range(0, actionsDescr.size())
                        .mapToObj(i -> (i + 1) + ". " + actionsDescr.get(i) + "\n\n")
                        .collect(Collectors.joining());
        return customizeText(text, custom);
    }

    public String customizeQuestPreview(String title, String descr, PlayerCustom custom) {
        log.info("Customize quest preview");
        String text = "*%s*\n\n%s".formatted(title, descr);
        return customizeText(text, custom);
    }

    public String customizeText(String text, PlayerCustom custom) {
        log.info("Customize text: {}", text);
        Matcher matcher = placeholder.matcher(text);
        StringBuffer sb = new StringBuffer();
        Map<String, String> actualValues = Map.of("ranger_name", custom.getRanger(),
                "planet_name", custom.getPlanet(),
                "system_name", custom.getSystem(),
                "reward_value", custom.getReward(),
                "date", custom.getTillDate());
        while (matcher.find()) {
            String key = matcher.group(1);
            String replacement = actualValues.get(key);
            if (replacement == null) {
                throw new IllegalArgumentException("Missing value for placeholder: " + key);
            }
            replacement = Matcher.quoteReplacement(replacement);
            matcher.appendReplacement(sb, replacement);
        }
        matcher.appendTail(sb);
        return sb.toString();
    }
}
