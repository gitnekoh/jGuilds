package me.nekoh.guilds.tasks;

import me.nekoh.guilds.managers.GuildManager;
import org.bukkit.scheduler.BukkitRunnable;

public class SortTask extends BukkitRunnable {
    @Override
    public void run() {
        GuildManager.getSortedByRankGuilds().sort((o1, o2) -> -(o1.getRank() - o2.getRank()));
    }
}
