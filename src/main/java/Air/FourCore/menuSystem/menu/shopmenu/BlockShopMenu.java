package Air.FourCore.menuSystem.menu.shopmenu;

import Air.FourCore.files.ShopConfig;
import Air.FourCore.menuSystem.PlayerMenuUtility;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class BlockShopMenu extends ShopMenu {

    public BlockShopMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    protected List<ShopItem> setItemList() {
        //int buy = 50;
        //int sell = -1;

        Set<String> set = ShopConfig.get().getConfigurationSection("BlockShop").getKeys(false);
        List<ShopItem> result = new ArrayList<>();
        for(String s : set){
            int buy = ShopConfig.get().getInt("BlockShop."+s+".buy");
            int sell = ShopConfig.get().getInt("BlockShop."+s+".sell");
            int sub = ShopConfig.get().getInt("BlockShop."+s+".sub");
            Material m = Material.matchMaterial(s);
            ItemStack item = null;
            if(m == null)
            {
                int index = ShopConfig.get().getInt("BlockShop." + s + ".material");
                if(index == 0){
                    result.add(null);
                }else{
                    item = new ItemStack(index);
                    item.setDurability((byte)sub);
                    result.add(new ShopItem(item, buy, sell));
                }
            }else{
                item = new ItemStack(m);
                item.setDurability((byte)sub);
                result.add(new ShopItem(item, buy, sell));
            }
        }
        return result;
    }

    @Override
    public String getMenuName() {
        return ChatColor.BOLD + "[ 블록 상점 ]";
    }

    @Override
    public int getSlots() {
        return 54;
    }
}
