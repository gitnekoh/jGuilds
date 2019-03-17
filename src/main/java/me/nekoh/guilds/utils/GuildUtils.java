package me.nekoh.guilds.utils;

import me.nekoh.guilds.guild.Guild;
import me.nekoh.guilds.managers.GuildManager;

import java.util.ArrayList;
import java.util.List;

public class GuildUtils {

    public static List<Guild> deserialize(List<String> strings) {
        List<Guild> toReturn = new ArrayList<>();
        strings.forEach(guildName -> toReturn.add(GuildManager.getFromName(guildName).get()));
        return toReturn;
    }

    public static List<String> serialize(List<Guild> name) {
        List<String> toReturn = new ArrayList<>();
        name.forEach(guildName -> toReturn.add(guildName.getName()));
        return toReturn;
    }
}
