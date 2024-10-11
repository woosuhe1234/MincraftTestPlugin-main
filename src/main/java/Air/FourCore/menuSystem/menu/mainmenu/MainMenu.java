package Air.FourCore.menuSystem.menu.mainmenu;

import Air.FourCore.item.CustomItemUtility;
import Air.FourCore.menuSystem.Menu;
import Air.FourCore.menuSystem.MenuUtility;
import Air.FourCore.menuSystem.PlayerMenuUtility;
import Air.FourCore.menuSystem.menu.etcmenu.NationListMenu;
import Air.FourCore.menuSystem.menu.etcmenu.PlayerListMenu;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import Air.FourCore.FourCore;
import Air.FourCore.user.BagData;
import Air.FourCore.user.UserData;

import java.util.Arrays;
import java.util.List;

import static Air.FourCore.menuSystem.MenuUtility.button;
import static Air.FourCore.menuSystem.MenuUtility.fillLineBackground;

public class MainMenu extends Menu {

    public MainMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        return ChatColor.GOLD + "" + ChatColor.BOLD + "[ Four Network ]";
    }

    @Override
    public int getSlots() {
        return 27;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        if (e.getClickedInventory().getType() == InventoryType.PLAYER) return;
        HumanEntity h = e.getWhoClicked();
        Player p = Bukkit.getPlayer(h.getUniqueId());

        switch (e.getCurrentItem().getType()) {
            case SKULL_ITEM:
                if (e.getCurrentItem().getDurability() == 0) {
                    new PlayerListMenu(PlayerMenuUtility.getPlayerMenuUtility(p)).open();
                } else {
                    PlayerInfoMenu menu = new PlayerInfoMenu(PlayerMenuUtility.getPlayerMenuUtility(playerMenuUtility.getOwner()));
                    menu.target = playerMenuUtility.getOwner();
                    menu.open();
                }
                break;
            case LAPIS_BLOCK:
                new NationListMenu(PlayerMenuUtility.getPlayerMenuUtility(p)).open();
                break;
            case CAULDRON_ITEM:
                h.openInventory(Bukkit.createInventory(null, 45, ChatColor.BOLD + "쓰레기통" + ChatColor.BOLD + "(닫으면 사라집니다!)"));
                break;
            case CHEST:
                h.openInventory(BagData.getInventory(h.getUniqueId()));
                BagData.getInventory(h.getUniqueId());
                break;
            case BOOK:
                p.sendMessage(ChatColor.RESET + "홈관련: /홈, /셋홈, /홈2, /셋홈2");
                p.sendMessage(ChatColor.RESET + "국가: /국가, /전쟁, /랭킹, /대련");
                p.sendMessage(ChatColor.RESET + "메뉴: /메뉴, /쓰레기통, /가방, /정보, /스텟, /특성, /퀘스트, /워프 ...");
                p.sendMessage(ChatColor.RESET + "정보: /돈, /돈 보내기, /캐시, /가이드, /관리자");
                p.sendMessage(ChatColor.RESET + "기타: /기본템, /보관함, /거래, /튜토리얼, /개조예시");
                p.sendMessage(ChatColor.RESET + "──────────────────────────────────");
                p.sendMessage(ChatColor.GOLD + "후원 : " + ChatColor.YELLOW + ChatColor.UNDERLINE + "https://skhcs.com/fournetwork");
                p.sendMessage(ChatColor.GREEN + "카페 : " + ChatColor.YELLOW + ChatColor.UNDERLINE + "https://cafe.naver.com/fournetwork");
                p.sendMessage(ChatColor.BLUE + "디스코드 : " + ChatColor.YELLOW + ChatColor.UNDERLINE + "https://discord.gg/tajBH7vmDh");
                p.sendMessage(ChatColor.GREEN + "마인리스트 : " + ChatColor.YELLOW + ChatColor.UNDERLINE + "https://minelist.kr/servers/fournetwork.kr");
                p.sendMessage(ChatColor.RESET + "──────────────────────────────────");
                break;
            case FLINT_AND_STEEL:
                //h.closeInventory();
                break;
            case BEACON:
                new NationWarpMenu(PlayerMenuUtility.getPlayerMenuUtility(p)).open();
                break;
            case MAGMA_CREAM:
                new CoinMenu(PlayerMenuUtility.getPlayerMenuUtility(p)).open();
                break;
            case EXP_BOTTLE:
                new StateMenu(PlayerMenuUtility.getPlayerMenuUtility(p)).open();
                break;
            case KNOWLEDGE_BOOK:
                new AbilityMenu(PlayerMenuUtility.getPlayerMenuUtility(p)).open();
                break;
            case TOTEM:
                new QuestMenu(PlayerMenuUtility.getPlayerMenuUtility(p)).open();
                break;
            case COMPASS:
                new WarpMenu(PlayerMenuUtility.getPlayerMenuUtility(p)).open();
                break;
            case EMERALD:
                new RuneMenu(PlayerMenuUtility.getPlayerMenuUtility(p)).open();
                break;
            case STORAGE_MINECART:
                UserData data = UserData.getUserData(p);
                if (!data.setting.isRewarded) {
                    data.setting.isRewarded = true;
                    p.getInventory().addItem(CustomItemUtility.NetherStartItemNone(1));
                    FourCore.economy.depositPlayer(p, 50000.0);
                    p.sendMessage(ChatColor.RESET + "일일보상: 1 네더의 별, 5 만원");
                    p.playSound(p.getLocation(), Sound.ENTITY_CHICKEN_EGG, 1, 1);
                } else {
                    p.sendMessage(ChatColor.RESET + "이미 보상을 수령하셨습니다. 매일 밤 12시에 초기화 됩니다.");
                }
                break;
            case POWERED_MINECART:
                new RewardMenu(PlayerMenuUtility.getPlayerMenuUtility(p)).open();
                break;
        }
    }

    @Override
    public void setInventory() {
        List<String> 쓰레기통 = Arrays.asList(
                ChatColor.RESET + "- 아이템 처분이 가능한 쓰레기통을 불러옵니다.",
                ChatColor.RED + "※ 개인의 실수로 발생한 일의 경우, 복구를 해드리지 않습니다."
        );
        button(inventory, Material.CAULDRON_ITEM, ChatColor.GOLD + "[ 쓰레기통 ]", 1, 쓰레기통);
        List<String> 가방 = Arrays.asList(
                ChatColor.RESET + "- 54칸의 개인 가방을 엽니다.",
                ChatColor.RED + "※ 렉 발생 시 넣고있던 아이템이 증발 할 수도 있습니다."
        );
        button(inventory, Material.CHEST, ChatColor.GOLD + "[ 가방 ]", 2, 가방);
        List<String> 정보 = Arrays.asList(
                ChatColor.RESET + "- 클릭 시 내 정보를 확인합니다."
        );

        button(inventory, Material.SKULL_ITEM, ChatColor.GOLD + "[ 플레이어 목록 ]", 3);

        // 플레이어 헤드
        ItemStack button = MenuUtility.getPlayerHead();
        ItemMeta button_meta = button.getItemMeta();
        button_meta.setDisplayName(ChatColor.GOLD + "[ 정보 ]");
        if (정보 != null)
            button_meta.setLore(정보);
        button.setItemMeta(button_meta);
        inventory.setItem(4, button);

        button(inventory, Material.LAPIS_BLOCK, ChatColor.GOLD + "[ 국가 목록 ]", 5);

        List<String> 도움말 = Arrays.asList(
                ChatColor.RESET + "- 서버의 명령어 목록을 볼 수 있습니다."
        );
        button(inventory, Material.BOOK, ChatColor.GOLD + "[ 도움말 ]", 6, 도움말);
        List<String> 설정 = Arrays.asList(
                ChatColor.RESET + "- 서버 플레이 옵션을 설정할 수 있습니다."
        );
        button(inventory, Material.FLINT_AND_STEEL, ChatColor.GOLD + "[ 설정 ]", 7, 설정);

        List<String> 국가워프 = Arrays.asList(
                ChatColor.RESET + "- 국가의 워프 포인트로 이동할 수 있습니다.",
                ChatColor.GRAY + "※ 국가에 속해있는 경우에만 가능합니다."
        );
        button(inventory, Material.BEACON, ChatColor.GOLD + "[ 국가워프 ]", 13, 국가워프);

        List<String> 스텟 = Arrays.asList(
                ChatColor.RESET + "- 스텟 포인트를 투자해 능력치를 올릴 수 있습니다.",
                "",
                ChatColor.GREEN + "[ 남은 포인트 ]",
                ChatColor.GREEN + " ▶ " + ChatColor.RESET + UserData.getUserData(playerMenuUtility.getOwner()).state
        );
        button(inventory, Material.EXP_BOTTLE, ChatColor.GOLD + "[ 스텟 ]", 19, 스텟);
        List<String> 특성 = Arrays.asList(
                ChatColor.RESET + "- 다양한 특성을 사용할 수 있습니다.."
        );
        button(inventory, Material.KNOWLEDGE_BOOK, ChatColor.GOLD + "[ 특성 ]", 20, 특성);
        List<String> 퀘스트 = Arrays.asList(
                ChatColor.RESET + "- 퀘스트를 통해 경험치와 보상을 얻을 수 있습니다."
        );
        button(inventory, Material.TOTEM, ChatColor.GOLD + "[ 퀘스트 ]", 21, 퀘스트);
        List<String> 워프 = Arrays.asList(
                ChatColor.RESET + "- 서버에 존재하는 워프로 간편하게 이동할 수 있습니다."
        );
        button(inventory, Material.COMPASS, ChatColor.GOLD + "[ 워프 ]", 22, 워프);
        List<String> 룬 = Arrays.asList(
                ChatColor.RESET + "- 룬을 통해 추가 능력을 얻을 수 있습니다."
        );
        button(inventory, Material.EMERALD, ChatColor.GOLD+"[ 룬 ]", 23,룬);
        List<String> 일일보상 = Arrays.asList(
                ChatColor.RESET + "- 일일 보상을 확인, 지급받을 수 있습니다.",
                ChatColor.GRAY + "- 네더의 별 뽑기, 돈 상자"
        );
        button(inventory, Material.STORAGE_MINECART, ChatColor.GOLD + "[ 일일보상 ]", 24, 일일보상);
        List<String> 접속보상 = Arrays.asList(
                ChatColor.RESET + "- 등급에 따른 보상을 확인, 지급받을 수 있습니다."
        );
        button(inventory, Material.POWERED_MINECART, ChatColor.GOLD + "[ 접속보상 ]", 25, 접속보상);

        /*
        List<String> 코인 = Arrays.asList(
                ChatColor.RESET + "- 실제 시세를 기반으로 코인에 투자할 수 있습니다.",
                ChatColor.GRAY + "※ 테스트중인 기능입니다."
        );
        button(inventory, Material.MAGMA_CREAM, ChatColor.GOLD + "[ 코인 ]", 26, 코인);
         */

        fillLineBackground(inventory, 0, true);
        fillLineBackground(inventory, 8, true);
    }

}
