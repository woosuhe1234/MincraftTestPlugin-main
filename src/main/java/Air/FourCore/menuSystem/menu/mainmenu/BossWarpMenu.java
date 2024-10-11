package Air.FourCore.menuSystem.menu.mainmenu;

import Air.FourCore.menuSystem.Menu;
import Air.FourCore.menuSystem.MenuUtility;
import Air.FourCore.menuSystem.PlayerMenuUtility;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import Air.FourCore.FourCore;
import Air.FourCore.WorldSetting;
import Air.FourCore.user.UserData;

import java.util.Arrays;
import java.util.List;

import static Air.FourCore.menuSystem.MenuUtility.button;
import static Air.FourCore.menuSystem.MenuUtility.fillLineBackground;

public class BossWarpMenu extends Menu {

    public BossWarpMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        return ChatColor.BOLD+"[ 보스 워프 ]";
    }

    @Override
    public int getSlots() {
        return 27;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        if(e.getClickedInventory().getType() == InventoryType.PLAYER) return;
        Player p = (Player) e.getWhoClicked();
        switch (e.getCurrentItem().getType()){
            case GOLD_AXE:
                JoinBossRoom(p, 0, "파프니르");
                break;
            case STONE_AXE:
                JoinBossRoom(p, 2, "요르문간드");
                break;
            case WOOD_PICKAXE:
                JoinBossRoom(p, 4, "리바이어던");
                break;
            case GOLD_HOE:
                JoinBossRoom(p, 6, "펜리르");
                break;
        }
    }

    private void JoinBossRoom(Player p, int index, String world){
        Location loc;
        if (WorldSetting.bossRooms[index].TryJoin(p)){
            if (FourCore.economy.getBalance(p) >= 5000000){
                FourCore.economy.withdrawPlayer(p, 5000000);
            }else{
                p.sendMessage("소지금이 부족합니다.");
                return;
            }
            loc = MenuUtility.getLocation(world + "1");
            if(loc != null){
                p.teleport(loc);
                p.sendMessage("보스 방에 입장했습니다. (시간제한: 8분)");
            }
        }else if (WorldSetting.bossRooms[index+1].TryJoin(p)){
            if (FourCore.economy.getBalance(p) >= 5000000){
                FourCore.economy.withdrawPlayer(p, 5000000);
            }else{
                p.sendMessage("소지금이 부족합니다.");
                return;
            }
            loc = MenuUtility.getLocation(world + "2");
            if(loc != null){
                p.teleport(loc);
                p.sendMessage("보스 방에 입장했습니다. (시간제한: 8분)");
            }
        }else{
            p.sendMessage("보스 방이 가득찼습니다. 잠시 후에 시도해주세요.");
        }
    }

    @Override
    public void setInventory() {
        UserData data = UserData.getUserData(playerMenuUtility.getOwner());

        List<String> lore = Arrays.asList("5백만원을 소모하고 보스방에 입장합니다.");

        button(inventory, Material.GOLD_AXE, ChatColor.DARK_RED+"[ 파프니르 ]", 10, lore);
        button(inventory,Material.STONE_AXE, ChatColor.RED+"[ 요르문간드 ]", 12, lore);
        button(inventory,Material.WOOD_PICKAXE, ChatColor.GREEN+"[ 리바이어던 ]", 14, lore);
        button(inventory,Material.GOLD_HOE, ChatColor.AQUA+"[ 펜리르 ]", 16, lore);

        fillLineBackground(inventory, 0, false);
        fillLineBackground(inventory, 2, false);
    }
}
