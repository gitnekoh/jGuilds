package me.nekoh.guilds.utils;

import me.nekoh.guilds.guild.Guild;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class CC {

    public static String translate(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static String line() {
        return ChatColor.STRIKETHROUGH + "-------------------------------------------------";
    }

    public static String shortLine() {
        return ChatColor.STRIKETHROUGH + "---------------------";
    }

    public static String formattedModeratorList(Guild guild) {
        StringBuilder sb = new StringBuilder();
        guild.getModerators().forEach(member -> sb.append(Bukkit.getOfflinePlayer(member).getName()).append(", "));
        return sb.toString();
    }

    public static String formattedMemberList(Guild guild) {
        StringBuilder sb = new StringBuilder();
        guild.getMembers().forEach(member -> sb.append(Bukkit.getOfflinePlayer(member).getName()).append(", "));
        return sb.toString();
    }
}
