package Air.FourCore.menuSystem.menu.mainmenu;

import Air.FourCore.item.CustomItemUtility;
import Air.FourCore.menuSystem.Menu;
import Air.FourCore.menuSystem.PlayerMenuUtility;
import Air.FourCore.network.CoinManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import Air.FourCore.user.UserData;

import java.util.Arrays;
import java.util.List;

import static Air.FourCore.menuSystem.MenuUtility.button;
import static Air.FourCore.menuSystem.MenuUtility.fillLineBackground;

public class CoinMenu extends Menu {

    public CoinMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        return ChatColor.BOLD + "[ 코인 ]";
    }

    @Override
    public int getSlots() {
        return 27;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        if(e.getClickedInventory().getType() == InventoryType.PLAYER) return;
        HumanEntity h = e.getWhoClicked();
        Player p = Bukkit.getPlayer(h.getUniqueId());
        UserData data = UserData.getUserData(p);

        switch (e.getCurrentItem().getType()) {
            case HOPPER_MINECART:
                break;
            case POWERED_MINECART:
                break;
            case COMMAND_MINECART:
                break;
            case EYE_OF_ENDER:
                break;
            case BARRIER:
                new MainMenu(PlayerMenuUtility.getPlayerMenuUtility(playerMenuUtility.getOwner())).open();
                break;
        }
        if (e.getSlot() == 13) {
            if (CoinManager.trade_price == 0) {
                p.sendMessage("현재 코인의 정보를 불러올 수 없습니다.");
                return;
            }
            if (e.isShiftClick()) {
                if (p.getInventory().contains(Material.NETHER_STAR, 10)) {
                    if(data.coin.buy(10)) {
                        CustomItemUtility.remove(p.getInventory(), CustomItemUtility.NetherStartItem(10));
                        p.sendMessage("네더의별 - 10");
                        p.playSound(p.getLocation(), Sound.ENTITY_CHICKEN_EGG, 1, 1);
                    }else{
                        p.sendMessage("최대 구매할수 있는 코인은 네더의별 100개 분량 입니다.");
                    }
                } else {
                    p.sendMessage("네더의 별이 부족합니다.");
                }
            } else {
                if (p.getInventory().contains(Material.NETHER_STAR, 1)) {
                    if (data.coin.buy(1)) {
                        CustomItemUtility.remove(p.getInventory(), CustomItemUtility.NetherStartItem(1));
                        p.sendMessage("네더의별 - 1");
                        p.playSound(p.getLocation(), Sound.ENTITY_CHICKEN_EGG, 1, 1);
                    }else{
                        p.sendMessage("최대 구매할수 있는 코인은 네더의별 100개 분량 입니다.");
                    }
                } else {
                    p.sendMessage("네더의 별이 부족합니다.");
                }
            }
        } else if (e.getSlot() == 14) {
            if (CoinManager.trade_price == 0) {
                p.sendMessage("현재 코인의 정보를 불러올 수 없습니다.");
                return;
            }
            if (e.isShiftClick()) {
                if (data.coin.sell(10)) {
                    p.getInventory().addItem(CustomItemUtility.NetherStartItem(10));
                    p.sendMessage("네더의별 + 10");
                    p.playSound(p.getLocation(), Sound.ENTITY_CHICKEN_EGG, 1, 1);
                } else {
                    p.sendMessage("판매할 코인이 부족합니다.");
                }
            } else {
                if (data.coin.sell(1)) {
                    p.getInventory().addItem(CustomItemUtility.NetherStartItem(1));
                    p.sendMessage("네더의별 + 1");
                    p.playSound(p.getLocation(), Sound.ENTITY_CHICKEN_EGG, 1, 1);
                } else {
                    p.sendMessage("판매할 코인이 부족합니다.");
                }
            }
        }
        setInventory();
    }

    @Override
    public void setInventory() {
        UserData data = UserData.getUserData(playerMenuUtility.getOwner());

        ChatColor color = CoinManager.signed_change_price < 0 ? ChatColor.RED : ChatColor.GREEN;
        List<String> lore = Arrays.asList(
                ChatColor.YELLOW + "현재가: " + color + String.format("%,.1f", CoinManager.trade_price),
                ChatColor.YELLOW + "전일대비: " + color + String.format("%.2f", CoinManager.signed_change_rate * 100) + "%, " + String.format("%,.0f", CoinManager.signed_change_price),
                ChatColor.GRAY + "오늘 최고가: " + String.format("%,.0f", CoinManager.high_price),
                ChatColor.GRAY + "오늘 최저가: " + String.format("%,.0f", CoinManager.low_price),
                "",
                ChatColor.BLUE + "내 코인 보유량: " + ChatColor.WHITE + String.format("%,.2f", data.coin.strkCoin) + " (" + String.format("%,.2f", data.coin.strkCoin * CoinManager.trade_price / CoinManager.nether) + "네더의별)",
                ChatColor.BLUE + "평균 단가: " + (data.coin.getYield() < 0 ? ChatColor.RED : ChatColor.GREEN) + String.format("%,.0f", data.coin.strkAveragePrice),
                ChatColor.BLUE + "수익률: " + (data.coin.getYield() < 0 ? ChatColor.RED : ChatColor.GREEN) + String.format("%,.2f", data.coin.getYield()) + "%",
                "",
                ChatColor.GRAY + "※ 실제 업비트 스트라이크 코인의 시세를 반영합니다.",
                ChatColor.GRAY + "※ 실제 코인의 상태에 따라 상장폐지될 수 있습니다.",
                ChatColor.GRAY + "※ 10초마다 실시간으로 갱신됩니다. (클릭 시 새로고침)",
                ChatColor.GRAY + "※ 1네더의 별 = 원화 1만원으로 계산",
                ChatColor.GRAY + "※ 구매/판매 수수료 " + String.format("%.2f", CoinManager.fee * 100) + "%"

        );
        button(inventory, Material.EYE_OF_ENDER, ChatColor.BLUE + "[ 스트라이크 코인 ]", 12, lore);
        List<String> 구매 = Arrays.asList(
                ChatColor.YELLOW + "클릭 :: " + ChatColor.WHITE + "네더의 별 1개 분량 구매",
                ChatColor.YELLOW + "쉬프트+클릭 :: " + ChatColor.WHITE + "네더의 별 10개 분량 구매",
                ChatColor.GOLD + "네더의별 1개 당 코인: " + ChatColor.WHITE + String.format("%,.3f", CoinManager.nether / CoinManager.trade_price * (1 + CoinManager.fee))
        );
        button(inventory, Material.STAINED_GLASS_PANE, ChatColor.GREEN + "[ 코인 구매 ]", 13, 구매);
        List<String> 판매 = Arrays.asList(
                ChatColor.YELLOW + "클릭 :: " + ChatColor.WHITE + "네더의 별 1개 분량 판매",
                ChatColor.YELLOW + "쉬프트+클릭 :: " + ChatColor.WHITE + "네더의 별 10개 분량 판매",
                ChatColor.GOLD + "네더의별 1개 당 코인: " + ChatColor.WHITE + String.format("%,.3f", CoinManager.nether / CoinManager.trade_price * (1 + CoinManager.fee))
        );
        button(inventory, Material.STAINED_GLASS_PANE, ChatColor.RED + "[ 코인 판매 ]", 14, 판매);
        button(inventory, Material.BARRIER, ChatColor.RED + "[ 뒤로 ]", 26);

        fillLineBackground(inventory, 0, false);
        fillLineBackground(inventory, 2, false);
    }
}
