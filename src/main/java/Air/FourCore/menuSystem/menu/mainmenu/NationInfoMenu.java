package Air.FourCore.menuSystem.menu.mainmenu;

import Air.FourCore.menuSystem.Menu;
import Air.FourCore.menuSystem.PlayerMenuUtility;
import Air.FourCore.menuSystem.menu.etcmenu.NationListMenu;
import Air.FourCore.nation.BlockData;
import Air.FourCore.nation.NationData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import Air.FourCore.user.UserData;
import Air.FourCore.user.UserUtility;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static Air.FourCore.menuSystem.MenuUtility.*;

public class NationInfoMenu extends Menu {

    public NationInfoMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }
    public NationData target;

    @Override
    public String getMenuName() {
        return ChatColor.BOLD+"[ "+target.nationName+" 국가 정보 ]";
    }

    @Override
    public int getSlots() {
        return 54;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        if(e.getClickedInventory().getType() == InventoryType.PLAYER) return;
        Player p = playerMenuUtility.getOwner();
        Material material = e.getCurrentItem().getType();
        if (material == Material.BARRIER) {
            new NationListMenu(PlayerMenuUtility.getPlayerMenuUtility(playerMenuUtility.getOwner())).open();
        }else if(e.getSlot() == 5 && target.allies.size() > 0){
            NationInfoMenu menu = new NationInfoMenu(PlayerMenuUtility.getPlayerMenuUtility(p));
            menu.target = NationData.getNationData(target.allies.get(0));
            menu.open();
        }
        int i = 11;
        for(UserData d : target.getAllPlayers()){
            try {
                if (e.getSlot() == i) {
                    PlayerInfoMenu menu = new PlayerInfoMenu(PlayerMenuUtility.getPlayerMenuUtility(p));
                    menu.target = Bukkit.getOfflinePlayer(d.uuid);
                    menu.open();
                }
            }catch (Exception exception){}
            i++;
            if(i == 16) i = 20;
        }
    }

    @Override
    public void setInventory() {
        if(target == null)
            return;

        Player p = playerMenuUtility.getOwner();
        UserData data = UserData.getUserData(p);
        boolean auth = p.isOp() || (data.nation != null && data.nation.equalsIgnoreCase(target.nationName));

        List<String> 상태 = new ArrayList<>();
        if(target.isWar()) 상태.add(ChatColor.RED + "◈ 전쟁중 ◈");
        button(inventory,Material.EXP_BOTTLE, ChatColor.GREEN + "▶ 상태", 3, 상태);

        List<String> 정보 = Arrays.asList(
                "",
                color("레벨: ", "&a") + target.level,
                color("금고: ", "&e") + (auth ? String.format("%,d",(long)target.money) + ChatColor.YELLOW + "G" +ChatColor.RED+" (외부 비공개)" : ChatColor.RED + "비공개"),
                color("점수: ") + target.score,
                color("국가 PVP: ") + (target.setting.nationPVP ? "허용" : "비허용"),
                color("동맹 PVP: ") + (target.setting.alliancePVP ? "허용" : "비허용")
        );
        button(inventory,Material.ENDER_PEARL, ChatColor.YELLOW+target.nationName+ChatColor.RESET+" 국가의 정보", 4, 정보);

        if(target.allies.size() > 0) {
            button(inventory, Material.BEACON, ChatColor.GOLD + target.allies.get(0) + " 동맹국", 5);
        }

        int i = 11;
        for (UserData d : target.getAllPlayers()){
            String job = "";
            if(d.job == UserData.Job.왕){
                job = ChatColor.RED + "[국왕] ";
            }else if(d.job == UserData.Job.부왕){
                job = ChatColor.YELLOW + "[부왕] ";
            }else if(d.job == UserData.Job.장군){
                job = ChatColor.YELLOW + "[장군] ";
            }else if(d.job == UserData.Job.시민){
                job = ChatColor.WHITE + "[시민] ";
            }
            try {
                button(inventory, new ItemStack(Material.SKULL_ITEM, 1, (d.isOnline() ? (short) 3 : (short) 0)), job + UserUtility.playerToString(Bukkit.getOfflinePlayer(d.uuid)), i);
            }catch (Exception exception){}

            i++;
            if(i == 16) i = 20;
        }

        i = 37;
        for (BlockData d : target.castles){
            List<String> 좌표 = Arrays.asList(
                    ChatColor.GOLD+"좌표: "+(auth? ChatColor.WHITE+d.world+"("+d.x+", "+d.y+", "+d.z+") "+ChatColor.RED+"(외부 비공개)": ChatColor.RED+"비공개")
            );
            button(inventory, Material.BEACON, ChatColor.RESET+""+(i-36)+"번 성", i, 좌표);
            i++;
        }
        i = 41;
        for (BlockData d : target.outposts){
            List<String> 좌표 = Arrays.asList(
                    ChatColor.GOLD+"좌표: "+(auth? ChatColor.WHITE+d.world+"("+d.x+", "+d.y+", "+d.z+") "+ChatColor.RED+"(외부 비공개)": ChatColor.RED+"비공개")
            );
            button(inventory, Material.LAPIS_BLOCK, ChatColor.RESET+""+(i-40)+"번 전초기지", i, 좌표);
            i++;
        }

        /*
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
            국가.add(color("국가인원: ") + (nation.citizens.size() + 1 + (nation.viceroy == null ? 0 : 1)));
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
         */

        button(inventory,Material.BARRIER, ChatColor.RED+"[ 뒤로 ]", 53);

        background(inventory, 1);
        background(inventory, 2);
        background(inventory, 6);
        background(inventory, 7);
        background(inventory, 10);
        background(inventory, 16);
        background(inventory, 19);
        background(inventory, 25);
        background(inventory, 40);
        fillLineBackground(inventory, 3, false);
        fillLineBackground(inventory, 5, false);
        fillLineBackground(inventory, 0, true);
        fillLineBackground(inventory, 8, true);

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
