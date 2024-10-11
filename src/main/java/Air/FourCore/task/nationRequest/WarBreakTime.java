package Air.FourCore.task.nationRequest;

import Air.FourCore.nation.NationData;
import Air.FourCore.nation.NationUtility;
import org.bukkit.ChatColor;

public class WarBreakTime extends WarTimerable {

    public WarBreakTime(long timer, NationData nation, NationData targetNation) {
        super(timer, nation, targetNation);
    }

    @Override
    public void tick() {

    }

    @Override
    public void end() {
        new WarTime(900, nation , targetNation);
        //super.stop();
        if(nation != null)
            nation.timerList.remove(this);
        if(targetNation != null)
            targetNation.timerList.remove(this);
        NationUtility.broadcastTitleNation(nation, ChatColor.GOLD+"[ 국가 전쟁 ]", "전쟁 시작");
        NationUtility.broadcastTitleNation(targetNation, ChatColor.GOLD+"[ 국가 전쟁 ]", "전쟁 시작");
    }

    @Override
    public void stop(){
        super.stop();
    }

}
