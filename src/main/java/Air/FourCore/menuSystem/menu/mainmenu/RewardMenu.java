package Air.FourCore.menuSystem.menu.mainmenu;

import Air.FourCore.item.CustomItemUtility;
import Air.FourCore.menuSystem.Menu;
import Air.FourCore.menuSystem.PlayerMenuUtility;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import Air.FourCore.user.UserData;

import java.util.ArrayList;
import java.util.List;

import static Air.FourCore.menuSystem.MenuUtility.*;

public class RewardMenu extends Menu {

    public RewardMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        return ChatColor.BOLD + "[ 접속 보상 ]";
    }

    @Override
    public int getSlots() {
        return 27;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        if(e.getClickedInventory().getType() == InventoryType.PLAYER) return;
        HumanEntity h = e.getWhoClicked();
        Player p = Bukkit.getPlayer(h.getUniqueId());
        UserData data = UserData.getUserData(p);

        switch (e.getCurrentItem().getType()) {
            case HOPPER_MINECART:
                if (!data.setting.isRewarded_1h) {
                    if (data.setting.dailyPlayTime >= 3600) {
                        data.setting.isRewarded_1h = true;
                        p.getInventory().addItem(CustomItemUtility.NetherStartItemNone(1));
                        p.sendMessage(ChatColor.RESET + "접속보상: 1 네더의 별");
                        p.playSound(p.getLocation(), Sound.ENTITY_CHICKEN_EGG, 1, 1);
                        setInventory();
                    } else {
                        p.sendMessage(ChatColor.RESET + "접속 시간이 부족합니다.");
                    }
                } else {
                    p.sendMessage(ChatColor.RESET + "이미 보상을 수령하셨습니다. 매일 밤 12시에 초기화 됩니다.");
                }
                break;
            case POWERED_MINECART:
                if (!data.setting.isRewarded_2h) {
                    if (data.setting.dailyPlayTime >= 2 * 3600) {
                        data.setting.isRewarded_2h = true;
                        p.getInventory().addItem(CustomItemUtility.NetherStartItemNone(1));
                        p.sendMessage(ChatColor.RESET + "접속보상: 1 네더의 별");
                        p.playSound(p.getLocation(), Sound.ENTITY_CHICKEN_EGG, 1, 1);
                        setInventory();
                    } else {
                        p.sendMessage(ChatColor.RESET + "접속 시간이 부족합니다.");
                    }
                } else {
                    p.sendMessage(ChatColor.RESET + "이미 보상을 수령하셨습니다. 매일 밤 12시에 초기화 됩니다.");
                }
                break;
            case COMMAND_MINECART:
                if (!data.setting.isRewarded_3h) {
                    if (data.setting.dailyPlayTime >= 3 * 3600) {
                        data.setting.isRewarded_3h = true;
                        p.getInventory().addItem(CustomItemUtility.NetherStartItemNone(1));
                        p.sendMessage(ChatColor.RESET + "접속보상: 1 네더의 별");
                        p.playSound(p.getLocation(), Sound.ENTITY_CHICKEN_EGG, 1, 1);
                        setInventory();
                    } else {
                        p.sendMessage(ChatColor.RESET + "접속 시간이 부족합니다.");
                    }
                } else {
                    p.sendMessage(ChatColor.RESET + "이미 보상을 수령하셨습니다. 매일 밤 12시에 초기화 됩니다.");
                }
                break;
            case EXPLOSIVE_MINECART:
                if (data.job != UserData.Job.왕 && data.job != UserData.Job.부왕) {
                    p.sendMessage(ChatColor.RESET + "왕 또는 부왕만 수령할 수 있습니다.");
                    break;
                }
                if (!data.setting.isRewarded_4h) {
                    if (data.setting.dailyPlayTime >= 4 * 3600) {
                        data.setting.isRewarded_4h = true;
                        p.getInventory().addItem(CustomItemUtility.NetherStartItemNone(1));
                        p.sendMessage(ChatColor.RESET + "접속보상: 1 네더의 별");
                        p.playSound(p.getLocation(), Sound.ENTITY_CHICKEN_EGG, 1, 1);
                        setInventory();
                    } else {
                        p.sendMessage(ChatColor.RESET + "접속 시간이 부족합니다.");
                    }
                } else {
                    p.sendMessage(ChatColor.RESET + "이미 보상을 수령하셨습니다. 매일 밤 12시에 초기화 됩니다.");
                }
                break;
            case BARRIER:
                new MainMenu(PlayerMenuUtility.getPlayerMenuUtility(playerMenuUtility.getOwner())).open();
                break;
        }
    }

    @Override
    public void setInventory() {
        UserData data = UserData.getUserData(playerMenuUtility.getOwner());
        List<String> 시간 = new ArrayList<>();
        int time = data.setting.dailyPlayTime;
        int hour = (int) (time * 0.00027777777777777777777777777777);                // 1/3600
        int min = (int) (time * 0.0166666666666666666666666666666 - hour * 60);    // 1/60
        long sec = time - (min * 60L) - (hour * 3600L);
        시간.add(color(hour + "시간 " + min + "분 " + sec + "초"));
        시간.add(color("&7※ 접속 시간은 매일 밤 12시에 초기화 됩니다."));
        button(inventory, Material.ENDER_PEARL, ChatColor.GREEN + "[ 접속시간 ]", 4, 시간);
        List<String> time1 = new ArrayList<>();
        if (data.setting.isRewarded_1h)
            time1.add(color("&7- 수령 완료 -"));
        button(inventory, Material.HOPPER_MINECART, ChatColor.YELLOW + "[ 1시간 보상 ]", 10, time1);
        List<String> time2 = new ArrayList<>();
        if (data.setting.isRewarded_2h)
            time2.add(color("&7- 수령 완료 -"));
        button(inventory, Material.POWERED_MINECART, ChatColor.YELLOW + "[ 2시간 보상 ]", 12, time2);
        List<String> time3 = new ArrayList<>();
        if (data.setting.isRewarded_3h)
            time3.add(color("&7- 수령 완료 -"));
        button(inventory, Material.COMMAND_MINECART, ChatColor.YELLOW + "[ 3시간 보상 ]", 14, time3);
        List<String> time4 = new ArrayList<>();
        time4.add(color("&6※ 왕 또는 부왕만 수령할 수 있습니다."));
        if (data.setting.isRewarded_4h)
            time4.add(color("&7- 수령 완료 -"));
        button(inventory, Material.EXPLOSIVE_MINECART, ChatColor.YELLOW + "[ 4시간 보상 ]", 16, time4);

        button(inventory, Material.BARRIER, ChatColor.RED + "[ 뒤로 ]", 26);

        fillLineBackground(inventory, 0, false);
        fillLineBackground(inventory, 2, false);
        /*
        new BukkitRunnable() {
            @Override
            public void run() {
                setInventory();
            }
        }.runTaskLater(FourCore.instance, 20);
         */
    }
}
