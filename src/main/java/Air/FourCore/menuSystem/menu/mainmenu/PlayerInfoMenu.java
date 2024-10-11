package Air.FourCore.menuSystem.menu.mainmenu;

import Air.FourCore.menuSystem.Menu;
import Air.FourCore.menuSystem.PlayerMenuUtility;
import Air.FourCore.nation.NationData;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import Air.FourCore.task.buff.ExpBuff;
import Air.FourCore.FourCore;
import Air.FourCore.user.BagData;
import Air.FourCore.user.UserData;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static Air.FourCore.menuSystem.MenuUtility.*;

public class PlayerInfoMenu extends Menu {

    public PlayerInfoMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }
    public OfflinePlayer target;

    @Override
    public String getMenuName() {
        return ChatColor.BOLD+"[ "+target.getName()+"플레이어 정보 ]";
    }

    @Override
    public int getSlots() {
        return 27;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        if(e.getClickedInventory().getType() == InventoryType.PLAYER) return;
        Player p = playerMenuUtility.getOwner();
        Material material = e.getCurrentItem().getType();
        if (material == Material.BARRIER) {
            new MainMenu(PlayerMenuUtility.getPlayerMenuUtility(playerMenuUtility.getOwner())).open();
        }
        if(p.isOp()){
            if (material == Material.PURPLE_SHULKER_BOX) {
                if(target.isOnline())
                    p.openInventory(target.getPlayer().getInventory());
                else
                    p.sendMessage("오프라인 플레이어는 볼 수 없습니다.");
            }else if (material == Material.ENDER_CHEST) {
                if(target.isOnline())
                    p.openInventory(target.getPlayer().getEnderChest());
                else
                    p.sendMessage("오프라인 플레이어는 볼 수 없습니다.");
            }else if (material == Material.CHEST) {
                p.openInventory(BagData.getInventory(target.getUniqueId()));
            }
        }
    }

    @Override
    public void setInventory() {

        if(target == null)
            return;

        UserData data = UserData.getUserData(target);

        List<String> 정보 = Arrays.asList(
                "",
                color("레벨: ", "&a") + data.level,
                color("경험치 : ", "&a") + (int)data.exp + ChatColor.GOLD + " / " + ChatColor.RESET + (int)data.getEXPGoal() + ChatColor.GRAY +" (" + String.format("%.2f",data.exp/data.getEXPGoal()*100) + "%)",
                color("소지금: ", "&e") + String.format("%,d",(long) FourCore.economy.getBalance(target)) + ChatColor.YELLOW + "G",
                color("캐시: ", "&b") + String.format("%,d",(long)data.cash) + "원",
                color("근력: ") + data.strength,
                color("무력: ") + data.force,
                color("방어력: ") + data.defense,
                color("정신력: ") + data.mental
        );
        button(inventory,Material.ENDER_PEARL, ChatColor.YELLOW+target.getName()+ChatColor.RESET+" 님의 정보", 4, 정보);

        if(target.isOnline()) {
            setEquipment(target.getPlayer().getInventory().getHelmet(), 10);
            setEquipment(target.getPlayer().getInventory().getChestplate(), 11);
            setEquipment(target.getPlayer().getInventory().getLeggings(), 12);
            setEquipment(target.getPlayer().getInventory().getBoots(), 13);
            setEquipment(target.getPlayer().getInventory().getItemInMainHand(), 14);
        }

        List<String> 국가 = new ArrayList<>();

        국가.add("");
        국가.add(color("국가: ") + (data.nation==null?"없음":data.nation));
        국가.add(color("직위: ") + data.job);
        NationData nation = data.getNation();
        if(nation != null) {
            국가.add(color("국가레벨: ") + nation.level);
            국가.add(color("국가인원: ") + (nation.citizens.size() + nation.generals.size() + 1 + (nation.viceroy == null ? 0 : 1)));
        }
        button(inventory,Material.COMPASS, ChatColor.GREEN+" ▶ COUNTRY", 15, 국가);


        DateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String playTime = "";
        if(target.isOnline()) {
            long sec = Math.floorDiv(target.getPlayer().getStatistic(Statistic.PLAY_ONE_TICK), 20);
            int hour = (int) (sec * 0.00027777777777777777777777777777);                // 1/3600
            int min = (int) (sec * 0.0166666666666666666666666666666 - (hour * 60));    // 1/60
            playTime = color("총 플레이 시간: ") + hour + "시간 " + min + "분";
        }

        List<String> 시간 = Arrays.asList(
                "",
                color("마지막 접속: ") + format.format(new Date(Bukkit.getServer().getOfflinePlayer(target.getUniqueId()).getLastPlayed())),
                color("플레이어 상태: ") + ((target.isOnline())? ChatColor.GREEN+"Online" : ChatColor.RED+"Offline"),
                playTime
        );
        button(inventory,Material.BEACON, ChatColor.GREEN+" ▶ PLAY TIME", 16, 시간);

        ExpBuff buff = data.<ExpBuff>getTimerable(ExpBuff.class);
        List<String> 상태 = new ArrayList<>();
        상태.add(color("경고 : &f"+data.warning+"회 &7※ 10회 밴"));
        if(buff != null){
            int min2 = (int) (buff.timer * 0.0166666666666666666666666666666);    // 1/60
            long sec2 = buff.timer - (min2 * 60);
            상태.add(color("경험치 x1.5 / ")+min2+"분 "+sec2+"초");
        }
        button(inventory,Material.EXP_BOTTLE, ChatColor.GREEN+" ▶ 상태", 22, 상태);


        if(playerMenuUtility.getOwner().isOp()) {
            button(inventory, Material.PURPLE_SHULKER_BOX, ChatColor.WHITE + "[ 인벤토리 ] (op전용)", 18);
            button(inventory, Material.ENDER_CHEST, ChatColor.WHITE + "[ 엔더상자 ] (op전용)", 19);
            button(inventory, Material.CHEST, ChatColor.WHITE + "[ 가방 ] (op전용)", 20);
        }

        button(inventory,Material.BARRIER, ChatColor.RED+"[ 뒤로 ]", 26);

        background(inventory, 9);
        background(inventory, 17);
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

    private void setEquipment(ItemStack item, int i) {
        //ItemStack mat = item;
        //if (mat == null) mat = new ItemStack(Material.AIR, 1);
        inventory.setItem(i, item);
    }

    public String color(String str, String add){
        return ChatColor.translateAlternateColorCodes('&',"&r"+add+str+"&r");
    }

    public String color(String str){
        return  color(str, "&6");
    }

}
