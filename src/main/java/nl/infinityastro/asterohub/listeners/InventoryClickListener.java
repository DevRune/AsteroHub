package nl.infinityastro.asterohub.listeners;

import nl.infinityastro.asterohub.AsteroHub;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class InventoryClickListener implements Listener {

    private final AsteroHub plugin;

    public InventoryClickListener(AsteroHub plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!event.getView().getTitle().equals("Server Selector")) {
            return; // Check if the inventory is the server selector
        }

        event.setCancelled(true); // Cancel the event to prevent taking the item

        ItemStack clickedItem = event.getCurrentItem();
        if (clickedItem == null || !clickedItem.hasItemMeta()) {
            return;
        }

        ItemMeta itemMeta = clickedItem.getItemMeta();
        if (!itemMeta.hasDisplayName()) {
            return;
        }

        Player player = (Player) event.getWhoClicked();
        String serverName = null;

        for (String key : plugin.getConfig().getConfigurationSection("servers").getKeys(false)) {
            String displayName = plugin.getConfig().getString("servers." + key + ".name");
            if (itemMeta.getDisplayName().equals(displayName)) {
                serverName = plugin.getConfig().getString("servers." + key + ".server");
                break;
            }
        }

        if (serverName != null) {
            // Use a plugin messaging channel to connect the player to the server
            player.sendMessage("Connecting to " + serverName + "...");
            player.performCommand("server " + serverName); // Example command, replace with actual server switch command
            player.closeInventory();
        }
    }
}
