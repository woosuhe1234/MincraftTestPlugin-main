package Air.FourCore.menuSystem.menu.mainmenu;

import Air.FourCore.menuSystem.Menu;
import Air.FourCore.menuSystem.MenuUtility;
import Air.FourCore.menuSystem.PlayerMenuUtility;
import Air.FourCore.menuSystem.menu.submenu.DungeonChannelMenu;
import Air.FourCore.menuSystem.menu.submenu.MineChannelMenu;
import Air.FourCore.menuSystem.menu.submenu.WildChannelMenu;
import menuSystem.menu.submenu.*;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import Air.FourCore.user.UserData;
import Air.FourCore.user.UserUtility;

import static Air.FourCore.menuSystem.MenuUtility.button;
import static Air.FourCore.menuSystem.MenuUtility.fillBackground;

public class WarpMenu extends Menu {

    public WarpMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        return ChatColor.BOLD+"[ 워프 존 ]";
    }

    @Override
    public int getSlots() {
        return 45;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        if(e.getClickedInventory().getType() == InventoryType.PLAYER) return;
        Player p = (Player) e.getWhoClicked();
        Location loc = null;
        switch (e.getCurrentItem().getType()){
            case STONE_SWORD:
                new DungeonChannelMenu(PlayerMenuUtility.getPlayerMenuUtility(playerMenuUtility.getOwner())).open();
                break;
            case BOW:
                loc = MenuUtility.getLocation("PVP장");
                if(loc != null)
                    UserUtility.teleport(p, loc);
                //new PVPChannelMenu(PlayerMenuUtility.getPlayerMenuUtility(playerMenuUtility.getOwner())).open();
                break;
            case EMERALD:
                new MineChannelMenu(PlayerMenuUtility.getPlayerMenuUtility(playerMenuUtility.getOwner())).open();
                //new MineChannelMenu(PlayerMenuUtility.getPlayerMenuUtility(playerMenuUtility.getOwner())).open();
                break;
            case FISHING_ROD:
                loc = MenuUtility.getLocation("튜토리얼");
                if(loc != null)
                    UserUtility.teleport(p, loc);
                break;
            case GOLD_INGOT:
                loc = MenuUtility.getLocation("골드던전");
                if(loc != null)
                    UserUtility.teleport(p, loc);
                break;
            case NETHER_STAR:
                loc = MenuUtility.getLocation("로비");
                if(loc != null)
                    UserUtility.teleport(p, loc);
                //new LobbyChannelMenu(PlayerMenuUtility.getPlayerMenuUtility(playerMenuUtility.getOwner())).open();
                break;
            case GRASS:
                new WildChannelMenu(PlayerMenuUtility.getPlayerMenuUtility(playerMenuUtility.getOwner())).open();
                break;
            case BED:
                loc = MenuUtility.getLocation("잠수대");
                if(loc != null)
                    UserUtility.teleport(p, loc);
                /*
                UserData data = UserData.getUserData(p);
                NationData nation = data.getNation();
                if (nation != null && nation.isWar() && nation.<WarTime>getTimerable(WarTime.class) != null) {
                    p.sendMessage(ChatColor.RESET + ("전쟁 중에는 할 수 없습니다."));
                    return;
                }
                if (data.home == null) {
                    p.sendMessage(ChatColor.RESET + ("홈이 없습니다. 먼저 '/셋홈'을 해주세요."));
                    return;
                }
                new WarpTime(5, data, data.home.getLocation());
                 */
                break;
            case TOTEM:
                loc = MenuUtility.getLocation("상점");
                if(loc != null)
                    UserUtility.teleport(p, loc);
                //new BlockShopMenu(PlayerMenuUtility.getPlayerMenuUtility(playerMenuUtility.getOwner())).open();
                break;
            case SNOW_BLOCK:
                break;
            case BARRIER:
                new MainMenu(PlayerMenuUtility.getPlayerMenuUtility(playerMenuUtility.getOwner())).open();
                break;
        }
    }

    @Override
    public void setInventory() {
        UserData data = UserData.getUserData(playerMenuUtility.getOwner());

        button(inventory, Material.STONE_SWORD, ChatColor.DARK_RED+"[ 던전 ]", 10);
        button(inventory,Material.BOW, ChatColor.RED+"[ PVP ]", 12);
        button(inventory,Material.EMERALD, ChatColor.GREEN+"[ 광산 ]", 14);
        button(inventory,Material.FISHING_ROD, ChatColor.AQUA+"[ 튜토리얼 ]", 16);

        button(inventory,Material.GOLD_INGOT, ChatColor.GOLD+"[ 골드던전 ]", 20);
        button(inventory,Material.NETHER_STAR, ChatColor.GOLD+"[ 로비 ]", 22);

        button(inventory,Material.GRASS, ChatColor.GREEN+"[ 야생 ]", 28);
        button(inventory,Material.BED, ChatColor.WHITE+"[ 잠수대 ]", 30);
        button(inventory,Material.TOTEM, ChatColor.DARK_GREEN+"[ 상점 ]", 32);
        button(inventory,Material.SNOW_BLOCK, ChatColor.YELLOW+"[ 컨텐츠 ]", 34);

        button(inventory,Material.BARRIER, ChatColor.RED+"[ 뒤로 ]", 44);

        fillBackground(inventory);
    }

}
