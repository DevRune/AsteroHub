package nl.infinityastro.asterohub.listeners;

import nl.infinityastro.asterohub.AsteroHub;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        // Clear inventory and chat
        player.getInventory().clear();
        for (int i = 0; i < 100; i++) {
            player.sendMessage("");
        }

        // Set join message
        event.setJoinMessage("§a" + player.getName() + " joined the lobby!");

        // Teleport to spawn
        player.teleport(AsteroHub.getInstance().getConfig().getLocation("hub.spawn"));

        // Set scoreboard and tablist
        AsteroHub.getInstance().getScoreboardManager().setScoreboard(player);
        AsteroHub.getInstance().getTablistManager().setTablist(player);

        // Enable flying if permitted
        if (player.hasPermission("astero.hub.fly")) {
            player.setAllowFlight(true);
        }

        ItemStack serverSelectorItem = new ItemStack(Material.COMPASS);
        ItemMeta meta = serverSelectorItem.getItemMeta();
        meta.setDisplayName("§7Server Selector");
        serverSelectorItem.setItemMeta(meta);
        player.getInventory().setItem(4, serverSelectorItem);

    }
}