package Air.FourCore.menuSystem.menu.mainmenu;

import Air.FourCore.menuSystem.Menu;
import Air.FourCore.menuSystem.PlayerMenuUtility;
import Air.FourCore.nation.NationData;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import Air.FourCore.task.userRequest.WarpTime;
import Air.FourCore.user.UserData;

import static Air.FourCore.menuSystem.MenuUtility.*;

public class NationWarpMenu extends Menu {

    public NationWarpMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        return ChatColor.BOLD+"[ 국가 워프 ]";
    }

    @Override
    public int getSlots() {
        return 27;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        if(e.getClickedInventory().getType() == InventoryType.PLAYER) return;
        UserData data = UserData.getUserData(playerMenuUtility.getOwner());
        NationData nation = data.getNation();
        Location loc;
        switch (e.getCurrentItem().getType()){
            case BEACON:
                if(nation == null) break;
                loc = nation.castles.get(e.getSlot() - 10).getBlock().getLocation();
                if(loc == null) break;
                loc.add(-9.5, -15 , 0.5);
                //playerMenuUtility.getOwner().teleport(loc);
                new WarpTime(5, data, loc);
                playerMenuUtility.getOwner().closeInventory();
                break;
            case BEDROCK:
                if(nation == null) break;
                loc = nation.outposts.get(e.getSlot() - 14).getBlock().getLocation();
                if(loc == null) break;
                loc.add(0.5, 1.0 , 0.5);
                //playerMenuUtility.getOwner().teleport(loc);
                new WarpTime(5, data, loc);
                playerMenuUtility.getOwner().closeInventory();
                break;
            case BARRIER:
                new MainMenu(PlayerMenuUtility.getPlayerMenuUtility(playerMenuUtility.getOwner())).open();
                break;
        }
    }

    @Override
    public void setInventory() {
        NationData nation = NationData.getNationData(UserData.getUserData(playerMenuUtility.getOwner()).nation);
        if(nation != null) {
            if(nation.castles.size() > 0)
                button(inventory, Material.BEACON, ChatColor.YELLOW + "[ 1번 성 ]", 10);
            if(nation.castles.size() > 1)
                button(inventory, Material.BEACON, ChatColor.YELLOW + "[ 2번 성 ]", 11);
            if(nation.castles.size() > 2)
                button(inventory, Material.BEACON, ChatColor.YELLOW + "[ 3번 성 ]", 12);
            if(nation.outposts.size() > 0)
                button(inventory, Material.BEDROCK, ChatColor.GOLD + "[ 1번 전초기지 ]", 14);
            if(nation.outposts.size() > 1)
                button(inventory, Material.BEDROCK, ChatColor.GOLD + "[ 2번 전초기지 ]", 15);
            if(nation.outposts.size() > 2)
                button(inventory, Material.BEDROCK, ChatColor.GOLD + "[ 3번 전초기지 ]", 16);
        }else{

        }

        button(inventory, Material.BARRIER, ChatColor.RED+"[ 뒤로 ]", 26);

        fillLineBackground(inventory, 0, false);
        fillLineBackground(inventory, 2, false);
        background(inventory,9);
        background(inventory,13);
        background(inventory,17);
    }

}
