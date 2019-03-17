package me.nekoh.guilds.utils;

import me.nekoh.guilds.Guilds;
import org.bukkit.Bukkit;

public class MessageUtils {

    public static void broadcast(String message) {
        Bukkit.getScheduler().runTaskAsynchronously(Guilds.getInstance(), () -> Bukkit.getOnlinePlayers().forEach(player -> player.sendMessage(CC.translate(message))));
    }

    public static void broadcastStaff(String permission, String message) {
        Bukkit.getScheduler().runTaskAsynchronously(Guilds.getInstance(), () -> Bukkit.getOnlinePlayers().stream().filter(player -> player.hasPermission(permission)).forEach(player -> player.sendMessage(CC.translate(message))));
    }
}
