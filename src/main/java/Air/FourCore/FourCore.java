package Air.FourCore;

import Air.FourCore.files.*;
import Air.FourCore.nation.*;
import Air.FourCore.user.*;
import com.mongodb.client.model.BulkWriteOptions;
import com.mongodb.client.model.UpdateOneModel;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.WriteModel;
import Air.FourCore.events.NationEvent;
import Air.FourCore.events.PlayerEvent;
import Air.FourCore.events.ServerEvent;
import files.*;
import Air.FourCore.management.ManagementCommandExecutor;
import Air.FourCore.management.RoleCommandExecutor;
import Air.FourCore.menuSystem.MenuCommandExecutor;
import Air.FourCore.menuSystem.MenuListener;
import Air.FourCore.mongoDB.MongoDB;
import Air.FourCore.mongoDB.SerializeUtility;
import nation.*;
import net.milkbowl.vault.economy.Economy;
import Air.FourCore.network.CoinManager;
import Air.FourCore.npc.NPCListener;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import Air.FourCore.plugins.MagicSpellsListener;
import Air.FourCore.task.TaskManager;
import Air.FourCore.task.UpdateTask;
import Air.FourCore.task.buff.ExpBuff;
import Air.FourCore.task.nationRequest.CastleBuildTime;
import Air.FourCore.task.userRequest.RejoinNationCoolTime;
import Air.FourCore.task.userRequest.inviteRequest.InviteCommandExecutor;
import user.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;
import java.util.UUID;
import java.util.logging.Logger;

import static com.mongodb.client.model.Filters.eq;

public final class FourCore extends JavaPlugin implements Listener, CommandExecutor {

    public static FourCore instance;
    public static Logger log;
    public static Economy economy = null;

    private UpdateTask updateTask = null;
    private TaskManager taskManager = null;

    @Override
    public void onEnable() {
        CoinManager.updateCoin();
        instance = this;
        log = Bukkit.getLogger();
        log.info("wow");
        for (int i = 0; i < WorldSetting.bossRooms.length; i++){
            WorldSetting.bossRooms[i] = new BossRoom();
        }
        //getConfig().options().copyDefaults(); // 얘도 있으면 절대 안바뀜(?)
        saveDefaultConfig(); // 얘만 있으면 config.yml 지우고 해야 바뀐게 적용됨
        MongoDB.initDB();
        ShopConfig.setup();
        ShopConfig.get().options().copyDefaults(true);
        ShopConfig.save();
        RewardConfig.setup();
        RewardConfig.get().options().copyDefaults(true);
        RewardConfig.save();
        BagConfig.setup();
        BagConfig.get().options().copyDefaults(true);
        BagConfig.save();
        UserConfig.setup();
        UserConfig.get().options().copyDefaults(true);
        UserConfig.save();
        WorldConfig.setup();
        WorldConfig.get().options().copyDefaults(true);
        WorldConfig.save();
        System.out.println("Hello World!");

        if (!setupEconomy()) {
            log.severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        userCommand(new String[]{"Air/FourCore/user", "userlist", "save", "레이드입장", "이벤트", "로그조사", "코인", "도박", "룬", "가이드티피", "점검보상", "점검보상지급", "개조예시", "경험치이벤트", "사전후원보상", "후원지급", "캐시지급", "리붓", "무력", "튜토리얼", "관리자", "가이드", "퀘스트", "대련", "후원", "카페", "디스코드", "마인리스트", "이벤트지급", "랭킹", "홈2", "셋홈2", "홈", "셋홈", "스텟", "정보", "돈", "캐시", "보관함", "가방", "쓰레기통", "워프", "도움말", "스텟초기화", "경험치쿠폰", "동맹권", "기본템", "국가강화석", "신호기", "가이드채팅", "경고", "보상초기화", "네더의별상자"});
        this.getCommand("수락").setExecutor(new InviteCommandExecutor());
        this.getCommand("거절").setExecutor(new InviteCommandExecutor());
        this.getCommand("관리").setExecutor(new ManagementCommandExecutor());
        this.getCommand("역할").setExecutor(new RoleCommandExecutor());
        this.getCommand("메뉴").setExecutor(new MenuCommandExecutor());
        this.getCommand("지급").setExecutor(new GiveCommandExecutor());
        this.getCommand("점령전").setExecutor(new DominationCommandExecutor());
        this.getCommand("귓속말").setExecutor(new WhisperCommandExecutor());
        this.getCommand("답장").setExecutor(new WhisperResponseCommandExecutor());
        this.getCommand("국가").setExecutor(new NationCommandExecutor());
        this.getCommand("국가관리").setExecutor(new NationManagementCommandExecutor());
        this.getCommand("전쟁").setExecutor(new WarCommandExecutor());
        this.getCommand("국가").setTabCompleter(new NationTabCompletion());

        getServer().getPluginManager().registerEvents(new MagicSpellsListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerEvent(), this);
        getServer().getPluginManager().registerEvents(new NationEvent(), this);
        getServer().getPluginManager().registerEvents(new ServerEvent(), this);
        getServer().getPluginManager().registerEvents(new MenuListener(), this);
        getServer().getPluginManager().registerEvents(new NPCListener(), this);
        getServer().getPluginManager().registerEvents(this, this);

        // Update
        if (updateTask == null) {
            updateTask = new UpdateTask();
            updateTask.runTaskTimer(this, 0l, 20l);
        }
        if (taskManager == null) {
            taskManager = new TaskManager();
            taskManager.runTaskTimer(this, 0l, 20l);
        }

        // reload 대비
        for (Document doc : MongoDB.nation.find()) {
            NationData nation = NationData.createNation(doc.getString("nationName"));
            if (nation != null) {
                nation.setData(doc);
                if (nation.castles.size() < 1) {
                    // 성 없을경우 1시간후 삭제
                    new CastleBuildTime(3600, nation);
                }
            }
        }

        for (Document doc : MongoDB.user.find()) {
            try {
                UserData user = UserData.getUserData(UUID.fromString(doc.getString("uuid")));
                user.userName = doc.getString("userName");
                user.setData(doc);

                Document timerList = (Document) doc.get("timerList");
                try {
                    int t = timerList.getInteger("ExpBuff");
                    new ExpBuff(t, user);
                } catch (Exception e) {
                }
                try {
                    int t = timerList.getInteger("RejoinNationCoolTime");
                    new RejoinNationCoolTime(t, user);
                } catch (Exception e) {
                }
                //UserData.loadUserData(user);
            } catch (Exception e) {
            }
        }
        for (String key : BagConfig.get().getKeys(false)) {
            try {
                BagData.getInventory(UUID.fromString(key))
                        .setContents(SerializeUtility.itemStackArrayFromBase64(BagConfig.get().getString(key)));
            } catch (Exception ignored) {
            }
            try {
                BagData.getInventory(UUID.fromString(key))
                        .setContents(SerializeUtility.itemStackArrayFromBase64(BagConfig.get().getString(key+".bag")));
                BagData.getLocker(UUID.fromString(key))
                        .setContents(SerializeUtility.itemStackArrayFromBase64(BagConfig.get().getString(key+".locker")));
                BagData.getRune(UUID.fromString(key))
                        .setContents(SerializeUtility.itemStackArrayFromBase64(BagConfig.get().getString(key+".rune")));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        // 버그픽스
        for(NationData nation : NationData.map.values()){
            nation.userBugFix();
        }
        /*
        Player[] players = Bukkit.getOnlinePlayers().toArray(new Player[0]);
        for(Player player : players){
            UserData.loadUserData(player);
        }*/

        // reload 대비
        for(Player player: Bukkit.getOnlinePlayers()){
            UserData.getUserData(player).loadData();
        }

        // 날짜 로드
        try{
            WorldSetting.lastResetReward = LocalDateTime.ofInstant(Instant.ofEpochMilli(WorldConfig.get().getLong("LastResetReward")),
                    TimeZone.getDefault().toZoneId());
        }catch (Exception ignored){}

    }

    private boolean setupEconomy() {
        RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        if (economyProvider != null) {
            economy = economyProvider.getProvider();
        }

        return (economy != null);
    }

    private void userCommand(String[] commands) {
        for (String command : commands) {
            try {
                this.getCommand(command).setExecutor(new UserCommandExecutor());
            }catch (Exception ignored){}
        }
    }

    private void initialize() {
    }

    @Override
    public void onDisable() {
        System.out.println("Backup World");

        boolean enable = true;
        try {
            enable = WorldSetting.enableSave;
        } catch (NoClassDefFoundError e) {
            e.printStackTrace();
        }
        if (enable) {
            UpdateOptions options = new UpdateOptions().upsert(true);
            // reload 대비
            List<WriteModel<Document>> nations = new ArrayList<>();
            for (NationData nation : NationData.map.values()) {
                //MongoDB.updateNation(nation);
                nations.add(new UpdateOneModel<>(eq("nationName", nation.nationName), new Document("$set", MongoDB.documentNation(nation)), options));
            }
            MongoDB.nation.bulkWrite(nations, new BulkWriteOptions().ordered(false));

            List<WriteModel<Document>> users = new ArrayList<>();
            for (UserData data : UserData.map.values()) {
                OfflinePlayer player = Bukkit.getOfflinePlayer(data.uuid);
                if (data.level > 1 || data.nation != null)
                    users.add(new UpdateOneModel<>(eq("uuid", data.uuid.toString()), new Document("$set", MongoDB.documentUser(data)), options));

            }
            MongoDB.user.bulkWrite(users, new BulkWriteOptions().ordered(false));

        } else {
            Bukkit.broadcastMessage(ChatColor.RED + "※ 이 서버가 테스트 서버라 판단하여 서버 종료시 데이터를 DB에 올리지 않았습니다!");
            Bukkit.broadcastMessage(ChatColor.RED + "※ DB에 올리고 싶다면 \'myworld\' 이름을 가진 월드의 명을 다르게 바꿔주세요.");
        }

        // 가방 세이브
        for (UserData data : UserData.map.values()) {
            try {
                BagConfig.get().set(data.uuid.toString()+".bag",
                        SerializeUtility.itemStackArrayToBase64(BagData.getInventory(data.uuid).getContents()));
                BagConfig.get().set(data.uuid.toString()+".locker",
                        SerializeUtility.itemStackArrayToBase64(BagData.getLocker(data.uuid).getContents()));
                BagConfig.get().set(data.uuid.toString()+".rune",
                        SerializeUtility.itemStackArrayToBase64(BagData.getRune(data.uuid).getContents()));
            }catch (Exception e){}
        }
        BagConfig.save();

        // reload 대비
        for(Player player: Bukkit.getOnlinePlayers()){
            UserData.getUserData(player).saveData();
        }
        UserConfig.save();

        // 날짜 저장
        WorldConfig.get().set("LastResetReward", WorldSetting.lastResetReward.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
        WorldConfig.save();
    }
}
