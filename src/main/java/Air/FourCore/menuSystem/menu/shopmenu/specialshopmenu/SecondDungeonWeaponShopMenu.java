package Air.FourCore.menuSystem.menu.shopmenu.specialshopmenu;

import Air.FourCore.item.CustomItemUtility;
import Air.FourCore.menuSystem.Menu;
import Air.FourCore.menuSystem.MenuUtility;
import Air.FourCore.menuSystem.PlayerMenuUtility;
import Air.FourCore.user.UserData;
import Air.FourCore.menuSystem.menu.shopmenu.ShopMenu;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

import static Air.FourCore.menuSystem.MenuUtility.button;
import static Air.FourCore.menuSystem.MenuUtility.fillLineBackground;

public class SecondDungeonWeaponShopMenu extends Menu {

    public SecondDungeonWeaponShopMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        return ChatColor.BOLD+"[ 2차 던전 무기 상점 ]";
    }

    @Override
    public int getSlots() {
        return 27;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        Player p = playerMenuUtility.getOwner();
        Inventory inventory = p.getInventory();
        if(e.getSlot() == 13){
            if(CustomItemUtility.amount(inventory, new ItemStack(Material.NETHER_STAR)) >= 10 &&
                    CustomItemUtility.contains(inventory, CustomItemUtility.ElfBowDummyItem(1))){
                ShopMenu.giveMagicItem(p, "스태프");
                CustomItemUtility.remove(inventory, new ItemStack(Material.NETHER_STAR, 10));
                CustomItemUtility.remove(inventory, CustomItemUtility.ElfBowDummyItem(1));
            }else{
                p.sendMessage("비용이 모자랍니다.");
            }
        }

    }

    @Override
    public void setInventory() {
        UserData data = UserData.getUserData(playerMenuUtility.getOwner());

        List<String> lore = Arrays.asList(
                ChatColor.BLUE+"던전 무기",
                ChatColor.GOLD+"[ 무기 스킬 ]",
                ChatColor.GOLD+"우클릭"+ChatColor.WHITE+":: 바라보는 방향에 마법을 사용한다.",
                ChatColor.GOLD+"쉬프트+우클릭"+ChatColor.WHITE+":: 영혼의 진을 소환한다.",
                ChatColor.GOLD+"쉬프트+좌클릭"+ChatColor.WHITE+":: 영혼의 힘을 빌려 좌클릭에 마법을 사용하게 만든다.",
                "",
                "가격: 엘프의활, 10네더의별"
        );
        MenuUtility.button(inventory, Material.WOOD_AXE, ChatColor.translateAlternateColorCodes('&',
                "&8영혼의 무기"), 13, lore);

        MenuUtility.fillLineBackground(inventory, 0, true);
        MenuUtility.fillLineBackground(inventory, 8, true);
    }
}
