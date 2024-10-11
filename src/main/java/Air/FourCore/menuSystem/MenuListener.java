package Air.FourCore.menuSystem;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.InventoryHolder;

public class MenuListener implements Listener {

    @EventHandler
    public void onMenuClick(InventoryClickEvent e){
        Player p = (Player) e.getWhoClicked();
        InventoryHolder holder = e.getInventory().getHolder();

        if(holder instanceof Menu){
            if((e.getInventory().getName().contains("합성") || e.getInventory().getName().contains("장착")) &&
                    ((e.getSlot() == 11 || e.getSlot() == 13 || e.getSlot() == 15) || (e.getClickedInventory() != null && e.getClickedInventory().getType() == InventoryType.PLAYER)))
            {

            }else{
                e.setCancelled(true);
            }
            if(e.getCurrentItem() == null){
                return;
            }
            Menu menu = (Menu) holder;
            menu.handleMenu(e);
            p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_HIT, 1f, 1f);
        }
    }
}
