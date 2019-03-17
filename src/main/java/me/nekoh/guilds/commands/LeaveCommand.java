package me.nekoh.guilds.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import me.nekoh.guilds.managers.GuildManager;
import me.nekoh.guilds.managers.PlayerManager;
import me.nekoh.guilds.player.PlayerData;
import me.nekoh.guilds.utils.CC;
import me.nekoh.guilds.utils.MessageUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class LeaveCommand extends BaseCommand {


    @CommandAlias("gleave|gopusc|opusc")
    @CommandPermission("guilds.leave")
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

        playerData.getGuild().getMembers().remove(player.getUniqueId());
        playerData.getGuild().getModerators().remove(player.getUniqueId());

        player.sendMessage(CC.translate("&aOdszedles z gildii &f" + playerData.getGuild().getTag() + "&a."));

        if (playerData.getGuild().getLeader().equals(player.getUniqueId())) {
            MessageUtils.broadcast("&aGildia &f" + playerData.getGuild().getTag() + "&a zostala usunieta.");
            playerData.getGuild().getMembers().forEach(member -> PlayerManager.getPlayers().get(member).setGuild(null));
            GuildManager.getGuilds().remove(playerData.getGuild().getTag());
        } else {
            playerData.getGuild().getMembers().forEach(member -> PlayerManager.getPlayers().get(member).getPlayer().sendMessage(CC.translate("&aGracz &f" + player.getName() + " &aopuscil gildie.")));
        }

        playerData.setGuild(null);
    }
}
