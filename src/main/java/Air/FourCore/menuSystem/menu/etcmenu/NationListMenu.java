package Air.FourCore.menuSystem.menu.etcmenu;

import Air.FourCore.menuSystem.menu.mainmenu.NationInfoMenu;
import Air.FourCore.menuSystem.PlayerMenuUtility;
import Air.FourCore.nation.NationData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import Air.FourCore.user.UserUtility;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NationListMenu extends PageableMenu {

    public NationListMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        return ChatColor.BOLD + "[ 국가 목록 ]";
    }

    @Override
    public int getSlots() {
        return 54;
    }

    @Override
    protected List<ItemStack> setItemList(){
        List<ItemStack> result = new ArrayList<>();
        for(NationData nation : NationData.map.values()){
            ItemStack itemStack = new ItemStack((nation.isWar() ? Material.GOLD_SWORD : Material.BEACON));
            ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.setDisplayName(ChatColor.GOLD + nation.nationName + (nation.isWar() ? ChatColor.RED + " [ 전쟁중 ]" : ""));
            List<String> lore = Arrays.asList(
                    ChatColor.GREEN + "레벨: " + ChatColor.RESET + nation.level,
                    ChatColor.YELLOW + "왕: " + ChatColor.RESET + (nation.king == null ? "-" : UserUtility.playerToString(Bukkit.getOfflinePlayer(nation.king))),
                    ChatColor.YELLOW + "부왕: " + ChatColor.RESET + (nation.viceroy == null ? "-" : UserUtility.playerToString(Bukkit.getOfflinePlayer(nation.viceroy))),
                    ChatColor.GOLD + "인원: " + ChatColor.RESET + nation.getAllPlayerCount() + ChatColor.GREEN + " (온라인: "+nation.getOnlinePlayerCount()+")",
                    ChatColor.GOLD + "성개수: " + ChatColor.RESET + nation.castles.size(),
                    ChatColor.GOLD + "동맹국: " + ChatColor.RESET + (nation.allies.size() > 0 ? nation.allies.get(0) : "-")
            );
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
            NationInfoMenu menu = new NationInfoMenu(PlayerMenuUtility.getPlayerMenuUtility(playerMenuUtility.getOwner()));
            int i = 0;
            for (Object key : NationData.map.keySet()) {
                if(i == index) {
                    menu.target = NationData.map.get(key);
                    menu.open();
                    break;
                }
                i++;
            }
        }
    }

    @Override
    public void setInventory() {
        super.setInventory();
    }
}
