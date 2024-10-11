package Air.FourCore.menuSystem.menu.shopmenu;

import Air.FourCore.menuSystem.PlayerMenuUtility;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

public class WeaponShopMenu extends ShopMenu {

    public WeaponShopMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    protected List<ShopItem> setItemList() {

        ItemStack m1 = new ItemStack(Material.DIAMOND_SWORD);
        m1.addEnchantment(Enchantment.DAMAGE_ALL, 1);
        ItemStack m2 = new ItemStack(Material.DIAMOND_SWORD);
        m2.addEnchantment(Enchantment.DAMAGE_ALL, 2);
        ItemStack m3 = new ItemStack(Material.DIAMOND_SWORD);
        m3.addEnchantment(Enchantment.DAMAGE_ALL, 3);
        ItemStack m4 = new ItemStack(Material.DIAMOND_SWORD);
        m4.addEnchantment(Enchantment.DAMAGE_ALL, 4);

        return Arrays.asList(
                new ShopItem(new ItemStack(Material.DIAMOND_SWORD), 50000, -1),
                null,
                new ShopItem(m1, 80000, -1),
                null,
                new ShopItem(m2, 100000, -1),
                null,
                new ShopItem(m3, 150000, -1),
                null,
                new ShopItem(m4, 200000, -1)
        );
    }

    @Override
    public String getMenuName() {
        return ChatColor.BOLD+"[ 무기 상점 ]";
    }

    @Override
    public int getSlots() {
        return 45;
    }
}
