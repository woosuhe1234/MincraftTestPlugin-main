package Air.FourCore.menuSystem.menu.shopmenu;

import Air.FourCore.menuSystem.PlayerMenuUtility;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

public class NetherWeaponShopMenu extends ShopMenu {

    public NetherWeaponShopMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    protected List<ShopItem> setItemList() {
        ItemStack w1 = getItemStack1(ChatColor.AQUA, "헤르마의 지팡이", "전령의 신 헤르마의 힘이 형상화 된 지팡이다.", "높게 도약한다.", 2);

        ItemStack w2 = getItemStack1(ChatColor.RED, "불카누스의 망치", "대장장이의 신 불카누스의 힘이 형상화 된 망치이다.", "5초간 집중한 후, 모든 아이템을 수리한다.", 1);

        ItemStack w3 = getItemStack1(ChatColor.GOLD, "케레스의 벼이삭", "대지의 여신 케레스의 힘이 형상화 된 벼이삭이다.", "대지를 풍요롭게 만들어 디버프를 준다.", 3);

        ItemStack w4 = getItemStack1(ChatColor.LIGHT_PURPLE, "비너스의 거울", "미의 여신 비너스의 힘이 형상화 된 거울이다.", "7초간 신비한 힘에 둘러싸인다.", 4);

        ItemStack w5 = getItemStack2(ChatColor.DARK_RED, "아폴로의 예언서", "태양의 신 아폴로의 힘이 형상화 된 예언서이다.", "운석을 떨어뜨려 폭발을 일으킨다.", "황금의 궁전을 자신에게 둘려싼다.", 5);

        ItemStack w6 = getItemStack2(ChatColor.DARK_GRAY, "마르스의 검", "전쟁의 신 마르스의 힘이 형상화 된 검이다.", "단 형식의 기술을 사용한다.", "검기를 날려 적을 벤다.", 6);

        ItemStack w7 = getItemStack2(ChatColor.GRAY, "디아나의 단검", "달의 신 디아나의 힘이 형상화 된 단검이다.", "적 뒤로 이동하며 디버프를 부여한다.","주위의 적을 달의 힘으로 끌어모은다.", 7);

        ItemStack w8 = getItemStack2(ChatColor.DARK_PURPLE, "바쿠스의 지팡이", "숲의 신 바쿠스의 힘이 형상화 된 지팡이다.", "숲의 파도를 날려 적을 띄운다.","술통을 소환해 적을 끌어온다.", 8);

        ItemStack w9 = getItemStack3(ChatColor.YELLOW, "유피테르의 창", "번개 신이라 불리던 유피테르의 힘이 형상화 된 창이다.", "유피테르의 창으로 공격 시 적을 침묵시킨다.", "뇌창을 날려 번개를 내려친다.","뇌창을 든 후, 빠른 속도로 전진한다.",9);

        ItemStack w10 = getItemStack3(ChatColor.BLUE, "넵투누스의 삼지창", "바다의 신 넵투누스의 힘이 형상화 된 삼지창이다.", "물 속에서 빠르게 이동한다.","물을 솟구쳐 적들을 띄운다.","균열을 생성하는 읍집체를 발사한다.", 10);

        ItemStack w11 = getItemStack3(ChatColor.BLACK, "플루토의 낫", "저승의 신 플루토의 힘이 형상화 된 낫이다.", "1%확률로 적의 공격을 회피한다.","우클릭시 전방을 2번 베어버린다.","밤의 폭풍에 숨어 5초간 은신한다.", 11);

        ItemStack w12 = getItemStack3(ChatColor.DARK_AQUA, "미네르바의 방패", "지혜의 여신 미네르바의 힘이 형상화 된 방패이다.", "피격 시 적들을 석화시킨다.","기를 발산해 적들을 띄운다.","신의 방패 아이기스를 들어올린다.", 12);

        return Arrays.asList(
                null,
                new ShopItem(w1, 40, -1, ShopItem.Moneys.네더의별,"low2"),
                null,
                new ShopItem(w2, 40, -1, ShopItem.Moneys.네더의별,"low1"),
                null,
                new ShopItem(w3, 40, -1, ShopItem.Moneys.네더의별,"low3"),
                null,
                new ShopItem(w4, 40, -1, ShopItem.Moneys.네더의별,"low4"),
                null,
                null,
                new ShopItem(w5, 60, -1, ShopItem.Moneys.네더의별,"low5"),
                null,
                new ShopItem(w6, 60, -1, ShopItem.Moneys.네더의별,"low6"),
                null,
                new ShopItem(w7, 60, -1, ShopItem.Moneys.네더의별,"low7"),
                null,
                new ShopItem(w8, 60, -1, ShopItem.Moneys.네더의별,"low8"),
                null,
                null,
                new ShopItem(w9, 80, -1, ShopItem.Moneys.네더의별,"low9"),
                null,
                new ShopItem(w10, 80, -1, ShopItem.Moneys.네더의별,"low10"),
                null,
                new ShopItem(w11, 80, -1, ShopItem.Moneys.네더의별,"low11"),
                null,
                new ShopItem(w12, 80, -1, ShopItem.Moneys.네더의별,"low12")
        );
    }

    @NotNull
    private ItemStack getItemStack1(ChatColor aqua, String x, String x1, String x2, int d) {
        ItemStack w1 = new ItemStack(Material.GOLD_SWORD);
        ItemMeta m1 = w1.getItemMeta();
        m1.setDisplayName(aqua + x);
        m1.setLore(Arrays.asList(ChatColor.RESET+ x1,
                ChatColor.GOLD+"[ 무기 스킬 ]",
                ChatColor.GOLD+"우클릭 :: "+ChatColor.RESET+ x2));
        w1.setItemMeta(m1);
        w1.setDurability((short) d);
        return w1;
    }
    private ItemStack getItemStack2(ChatColor aqua, String x, String x1, String x2,String x3, int d) {
        ItemStack w1 = new ItemStack(Material.GOLD_SWORD);
        ItemMeta m1 = w1.getItemMeta();
        m1.setDisplayName(aqua + x);
        m1.setLore(Arrays.asList(ChatColor.RESET+ x1,
                ChatColor.GOLD+"[ 무기 스킬 ]",
                ChatColor.GOLD+"우클릭 :: "+ChatColor.RESET+ x2,
                ChatColor.GOLD+"쉬프트+우클릭 :: "+ChatColor.RESET+ x3));
        w1.setItemMeta(m1);
        w1.setDurability((short) d);
        return w1;
    }

    private ItemStack getItemStack3(ChatColor aqua, String x, String x1, String x2, String x3, String x4, int d) {
        ItemStack w1 = new ItemStack(Material.GOLD_SWORD);
        ItemMeta m1 = w1.getItemMeta();
        m1.setDisplayName(aqua + x);
        m1.setLore(Arrays.asList(ChatColor.RESET+ x1,
                ChatColor.GOLD+"[ 무기 스킬 ]",
                ChatColor.GOLD+"패시브 :: "+ChatColor.RESET+ x2,
                ChatColor.GOLD+"우클릭 :: "+ChatColor.RESET+ x3,
                ChatColor.GOLD+"쉬프트+우클릭 :: "+ChatColor.RESET+ x4));
        w1.setItemMeta(m1);
        w1.setDurability((short) d);
        return w1;
    }

    @Override
    public String getMenuName() {
        return ChatColor.YELLOW+"[ 신의 무기상점 ]";
    }

    @Override
    public int getSlots() {
        return 45;
    }
}
