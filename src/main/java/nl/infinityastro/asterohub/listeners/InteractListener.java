package nl.infinityastro.asterohub.listeners;

import nl.infinityastro.asterohub.AsteroHub;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.Set;

public class InteractListener implements Listener {

    private final AsteroHub plugin;
    private final int inventorySize;

    public InteractListener(AsteroHub plugin) {
        this.plugin = plugin;
        this.inventorySize = plugin.getConfig().getInt("inventory_size", 9) * 9; // Convert rows to slots
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        event.setCancelled(true);
        Player player = event.getPlayer();
        ItemStack item = event.getItem();

        if (item == null || item.getType() == Material.AIR) {
            return;
        }

        // Check if the item clicked is the server selector item
        if (item.getType() == Material.COMPASS && item.hasItemMeta()) {
            ItemMeta meta = item.getItemMeta();
            if (meta.hasDisplayName() && meta.getDisplayName().equals("§7Server Selector")) {
                event.setCancelled(true); // Prevent the item from being used for its normal purpose

                // Open the server selector inventory
                player.openInventory(loadServerSelector(player));
            }
        }
    }

    private Inventory loadServerSelector(Player player) {
        Inventory serverSelector = Bukkit.createInventory(null, inventorySize, "Server Selector");

        // Load servers from config
        Set<String> serverKeys = plugin.getConfig().getConfigurationSection("servers").getKeys(false);
        for (String key : serverKeys) {
            String name = plugin.getConfig().getString("servers." + key + ".name");
            String permission = plugin.getConfig().getString("servers." + key + ".permission");
            String server = plugin.getConfig().getString("servers." + key + ".server");
            Material material = Material.matchMaterial(plugin.getConfig().getString("servers." + key + ".material", "STONE"));
            List<String> lore = plugin.getConfig().getStringList("servers." + key + ".lore");
            int slot = plugin.getConfig().getInt("servers." + key + ".slot");

            if (player.hasPermission(permission)) {
                ItemStack item = new ItemStack(material);
                ItemMeta meta = item.getItemMeta();
                meta.setDisplayName(name);
                meta.setLore(lore);
                item.setItemMeta(meta);

                serverSelector.setItem(slot, item);
            }
        }

        return serverSelector;
    }
}
