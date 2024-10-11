package Air.FourCore.nation;

import Air.FourCore.user.UserData;
import Air.FourCore.user.UserUtility;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import Air.FourCore.task.nationRequest.WarBreakTime;
import Air.FourCore.task.nationRequest.WarTime;
import Air.FourCore.task.nationRequest.WarTimerable;

import static Air.FourCore.nation.NationUtility.ex;
import static Air.FourCore.nation.NationUtility.nation;
import static Air.FourCore.WorldSetting.*;
import static Air.FourCore.user.UserData.getUserData;

public class WarCommandExecutor implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player p = (Player) sender;
        UserData data = UserData.getUserData(p);
        if (p != null) {
            if (args.length > 0) {
                if (args[0].equalsIgnoreCase("정보")) {
                    정보(args, p);
                } else if (args[0].equalsIgnoreCase("반격")) {
                    반격(args, p, data);
                }
            } else {
                p.sendMessage(nation("/전쟁 정보"));
                p.sendMessage(nation("/전쟁 반격"));
            }
        } else {
            System.out.println("You are not Player");
        }
        return true;
    }

    private void 반격(String[] args, Player p, UserData data) {
        NationData nation = data.getNation();
        if(nation == null){
            p.sendMessage(nation("국가가 없습니다."));
            return;
        }
        WarBreakTime t = nation.<WarBreakTime>getTimerable(WarBreakTime.class);
        if(t == null) {
            p.sendMessage(nation("전쟁 준비중이 아닙니다."));
            return;
        }
        if(nation != warReceiver){
            p.sendMessage(nation("수비측만 반격할 수 있습니다."));
            return;
        }
        if(counterWar){
            p.sendMessage(nation("이미 반격중입니다."));
            return;
        }
        if(nation.money < 2000000){
            p.sendMessage(nation("반격에 필요한 200만원이 부족합니다."));
            return;
        }
        UserUtility.brodcastSound(Sound.ENTITY_ENDERDRAGON_FIREBALL_EXPLODE);
        counterWar = true;
        nation.money -= 2000000;
        p.sendMessage(nation("반격에 200만원을 사용하였습니다."));
        Bukkit.broadcastMessage(nation("&6[ &e"+nation.nationName+" &f국가가 반격했습니다. &6]"));
    }

    private void 정보(String[] args, Player p) {
        if(isWar()){
            p.sendMessage(nation("전쟁 중\n"));
            UserData senderKing = UserData.getUserData(warSender.king);
            UserData receiverKing = UserData.getUserData(warReceiver.king);
            String s = "";
            String r = "";
            if(Bukkit.getPlayer(warSender.king) != null && Bukkit.getPlayer(warSender.king).isOnline()){
                s = "&e"+senderKing.userName+" (참여)";
            }else{
                s = "&7"+senderKing.userName+" (미참여)";
            }
            if(Bukkit.getPlayer(warReceiver.king) != null && Bukkit.getPlayer(warReceiver.king).isOnline()){
                r = "&e"+receiverKing.userName+" (참여)";
            }else{
                r = "&7"+receiverKing.userName+" (미참여)";
            }
            p.sendMessage(ex("공격: &c"+warSender.nationName+" 국가&f\n       "+s));
            if(warSenderAllie != null) p.sendMessage(ChatColor.RED+"       동맹참전: "+warSenderAllie.nationName);
            p.sendMessage(ex("방어: &a"+warReceiver.nationName+" 국가&f\n       "+r));
            if(warReceiverAllie != null) p.sendMessage(ChatColor.GREEN+"       동맹참전: "+warReceiverAllie.nationName);
            long time = 0;
            if(warSender.<WarTimerable>getTimerable(WarTimerable.class)!= null){
                time = warSender.<WarTimerable>getTimerable(WarTimerable.class).timer / 2;
            }
            int min = (int) (time * 0.0166666666666666666666666666666);    // 1/60
            long sec = time - (min * 60L);
            if(warSender.<WarBreakTime>getTimerable(WarBreakTime.class)!= null){
                p.sendMessage(ex("남은 준비시간: &e"+min+"분 "+sec+"초"));
            }else if(warSender.<WarTime>getTimerable(WarTime.class)!= null){
                p.sendMessage(ex("남은 전쟁시간: &e"+min+"분 "+sec+"초"));
            }
            p.sendMessage(ex("남은 전쟁시간: &e"+min+"분 "+sec+"초"));
            p.sendMessage("반격여부: " + (counterWar?"O":"X"));
        }else{
            p.sendMessage("전쟁중이 아닙니다.");
        }
    }
}
