package Air.FourCore.nation;

import Air.FourCore.mongoDB.MongoDB;
import Air.FourCore.plugins.WorldEditUtility;
import Air.FourCore.user.UserData;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import Air.FourCore.task.Timerable;
import Air.FourCore.task.nationRequest.ChangeKingCoolTime;
import Air.FourCore.task.nationRequest.ChangeViceroyCoolTime;
import Air.FourCore.task.nationRequest.WarCoolTime;
import Air.FourCore.task.nationRequest.WarNewbieCoolTime;
import Air.FourCore.WorldSetting;

import java.util.*;

public class NationData implements Comparable<NationData> {
    public static HashMap<String, NationData> map = new HashMap<>();
    public LinkedList<Timerable> timerList = new LinkedList<>();

    public String nationName;
    public int level = 1;
    public UUID king;
    public UUID viceroy;
    public List<UUID> generals = new ArrayList<>();
    public List<UUID> citizens = new ArrayList<>();
    public List<String> allies = new ArrayList<>();
    public double money = 0.0;
    public int score = 0;

    public List<BlockData> castles = new ArrayList<>();
    public List<BlockData> outposts = new ArrayList<>();

    public NationSetting setting = new NationSetting();


    public <U extends Timerable> U getTimerable(Class<U> clazz) {
        for (Timerable t : timerList) {
            if (clazz.isInstance(t)) {
                U result = (U) t;
                if (result != null) {
                    return result;
                }
            }
        }
        return null;
    }

    public NationData getEnemyNation() {
        if (isWar()) {
            if (WorldSetting.warSender == this) {
                return WorldSetting.warReceiver;
            } else if (WorldSetting.warReceiver == this) {
                return WorldSetting.warSender;
            }
        }
        return null;
    }

    public void playSound(Sound sound) {
        for (Player p : getOnlinePlayers()) {
            p.playSound(p.getLocation(), sound, 1, 1);
        }
    }

    public @NotNull List<UserData> getAllPlayers() {
        List<UserData> result = new ArrayList<>();
        if (king != null) result.add(UserData.getUserData(king));
        if (viceroy != null) result.add(UserData.getUserData(viceroy));
        for (UUID uuid : generals) {
            if (uuid != null)
                result.add(UserData.getUserData(uuid));
        }
        for (UUID uuid : citizens) {
            if (uuid != null)
                result.add(UserData.getUserData(uuid));
        }
        return result;
    }

    public @NotNull List<Player> getOnlinePlayers() {
        List<Player> result = new ArrayList<>();
        if (king != null && Bukkit.getPlayer(king) != null) result.add(Bukkit.getPlayer(king));
        if (viceroy != null && Bukkit.getPlayer(viceroy) != null) result.add(Bukkit.getPlayer(viceroy));
        for (UUID uuid : generals) {
            if (uuid != null && Bukkit.getPlayer(uuid) != null)
                result.add(Bukkit.getPlayer(uuid));
        }
        for (UUID uuid : citizens) {
            if (uuid != null && Bukkit.getPlayer(uuid) != null)
                result.add(Bukkit.getPlayer(uuid));
        }
        return result;
    }

    public @NotNull List<Player> getRealOnlinePlayers() {
        List<Player> result = new ArrayList<>();
        if (king != null && Bukkit.getPlayer(king) != null && !Bukkit.getPlayer(king).getWorld().getName().equalsIgnoreCase("잠수대")) result.add(Bukkit.getPlayer(king));
        if (viceroy != null && Bukkit.getPlayer(viceroy) != null && !Bukkit.getPlayer(viceroy).getWorld().getName().equalsIgnoreCase("잠수대")) result.add(Bukkit.getPlayer(viceroy));
        for (UUID uuid : generals) {
            if (uuid != null && Bukkit.getPlayer(uuid) != null && !Bukkit.getPlayer(uuid).getWorld().getName().equalsIgnoreCase("잠수대"))
                result.add(Bukkit.getPlayer(uuid));
        }
        for (UUID uuid : citizens) {
            if (uuid != null && Bukkit.getPlayer(uuid) != null && !Bukkit.getPlayer(uuid).getWorld().getName().equalsIgnoreCase("잠수대"))
                result.add(Bukkit.getPlayer(uuid));
        }
        return result;
    }

    public int getAllPlayerCount() {
        int result = 0;
        if (king != null) {
            result++;
        }
        if (viceroy != null) {
            result++;
        }
        for (UUID uuid : generals) {
            result++;
        }
        for (UUID uuid : citizens) {
            result++;
        }
        return result;
    }

    public int getOnlinePlayerCount() {
        int result = 0;
        Player k = Bukkit.getPlayer(king);
        if (k != null && k.isOnline() && !k.getWorld().getName().equalsIgnoreCase("잠수대")) result++;
        Player v = Bukkit.getPlayer(viceroy);
        if (v != null && v.isOnline() && !v.getWorld().getName().equalsIgnoreCase("잠수대")) result++;
        for (UUID uuid : generals) {
            Player c = Bukkit.getPlayer(uuid);
            if (c != null && c.isOnline() && !c.getWorld().getName().equalsIgnoreCase("잠수대")) result++;
        }
        for (UUID uuid : citizens) {
            Player c = Bukkit.getPlayer(uuid);
            if (c != null && c.isOnline() && !c.getWorld().getName().equalsIgnoreCase("잠수대")) result++;
        }
        return result;
    }

    public void teleport(Location location){
        for(Player p : getRealOnlinePlayers()){
            p.teleport(location);
        }
    }

    public boolean isWar() {
        return (WorldSetting.warSender == this || WorldSetting.warReceiver == this || WorldSetting.warSenderAllie == this || WorldSetting.warReceiverAllie == this);
    }
    // set user job
    /*
    public void addCitizen(UserData data){
        if(data.job != UserData.Job.일반유저)
            return;
        data.job = UserData.Job.시민;
        data.nation = nationName;
        citizens.add(data.uuid);
    }*/

    public NationData(String name, Player player) {
        nationName = name;
        king = player.getUniqueId();
    }

    public NationData(String name) {
        nationName = name;
    }

    public static NationData getNationData(String name) {
        if (name == null) return null;
        NationData result = map.get(name);
        if (result == null) {
            if (MongoDB.containsNation(name)) {
                NationData newNation = createNation(name);
                assert newNation != null;
                MongoDB.readNation(newNation);
            } else {
                return null;
            }
        }
        return result;
    }

    public void remove() {
        if (king != null) {
            UserData data = UserData.getUserData(king);
            data.setUserJob(UserData.Job.일반유저);
            //new RejoinNationCoolTime(86400, data);
        }
        if (viceroy != null) {
            UserData data = UserData.getUserData(viceroy);
            data.setUserJob(UserData.Job.일반유저);
            //new RejoinNationCoolTime(86400, data);
        }
        try {
            if (generals != null) {
                for (UUID uuid : generals) {
                    UserData data = UserData.getUserData(uuid);
                    data.setUserJob(UserData.Job.장군);
                    //new RejoinNationCoolTime(86400, data);
                }
            }
        } catch (Exception ignored) {
        }
        try {
            if (citizens != null) {
                for (UUID uuid : citizens) {
                    UserData data = UserData.getUserData(uuid);
                    data.setUserJob(UserData.Job.일반유저);
                    //new RejoinNationCoolTime(86400, data);
                }
            }
        } catch (Exception ignored) {
        }
        for (BlockData b : castles) {
            WorldEditUtility.setEmpty(b.getBlock().getLocation(), 36, 36);
        }
        for (BlockData b : outposts) {
            b.getBlock().setType(Material.AIR);
        }
        for (String s : allies) {
            NationData ally = NationData.getNationData(s);
            ally.allies.remove(nationName);
        }
        NationData.map.remove(nationName);
        MongoDB.deleteNation(this);
    }

    public static NationData createNation(String name) {
        if (map.containsKey(name)) {
            return null;
        } else {
            map.put(name, new NationData(name));
            return map.get(name);
        }
    }

    public static NationData createNation(String name, Player player) {
        for (String n : map.keySet()) {
            if (n.equalsIgnoreCase(name)) {
                return null;
            }
        }
        if (map.containsKey(name)) {
            return null;
        } else {
            map.put(name, new NationData(name, player));
            return map.get(name);
        }
    }

    @Override
    public int compareTo(@NotNull NationData o) {
        // TODO Auto-generated method stub
        return Integer.compare(this.score, o.score);
    }

    public void userBugFix() {
        if (king != null) {
            UserData data = UserData.getUserData(king);
            data.nation = nationName;
            data.job = UserData.Job.왕;
        }
        if (viceroy != null) {
            UserData data = UserData.getUserData(viceroy);
            data.nation = nationName;
            data.job = UserData.Job.부왕;
        }
        for (UUID uuid : generals) {
            if (uuid != null) {
                UserData data = UserData.getUserData(uuid);
                data.nation = nationName;
                data.job = UserData.Job.장군;
            }
        }
        for (UUID uuid : citizens) {
            if (uuid != null) {
                UserData data = UserData.getUserData(uuid);
                data.nation = nationName;
                data.job = UserData.Job.시민;
            }
        }
    }

    public void setData(Document doc) {
        List<UUID> citizensList = new ArrayList<>();
        for (String c : doc.getList("citizens", String.class)) {
            citizensList.add(UUID.fromString(c));
        }
        List<UUID> generalsList = new ArrayList<>();
        try {
            for (String c : doc.getList("generals", String.class)) {
                generalsList.add(UUID.fromString(c));
            }
        } catch (Exception ignored) {
        }
        //nation.nationName = data.getString("nationName");
        level = doc.getInteger("level");
        String k = doc.getString("king");
        king = k == null ? null : UUID.fromString(k);
        String v = doc.getString("viceroy");
        viceroy = v == null ? null : UUID.fromString(v);
        this.citizens = citizensList;
        this.generals = generalsList;
        allies = doc.getList("allies", String.class);
        money = doc.getDouble("money");
        try {
            score = doc.getInteger("score");
        } catch (Exception ignored) {
        }

        List<Document> castlesDoc = doc.getList("castles", Document.class);
        List<Document> outpostsDoc = doc.getList("outposts", Document.class);
        List<BlockData> castlesList = new ArrayList<>();
        List<BlockData> outpostsList = new ArrayList<>();
        if (castlesDoc != null) {
            for (Document d : castlesDoc) {
                String w = d.getString("world");
                int x = d.getInteger("x");
                int y = d.getInteger("y");
                int z = d.getInteger("z");
                castlesList.add(new BlockData(w, x, y, z));
            }
        }
        if (castlesDoc != null) {
            for (Document d : outpostsDoc) {
                String w = d.getString("world");
                int x = d.getInteger("x");
                int y = d.getInteger("y");
                int z = d.getInteger("z");
                outpostsList.add(new BlockData(w, x, y, z));
            }
        }
        this.castles = castlesList;
        this.outposts = outpostsList;

        Document timerList = (Document) doc.get("timerList");
        try {
            int t = timerList.getInteger("ChangeViceroyCoolTime");
            new ChangeViceroyCoolTime(t, this);
        } catch (Exception ignored) {
        }
        try {
            int t = timerList.getInteger("ChangeKingCoolTime");
            new ChangeKingCoolTime(t, this);
        } catch (Exception ignored) {
        }
        try {
            int t = timerList.getInteger("WarCoolTime");
            new WarCoolTime(t, this);
        } catch (Exception ignored) {
        }
        try {
            int t = timerList.getInteger("WarNewbieCoolTime");
            new WarNewbieCoolTime(t, this);
        } catch (Exception ignored) {
        }
    }
}
