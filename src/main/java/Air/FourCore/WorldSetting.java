package Air.FourCore;

import Air.FourCore.menuSystem.MenuUtility;
import Air.FourCore.nation.NationData;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import Air.FourCore.task.Timerable;
import Air.FourCore.task.worldTask.BattleReadyTime;
import Air.FourCore.user.UserUtility;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class WorldSetting {
    public static LinkedList<Timerable> timerList = new LinkedList<>();

    public static boolean enableSave = Bukkit.getWorld("myworld") == null;
    public static LocalDateTime lastResetReward;

    public static int ExpEvent = 0;
    public static boolean event = false;
    public static boolean blockWar = false;
    public static boolean counterWar = false;
    public static NationData warSender = null;
    public static NationData warReceiver = null;
    public static NationData warSenderAllie = null;
    public static NationData warReceiverAllie = null;

    public static Player[] battlePlayer = new Player[8]; // 4개 채널
    public static BossRoom[] bossRooms = new BossRoom[8]; // 각각 2개 채널

    public static void stopWar(){
        WorldSetting.warSender = null;
        WorldSetting.warReceiver = null;
        WorldSetting.warSenderAllie = null;
        WorldSetting.warReceiverAllie = null;
        WorldSetting.counterWar = false;
    }

    public static boolean isWar(){
        return warSender != null || warReceiver != null;
    }

    public static void startBattle(Player p1, Player p2){
        int index = isFullBattle();

        if(index < 0){
            p1.sendMessage("대련 채널이 가득찼습니다. 잠시 후에 다시 시도해주세요.");
            p2.sendMessage("대련 채널이 가득찼습니다. 잠시 후에 다시 시도해주세요.");
        }else{
            Location l1 = p1.getLocation(), l2 = p2.getLocation();
            battlePlayer[index] = p1;
            battlePlayer[index+1] = p2;
            Location loc1 = MenuUtility.getLocation("대련A-"+(index/2+1));
            Location loc2 = MenuUtility.getLocation("대련B-"+(index/2+1));
            if(loc1 != null)
                UserUtility.teleport(p1, loc1);
            if(loc2 != null)
                UserUtility.teleport(p2, loc2);
            new BattleReadyTime(5, p1, p2, index, l1, l2);
        }

    }

    public static int isFullBattle() {
        int index = -1;
        for (int i = 0; i < battlePlayer.length; i+=2) {
            if(battlePlayer[i] == null && battlePlayer[i+1] == null) {
                //isFull = false;
                index = i;
                break;
            }
        }
        return index;
    }

    public static <U extends Timerable> U getTimerable(Class<U> clazz) {
        for (Timerable t : timerList) {
            if (clazz.isInstance(t)) {
                U result = (U) t;
                if (result != null) {
                    return result;
                }
            }
        }
        return null;
    }

    public static <U extends Timerable> List<U> getTimerables(Class<U> clazz) {
        List<U> result = new ArrayList<>();
        for (Timerable t : timerList) {
            if (clazz.isInstance(t)) {
                U temp = (U) t;
                if (temp != null) {
                    result.add(temp);
                }
            }
        }
        return result;
    }


}
