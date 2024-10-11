package Air.FourCore.task.nationRequest;

import Air.FourCore.nation.NationData;
import Air.FourCore.nation.NationUtility;

public class WarNewbieCoolTime extends WarCoolTime {
    public WarNewbieCoolTime(long timer, NationData nation) {
        super(timer, nation);
    }

    @Override
    public void tick() {

    }

    @Override
    public void end() {
        NationUtility.broadcastNation(nation, NationUtility.nation("※ 뉴비국가 보호기간이 끝났습니다."));
    }
}
