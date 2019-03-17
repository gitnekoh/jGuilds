package me.nekoh.guilds.guild;

import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;
import lombok.Getter;
import lombok.Setter;
import me.nekoh.guilds.Guilds;
import me.nekoh.guilds.cuboid.Cuboid;
import me.nekoh.guilds.managers.GuildManager;
import me.nekoh.guilds.utils.GuildUtils;
import me.nekoh.guilds.utils.LocationUtils;
import me.nekoh.guilds.utils.UUIDUtils;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.*;
import java.util.logging.Level;

@Getter
@Setter
public class Guild {

    private String name;
    private String tag;
    private UUID leader;
    private Location center;
    private Location home;
    private int maxMembers;
    private int cuboidSize;
    private int rank;
    private Cuboid cuboid;
    private List<UUID> memberInvites;
    private List<UUID> moderators;
    private List<UUID> members;
    private List<Guild> alliesInvites;
    private List<Guild> allies;

    public Guild(String tag, String name) {
        this.tag = tag;
        this.name = name;
        GuildManager.getGuilds().put(tag.toUpperCase(), this);
    }

    public Guild(String name, String tag, UUID leader, Location center, int maxMembers, int cuboidSize) {
        this.name = name;
        this.tag = tag;
        this.leader = leader;
        this.center = center;
        this.home = center;
        this.maxMembers = maxMembers;
        this.cuboidSize = cuboidSize;
        this.cuboid = new Cuboid(new Location(center.getWorld(), center.getX() + cuboidSize, 256, center.getZ() + cuboidSize), new Location(center.getWorld(), center.getX() - cuboidSize, 0, center.getZ() - cuboidSize));
        this.memberInvites = new ArrayList<>();
        this.moderators = new ArrayList<>();
        this.members = Collections.singletonList(leader);
        this.alliesInvites = new ArrayList<>();
        this.allies = new ArrayList<>();
        GuildManager.getGuilds().put(tag.toUpperCase(), this);
    }

    public void save() {
        Bukkit.getScheduler().runTaskAsynchronously(Guilds.getInstance(), () -> {
            long now = System.currentTimeMillis();

            Document doc = new Document("name", this.name);

            doc.append("tag", this.tag)
                    .append("leader", this.leader.toString())
                    .append("center", LocationUtils.serialize(this.center))
                    .append("home", LocationUtils.serialize(this.home))
                    .append("maxMembers", this.maxMembers)
                    .append("cuboidSize", this.cuboidSize)
                    .append("rank", this.rank)
                    .append("moderators", UUIDUtils.serialize(this.moderators))
                    .append("members", UUIDUtils.serialize(this.members))
                    .append("allies", GuildUtils.serialize(this.allies));

            Guilds.getInstance().getDataManager().getGuilds().replaceOne(Filters.eq("name", this.name), doc, new ReplaceOptions().upsert(true));

            Bukkit.getScheduler().runTask(Guilds.getInstance(), () -> Bukkit.getLogger().log(Level.INFO, "Saved guild " + this.name + " data in " + (System.currentTimeMillis() - now) + " ms."));
        });
    }
}
