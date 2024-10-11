package Air.FourCore.task.userRequest.inviteRequest;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import Air.FourCore.WorldSetting;
import Air.FourCore.user.UserData;

public class BattleInviteRequest extends InviteRequest {

    public BattleInviteRequest(long timer, UserData data, UserData sender) {
        super(timer, data, sender);
    }

    @Override
    public void tick() {
        Player p = Bukkit.getPlayer(data.uuid);
        if (p != null) {
            p.sendMessage(ChatColor.GOLD + sender.userName + ChatColor.RESET + " 님으로 부터 대련 신청을 받았습니다.");
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a/수락 &f또는 &c/거절&f 명령어를 쳐주세요. &7" + timer + "초 남았습니다."));
        }
    }

    @Override
    public void end() {
        Player p = Bukkit.getPlayer(data.uuid);
        Player s = Bukkit.getPlayer(sender.uuid);
        if (p != null) {
            p.sendMessage(ChatColor.GRAY + "대련 신청이 만료되었습니다.");
        }
        if (s != null) {
            s.sendMessage(ChatColor.GRAY + "대련 신청이 만료되었습니다.");
        }
    }

    @Override
    protected void ReceiverAccept(Player p) {
        p.sendMessage(ChatColor.GRAY + "대련에 수락했습니다.");
        Player s = Bukkit.getPlayer(sender.uuid);
        WorldSetting.startBattle(s, p);
    }

    @Override
    protected void ReceiverRefuse(Player p) {
        p.sendMessage(ChatColor.GRAY + "대련에 거절했습니다.");
    }

    @Override
    protected void SenderAccept(Player p) {
        p.sendMessage(ChatColor.GRAY + "상대가 대련 신청에 수락했습니다.");
    }

    @Override
    protected void SenderRefuse(Player p) {
        p.sendMessage(ChatColor.GRAY + "상대가 대련 신청에 거절했습니다.");
    }
}
