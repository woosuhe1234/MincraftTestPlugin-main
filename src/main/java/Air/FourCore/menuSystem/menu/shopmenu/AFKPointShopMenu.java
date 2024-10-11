package Air.FourCore.menuSystem.menu.shopmenu;

import Air.FourCore.item.CustomItemUtility;
import Air.FourCore.menuSystem.PlayerMenuUtility;
import org.bukkit.ChatColor;

import java.util.Arrays;
import java.util.List;

public class AFKPointShopMenu extends ShopMenu {

    public AFKPointShopMenu(PlayerMenuUtility playerMenuUtility) {
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
                new ShopItem(CustomItemUtility.NetherStartItem(), 135, -1, ShopItem.Moneys.포인트),
                null,
                null,
                null,
                null
        );
    }

    @Override
    public String getMenuName() {
        return ChatColor.YELLOW+"[ 네더 기타상점 ]";
    }

    @Override
    public int getSlots() {
        return 45;
    }
}
