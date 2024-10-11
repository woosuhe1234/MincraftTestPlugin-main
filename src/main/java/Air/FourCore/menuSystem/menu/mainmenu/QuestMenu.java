package Air.FourCore.menuSystem.menu.mainmenu;

import Air.FourCore.menuSystem.Menu;
import Air.FourCore.menuSystem.PlayerMenuUtility;
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

import static Air.FourCore.menuSystem.MenuUtility.*;

public class QuestMenu extends Menu {

    public QuestMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        return ChatColor.BOLD + "[ 퀘스트 ]";
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
            case EXPLOSIVE_MINECART:
                break;
            case BARRIER:
                new MainMenu(PlayerMenuUtility.getPlayerMenuUtility(playerMenuUtility.getOwner())).open();
                break;
        }
        if (e.getSlot() == 11) {
            if (data.questList[0] != null) {
                try {
                    data.questList[0].getClass().getDeclaredConstructor(UserData.class).newInstance(data);
                } catch (Exception ignored) {
                }
            }
        } else if (e.getSlot() == 13) {
            if (data.questList[1] != null) {
                try {
                    data.questList[1].getClass().getDeclaredConstructor(UserData.class).newInstance(data);
                } catch (Exception ignored) {
                }
            }
        } else if (e.getSlot() == 15) {
            if (data.questList[2] != null) {
                try {
                    data.questList[2].getClass().getDeclaredConstructor(UserData.class).newInstance(data);
                } catch (Exception ignored) {
                }
            }
        } else if (e.getSlot() == 20) {
            if (data.questList[0] != null) {
                data.questList[0] = null;
                p.sendMessage("퀘스트를 포기했습니다.");
                p.playSound(p.getLocation(), Sound.ENTITY_CHICKEN_EGG, 1, 1);
            }
        } else if (e.getSlot() == 22) {
            if (data.questList[1] != null) {
                data.questList[1] = null;
                p.sendMessage("퀘스트를 포기했습니다.");
                p.playSound(p.getLocation(), Sound.ENTITY_CHICKEN_EGG, 1, 1);
            }
        } else if (e.getSlot() == 24) {
            if (data.questList[2] != null) {
                data.questList[2] = null;
                p.sendMessage("퀘스트를 포기했습니다.");
                p.playSound(p.getLocation(), Sound.ENTITY_CHICKEN_EGG, 1, 1);
            }
        }
        setInventory();
    }

    @Override
    public void setInventory() {
        UserData data = UserData.getUserData(playerMenuUtility.getOwner());

        if (data.questList != null) {
            if (data.questList[0] != null) {
                List<String> lore = Arrays.asList(ChatColor.YELLOW + data.questList[0].getRewardString());
                button(inventory, Material.KNOWLEDGE_BOOK, ChatColor.RESET + data.questList[0].getName(), 11, lore);
            }else{
                button(inventory, Material.AIR, null, 11);
            }
            if (data.questList[1] != null) {
                List<String> lore = Arrays.asList(ChatColor.YELLOW + data.questList[1].getRewardString());
                button(inventory, Material.KNOWLEDGE_BOOK, ChatColor.RESET + data.questList[1].getName(), 13, lore);
            }else{
                button(inventory, Material.AIR, null, 13);
            }
            if (data.questList[2] != null) {
                List<String> lore = Arrays.asList(ChatColor.YELLOW + data.questList[2].getRewardString());
                button(inventory, Material.KNOWLEDGE_BOOK, ChatColor.RESET + data.questList[2].getName(), 15, lore);
            }else{
                button(inventory, Material.AIR, null, 15);
            }
        }

        List<String> lore = Arrays.asList(ChatColor.RED + "※ 클릭시 퀘스트를 포기합니다.");
        button(inventory, Material.STAINED_GLASS_PANE, ChatColor.YELLOW + "[ 메인 퀘스트 ]", 20, lore);
        button(inventory, Material.STAINED_GLASS_PANE, ChatColor.WHITE + "[ 일반 퀘스트 ]", 22, lore);
        button(inventory, Material.STAINED_GLASS_PANE, ChatColor.GOLD + "[ 일일 퀘스트 ]", 24, lore);
        button(inventory, Material.BARRIER, ChatColor.RED + "[ 뒤로 ]", 26);

        background(inventory, 9);
        background(inventory, 10);
        background(inventory, 12);
        background(inventory, 14);
        background(inventory, 16);
        background(inventory, 17);
        fillLineBackground(inventory, 0, false);
        fillLineBackground(inventory, 2, false);
    }
}
