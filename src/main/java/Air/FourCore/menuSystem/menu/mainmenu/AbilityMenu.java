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

import java.util.Arrays;
import java.util.List;

import static Air.FourCore.menuSystem.MenuUtility.*;

public class AbilityMenu extends Menu {

    public AbilityMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        return ChatColor.BOLD + "[ 특성 ]";
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

        List<String> lore1 = Arrays.asList(ChatColor.RESET+"체력 14칸을 회복시키는 아이템을 얻습니다.");
        List<String> lore2 = Arrays.asList(ChatColor.RESET+"추후 업데이트 될 예정입니다.");
        List<String> lore3 = Arrays.asList(ChatColor.RESET+"추후 업데이트 될 예정입니다.");

        if(data.level >= 30) {
            button(inventory, Material.EYE_OF_ENDER, ChatColor.GREEN + "[ 초회복 ]", 11, lore1);
        }else{
            button(inventory, Material.ENDER_PEARL, ChatColor.GRAY + "[ 잠김 ] - 30레벨에 해금됩니다.", 11);
        }
        if(data.level >= 50) {
            button(inventory, Material.EYE_OF_ENDER, ChatColor.GRAY + "[ 미구현 ]", 13, lore2);
        }else{
            button(inventory, Material.ENDER_PEARL, ChatColor.GRAY + "[ 잠김 ] - 50레벨에 해금됩니다.", 13);
        }
        if(data.level >= 70) {
            button(inventory, Material.EYE_OF_ENDER, ChatColor.GRAY + "[ 미구현 ]", 15, lore3);
        }else{
            button(inventory, Material.ENDER_PEARL, ChatColor.GRAY + "[ 잠김 ] - 70레벨에 해금됩니다.", 15);
        }
        button(inventory, Material.BARRIER, ChatColor.RED + "[ 뒤로 ]", 26);

        fillBackground(inventory);
    }
}
