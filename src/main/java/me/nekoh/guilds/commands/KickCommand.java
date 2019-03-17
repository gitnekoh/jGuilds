package me.nekoh.guilds.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import me.nekoh.guilds.managers.PlayerManager;
import me.nekoh.guilds.player.PlayerData;
import me.nekoh.guilds.utils.CC;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.annotation.Syntax;

public class KickCommand extends BaseCommand {

    @CommandAlias("gkick|gwyrzuc|wyrzuc")
    @CommandPermission("guilds.kick")
    @Syntax("<player>")
    public void execute(CommandSender sender, String name) {
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

        if (!playerData.getGuild().getLeader().equals(player.getUniqueId()) && !playerData.getGuild().getModerators().contains(player.getUniqueId())) {
            sender.sendMessage(CC.translate("&cNie jestes liderem ani moderatorem!"));
            return;
        }

        Player target = Bukkit.getPlayer(name);

        if (target == null) {
            sender.sendMessage(CC.translate("&cNie ma takiego gracza!"));
            return;
        }

        PlayerData targetData = PlayerManager.getPlayers().get(target.getUniqueId());

        if (targetData.getGuild() == null) {
            sender.sendMessage(CC.translate("&cTen gracz nie ma gildii!"));
            return;
        }

        if (targetData.getGuild() != playerData.getGuild()) {
            sender.sendMessage(CC.translate("&cTen gracz nie jest w twojej gildii!"));
            return;
        }

        if(playerData.getGuild().getLeader().equals(target.getUniqueId())) {
            sender.sendMessage(CC.translate("&cNie mozesz wyrzucic lidera gildii!"));
            return;
        }

        if(playerData.getGuild().getModerators().contains(player.getUniqueId()) && playerData.getGuild().getModerators().contains(target.getUniqueId())) {
            sender.sendMessage(CC.translate("&cTylko lider moze wyrzucic moderatora z gildii!"));
            return;
        }

        playerData.getGuild().getModerators().remove(target.getUniqueId());
        playerData.getGuild().getMembers().remove(targetData.getUuid());
        targetData.setGuild(null);
        player.sendMessage(CC.translate("&aWyrzuciles gracza &f" + target.getName() + "&a z gildii."));
        target.sendMessage(CC.translate("&cZostales wyrzucony z gildii &f" + playerData.getGuild().getTag() + "&c!"));
        sender.sendMessage(CC.translate("&aGracz &f" + target.getName() + "&a zostal wyrzucony z gildii &f" + playerData.getGuild().getTag() + "&a."));
    }
}