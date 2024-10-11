package Air.FourCore.menuSystem.menu.mainmenu;

import Air.FourCore.menuSystem.Menu;
import Air.FourCore.menuSystem.PlayerMenuUtility;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import Air.FourCore.user.UserData;

import static Air.FourCore.menuSystem.MenuUtility.*;

public class RuneEquipMenu extends Menu {

    public RuneEquipMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        return ChatColor.BOLD + "[ 룬 장착 ]";
    }

    @Override
    public int getSlots() {
        return 27;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        if(e.getClickedInventory().getType() == InventoryType.PLAYER) return;
        HumanEntity h = e.getWhoClicked();
        Player p = (Player) h;
        UserData data = UserData.getUserData(p);

        switch (e.getCurrentItem().getType()) {
            case BARRIER:
                p.closeInventory();
                new RuneMenu(PlayerMenuUtility.getPlayerMenuUtility(playerMenuUtility.getOwner())).open();
                break;
        }
        setInventory();
    }

    @Override
    public void setInventory() {
        Player p = playerMenuUtility.getOwner();
        UserData data = UserData.getUserData(playerMenuUtility.getOwner());

        button(inventory, Material.STAINED_GLASS_PANE, ChatColor.GREEN + "[ 룬1 ]", 2);
        button(inventory, Material.STAINED_GLASS_PANE, ChatColor.GREEN + "[ 룬2 ]", 4);
        button(inventory, Material.STAINED_GLASS_PANE, ChatColor.GREEN + "[ 룬3 ]", 6);

        button(inventory, Material.BARRIER, ChatColor.RED + "[ 뒤로 ]", 26);

        background(inventory, 9);
        background(inventory, 10);
        background(inventory, 12);
        background(inventory, 14);
        background(inventory, 16);
        background(inventory, 17);
        fillLineBackground(inventory, 0, false);
        fillLineBackground(inventory, 2, false);
    }
}
