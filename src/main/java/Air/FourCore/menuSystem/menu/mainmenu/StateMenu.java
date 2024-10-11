package Air.FourCore.menuSystem.menu.mainmenu;

import Air.FourCore.item.CustomItemUtility;
import Air.FourCore.menuSystem.Menu;
import Air.FourCore.menuSystem.PlayerMenuUtility;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import Air.FourCore.user.UserData;

import java.util.Arrays;
import java.util.List;

import static Air.FourCore.menuSystem.MenuUtility.*;

public class StateMenu extends Menu {

    public StateMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        return ChatColor.BOLD + "[ 스텟 창 ]";
    }

    @Override
    public int getSlots() {
        return 27;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        if(e.getClickedInventory().getType() == InventoryType.PLAYER) return;
        Player p = playerMenuUtility.getOwner();
        UserData data = UserData.getUserData(p);

        switch (e.getCurrentItem().getType()) {
            case DIAMOND_SWORD:
                if (e.isShiftClick()) {
                    if (data.state >= 10 && data.strength <= 90) {
                        data.state -= 10;
                        data.strength += 10;
                        p.playSound(p.getLocation(), Sound.ENTITY_CHICKEN_EGG, 1, 1);
                    }
                } else {
                    if (data.state >= 1 && data.strength < 100) {
                        data.state--;
                        data.strength++;
                        p.playSound(p.getLocation(), Sound.ENTITY_CHICKEN_EGG, 1, 1);
                    }
                }
                data.updateStatus();
                setInventory();
                break;
            case FEATHER:
                if (e.isShiftClick()) {
                    if (data.state >= 10 && data.force <= 90) {
                        data.state -= 10;
                        data.force += 10;
                        p.playSound(p.getLocation(), Sound.ENTITY_CHICKEN_EGG, 1, 1);
                    }
                } else {
                    if (data.state >= 1 && data.force < 100) {
                        data.state--;
                        data.force++;
                        p.playSound(p.getLocation(), Sound.ENTITY_CHICKEN_EGG, 1, 1);
                    }
                }
                data.updateStatus();
                setInventory();
                break;
            case SHIELD:
                if (e.isShiftClick()) {
                    if (data.state >= 10 && data.defense <= 90) {
                        data.state -= 10;
                        data.defense += 10;
                        p.playSound(p.getLocation(), Sound.ENTITY_CHICKEN_EGG, 1, 1);
                    }
                } else {
                    if (data.state >= 1 && data.defense < 100) {
                        data.state--;
                        data.defense++;
                        p.playSound(p.getLocation(), Sound.ENTITY_CHICKEN_EGG, 1, 1);
                    }
                }
                data.updateStatus();
                setInventory();
                break;
            case TOTEM:
                if (e.isShiftClick()) {
                    if (data.state >= 10 && data.mental <= 90) {
                        data.state -= 10;
                        data.mental += 10;
                        p.playSound(p.getLocation(), Sound.ENTITY_CHICKEN_EGG, 1, 1);
                    }
                } else {
                    if (data.state >= 1 && data.mental < 100) {
                        data.state--;
                        data.mental++;
                        p.playSound(p.getLocation(), Sound.ENTITY_CHICKEN_EGG, 1, 1);
                    }
                }
                data.updateStatus();
                setInventory();
                break;
            case PAPER:
                if (CustomItemUtility.contains(p.getInventory(),CustomItemUtility.StatResetItem())){ // 2스텍 인식 못함
                    if (data.strength == 0 && data.force == 0 && data.defense == 0 && data.mental == 0) {
                        p.sendMessage("이미 초기화 상태입니다.");
                    } else {
                        CustomItemUtility.remove(p.getInventory(), CustomItemUtility.StatResetItem()); // 전부 사라짐
                        data.state = data.level * 3;
                        data.strength = 0;
                        data.force = 0;
                        data.defense = 0;
                        data.mental = 0;
                        data.updateStatus();
                        setInventory();
                        p.sendMessage("스텟을 초기화 했습니다!");
                        p.playSound(p.getLocation(), Sound.ENTITY_CHICKEN_EGG, 1, 1);
                    }
                } else {
                    p.sendMessage("스텟 초기화권이 필요합니다.");
                }
                break;
            case BARRIER:
                new MainMenu(PlayerMenuUtility.getPlayerMenuUtility(playerMenuUtility.getOwner())).open();
                break;
        }

    }

    @Override
    public void setInventory() {
        UserData data = UserData.getUserData(playerMenuUtility.getOwner());

        List<String> strength_lore = Arrays.asList(
                "",
                color(ChatColor.GOLD + "[ 추가 능력치 ]"),
                color("- 근력 포인트 1당 공격력 0.5 증가"),
                color("- 근력 포인트 1당 체력 0.2 증가"),
                color(ChatColor.GRAY + "※ 쉬프트 클릭 시 +10")
        );
        button(inventory, Material.DIAMOND_SWORD, ChatColor.RED + "[ 근력 ] " + data.strength + "/100", 10, strength_lore);

        List<String> force_lore = Arrays.asList(
                "",
                color(ChatColor.GOLD + "[ 추가 능력치 ]"),
                color("- 무력 포인트 1당 공격력 0.4 증가"),
                color("- 무력 포인트 1당 체력 0.1 증가"),
                color("- 무력 포인트 1당 이동 속도 1% 증가"),
                color("- 무력 포인트 50 이상시 피격시 5% 확률로 회피"),
                color(ChatColor.GRAY + "※ 쉬프트 클릭 시 +10")
        );
        button(inventory, Material.FEATHER, ChatColor.AQUA + "[ 무력 ] " + data.force + "/100", 12, force_lore);

        List<String> defense_lore = Arrays.asList(
                "",
                color(ChatColor.GOLD + "[ 추가 능력치 ]"),
                color("- 방어력 포인트 1당 공격력 0.1 증가"),
                color("- 방어력 포인트 1당 체력 0.4 증가"),
                color("- 방어력 포인트 50 이상시 피격시 2% 확률로 저항1"),
                color(ChatColor.GRAY + "※ 쉬프트 클릭 시 +10")
        );
        button(inventory, Material.SHIELD, ChatColor.DARK_GREEN + "[ 방어력 ] " + data.defense + "/100", 14, defense_lore);

        List<String> mental_lore = Arrays.asList(
                "",
                color(ChatColor.GOLD + "[ 추가 능력치 ]"),
                color("- 정신력 포인트 1당 체력 0.75 증가"),
                color("- 정신력 포인트 50 이상시 타격시 30초 마다 흡혈"),
                color(ChatColor.GRAY + "※ 쉬프트 클릭 시 +10")
        );
        button(inventory, Material.TOTEM, ChatColor.GREEN + "[ 정신력 ] " + data.mental + "/100", 16, mental_lore);

        List<String> stat_lore = Arrays.asList(
                color(ChatColor.GREEN + " ▶ " + ChatColor.RESET + data.state),
                "",
                color(ChatColor.GOLD + "[ 능력치 ]"),
                color(ChatColor.RESET + "- 근력: " + ChatColor.RESET + data.strength),
                color(ChatColor.RESET + "- 무력: " + ChatColor.RESET + data.force),
                color(ChatColor.RESET + "- 방어력: " + ChatColor.RESET + data.defense),
                color(ChatColor.RESET + "- 정신력: " + ChatColor.RESET + data.mental),
                "",
                color(ChatColor.GRAY + "- 공격력 +" + String.format("%.1f", data.getDamage())),
                color(ChatColor.GRAY + "- 체력 +" + String.format("%.1f", data.getHealth())),
                color(ChatColor.GRAY + "- 스피드 +" + String.format("%.1f", data.getSpeed() * 500) + "%")
        );
        button(inventory, Material.NETHER_STAR, ChatColor.GREEN + "[ 남은 포인트 ]", 22, stat_lore);

        List<String> init_lore = Arrays.asList(
                "",
                color("&7※ 스텟 초기화권을 소모합니다")
        );
        button(inventory, Material.PAPER, ChatColor.DARK_RED + "[ 스텟 초기화 ]", 18, init_lore);
        button(inventory, Material.BARRIER, ChatColor.RED + "[ 뒤로 ]", 26);

        fillLineBackground(inventory, 0, false);
        fillLineBackground(inventory, 2, false);
    }
}
