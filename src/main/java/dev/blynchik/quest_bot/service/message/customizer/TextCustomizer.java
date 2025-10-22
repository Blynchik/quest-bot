package dev.blynchik.quest_bot.service.message.customizer;

import dev.blynchik.quest_bot.model.user.PlayerCustom;
import dev.blynchik.quest_bot.service.model.quest.PlayerCustomStateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static dev.blynchik.quest_bot.config.bot.util.TextCustomizerUtil.*;

@Component
@Slf4j
public class TextCustomizer {
    private final Pattern placeholder = Pattern.compile("\\{([A-Za-z0-9_]+)\\}");
    private final PlayerCustomStateService playerCustomStateService;

    @Autowired
    public TextCustomizer(PlayerCustomStateService playerCustomStateService) {
        this.playerCustomStateService = playerCustomStateService;
    }

    public String customizeEvent(String eventDescr, List<String> actionsDescr, PlayerCustom custom, boolean hideState) {
        log.info("Customize reply");
        Map<String, String> customizedStates = playerCustomStateService.customizeState(custom);
        String statePart = hideState ? "" : "%s%s".formatted(CUSTOM_STATE_SEPARATOR.replace(),
                customizedStates.keySet().stream()
                        .map(k -> "%s:\n%s\n\n".formatted(k, customizedStates.get(k)))
                        .collect(Collectors.joining()));
        String text = "%s%s%s%s".formatted(
                eventDescr,
                statePart,
                ACTIONS_SEPARATOR.replace(),
                IntStream.range(0, actionsDescr.size())
                        .mapToObj(i -> "%d. %s\n\n".formatted(i + 1, actionsDescr.get(i)))
                        .collect(Collectors.joining()));
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
        StringBuilder sb = new StringBuilder();
        Map<String, String> actualValues = Map.of(
                RANGER.replace(), custom.getRanger(),
                PLANET.replace(), custom.getPlanet(),
                SYSTEM.replace(), custom.getSystem(),
                REWARD.replace(), custom.getReward(),
                DATE.replace(), custom.getTillDate());
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
