package Air.FourCore.menuSystem.menu.shopmenu;

import Air.FourCore.item.CustomItemUtility;
import Air.FourCore.menuSystem.PlayerMenuUtility;
import org.bukkit.ChatColor;

import java.util.Arrays;
import java.util.List;

public class NetherETCShopMenu extends ShopMenu {

    public NetherETCShopMenu(PlayerMenuUtility playerMenuUtility) {
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
                new ShopItem(CustomItemUtility.StatResetItem(), 20, -1, ShopItem.Moneys.네더의별),
                null,
                null,
                new ShopItem(CustomItemUtility.NationStoneItem(), 2, -1, ShopItem.Moneys.네더의별),
                null,
                null,
                new ShopItem(CustomItemUtility.LowStoneItem(), 5, -1, ShopItem.Moneys.네더의별)
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
