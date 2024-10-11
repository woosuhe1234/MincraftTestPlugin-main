package Air.FourCore.menuSystem.menu.shopmenu;

import Air.FourCore.item.CustomItemUtility;
import Air.FourCore.menuSystem.PlayerMenuUtility;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

import static Air.FourCore.menuSystem.MenuUtility.button;
import static Air.FourCore.menuSystem.MenuUtility.fillLineBackground;

public class TestShopMenu extends ShopMenu {

    public TestShopMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    protected List<ShopItem> setItemList() {
        return Arrays.asList(
                new ShopItem(new ItemStack(Material.NETHER_STAR), -1, 150000),
                new ShopItem(CustomItemUtility.ExpBuffItem1h(), 99999, -1),
                null,
                new ShopItem(new ItemStack(Material.FISHING_ROD), 100, 50),
                new ShopItem(new ItemStack(Material.FISHING_ROD), 100, 50),
                new ShopItem(new ItemStack(Material.FISHING_ROD), 100, 50),
                new ShopItem(new ItemStack(Material.FISHING_ROD), 100, 50),
                new ShopItem(new ItemStack(Material.FISHING_ROD), 100, 50),
                new ShopItem(new ItemStack(Material.FISHING_ROD), 100, 50),

                new ShopItem(new ItemStack(Material.FISHING_ROD), 100, 50),
                new ShopItem(new ItemStack(Material.FISHING_ROD), 100, 50),
                new ShopItem(new ItemStack(Material.FISHING_ROD), 100, 50),
                new ShopItem(new ItemStack(Material.FISHING_ROD), 100, 50),
                new ShopItem(new ItemStack(Material.FISHING_ROD), 100, 50),
                new ShopItem(new ItemStack(Material.FISHING_ROD), 100, 50),
                new ShopItem(new ItemStack(Material.FISHING_ROD), 100, 50),
                new ShopItem(new ItemStack(Material.FISHING_ROD), 100, 50),
                new ShopItem(new ItemStack(Material.FISHING_ROD), 100, 50),

                new ShopItem(new ItemStack(Material.FISHING_ROD), 100, 50),
                new ShopItem(new ItemStack(Material.FISHING_ROD), 100, 50),
                new ShopItem(new ItemStack(Material.FISHING_ROD), 100, 50),
                new ShopItem(new ItemStack(Material.FISHING_ROD), 100, 50),
                new ShopItem(new ItemStack(Material.FISHING_ROD), 100, 50),
                new ShopItem(new ItemStack(Material.FISHING_ROD), 100, 50),
                new ShopItem(new ItemStack(Material.FISHING_ROD), 100, 50),
                new ShopItem(new ItemStack(Material.FISHING_ROD), 100, 50),
                new ShopItem(new ItemStack(Material.FISHING_ROD), 100, 50),

                new ShopItem(new ItemStack(Material.FISHING_ROD), 100, 50),
                new ShopItem(new ItemStack(Material.FISHING_ROD), 100, 50),
                new ShopItem(new ItemStack(Material.FISHING_ROD), 150, 150)
        );
    }

    @Override
    public String getMenuName() {
        return ChatColor.BOLD+"[ 테스트 상점 ]";
    }

    @Override
    public int getSlots() {
        return 27;
    }
}
