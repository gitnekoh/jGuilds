package me.nekoh.guilds.player;

import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;
import lombok.Getter;
import lombok.Setter;
import me.nekoh.guilds.Guilds;
import me.nekoh.guilds.guild.Guild;
import me.nekoh.guilds.managers.GuildManager;
import me.nekoh.guilds.managers.PlayerManager;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

@Getter
@Setter
public class PlayerData {

    private UUID uuid;
    private String name;
    private Player player;
    private Guild guild;
    private int rank;
    private int teleportCooldown;
    private boolean wasTeleporting;
    private boolean teleportCancelled;
    private int attackCooldown;
    private boolean loaded = false;

    public PlayerData(UUID uuid) {
        this.uuid = uuid;
        PlayerManager.getPlayers().put(uuid, this);
    }


    public void load() {
        Document found = Guilds.getInstance().getDataManager().getPlayers().find(Filters.eq("uuid", this.uuid.toString())).first();

        if (found != null) {
            if (found.getString("guild") != null) {
                if (GuildManager.getFromTag(found.getString("guild")) != null) {
                    this.guild = GuildManager.getFromTag(found.getString("guild"));
                }
            }
            if (found.getInteger("rank") != null) {
                this.rank = found.getInteger("rank");
            }

            this.name = Bukkit.getOfflinePlayer(this.uuid).getName();
            this.player = Bukkit.getOfflinePlayer(this.uuid).getPlayer();

        } else {
            this.name = Bukkit.getOfflinePlayer(this.uuid).getName();
            this.player = Bukkit.getOfflinePlayer(this.uuid).getPlayer();
            this.rank = 1000;
            Document doc = new Document("uuid", this.uuid.toString());
            doc.append("name", this.name).append("rank", this.rank);
            Guilds.getInstance().getDataManager().getPlayers().insertOne(doc);
        }
    }

    public void save() {
        Document doc = new Document("uuid", this.uuid.toString());

        doc.append("name", this.name).append("rank", this.rank);

        if (this.guild != null) {
            doc.append("guild", this.guild.getTag());
        }

        Guilds.getInstance().getDataManager().getPlayers().replaceOne(Filters.eq("uuid", this.uuid.toString()), doc, new ReplaceOptions().upsert(true));
    }
}
