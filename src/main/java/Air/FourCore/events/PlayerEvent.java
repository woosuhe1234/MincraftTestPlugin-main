package Air.FourCore.events;

//import com.sk89q.worldedit.extent.clipboard.Clipboard;

import Air.FourCore.files.BagConfig;
import Air.FourCore.files.UserConfig;
import Air.FourCore.item.BanItem;
import Air.FourCore.item.CustomItemUtility;
import Air.FourCore.menuSystem.MenuUtility;
import Air.FourCore.menuSystem.PlayerMenuUtility;
import Air.FourCore.menuSystem.menu.mainmenu.EnderWarpMenu;
import Air.FourCore.menuSystem.menu.mainmenu.MainMenu;
import Air.FourCore.mongoDB.SerializeUtility;
import Air.FourCore.nation.NationBlock;
import Air.FourCore.nation.NationData;
import Air.FourCore.nation.NationUtility;
import Air.FourCore.npc.quest.DailyQuest1;
import Air.FourCore.npc.quest.Quest;
import Air.FourCore.plugins.MagicSpellsListener;
import Air.FourCore.task.buff.ExpBuff;
import Air.FourCore.task.userRequest.WarpTime;
import Air.FourCore.task.worldTask.BattleTime;
import Air.FourCore.user.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.block.DoubleChest;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import Air.FourCore.task.userRequest.VampireCoolTime;
import Air.FourCore.RandomSystem;
import Air.FourCore.FourCore;
import Air.FourCore.WorldSetting;
import user.*;

import java.io.IOException;
import java.time.LocalTime;
import java.time.temporal.ChronoField;
import java.util.*;

import static org.bukkit.event.entity.EntityDamageEvent.DamageCause;

public class PlayerEvent implements Listener {
    private static Map<UUID, List<ItemStack>> belongingItemMap = new HashMap<>();

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player p = event.getPlayer();
        UserData data = UserData.getUserData(p);
        event.setJoinMessage(null);
        Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&',
                "&a&o[FourNetwork]&o &e" + p.getName() + " &f님이 서버에 접속하셨습니다. &a[ " + Bukkit.getOnlinePlayers().size() + "명 ]"));

        p.setPlayerListName((data.job == UserData.Job.왕 || data.job == UserData.Job.부왕 ? ChatColor.YELLOW : ChatColor.GRAY) + "[" + data.job.name() + "] " + ChatColor.WHITE + p.getName());
        UserData.loadUserData(p);
        data.userName = p.getName();
        data.loadData();
        // 국가 삭제
        if (data.nation != null && data.getNation() == null) {
            data.setUserJob(UserData.Job.일반유저);
            p.sendMessage("당신이 속한 국가가 사라졌습니다.");
        }
        // 권한 부여
        if (data.role == UserSetting.Role.가이드) {
            p.addAttachment(FourCore.instance).setPermission("FourCore.op", false);
            p.addAttachment(FourCore.instance).setPermission("FourCore.guide", true);
        }

        // 처음 접속
        if (!p.hasPlayedBefore()) {
            try {
                p.teleport(MenuUtility.getLocation("튜토리얼")); // 로비
            } catch (Exception ignored) {
            }
            UserUtility.basicItem(p.getInventory());
            //data.setting.isGetBasicItem = true;
        }
        // 대결 채널에서 로비로
        if (p.getWorld().getName().equalsIgnoreCase("VS")){
            Location loc = MenuUtility.getLocation("로비");
            if(loc != null){
                p.teleport(loc);
            }
        }
        // 밴
        if (data.warning >= 10) {
            p.banPlayerFull("밴 되었습니다.");
        }

        // 가방 로드
        try {
            BagData.getInventory(data.uuid).setContents(SerializeUtility.itemStackArrayFromBase64(BagConfig.get().getString(data.uuid.toString()+".bag")));
            BagData.getRune(data.uuid).setContents(SerializeUtility.itemStackArrayFromBase64(BagConfig.get().getString(data.uuid.toString()+".rune")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 특성 :: 초회복 아이템 지급
        if(data.level >= 30 && !CustomItemUtility.exist(p, CustomItemUtility.SuperHealthItem(1))){
            p.getInventory().addItem(CustomItemUtility.SuperHealthItem(1));
        }

        // 룬 능력 적용
        data.rune.setStates();
    }


    /*
    @EventHandler
    public void onPlayerQuit(Crop event) {

    }*/

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player p = event.getPlayer();
        event.setQuitMessage(null);
        Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&',
                "&c&o[FourNetwork]&o &e" + p.getName() + " &f님이 서버에 퇴장하셨습니다. &c[ " + (Bukkit.getOnlinePlayers().size() - 1) + "명 ]"));

        UserData data = UserData.getUserData(p);
        if (data.level > 1 || data.nation != null)
            UserData.saveUserData(p);
        data.saveData();
        UserConfig.save();

        BagConfig.get().set(data.uuid.toString()+".bag", SerializeUtility.itemStackArrayToBase64(BagData.getInventory(data.uuid).getContents()));
        BagConfig.get().set(data.uuid.toString()+".locker", SerializeUtility.itemStackArrayToBase64(BagData.getLocker(data.uuid).getContents()));
        BagConfig.get().set(data.uuid.toString()+".rune", SerializeUtility.itemStackArrayToBase64(BagData.getRune(data.uuid).getContents()));
        BagConfig.save();
    }

    @EventHandler
    public void onPlayerItemConsume(PlayerItemConsumeEvent e) {
        if (e.getItem() != null && e.getItem().getType() == Material.GOLDEN_APPLE) {
            e.setCancelled(true);
            e.getPlayer().getInventory().setItemInMainHand(new ItemStack(Material.AIR));
            e.getPlayer().sendMessage(ChatColor.RED + "※ 서버에서 금지된 아이템입니다.");
        }
    }


    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent e) {
        if(e.getInventory() == null)
            return;
        if (e.getInventory().getHolder() instanceof Chest || e.getInventory().getHolder() instanceof DoubleChest) {
            UserData data = UserData.getUserData((Player) e.getPlayer());
            NationData nation = null;
            if(data.nation != null) nation = data.getNation();

            NationBlock nb = NationUtility.getAllCastlesWithoutAllie(nation);
            int index = NationUtility.isCastleArea(nb, e.getInventory().getLocation());
            if (index != -1 && !nation.isWar()) {
                e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&',
                        "&f[ &6국가 &f] 다른 국가에선 상자를 열 수 없습니다."));
                e.setCancelled(true);
                return;
            }
        }

        String name = e.getInventory().getName();
        if(name.contains("장착")) {
            Inventory runeInventory = BagData.getRune(e.getPlayer().getUniqueId());
            if(runeInventory == null)
                return;
            if(runeInventory.getItem(0) != null)
                e.getInventory().addItem(runeInventory.getItem(0));
            if(runeInventory.getItem(1) != null)
                e.getInventory().addItem(runeInventory.getItem(1));
            if(runeInventory.getItem(2) != null)
                e.getInventory().addItem(runeInventory.getItem(2));
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e) {
        if(e.getInventory() == null)
            return;
        String name = e.getInventory().getName();
        if(name.contains("합성")) {
            Inventory playerInventory = e.getPlayer().getInventory();
            if(e.getInventory().getItem(11) != null)
                playerInventory.addItem(e.getInventory().getItem(11));
            if(e.getInventory().getItem(13) != null)
                playerInventory.addItem(e.getInventory().getItem(13));
            if(e.getInventory().getItem(15) != null)
                playerInventory.addItem(e.getInventory().getItem(15));
        }

        Inventory playerInventory = e.getPlayer().getInventory();
        Inventory inventory = e.getInventory();
        if(name.contains("장착")) {
            Inventory runeInventory = BagData.getRune(e.getPlayer().getUniqueId());
            runeInventory.setItem(0, e.getInventory().getItem(11));
            runeInventory.setItem(1, e.getInventory().getItem(13));
            runeInventory.setItem(2, e.getInventory().getItem(15));

            if (inventory.getItem(11) != null && (!CustomItemUtility.equals(inventory.getItem(11), CustomItemUtility.LowStoneItem()) && !CustomItemUtility.equals(inventory.getItem(11), CustomItemUtility.MiddleStoneItem()) && !CustomItemUtility.equals(inventory.getItem(11), CustomItemUtility.TopStoneItem()))) {
                playerInventory.addItem(inventory.getItem(11));
                inventory.setItem(11, null);
                runeInventory.setItem(0, null);
            }
            if (inventory.getItem(13) != null && (!CustomItemUtility.equals(inventory.getItem(13), CustomItemUtility.LowStoneItem()) && !CustomItemUtility.equals(inventory.getItem(13), CustomItemUtility.MiddleStoneItem()) && !CustomItemUtility.equals(inventory.getItem(13), CustomItemUtility.TopStoneItem()))) {
                playerInventory.addItem(inventory.getItem(13));
                inventory.setItem(13, null);
                runeInventory.setItem(1, null);
            }
            if (inventory.getItem(15) != null && (!CustomItemUtility.equals(inventory.getItem(15), CustomItemUtility.LowStoneItem()) && !CustomItemUtility.equals(inventory.getItem(15), CustomItemUtility.MiddleStoneItem()) && !CustomItemUtility.equals(inventory.getItem(15), CustomItemUtility.TopStoneItem()))) {
                playerInventory.addItem(inventory.getItem(15));
                inventory.setItem(15, null);
                runeInventory.setItem(2, null);
            }

            UserData.getUserData(e.getPlayer().getUniqueId()).rune.setStates();
        }
    }

    @EventHandler
    public void onInventoryMoveItem(InventoryClickEvent e) {
        // 가방 로그
        //UserData data = UserData.getUserData((Player) e.getInitiator().getViewers().get(0));
        //BagData.getInventory(user.uuid)
        //for(MainMenu.){

        //}
        if(e.getClickedInventory() == null) return;
        if(e.getClickedInventory().getType() == InventoryType.PLAYER && e.getInventory().getName().contains("보관함")){
            e.setCancelled(true);
            e.getWhoClicked().sendMessage("보관함은 꺼내기만 가능합니다.");
            return;
        }
        if(e.getInventory().getName().contains("보관함")){
            if (e.getHotbarButton() >= 0 && e.getHotbarButton() <= 9) {
                e.setCancelled(true);
            }
        }

        InventoryType type = e.getInventory().getType();
        String name = e.getInventory().getName();
        if (type == InventoryType.FURNACE || type == InventoryType.SHULKER_BOX || type == InventoryType.BEACON || type == InventoryType.HOPPER || type == InventoryType.DISPENSER || type == InventoryType.DROPPER || type == InventoryType.ANVIL ||
                (type == InventoryType.CHEST && !name.contains("가방") && !name.contains("쓰레기통") && !name.contains("보관함") && !name.contains("장착") && !name.contains("합성"))) {
            if (CustomItemUtility.isBelonging(e.getCurrentItem())) {
                // 거래 가능은 거래 가능
                if(name.contains("거래") && CustomItemUtility.isTrading(e.getCurrentItem()))
                    return;
                if(name.contains("장착") && (e.getSlot() == 11 || e.getSlot() == 13 || e.getSlot() == 15))
                    return;
                if(name.contains("합성") && (e.getSlot() == 11 || e.getSlot() == 13 || e.getSlot() == 15))
                    return;
                e.setCancelled(true);
                e.getWhoClicked().sendMessage("귀속 아이템은 옮길 수 없습니다.");
            }
            if (e.getHotbarButton() >= 0 && e.getHotbarButton() <= 9) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onExplotion(EntityExplodeEvent e) {
        if (e.isCancelled()) return;
        // 블록 폭발 방지
        e.blockList().clear();
    }

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent e) {
        Material material = e.getItemDrop().getItemStack().getType();
        if (CustomItemUtility.isBelonging(e.getItemDrop().getItemStack()) || material == Material.GOLD_SWORD || material == Material.IRON_SWORD || material == Material.WOOD_SWORD) {
            e.setCancelled(true);
            e.getPlayer().sendMessage("귀속 아이템은 버릴 수 없습니다.");
        }
    }

    @EventHandler
    public void onPlayerAttemptPickupItem(PlayerAttemptPickupItemEvent e) {
        // 아이템 밴
        for (Material m : BanItem.banItems) {
            if (e.getItem().getItemStack().getType() == m) {
                e.getPlayer().sendMessage(ChatColor.RED + "서버에서 금지된 아이템 입니다.");
                e.getItem().getItemStack().setAmount(0);
                e.setCancelled(true);
                return;
            }
        }
        if(e.getItem() != null && e.getItem().getItemStack() != null && CustomItemUtility.equals(e.getItem().getItemStack(), new ItemStack(Material.NETHER_STAR))){
            e.getPlayer().sendMessage(ChatColor.RED + "서버에서 금지된 아이템 입니다.");
            e.getItem().getItemStack().setAmount(0);
            e.setCancelled(true);
            return;
        }
    }

    @EventHandler
    public void onHungerDeplete(FoodLevelChangeEvent e) {
        // 배고픔 방지
        e.setFoodLevel(20);
        e.setCancelled(true);
    }

    @EventHandler
    public void onCreatureSpawn(CreatureSpawnEvent e) {
        // 야생 몹 스폰 금지
        if (e.getSpawnReason().equals(CreatureSpawnEvent.SpawnReason.NATURAL))
            e.setCancelled(true);
    }

    @EventHandler
    public void onEntityDamageByPlayer(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player) {
            Player p = (Player) e.getDamager();

            if (e.getEntity() instanceof Player) {
                Player target = (Player) e.getEntity();
                NationData nation = NationData.getNationData(UserData.getUserData(p).nation);

                // 뉴비야생 PVP 금지
                if (p.getWorld().getName().contains("뉴비")) {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&',
                            "&f[ &6국가 &f] 뉴비 야생에선 PVP를 할 수 없습니다."));
                    e.setCancelled(true);
                    return;
                }

                // 다른 국가에서 PVP시 데미지 캔슬
                NationBlock nb = NationUtility.getAllCastlesWithoutAllie(nation);
                int index = NationUtility.isCastleArea(nb, target.getLocation());
                if (index != -1 && (nation == null || !nation.isWar())) {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&',
                            "&f[ &6국가 &f] 다른 국가에선 공격할 수 없습니다."));
                    e.setCancelled(true);
                    return;
                }

                // 국가 PVP 비활성화시 데미지 캔슬
                String targetNation = UserData.getUserData(target).nation;
                if (nation != null && targetNation != null && !p.getWorld().getName().contains("사흉수") && !p.getWorld().getName().contains("PVP")) {
                    if (nation.nationName.equalsIgnoreCase(targetNation) && !nation.setting.nationPVP) {
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                "&f[ &6국가 &f] 국가원간의 PVP가 &c비활성화 &f되어있습니다."));
                        e.setCancelled(true);
                        return;
                    }
                    if (nation.allies.contains(targetNation) && !nation.setting.alliancePVP) {
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                "&f[ &6국가 &f] 동맹원간의 PVP가 &c비활성화 &f되어있습니다."));
                        e.setCancelled(true);
                        return;
                    }
                }

                /*
                // 전투모드 활성화
                UserData data = UserData.getUserData(p);
                if (data.<BattleModeTime>getTimerable(BattleModeTime.class) == null){
                    new BattleModeTime(30, data);
                }else{
                    data.<BattleModeTime>getTimerable(BattleModeTime.class).timer = 30;
                }
                // 전투모드 활성화
                UserData targetData = UserData.getUserData(target);
                if (targetData.<BattleModeTime>getTimerable(BattleModeTime.class) == null){
                    new BattleModeTime(30, targetData);
                }else{
                    targetData.<BattleModeTime>getTimerable(BattleModeTime.class).timer = 30;
                }
                */
            }

            // 매직스펠이면 취소
            if (MagicSpellsListener.set.contains(p)) {
                MagicSpellsListener.set.remove(p);
                return;
            }

            // 데미지 보정 적용
            if (e.getCause() == DamageCause.ENTITY_ATTACK ||
                    e.getCause() == DamageCause.ENTITY_SWEEP_ATTACK ||
                    e.getCause() == DamageCause.PROJECTILE) {
                UserData data = UserData.getUserData(p);
                if (e.getEntity() instanceof Player) {
                    // Player target = (Player) e.getEntity();
                    e.setDamage(e.getDamage() + data.getDamage() / 5);
                } else {
                    e.setDamage(e.getDamage() + data.getDamage());
                }
                // 흡혈
                if (e.getDamage() == 0.0)
                    return;
                if (data.mental >= 50 && data.<VampireCoolTime>getTimerable(VampireCoolTime.class) == null) {
                    new VampireCoolTime(30, data);
                    p.sendMessage(ChatColor.GRAY + "흡혈!");
                    UserUtility.addHealth(p, 5);
                }
            }
        }
    }

    private double CalculateDamage(double damage, double defense, double toughness) {
        return damage * (1 - Math.min(20, Math.max(defense / 5, defense - damage / (2 + toughness / 4))) / 25);
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent e) {
        // 데미지 0이면 무시
        if (e.getDamage() == 0.0)
            return;

        // 낙하피해 무시
        if (e.getCause() == DamageCause.FALL) {
            e.setCancelled(true);
        }

        // 방어구 내구도 무한
        if (e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();
            PlayerInventory inventory = p.getInventory();
            if (inventory.getHelmet() != null)
                inventory.getHelmet().setDurability((short) 0);
            if (inventory.getChestplate() != null)
                inventory.getChestplate().setDurability((short) 0);
            if (inventory.getLeggings() != null)
                inventory.getLeggings().setDurability((short) 0);
            if (inventory.getBoots() != null)
                inventory.getBoots().setDurability((short) 0);
        }

        // 회피/저항
        if (e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();
            UserData data = UserData.getUserData(p);
            if (data.force >= 50 && RandomSystem.random.nextInt(20) == 0) {
                p.sendMessage(ChatColor.GRAY + "회피!");
                e.setCancelled(true);
            }
            if (data.defense >= 50 && RandomSystem.random.nextInt(50) == 0) {
                p.sendMessage(ChatColor.GRAY + "저항!");
                p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 60, 0));
            }
        }
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent e) {
        // 몹 잡을때
        Player killer = e.getEntity().getKiller();
        String name = e.getEntity().getName();
        if (killer != null) {
            UserData data = UserData.getUserData(killer);
            FileConfiguration config = FourCore.instance.getConfig();
            Set<String> set = config.getConfigurationSection("Mobs").getKeys(false);

            if (set.contains(e.getEntity().getName())) {
                int exp = config.getInt("Mobs." + name + ".EXP");
                int level = config.getInt("Mobs." + name + ".level");

                if (level == 0 || (data.level + 10 >= level && data.level - 20 < level)) {
                    //경험치 버프
                    String extra = "";
                    float plus = 0;

                    if (data.<ExpBuff>getTimerable(ExpBuff.class) != null) {
                        plus += 0.5f;
                    }
                    plus += WorldSetting.ExpEvent * 0.01f;
                    plus += data.rune.getState(UserRune.State.경험치) * 0.01f;

                    if(plus == 0){
                        UserData.getUserData(killer).addExp(exp);
                    }else{
                        extra = "(+" + (int) (exp * plus) + ")";
                        UserData.getUserData(killer).addExp(exp * (1f + plus));
                    }

                    killer.sendMessage(ChatColor.GRAY + "※ 경험치 " + (long) exp + extra + " 획득! - [ " + (long) data.exp + " / " + (long) data.getEXPGoal() + " ] (" + String.format("%.2f", data.exp / data.getEXPGoal() * 100) + "%)");

                    // 이벤트 드롭 아이템
                    if(WorldSetting.event){
                        int rand = RandomSystem.random.nextInt(3000);
                        if(rand == 0){
                            killer.getInventory().addItem(CustomItemUtility.NetherStartBoxItem());
                            killer.sendMessage(ChatColor.YELLOW + "※ 네더의별 상자를 얻으셨습니다.");
                        }else if(rand <= 1){
                            killer.getInventory().addItem(CustomItemUtility.StatResetItem());
                            killer.sendMessage(ChatColor.YELLOW + "※ 스텟 초기화권을 얻으셨습니다.");
                        }else if(rand <= 3){
                            killer.getInventory().addItem(CustomItemUtility.InitNationCoolTimeItem(1));
                            killer.sendMessage(ChatColor.YELLOW + "※ 국가 쿨타임 초기화권을 얻으셨습니다.");
                        }else if(rand <= 5){
                            killer.getInventory().addItem(CustomItemUtility.DetectLocationItem(1));
                            killer.sendMessage(ChatColor.YELLOW + "※ 좌표 탐지권을 얻으셨습니다.");
                        }else if(rand <= 15){
                            killer.getInventory().addItem(CustomItemUtility.NetherStartItem(1));
                            killer.sendMessage(ChatColor.YELLOW + "※ 네더의별을 얻으셨습니다.");
                        }else if(rand <= 25){
                            killer.getInventory().addItem(CustomItemUtility.NationStoneItem());
                            killer.sendMessage(ChatColor.YELLOW + "※ 국가 강화석을 얻으셨습니다.");
                        }else if(rand <= 35){
                            killer.getInventory().addItem(CustomItemUtility.MoneyBoxItem(1));
                            killer.sendMessage(ChatColor.YELLOW + "※ 돈 주머니를 얻으셨습니다.");
                        }
                    }
                } else {
                    killer.sendMessage(ChatColor.GRAY + "※ 레벨에 맞지 않는 몬스터 입니다.");
                }
            }

            // 드롭 아이템
            if (name.contains("파프니르") || name.contains("리바이어던") || name.contains("펜리르") || name.contains("요르문간드")) {
                if(RandomSystem.random.nextInt(5) <= 3){
                    killer.getInventory().addItem(CustomItemUtility.SoulItem(1));
                    killer.sendMessage(ChatColor.YELLOW + "※ 영혼 아이템을 얻으셨습니다!");
                }else{
                    killer.sendMessage(ChatColor.GRAY + "※ 영혼 아이템을 얻지 못했습니다.");
                }
                Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&',
                        "&6[ &a"+killer.getName()+" &f님이 "+name+"&f를 잡았습니다. &6]"));
                Location loc = MenuUtility.getLocation("로비");
                if(loc != null){
                    killer.teleport(loc);
                }
            }
            // 레이드 보상
            if (name.contains("전설의 기사")){
                NationData nation = UserData.getUserData(killer).getNation();
                Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&',
                        "&6[ &e"+ nation.nationName+" &f국가의 &a"+killer.getName()+" &f님이 1단계 보스를 처치하셨습니다! &6]"));
                killer.getInventory().addItem(CustomItemUtility.NetherStartItem(10));
                killer.getInventory().addItem(CustomItemUtility.NetherStartItemNone(10));
                nation.teleport(MenuUtility.getLocation("로비"));
            }

            // 퀘스트 진행
            if (data.questList != null) {
                for (Quest q : data.questList) {
                    if(q == null) continue;
                    if (q.getTarget().contains(name) || q.getTarget().equals("ALL")) {
                        q.add();
                    }
                }
            }

            // 황금 던전
            int r = RandomSystem.random.nextInt(100);
            int money = 0;
            if (name.equalsIgnoreCase("하급골드기사")) {
                if (r < 85) money = 5000;
                else if (r < 95) money = 7500;
                else money = 10000;
                FourCore.economy.depositPlayer(killer, money);
                killer.sendMessage(ChatColor.RESET + "+" + money + "원");
            } else if (name.equalsIgnoreCase("중급골드기사")) {
                if (r < 80) money = 5000;
                else if (r < 97) money = 6000;
                else money = 7000;
                FourCore.economy.depositPlayer(killer, money);
                killer.sendMessage(ChatColor.RESET + "+" + money + "원");
            } else if (name.equalsIgnoreCase("상급골드기사")) {
                if (r < 80) money = 7000;
                else if (r < 97) money = 9000;
                else money = 10000;
                FourCore.economy.depositPlayer(killer, money);
                killer.sendMessage(ChatColor.RESET + "+" + money + "원");
            }
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        Player killer = e.getEntity().getKiller();
        Player player = e.getEntity().getPlayer();

        // 사살 알림
        if (killer != null) {
            ItemStack weapon = killer.getInventory().getItemInMainHand();
            String name = "";
            if (weapon == null || weapon.getType() == Material.AIR) {
                name = "맨손";
            } else {
                name = weapon.getType().name();
                if (weapon.getItemMeta().getDisplayName() != null) {
                    name = weapon.getItemMeta().getDisplayName();
                }
            }
            // 기존 알림 음소거
            e.setDeathMessage(null);
            Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&',
                    "&4[&f 사살 &4] &c[&f " + killer.getName() + " &c] &f님이 &9[&f " + player.getName() + " &9] &f님을 &a[ &8"
                            + name + " &a] &f(으)로 죽이셨습니다."));

        }

        // 대련 결과
        for(BattleTime b: WorldSetting.<BattleTime>getTimerables(BattleTime.class)){
            if(b.p1 == player){
                b.win(b.p2, b.p1);
                break;
            }else if(b.p2 == player){
                b.win(b.p1, b.p2);
                break;
            }
        }

        // 죽을때 귀속 아이템 드랍 방지
        List<ItemStack> belonging = new ArrayList<>();
        for (ItemStack item : e.getDrops()) {
            if (CustomItemUtility.isBelonging(item)) {
                belonging.add(item);
            }
        }
        e.getDrops().removeAll(belonging);
        if(belonging.size() > 0)
            belongingItemMap.put(player.getUniqueId(), belonging);
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent e) {
        List<ItemStack> belonging = belongingItemMap.get(e.getPlayer().getUniqueId());
        if (belonging == null) return;
        for (ItemStack item : belonging) {
            e.getPlayer().getInventory().addItem(item);
        }
        //belongingItemMap.remove(e.getPlayer().getUniqueId());
    }

    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent e) {
        if (e.getCause().equals(PlayerTeleportEvent.TeleportCause.ENDER_PEARL)) {
            e.setCancelled(true);
        }

        if (e.getCause().equals(PlayerTeleportEvent.TeleportCause.NETHER_PORTAL)) {

            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerSwapHandItems(PlayerSwapHandItemsEvent e) {
        Player p = e.getPlayer();
        if (p.isSneaking()) {
            e.setCancelled(true);
            new MainMenu(PlayerMenuUtility.getPlayerMenuUtility(p)).open();
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        // 지옥포탈 드가기
        try {
            Location loc = p.getPlayer().getLocation();
            int block = loc.getWorld().getBlockTypeIdAt(loc);
            if (block == 90) {
                Location loc2 = p.getPlayer().getLocation();
                loc2.setX(loc2.getX() + 2);
                loc2.setZ(loc2.getZ() + 1);
                p.teleport(loc2);
                new BukkitRunnable() {

                    @Override
                    public void run() {
                        new EnderWarpMenu(PlayerMenuUtility.getPlayerMenuUtility(p)).open(); //접속후 바로 인벤토리 열면 튕김

                    }
                }.runTaskLater(FourCore.instance, 2);
            }
        } catch (Exception exception) {
        }

        // 포탈중 움직이면 취소
        UserData data = UserData.getUserData(p);
        if (data.<WarpTime>getTimerable(WarpTime.class) != null) {
            if ((int) e.getFrom().getX() != (int) e.getTo().getX() ||
                    (int) e.getFrom().getY() != (int) e.getTo().getY() ||
                    (int) e.getFrom().getZ() != (int) e.getTo().getZ())
                data.<WarpTime>getTimerable(WarpTime.class).stop();
        }

        // 일일퀘
        if (data.questList[2] != null && LocalTime.now().getLong(ChronoField.MILLI_OF_SECOND) % 1000 < 50 && data.questList[2].getClass() == DailyQuest1.class){
            data.questList[2].add();
            //data.questList[2].current++;
        }
    }


}
