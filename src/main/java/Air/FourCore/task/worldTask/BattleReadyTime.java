package Air.FourCore.task.worldTask;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import Air.FourCore.WorldSetting;
import Air.FourCore.user.UserData;

public class BattleReadyTime extends BattleTimerable {

    protected UserData data;

    public BattleReadyTime(long timer, Player p1, Player p2, int index, Location loc1, Location loc2){
        super(timer, p1, p2, index, loc1, loc2);
        Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&',
                "&6[ "+p1.getName()+" ] &f님과 &a[ "+p2.getName()+" ] &f님의 대련이 시작했습니다! &e[ CH."+(index+1)+" ] "));
        tick();
    }

    @Override
    public void tick() {
        p1.sendTitle(ChatColor.RESET+"전투 준비", timer+"초", 0, 20, 20);
        p2.sendTitle(ChatColor.RESET+"전투 준비", timer+"초", 0, 20, 20);
    }

    @Override
    public void end() {
        new BattleTime(300, p1, p2, index, loc1, loc2);
        WorldSetting.timerList.remove(this);
        p1.sendTitle(ChatColor.GOLD+"전투 시작!", "", 10, 60, 20);
        p2.sendTitle(ChatColor.GOLD+"전투 시작!", "", 10, 60, 20);
    }

}
