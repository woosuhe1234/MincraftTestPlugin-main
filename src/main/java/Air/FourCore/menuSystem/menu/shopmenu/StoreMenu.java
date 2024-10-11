package Air.FourCore.menuSystem.menu.shopmenu;

import Air.FourCore.menuSystem.Menu;
import Air.FourCore.menuSystem.MenuUtility;
import Air.FourCore.menuSystem.PlayerMenuUtility;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;

import static Air.FourCore.menuSystem.MenuUtility.button;
import static Air.FourCore.menuSystem.MenuUtility.fillLineBackground;

public class StoreMenu extends Menu {

    public StoreMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        return ChatColor.BOLD+"[ 상점 ]";
    }

    @Override
    public int getSlots() {
        return 45;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        switch (e.getCurrentItem().getType()){
            case EYE_OF_ENDER:
            case SLIME_BALL:
                break;
            case BARRIER:
                break;
        }
    }

    @Override
    public void setInventory() {
        MenuUtility.button(inventory, Material.WOOD_SWORD, ChatColor.DARK_GREEN+"[ 임시 아이템 ]", 3);
        MenuUtility.button(inventory,Material.WOOD_AXE, ChatColor.DARK_GREEN+"[ 임시 아이템 ]", 5);
        MenuUtility.button(inventory,Material.STONE_SWORD, ChatColor.DARK_GREEN+"[ 임시 아이템 ]", 12);
        MenuUtility.button(inventory,Material.STONE_AXE, ChatColor.DARK_GREEN+"[ 임시 아이템 ]", 14);
        MenuUtility.button(inventory,Material.IRON_SWORD, ChatColor.DARK_GREEN+"[ 임시 아이템 ]", 21);
        MenuUtility.button(inventory,Material.IRON_AXE, ChatColor.DARK_GREEN+"[ 임시 아이템 ]", 23);
        MenuUtility.button(inventory,Material.GOLD_SWORD, ChatColor.DARK_GREEN+"[ 임시 아이템 ]", 30);
        MenuUtility.button(inventory,Material.GOLD_AXE, ChatColor.DARK_GREEN+"[ 임시 아이템 ]", 32);
        MenuUtility.button(inventory,Material.DIAMOND_SWORD, ChatColor.DARK_GREEN+"[ 임시 아이템 ]", 39);
        MenuUtility.button(inventory,Material.DIAMOND_AXE, ChatColor.DARK_GREEN+"[ 임시 아이템 ]", 41);

        MenuUtility.fillLineBackground(inventory, 0, true);
        MenuUtility.fillLineBackground(inventory, 8, true);
    }
}
