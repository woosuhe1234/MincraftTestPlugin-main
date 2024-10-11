package Air.FourCore.task.nationRequest;

import Air.FourCore.nation.NationData;
import Air.FourCore.nation.NationUtility;
import org.bukkit.Bukkit;
import Air.FourCore.WorldSetting;

public abstract class WarTimerable extends NationTimerable {

    public NationData targetNation;

    public WarTimerable(long timer, NationData nation, NationData targetNation){
        // 두 국가에서 1씩 감소라서 타이머 두배로
        super(timer * 2, nation);
        this.targetNation = targetNation;
        targetNation.timerList.add(this);
    }

    @Override
    public void stop(){
        new WarCoolTime(300, nation);
        new WarCoolTime(300, targetNation);
        WorldSetting.stopWar();
        Bukkit.broadcastMessage(NationUtility.nation("&6[ " + nation.nationName + " ] &f국과 &a[ " + targetNation.nationName + " ] &f국의 전쟁이 끝났습니다."));

        if(targetNation != null)
            targetNation.timerList.remove(this);
        super.stop();
    }

}
