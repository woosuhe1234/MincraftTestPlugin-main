package Air.FourCore.task.userRequest;

import Air.FourCore.user.UserData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class BattleModeTime extends UserTimerable {

    public BattleModeTime(long timer, UserData data) {
        super(timer, data);
        Bukkit.getPlayer(data.uuid).sendMessage(ChatColor.translateAlternateColorCodes('&', "&f&l[ &c전투모드 &f&l] &f전투 모드가 활성화되었습니다."));
    }

    @Override
    public void tick() {

    }

    @Override
    public void end() {
        Bukkit.getPlayer(data.uuid).sendMessage(ChatColor.translateAlternateColorCodes('&', "&f&l[ &c전투모드 &f&l] &f전투 모드가 종료되었습니다."));
    }

    @Override
    public void stop(){
        Bukkit.getPlayer(data.uuid).sendMessage(ChatColor.translateAlternateColorCodes('&', "&f&l[ &c전투모드 &f&l] &f전투 모드가 종료되었습니다."));
        super.stop();
    }
}
