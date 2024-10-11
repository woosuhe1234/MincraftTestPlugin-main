package Air.FourCore.menuSystem.menu.shopmenu;

import Air.FourCore.item.CustomItemUtility;
import Air.FourCore.menuSystem.PlayerMenuUtility;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

public class DungeonShopMenu extends ShopMenu {

    public DungeonShopMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    protected List<ShopItem> setItemList() {

        ItemStack w1 = getItemStack2(ChatColor.GREEN, "엘프의 활", "던전 무기", "바라보는 방향에 화살을 날린다.", "강화된 화살을 날린다", 0);

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
                new ShopItem(w1, 15, -1, ShopItem.Moneys.네더의별, "엘프의활"),
                null,
                new ShopItem(CustomItemUtility.HealingPotionItem(1), 2500, -1),
                null,
                null,
                null
        );
    }

    private ItemStack getItemStack2(ChatColor aqua, String x, String x1, String x2, String x3, int d) {
        ItemStack w1 = new ItemStack(Material.SHEARS);
        ItemMeta m1 = w1.getItemMeta();
        m1.setDisplayName(aqua + x);
        m1.setLore(Arrays.asList(ChatColor.RESET+ x1,
                ChatColor.GOLD+"[ 무기 스킬 ]",
                ChatColor.GOLD+"우클릭 :: "+ChatColor.RESET+ x2,
                ChatColor.GOLD+"쉬프트+우클릭 :: "+ChatColor.RESET+ x3));
        w1.setItemMeta(m1);
        w1.setDurability((short) d);
        return w1;
    }

    @Override
    public String getMenuName() {
        return ChatColor.BOLD + "[ 던전 상점 ]";
    }

    @Override
    public int getSlots() {
        return 45;
    }
}
