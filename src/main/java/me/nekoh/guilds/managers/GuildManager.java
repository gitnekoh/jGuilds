package me.nekoh.guilds.managers;

import lombok.Getter;
import me.nekoh.guilds.Guilds;
import me.nekoh.guilds.cuboid.Cuboid;
import me.nekoh.guilds.guild.Guild;
import me.nekoh.guilds.utils.GuildUtils;
import me.nekoh.guilds.utils.LocationUtils;
import me.nekoh.guilds.utils.UUIDUtils;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;

public class GuildManager {

    @Getter
    private static HashMap<String, Guild> guilds = new HashMap<>();
    @Getter
    private static List<Guild> sortedByRankGuilds = new ArrayList<>();
    @Getter
    private static HashMap<Material, Integer> neededItems = new HashMap<>();

    static {
        neededItems.put(Material.DIAMOND, 32);
        neededItems.put(Material.EMERALD, 5);
        neededItems.put(Material.IRON_INGOT, 63);
        neededItems.put(Material.GOLD_INGOT, 32);
    }

    @SuppressWarnings("unchecked")
    public void loadAll() {
        Bukkit.getScheduler().runTaskAsynchronously(Guilds.getInstance(), () -> {
            long now = System.currentTimeMillis();
            AtomicInteger x = new AtomicInteger(0);

            for (Document doc : Guilds.getInstance().getDataManager().getGuilds().find()) {
                long now1 = System.currentTimeMillis();

                Guild guild = new Guild(doc.getString("tag"), doc.getString("name"));
                guild.setCreator(UUID.fromString(doc.getString("creator")));
                guild.setLeader(UUID.fromString(doc.getString("leader")));
                guild.setCenter(LocationUtils.deserialize(doc.getString("center")));
                guild.setHome(LocationUtils.deserialize(doc.getString("home")));
                guild.setCreateTime(doc.getLong("createTime"));
                guild.setMaxMembers(doc.getInteger("maxMembers"));
                guild.setCuboidSize(doc.getInteger("cuboidSize"));
                guild.setCuboid(new Cuboid(new Location(guild.getCenter().getWorld(), guild.getCenter().getX() + guild.getCuboidSize(), 257, guild.getCenter().getZ() + guild.getCuboidSize()), new Location(guild.getCenter().getWorld(), guild.getCenter().getX() - guild.getCuboidSize(), -1, guild.getCenter().getZ() - guild.getCuboidSize())));
                guild.setRank(doc.getInteger("rank"));
                guild.setMemberInvites(new ArrayList<>());
                guild.setModerators(UUIDUtils.deserialize((List<String>) doc.get("moderators")));
                guild.setMembers(UUIDUtils.deserialize((List<String>) doc.get("members")));
                guild.setAlliesInvites(new ArrayList<>());
                guild.setAllies(GuildUtils.deserialize((List<String>) doc.get("allies")));

                Bukkit.getLogger().log(Level.INFO, "Loaded guild " + guild.getName() + " in " + (System.currentTimeMillis() - now1) + " ms.");
                x.incrementAndGet();
            }
            sortedByRankGuilds.addAll(guilds.values());
            Bukkit.getScheduler().runTask(Guilds.getInstance(), () -> Bukkit.getLogger().log(Level.INFO, "Loaded (" + x.get() + ") guilds in " + (System.currentTimeMillis() - now) + " ms."));
        });
    }

    public static Guild getFromTag(String name) {
        return guilds.get(name.toUpperCase());
    }

    public static Optional<Guild> getFromName(String name) {
        return guilds.values().stream().filter(guild -> guild.getName().equalsIgnoreCase(name)).findAny();
    }


}
