package Air.FourCore.task.userRequest;

import Air.FourCore.user.UserData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class DominationMatchingTime extends UserTimerable {

    public DominationMatchingTime(long timer, UserData data) {
        super(timer, data);
    }

    @Override
    public void tick() {
        timer+=2;
        if(timer % 30 == 0){
            Player p = Bukkit.getPlayer(data.uuid);
            if(p != null)
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7※ 점령전 매칭중입니다. 취소하려면 &e/점령전 매칭 &7을 한번 더 쳐주세요."));
        }
    }

    @Override
    public void end() {

    }
}
