package Air.FourCore.user;

import Air.FourCore.item.CustomItemUtility;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GiveCommandExecutor implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            System.out.println("버킷에서는 할 수 없습니다.");
            return true;
        }
        Player player = (Player) sender;
        if (args.length == 0) {
            player.sendMessage(ChatColor.RESET + "/지급 [아이템] [갯수] [닉네임(생략시 자신)]");
            player.sendMessage(ChatColor.RESET + "지급가능 아이템:");
            player.sendMessage(ChatColor.RESET + "네더의별, 네더의별상자, 스텟초기화, 동맹권, 국가강화석, 성, 철문폭탄, 힐링포션, 좌표탐지권, 국가쿨타임초기화권, 영혼, 하/중/상급강화석");
            return true;
        }
        Player target = player;
        if (args.length >= 3) {
            target = Bukkit.getPlayer(args[2]);
            if (target == null) {
                player.sendMessage(ChatColor.RESET + "해당 플레이어가 없습니다.");
                return true;
            }
        }
        int amount = 1;
        try {
            amount = Integer.parseInt(args[1]);
        } catch (Exception e) {}
        if(args[0].equalsIgnoreCase("네더의별")){
            target.getInventory().addItem(CustomItemUtility.NetherStartItem(amount));
        } else if (args[0].equalsIgnoreCase("네더의별상자")) {
            for (int i=0; i<amount; i++) target.getInventory().addItem(CustomItemUtility.NetherStartBoxItem());
        } else if (args[0].equalsIgnoreCase("스텟초기화")) {
            for (int i=0; i<amount; i++) target.getInventory().addItem(CustomItemUtility.StatResetItem());
        } else if (args[0].equalsIgnoreCase("동맹권")) {
            for (int i=0; i<amount; i++) target.getInventory().addItem(CustomItemUtility.AllianceTicketItem());
        } else if (args[0].equalsIgnoreCase("국가강화석")) {
            for (int i=0; i<amount; i++) target.getInventory().addItem(CustomItemUtility.NationStoneItem());
        } else if (args[0].equalsIgnoreCase("성")) {
            for (int i=0; i<amount; i++) target.getInventory().addItem(CustomItemUtility.CastleItem());
        } else if (args[0].equalsIgnoreCase("철문폭탄")) {
            for (int i=0; i<amount; i++) target.getInventory().addItem(CustomItemUtility.IronDoorBombItem());
        } else if (args[0].equalsIgnoreCase("힐링포션")) {
            target.getInventory().addItem(CustomItemUtility.HealingPotionItem(amount));
        } else if (args[0].equalsIgnoreCase("좌표탐지권")) {
            target.getInventory().addItem(CustomItemUtility.DetectLocationItem(amount));
        } else if (args[0].equalsIgnoreCase("국가쿨타임초기화권")) {
            target.getInventory().addItem(CustomItemUtility.InitNationCoolTimeItem(amount));
        } else if (args[0].equalsIgnoreCase("영혼")) {
            target.getInventory().addItem(CustomItemUtility.SoulItem(amount));
        } else if (args[0].equalsIgnoreCase("하급강화석")) {
            for (int i=0; i<amount; i++) target.getInventory().addItem(CustomItemUtility.LowStoneItem());
        } else if (args[0].equalsIgnoreCase("중급강화석")) {
            for (int i=0; i<amount; i++) target.getInventory().addItem(CustomItemUtility.MiddleStoneItem());
        } else if (args[0].equalsIgnoreCase("상급강화석")) {
            for (int i=0; i<amount; i++) target.getInventory().addItem(CustomItemUtility.TopStoneItem());
        } else {
            player.sendMessage("해당 아이템은 지급 목록에 없습니다.");
            return true;
        }

        player.sendMessage("아이템 지급 완료");
        if(player != target)
            target.sendMessage("아이템 지급 완료");
        return true;
    }
}
