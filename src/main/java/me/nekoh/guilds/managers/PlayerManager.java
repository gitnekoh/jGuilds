package me.nekoh.guilds.managers;

import lombok.Getter;
import me.nekoh.guilds.Guilds;
import me.nekoh.guilds.player.PlayerData;
import org.bukkit.Bukkit;

import java.util.HashMap;
import java.util.UUID;
import java.util.logging.Level;

public class PlayerManager {

    @Getter
    private static HashMap<UUID, PlayerData> players = new HashMap<>();

    private PlayerData get(UUID uuid) {
        return players.computeIfAbsent(uuid, PlayerData::new);
    }

    private void remove(UUID uuid) {
        players.remove(uuid);
    }

    public void load(UUID uuid) {
        Bukkit.getScheduler().runTaskAsynchronously(Guilds.getInstance(), () -> {
            long now = System.currentTimeMillis();

            Guilds.getInstance().getPlayerManager().get(uuid).load();

            Bukkit.getScheduler().runTask(Guilds.getInstance(), () -> Bukkit.getLogger().log(Level.INFO, "Loaded player " + uuid + " data in " + (System.currentTimeMillis() - now) + " ms."));
        });
    }

    public void unLoad(UUID uuid) {
        Bukkit.getScheduler().runTaskAsynchronously(Guilds.getInstance(), () -> {
            long now = System.currentTimeMillis();

            Guilds.getInstance().getPlayerManager().get(uuid).save();

            Bukkit.getScheduler().runTask(Guilds.getInstance(), () -> {
                Bukkit.getLogger().log(Level.INFO, "Saved player " + uuid + " data in " + (System.currentTimeMillis() - now) + " ms.");
                Guilds.getInstance().getPlayerManager().remove(uuid);
            });
        });
    }
}
