package Air.FourCore.menuSystem.menu.mainmenu;

import Air.FourCore.menuSystem.Menu;
import Air.FourCore.menuSystem.PlayerMenuUtility;
import Air.FourCore.menuSystem.menu.submenu.DungeonChannelMenu;
import Air.FourCore.menuSystem.menu.submenu.FishingChannelMenu;
import Air.FourCore.menuSystem.menu.submenu.MineChannelMenu;
import Air.FourCore.menuSystem.menu.submenu.PVPChannelMenu;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import Air.FourCore.user.UserData;

import static Air.FourCore.menuSystem.MenuUtility.button;
import static Air.FourCore.menuSystem.MenuUtility.fillBackground;

public class EnderWarpMenu extends Menu {

    public EnderWarpMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        return ChatColor.BOLD+"[ 워프 존 ]";
    }

    @Override
    public int getSlots() {
        return 27;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        if(e.getClickedInventory().getType() == InventoryType.PLAYER) return;

        switch (e.getCurrentItem().getType()){
            case STONE_SWORD:
                new DungeonChannelMenu(PlayerMenuUtility.getPlayerMenuUtility(playerMenuUtility.getOwner())).open();
                break;
            case BOW:
                new PVPChannelMenu(PlayerMenuUtility.getPlayerMenuUtility(playerMenuUtility.getOwner())).open();
                break;
            case EMERALD:
                new MineChannelMenu(PlayerMenuUtility.getPlayerMenuUtility(playerMenuUtility.getOwner())).open();
                //new MineChannelMenu(PlayerMenuUtility.getPlayerMenuUtility(playerMenuUtility.getOwner())).open();
                break;
            case FISHING_ROD:
                new FishingChannelMenu(PlayerMenuUtility.getPlayerMenuUtility(playerMenuUtility.getOwner())).open();
                break;
        }
    }

    @Override
    public void setInventory() {
        UserData data = UserData.getUserData(playerMenuUtility.getOwner());

        button(inventory, Material.STONE_SWORD, ChatColor.DARK_RED+"[ 던전 ]", 10);
        button(inventory,Material.BOW, ChatColor.RED+"[ PVP ]", 12);
        button(inventory,Material.EMERALD, ChatColor.GREEN+"[ 광산 ]", 14);
        button(inventory,Material.FISHING_ROD, ChatColor.AQUA+"[ 낚시터 ]", 16);

        fillBackground(inventory);
    }
}
