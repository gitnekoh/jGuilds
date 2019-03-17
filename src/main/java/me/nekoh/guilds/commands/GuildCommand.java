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
import me.nekoh.guilds.utils.InventoryUtils;
import me.nekoh.guilds.utils.MessageUtils;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GuildCommand extends BaseCommand {

    @CommandAlias("guild|gildia|g")
    @CommandPermission("guilds.guild")
    public void execute(CommandSender sender) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(CC.translate("&cTa komenda moze byc uzyta tylko w grze!"));
            return;
        }

        sender.sendMessage(CC.translate(
                "&m" + CC.shortLine() +
                "&f/gildia &a- Wszystkie komendy dotyczace gildii.\n" +
                "&f/gzaloz <tag> <name> &a- Zakladanie nowej gildii.\n" +
                "&f/gdom &a- Teleportowanie do domu gildii.\n" +
                "&f/ginfo <tag> &a- Informacje na temat danej gildii.\n" +
                "&f/ginvite <gracz> &a- Zapraszanie gracza do gildii.\n" +
                "&f/gjoin <tag> &a- Dolaczanie do gildii." +
                "&f/gwyrzuc <gracz> &a- Wyrzucanie gracza z gildii." +
                "&f/gopusc &a- Opuszczanie gildii." +
                "&m" + CC.shortLine())
        );

    }

}
