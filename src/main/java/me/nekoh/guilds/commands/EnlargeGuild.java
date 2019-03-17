package me.nekoh.guilds.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import me.nekoh.guilds.guild.Guild;
import me.nekoh.guilds.managers.PlayerManager;
import me.nekoh.guilds.player.PlayerData;
import me.nekoh.guilds.utils.CC;
import me.nekoh.guilds.utils.InventoryUtils;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EnlargeGuild extends BaseCommand {

    @CommandAlias("genlarge|gpowieksz|powieksz")
    @CommandPermission("guilds.guild")
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

        if (!playerData.getGuild().getLeader().equals(player.getUniqueId()) && !playerData.getGuild().getModerators().contains(player.getUniqueId())) {
            sender.sendMessage(CC.translate("&cNie jestes liderem ani moderatorem!"));
            return;
        }

        Guild guild = playerData.getGuild();

        int emeraldAmount = 16 + (guild.getCuboidSize() * 6);

        if (!player.getInventory().contains(Material.EMERALD, emeraldAmount)) {
            sender.sendMessage(CC.translate("&cNie masz wystarczajacej liczby emeraldow (" + emeraldAmount + ")!"));
            return;
        }

        InventoryUtils.removeItems(player.getInventory(), Material.EMERALD, emeraldAmount);

        guild.setCuboidSize(guild.getCuboidSize() + 2);
        guild.updateCuboidSize();
        player.sendMessage(CC.translate("&aPowiekszono teren gildii do &f" + guild.getCuboidSize() + "&a."));
        guild.save();
    }
}
