package Air.FourCore.task.nationRequest;

import Air.FourCore.nation.NationData;
import Air.FourCore.task.Timerable;

public abstract class NationTimerable extends Timerable {

    public NationData nation;

    public NationTimerable(long timer, NationData nation){
        this.timer = timer;
        this.nation = nation;
        nation.timerList.add(this);
    }

    public void stop(){
        if(nation != null)
            nation.timerList.remove(this);
    }
}
