package Air.FourCore.task.userRequest;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import Air.FourCore.user.UserData;

public class WarpTime extends UserTimerable {

    Location loc;

    public WarpTime(long timer, UserData data, Location loc) {
        super(timer, data);
        this.loc = loc;
        tick();
    }

    @Override
    public void tick() {
        Bukkit.getPlayer(data.uuid).sendTitle(ChatColor.GREEN+"[WARP]", ChatColor.YELLOW+""+timer+"초"+ChatColor.RESET+"간 가만히 기다려주세요.", 0, 20, 5);
    }

    @Override
    public void end() {
        if(data != null) {
            OfflinePlayer p = Bukkit.getPlayer(data.uuid);
            if(p != null && p.isOnline() && loc != null)
                p.getPlayer().teleport(loc);
            // 경고때매 임시추가
            data.timerList.remove(this);
        }
    }

    @Override
    public void stop(){
        super.stop();
        Bukkit.getPlayer(data.uuid).sendTitle(ChatColor.GREEN+"[WARP]", "움직임이 감지되어 워프가 취소되었습니다.", 0, 20, 20);
    }
}
