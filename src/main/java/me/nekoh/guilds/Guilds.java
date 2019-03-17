package me.nekoh.guilds;

import co.aikar.commands.BukkitCommandManager;
import co.aikar.commands.CommandManager;
import lombok.Getter;
import me.nekoh.guilds.guild.Guild;
import me.nekoh.guilds.listeners.ChatListener;
import me.nekoh.guilds.listeners.DataListener;
import me.nekoh.guilds.listeners.CuboidListener;
import me.nekoh.guilds.listeners.MovementListener;
import me.nekoh.guilds.managers.DataManager;
import me.nekoh.guilds.managers.GuildManager;
import me.nekoh.guilds.managers.PlayerManager;
import me.nekoh.guilds.tasks.CooldownTask;
import me.nekoh.guilds.commands.*;
import me.nekoh.guilds.tasks.SortTask;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;


@Getter
public final class Guilds extends JavaPlugin {

    private DataManager dataManager;
    private GuildManager guildManager;
    private PlayerManager playerManager;

    @Getter
    public static Guilds instance;

    @Override
    public void onLoad() {
        this.dataManager = new DataManager();

        dataManager.connect();
    }

    @Override
    public void onEnable() {
        instance = this;
        this.guildManager = new GuildManager();
        this.playerManager = new PlayerManager();
        this.guildManager.loadAll();

        new CooldownTask().runTaskTimerAsynchronously(this, 20L, 20L);
        new SortTask().runTaskTimerAsynchronously(this, 20 * 60, 20L);
        new DataListener();
        new ChatListener();
        new CuboidListener();
        new MovementListener();

        loadCommands();

        Bukkit.getOnlinePlayers().forEach(player -> this.playerManager.load(player.getUniqueId()));
    }

    @Override
    public void onDisable() {
        Bukkit.getOnlinePlayers().forEach(player -> this.playerManager.unLoad(player.getUniqueId()));
        GuildManager.getGuilds().values().forEach(Guild::save);
        instance = null;
    }

    private void loadCommands() {
        CommandManager commandManager = new BukkitCommandManager(this);
        commandManager.registerCommand(new GuildCommand());
        commandManager.registerCommand(new CreateCommand());
        commandManager.registerCommand(new InfoCommand());
        commandManager.registerCommand(new HomeCommand());
        commandManager.registerCommand(new InviteCommand());
        commandManager.registerCommand(new JoinCommand());
        commandManager.registerCommand(new KickCommand());
        commandManager.registerCommand(new LeaveCommand());
    }
}
