package Air.FourCore.task.worldTask;

import Air.FourCore.menuSystem.MenuUtility;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import Air.FourCore.WorldSetting;
import Air.FourCore.user.UserData;

public class BattleTime extends BattleTimerable {

    protected UserData data;

    public BattleTime(long timer, Player p1, Player p2, int index, Location loc1, Location loc2) {
        super(timer, p1, p2, index, loc1, loc2);
    }

    @Override
    public void tick() {
        if (!p1.isOnline() || p1 == null) {
            win(p2, p1);
            p2.sendMessage("상대가 나갔습니다.");
        }
        if (!p2.isOnline() || p2 == null) {
            win(p1, p2);
            p2.sendMessage("상대가 나갔습니다.");
        }
    }

    @Override
    public void stop() {
        WorldSetting.battlePlayer[index] = null;
        WorldSetting.battlePlayer[index+1] = null;
        Location loc = MenuUtility.getLocation("로비");
        if (loc != null) {
            p1.teleport(loc);
            p2.teleport(loc);
        }else{
            p1.teleport(loc1);
            p2.teleport(loc2);
        }
        super.stop();
    }

    @Override
    public void end() {
        WorldSetting.battlePlayer[index] = null;
        WorldSetting.battlePlayer[index+1] = null;
        Location loc = MenuUtility.getLocation("로비");
        if (loc != null) {
            p1.teleport(loc);
            p2.teleport(loc);
        }else{
            p1.teleport(loc1);
            p2.teleport(loc2);
        }
        p1.sendTitle(ChatColor.RED + "전투 종료", "시간초과", 10, 60, 20);
    }

    public void win(Player winner, Player loser) {
        if(winner != null) {
            winner.sendTitle(ChatColor.YELLOW + "전투 승리!", "", 10, 60, 20);
            Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&',
                    "&e[ "+winner.getName()+" ] &f님이 대련에서 승리하셨습니다! &e[ CH."+(index+1)+" ] "));
        }
        if(loser != null)
            loser.sendTitle(ChatColor.RED + "전투 패배", "", 10, 60, 20);
        stop();
    }

}
