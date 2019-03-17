package me.nekoh.guilds.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Syntax;
import me.nekoh.guilds.managers.PlayerManager;
import me.nekoh.guilds.player.PlayerData;
import me.nekoh.guilds.utils.CC;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class InviteCommand extends BaseCommand {

    @CommandAlias("ginvite|gzapros|zapros")
    @CommandPermission("guilds.invite")
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

        if (playerData.getGuild().getMaxMembers() <= playerData.getGuild().getMembers().size()) {
            sender.sendMessage(CC.translate("&cGildia jest juz pelna!"));
            return;
        }

        Player target = Bukkit.getPlayer(name);

        if (target == null) {
            sender.sendMessage(CC.translate("&cNie ma takiego gracza!"));
            return;
        }

        PlayerData targetData = PlayerManager.getPlayers().get(target.getUniqueId());

        if (targetData.getGuild() != null) {
            sender.sendMessage(CC.translate("&cTen gracz ma juz gildie!"));
            return;
        }

        playerData.getGuild().getMemberInvites().add(target.getUniqueId());
        player.sendMessage(CC.translate("&aZaprosiles gracza &f" + target.getName() + "&a do gildii."));
        target.sendMessage(CC.translate("&aZostales zaproszony do gildii &f" + playerData.getGuild().getTag() + "&a."));
        target.sendMessage(CC.translate("&aUzyj komendy &f/dolacz " + playerData.getGuild().getTag() + " &aby do niej dolaczyc."));
        sender.sendMessage(CC.translate("&aZaproszono gracza &f" + target.getName() + "&a do gildii."));
    }
}
