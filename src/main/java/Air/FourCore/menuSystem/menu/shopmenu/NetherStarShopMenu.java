package Air.FourCore.menuSystem.menu.shopmenu;

import Air.FourCore.item.CustomItemUtility;
import Air.FourCore.menuSystem.PlayerMenuUtility;
import org.bukkit.ChatColor;

import java.util.Arrays;
import java.util.List;

public class NetherStarShopMenu extends ShopMenu {

    public NetherStarShopMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    protected List<ShopItem> setItemList() {
        return Arrays.asList(
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,

                null,
                null,
                null,
                null,
                new ShopItem(CustomItemUtility.NetherStartItem(5), 5000000, -1),
                null,
                null,
                null,
                null
        );
    }

    @Override
    public String getMenuName() {
        return ChatColor.YELLOW+"[ 네더의별 구매상점 ]";
    }

    @Override
    public int getSlots() {
        return 45;
    }
}
