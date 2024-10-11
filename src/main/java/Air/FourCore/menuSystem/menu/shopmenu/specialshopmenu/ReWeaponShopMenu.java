package Air.FourCore.menuSystem.menu.shopmenu.specialshopmenu;

import Air.FourCore.item.CustomItemUtility;
import Air.FourCore.menuSystem.Menu;
import Air.FourCore.menuSystem.MenuUtility;
import Air.FourCore.menuSystem.PlayerMenuUtility;
import Air.FourCore.user.UserData;
import Air.FourCore.menuSystem.menu.shopmenu.ShopMenu;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import Air.FourCore.FourCore;

import java.util.Arrays;
import java.util.List;

import static Air.FourCore.menuSystem.MenuUtility.button;
import static Air.FourCore.menuSystem.MenuUtility.fillLineBackground;

public class ReWeaponShopMenu extends Menu {

    public ReWeaponShopMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        return ChatColor.BOLD+"[ 리바이어던 상점 ]";
    }

    @Override
    public int getSlots() {
        return 27;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        Player p = playerMenuUtility.getOwner();
        Inventory inventory = p.getInventory();
        if(e.getSlot() == 13){
            if(CustomItemUtility.amount(inventory, new ItemStack(Material.NETHER_STAR)) >= 20 &&
                    CustomItemUtility.contains(inventory, CustomItemUtility.HongDummyItem(1)) &&
                    CustomItemUtility.amount(inventory, CustomItemUtility.SoulItem(1)) >= 1 &&
                    FourCore.economy.getBalance(p) >= 15000000){
                ShopMenu.giveMagicItem(p, "리바");
                CustomItemUtility.remove(inventory, new ItemStack(Material.NETHER_STAR, 20));
                CustomItemUtility.remove(inventory, CustomItemUtility.HongDummyItem(1));
                CustomItemUtility.remove(inventory, CustomItemUtility.SoulItem(1));
                FourCore.economy.withdrawPlayer(p, 15000000);
                Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&',
                        "&c[&6[&e[ &a"+p.getName()+"&f님이 &3리바이어던&8의 &7검&f를 구매하셨습니다! &e]&6]&c]"));
            }else{
                p.sendMessage("비용이 모자랍니다.");
            }
        }

    }

    @Override
    public void setInventory() {
        UserData data = UserData.getUserData(playerMenuUtility.getOwner());

        List<String> lore = Arrays.asList(
                ChatColor.GRAY+"심해속의 물의 괴수는 먹잇감을 노린다",
                ChatColor.GRAY+"인류가 최대로 얻을수있는 근력으로만 들을수있다",
                "",
                "가격: 홍련, 20네더의별, 영혼, 1500만원"
        );
        MenuUtility.button(inventory, Material.WOOD_PICKAXE, ChatColor.translateAlternateColorCodes('&',
                "&3리바이어던&8의 &7검"), 13, lore);

        MenuUtility.fillLineBackground(inventory, 0, true);
        MenuUtility.fillLineBackground(inventory, 8, true);
    }
}
