package me.nekoh.guilds.tasks;

import me.nekoh.guilds.managers.PlayerManager;
import me.nekoh.guilds.player.PlayerData;
import me.nekoh.guilds.utils.CC;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class NameTagTask extends BukkitRunnable {
    @SuppressWarnings("deprecation")
    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {

            Scoreboard sb = player.getScoreboard();

            if (sb == null) {
                sb = Bukkit.getScoreboardManager().getNewScoreboard();
                player.setScoreboard(sb);
            }

            Objective obj = sb.getObjective("names");

            if (obj == null) {
                sb.registerNewObjective("names", "dummy");
            }

            PlayerData playerData = PlayerManager.getPlayers().get(player.getUniqueId());

            for (final Player target : Bukkit.getOnlinePlayers()) {
                if (player == target) {
                    continue;
                }

                PlayerData targetData = PlayerManager.getPlayers().get(target.getUniqueId());

                String prefix = "";

                if (playerData.getGuild() != null) {
                    if (targetData.getGuild() != null) {
                        if (!playerData.getGuild().getAllies().contains(targetData.getGuild())) {
                            if (targetData.getGuild() != playerData.getGuild()) {
                                prefix = CC.translate("&c[" + targetData.getGuild().getTag() + "] ");
                            } else {
                                prefix = CC.translate("&a[" + targetData.getGuild().getTag() + "] ");
                            }
                        } else {
                            prefix = CC.translate("&9[" + targetData.getGuild().getTag() + "] ");
                        }
                    }
                } else if (targetData.getGuild() != null) {
                    prefix = CC.translate("&c[" + targetData.getGuild().getTag() + "] ");
                }

                Team t = sb.getTeam(target.getName());
                if (t == null) {
                    t = sb.registerNewTeam(target.getName());
                }

                t.setPrefix(prefix);
                t.addPlayer(target);
            }
        }
    }
}
