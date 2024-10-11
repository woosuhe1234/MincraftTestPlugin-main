package Air.FourCore.npc;

import Air.FourCore.menuSystem.MenuUtility;
import Air.FourCore.menuSystem.PlayerMenuUtility;
import Air.FourCore.menuSystem.menu.mainmenu.BossWarpMenu;
import Air.FourCore.menuSystem.menu.shopmenu.*;
import Air.FourCore.menuSystem.menu.shopmenu.specialshopmenu.*;
import Air.FourCore.npc.quest.*;
import menuSystem.menu.shopmenu.*;
import menuSystem.menu.shopmenu.specialshopmenu.*;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import npc.quest.*;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import Air.FourCore.user.UserData;

public class NPCListener implements Listener {


    //개발중
    @EventHandler
    public void onInteractNPC(PlayerInteractEntityEvent e) {
        /*
        if (e.getRightClicked() instanceof NPC) {
            NPC he = (NPC) e.getRightClicked();
            if(he.getName().contains("회복 요정")){
                p.sendTitle(ChatColor.RED+"회복되었습니다","",5,20,20);
                p.setHealth(p.getMaxHealth());
            }else if(he.getName().equalsIgnoreCase("&c[&cH&c] &f회복 요정")){
                new StoreMenu(PlayerMenuUtility.getPlayerMenuUtility(p)).open();
            }
        }*/
    }

    @EventHandler
    public void onRightClick(NPCRightClickEvent event){
        net.citizensnpcs.api.npc.NPC npc = event.getNPC();
        Player p = event.getClicker();
        UserData data = UserData.getUserData(p);
        PlayerMenuUtility util = PlayerMenuUtility.getPlayerMenuUtility(p);
        if (npc.getName().contains("회복 요정")){
            p.sendTitle(ChatColor.RED+"회복되었습니다","",5,20,20);
            p.setHealth(p.getMaxHealth());
        }else if(npc.getName().contains("네더무기상점")) {
            new NetherWeaponShopMenu(util).open();
        }else if(npc.getName().contains("홍련의무기상점")) {
            new HongWeaponShopMenu(util).open();
        }else if(npc.getName().contains("네더기타상점")){
            new NetherETCShopMenu(util).open();
        }else if(npc.getName().contains("네더의별구매상점")){
            new NetherStarShopMenu(util).open();
        }else if(npc.getName().contains("갑옷상점")){
            new ArmorShopMenu(util).open();
        }else if(npc.getName().contains("국가상점")){
            new NationShopMenu(util).open();
        }else if(npc.getName().contains("블럭상점")){
            new BlockShopMenu(util).open();
        }else if(npc.getName().contains("도구상점")){
            new ToolShopMenu(util).open();
        }else if(npc.getName().contains("농사상점")){
            new FarmShopMenu(util).open();
        }else if(npc.getName().contains("광물상점")){
            new MineralShopMenu(util).open();
        }else if(npc.getName().contains("캐시상점")){
            new CashShopMenu(util).open();
        }else if(npc.getName().contains("잠수상점")){
            new AFKPointShopMenu(util).open();
        }else if(npc.getName().contains("던전상점")){
            new DungeonShopMenu(util).open();
        }else if(npc.getName().contains("2차던전무기상점")){
            new SecondDungeonWeaponShopMenu(util).open();
        }else if(npc.getName().contains("던전무기상점")){
            new DungeonWeaponShopMenu(util).open();
        }else if(npc.getName().contains("무기상점")){
            new WeaponShopMenu(util).open();
        }else if(npc.getName().contains("1던전퀘스트")){
            new DungeonQuest1(data);
        }else if(npc.getName().contains("2던전퀘스트")){
            new DungeonQuest2(data);
        }else if(npc.getName().contains("3던전퀘스트")){
            new DungeonQuest3(data);
        }else if(npc.getName().contains("4던전퀘스트")){
            new DungeonQuest4(data);
        }else if(npc.getName().contains("5던전퀘스트")){
            new DungeonQuest5(data);
        }else if(npc.getName().contains("6던전퀘스트")){
            new DungeonQuest6(data);
        }else if(npc.getName().contains("7던전퀘스트")){
            new DungeonQuest7(data);
        }else if(npc.getName().contains("8던전퀘스트")){
            new DungeonQuest8(data);
        }else if(npc.getName().contains("6던전반복퀘")){
            new DungeonRepeatQuest6(data);
        }else if(npc.getName().contains("7던전반복퀘")){
            new DungeonRepeatQuest7(data);
        }else if(npc.getName().contains("8던전반복퀘")){
            new DungeonRepeatQuest8(data);
        }else if(npc.getName().contains("요르문간드")){
            new YoWeaponShopMenu(util).open();
        }else if(npc.getName().contains("파프니르")){
            new PaWeaponShopMenu(util).open();
        }else if(npc.getName().contains("펜리르")){
            new FenWeaponShopMenu(util).open();
        }else if(npc.getName().contains("리바이어던")){
            new ReWeaponShopMenu(util).open();
        }else if(npc.getName().contains("요정")){
            new BossWarpMenu(util).open();
        }else if(npc.getName().contains("어둠의NPC")){
            Location loc = MenuUtility.getLocation("어둠의상점");
            if(loc != null)
                p.teleport(loc);
        }else if(npc.getName().contains("어둠의상점")){
            new FarmShopMenu(util).open();
        }
    }
}
