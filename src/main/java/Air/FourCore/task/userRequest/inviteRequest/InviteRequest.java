package Air.FourCore.task.userRequest.inviteRequest;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import Air.FourCore.task.userRequest.UserTimerable;
import Air.FourCore.user.UserData;

public abstract class InviteRequest extends UserTimerable {

    public UserData sender;

    public InviteRequest(long timer, UserData data, UserData sender) {
        super(timer, data);
        this.sender = sender;
    }

    public void answer(boolean accept) {
        super.stop();
        Player p = Bukkit.getPlayer(data.uuid);
        Player s = Bukkit.getPlayer(sender.uuid);
        if (p != null) {
            if(accept){
                ReceiverAccept(p);
            }else{
                ReceiverRefuse(p);
            }
        }
        if (s != null) {
            if(accept){
                SenderAccept(s);
            }else{
                SenderRefuse(s);
            }
        }
    }

    abstract protected void ReceiverAccept(Player p);
    abstract protected void ReceiverRefuse(Player p);
    abstract protected void SenderAccept(Player p);
    abstract protected void SenderRefuse(Player p);
}
