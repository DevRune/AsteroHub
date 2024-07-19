package nl.infinityastro.asterohub.managers;

import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.text.Component;
import nl.infinityastro.asterohub.AsteroHub;
import nl.infinityastro.asterohub.utils.ColorUtils;
import org.bukkit.entity.Player;

public class TablistManager {

    private final AsteroHub plugin;

    public TablistManager(AsteroHub plugin) {
        this.plugin = plugin;
    }

    public void setTablist(Player player) {
        String header = "§5§lInfinity Astro";
        String footer = "§d" + plugin.getConfig().getString("server.ip");

        Component prefix = ColorUtils.parseHtmlEntities(PlaceholderAPI.setPlaceholders(player, "%vault_prefix%"));
        player.setPlayerListHeaderFooter(header, footer);
        player.playerListName(prefix.append(Component.text(player.getName())));
    }
}
