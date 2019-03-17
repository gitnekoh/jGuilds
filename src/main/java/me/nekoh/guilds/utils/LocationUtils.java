package me.nekoh.guilds.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class LocationUtils {

    public static String serialize(Location location) {
        return location.getWorld().getName() + "@"
                + location.getBlockX() + "@"
                + location.getBlockY() + "@"
                + location.getBlockZ() + "@"
                + location.getYaw() + "@"
                + location.getPitch();
    }

    public static Location deserialize(String string) {
        String[] location = string.split("@");
        return new Location(
                Bukkit.getWorld(location[0]),
                Double.valueOf(location[1]),
                Double.valueOf(location[2]),
                Double.valueOf(location[3]),
                Float.valueOf(location[4]),
                Float.valueOf(location[5]));
    }
}
