package Air.FourCore.task.userRequest;

import Air.FourCore.user.UserData;
import org.bukkit.Bukkit;

public class RejoinNationCoolTime extends UserTimerable {

    public RejoinNationCoolTime(long timer, UserData data) {
        super(timer, data);
    }

    @Override
    public void tick() {
    }

    @Override
    public void end() {
        Bukkit.getPlayer(data.uuid).sendMessage("※ 국가 가입 쿨타임이 끝났습니다.");
    }
}
