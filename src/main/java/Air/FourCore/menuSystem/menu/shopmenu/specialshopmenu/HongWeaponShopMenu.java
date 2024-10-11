package Air.FourCore.menuSystem.menu.shopmenu.specialshopmenu;

import Air.FourCore.item.CustomItemUtility;
import Air.FourCore.menuSystem.PlayerMenuUtility;
import Air.FourCore.menuSystem.menu.shopmenu.ShopItem;
import Air.FourCore.menuSystem.menu.shopmenu.ShopMenu;
import org.bukkit.ChatColor;

import java.util.Arrays;
import java.util.List;

public class HongWeaponShopMenu extends ShopMenu {

    public HongWeaponShopMenu(PlayerMenuUtility playerMenuUtility) {
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
                new ShopItem(CustomItemUtility.HongDummyItem(1), 180, -1, ShopItem.Moneys.네더의별, "홍련"),
                null,
                null,
                null,
                null
        );
    }

    @Override
    public String getMenuName() {
        return ChatColor.YELLOW+""+ChatColor.BOLD+"[ 홍련의 무기상점 ]";
    }

    @Override
    public int getSlots() {
        return 45;
    }
}
