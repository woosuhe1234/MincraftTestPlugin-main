package Air.FourCore.task.nationRequest;

import Air.FourCore.nation.NationData;
import Air.FourCore.nation.NationUtility;

public class ChangeViceroyCoolTime extends NationTimerable {
    public ChangeViceroyCoolTime(long timer, NationData nation) {
        super(timer, nation);
    }

    @Override
    public void tick() {

    }

    @Override
    public void end() {
        NationUtility.broadcastNation(nation, NationUtility.nation("※ 부왕 부여 쿨타임이 끝났습니다."));
    }
}
