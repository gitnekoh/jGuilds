package me.nekoh.guilds.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import me.nekoh.guilds.utils.CC;
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
                "&7" + CC.shortLine() + "&f[GILDIE]&7" + CC.shortLine() + "\n" +
                        "&f/gildia &a- Wszystkie komendy dotyczace gildii.\n" +
                        "&f/gzaloz <tag> <name> &a- Zakladanie nowej gildii.\n" +
                        "&f/gpowieksz &a- Powieksza teren gildii.\n" +
                        "&f/gdom &a- Teleportowanie do domu gildii.\n" +
                        "&f/ginfo <tag> &a- Informacje na temat danej gildii.\n" +
                        "&f/ginvite <gracz> &a- Zapraszanie gracza do gildii.\n" +
                        "&f/gjoin <tag> &a- Dolaczanie do gildii.\n" +
                        "&f/gwyrzuc <gracz> &a- Wyrzucanie gracza z gildii.\n" +
                        "&f/gopusc &a- Opuszczanie gildii.\n" +
                        "&7" + CC.shortLine() + "&f[GILDIE]&7" + CC.shortLine())
        );

    }

}
