package me.nekoh.guilds.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import me.nekoh.guilds.managers.PlayerManager;
import me.nekoh.guilds.player.PlayerData;
import me.nekoh.guilds.utils.CC;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class HomeCommand  extends BaseCommand {

    @CommandAlias("ghome|gdom|dom")
    @CommandPermission("guilds.home")
    public void execute(CommandSender sender) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(CC.translate("&cTa komenda moze byc uzyta tylko w grze!"));
            return;
        }

        Player player = (Player) sender;
        PlayerData playerData = PlayerManager.getPlayers().get(player.getUniqueId());

        if (playerData.getGuild() == null) {
            sender.sendMessage(CC.translate("&cNie jestes w gildii!"));
            return;
        }

        if (playerData.getAttackCooldown() > 0) {
            sender.sendMessage(CC.translate("&cNie mozesz sie teleportowac podczas walki!"));
            return;
        }

        sender.sendMessage(CC.translate("&aTeleportacja nastapi za 10 sekund."));
        player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20 * 10, 99999));
        player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 20 * 10, 99999));
        playerData.setTeleportCancelled(false);
        playerData.setWasTeleporting(true);
        playerData.setTeleportCooldown(10);
    }
}
