package nl.infinityastro.asterohub.commands;

import nl.infinityastro.asterohub.AsteroHub;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpawnCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            player.teleport(AsteroHub.getInstance().getConfig().getLocation("hub.spawn"));
            player.sendMessage("§aYou have been teleported to the spawn!");
            return true;
        } else {
            sender.sendMessage("§cOnly players can use this command.");
            return false;
        }
    }
}
