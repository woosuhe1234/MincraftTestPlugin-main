package Air.FourCore.task.userRequest;

import Air.FourCore.user.UserData;
import Air.FourCore.task.Timerable;

public abstract class UserTimerable extends Timerable {

    protected UserData data;

    public UserTimerable(long timer, UserData data){
        this.timer = timer;
        this.data = data;
        data.timerList.add(this);
    }

    public void stop(){
        if(data != null)
            data.timerList.remove(this);
    }

}
