package Air.FourCore.menuSystem.menu.submenu;

import Air.FourCore.menuSystem.menu.mainmenu.WarpMenu;
import Air.FourCore.menuSystem.Menu;
import Air.FourCore.menuSystem.PlayerMenuUtility;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;

import static Air.FourCore.menuSystem.MenuUtility.button;
import static Air.FourCore.menuSystem.MenuUtility.fillLineBackground;

public class PVPChannelMenu extends Menu {

    public PVPChannelMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        return ChatColor.BOLD+"[ 채널 ]";
    }

    @Override
    public int getSlots() {
        return 27;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        if(e.getClickedInventory().getType() == InventoryType.PLAYER) return;
        switch (e.getCurrentItem().getType()){
            case EYE_OF_ENDER:
            case SLIME_BALL:
                break;
            case BARRIER:
                new WarpMenu(PlayerMenuUtility.getPlayerMenuUtility(playerMenuUtility.getOwner())).open();
                break;
        }
    }

    @Override
    public void setInventory() {
        button(inventory, Material.EYE_OF_ENDER, ChatColor.RED+"[ 메인 채널 ]", 11);
        button(inventory,Material.SLIME_BALL, ChatColor.DARK_GREEN+"[ 채널1 ]", 13);
        button(inventory,Material.SLIME_BALL, ChatColor.DARK_GREEN+"[ 채널2 ]", 14);
        button(inventory,Material.SLIME_BALL, ChatColor.DARK_GREEN+"[ 채널3 ]", 15);

        button(inventory,Material.BARRIER, ChatColor.RED+"[ 뒤로 ]", 26);

        fillLineBackground(inventory, 0, false);
        fillLineBackground(inventory, 2, false);
    }
}
