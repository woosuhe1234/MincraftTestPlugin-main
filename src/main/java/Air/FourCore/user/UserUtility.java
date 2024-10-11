package Air.FourCore.user;

import Air.FourCore.npc.quest.DailyQuest1;
import Air.FourCore.npc.quest.DailyQuest2;
import com.mongodb.client.model.BulkWriteOptions;
import com.mongodb.client.model.UpdateOneModel;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.WriteModel;
import Air.FourCore.mongoDB.MongoDB;
import org.bson.Document;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import Air.FourCore.task.userRequest.WarpTime;
import Air.FourCore.RandomSystem;
import Air.FourCore.WorldSetting;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class UserUtility {

    public static void brodcastSound(Sound sound) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.playSound(p.getLocation(), sound, 1, 1);
        }
    }

    public static void addHealth(Player p, double value) {
        if (p == null || !p.isOnline()) return;
        if (p.getHealth() + value <= p.getMaxHealth()) {
            p.setHealth(p.getHealth() + value);
        } else {
            p.setHealth(p.getMaxHealth());
        }
    }

    public static String playerToString(OfflinePlayer offline) {
        if (offline == null)
            return ChatColor.GRAY + "-";
        String name = offline.getName();
        if (name == null)
            name = UserData.getUserData(offline.getUniqueId()).userName;
        if (offline.isOnline()) {
            Player p = offline.getPlayer();
            if (p.getWorld().getName().contains("잠수")) {
                return ChatColor.YELLOW + name;
            } else {
                return ChatColor.GREEN + name;
            }
        } else {
            return ChatColor.GRAY + name;
        }
    }

    public static void teleport(Player p, Location loc) {
        if (p.getWorld().getName().contains("야생") || p.getWorld().getName().contains("PVP") || p.getWorld().getName().contains("골드던전")) {
            UserData data = UserData.getUserData(p);
            new WarpTime(7, data, loc);
            p.closeInventory();
        } else {
            p.teleport(loc);
        }
    }

    public static void basicItem(Inventory inventory) {
        ItemStack boots = new ItemStack(Material.IRON_BOOTS);
        boots.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
        inventory.addItem(boots);
        ItemStack leggings = new ItemStack(Material.IRON_LEGGINGS);
        leggings.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
        inventory.addItem(leggings);
        ItemStack chestplate = new ItemStack(Material.IRON_CHESTPLATE);
        chestplate.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
        inventory.addItem(chestplate);
        ItemStack helmet = new ItemStack(Material.IRON_HELMET);
        helmet.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
        inventory.addItem(helmet);
        ItemStack sword = new ItemStack(Material.IRON_SWORD);
        sword.addEnchantment(Enchantment.DAMAGE_ALL, 4);
        inventory.addItem(sword);
        ItemStack pickaxe = new ItemStack(Material.IRON_PICKAXE);
        pickaxe.addEnchantment(Enchantment.DIG_SPEED, 3);
        inventory.addItem(pickaxe);
    }

    public static void resetReward() {
        Bukkit.broadcastMessage("일일 보상이 초기화 되었습니다.");
        List<WriteModel<Document>> users = new ArrayList<>();
        for (UserData data : UserData.map.values()) {
            try {
                if (data == null) continue;
                if (RandomSystem.random.nextInt(2) == 0)
                    new DailyQuest1(data);
                else
                    new DailyQuest2(data);
                data.setting.countGambling = 0;
                data.setting.isBuyNetherStar = false;
                data.setting.isGetBasicItem = false;
                data.setting.isRewarded = false;
                data.setting.isRewarded_1h = false;
                data.setting.isRewarded_2h = false;
                data.setting.isRewarded_3h = false;
                data.setting.isRewarded_4h = false;
                data.setting.dailyPlayTime = 0;
                if (!data.isOnline() && WorldSetting.enableSave) {
                    // yml
                    //오류뜸 null reference
                    //UserConfig.get().set(data.uuid.toString() + ".CountGambling", data.setting.countGambling);
                    // DB
                    Document doc = new Document()
                            .append("setting", new Document()
                                    .append("isBuyNetherStar", data.setting.isBuyNetherStar)
                                    .append("isGetBasicItem", data.setting.isGetBasicItem)
                                    .append("isRewarded", data.setting.isRewarded)
                                    .append("isRewarded_1h", data.setting.isRewarded_1h)
                                    .append("isRewarded_2h", data.setting.isRewarded_2h)
                                    .append("isRewarded_3h", data.setting.isRewarded_3h)
                                    .append("isRewarded_4h", data.setting.isRewarded_4h)
                                    .append("dailyPlayTime", data.setting.dailyPlayTime));
                    UpdateOptions options = new UpdateOptions().upsert(true);
                    users.add(new UpdateOneModel<>(eq("uuid", data.uuid.toString()), new Document("$set", doc), options));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (WorldSetting.enableSave)
            MongoDB.user.bulkWrite(users, new BulkWriteOptions().ordered(false));
        //UserConfig.save();
    }
}
