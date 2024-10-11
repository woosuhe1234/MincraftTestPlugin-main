package Air.FourCore.menuSystem.menu.etcmenu;

import Air.FourCore.menuSystem.Menu;
import Air.FourCore.menuSystem.PlayerMenuUtility;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.material.MaterialData;
import org.bukkit.material.Openable;

import static Air.FourCore.menuSystem.MenuUtility.button;
import static Air.FourCore.menuSystem.MenuUtility.fillBackground;

public class DoorLockMenu extends Menu {

    public DoorLockMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    public Block door;

    @Override
    public String getMenuName() {
        return ChatColor.BOLD+"[ 도어락 ]";
    }

    @Override
    public int getSlots() {
        return 36;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        if(e.getClickedInventory().getType() == InventoryType.PLAYER) return;
        switch (e.getCurrentItem().getType()){
            case SLIME_BALL:
                playerMenuUtility.getOwner().closeInventory();
                BlockState state = door.getState();
                MaterialData data = state.getData();
                Openable opn = (Openable) data; //Cast data to Openable
                opn.setOpen(true); // Open the door
                state.setData(data); // Add the data to the BlockState
                state.update();
                break;
        }
    }

    @Override
    public void setInventory() {
        button(inventory,Material.SLIME_BALL, ChatColor.WHITE+"[ 7 ]", 3);
        button(inventory,Material.SLIME_BALL, ChatColor.WHITE+"[ 8 ]", 4);
        button(inventory,Material.SLIME_BALL, ChatColor.WHITE+"[ 9 ]", 5);
        button(inventory,Material.SLIME_BALL, ChatColor.WHITE+"[ 4 ]", 12);
        button(inventory,Material.SLIME_BALL, ChatColor.WHITE+"[ 5 ]", 13);
        button(inventory,Material.SLIME_BALL, ChatColor.WHITE+"[ 6 ]", 14);
        button(inventory,Material.SLIME_BALL, ChatColor.WHITE+"[ 1 ]", 21);
        button(inventory,Material.SLIME_BALL, ChatColor.WHITE+"[ 2 ]", 22);
        button(inventory,Material.SLIME_BALL, ChatColor.WHITE+"[ 3 ]", 23);
        button(inventory,Material.SLIME_BALL, ChatColor.WHITE+"[ 0 ]", 31);

        fillBackground(inventory);
    }

    public String color(String str){
        return ChatColor.translateAlternateColorCodes('&',"&r"+str);
    }

}
