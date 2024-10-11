package Air.FourCore.events;

import Air.FourCore.item.BanItem;
import Air.FourCore.item.CustomItemUtility;
import Air.FourCore.nation.BlockData;
import Air.FourCore.nation.NationBlock;
import Air.FourCore.nation.NationData;
import Air.FourCore.nation.NationUtility;
import Air.FourCore.plugins.WorldEditUtility;
import Air.FourCore.task.buff.ExpBuff;
import Air.FourCore.task.userRequest.HealingPotionCoolTime;
import Air.FourCore.task.userRequest.RejoinNationCoolTime;
import Air.FourCore.task.userRequest.SuperHealthCoolTime;
import Air.FourCore.user.UserData;
import Air.FourCore.user.UserSetting;
import Air.FourCore.user.UserUtility;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.material.Crops;
import org.bukkit.material.Door;
import org.bukkit.material.MaterialData;
import org.bukkit.material.Openable;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import Air.FourCore.task.nationRequest.CastleBuildTime;
import Air.FourCore.task.nationRequest.WarTime;
import Air.FourCore.RandomSystem;
import Air.FourCore.FourCore;
import Air.FourCore.WorldSetting;

import static Air.FourCore.user.UserData.getUserData;

public class NationEvent implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        Player p = e.getPlayer();
        UserData data = UserData.getUserData(p);
        NationData nation = data.getNation();

        // 국가 성 파괴
        if (e.getBlock().getType() == Material.BEACON) {
            e.setDropItems(false);
            /* 맨손아니여도 부술수있을때
            if (e.getPlayer().getItemInHand().getType() != Material.AIR) {
                p.sendMessage(nation("맨손이 아니면 부술 수 없습니다."));
                e.setCancelled(true);
                return;
            }
            */
            NationData targetNation = null;
            try {
                targetNation = nation.getEnemyNation();
            } catch (Exception ex) {
            }
            // 대표만 부수기
            if (data.role == UserSetting.Role.대표) {
                NationBlock nb = NationUtility.getAllCastles(null);
                for (int i = 0; i < nb.block.size(); i++) {
                    if (nb.block.get(i).getBlock() == e.getBlock()) {
                        targetNation = nb.nation.get(i);
                        break;
                    }
                }
                if (targetNation != null) {
                    targetNation.castles.removeIf(n -> n.world.equalsIgnoreCase(e.getBlock().getWorld().getName()) && n.x == e.getBlock().getX() && n.y == e.getBlock().getY() && n.z == e.getBlock().getZ());
                    WorldEditUtility.setEmpty(e.getBlock().getLocation(), 36, 36);
                    p.sendMessage("대표의 권한으로 성을 삭제했습니다.");
                    return;
                } else {
                    p.sendMessage("대표의 권한으로 신호기를 삭제했습니다.");
                    return;
                }
            }
            if (targetNation != null && nation.<WarTime>getTimerable(WarTime.class) != null) {
                Boolean breakable = false;
                breakable = targetNation.castles.stream().anyMatch(b -> b.getBlock().getLocation().equals(e.getBlock().getLocation()));
                if (breakable) {
                    if (nation == WorldSetting.warSender || (nation == WorldSetting.warReceiver && WorldSetting.counterWar)) {
                        targetNation.castles.removeIf(n -> n.world.equalsIgnoreCase(e.getBlock().getWorld().getName()) && n.x == e.getBlock().getX() && n.y == e.getBlock().getY() && n.z == e.getBlock().getZ());
                        e.getBlock().getWorld().createExplosion(e.getBlock().getLocation(), 0.0f, false);
                        WorldEditUtility.setEmpty(e.getBlock().getLocation(), 36, 36);
                        NationUtility.broadcastTitleAlliance(nation, "&6[ 국가 전쟁 ]", "&6" + targetNation.nationName + " &f국가의 성 파괴");
                        NationUtility.broadcastTitleAlliance(targetNation, "&6[ 국가 전쟁 ]", "&6" + targetNation.nationName + " &f국가의 성 파괴");
                        Bukkit.broadcastMessage(NationUtility.nation("&6" + nation.nationName + " 국가의 &e" + p.getName() + " &f님이 &6" + targetNation.nationName + " &f국가의 성을 파괴하였습니다!"));
                        Bukkit.broadcastMessage(NationUtility.nation("성을 파괴한 보상으로 &e" + p.getName() + " &f에게 &l네더별 15개 &l가 지급되었습니다."));
                        p.getInventory().addItem(CustomItemUtility.NetherStartItemNone(15));
                        if (targetNation.castles.size() <= 0) {
                            nation.<WarTime>getTimerable(WarTime.class).destroy(nation != WorldSetting.warSender);
                            p.getInventory().addItem(CustomItemUtility.NetherStartItemNone(30));
                            Bukkit.broadcastMessage(NationUtility.nation("멸망시킨 보상으로 &e" + p.getName() + " &f에게 &l네더별 30개 &l가 추가 지급되었습니다."));
                            nation.score += 3;
                        }
                        FourCore.log.info(targetNation.nationName + "국가의 (" + e.getBlock().getX() + "," + e.getBlock().getY() + "," + e.getBlock().getZ() + ")에 성이 파괴되었습니다.");
                        nation.score += 5;
                        targetNation.score -= 3;
                        return;
                    } else {
                        p.sendMessage(NationUtility.nation("방어측은 상대의 성을 부술 수 없습니다. 공격측만 부술 수 있습니다."));
                        e.setCancelled(true);
                    }
                } else {
                    p.sendMessage(NationUtility.nation("전쟁 상대의 신호기만 부술 수 있습니다."));
                    e.setCancelled(true);
                }
            } else {
                p.sendMessage(NationUtility.nation("전쟁 중이 아니면 부술 수 없습니다."));
                e.setCancelled(true);
            }
        }

        // 전초기지 파괴
        if (e.getBlock().getType() == Material.LAPIS_BLOCK) {
            e.setDropItems(false);
            if (e.getPlayer().getItemInHand().getType() != Material.AIR) {
                p.sendMessage(NationUtility.nation("맨손이 아니면 부술 수 없습니다."));
                e.setCancelled(true);
                return;
            }
            NationData targetNation = null;
            NationBlock n = NationUtility.getAllOutposts(null);
            for (int i = 0; i < n.block.size(); i++) {
                if (n.block.get(i).getBlock().equals(e.getBlock())) {
                    targetNation = n.nation.get(i);
                    break;
                }
            }
            if (targetNation != null) {
                targetNation.outposts.removeIf(b -> b.world.equalsIgnoreCase(e.getBlock().getWorld().getName()) && b.x == e.getBlock().getX() && b.y == e.getBlock().getY() && b.z == e.getBlock().getZ());
                targetNation.outposts.remove(new BlockData(e.getBlock()));
                NationUtility.broadcastTitleAlliance(nation, "&6[ 국가 전쟁 ]", "&6" + targetNation.nationName + " &f국가의 전초기지 파괴");
                NationUtility.broadcastTitleAlliance(targetNation, "&6[ 국가 전쟁 ]", "&6" + targetNation.nationName + " &f국가의 전초기지 파괴");
                NationUtility.broadcastNation(nation, "&6" + nation.nationName + " 국가의 &e" + p.getName() + " &f님이 &6" + targetNation.nationName + " &f국가의 전초기지를 파괴하였습니다.");
                NationUtility.broadcastNation(targetNation, "&6" + nation.nationName + " 국가의 &e" + p.getName() + " &f님이 &6" + targetNation.nationName + " &f국가의 전초기지를 파괴하였습니다.");
                FourCore.log.info(targetNation.nationName + "국가의 (" + e.getBlock().getX() + "," + e.getBlock().getY() + "," + e.getBlock().getZ() + ")에 전초기지가 파괴되었습니다.");
            }
            return;
        }

        // 전쟁중 파괴불가
        if (nation != null && nation.<WarTime>getTimerable(WarTime.class) != null) {
            p.sendMessage(NationUtility.nation("전쟁 중에는 부술 수 없습니다."));
            e.setCancelled(true);
            return;
        }

        // 블록 보호
        NationBlock n = NationUtility.getAllCastles(nation);
        int index = NationUtility.isCastleArea(n, e.getBlock().getLocation());
        if (index != -1) {
            p.sendMessage(NationUtility.nation("&6" + n.nation.get(index).nationName + " &f국가의 영토입니다."));
            e.setCancelled(true);
            return;
        }

        // 농사 들어오기
        Material type = e.getBlock().getType();
        if (!data.isBusy()) {
            if (type == Material.PUMPKIN) {
                p.getInventory().addItem(new ItemStack(Material.PUMPKIN));
                e.setDropItems(false);
            }
            if (type == Material.SUGAR_CANE_BLOCK) {
                p.getInventory().addItem(new ItemStack(Material.SUGAR_CANE));
                e.setDropItems(false);
                if (e.getBlock().getWorld().getBlockAt(e.getBlock().getLocation().add(0, 1, 0)).getType() == Material.SUGAR_CANE_BLOCK) {
                    p.getInventory().addItem(new ItemStack(Material.SUGAR_CANE));
                    if (e.getBlock().getWorld().getBlockAt(e.getBlock().getLocation().add(0, 2, 0)).getType() == Material.SUGAR_CANE_BLOCK) {
                        p.getInventory().addItem(new ItemStack(Material.SUGAR_CANE));
                    }
                }
            }
            if (type == Material.CROPS) {
                CropState cropState = ((Crops) e.getBlock().getState().getData()).getState();
                if (cropState == CropState.RIPE) {
                    p.getInventory().addItem(new ItemStack(Material.WHEAT));
                }
                e.setDropItems(false);
            }
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        UserData data = UserData.getUserData(e.getPlayer());
        NationData nation = data.getNation();

        // 아이템 밴
        try {
            for (Material m : BanItem.banItems) {
                if (e.getBlock().getType() == m) {
                    e.getPlayer().sendMessage(ChatColor.RED + "서버에서 금지된 아이템 입니다.");
                    e.setCancelled(true);
                    return;
                }
            }
        } catch (NoClassDefFoundError error) {
        }

        // 전쟁중 설치 불가
        if (nation != null && nation.<WarTime>getTimerable(WarTime.class) != null) {
            e.getPlayer().sendMessage("전쟁 중에는 설치할 수 없습니다.");
            e.setCancelled(true);
            return;
        }

        // 블록 보호
        NationBlock n = NationUtility.getAllCastles(nation);
        int protect = 36;
        if (e.getBlock().getType() == Material.BEACON) protect = 80;
        if (e.getBlock().getType() == Material.LAPIS_BLOCK) protect = 80;
        int x1 = e.getBlock().getX(), z1 = e.getBlock().getZ();
        for (int i = 0; i < n.block.size(); i++) {
            BlockData b = n.block.get(i);
            int x2 = b.x, z2 = b.z;
            double distance = Math.max(Math.abs(x1 - x2), Math.abs(z1 - z2));
            if (b.world.equalsIgnoreCase(e.getBlock().getWorld().getName()) && distance <= protect) {
                if (e.getBlock().getType() == Material.BEACON) {
                    e.getPlayer().sendMessage(NationUtility.nation("다른 국가의 영토와 너무 가깝습니다. 80칸이상 떨어져주세요. &7현재 " + distance + "칸"));
                } else if (e.getBlock().getType() == Material.LAPIS_BLOCK) {
                    e.getPlayer().sendMessage(NationUtility.nation("다른 국가의 영토와 너무 가깝습니다. 80칸이상 떨어져주세요. &7현재 " + distance + "칸"));
                } else {
                    e.getPlayer().sendMessage(NationUtility.nation("&6" + n.nation.get(i).nationName + " &f국가의 영토입니다."));
                }
                e.setCancelled(true);
                return;
            }
        }

        // 국가 성 설치
        if (e.getBlock().getType() == Material.BEACON) {
            if (nation != null && data.job != null && (data.job == UserData.Job.왕 || data.job == UserData.Job.부왕)) {
                if (nation.isWar()) {
                    e.getPlayer().sendMessage(NationUtility.nation("전쟁중엔 할 수 없습니다."));
                    return;
                }
                if (e.getPlayer().getWorld().getName().contains("뉴비")) {
                    e.getPlayer().sendMessage(NationUtility.nation("뉴비 야생에선 국가를 세울 수 없습니다."));
                    return;
                }
                if (nation.castles.size() < (nation.level + 1) / 2) {
                    if (nation.<CastleBuildTime>getTimerable(CastleBuildTime.class) != null)
                        nation.<CastleBuildTime>getTimerable(CastleBuildTime.class).stop();

                    // 월드에딧 로드
                    String url = Bukkit.getPluginManager().getPlugin("FourCore").getDataFolder().getAbsolutePath() + "\\sung.schematic";
                    WorldEditUtility.loadSchematic(e.getBlock().getLocation(), url);

                    // 국가에 성 정보 저장
                    Location loc = e.getBlock().getLocation().add(-17, 15, 0);
                    Block block = e.getBlock().getWorld().getBlockAt(loc);
                    if (block.getType() != Material.BEACON) {
                        System.out.println(ChatColor.RED + "성을 생성했지만 신호기가 없습니다.");
                    }
                    nation.castles.add(new BlockData(block));
                    e.getPlayer().sendMessage(NationUtility.nation("성공적으로 성을 건설했습니다."));
                    FourCore.log.info(nation.nationName + "국가가 (" + block.getX() + "," + block.getY() + "," + block.getZ() + ")에 성을 건설했습니다.");
                } else {
                    e.setCancelled(true);
                    e.getPlayer().sendMessage(NationUtility.nation("성의 갯수가 이미 최대치입니다."));
                }
            } else {
                e.setCancelled(true);
                e.getPlayer().sendMessage(NationUtility.nation("왕 또는 부왕만 성을 건설할 수 있습니다."));
            }
        }

        // 전초기지 설치
        if (e.getBlock().getType() == Material.LAPIS_BLOCK) {
            if (nation != null && data.job != null && (data.job == UserData.Job.왕 || data.job == UserData.Job.부왕)) {
                if (nation.isWar()) {
                    e.getPlayer().sendMessage(NationUtility.nation("전쟁중엔 할 수 없습니다."));
                    return;
                }
                if (nation.outposts.size() < (nation.level + 1) / 2) {
                    // 국가에 전초기지 정보 저장
                    //Location loc = e.getBlock().getLocation().add(0, 0, 0);
                    Block block = e.getBlock();//.getWorld().getBlockAt(loc);
                    if (block.getType() != Material.LAPIS_BLOCK) {
                        System.out.println(ChatColor.RED + "전초기지을 생성했지만 청금석블록이 없습니다.");
                    }
                    nation.outposts.add(new BlockData(block));
                    e.getPlayer().sendMessage(NationUtility.nation("성공적으로 전초기지을 건설했습니다."));
                    FourCore.log.info(nation.nationName + "국가가 (" + block.getX() + "," + block.getY() + "," + block.getZ() + ")에 전초기지를 건설했습니다.");
                } else {
                    e.setCancelled(true);
                    e.getPlayer().sendMessage(NationUtility.nation("전초기지의 갯수가 이미 최대치입니다."));
                }
            } else {
                e.setCancelled(true);
                e.getPlayer().sendMessage(NationUtility.nation("왕 또는 부왕만 성을 건설할 수 있습니다."));
            }
        }
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        UserData data = UserData.getUserData(p);

        String job = "";
        switch (data.job) {
            case 왕:
                job = "&e[왕] ";
                break;
            case 부왕:
                job = "&e[부왕] ";
                break;
            case 장군:
                job = "&e[장군] ";
                break;
            case 시민:
                job = "&7[시민] ";
                break;
            case 일반유저:
                job = "";
                break;
        }
        switch (data.role) {
            case 대표:
                job = "&c[대표] ";
                break;
            case 관리자:
                job = "&c[관리자] ";
                break;
            case 가이드:
                job = "&b[가이드] ";
                break;
            case 일반유저:
                break;
        }
        String nation = "";
        if (data.nation != null) {
            nation = "&6&o&l[" + data.nation + "]&o&l ";
        }
        e.setCancelled(true);
        String message = ChatColor.translateAlternateColorCodes('&',
                job + "&7[Lv." + data.level + "] " + nation + "&a" + p.getName() + " &f: " + e.getMessage());

        NationData nationData = data.getNation();

        switch (data.setting.chatMod) {
            case 일반:
                Bukkit.broadcastMessage(message);
                break;
            case 국가:
                if (nationData == null) {
                    p.sendMessage(ChatColor.RESET + "국가가 없어서 국가 채팅을 할 수 없습니다.");
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f채팅 모드가 &6[ 일반채팅 ] &f으로 변경되었습니다"));
                    data.setting.chatMod = UserSetting.ChatMod.일반;
                } else {
                    NationUtility.broadcastNation(data.nation, "&9[ 국가채팅 ] " + message);
                    System.out.println("[ 국가채팅 ] " + message);
                }
                break;
            case 동맹:
                if (nationData == null) {
                    p.sendMessage(ChatColor.RESET + "국가가 없어서 동맹 채팅을 할 수 없습니다.");
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f채팅 모드가 &6[ 일반채팅 ] &f으로 변경되었습니다"));
                    data.setting.chatMod = UserSetting.ChatMod.일반;
                } else {
                    NationUtility.broadcastAlliance(data.nation, "&b[ 동맹채팅 ] " + message);
                    System.out.println("[ 동맹채팅 ] " + message);
                }
                break;
            case 가이드:
                if (data.role == UserSetting.Role.대표 || data.role == UserSetting.Role.관리자) {
                    message = ChatColor.translateAlternateColorCodes('&',
                            "&c" + job + "" + p.getName() + " : " + e.getMessage());
                    Bukkit.broadcastMessage(message);
                } else if (data.role == UserSetting.Role.가이드) {
                    message = ChatColor.translateAlternateColorCodes('&',
                            job + "" + p.getName() + " : " + e.getMessage());
                    Bukkit.broadcastMessage(message);
                } else {
                    p.sendMessage(ChatColor.RESET + "일반 유저는 가이드 채팅을 하실 수 없습니다.");
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f채팅 모드가 &6[ 일반채팅 ] &f으로 변경되었습니다"));
                    data.setting.chatMod = UserSetting.ChatMod.일반;
                }
        }
    }

    // 개발중
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        Action action = e.getAction();
        Player p = e.getPlayer();
        UserData data = UserData.getUserData(p);
        NationData nation = data.getNation();
        NationBlock nb = NationUtility.getAllCastles(nation);

        // 아이템 밴
        if (e.getItem() != null && e.getItem().getType() == Material.LAVA_BUCKET) {
            e.setCancelled(true);
            p.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
            p.sendMessage(ChatColor.RED + "※ 서버에서 금지된 아이템입니다.");
            return;
        }

        // 무기 내구도 무한
        try {
            PlayerInventory inventory = p.getInventory();
            ItemStack hand = inventory.getItemInMainHand();
            if (hand != null && (hand.getType() == Material.SHEARS || hand.getType() == Material.IRON_SWORD || hand.getType() == Material.DIAMOND_SWORD || hand.getType() == Material.DIAMOND_SPADE || hand.getType() == Material.DIAMOND_PICKAXE || hand.getType() == Material.DIAMOND_HOE))
                hand.setDurability((short) 0);
        } catch (Exception exception) {
        }

        // 상자 잠금
        if (action == Action.RIGHT_CLICK_BLOCK && e.getClickedBlock().getType() == Material.CHEST) {
            //if()
        }

        /*
        // 로비에서 스킬 막기
        if (action == Action.RIGHT_CLICK_AIR && e.getItem().getType() == Material.GOLD_SWORD) {
            if (p.getWorld().getName().equalsIgnoreCase("world")) {
                p.sendMessage(ChatColor.RESET + "로비에서는 할 수 없습니다.");
                e.setCancelled(true);
            }
        }
         */

        // 신호기 채굴 피로도
        if (action == Action.LEFT_CLICK_BLOCK && e.getClickedBlock().getType() == Material.BEACON) {
            p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 1200, 0)); // 300
            /* 맨손아니여도 부술수있을때
            if (e.getItem() == null) {
                // p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 1200, 0)); // 300
            } else {

                //p.sendMessage(nation("신호기는 맨손으로만 부술 수 있습니다. &7※ 맨손이 아니면 부셔도 재생됩니다."));
                //e.setCancelled(true);
            }
            */
        }

        /* 맨손아니여도 부술수있을때
        // 전초기지
        if (action == Action.LEFT_CLICK_BLOCK && e.getClickedBlock().getType() == Material.LAPIS_BLOCK) {
            if (e.getItem() == null) {
            } else {
                p.sendMessage(nation("전초기지는 맨손으로만 부술 수 있습니다. &7※ 맨손이 아니면 부셔도 재생됩니다."));
                //e.setCancelled(true);
            }
        }
        */

        // 신호기 정보 안뜨게
        if (action == Action.RIGHT_CLICK_BLOCK && e.getClickedBlock().getType() == Material.BEACON) {
            e.setCancelled(true);
        }
        // 모루 정보 안뜨게
        if (action == Action.RIGHT_CLICK_BLOCK && e.getClickedBlock().getType() == Material.ANVIL) {
            e.setCancelled(true);
            p.sendMessage("모루는 이용하실 수 없습니다.");
        }
        // 제작대 정보 안뜨게
        if (action == Action.RIGHT_CLICK_BLOCK && e.getClickedBlock().getType() == Material.WORKBENCH) {
            e.setCancelled(true);
            p.sendMessage("제작대는 이용하실 수 없습니다.");
        }

        // 국가원 철문 (작동안하는듯)
        Block clicked = e.getClickedBlock();
        //NationData nation = NationData.getNationData(getUserData(p).nation);
        if (clicked != null && clicked.getType() == Material.IRON_DOOR_BLOCK) {
            boolean block = true;
            NationBlock n = NationUtility.getAllCastlesWithoutAllie(nation);
            int protect = 36;
            int x1 = clicked.getX(), z1 = clicked.getZ();
            for (int i = 0; i < n.block.size(); i++) {
                BlockData b = n.block.get(i);
                int x2 = b.x, z2 = b.z;
                double distance = Math.max(Math.abs(x1 - x2), Math.abs(z1 - z2));
                if (b.world.equalsIgnoreCase(e.getPlayer().getWorld().getName()) && distance <= protect) {
                    block = false;
                    break;
                }
            }
            if (block) {
                Door door = (Door) clicked.getState().getData();
                if (door.isTopHalf())
                    clicked = clicked.getRelative(BlockFace.DOWN);

                BlockState state = clicked.getState();
                MaterialData mData = state.getData();
                Openable opn = (Openable) mData; //Cast data to Openable
                if (opn.isOpen()) {
                    opn.setOpen(false);
                } else {
                    opn.setOpen(true);
                }
                state.setData(mData); // Add the data to the BlockState
                state.update();
            } else {
                p.sendMessage(ChatColor.RESET + "국가원만 열 수 있습니다.");
            }
            /*
                DoorLockMenu menu = new DoorLockMenu(PlayerMenuUtility.getPlayerMenuUtility(p));
                menu.door = clicked;
                menu.open();
                e.setCancelled(true);*/
        }

        // 양동이 금지
        if (e.getClickedBlock() != null && e.getItem() != null && (e.getItem().getType() == Material.BUCKET || e.getItem().getType() == Material.WATER_BUCKET || e.getItem().getType() == Material.LAVA_BUCKET)) {
            int protect = 36;
            int x1 = e.getClickedBlock().getX(), z1 = e.getClickedBlock().getZ();
            for (int i = 0; i < nb.block.size(); i++) {
                BlockData b = nb.block.get(i);
                int x2 = b.x, z2 = b.z;
                double distance = Math.max(Math.abs(x1 - x2), Math.abs(z1 - z2));
                if (b.world.equalsIgnoreCase(e.getPlayer().getWorld().getName()) && distance <= protect) {
                    p.sendMessage(NationUtility.nation("&6" + nb.nation.get(i).nationName + " &f국가의 영토입니다."));
                    e.setCancelled(true);
                    return;
                }
            }
        }

        // 철문 폭탄
        ItemStack current = e.getItem();
        if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (CustomItemUtility.equals(current, CustomItemUtility.IronDoorBombItem())) {
                if (e.getClickedBlock() != null && e.getClickedBlock().getType() == Material.IRON_DOOR_BLOCK) {
                    if (data.getNation().nationName.equalsIgnoreCase(WorldSetting.warSender.nationName) || (data.getNation().nationName.equalsIgnoreCase(WorldSetting.warReceiver.nationName) && WorldSetting.counterWar)) {
                        e.getItem().setAmount(e.getItem().getAmount() - 1);
                        e.getClickedBlock().getWorld().createExplosion(e.getClickedBlock().getLocation(), 0.0f, false);
                        e.getClickedBlock().setType(Material.AIR);
                        p.sendMessage(ChatColor.GOLD + "※ 철문을 파괴했습니다.");
                        FourCore.log.info(e.getPlayer().getName() + "님이 (" + e.getClickedBlock().getX() + ", " + e.getClickedBlock().getY() + ", " + e.getClickedBlock().getZ() + ")의 철문을 파괴했습니다.");
                        return;
                    } else {
                        p.sendMessage("전쟁 중의 공격측만 사용 가능합니다.");
                    }
                } else {
                    p.sendMessage("철문에만 사용 가능합니다.");
                }
            }
        }

        // 경험치 쿠폰 아이템 사용
        if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (CustomItemUtility.equals(current, CustomItemUtility.ExpBuffItem1h())) {
                if (data.<ExpBuff>getTimerable(ExpBuff.class) == null) {
                    current.setAmount(current.getAmount() - 1);
                    new ExpBuff(3600, UserData.getUserData(p));
                    p.sendMessage(ChatColor.WHITE + "버프가 적용되었습니다!");
                    p.playSound(p.getLocation(), Sound.ENTITY_CHICKEN_EGG, 1, 1);
                } else {
                    p.sendMessage(ChatColor.WHITE + "이미 버프가 적용중입니다!");
                }
            } else if (CustomItemUtility.equals(current, CustomItemUtility.ExpBuffItem15m())) {
                if (data.<ExpBuff>getTimerable(ExpBuff.class) == null) {
                    current.setAmount(current.getAmount() - 1);
                    new ExpBuff(900, UserData.getUserData(p));
                    p.sendMessage(ChatColor.WHITE + "버프가 적용되었습니다!");
                    p.playSound(p.getLocation(), Sound.ENTITY_CHICKEN_EGG, 1, 1);
                } else {
                    p.sendMessage(ChatColor.WHITE + "이미 버프가 적용중입니다!");
                }
                // SoulPieceItem
            } else if (CustomItemUtility.equals(current, CustomItemUtility.SoulPieceItem(1))) {
                if (FourCore.economy.getBalance(p) >= 1000000) {
                    if (RandomSystem.random.nextInt(5) == 0) {
                        p.sendMessage(ChatColor.RED + "영혼 제작 실패!");
                        FourCore.economy.withdrawPlayer(p, 1000000);
                        p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_DEATH, 1, 1);
                    } else {
                        p.sendMessage(ChatColor.GREEN + "영혼 제작 성공!");
                        FourCore.economy.withdrawPlayer(p, 1000000);
                        current.setAmount(current.getAmount() - 1);
                        p.getInventory().addItem(CustomItemUtility.SoulItem(1));
                        p.playSound(p.getLocation(), Sound.ENTITY_CHICKEN_EGG, 1, 1);
                    }
                } else {
                    p.sendMessage(ChatColor.RESET + "비용이 부족합니다. (100만원 필요)");
                }
                // 좌표탐지권
            } else if (CustomItemUtility.equals(current, CustomItemUtility.InitNationCoolTimeItem(1))) {
                RejoinNationCoolTime coolTime = data.<RejoinNationCoolTime>getTimerable(RejoinNationCoolTime.class);
                if (coolTime != null) {
                    coolTime.stop();
                    p.sendMessage(ChatColor.RESET + "국가 가입 쿨타임 초기화 완료.");
                    current.setAmount(current.getAmount() - 1);
                    p.playSound(p.getLocation(), Sound.ENTITY_CHICKEN_EGG, 1, 1);
                } else {
                    p.sendMessage(ChatColor.RESET + "이미 국가 가입을 할수 있습니다.");
                }
                // 좌표탐지권
            } else if (CustomItemUtility.equals(current, CustomItemUtility.DetectLocationItem(1))) {
                NationBlock nationBlock = NationUtility.getAllCastlesWithoutAllie(nation);
                int r = RandomSystem.random.nextInt(nationBlock.block.size());
                BlockData b = nationBlock.block.get(r);
                p.sendMessage(ChatColor.AQUA + "[ ??? 국가의 성 월드: " + b.world + ", 좌표: (" + b.x + ", " + b.y + ", " + b.z + ") ]");
                current.setAmount(current.getAmount() - 1);
                p.playSound(p.getLocation(), Sound.ENTITY_CHICKEN_EGG, 1, 1);
                // 초회복 특성
            } else if (CustomItemUtility.equals(current, CustomItemUtility.SuperHealthItem(1))) {
                if (data.<SuperHealthCoolTime>getTimerable(SuperHealthCoolTime.class) == null) {
                    new SuperHealthCoolTime(120, UserData.getUserData(p));
                    p.sendMessage(ChatColor.RED + "※ 회복!");
                    // UserUtility.addHealth(p, 28);
                    for (int i = 0; i < 14; i++) {
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                UserUtility.addHealth(p, 2);
                            }
                        }.runTaskLater(FourCore.instance, i * 4);
                    }
                    p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_BURP, 1, 1);
                } else {
                    p.sendMessage(ChatColor.GRAY + "※ 쿨타임입니다. " + data.<SuperHealthCoolTime>getTimerable(SuperHealthCoolTime.class).timer + "초");
                }
                // 힐링 포션
            } else if (CustomItemUtility.equals(current, CustomItemUtility.HealingPotionItem(1))) {
                if (p.getWorld().getName().contains("D")) {
                    if (data.<HealingPotionCoolTime>getTimerable(HealingPotionCoolTime.class) == null) {
                        new HealingPotionCoolTime(5, UserData.getUserData(p));
                        p.sendMessage(ChatColor.RED + "※ 회복!");
                        UserUtility.addHealth(p, 6);
                        current.setAmount(current.getAmount() - 1);
                        p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_BURP, 1, 1);
                    } else {
                        p.sendMessage(ChatColor.GRAY + "※ 쿨타임입니다.");
                    }
                } else {
                    p.sendMessage(ChatColor.GRAY + "※ 던전에서만 사용할 수 있습니다.");
                }
                // 돈 주머니 사용
            } else if (CustomItemUtility.equals(current, CustomItemUtility.MoneyBoxItem(1))) {
                int value = RandomSystem.random.nextInt(2);
                if (value == 0) {
                    FourCore.economy.depositPlayer(p, 50000);
                    p.sendMessage(ChatColor.YELLOW + "5만원 당첨!");
                } else {
                    FourCore.economy.depositPlayer(p, 100000);
                    p.sendMessage(ChatColor.YELLOW + "10만원 당첨!");
                }
                p.playSound(p.getLocation(), Sound.ENTITY_CHICKEN_EGG, 1, 1);
                current.setAmount(current.getAmount() - 1);
                e.setCancelled(true);
                // 네더의별 상자 사용
            } else if (CustomItemUtility.equals(current, CustomItemUtility.NetherStartBoxItem())) {
                int value = RandomSystem.random.nextInt(10);
                if (value < 2) {
                    p.getInventory().addItem(CustomItemUtility.NetherStartItem(5));
                    p.sendMessage(ChatColor.YELLOW + "네더의별 5개 당첨!");
                } else if (value < 5) {
                    p.getInventory().addItem(CustomItemUtility.NetherStartItem(4));
                    p.sendMessage(ChatColor.YELLOW + "네더의별 4개 당첨!");
                } else {
                    p.getInventory().addItem(CustomItemUtility.NetherStartItem(3));
                    p.sendMessage(ChatColor.YELLOW + "네더의별 3개 당첨!");
                }
                p.playSound(p.getLocation(), Sound.ENTITY_CHICKEN_EGG, 1, 1);
                current.setAmount(current.getAmount() - 1);
                e.setCancelled(true);
                // 네더의별 상자 사용
            } else if (CustomItemUtility.equals(current, CustomItemUtility.NetherStartBoxItemNone())) {
                int value = RandomSystem.random.nextInt(10);
                if (value < 2) {
                    p.getInventory().addItem(CustomItemUtility.NetherStartItemNone(5));
                    p.sendMessage(ChatColor.YELLOW + "네더의별 5개 당첨!");
                } else if (value < 5) {
                    p.getInventory().addItem(CustomItemUtility.NetherStartItemNone(4));
                    p.sendMessage(ChatColor.YELLOW + "네더의별 4개 당첨!");
                } else {
                    p.getInventory().addItem(CustomItemUtility.NetherStartItemNone(3));
                    p.sendMessage(ChatColor.YELLOW + "네더의별 3개 당첨!");
                }
                p.playSound(p.getLocation(), Sound.ENTITY_CHICKEN_EGG, 1, 1);
                current.setAmount(current.getAmount() - 1);
                e.setCancelled(true);
            }
        }
    }
}
