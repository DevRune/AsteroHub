package nl.infinityastro.asterohub;


import nl.infinityastro.asterohub.commands.SetSpawnCommand;
import nl.infinityastro.asterohub.commands.SpawnCommand;
import nl.infinityastro.asterohub.listeners.*;
import nl.infinityastro.asterohub.managers.HubScoreboardManager;
import nl.infinityastro.asterohub.managers.TablistManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class AsteroHub extends JavaPlugin {

    private static AsteroHub instance;
    private HubScoreboardManager scoreboardManager;
    private TablistManager tablistManager;

    @Override
    public void onEnable() {
        instance = this;

        // Load configurations and setup
        saveDefaultConfig();


        // Register commands
        getCommand("spawn").setExecutor(new SpawnCommand());
        getCommand("setspawn").setExecutor(new SetSpawnCommand());


        // Register event listeners
        Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerQuitListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerInteractListener(), this);
        Bukkit.getPluginManager().registerEvents(new BlockBreakListener(), this);
        Bukkit.getPluginManager().registerEvents(new BlockPlaceListener(), this);
        Bukkit.getPluginManager().registerEvents(new EntityDamageListener(), this);
        Bukkit.getPluginManager().registerEvents(new WeatherChangeListener(), this);
        Bukkit.getPluginManager().registerEvents(new InventoryClickListener(this), this);
        Bukkit.getPluginManager().registerEvents(new PlayerChatListener(), this);

        // Initialize managers
        scoreboardManager = new HubScoreboardManager(this);
        tablistManager = new TablistManager(this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static AsteroHub getInstance() {
        return instance;
    }

    public HubScoreboardManager getScoreboardManager() {
        return scoreboardManager;
    }

    public TablistManager getTablistManager() {
        return tablistManager;
    }
}
