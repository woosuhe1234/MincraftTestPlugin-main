package Air.FourCore.menuSystem.menu.shopmenu;

import org.bukkit.inventory.ItemStack;

public class ShopItem {

    public ShopItem(ItemStack item, int buyPrice, int sellPrice, Moneys money, String info) {
        this.item = item;
        this.buyPrice = buyPrice;
        this.sellPrice = sellPrice;
        this.money = money;
        this.info = info;
    }

    public ShopItem(ItemStack item, int buyPrice, int sellPrice, Moneys money) {
        this.item = item;
        this.buyPrice = buyPrice;
        this.sellPrice = sellPrice;
        this.money = money;
    }

    public ShopItem(ItemStack item, int buyPrice, int sellPrice, int extraSellPercent) {
        this.item = item;
        this.buyPrice = buyPrice;
        this.sellPrice = sellPrice;
        this.money = Moneys.원;
        this.extraSellPercent = extraSellPercent;
    }

    public ShopItem(ItemStack item, int buyPrice, int sellPrice) {
        this.item = item;
        this.buyPrice = buyPrice;
        this.sellPrice = sellPrice;
        this.money = Moneys.원;
    }

    ItemStack item;
    int buyPrice;
    int sellPrice;
    Moneys money;
    String info;
    int extraSellPercent = 0;

    public int getExtra(){
        return (int)(sellPrice * extraSellPercent * 0.01);
    }

    public enum Moneys {
        원, 캐시, 네더의별, 포인트
    }
}

