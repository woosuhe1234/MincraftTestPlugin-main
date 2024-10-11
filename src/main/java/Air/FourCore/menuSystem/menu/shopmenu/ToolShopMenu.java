package Air.FourCore.menuSystem.menu.shopmenu;

import Air.FourCore.menuSystem.PlayerMenuUtility;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

public class ToolShopMenu extends ShopMenu {

    public ToolShopMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    protected List<ShopItem> setItemList() {

        ItemStack w1 = new ItemStack(Material.DIAMOND_SWORD);
        w1.addEnchantment(Enchantment.DAMAGE_ALL, 1);
        ItemStack w2 = new ItemStack(Material.DIAMOND_SWORD);
        w2.addEnchantment(Enchantment.DAMAGE_ALL, 2);
        ItemStack w3 = new ItemStack(Material.DIAMOND_SWORD);
        w3.addEnchantment(Enchantment.DAMAGE_ALL, 3);
        ItemStack w4 = new ItemStack(Material.DIAMOND_SWORD);
        w4.addEnchantment(Enchantment.DAMAGE_ALL, 4);

        ItemStack m1 = new ItemStack(Material.DIAMOND_PICKAXE);
        m1.addEnchantment(Enchantment.DIG_SPEED, 4);
        ItemStack m2 = new ItemStack(Material.DIAMOND_SPADE);
        m2.addEnchantment(Enchantment.DIG_SPEED, 4);
        ItemStack m3 = new ItemStack(Material.DIAMOND_HOE);
        //m3.addUnsafeEnchantment(., 4);
        ItemStack m4 = new ItemStack(Material.DIAMOND_AXE);
        m4.addEnchantment(Enchantment.DIG_SPEED, 5);

        return Arrays.asList(
                new ShopItem(new ItemStack(Material.DIAMOND_SWORD), 50000, -1),
                null,
                new ShopItem(w1, 80000, -1),
                null,
                new ShopItem(w2, 100000, -1),
                null,
                new ShopItem(w3, 150000, -1),
                null,
                new ShopItem(w4, 200000, -1),

                null,
                new ShopItem(m1, 500000, -1),
                null,
                new ShopItem(m2, 500000, -1),
                null,
                new ShopItem(m3, 50000, -1),
                null,
                new ShopItem(m4, 500000, -1)
        );
    }

    @Override
    public String getMenuName() {
        return ChatColor.BOLD+"[ 도구 상점 ]";
    }

    @Override
    public int getSlots() {
        return 45;
    }
}
