package me.nekoh.guilds.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UUIDUtils {

    public static List<String> serialize(List<UUID> uuids) {
        List<String> toReturn = new ArrayList<>();
        uuids.forEach(uuid -> toReturn.add(uuid.toString()));
        return toReturn;
    }

    public static List<UUID> deserialize(List<String> strings) {
        List<UUID> toReturn = new ArrayList<>();
        strings.forEach(string -> toReturn.add(UUID.fromString(string)));
        return toReturn;
    }
}
