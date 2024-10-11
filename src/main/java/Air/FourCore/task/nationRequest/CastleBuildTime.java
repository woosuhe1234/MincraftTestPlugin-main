package Air.FourCore.task.nationRequest;

import Air.FourCore.nation.NationData;
import Air.FourCore.nation.NationUtility;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class CastleBuildTime extends NationTimerable {
    public CastleBuildTime(long timer, NationData nation) {
        super(timer, nation);
    }

    @Override
    public void tick() {
        if(timer % 20 == 0){
            Player p = Bukkit.getPlayer(nation.king);
            if(p != null){
                p.sendMessage(NationUtility.nation("완전한 국가가 되기 위해 &c1시간 &f안에 국가의 성을 건설해 주세요. \n&6" +timer/60+"분 남았습니다."));
            }
        }
    }

    @Override
    public void end() {
        if(nation.castles.size() > 0){ return; }
        Player p = Bukkit.getPlayer(nation.king);
        if(p != null){
            p.sendMessage(NationUtility.nation("1시간 동안 성을 짓지 않아 "+nation.nationName+"국가가 삭제되었습니다."));
        }
        nation.remove();
    }
}
