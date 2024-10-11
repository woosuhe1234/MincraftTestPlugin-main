package Air.FourCore.menuSystem.menu.mainmenu;

import Air.FourCore.item.CustomItemUtility;
import Air.FourCore.menuSystem.Menu;
import Air.FourCore.menuSystem.PlayerMenuUtility;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import Air.FourCore.user.UserData;

import java.util.Arrays;
import java.util.List;

import static Air.FourCore.menuSystem.MenuUtility.*;

public class RuneMenu_OLD extends Menu {

    public RuneMenu_OLD(PlayerMenuUtility playerMenuUtility) {
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
            case NETHER_STAR:
                if(CustomItemUtility.amount(p.getInventory(), new ItemStack(Material.NETHER_STAR)) >= 5){
                    CustomItemUtility.remove(p.getInventory(), new ItemStack(Material.NETHER_STAR, 5));
                    data.rune.reRole();
                    p.sendMessage("리롤 완료.");
                    p.playSound(p.getLocation(), Sound.ENTITY_CHICKEN_EGG, 1, 1);
                }else{
                    p.sendMessage("비용이 부족합니다.");
                }
                break;
            case BARRIER:
                new RuneMenu(PlayerMenuUtility.getPlayerMenuUtility(playerMenuUtility.getOwner())).open();
                break;
        }
        setInventory();
    }

    @Override
    public void setInventory() {
        Player p = playerMenuUtility.getOwner();
        UserData data = UserData.getUserData(playerMenuUtility.getOwner());

        /*
        List<String> lore1 = Arrays.asList(ChatColor.RESET+data.rune.getName(0));
        List<String> lore2 = Arrays.asList(ChatColor.RESET+data.rune.getName(1));
        List<String> lore3 = Arrays.asList(ChatColor.RESET+data.rune.getName(2));

        if(data.level >= UserRune.getRune(1)) {
            button(inventory, Material.EMERALD, ChatColor.GREEN + "[ 룬1 ]", 11, lore1);
        }else{
            button(inventory, Material.EMERALD, ChatColor.GRAY + "[ 잠김 ] - "+UserRune.getRune(1)+"레벨에 해금됩니다.", 11);
        }
        if(data.level >= UserRune.getRune(2)) {
            button(inventory, Material.EMERALD, ChatColor.GREEN + "[ 룬2 ]", 13, lore2);
        }else{
            button(inventory, Material.EMERALD, ChatColor.GRAY + "[ 잠김 ] - "+UserRune.getRune(2)+"레벨에 해금됩니다.", 13);
        }
        if(data.level >= UserRune.getRune(3)) {
            button(inventory, Material.EMERALD, ChatColor.GREEN + "[ 룬3 ]", 15, lore3);
        }else{
            button(inventory, Material.EMERALD, ChatColor.GRAY + "[ 잠김 ] - "+UserRune.getRune(3)+"레벨에 해금됩니다.", 15);
        }

         */
        List<String> lore4 = Arrays.asList(ChatColor.RESET+"비용 : 5 네더의별");
        button(inventory, Material.NETHER_STAR, ChatColor.LIGHT_PURPLE + "[ 리롤 ]", 22, lore4);
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
