package Air.FourCore.item;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import Air.FourCore.user.BagData;
import Air.FourCore.user.UserRune;

import java.util.Arrays;
import java.util.List;

public class CustomItemUtility {

    public static ItemStack NetherStartItem() {
        return NetherStartItem(1);
    }

    public static ItemStack NetherStartItem(int amount) {
        ItemStack item = new ItemStack(Material.NETHER_STAR, amount);
        ItemMeta item_meta = item.getItemMeta();
        List<String> lore = Arrays.asList(
                ChatColor.GRAY + "귀속아이템"
        );
        item_meta.setLore(lore);
        item.setItemMeta(item_meta);
        return item;
    }

    public static ItemStack NetherStartItemNone(int amount) {
        ItemStack item = new ItemStack(Material.NETHER_STAR, amount);
        ItemMeta item_meta = item.getItemMeta();
        List<String> lore = Arrays.asList(
                ChatColor.GRAY + "거래가능",
                ChatColor.GRAY + "귀속아이템"
        );
        item_meta.setLore(lore);
        item.setItemMeta(item_meta);
        return item;
    }

    public static ItemStack SuperHealthItem(int amount) {
        ItemStack item = new ItemStack(Material.SLIME_BALL, amount);
        ItemMeta item_meta = item.getItemMeta();
        item_meta.setDisplayName(ChatColor.GREEN + "특성 :: 초회복");
        List<String> lore = Arrays.asList(
                ChatColor.WHITE + "우 클릭시 체력 14칸 회복",
                ChatColor.WHITE + "쿨타임 120초",
                ChatColor.GRAY + "귀속아이템"
        );
        item_meta.setLore(lore);
        item.setItemMeta(item_meta);
        return item;
    }

    public static ItemStack HongDummyItem(int amount) {
        ItemStack item = new ItemStack(Material.WOOD_SWORD, amount);
        ItemMeta item_meta = item.getItemMeta();
        item_meta.setDisplayName(ChatColor.GOLD + "홍련");
        List<String> lore = Arrays.asList(
                ChatColor.GRAY + "귀속아이템"
        );
        item_meta.setLore(lore);
        item.setItemMeta(item_meta);
        return item;
    }

    public static ItemStack ElfBowDummyItem(int amount) {
        ItemStack item = new ItemStack(Material.SHEARS, amount);
        ItemMeta item_meta = item.getItemMeta();
        item_meta.setDisplayName(ChatColor.GREEN + "엘프의 활");
        List<String> lore = Arrays.asList(
                ChatColor.GRAY + "귀속아이템"
        );
        item_meta.setLore(lore);
        item.setItemMeta(item_meta);
        return item;
    }

    public static ItemStack SoulItem(int amount) {
        ItemStack item = new ItemStack(Material.PRISMARINE_SHARD, amount);
        ItemMeta item_meta = item.getItemMeta();
        item_meta.setDisplayName(ChatColor.DARK_PURPLE + "영혼");
        List<String> lore = Arrays.asList(
                ChatColor.WHITE + "사대수의 재료입니다."
        );
        item_meta.setLore(lore);
        item.setItemMeta(item_meta);
        return item;
    }

    public static ItemStack SoulPieceItem(int amount) {
        ItemStack item = new ItemStack(Material.BLAZE_POWDER, amount);
        ItemMeta item_meta = item.getItemMeta();
        item_meta.setDisplayName(ChatColor.LIGHT_PURPLE + "영혼 조각");
        List<String> lore = Arrays.asList(
                ChatColor.translateAlternateColorCodes('&',
                        "&f손에 들고 우클릭시 &e100만원&f을 소모하여 &5영혼&f을 제작합니다.")
        );
        item_meta.setLore(lore);
        item.setItemMeta(item_meta);
        return item;
    }

    public static ItemStack InitNationCoolTimeItem(int amount) {
        ItemStack item = new ItemStack(Material.PAPER, amount);
        ItemMeta item_meta = item.getItemMeta();
        item_meta.setDisplayName(ChatColor.YELLOW + "국가 쿨타임 초기화권");
        List<String> lore = Arrays.asList(
                ChatColor.WHITE + "국가 가입 쿨타임을 초기화시킵니다."
        );
        item_meta.setLore(lore);
        item.setItemMeta(item_meta);
        return item;
    }

    public static ItemStack DetectLocationItem(int amount) {
        ItemStack item = new ItemStack(Material.MAP, amount);
        ItemMeta item_meta = item.getItemMeta();
        item_meta.setDisplayName(ChatColor.AQUA + "좌표탐지권");
        List<String> lore = Arrays.asList(
                ChatColor.WHITE + "손에 들고 우클릭시 무작위 국가의 좌표를 알려줍니다.",
                ChatColor.WHITE + "자신의 국가와 동맹국의 좌표는 나오지 않습니다.",
                ChatColor.RED + "※ 좌표를 전체 채팅으로 발설하면 처벌받으실 수 있습니다.",
                ChatColor.GRAY + "귀속아이템"
        );
        item_meta.setLore(lore);
        item.setItemMeta(item_meta);
        return item;
    }

    public static ItemStack HealingPotionItem(int amount) {
        ItemStack item = new ItemStack(Material.INK_SACK, amount);
        ItemMeta item_meta = item.getItemMeta();
        item_meta.setDisplayName(ChatColor.RED + "힐링 포션");
        List<String> lore = Arrays.asList(
                ChatColor.WHITE + "사용시 체력을 3칸 회복합니다. 쿨타임 5초",
                ChatColor.WHITE + "던전에서만 사용할 수 있습니다."
        );
        item_meta.setLore(lore);
        item.setItemMeta(item_meta);
        return item;
    }

    public static ItemStack IronDoorBombItem() {
        ItemStack item = new ItemStack(Material.getMaterial(289), 1);
        ItemMeta item_meta = item.getItemMeta();
        item_meta.setDisplayName(ChatColor.DARK_RED + "철문 폭탄");
        List<String> lore = Arrays.asList(
                ChatColor.WHITE + "전쟁 중 철문에 우클릭하면 파괴할 수 있습니다."
        );
        item_meta.setLore(lore);
        item.setItemMeta(item_meta);
        return item;
    }

    public static ItemStack StatResetItem() {
        ItemStack item = new ItemStack(Material.PAPER, 1);
        ItemMeta item_meta = item.getItemMeta();
        item_meta.setDisplayName(ChatColor.DARK_RED + "스텟 초기화권");
        List<String> lore = Arrays.asList(
                ChatColor.GRAY + "거래가능",
                ChatColor.GRAY + "귀속아이템"
        );
        item_meta.setLore(lore);
        item.setItemMeta(item_meta);
        return item;
    }

    public static ItemStack NationStoneItem() {
        ItemStack item = new ItemStack(Material.PAPER, 1);
        ItemMeta item_meta = item.getItemMeta();
        item_meta.setDisplayName(ChatColor.DARK_PURPLE + "국가 강화석");
        List<String> lore = Arrays.asList(
                ChatColor.GRAY + "거래가능",
                ChatColor.GRAY + "귀속아이템"
        );
        item_meta.setLore(lore);
        item.setItemMeta(item_meta);
        return item;
    }

    public static ItemStack MoneyBoxItem(int i) {
        ItemStack item = new ItemStack(Material.CHEST, i);
        ItemMeta item_meta = item.getItemMeta();
        item_meta.setDisplayName(ChatColor.GOLD + "[ 돈 주머니 ]");
        List<String> lore = Arrays.asList(
                ChatColor.RESET + "손에 들고 허공에 우 클릭하여 사용합니다.",
                ChatColor.YELLOW + "확률 - 50%: 5만원, 50%: 10만원",
                ChatColor.GRAY + "귀속아이템"
        );
        item_meta.setLore(lore);
        item.setItemMeta(item_meta);
        return item;
    }

    public static ItemStack NetherStartBoxItem() {
        ItemStack item = new ItemStack(Material.CHEST, 1);
        ItemMeta item_meta = item.getItemMeta();
        item_meta.setDisplayName(ChatColor.YELLOW + "[ 네더의 별 상자 ]");
        List<String> lore = Arrays.asList(
                ChatColor.RESET + "손에 들고 허공에 우 클릭하여 사용합니다.",
                ChatColor.YELLOW + "확률 - 네더의 별 50%:3개, 30%:4개, 20%:5개",
                ChatColor.GRAY + "귀속아이템"
        );
        item_meta.setLore(lore);
        item.setItemMeta(item_meta);
        return item;
    }

    public static ItemStack NetherStartBoxItemNone() {
        ItemStack item = new ItemStack(Material.CHEST, 1);
        ItemMeta item_meta = item.getItemMeta();
        item_meta.setDisplayName(ChatColor.YELLOW + "[ 네더의 별 상자 ]"+ChatColor.GRAY + "(거래가능)");
        List<String> lore = Arrays.asList(
                ChatColor.RESET + "손에 들고 허공에 우 클릭하여 사용합니다.",
                ChatColor.YELLOW + "확률 - 네더의 별 50%:3개, 30%:4개, 20%:5개",
                ChatColor.GRAY + "거래가능",
                ChatColor.GRAY + "귀속아이템"
        );
        item_meta.setLore(lore);
        item.setItemMeta(item_meta);
        return item;
    }

    public static ItemStack AllianceTicketItem() {
        ItemStack item = new ItemStack(Material.PAPER, 1);
        ItemMeta item_meta = item.getItemMeta();
        item_meta.setDisplayName(ChatColor.AQUA + "동맹권");
        List<String> lore = Arrays.asList(
                ChatColor.RESET + "국가간 동맹을 위해 필요한 아이템입니다.",
                ChatColor.RESET + "양 국의 대표가 각각 들고있어야 합니다.",
                ChatColor.RESET + "/국가 동맹 [국가명] 으로 동맹을 신청해주세요.",
                ChatColor.GRAY + "거래가능",
                ChatColor.GRAY + "귀속아이템"
        );
        item_meta.setLore(lore);
        item.setItemMeta(item_meta);
        return item;
    }

    public static ItemStack ExpBuffItem1h() {
        ItemStack item = new ItemStack(Material.PAPER, 1);
        ItemMeta item_meta = item.getItemMeta();
        item_meta.setDisplayName(ChatColor.DARK_PURPLE + "경험치 x1.5 쿠폰 1시간");
        List<String> lore = Arrays.asList(
                ChatColor.RESET + "손에 들고 허공에 우 클릭하여 사용합니다.",
                ChatColor.RESET + "1시간 동안 지속됩니다.",
                ChatColor.RESET + "버프 시간은 [플레이어 정보]에서 확인 가능합니다.",
                ChatColor.GRAY + "귀속아이템"
        );
        item_meta.setLore(lore);
        item.setItemMeta(item_meta);
        return item;
    }

    public static ItemStack ExpBuffItem15m() {
        ItemStack item = new ItemStack(Material.PAPER, 1);
        ItemMeta item_meta = item.getItemMeta();
        item_meta.setDisplayName(ChatColor.DARK_PURPLE + "경험치 x1.5 쿠폰 15분");
        List<String> lore = Arrays.asList(
                ChatColor.RESET + "손에 들고 허공에 우 클릭하여 사용합니다.",
                ChatColor.RESET + "15분 동안 지속됩니다.",
                ChatColor.RESET + "버프 시간은 [플레이어 정보]에서 확인 가능합니다.",
                ChatColor.GRAY + "귀속아이템"
        );
        item_meta.setLore(lore);
        item.setItemMeta(item_meta);
        return item;
    }

    public static ItemStack CastleItem() {
        ItemStack item = new ItemStack(Material.BEACON, 1);
        ItemMeta item_meta = item.getItemMeta();
        item_meta.setDisplayName(ChatColor.AQUA + "성");
        List<String> lore = Arrays.asList(
                ChatColor.RESET + "국가를 세우고 야생에 성을 건설하세요.",
                ChatColor.GRAY + "거래가능",
                ChatColor.GRAY + "귀속아이템"
        );
        item_meta.setLore(lore);
        item.setItemMeta(item_meta);
        return item;
    }

    public static ItemStack LowStoneItem() {
        ItemStack item = new ItemStack(Material.DIAMOND_BARDING, 1);
        ItemMeta item_meta = item.getItemMeta();
        item_meta.setDisplayName(ChatColor.GREEN + "하급 강화석");
        List<String> lore = Arrays.asList(
                ChatColor.RESET + UserRune.getRandomString(1)
        );
        item_meta.setLore(lore);
        item.setItemMeta(item_meta);
        return item;
    }

    public static ItemStack MiddleStoneItem() {
        ItemStack item = new ItemStack(Material.DIAMOND_BARDING, 1);
        ItemMeta item_meta = item.getItemMeta();
        item_meta.setDisplayName(ChatColor.BLUE + "중급 강화석");
        List<String> lore = Arrays.asList(
                ChatColor.RESET + UserRune.getRandomString(2)
        );
        item_meta.setLore(lore);
        item.setItemMeta(item_meta);
        return item;
    }

    public static ItemStack TopStoneItem() {
        ItemStack item = new ItemStack(Material.DIAMOND_BARDING, 1);
        ItemMeta item_meta = item.getItemMeta();
        item_meta.setDisplayName(ChatColor.GOLD + "상급 강화석");
        List<String> lore = Arrays.asList(
                ChatColor.RESET + UserRune.getRandomString(3)
        );
        item_meta.setLore(lore);
        item.setItemMeta(item_meta);
        return item;
    }

    public static boolean equals(ItemStack item1, ItemStack item2) {
        if (item1 == null) return false;
        ItemMeta m1 = item1.getItemMeta(), m2 = item2.getItemMeta();
        if (m1 == null || m1.getDisplayName() == null){
            if (m2.getDisplayName() == null && item1.getType() == item2.getType()){
                return true;
            }else{
                return false;
            }
        }
        if (item1.getType() == item2.getType() && m1.getDisplayName().equalsIgnoreCase(m2.getDisplayName())) {
            return true;
        } else {
            return false;
        }
    }

    public static int amount(Inventory inventory, ItemStack item) {
        int result = 0;
        for (ItemStack i : inventory.getContents()) {
            if (equals(i, item)) {
                result += i.getAmount();
            }
        }
        return result;
    }

    public static boolean exist(Player p, ItemStack item){
        return CustomItemUtility.contains(p.getInventory(), item) ||
                CustomItemUtility.contains(p.getEnderChest(), item) ||
                CustomItemUtility.contains(BagData.getInventory(p.getUniqueId()), item);
    }

    public static boolean contains(Inventory inventory, ItemStack item) {
        boolean result = false;
        for (ItemStack i : inventory.getContents()) {
            if (equals(i, item)) {
                result = true;
                break;
            }
        }
        return result;
    }
    public static void remove(Inventory inventory, ItemStack item) {
        remove(inventory,item,item.getAmount());
    }

    public static void remove(Inventory inventory, ItemStack item, int amount) {
        for (ItemStack i : inventory.getContents()) {
            if (equals(i, item)) {
                if(amount > i.getAmount()){
                    amount -= i.getAmount();
                    i.setAmount(0);
                }else{
                    i.setAmount(i.getAmount() - amount);
                    amount = 0;
                }
                if(amount <= 0)
                    break;
            }
        }
    }

    public static boolean isEmptySpace(Inventory inventory){
        int size = 0;
        for(ItemStack item : inventory.getContents()){
            if(item == null)
                size ++;
            if(size >= 2)
                return true;
        }
        return false;
    }

    public static boolean isBelonging(ItemStack item) {
        try {
            List<String> lore = item.getItemMeta().getLore();
            for (String s : lore) {
                if (s.contains("귀속")) {
                    return true;
                }
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    public static boolean isTrading(ItemStack item) {
        try {
            List<String> lore = item.getItemMeta().getLore();
            for (String s : lore) {
                if (s.contains("거래가능")) {
                    return true;
                }
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }
}
