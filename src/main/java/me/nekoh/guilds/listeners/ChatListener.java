package me.nekoh.guilds.listeners;

import me.nekoh.guilds.Guilds;
import me.nekoh.guilds.managers.PlayerManager;
import me.nekoh.guilds.player.PlayerData;
import me.nekoh.guilds.utils.MessageUtils;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {

    public ChatListener() {
        Bukkit.getPluginManager().registerEvents(this, Guilds.getInstance());
    }

    @EventHandler
    public void chatFormat(AsyncPlayerChatEvent event) {
        PlayerData playerData = PlayerManager.getPlayers().get(event.getPlayer().getUniqueId());
        String formattedMessage = (playerData.getGuild() != null ? "[" + playerData.getGuild().getTag() + "] " : "") + event.getPlayer().getName() + "&f: " + event.getMessage();
        MessageUtils.broadcast(formattedMessage);
        event.setCancelled(true);
    }
}
