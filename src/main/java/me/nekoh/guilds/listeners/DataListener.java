package me.nekoh.guilds.listeners;

import me.nekoh.guilds.Guilds;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class DataListener implements Listener {

    public DataListener() {
        Bukkit.getPluginManager().registerEvents(this, Guilds.getInstance());
    }

    @EventHandler
    public void loadData(AsyncPlayerPreLoginEvent event) {
        Guilds.getInstance().getPlayerManager().load(event.getUniqueId());
    }

    @EventHandler
    public void unloadData(PlayerQuitEvent event) {
        Guilds.getInstance().getPlayerManager().unLoad(event.getPlayer().getUniqueId());
    }
}
