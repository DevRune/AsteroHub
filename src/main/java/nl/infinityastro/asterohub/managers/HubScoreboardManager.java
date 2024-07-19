package nl.infinityastro.asterohub.managers;

import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import nl.infinityastro.asterohub.AsteroHub;
import nl.infinityastro.asterohub.utils.ColorUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

public class HubScoreboardManager {

    private final AsteroHub plugin;

    public HubScoreboardManager(AsteroHub plugin) {
        this.plugin = plugin;
    }



    public void setScoreboard(Player player) {
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard board = manager.getNewScoreboard();
        Objective objective = board.registerNewObjective("hub", "dummy", "§5§lInfinity Astro");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        // Setting up scoreboard entries
        objective.getScore(" ").setScore(8);
        objective.getScore("§d§lName:").setScore(7);
        objective.getScore(player.getName()).setScore(6);
        objective.getScore("  ").setScore(5);
        objective.getScore("§d§lRank:").setScore(4);

        // Getting and setting the rank placeholder
        String rank = getRank(player);
        objective.getScore(ColorUtils.componentToStringWithColors(ColorUtils.parseHtmlEntities(rank))).setScore(3);

        objective.getScore("   ").setScore(2);
        objective.getScore(ChatColor.GRAY + plugin.getConfig().getString("server.ip")).setScore(1);

        player.setScoreboard(board);
    }

    private String getRank(Player player) {
        return PlaceholderAPI.setPlaceholders(player, "%vault_suffix%");
    }
}
