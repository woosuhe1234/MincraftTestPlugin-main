package Air.FourCore.task.worldTask;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import Air.FourCore.task.Timerable;
import Air.FourCore.WorldSetting;

public abstract class BattleTimerable extends Timerable {

    public Player p1, p2;
    protected int index;
    protected Location loc1, loc2;

    public BattleTimerable(long timer, Player p1, Player p2, int index, Location loc1, Location loc2){
        this.timer = timer;
        this.p1 = p1;
        this.p2 = p2;
        this.index = index;
        this.loc1 = loc1;
        this.loc2 = loc2;
        WorldSetting.timerList.add(this);
    }

    public void stop(){
        WorldSetting.timerList.remove(this);
    }

}
