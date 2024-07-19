package nl.infinityastro.asterohub.utils;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class ChatUtils {

    private static final List<String> SWEAR_WORDS = Arrays.asList(
            "fuck", "shit", "asshole", "bitch", "bastard", "cunt", "dick", "piss",
            "cock", "twat", "bollocks", "wanker", "arsehole", "motherfucker", "arse"
    );
    private static final Pattern SWEAR_PATTERN;

    static {
        StringBuilder patternString = new StringBuilder();
        for (String word : SWEAR_WORDS) {
            patternString.append("\\b").append(word).append("\\b|");
        }
        patternString.setLength(patternString.length() - 1); // Remove last "|"
        SWEAR_PATTERN = Pattern.compile(patternString.toString(), Pattern.CASE_INSENSITIVE);
    }

    public static boolean containsSwearWords(String message) {
        return SWEAR_PATTERN.matcher(message).find();
    }
}
