package me.nekoh.guilds.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Syntax;
import me.nekoh.guilds.guild.Guild;
import me.nekoh.guilds.managers.GuildManager;
import me.nekoh.guilds.managers.PlayerManager;
import me.nekoh.guilds.player.PlayerData;
import me.nekoh.guilds.utils.CC;
import me.nekoh.guilds.utils.MessageUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class JoinCommand extends BaseCommand {

    @CommandAlias("join|dolacz")
    @CommandPermission("guilds.join")
    @Syntax("<guild/tag>")
    public void execute(CommandSender sender, String tag) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(CC.translate("&cTa komenda moze byc uzyta tylko w grze!"));
            return;
        }

        Player player = (Player) sender;
        PlayerData playerData = PlayerManager.getPlayers().get(player.getUniqueId());

        if (playerData.getGuild() != null) {
            sender.sendMessage(CC.translate("&cJestes juz w gildii!"));
            return;
        }

        Guild guild = GuildManager.getFromTag(tag);

        if (guild == null) {
            if (!GuildManager.getFromName(tag).isPresent()) {
                sender.sendMessage(CC.translate("&cNie ma takiej gildii!"));
                return;
            } else {
                guild = GuildManager.getFromName(tag).get();
            }
        }

        if (!guild.getMemberInvites().contains(player.getUniqueId())) {
            sender.sendMessage(CC.translate("&cNie zostales zaproszony do tej gildii!"));
            return;
        }

        if (guild.getMaxMembers() <= guild.getMembers().size()) {
            sender.sendMessage(CC.translate("&cGildia jest juz pelna!"));
            return;
        }


        System.out.println(guild.getMembers().toArray());
        guild.getMemberInvites().remove(player.getUniqueId());
        guild.getMembers().add(player.getUniqueId());
        guild.save();

        playerData.setGuild(guild);
        sender.sendMessage(CC.translate("&aDolaczyles do gildii &f" + guild.getTag() + "&a!"));
        MessageUtils.broadcast("&aGracz &f" + player.getName() + " &adolaczyl do gildii &f" + guild.getTag() + "&a!");
    }
}
