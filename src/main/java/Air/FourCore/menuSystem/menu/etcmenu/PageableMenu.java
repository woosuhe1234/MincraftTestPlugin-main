package Air.FourCore.menuSystem.menu.etcmenu;

import Air.FourCore.menuSystem.menu.mainmenu.MainMenu;
import Air.FourCore.menuSystem.Menu;
import Air.FourCore.menuSystem.PlayerMenuUtility;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import Air.FourCore.user.UserData;

import java.util.ArrayList;
import java.util.List;

import static Air.FourCore.menuSystem.MenuUtility.button;
import static Air.FourCore.menuSystem.MenuUtility.fillLineBackground;

public abstract class PageableMenu extends Menu {
    List<ItemStack> itemList = new ArrayList<>();
    int page = 1;

    public PageableMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
        itemList = setItemList();
    }

    protected abstract List<ItemStack> setItemList();

    @Override
    public void handleMenu(InventoryClickEvent e) {
        if(e.getClickedInventory().getType() == InventoryType.PLAYER) return;
        int totalPage = 1;
        totalPage = (int) Math.ceil(itemList.size() / ((double) getSlots() - 18.0));
        if (totalPage <= 0) totalPage = 1;

        if (e.getCurrentItem().getType() == Material.PAPER) {
            if (e.getSlot() == getSlots() - 9) {
                if(e.isRightClick()){
                    if(page-5 >= 1){
                        page -= 5;
                    }else{
                        page = 1;
                    }
                }else {
                    page--;
                }
                setInventory();
            } else if (e.getSlot() == getSlots() - 1) {
                if(e.isRightClick()){
                    if(page+5 <= totalPage){
                        page += 5;
                    }else{
                        page = totalPage;
                    }
                }else {
                    page++;
                }
                setInventory();
            }
        }else if (e.getCurrentItem().getType() == Material.BARRIER) {
            new MainMenu(PlayerMenuUtility.getPlayerMenuUtility(playerMenuUtility.getOwner())).open();
        }
        /*
        if (e.getSlot() >= getSlots() - 18)
            return;
        if (e.getInventory().equals(this))
            return;
        int index = (page - 1) * (getSlots() - 18) + e.getSlot();
        if (index < itemList.size() && itemList.get(index) != null) {
            ShopItem item = itemList.get(index);
            //
        }
        */
    }

    @Override
    public void setInventory() {
        UserData data = UserData.getUserData(playerMenuUtility.getOwner());
        for (ItemStack i : inventory.getContents()) {
            if (i != null)
                i.setAmount(0);
        }

        int totalPage = 1;
        totalPage = (int) Math.ceil(itemList.size() / ((double) getSlots() - 18.0));
        if (totalPage <= 0) totalPage = 1;

        for (int i = (page - 1) * (getSlots() - 18); i < (page) * (getSlots() - 18); i++) {
            if (itemList.size() <= i || page <= 0) break;
            ItemStack item = itemList.get(i);
            if (item == null) continue;
            button(inventory, item, i - ((page - 1) * (getSlots() - 18)));
        }

        if (page > 1)
            button(inventory, Material.PAPER, ChatColor.DARK_GREEN + "[ 이전 페이지 ]", getSlots() - 9);
        button(inventory, Material.BOOK, "&f[ &6" + page + "&f/" + totalPage + " 페이지 ]", getSlots() - 5);
        if (page < totalPage)
            button(inventory, Material.PAPER, ChatColor.DARK_GREEN + "[ 다음 페이지 ]", getSlots() - 1);

        button(inventory, Material.BARRIER, ChatColor.RED + "[ 뒤로 ]", getSlots() - 14);

        fillLineBackground(inventory, (getSlots() / 9) - 2, false);
    }
}
