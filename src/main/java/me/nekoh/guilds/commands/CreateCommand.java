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

public class CreateCommand extends BaseCommand {

    @CommandAlias("gcreate|gzaloz|zaloz")
    @CommandPermission("guilds.create")
    @Syntax("<tag> <name>")
    public void execute(CommandSender sender, String tag, String name) {
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

        if (tag.length() > 5 || tag.length() < 2) {
            sender.sendMessage(CC.translate("&cTAG powinien miec od 2 do 5 znakow!"));
            return;
        }

        if (name.length() > 16 || tag.length() < 4) {
            sender.sendMessage(CC.translate("&cNazwa powinna miec od 4 do 16 znakow!"));
            return;
        }

        if (GuildManager.getFromTag(tag) != null) {
            sender.sendMessage(CC.translate("&cGildia z takim tagiem juz istnieje!"));
            return;
        }

        if (GuildManager.getFromName(name).isPresent()) {
            sender.sendMessage(CC.translate("&cGildia z taka nazwa juz istnieje!"));
            return;
        }

        for (Material material : GuildManager.getNeededItems().keySet()) {
            if (!player.getInventory().contains(material, GuildManager.getNeededItems().get(material))) {
                sender.sendMessage(CC.translate("&cNie masz wymaganych przedmiotow! (" + GuildManager.getNeededItems().keySet().toString() + ")"));
                return;
            }
        }

        for (Material material : GuildManager.getNeededItems().keySet()) {
            InventoryUtils.removeItems(player.getInventory(), material, GuildManager.getNeededItems().get(material));
        }

        player.updateInventory();

        Guild guild = new Guild(name, tag.toUpperCase(), ((Player) sender).getUniqueId(), ((Player) sender).getLocation(), 20, 20);
        playerData.setGuild(guild);
        guild.save();

        sender.sendMessage(CC.translate("&aPomyslnie utworzono gildie &f" + name + " &a(&f" + tag.toUpperCase() + "&a)."));
        MessageUtils.broadcast("&aGildia &f" + name + " &a(&f" + tag.toUpperCase() + "&a) zostala zalozona przez &f" + player.getName() + "&a!");
    }
}
