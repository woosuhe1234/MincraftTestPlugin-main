package Air.FourCore.menuSystem.menu.submenu;

import Air.FourCore.menuSystem.menu.mainmenu.WarpMenu;
import Air.FourCore.menuSystem.Menu;
import Air.FourCore.menuSystem.MenuUtility;
import Air.FourCore.menuSystem.PlayerMenuUtility;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import Air.FourCore.user.UserUtility;

import static Air.FourCore.menuSystem.MenuUtility.button;
import static Air.FourCore.menuSystem.MenuUtility.fillLineBackground;

public class MineChannelMenu extends Menu {

    public MineChannelMenu(PlayerMenuUtility playerMenuUtility) {
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
                Location loc = MenuUtility.getLocation("광산-"+(e.getSlot() - 11));
                if(loc != null)
                    UserUtility.teleport((Player) e.getWhoClicked(), loc);
                break;
            case BARRIER:
                new WarpMenu(PlayerMenuUtility.getPlayerMenuUtility(playerMenuUtility.getOwner())).open();
                break;
        }
    }

    @Override
    public void setInventory() {
        button(inventory,Material.SLIME_BALL, ChatColor.DARK_GREEN+"[ 채널1 ]", 12);
        button(inventory,Material.SLIME_BALL, ChatColor.DARK_GREEN+"[ 채널2 ]", 13);
        //button(inventory,Material.SLIME_BALL, ChatColor.DARK_GREEN+"[ 채널3 ]", 15);

        button(inventory,Material.BARRIER, ChatColor.RED+"[ 뒤로 ]", 26);

        fillLineBackground(inventory, 0, false);
        fillLineBackground(inventory, 2, false);
    }
}
