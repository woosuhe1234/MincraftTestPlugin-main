package Air.FourCore.mongoDB;

import Air.FourCore.user.UserData;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.UpdateOptions;
import Air.FourCore.nation.BlockData;
import Air.FourCore.nation.NationData;
import org.bson.Document;
import Air.FourCore.task.Timerable;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.mongodb.client.model.Filters.eq;

public class MongoDB {

    static MongoClient mongoClient;
    static MongoDatabase database;
    public static MongoCollection<Document> user;
    public static MongoCollection<Document> nation;

    public static void initDB() {

        //                    -- when security password --
        String password = null;

        File file = new File("D:/Keys/MongoDB.txt");
        if(!file.exists()){
            file = new File("C:/Users/Administrator/Documents/MongoDB.txt");
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            password = br.readLine().trim();
        }
        catch (IOException ignored){ System.out.println("IOException: "+ignored); }

        // temporary open password
        ConnectionString connectionString = new ConnectionString("mongodb+srv://airman:"+password+"@cluster0.ddtho.mongodb.net/myFirstDatabase?retryWrites=true&w=majority");
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();

        mongoClient = MongoClients.create(settings);
        database = mongoClient.getDatabase("minecraft");
        user = database.getCollection("Air/FourCore/user");
        nation = database.getCollection("Air/FourCore/nation");
    }

    public static void insertNation(NationData nation) {
        Document doc = documentNation(nation);

        MongoDB.nation.insertOne(doc);
    }

    public static void updateNation(NationData nation) {
        Document doc = documentNation(nation);
        try {

            UpdateOptions options = new UpdateOptions().upsert(true);
            MongoDB.nation.updateOne(eq("nationName", nation.nationName), new Document("$set", doc), options);
        } catch (NoClassDefFoundError e) {
        }
    }

    public static Document documentNation(NationData nation){
        ArrayList<String> citizens = new ArrayList<>();
        for (UUID c : nation.citizens) {
            citizens.add(c.toString());
        }
        ArrayList<String> generals = new ArrayList<>();
        for (UUID c : nation.generals) {
            generals.add(c.toString());
        }
        List<Document> castles = new ArrayList<>();
        List<Document> outposts = new ArrayList<>();
        for (BlockData b : nation.castles) {
            castles.add(new Document("world", b.world)
                    .append("x", b.x)
                    .append("y", b.y)
                    .append("z", b.z)
            );
        }
        for (BlockData b : nation.outposts) {
            outposts.add(new Document("world", b.world)
                    .append("x", b.x)
                    .append("y", b.y)
                    .append("z", b.z)
            );
        }

        Document timerList = new Document();
        for (Timerable t : nation.timerList) {
            timerList.append(t.getClass().getSimpleName(), t.timer);
        }

        Document doc = new Document("nationName", nation.nationName)
                .append("level", nation.level)
                .append("king", (nation.king == null ? null : nation.king.toString()))
                .append("viceroy", (nation.viceroy == null ? null : nation.viceroy.toString()))
                .append("generals", generals)
                .append("citizens", citizens)
                .append("allies", nation.allies)
                .append("money", nation.money)
                .append("castles", castles)
                .append("outposts", outposts)
                .append("score", nation.score)
                .append("timerList", timerList);

        return doc;
    }

    public static void deleteNation(NationData nation) {

        MongoDB.nation.deleteOne(eq("nationName", nation.nationName));
    }

    public static void readNation(NationData nation) {
        Document data = MongoDB.nation.find(eq("nationName", nation.nationName)).first();

        if (data != null) {
            nation.setData(data);
        } else {
            insertNation(nation);
        }
    }

    public static boolean containsNation(String name) {
        Document data = MongoDB.nation.find(eq("nationName", name)).first();
        return data != null;
    }

    public static void insertUser(UserData user) {
        Document doc = documentUser(user);

        MongoDB.user.insertOne(doc);
    }

    public static void updateUser(UserData user) {
        try {
            Document doc = documentUser(user);

            UpdateOptions options = new UpdateOptions().upsert(true);
            MongoDB.user.updateOne(eq("uuid", user.uuid.toString()), new Document("$set", doc), options);
        } catch (NoClassDefFoundError e) {
            e.printStackTrace();
        }
    }

    public static Document documentUser(UserData user){
        Document home = null;
        if (user.home != null) {
            home = new Document()
                    .append("world", user.home.world)
                    .append("x", user.home.x)
                    .append("y", user.home.y)
                    .append("z", user.home.z);
        }
        Document home2 = null;
        if (user.home2 != null) {
            home2 = new Document()
                    .append("world", user.home2.world)
                    .append("x", user.home2.x)
                    .append("y", user.home2.y)
                    .append("z", user.home2.z);
        }

        Document timerList = new Document();
        for (Timerable t : user.timerList) {
            timerList.append(t.getClass().getSimpleName(), t.timer);
        }

        Document doc = new Document()
                .append("userName", user.userName)
                .append("Air/FourCore/nation", user.nation)
                .append("job", user.job.name())
                //.append("money", FourCore.economy.getBalance(Bukkit.getOfflinePlayer(user.uuid)))
                .append("cash", user.cash)
                .append("point", user.point)
                .append("level", user.level)
                .append("exp", user.exp)
                .append("state", user.state)
                .append("strength", user.strength)
                .append("force", user.force)
                .append("mental", user.mental)
                .append("defense", user.defense)
                .append("home", home)
                .append("home2", home2)
                //.append("bag", SerializeUtility.itemStackArrayToBase64(BagData.getInventory(user.uuid).getContents()))
                .append("setting", user.setting.getDocument())
                .append("role", user.role.name())
                .append("warning", user.warning)
                .append("timerList", timerList);

        return doc;
    }

    public static void readUser(UserData user) {
        Document data = MongoDB.user.find(eq("uuid", user.uuid.toString())).first();

        if (data != null) {
            user.setData(data);


            //try {
            //BagData.getInventory(user.uuid).setContents(SerializeUtility.itemStackArrayFromBase64(data.getString("bag")));

            //} catch (IOException e) {
            //}


        } else {
            insertUser(user);
        }
    }
}