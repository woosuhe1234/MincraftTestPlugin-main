package Air.FourCore.menuSystem.menu.shopmenu;

import Air.FourCore.menuSystem.PlayerMenuUtility;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import Air.FourCore.user.UserData;
import Air.FourCore.user.UserRune;

import java.util.Arrays;
import java.util.List;

public class MineralShopMenu extends ShopMenu {

    public MineralShopMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    protected List<ShopItem> setItemList() {
        int state = UserData.getUserData(playerMenuUtility.getOwner()).rune.getState(UserRune.State.광물판매);
        return Arrays.asList(
                new ShopItem(new ItemStack(Material.COBBLESTONE), -1, 10, state),
                new ShopItem(new ItemStack(Material.COAL), -1, 100, state),
                new ShopItem(new ItemStack(Material.INK_SACK, 1, (short) 4), -1, 110, state),
                new ShopItem(new ItemStack(Material.IRON_INGOT), -1, 300, state),
                new ShopItem(new ItemStack(Material.IRON_ORE), -1, 300, state),
                new ShopItem(new ItemStack(Material.GOLD_INGOT), -1, 400, state),
                new ShopItem(new ItemStack(Material.GOLD_ORE), -1, 400, state),
                new ShopItem(new ItemStack(Material.DIAMOND), -1, 2000, state),
                new ShopItem(new ItemStack(Material.EMERALD), -1, 3500, state)
        );
    }

    @Override
    public String getMenuName() {
        return ChatColor.BOLD+"[ 광물 상점 ]";
    }

    @Override
    public int getSlots() {
        return 45;
    }
}
