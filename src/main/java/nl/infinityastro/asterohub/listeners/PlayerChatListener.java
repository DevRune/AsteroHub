package nl.infinityastro.asterohub.listeners;

import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.text.Component;
import nl.infinityastro.asterohub.utils.ChatUtils;
import nl.infinityastro.asterohub.utils.ColorUtils;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.ChatColor;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.entity.Player;

public class PlayerChatListener implements Listener {

    private final Map<Player, Long> lastChat = new HashMap<>();

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        if (!player.hasPermission("astero.hub.bypass.swear")) {
            if (ChatUtils.containsSwearWords(event.getMessage())) {
                event.setCancelled(true);
                player.sendMessage(ChatColor.RED + "Please refrain from using inappropriate language.");
                return;
            }
        }

        if (!player.hasPermission("astero.hub.bypass.chatcooldown")) {
            long cooldown = Math.min(20000, 250 * event.getPlayer().getWorld().getPlayers().size());
            long currentTime = System.currentTimeMillis();
            long lastTime = lastChat.getOrDefault(player, 0L);
            if (currentTime - lastTime < cooldown) {
                event.setCancelled(true);
                player.sendMessage(ChatColor.RED + "You are sending messages too fast!");
                return;
            }
            lastChat.put(player, currentTime);
        }

        String prefix = PlaceholderAPI.setPlaceholders(player, "%vault_prefix%");
        event.setCancelled(true);
        Bukkit.broadcast(ColorUtils.parseHtmlEntities(prefix).append(Component.text(player.getDisplayName() + ChatColor.WHITE + ": " + ChatColor.GRAY + event.getMessage())));
    }
}
