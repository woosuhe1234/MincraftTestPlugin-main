package Air.FourCore.task;

import Air.FourCore.nation.NationData;
import Air.FourCore.user.UserData;
import org.bukkit.scheduler.BukkitRunnable;
import Air.FourCore.WorldSetting;

import java.util.Iterator;

public class TaskManager extends BukkitRunnable {

    @Override
    public void run() {
        for (UserData data : UserData.map.values()) {
            for (Iterator<Timerable> it = data.timerList.iterator(); it.hasNext(); ) {
                Timerable timer = it.next();
                timer.timer--;
                if (timer.timer <= 0) {
                    it.remove();
                    timer.end();
                } else {
                    timer.tick();
                }
            }
        }
        for (NationData data : NationData.map.values()) {
            for (Iterator<Timerable> it = data.timerList.iterator(); it.hasNext(); ) {
                Timerable timer = it.next();
                timer.timer--;
                if (timer.timer <= 0) {
                    it.remove();
                    timer.end();
                } else {
                    timer.tick();
                }
            }
        }

        for (Iterator<Timerable> it = WorldSetting.timerList.iterator(); it.hasNext(); ) {
            Timerable timer = it.next();
            timer.timer--;
            if (timer.timer <= 0) {
                it.remove();
                timer.end();
            } else {
                timer.tick();
            }
        }
    }

}
