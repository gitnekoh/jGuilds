package me.nekoh.guilds.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Syntax;
import me.nekoh.guilds.guild.Guild;
import me.nekoh.guilds.managers.GuildManager;
import me.nekoh.guilds.utils.CC;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class InfoCommand extends BaseCommand {

    @CommandAlias("ginfo|info")
    @CommandPermission("guilds.guild")
    @Syntax("<tag>")
    public void execute(CommandSender sender, String tag) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(CC.translate("&cTa komenda moze byc uzyta tylko w grze!"));
            return;
        }
        Guild guild = GuildManager.getFromTag(tag);
        if (guild == null) {
            if (GuildManager.getFromName(tag).isPresent()) {
                guild = GuildManager.getFromName(tag).get();
            } else {
                sender.sendMessage(CC.translate("&cNie ma takiej gildii."));
                return;
            }
        }
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh/mm");
        sender.sendMessage(CC.translate(
                "&m" + CC.shortLine() + "&a" + guild.getTag() + "&m" + CC.shortLine() +
                        "&aPelna nazwa: &f" + guild.getName() + "\n" +
                        "&aData zalozenia: &f" + formatter.format(new Date(guild.getCreateTime())) + "\n" +
                        "&aPozycja w rankingu: &f" + GuildManager.getSortedByRankGuilds().indexOf(guild) + "\n" +
                        "&aZalozyciel: &f" + Bukkit.getOfflinePlayer(guild.getCreator()).getName() + "\n" +
                        "&aLider: &f" + Bukkit.getOfflinePlayer(guild.getLeader()).getName() + "\n" +
                        "&aZastepcy: &f" + CC.formattedModeratorList(guild) + "\n" +
                        "&aCzlonkowie: &f" + CC.formattedMemberList(guild) + "\n" +
                        "&m" + CC.shortLine()
        ));
    }
}
