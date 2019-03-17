package me.nekoh.guilds.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import me.nekoh.guilds.guild.Guild;
import me.nekoh.guilds.managers.GuildManager;
import me.nekoh.guilds.managers.PlayerManager;
import me.nekoh.guilds.player.PlayerData;
import me.nekoh.guilds.utils.CC;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Optional;

public class HomeCommand extends BaseCommand {

    @CommandAlias("ghome|gdom|dom")
    @CommandPermission("guilds.dom")
    public void execute(CommandSender sender) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(CC.translate("&cTa komenda moze byc uzyta tylko w grze!"));
            return;
        }

        System.out.println("1");
        Player player = (Player) sender;
        PlayerData playerData = PlayerManager.getPlayers().get(player.getUniqueId());
        System.out.println("22");
        if (playerData.getGuild() == null) {
            System.out.println("2");
            sender.sendMessage(CC.translate("&cNie jestes w gildii!"));
            return;
        }
        System.out.println("223");
        if (playerData.getAttackCooldown() > 0) {
            System.out.println("2");
            sender.sendMessage(CC.translate("&cNie mozesz sie teleportowac podczas walki!"));
            return;
        }
        System.out.println("224");

        Optional<Guild> opGuild = GuildManager.getGuilds().values().stream().parallel().filter(guild -> guild.getCuboid().isIn(player.getLocation())).findFirst();
        if (opGuild.isPresent()) {
            System.out.println("3");
            if (playerData.getGuild() != null) {
                System.out.println("4");
                if (opGuild.get() != playerData.getGuild() && !playerData.getGuild().getAllies().contains(opGuild.get())) {
                    System.out.println("5");
                    sender.sendMessage(CC.translate("&cNie mozesz sie teleportowac na terenie wrogiej gildii!"));
                    return;
                }
            } else {
                System.out.println("6");
                sender.sendMessage(CC.translate("&cNie mozesz sie teleportowac na terenie wrogiej gildii!"));
                return;
            }
        }

        System.out.println("7");
        sender.sendMessage(CC.translate("&aTeleportacja nastapi za 10 sekund."));
        player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20 * 10, 99999));
        player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 20 * 10, 99999));
        playerData.setTeleportCancelled(false);
        playerData.setWasTeleporting(true);
        playerData.setTeleportCooldown(10);
    }
}
