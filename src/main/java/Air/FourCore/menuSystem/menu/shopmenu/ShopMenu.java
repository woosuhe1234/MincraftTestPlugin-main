package Air.FourCore.menuSystem.menu.shopmenu;

import Air.FourCore.item.CustomItemUtility;
import Air.FourCore.menuSystem.Menu;
import Air.FourCore.menuSystem.MenuUtility;
import Air.FourCore.menuSystem.PlayerMenuUtility;
import Air.FourCore.user.UserData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import Air.FourCore.FourCore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static Air.FourCore.menuSystem.MenuUtility.button;
import static Air.FourCore.menuSystem.MenuUtility.fillLineBackground;

public abstract class ShopMenu extends Menu {
    List<ShopItem> itemList = new ArrayList<>();
    int page = 1;

    public ShopMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
        itemList = setItemList();
    }

    protected abstract List<ShopItem> setItemList();

    @Override
    public void handleMenu(InventoryClickEvent e) {
        if(e.getClickedInventory().getType() == InventoryType.PLAYER) return;
        switch (e.getCurrentItem().getType()) {
            case PAPER:
                if (e.getSlot() == getSlots() - 9) {
                    page--;
                    setInventory();
                } else if (e.getSlot() == getSlots() - 1) {
                    page++;
                    setInventory();
                }
                break;
        }
        if (e.getSlot() >= getSlots() - 18)
            return;
        if (e.getInventory().equals(this))
            return;
        int index = (page - 1) * (getSlots() - 18) + e.getSlot();
        if (index < itemList.size() && itemList.get(index) != null) {
            ShopItem item = itemList.get(index);
            if (e.isShiftClick()) {
                if (e.isLeftClick()) {
                    전체구매(item);
                } else if (e.isRightClick()) {
                    전체판매(item);
                }
            } else {
                if (e.isLeftClick()) {
                    구매(item);
                } else if (e.isRightClick()) {
                    판매(item);
                }
            }
            setInventory();
        }
    }

    private void 구매(ShopItem item) {
        Player p = playerMenuUtility.getOwner();
        UserData data = UserData.getUserData(p);
        Inventory playerInventory = playerMenuUtility.getOwner().getInventory();
        if (item.buyPrice < 0) {
            p.sendMessage(ChatColor.RESET + "구매할 수 없는 아이템입니다.");
            return;
        }
        if (item.money == ShopItem.Moneys.네더의별) {
            if (item.buyPrice > CustomItemUtility.amount(p.getInventory(), new ItemStack(Material.NETHER_STAR))) {
                p.sendMessage(ChatColor.RESET + "구매할 비용이 부족합니다.");
                return;
            }
        } else if (item.money == ShopItem.Moneys.캐시) {
            if (item.buyPrice > data.cash) {
                p.sendMessage(ChatColor.RESET + "구매할 비용이 부족합니다.");
                return;
            }
        } else if (item.money == ShopItem.Moneys.포인트) {
            if (item.buyPrice > data.point) {
                p.sendMessage(ChatColor.RESET + "구매할 비용이 부족합니다.");
                return;
            }
        } else {
            if (item.buyPrice > FourCore.economy.getBalance(p)) {
                p.sendMessage(ChatColor.RESET + "구매할 돈이 부족합니다.");
                return;
            }
            // 네더별 일일
            if(item.item.getType() == Material.NETHER_STAR) {
                if (data.setting.isBuyNetherStar) {
                    p.sendMessage(ChatColor.RESET + "다음 날 구매하실 수 있습니다.");
                    return;
                } else {
                    data.setting.isBuyNetherStar = true;
                }
            }
        }
        if (!CustomItemUtility.isEmptySpace(playerInventory)) {
            p.sendMessage(ChatColor.RESET + "인벤토리에 여유 공간이 부족합니다.");
            return;
        }
        // 신의무기 전용
        if ((item.item.getType() == Material.WOOD_SWORD || item.item.getType() == Material.IRON_SWORD || item.item.getType() == Material.GOLD_SWORD) && item.money == ShopItem.Moneys.네더의별 && item.info != null) {
            giveMagicItem(p, item.info);
            //Bukkit.getServer().dispatchCommand(p,"c teach low" + item.item.getDurability());
            CustomItemUtility.remove(p.getInventory(), CustomItemUtility.NetherStartItem(), item.buyPrice);
            p.sendMessage(ChatColor.RESET + "-" + String.format("%,d", (long) item.buyPrice) + "네더의 별");
            p.playSound(p.getLocation(), Sound.ENTITY_CHICKEN_EGG, 1, 1);

            // 특수효과
            if(item.info.equalsIgnoreCase("홍련")){
                Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&',
                        "&c[&6[&e[ &a"+p.getName()+"&f님이 &6홍련&f을 구매하셨습니다! &e]&6]&c]"));
            }
            return;
        }
        if (item.money == ShopItem.Moneys.네더의별) {
            CustomItemUtility.remove(p.getInventory(), CustomItemUtility.NetherStartItem(), item.buyPrice);
            if(item.item.getItemMeta().getDisplayName() == null){
                FourCore.log.info(p.getName() + "님이 " + item.item.getType().name() + "를 구매하셨습니다.");
            }else{
                FourCore.log.info(p.getName() + "님이 " + item.item.getItemMeta().getDisplayName() + "를 구매하셨습니다.");
            }
        } else if (item.money == ShopItem.Moneys.캐시) {
            data.cash -= item.buyPrice;
        } else if (item.money == ShopItem.Moneys.포인트) {
            data.point -= item.buyPrice;
        } else {
            FourCore.economy.withdrawPlayer(p, item.buyPrice);
        }
        p.sendMessage(ChatColor.RESET + "-" + String.format("%,d", (long) item.buyPrice) + item.money.toString());
        playerInventory.addItem(item.item);
        p.playSound(p.getLocation(), Sound.ENTITY_CHICKEN_EGG, 1, 1);
        setInventory();

        // 오류 방지
        if(item.item.getType() == Material.NETHER_STAR && item.item.getAmount() > 1){
            playerMenuUtility.getOwner().closeInventory();
        }
    }

    private void 판매(ShopItem item) {
        Player p = playerMenuUtility.getOwner();
        UserData data = UserData.getUserData(p);
        Inventory playerInventory = playerMenuUtility.getOwner().getInventory();
        if (item.sellPrice < 0) {
            p.sendMessage(ChatColor.RESET + "판매할 수 없는 아이템입니다.");
            return;
        }
        if (!CustomItemUtility.contains(playerInventory, item.item)) {
            p.sendMessage(ChatColor.RESET + "판매할 아이템이 없습니다.");
            return;
        }

        FourCore.economy.depositPlayer(p, item.sellPrice + item.getExtra());
        String message = ChatColor.RESET + "+" + String.format("%,d", (long) item.sellPrice) + "원";
        if (item.extraSellPercent > 0)
            message += "(+"+ item.getExtra() +")";
        p.sendMessage(message);

        CustomItemUtility.remove(playerInventory, item.item);
        p.playSound(p.getLocation(), Sound.ENTITY_CHICKEN_EGG, 1, 1);
        setInventory();
    }

    private void 전체구매(ShopItem item) {
        Player p = playerMenuUtility.getOwner();
        UserData data = UserData.getUserData(p);
        Inventory playerInventory = playerMenuUtility.getOwner().getInventory();
        if (item.item.getMaxStackSize() < 64 || item.money != ShopItem.Moneys.원 || item.item.getAmount() != 1) {
            p.sendMessage(ChatColor.RESET + "스택으로 구매할 수 없는 아이템 입니다.");
            return;
        }
        if (item.buyPrice < 0) {
            p.sendMessage(ChatColor.RESET + "구매할 수 없는 아이템입니다.");
            return;
        }
        if (item.buyPrice * 64 > FourCore.economy.getBalance(p)) {
            p.sendMessage(ChatColor.RESET + "구매할 돈이 부족합니다.");
            return;
        }
        if (!CustomItemUtility.isEmptySpace(playerInventory)) {
            p.sendMessage(ChatColor.RESET + "인벤토리에 여유 공간이 부족합니다.");
            return;
        }
        FourCore.economy.withdrawPlayer(p, item.buyPrice * 64);
        p.sendMessage(ChatColor.RESET + "-" + String.format("%,d", (long) item.buyPrice * 64) + "원");
        for (int i = 0; i < 64; i++) {
            playerInventory.addItem(item.item);
        }
        p.playSound(p.getLocation(), Sound.ENTITY_CHICKEN_EGG, 1, 1);
        setInventory();
    }

    private void 전체판매(ShopItem item) {
        Player p = playerMenuUtility.getOwner();
        UserData data = UserData.getUserData(p);
        Inventory playerInventory = playerMenuUtility.getOwner().getInventory();
        if (item.sellPrice < 0) {
            p.sendMessage(ChatColor.RESET + "판매할 수 없는 아이템입니다.");
            return;
        }
        if (!CustomItemUtility.contains(playerInventory, item.item)) {
            p.sendMessage(ChatColor.RESET + "판매할 아이템이 없습니다.");
            return;
        }
        int amount = CustomItemUtility.amount(playerInventory, item.item);

        FourCore.economy.depositPlayer(p, (item.sellPrice + item.getExtra()) * amount);
        String message = ChatColor.RESET + "+" + String.format("%,d", (long) item.sellPrice * amount) + "원";
        if (item.extraSellPercent > 0)
            message += "(+"+ (item.getExtra() * amount) +")";
        p.sendMessage(message);

        CustomItemUtility.remove(playerInventory, item.item, amount);
        p.playSound(p.getLocation(), Sound.ENTITY_CHICKEN_EGG, 1, 1);
        setInventory();
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
            ShopItem item = itemList.get(i);
            if (item == null) continue;
            MenuUtility.button(inventory, item.item, i - ((page - 1) * (getSlots() - 18)), help(item));
        }

        if (page > 1)
            MenuUtility.button(inventory, Material.PAPER, ChatColor.DARK_GREEN + "[ 이전 페이지 ]", getSlots() - 9);
        List<String> 페이지 = Arrays.asList("", ChatColor.translateAlternateColorCodes('&',
                        "&e소지금: &f" + String.format("%,d", (long) FourCore.economy.getBalance(playerMenuUtility.getOwner())) + "&eG"),
                ChatColor.translateAlternateColorCodes('&',
                        "&b캐시: &f" + String.format("%,d", (long) data.cash)),
                ChatColor.translateAlternateColorCodes('&',
                        "&9포인트: &f" + String.format("%,d", (long) data.point)));
        MenuUtility.button(inventory, Material.BOOK, "&f[ &6" + page + "&f/" + totalPage + " 페이지 ]", getSlots() - 5, 페이지);
        if (page < totalPage)
            MenuUtility.button(inventory, Material.PAPER, ChatColor.DARK_GREEN + "[ 다음 페이지 ]", getSlots() - 1);

        MenuUtility.fillLineBackground(inventory, (getSlots() / 9) - 2, false);
    }

    private List<String> help(ShopItem item) {
        String b, s;
        if (item.buyPrice < 0) b = "구매 불가";
        else b = String.format("%,d", item.buyPrice) + item.money.toString();
        if (item.sellPrice < 0) s = "판매 불가";
        else s = String.format("%,d", item.sellPrice) + " 골드";
        List<String> result = Arrays.asList(
                "",
                ChatColor.translateAlternateColorCodes('&', "&a구매 &f가격 : " + b),
                ChatColor.translateAlternateColorCodes('&', "&c판매 &f가격 : " + s),
                "",
                ChatColor.translateAlternateColorCodes('&', "        &f[ &e이용방법 &f]"),
                "",
                ChatColor.translateAlternateColorCodes('&', "&a좌클릭       &7>    &f1 개 &a구매"),
                ChatColor.translateAlternateColorCodes('&', "&c우클릭       &7>    &f1 개 &c판매"),
                ChatColor.translateAlternateColorCodes('&', "&7쉬프트&a좌클릭 &7>   &f64 개 &a구매"),
                ChatColor.translateAlternateColorCodes('&', "&7쉬프트&c우클릭 &7>    &f전체 &c판매")
        );
        return result;
    }

    public static void giveMagicItem(Player p, String s){
        boolean temp = p.isOp();
        try {
            p.setOp(true);
            Bukkit.getServer().dispatchCommand(p, "c magicitem "+s);
        } finally {
            if (temp)
                p.setOp(true);
            else
                p.setOp(false);
        }
    }
}
