package me.nekoh.guilds.listeners;

import me.nekoh.guilds.Guilds;
import me.nekoh.guilds.guild.Guild;
import me.nekoh.guilds.managers.GuildManager;
import me.nekoh.guilds.managers.PlayerManager;
import me.nekoh.guilds.player.PlayerData;
import me.nekoh.guilds.utils.ActionBarUtil;
import me.nekoh.guilds.utils.CC;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.Optional;

public class CuboidListener implements Listener {

    public CuboidListener() {
        Bukkit.getPluginManager().registerEvents(this, Guilds.getInstance());
    }

    @EventHandler
    public void onCuboidJoin(PlayerMoveEvent event) {
        if (event.getFrom().getX() == event.getTo().getX() && event.getFrom().getY() == event.getTo().getY() && event.getFrom().getZ() == event.getTo().getZ()) {
            return;
        }

        Bukkit.getScheduler().runTaskAsynchronously(Guilds.getInstance(), () -> {
            PlayerData playerData = PlayerManager.getPlayers().get(event.getPlayer().getUniqueId());

            Optional<Guild> lastOpGuild = GuildManager.getGuilds().values().stream().parallel().filter(guild -> guild.getCuboid().isIn(event.getFrom())).findFirst();
            Optional<Guild> opGuild = GuildManager.getGuilds().values().stream().parallel().filter(guild -> guild.getCuboid().isIn(event.getTo())).findFirst();

            if (lastOpGuild.isPresent() && opGuild.isPresent()) {
                if(lastOpGuild.get() != playerData.getGuild() && opGuild.get() != playerData.getGuild()) {
                    ActionBarUtil.sendActionBarMessage(event.getPlayer(), CC.translate("&cJestes na teren wrogiej gildii [" + opGuild.get().getTag() + "]"));
                } else {
                    ActionBarUtil.sendActionBarMessage(event.getPlayer(), CC.translate("&aJestes na teren swojej gildii [" + opGuild.get().getTag() + "]"));
                }
            } else if (!lastOpGuild.isPresent() && opGuild.isPresent()) {
                if (opGuild.get() != playerData.getGuild()) {
                    event.getPlayer().sendMessage(CC.translate("&cWszedles na teren wrogiej gildii [" + opGuild.get().getTag() + "]"));
                }
            }
        });

    }

    @EventHandler
    public void onCuboidBlockBreak(BlockBreakEvent event) {
        PlayerData playerData = PlayerManager.getPlayers().get(event.getPlayer().getUniqueId());
        Optional<Guild> opGuild = GuildManager.getGuilds().values().stream().parallel().filter(guild -> guild.getCuboid().isIn(event.getBlock().getLocation())).findFirst();
        if (opGuild.isPresent()) {
            if (opGuild.get() != playerData.getGuild()) {
                event.getPlayer().sendMessage(CC.translate("&cNie mozesz niszczyc blokow na terenie wrogiej gildii!"));
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onCuboidBlockPlace(BlockPlaceEvent event) {
        PlayerData playerData = PlayerManager.getPlayers().get(event.getPlayer().getUniqueId());
        Optional<Guild> opGuild = GuildManager.getGuilds().values().stream().parallel().filter(guild -> guild.getCuboid().isIn(event.getBlock().getLocation())).findFirst();
        if (opGuild.isPresent()) {
            if (opGuild.get() != playerData.getGuild()) {
                event.getPlayer().sendMessage(CC.translate("&cNie mozesz stawiac blokow na terenie wrogiej gildii!"));
                event.setCancelled(true);
            }
        }
    }
}
