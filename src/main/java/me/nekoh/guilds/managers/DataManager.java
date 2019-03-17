package me.nekoh.guilds.managers;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import lombok.Getter;
import org.bson.Document;
import org.bukkit.Bukkit;

import java.util.logging.*;

@Getter
public class DataManager {

    private MongoClient mongoClient;
    private MongoDatabase mongoDatabase;
    private MongoCollection<Document> players;
    private MongoCollection<Document> guilds;
    private MongoCollection<Document> neededItems;
    private boolean successful;

    public void connect() {
        try {
            long now = System.currentTimeMillis();                  //UR
            this.mongoClient = new MongoClient(new MongoClientURI(""/*ur mongo link*/));
            this.mongoDatabase = mongoClient.getDatabase("guild");
            this.players = mongoDatabase.getCollection("players");
            this.guilds = mongoDatabase.getCollection("guilds");
            Bukkit.getLogger().log(Level.WARNING, "Mongo has been loaded in " + (System.currentTimeMillis() - now) + " ms.");
            this.successful = true;
        } catch (MongoException e) {
            Bukkit.getLogger().log(Level.WARNING, "Mongo has failed");
            this.successful = false;
        }
    }
}
