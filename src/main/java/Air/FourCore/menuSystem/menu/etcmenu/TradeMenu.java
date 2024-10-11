package Air.FourCore.menuSystem.menu.etcmenu;

import Air.FourCore.menuSystem.Menu;
import Air.FourCore.menuSystem.PlayerMenuUtility;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

import static Air.FourCore.menuSystem.MenuUtility.button;
import static Air.FourCore.menuSystem.MenuUtility.fillLineBackground;

public class TradeMenu extends Menu {

    public ItemStack[] items = new ItemStack[9];
    public int confirm_me = 0;
    public int confirm_you = 0;
    public TradeMenu you;

    public TradeMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        return ChatColor.BOLD + "[ 거래: " + you.playerMenuUtility.getOwner().getName() + " ]";
    }

    @Override
    public int getSlots() {
        return 45;
    }

    /*
    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e) {
        if(e.getInventory() == this) {
            Inventory playerInventory = playerMenuUtility.getOwner().getInventory();
            for (ItemStack item : items) {
                if (item != null) {
                    playerInventory.addItem(item);
                }
            }
            playerMenuUtility.getOwner().sendMessage("dddddddddddddddddddd");
        }
    }*/

    @Override
    public void handleMenu(InventoryClickEvent e) {
        Inventory playerInventory = playerMenuUtility.getOwner().getInventory();
        if (e.getClickedInventory() == playerInventory) {
            if (confirm_me == 0) {
                inventory.addItem(playerInventory.getItem(e.getSlot()));
                playerInventory.setItem(e.getSlot(), new ItemStack(Material.AIR));
                setInventory();
                you.setInventory();

            }
            return;
        }
        switch (e.getSlot()) {
            case 18:
                if (confirm_me == 0) {
                    confirm_me++;
                    you.confirm_you++;
                    setInventory();
                    you.setInventory();
                }
                break;
            case 27:
                if (confirm_me == 1) {
                    confirm_me++;
                    you.confirm_you++;
                    setInventory();
                    you.setInventory();
                    if (confirm_me == 2 && confirm_you == 2) {
                        // 서로 아이템 주기
                        playerMenuUtility.getOwner().closeInventory();
                        you.playerMenuUtility.getOwner().closeInventory();
                    }
                }
                break;
            case 10:
            case 11:
            case 12:
            case 19:
            case 20:
            case 21:
            case 28:
            case 29:
            case 30:
                if (confirm_me == 0) {
                    if (true) {
                        playerInventory.addItem(inventory.getItem(e.getSlot()));
                        inventory.setItem(e.getSlot(), new ItemStack(Material.AIR));
                        setInventory();
                        you.setInventory();
                    }
                }
                break;
        }
    }

    @Override
    public void setInventory() {
        items[0] = inventory.getItem(10);
        items[1] = inventory.getItem(11);
        items[2] = inventory.getItem(12);
        items[3] = inventory.getItem(19);
        items[4] = inventory.getItem(20);
        items[5] = inventory.getItem(21);
        items[6] = inventory.getItem(28);
        items[7] = inventory.getItem(29);
        items[8] = inventory.getItem(30);
        if (you.items[0] != null)
            inventory.setItem(14, you.items[0]);
        if (you.items[1] != null)
            inventory.setItem(15, you.items[1]);
        if (you.items[2] != null)
            inventory.setItem(16, you.items[2]);
        if (you.items[3] != null)
            inventory.setItem(23, you.items[3]);
        if (you.items[4] != null)
            inventory.setItem(24, you.items[4]);
        if (you.items[5] != null)
            inventory.setItem(25, you.items[5]);
        if (you.items[6] != null)
            inventory.setItem(32, you.items[6]);
        if (you.items[7] != null)
            inventory.setItem(33, you.items[7]);
        if (you.items[8] != null)
            inventory.setItem(34, you.items[8]);

        if (confirm_me <= 0) {
            button(inventory, new ItemStack(Material.HARD_CLAY, 1, (short) 1), ChatColor.WHITE + "[ 1차 확인 준비 ]", 18);
        } else {
            button(inventory, new ItemStack(Material.WOOL, 1, (short) 5), ChatColor.GREEN + "[ 1차 확인 완료 ]", 18);
        }
        if (confirm_me <= 1) {
            button(inventory, new ItemStack(Material.HARD_CLAY, 1, (short) 1), ChatColor.WHITE + "[ 2차 확인 준비 ]", 27);
        } else {
            button(inventory, new ItemStack(Material.WOOL, 1, (short) 5), ChatColor.GREEN + "[ 2차 확인 완료 ]", 27);
        }


        if (confirm_you <= 0) {
            button(inventory, new ItemStack(Material.HARD_CLAY, 1, (short) 1), ChatColor.WHITE + "[ 1차 확인 준비 ]", 26);
        } else {
            button(inventory, new ItemStack(Material.WOOL, 1, (short) 5), ChatColor.GREEN + "[ 1차 확인 완료 ]", 26);
        }
        if (confirm_you <= 1) {
            button(inventory, new ItemStack(Material.HARD_CLAY, 1, (short) 1), ChatColor.WHITE + "[ 2차 확인 준비 ]", 35);
        } else {
            button(inventory, new ItemStack(Material.WOOL, 1, (short) 5), ChatColor.GREEN + "[ 2차 확인 완료 ]", 35);
        }

        button(inventory, new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 5), ChatColor.GREEN + "내 거래창", 38, Arrays.asList(ChatColor.GRAY + "※ 1차 확인이 완료되면 더이상 아이템을 올리거나 뺄 수 없습니다."));
        button(inventory, new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 1), ChatColor.GOLD + "상대 거래창", 42);
        fillLineBackground(inventory, 0, false);
        fillLineBackground(inventory, 4, false);
        fillLineBackground(inventory, 0, true);
        fillLineBackground(inventory, 4, true);
        fillLineBackground(inventory, 8, true);
    }
}
