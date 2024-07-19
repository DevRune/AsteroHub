package nl.infinityastro.asterohub.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ColorUtils {

    // Regular expression voor het vinden van HTML-entiteiten zoals &#abcdef
    private static final Pattern HTML_ENTITY_PATTERN = Pattern.compile("&#([0-9a-fA-F]{6})");

    // Methode om een tekst met HTML-entiteiten naar een gekleurde Component te converteren
    public static Component parseHtmlEntities(String message) {
        TextComponent.Builder builder = Component.text();

        Matcher matcher = HTML_ENTITY_PATTERN.matcher(message);
        int lastEnd = 0;
        while (matcher.find()) {
            // Voeg de tekst voor de match toe als grijs
            builder.append(Component.text(message.substring(lastEnd, matcher.start()), NamedTextColor.GRAY));

            // Voeg de gematchte kleur toe aan de tekst erna
            String hexColor = matcher.group(1);
            TextColor color = TextColor.fromHexString("#" + hexColor);
            String textAfterColor = message.substring(matcher.end());
            builder.append(Component.text(textAfterColor).style(Style.style().color(color)));

            // Update lastEnd
            lastEnd = matcher.end() + textAfterColor.length();
        }

        // Voeg de resterende tekst toe als grijs
        builder.append(Component.text(message.substring(lastEnd)));

        return builder.build();
    }

    public static String componentToString(Component component) {
        return PlainTextComponentSerializer.plainText().serialize(component);
    }

    public static String componentToStringWithColors(Component component) {
        // Converteer naar een Legacy tekst representatie met kleurcodes
        return LegacyComponentSerializer.legacySection().serialize(component);
    }
}
