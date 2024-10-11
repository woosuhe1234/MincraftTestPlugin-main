package Air.FourCore.menuSystem.menu.mainmenu;

import Air.FourCore.item.CustomItemUtility;
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
import org.bukkit.inventory.ItemStack;
import Air.FourCore.RandomSystem;
import Air.FourCore.user.UserData;

import java.util.Arrays;
import java.util.List;

import static Air.FourCore.menuSystem.MenuUtility.*;

public class RuneFusionMenu extends Menu {

    public RuneFusionMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        return ChatColor.BOLD + "[ 룬 합성 ]";
    }

    @Override
    public int getSlots() {
        return 27;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        if(e.getClickedInventory().getType() == InventoryType.PLAYER) return;
        HumanEntity h = e.getWhoClicked();
        Player p = (Player) h;
        UserData data = UserData.getUserData(p);

        int rand = RandomSystem.random.nextInt(100);
        switch (e.getCurrentItem().getType()) {
            case NETHER_STAR:
                if(inventory.getItem(15) != null){
                    p.sendMessage("먼저 합성 완료된 아이템을 꺼내주세요.");
                }else if(inventory.getItem(11) == null || inventory.getItem(13) == null){
                    p.sendMessage("먼저 재료칸에 적절한 재료를 채워주세요.");
                }else if(CustomItemUtility.equals(inventory.getItem(11), CustomItemUtility.LowStoneItem()) &&
                        CustomItemUtility.equals(inventory.getItem(13), CustomItemUtility.LowStoneItem())){
                    if(rand < 30){
                        Fusion(CustomItemUtility.MiddleStoneItem());
                        p.sendMessage("합성 성공!");
                        p.playSound(p.getLocation(), Sound.ENTITY_CHICKEN_EGG, 1, 1);
                    }else{
                        Fusion(CustomItemUtility.LowStoneItem());
                        p.sendMessage("합성 실패.");
                        p.playSound(p.getLocation(), Sound.ENTITY_CHICKEN_EGG, 1, 1);
                    }
                }else if(CustomItemUtility.equals(inventory.getItem(11), CustomItemUtility.LowStoneItem()) &&
                        CustomItemUtility.equals(inventory.getItem(13), CustomItemUtility.NetherStartItem())){
                    Fusion(CustomItemUtility.LowStoneItem());
                    p.sendMessage("리롤 성공!");
                    p.playSound(p.getLocation(), Sound.ENTITY_CHICKEN_EGG, 1, 1);
                }else if(CustomItemUtility.equals(inventory.getItem(11), CustomItemUtility.MiddleStoneItem()) &&
                        CustomItemUtility.equals(inventory.getItem(13), CustomItemUtility.MiddleStoneItem())){
                    if(rand < 5){
                        Fusion(CustomItemUtility.TopStoneItem());
                        p.sendMessage("합성 성공!");
                        p.playSound(p.getLocation(), Sound.ENTITY_CHICKEN_EGG, 1, 1);
                        Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&',
                                "&c[&6[&e[ &a"+p.getName()+"&f님이 &c상급 강화석 &f합성에 성공하셨습니다! &e]&6]&c]"));
                    }else{
                        Fusion(CustomItemUtility.MiddleStoneItem());
                        p.sendMessage("합성 실패.");
                        p.playSound(p.getLocation(), Sound.ENTITY_CHICKEN_EGG, 1, 1);
                    }
                    //성공시 XXXX님이 상급 강화석 합성에 성공하셨습니다
                }else if(CustomItemUtility.equals(inventory.getItem(11), CustomItemUtility.MiddleStoneItem()) &&
                        CustomItemUtility.equals(inventory.getItem(13), CustomItemUtility.NetherStartItem())){
                    Fusion(CustomItemUtility.MiddleStoneItem());
                    p.sendMessage("리롤 성공!");
                    p.playSound(p.getLocation(), Sound.ENTITY_CHICKEN_EGG, 1, 1);
                }else if(CustomItemUtility.equals(inventory.getItem(11), CustomItemUtility.TopStoneItem()) &&
                        CustomItemUtility.equals(inventory.getItem(13), CustomItemUtility.NetherStartItem())){
                    Fusion(CustomItemUtility.TopStoneItem());
                    p.sendMessage("리롤 성공!");
                    p.playSound(p.getLocation(), Sound.ENTITY_CHICKEN_EGG, 1, 1);
                }else{
                    p.sendMessage("먼저 재료칸에 적절한 재료를 채워주세요.");
                }
                break;
            case BARRIER:
                p.closeInventory();
                new RuneMenu(PlayerMenuUtility.getPlayerMenuUtility(playerMenuUtility.getOwner())).open();
                break;
        }
        setInventory();


    }

    void Fusion(ItemStack item){
        inventory.getItem(11).setAmount(inventory.getItem(11).getAmount() - 1);
        inventory.getItem(13).setAmount(inventory.getItem(13).getAmount() - 1);
        inventory.setItem(15, item);
    }

    @Override
    public void setInventory() {
        Player p = playerMenuUtility.getOwner();
        UserData data = UserData.getUserData(playerMenuUtility.getOwner());

        ItemStack glass = new ItemStack(Material.STAINED_GLASS_PANE, 1);
        glass.setDurability((byte)5);

        button(inventory, Material.STAINED_GLASS_PANE, ChatColor.GREEN + "[ 룬 재료1 ]", 2);
        button(inventory, Material.STAINED_GLASS_PANE, ChatColor.GREEN + "[ 룬 재료2 ]", 4);
        button(inventory, glass, ChatColor.GREEN + "[ 결과 ]", 6);

        List<String> lore4 = Arrays.asList(ChatColor.RESET+"룬 + 룬 = 합성, 룬 + 네더의별 = 리롤");
        button(inventory, Material.NETHER_STAR, ChatColor.GREEN + "[ 합성 ]", 22, lore4);
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
