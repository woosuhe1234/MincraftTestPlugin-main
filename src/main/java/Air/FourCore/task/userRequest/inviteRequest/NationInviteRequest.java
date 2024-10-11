package Air.FourCore.task.userRequest.inviteRequest;

import Air.FourCore.nation.NationData;
import Air.FourCore.nation.NationUtility;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import Air.FourCore.user.UserData;

public class NationInviteRequest extends InviteRequest {

    public NationData nation;

    public NationInviteRequest(long timer, UserData data, UserData sender, NationData nation) {
        super(timer, data, sender);
        this.nation = nation;
    }

    @Override
    public void tick() {
        Player p = Bukkit.getPlayer(data.uuid);
        if (p != null) {
            p.sendMessage(ChatColor.GREEN + nation.nationName + ChatColor.RESET + " 국가로부터 시민이 될 기회를 제안받았습니다!");
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a/수락 &f또는 &c/거절&f 명령어를 쳐주세요. &7"+timer+"초 남았습니다."));
        }
    }

    @Override
    public void end() {
        Player p = Bukkit.getPlayer(data.uuid);
        Player s = Bukkit.getPlayer(sender.uuid);
        if (p != null) {
            p.sendMessage(ChatColor.GRAY + "초대 요청이 만료되었습니다.");
        }
        if (s != null) {
            s.sendMessage(ChatColor.GRAY + "초대 요청이 만료되었습니다.");
        }
    }

    @Override
    protected void ReceiverAccept(Player p) {
        p.sendMessage(ChatColor.GRAY + "초대에 수락했습니다.");
        UserData.getUserData(p).setUserJob(UserData.Job.시민, nation);
        NationUtility.broadcastNation(nation,"&a"+p.getName()+ "&f님이 &6"+nation.nationName+"&f국가에 가입하셨습니다.");
    }

    @Override
    protected void ReceiverRefuse(Player p) {
        p.sendMessage(ChatColor.GRAY + "초대에 거절했습니다.");
    }

    @Override
    protected void SenderAccept(Player p) {
        p.sendMessage(ChatColor.GRAY + "상대가 국가 초대에 수락했습니다.");
    }

    @Override
    protected void SenderRefuse(Player p) {
        p.sendMessage(ChatColor.GRAY + "상대가 국가 초대에 거절했습니다.");
    }
}
