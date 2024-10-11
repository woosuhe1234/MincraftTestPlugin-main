package Air.FourCore.menuSystem.menu.shopmenu;

import Air.FourCore.item.CustomItemUtility;
import Air.FourCore.menuSystem.PlayerMenuUtility;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

public class NationShopMenu extends ShopMenu {

    public NationShopMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    protected List<ShopItem> setItemList() {
        ItemStack item = new ItemStack(Material.LAPIS_BLOCK);
        ItemMeta item_meta = item.getItemMeta();
        item_meta.setDisplayName(ChatColor.AQUA+"전초기지");
        item.setItemMeta(item_meta);
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

                new ShopItem(CustomItemUtility.CastleItem(), 40, -1, ShopItem.Moneys.네더의별),
                null,
                new ShopItem(item, 1000000, -1),
                null,
                new ShopItem(new ItemStack(Material.IRON_DOOR), 500000, -1),
                null,
                new ShopItem(CustomItemUtility.IronDoorBombItem(), 500000, -1),
                null,
                new ShopItem(CustomItemUtility.AllianceTicketItem(), 10000000, -1)
        );
    }

    @Override
    public String getMenuName() {
        return ChatColor.BOLD+"[ 국가 상점 ]";
    }

    @Override
    public int getSlots() {
        return 45;
    }
}
