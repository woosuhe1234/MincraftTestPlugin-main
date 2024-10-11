package Air.FourCore.menuSystem.menu.mainmenu;

import Air.FourCore.menuSystem.Menu;
import Air.FourCore.menuSystem.MenuUtility;
import Air.FourCore.menuSystem.PlayerMenuUtility;
import Air.FourCore.menuSystem.menu.submenu.DungeonChannelMenu;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import Air.FourCore.user.UserData;
import Air.FourCore.user.UserUtility;

import java.util.Arrays;
import java.util.List;

import static Air.FourCore.menuSystem.MenuUtility.button;
import static Air.FourCore.menuSystem.MenuUtility.fillBackground;

public class DungeonMenu extends Menu {

    public int channel = 1;

    public DungeonMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        return ChatColor.BOLD + "[ 던전 ]";
    }

    @Override
    public int getSlots() {
        return 45;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        if(e.getClickedInventory().getType() == InventoryType.PLAYER) return;
        Player p = playerMenuUtility.getOwner();
        UserData data = UserData.getUserData(p);
        Location loc;
        switch (e.getCurrentItem().getType()) {
            case COBBLESTONE:
                loc = MenuUtility.getLocation("D1-" + channel);
                if (loc != null )
                    UserUtility.teleport(p, loc);
                break;
            case SLIME_BLOCK:
                loc = MenuUtility.getLocation("D2-" + channel);
                if (loc != null && data.level > 10)
                    UserUtility.teleport(p, loc);
                break;
            case NETHERRACK:
                loc = MenuUtility.getLocation("D3-" + channel);
                if (loc != null && data.level > 20)
                    UserUtility.teleport(p, loc);
                break;
            case STONE:
                loc = MenuUtility.getLocation("D4-" + channel);
                if (loc != null && data.level > 30)
                    UserUtility.teleport(p, loc);
                break;
            case LIGHT_BLUE_GLAZED_TERRACOTTA:
                loc = MenuUtility.getLocation("D5-" + channel);
                if (loc != null && data.level > 40)
                    UserUtility.teleport(p, loc);
                break;
            case PINK_GLAZED_TERRACOTTA:
                loc = MenuUtility.getLocation("D6-" + channel);
                if (loc != null && data.level > 50)
                    UserUtility.teleport(p, loc);
                break;
            case WOOL:
                loc = MenuUtility.getLocation("D7-" + channel);
                if (loc != null && data.level > 60)
                    UserUtility.teleport(p, loc);
                break;
            case SAND:
                loc = MenuUtility.getLocation("D8-" + channel);
                if (loc != null && data.level > 70)
                    UserUtility.teleport(p, loc);
                break;
            case BARRIER:
                new DungeonChannelMenu(PlayerMenuUtility.getPlayerMenuUtility(p)).open();
                break;
        }
    }

    @Override
    public void setInventory() {
        List<String> d1 = Arrays.asList(ChatColor.RESET + "적정 레벨: 1 ~ 10", "", ChatColor.GRAY + "※ 레벨에 맞는 던전만 사냥하실 수 있습니다.");
        button(inventory, Material.COBBLESTONE, ChatColor.WHITE + "[ 던전1 ]", 10, d1);
        List<String> d2 = Arrays.asList(ChatColor.RESET + "적정 레벨: 11 ~ 20", "", ChatColor.GRAY + "※ 레벨에 맞는 던전만 사냥하실 수 있습니다.");
        button(inventory, Material.SLIME_BLOCK, ChatColor.RED + "[ 던전2 ]", 12, d2);
        List<String> d3 = Arrays.asList(ChatColor.RESET + "적정 레벨: 21 ~ 30", "", ChatColor.GRAY + "※ 레벨에 맞는 던전만 사냥하실 수 있습니다.");
        button(inventory, Material.NETHERRACK, ChatColor.DARK_GREEN + "[ 던전3 ]", 14, d3);
        List<String> d4 = Arrays.asList(ChatColor.RESET + "적정 레벨: 31 ~ 40", "", ChatColor.GRAY + "※ 레벨에 맞는 던전만 사냥하실 수 있습니다.");
        button(inventory, Material.STONE, ChatColor.AQUA + "[ 던전4 ]", 16, d4);

        List<String> d5 = Arrays.asList(ChatColor.RESET + "적정 레벨: 41 ~ 50", "", ChatColor.GRAY + "※ 레벨에 맞는 던전만 사냥하실 수 있습니다.");
        button(inventory, Material.LIGHT_BLUE_GLAZED_TERRACOTTA, ChatColor.GREEN + "[ 던전5 ]", 28, d5);
        List<String> d6 = Arrays.asList(ChatColor.RESET + "적정 레벨: 51 ~ 60", "", ChatColor.GRAY + "※ 레벨에 맞는 던전만 사냥하실 수 있습니다.");
        button(inventory, Material.PINK_GLAZED_TERRACOTTA, ChatColor.WHITE + "[ 던전6 ]", 30, d6);
        List<String> d7 = Arrays.asList(ChatColor.RESET + "적정 레벨: 61 ~ 70", "", ChatColor.GRAY + "※ 레벨에 맞는 던전만 사냥하실 수 있습니다.");
        button(inventory, Material.WOOL, ChatColor.DARK_AQUA + "[ 던전7 ]", 32, d7);
        List<String> d8 = Arrays.asList(ChatColor.RESET + "적정 레벨: 71 ~ 100", "", ChatColor.GRAY + "※ 레벨에 맞는 던전만 사냥하실 수 있습니다.");
        button(inventory, Material.SAND, ChatColor.YELLOW + "[ 던전8 ]", 34, d8);

        button(inventory, Material.BARRIER, ChatColor.RED + "[ 뒤로 ]", 44);

        fillBackground(inventory);
    }
}
