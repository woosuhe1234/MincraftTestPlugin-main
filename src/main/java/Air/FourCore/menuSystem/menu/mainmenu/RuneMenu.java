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

import static Air.FourCore.menuSystem.MenuUtility.button;
import static Air.FourCore.menuSystem.MenuUtility.fillLineBackground;

public class RuneMenu extends Menu {

    public RuneMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        return ChatColor.BOLD + "[ 룬 ]";
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
            case EMERALD:
                new RuneEquipMenu(PlayerMenuUtility.getPlayerMenuUtility(playerMenuUtility.getOwner())).open();
                break;
            case ENCHANTMENT_TABLE:
                new RuneFusionMenu(PlayerMenuUtility.getPlayerMenuUtility(playerMenuUtility.getOwner())).open();
                break;
            case BARRIER:
                new MainMenu(PlayerMenuUtility.getPlayerMenuUtility(playerMenuUtility.getOwner())).open();
                break;
        }
        setInventory();
    }

    @Override
    public void setInventory() {
        Player p = playerMenuUtility.getOwner();
        UserData data = UserData.getUserData(playerMenuUtility.getOwner());

        button(inventory, Material.EMERALD, ChatColor.GREEN + "[ 룬 장착 ]", 11);
        button(inventory, Material.ENCHANTMENT_TABLE, ChatColor.GOLD + "[ 룬 합성 ]", 15);

        button(inventory, Material.BARRIER, ChatColor.RED + "[ 뒤로 ]", 26);

        fillLineBackground(inventory, 0, false);
        fillLineBackground(inventory, 2, false);
    }
}
