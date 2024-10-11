package Air.FourCore.menuSystem.menu.shopmenu;

import Air.FourCore.menuSystem.PlayerMenuUtility;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

public class ArmorShopMenu extends ShopMenu {

    public ArmorShopMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    protected List<ShopItem> setItemList() {

        ItemStack m1_1 = new ItemStack(Material.DIAMOND_HELMET);
        m1_1.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        ItemStack m2_1 = new ItemStack(Material.DIAMOND_CHESTPLATE);
        m2_1.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        ItemStack m3_1 = new ItemStack(Material.DIAMOND_LEGGINGS);
        m3_1.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        ItemStack m4_1 = new ItemStack(Material.DIAMOND_BOOTS);
        m4_1.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);

        ItemStack m1_2 = new ItemStack(Material.DIAMOND_HELMET);
        m1_2.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
        ItemStack m2_2 = new ItemStack(Material.DIAMOND_CHESTPLATE);
        m2_2.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
        ItemStack m3_2 = new ItemStack(Material.DIAMOND_LEGGINGS);
        m3_2.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
        ItemStack m4_2 = new ItemStack(Material.DIAMOND_BOOTS);
        m4_2.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);

        ItemStack m1_3 = new ItemStack(Material.DIAMOND_HELMET);
        m1_3.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
        ItemStack m2_3 = new ItemStack(Material.DIAMOND_CHESTPLATE);
        m2_3.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
        ItemStack m3_3 = new ItemStack(Material.DIAMOND_LEGGINGS);
        m3_3.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
        ItemStack m4_3 = new ItemStack(Material.DIAMOND_BOOTS);
        m4_3.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);

        ItemStack m1_4 = new ItemStack(Material.DIAMOND_HELMET);
        m1_4.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
        ItemStack m2_4 = new ItemStack(Material.DIAMOND_CHESTPLATE);
        m2_4.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
        ItemStack m3_4 = new ItemStack(Material.DIAMOND_LEGGINGS);
        m3_4.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
        ItemStack m4_4 = new ItemStack(Material.DIAMOND_BOOTS);
        m4_4.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);

        return Arrays.asList(
                null,
                new ShopItem(new ItemStack(Material.DIAMOND_HELMET), 50000, -1),
                null,
                new ShopItem(new ItemStack(Material.DIAMOND_CHESTPLATE), 50000, -1),
                null,
                new ShopItem(new ItemStack(Material.DIAMOND_LEGGINGS), 50000, -1),
                null,
                new ShopItem(new ItemStack(Material.DIAMOND_BOOTS), 50000, -1),
                null,

                null,
                new ShopItem(m1_1, 80000, -1),
                null,
                new ShopItem(m2_1, 80000, -1),
                null,
                new ShopItem(m3_1, 80000, -1),
                null,
                new ShopItem(m4_1, 80000, -1),
                null,

                null,
                new ShopItem(m1_2, 100000, -1),
                null,
                new ShopItem(m2_2, 100000, -1),
                null,
                new ShopItem(m3_2, 100000, -1),
                null,
                new ShopItem(m4_2, 100000, -1),
                null,

                null,
                new ShopItem(m1_3, 150000, -1),
                null,
                new ShopItem(m2_3, 150000, -1),
                null,
                new ShopItem(m3_3, 150000, -1),
                null,
                new ShopItem(m4_3, 150000, -1),
                null,

                null,
                new ShopItem(m1_4, 200000, -1),
                null,
                new ShopItem(m2_4, 200000, -1),
                null,
                new ShopItem(m3_4, 200000, -1),
                null,
                new ShopItem(m4_4, 200000, -1),
                null
        );
    }

    @Override
    public String getMenuName() {
        return ChatColor.BOLD+"[ 방어구 상점 ]";
    }

    @Override
    public int getSlots() {
        return 63;
    }
}
