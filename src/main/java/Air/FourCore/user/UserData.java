package Air.FourCore.user;

import Air.FourCore.files.UserConfig;
import Air.FourCore.item.CustomItemUtility;
import Air.FourCore.npc.quest.Quest;
import Air.FourCore.npc.quest.QuestUtility;
import Air.FourCore.mongoDB.MongoDB;
import Air.FourCore.nation.BlockData;
import Air.FourCore.nation.NationData;
import org.bson.Document;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import Air.FourCore.task.Timerable;
import Air.FourCore.FourCore;
import Air.FourCore.WorldSetting;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.UUID;

public class UserData implements Comparable<UserData> {
    public static HashMap<UUID, UserData> map = new HashMap<>();
    public LinkedList<Timerable> timerList = new LinkedList<>();
    public Quest[] questList = new Quest[3]; // [ Main , Normal , Event ]
    public UUID uuid = null;
    public String userName = null;
    public String nation = null;
    public Job job = Job.일반유저;

    public double cash = 0.0;
    public double point = 0.0;

    public int level = 1;
    public double exp = 0.0;
    public int state = 3;
    public int strength = 0;
    public int force = 0;
    public int mental = 0;
    public int defense = 0;
    public BlockData home = null;
    public BlockData home2 = null;

    public UserSetting.Role role = UserSetting.Role.일반유저;
    public int warning = 0;

    public UserRune rune = new UserRune(this);
    public UserSetting setting = new UserSetting();
    public UserRecord record = new UserRecord();
    public UserStatus status = new UserStatus();
    public UserCoin coin = new UserCoin();

    @Override
    public int compareTo(@NotNull UserData data) {
        // TODO Auto-generated method stub
        if (this.level < data.level) {
            return -1;
        } else if (this.level == data.level) {
            return 0;
        } else {
            return 1;
        }
    }

    public enum Job {
        왕, 부왕, 장군, 시민, 일반유저
    }

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

    public void saveData(){
        UserConfig.get().set(uuid.toString() + ".DungeonQuest", record.dungeonQuestClear);
        UserConfig.get().set(uuid.toString() + ".states", rune.states_OLD);
        UserConfig.get().set(uuid.toString() + ".values", rune.values);
        UserConfig.get().set(uuid.toString() + ".strk.Coin", coin.strkCoin);
        UserConfig.get().set(uuid.toString() + ".strk.Amount", coin.strkAmount);
        UserConfig.get().set(uuid.toString() + ".strk.AveragePrice", coin.strkAveragePrice);
        UserConfig.get().set(uuid.toString() + ".CountGambling", setting.countGambling);
        int i = 1;
        for(Quest quest : questList){
            if (quest != null){
                UserConfig.get().set(uuid.toString() + ".Quest.name"+i, quest.getClass().getSimpleName());
                UserConfig.get().set(uuid.toString() + ".Quest.amount"+i, quest.current);
            }else{
                UserConfig.get().set(uuid.toString() + ".Quest.name"+i, "");
                UserConfig.get().set(uuid.toString() + ".Quest.amount"+i, 0);
            }
            i++;
        }
        // 이 함수를 쓰고 UserConfig.save(); 반드시 필요!
        // 한번에 처리하려고 여기선 빼둔거임
    }

    public void loadData(){
        ConfigurationSection config = UserConfig.get().getConfigurationSection(uuid.toString());
        try {
            record.dungeonQuestClear = config.getBooleanList("DungeonQuest");
            rune.states_OLD = config.getIntegerList("states");
            rune.values = config.getIntegerList("values");
            setting.countGambling = config.getInt("CountGambling");
            ConfigurationSection strk = config.getConfigurationSection("strk");
            coin.strkCoin = strk.getDouble("Coin");
            coin.strkAmount = strk.getDouble("Amount");
            coin.strkAveragePrice = strk.getDouble("AveragePrice");
            for(int i = 1; i <= 3; i ++){
                String name = config.getString("Quest.name"+i);
                int amount = config.getInt("Quest.amount"+i);
                QuestUtility.strToClass(this, name, amount);
            }
        }catch (Exception ignored){}

        if(rune.states_OLD.size() == 0){
            rune.states_OLD.add(0);
            rune.states_OLD.add(0);
            rune.states_OLD.add(0);
            rune.values.add(0);
            rune.values.add(0);
            rune.values.add(0);
        }

        if(record.dungeonQuestClear.size() == 0){
            record.dungeonQuestClear.add(false);
            record.dungeonQuestClear.add(false);
            record.dungeonQuestClear.add(false);
            record.dungeonQuestClear.add(false);
            record.dungeonQuestClear.add(false);
            record.dungeonQuestClear.add(false);
            record.dungeonQuestClear.add(false);
            record.dungeonQuestClear.add(false);
        }
    }

    public void setData(Document doc) {
        this.nation = doc.getString("Air/FourCore/nation");
        try {
            this.job = Job.valueOf(doc.getString("job"));
        } catch (Exception e) {
            this.job = Job.일반유저;
        }
        /*
        OfflinePlayer p = Bukkit.getOfflinePlayer(uuid);
        double dbMoney = 0;
        try {
            dbMoney = doc.getDouble("money");
        } catch (Exception e) {
        }
        double myMoney = FourCore.economy.getBalance(p);
        if (dbMoney >= myMoney) {
            FourCore.economy.depositPlayer(p, dbMoney - myMoney);
        } else {
            FourCore.economy.withdrawPlayer(p, myMoney - dbMoney);
        }
        */
        try {
            this.cash = doc.getDouble("cash");
        } catch (Exception e) {
            this.cash = 0;
        }
        try {
            this.point = doc.getDouble("point");
        } catch (Exception e) {
            this.point = 0;
        }
        try {
            this.level = doc.getInteger("level");
            this.exp = doc.getDouble("exp");
            this.state = doc.getInteger("state");
            this.strength = doc.getInteger("strength");
            this.force = doc.getInteger("force");
            this.mental = doc.getInteger("mental");
            this.defense = doc.getInteger("defense");
        } catch (Exception e) {
        }
        Document home = (Document) doc.get("home");
        try {
            this.home = new BlockData(home.getString("world"), home.getInteger("x"), home.getInteger("y"), home.getInteger("z"));
        } catch (Exception e) {
            this.home = null;
        }
        Document home2 = (Document) doc.get("home2");
        try {
            this.home2 = new BlockData(home2.getString("world"), home2.getInteger("x"), home2.getInteger("y"), home2.getInteger("z"));
        } catch (Exception e) {
            this.home2 = null;
        }
        setting.setData((Document) doc.get("setting"));
        try {
            this.role = UserSetting.Role.valueOf(doc.getString("role"));
        } catch (Exception e) {
        }
        try {
            this.warning = doc.getInteger("warning");
        } catch (Exception e) {
        }
    }

    private UserData(Player p) {
        uuid = p.getUniqueId();
        userName = p.getName();
        //FourCore.economy.has(p, 12);
    }

    private UserData(OfflinePlayer p) {
        uuid = p.getUniqueId();
        userName = p.getName();
        //FourCore.economy.has(p, 12);
    }

    public UserData(UUID uuid) {
        this.uuid = uuid;
    }

    public double getDamage() {
        return strength * 0.5 + force * 0.4 + defense * 0.1 + rune.getState(UserRune.State.공격력);
    }

    public double getHealth() {
        return strength * 0.2 + force * 0.1 + mental * 0.75 + defense * 0.4 + rune.getState(UserRune.State.체력);
    }

    public double getSpeed() {
        return ((force * 0.2) + rune.getState(UserRune.State.이동속도)) * 0.01; // (force계수) * 100% * 기본속도
    }

    public double getDefence() {
        return Bukkit.getPlayer(uuid).getAttribute(Attribute.GENERIC_ARMOR).getValue() + rune.getState(UserRune.State.방어력);
    }

    /*
    public double getVampire() {
        return rune.getState(UserRune.State.흡혈);
    }*/

    public void addExp(double e) {
        exp += e;

        double goal = getEXPGoal();
        while (exp >= goal) {
            if (level < 100) {
                exp -= goal;
                levelUp();
            } else {
                break;
            }
        }
    }

    public double getEXPGoal() {
        if (level < 1 || level > 100)
            return 0;
        return FourCore.instance.getConfig().getDoubleList("LevelExpList").get(level - 1);
    }

    private void levelUp() {
        level += 1;
        state += 3;

        Player p = Bukkit.getPlayer(uuid);
        p.setHealth(p.getMaxHealth());
        p.getWorld().playEffect(p.getLocation(), Effect.FIREWORK_SHOOT, null);
        p.sendTitle(ChatColor.GOLD + "" + ChatColor.BOLD + "레벨 " + level, ChatColor.RESET + "레벨이 올랐습니다!", 5, 60, 30);
        p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
        if (level == 70) {
            p.sendMessage(ChatColor.GOLD + "※ 70레벨 보상 - 네더의별 80개!!");
            p.getInventory().addItem(CustomItemUtility.NetherStartItem(80));
        }
    }

    public void updateStatus() {
        Player p = Bukkit.getPlayer(uuid);
        p.setMaxHealth(20 + getHealth());
        float speed = 0.2f;
        if(!setting.ignoreSpeed)
            speed += (float) getSpeed();
        p.setWalkSpeed(speed);
        p.setFlySpeed(0.2f);
    }

    public void setUserJob(Job job) {
        NationData targetNation = NationData.getNationData(nation);
        setUserJob(job, targetNation);
    }

    public void setUserJob(Job job, NationData targetNation) {
        if (targetNation != null) {
            beforeSetUserJob(targetNation);
            this.job = job;
            afterSetUserJob(targetNation);
        } else {
            this.job = job;
            this.nation = null;
        }
    }

    public NationData getNation(){
        if(nation == null){
            return null;
        }else{
            return NationData.getNationData(nation);
        }
    }

    public boolean isBusy(){
        if((getNation() != null && getNation().isWar()) || isBattle()){
            return true;
        }else{
            return false;
        }
    }

    public boolean isBattle(){
        for(Player p : WorldSetting.battlePlayer){
            if(p == null) continue;
            if(uuid == p.getUniqueId()){
                return true;
            }
        }
        return false;
    }

    public boolean isOnline() {
        if (userName == null || Bukkit.getPlayer(userName) == null) {
            return false;
        } else {
            return Bukkit.getPlayer(userName).isOnline();
        }
    }

    private void beforeSetUserJob(NationData targetNation) {
        switch (job) {
            case 왕:
                targetNation.king = null;
                break;
            case 부왕:
                targetNation.viceroy = null;
                break;
            case 장군:
                targetNation.generals.remove(uuid);
                break;
            case 시민:
                targetNation.citizens.remove(uuid);
                break;
            case 일반유저:
                break;
            default:
                break;
        }
    }

    private void afterSetUserJob(NationData targetNation) {
        switch (job) {
            case 왕:
                targetNation.king = uuid;
                nation = targetNation.nationName;
                break;
            case 부왕:
                targetNation.viceroy = uuid;
                nation = targetNation.nationName;
                break;
            case 장군:
                targetNation.generals.add(uuid);
                nation = targetNation.nationName;
                break;
            case 시민:
                targetNation.citizens.add(uuid);
                nation = targetNation.nationName;
                break;
            case 일반유저:
                nation = null;
                break;
            default:
                break;
        }
    }

    public static @NotNull UserData getUserData(UUID uuid) {
        if (map.containsKey(uuid)) {
            return map.get(uuid);
        } else {
            map.put(uuid, new UserData(uuid));
            return map.get(uuid);
        }
    }

    public static UserData getUserData(Player p) {
        if (p == null) return null;
        UUID uuid = p.getUniqueId();
        if (map.containsKey(uuid)) {
            return map.get(uuid);
        } else {
            map.put(uuid, new UserData(p));
            return map.get(uuid);
        }
    }

    public static UserData getUserData(OfflinePlayer p) {
        if (p == null) return null;
        UUID uuid = p.getUniqueId();
        if (map.containsKey(uuid)) {
            return map.get(uuid);
        } else {
            map.put(uuid, new UserData(p));
            return map.get(uuid);
        }
    }

    public static UserData tryGetUserData(String name) {
        for (UserData data : map.values()) {
            if (data.userName.equalsIgnoreCase(name))
                return data;
        }
        return null;
    }


    public static UserData loadUserData(Player p) {
        UserData user = UserData.getUserData(p);
        //MongoDB.readUser(user);
        user.updateStatus();
        return user;
    }

    public static UserData loadUserData(UserData user) {
        //MongoDB.readUser(user);
        user.updateStatus();
        return user;
    }

    public static UserData saveUserData(Player p) {
        UserData user = UserData.getUserData(p);
        MongoDB.updateUser(user);
        return user;
    }

    public static UserData saveUserData(UserData user) {
        MongoDB.updateUser(user);
        return user;
    }

}
