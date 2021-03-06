package me.nekoh.guilds.tasks;

import me.nekoh.guilds.managers.PlayerManager;
import me.nekoh.guilds.utils.CC;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class CooldownTask extends BukkitRunnable {
    @Override
    public void run() {
       PlayerManager.getPlayers().values().parallelStream().forEach(playerData -> {
           if (playerData.getTeleportCooldown() > 0) {
               playerData.setTeleportCooldown(playerData.getTeleportCooldown() - 1);
           } else if (playerData.getTeleportCooldown() == 0 && playerData.isWasTeleporting()) {
               playerData.setWasTeleporting(false);
               if (!playerData.isTeleportCancelled()) {
                   playerData.getPlayer().teleport(playerData.getGuild().getCenter());
                   playerData.getPlayer().sendMessage(CC.translate("&aPrzeteleportowano do domu gildii."));
               }
               playerData.getPlayer().removePotionEffect(PotionEffectType.BLINDNESS);
               playerData.getPlayer().removePotionEffect(PotionEffectType.CONFUSION);
           }
           if (playerData.getAttackCooldown() > 0) {
               playerData.setAttackCooldown(playerData.getAttackCooldown() - 1);
           }
       });

    }
}
