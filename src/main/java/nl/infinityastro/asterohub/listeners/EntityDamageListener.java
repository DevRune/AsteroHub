package nl.infinityastro.asterohub.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.entity.Player;

public class EntityDamageListener implements Listener {

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if (event.getCause() == EntityDamageEvent.DamageCause.VOID) {
                if (!player.hasPermission("astero.hub.bypass.voiddeath")) {
                    player.teleport(player.getWorld().getSpawnLocation());
                    event.setCancelled(true);
                }
            } else if (!player.hasPermission("astero.hub.bypass.falldamage") && event.getCause() == EntityDamageEvent.DamageCause.FALL) {
                event.setCancelled(true);
            } else if (!player.hasPermission("astero.hub.bypass.pvp") && event.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK) {
                event.setCancelled(true);
            }
        }
    }
}
