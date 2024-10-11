package Air.FourCore.menuSystem.menu.shopmenu;

import Air.FourCore.item.CustomItemUtility;
import Air.FourCore.menuSystem.PlayerMenuUtility;
import org.bukkit.ChatColor;

import java.util.Arrays;
import java.util.List;

public class CashShopMenu extends ShopMenu {

    public CashShopMenu(PlayerMenuUtility playerMenuUtility) {
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
                new ShopItem(CustomItemUtility.NetherStartItemNone(40), 11000, -1 , ShopItem.Moneys.캐시),
                null,
                new ShopItem(CustomItemUtility.NetherStartBoxItemNone(), 1100, -1, ShopItem.Moneys.캐시),
                null,
                new ShopItem(CustomItemUtility.InitNationCoolTimeItem(1), 2500, -1, ShopItem.Moneys.캐시),
                null,
                new ShopItem(CustomItemUtility.DetectLocationItem(1), 2990, -1, ShopItem.Moneys.캐시),
                null
        );
    }

    @Override
    public String getMenuName() {
        return ChatColor.BOLD+"[ 농사 상점 ]";
    }

    @Override
    public int getSlots() {
        return 45;
    }
}
