package me.nekoh.guilds.listeners;

import me.nekoh.guilds.Guilds;
import me.nekoh.guilds.managers.PlayerManager;
import me.nekoh.guilds.player.PlayerData;
import me.nekoh.guilds.utils.CC;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class MovementListener implements Listener {

    public MovementListener() {
        Bukkit.getPluginManager().registerEvents(this, Guilds.getInstance());
    }
    @EventHandler
    public void onCooldownCancel(PlayerMoveEvent event) {
        if (event.getFrom().getX() == event.getTo().getX() && event.getFrom().getY() == event.getTo().getY() && event.getFrom().getZ() == event.getTo().getZ()) {
            return;
        }

        PlayerData playerData = PlayerManager.getPlayers().get(event.getPlayer().getUniqueId());

        if (playerData.getTeleportCooldown() > 0) {
            event.getPlayer().sendMessage(CC.translate("&cTeleportowanie zostalo przerwane z powodu poruszenia sie!"));
            playerData.setTeleportCancelled(true);
            playerData.setTeleportCooldown(0);
        }
    }
}
