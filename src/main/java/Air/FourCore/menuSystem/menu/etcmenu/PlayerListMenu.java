package Air.FourCore.menuSystem.menu.etcmenu;

import Air.FourCore.menuSystem.menu.mainmenu.PlayerInfoMenu;
import Air.FourCore.item.CustomItemUtility;
import Air.FourCore.menuSystem.MenuUtility;
import Air.FourCore.menuSystem.PlayerMenuUtility;
import org.bukkit.*;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import Air.FourCore.FourCore;
import Air.FourCore.user.BagData;
import Air.FourCore.user.UserData;
import Air.FourCore.user.UserSetting;
import Air.FourCore.user.UserUtility;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PlayerListMenu extends PageableMenu {

    public PlayerListMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        return ChatColor.BOLD + "[ 플레이어 목록 ]";
    }

    @Override
    public int getSlots() {
        return 54;
    }

    @Override
    protected List<ItemStack> setItemList(){
        List<ItemStack> result = new ArrayList<>();
        for(OfflinePlayer offlinePlayer : Bukkit.getOnlinePlayers()){
            UserData target = UserData.getUserData(offlinePlayer);
            ItemStack itemStack = offlinePlayer.isOnline()? MenuUtility.getPlayerHead() : new ItemStack(Material.SKULL_ITEM);
            ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.setDisplayName(UserUtility.playerToString(offlinePlayer) + " " + (target.role == UserSetting.Role.일반유저 ? "" : ChatColor.RED + "[ "+target.role.name()+" ]"));
            List<String> lore = Arrays.asList(
                    ChatColor.GOLD + "레벨: " + ChatColor.RESET + target.level,
                    ChatColor.GOLD + "국가: " + ChatColor.RESET + (target.nation == null ? "-" : target.nation),
                    ChatColor.GOLD + "직위: " + ChatColor.RESET + target.job,
                    ChatColor.GOLD + "소지금: " + ChatColor.RESET + String.format("%,d", (long) FourCore.economy.getBalance(offlinePlayer)),
                    ChatColor.GRAY + target.uuid.toString(),
                    null,
                    null,
                    null
            );
            if(playerMenuUtility.getOwner().isOp()){
                Location loc = offlinePlayer.getPlayer().getLocation();
                lore.set(5,ChatColor.GOLD + "[Op전용] 위치: " + ChatColor.RESET + loc.getWorld().getName()  + " (" +(int)loc.getX()+", "+(int)loc.getY()+", "+(int)loc.getZ()+")");
                lore.set(6,ChatColor.GOLD + "[Op전용] 네더의별: " + ChatColor.RESET +( CustomItemUtility.amount(offlinePlayer.getPlayer().getInventory(), CustomItemUtility.NetherStartItem()) + CustomItemUtility.amount(offlinePlayer.getPlayer().getEnderChest(), CustomItemUtility.NetherStartItem()) + CustomItemUtility.amount(BagData.getInventory(offlinePlayer.getUniqueId()), CustomItemUtility.NetherStartItem()) )+ "개");
                lore.set(7,ChatColor.GOLD + "[Op전용] 코인: " + ChatColor.RESET + String.format("%,.2f", target.coin.strkCoin) + ", 수익률: " + String.format("%,.2f",target.coin.getYield())+"%");
            }
            itemMeta.setLore(lore);
            itemStack.setItemMeta(itemMeta);
            result.add(itemStack);
        }
        return result;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        super.handleMenu(e);

        if (e.getSlot() >= getSlots() - 18)
            return;
        if (e.getInventory().equals(this))
            return;
        int index = (page - 1) * (getSlots() - 18) + e.getSlot();
        if (index < itemList.size() && itemList.get(index) != null) {
            //ItemStack item = itemList.get(index);
            PlayerInfoMenu menu = new PlayerInfoMenu(PlayerMenuUtility.getPlayerMenuUtility(playerMenuUtility.getOwner()));
            menu.target = (Bukkit.getOnlinePlayers().toArray(new OfflinePlayer[0]))[index];
            menu.open();
        }
    }

    @Override
    public void setInventory() {
        super.setInventory();
    }
}
