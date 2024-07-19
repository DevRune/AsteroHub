package nl.infinityastro.asterohub.commands;

import nl.infinityastro.asterohub.AsteroHub;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetSpawnCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            Location loc = player.getLocation();
            AsteroHub.getInstance().getConfig().set("hub.spawn", loc);
            AsteroHub.getInstance().saveConfig();
            player.sendMessage("§aHub spawn location set!");
            return true;
        } else {
            sender.sendMessage("§cOnly players can use this command.");
            return false;
        }
    }
}
