package Air.FourCore.menuSystem.menu.shopmenu;

import Air.FourCore.menuSystem.PlayerMenuUtility;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import Air.FourCore.user.UserData;
import Air.FourCore.user.UserRune;

import java.util.Arrays;
import java.util.List;

public class FarmShopMenu extends ShopMenu {

    public FarmShopMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    protected List<ShopItem> setItemList() {
        int state = UserData.getUserData(playerMenuUtility.getOwner()).rune.getState(UserRune.State.농작물판매);

        return Arrays.asList(
                new ShopItem(new ItemStack(Material.CARROT_ITEM), 750, 90, state),
                new ShopItem(new ItemStack(Material.POTATO_ITEM), 750, 90, state),
                new ShopItem(new ItemStack(Material.MELON), -1, 52, state),
                new ShopItem(new ItemStack(Material.MELON_BLOCK), -1, 470, state),
                new ShopItem(new ItemStack(Material.MELON_SEEDS), 1650, -1, state),
                new ShopItem(new ItemStack(Material.INK_SACK,1,(short) 3), 1200, 125, state),
                new ShopItem(new ItemStack(Material.PUMPKIN), -1, 455, state),
                new ShopItem(new ItemStack(Material.PUMPKIN_SEEDS), 1650, -1, state),
                new ShopItem(new ItemStack(Material.BEETROOT), -1, 415, state),
                new ShopItem(new ItemStack(Material.BEETROOT_SEEDS), 850, -1, state),
                new ShopItem(new ItemStack(Material.SUGAR_CANE), 1250, 250, state),
                new ShopItem(new ItemStack(Material.WHEAT), -1, 415, state),
                new ShopItem(new ItemStack(Material.SEEDS), 10, -1, state)
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
