package Air.FourCore.task.userRequest.inviteRequest;

import Air.FourCore.menuSystem.PlayerMenuUtility;
import Air.FourCore.menuSystem.menu.etcmenu.TradeMenu;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import Air.FourCore.user.UserData;

public class TradeInviteRequest extends InviteRequest {

    public TradeInviteRequest(long timer, UserData data, UserData sender) {
        super(timer, data, sender);
    }

    @Override
    public void tick() {
        Player p = Bukkit.getPlayer(data.uuid);
        Player s = Bukkit.getPlayer(sender.uuid);
        if (p != null) {
            p.sendMessage(ChatColor.GOLD + sender.userName + ChatColor.RESET + " 님으로 부터 거래 신청이 왔습니다.");
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a/수락 &f또는 &c/거절&f 명령어를 쳐주세요. &7" + timer + "초 남았습니다."));
        }
        if (p == null || s == null) {
            if (p != null) {
                p.sendMessage(ChatColor.GRAY + "해당 플레이어가 나가서 거래가 취소되었습니다.");
            }
            if (s != null) {
                s.sendMessage(ChatColor.GRAY + "해당 플레이어가 나가서 거래가 취소되었습니다.");
            }
            if (data != null)
                data.timerList.remove(this);
        }
    }

    @Override
    public void end() {
        Player p = Bukkit.getPlayer(data.uuid);
        Player s = Bukkit.getPlayer(sender.uuid);
        if (p != null) {
            p.sendMessage(ChatColor.GRAY + "거래 요청이 만료되었습니다.");
        }
        if (s != null) {
            s.sendMessage(ChatColor.GRAY + "거래 요청이 만료되었습니다.");
        }
    }

    public void stop() {
        Player p = Bukkit.getPlayer(data.uuid);
        if (p != null) {
            p.sendMessage(ChatColor.GRAY + "거래 요청이 취소되었습니다.");
        }
        if (data != null)
            data.timerList.remove(this);
    }

    @Override
    protected void ReceiverAccept(Player p) {
        Player s = Bukkit.getPlayer(sender.uuid);
        if (s != null) {
            TradeMenu t1 = new TradeMenu(PlayerMenuUtility.getPlayerMenuUtility(p));
            TradeMenu t2 = new TradeMenu(PlayerMenuUtility.getPlayerMenuUtility(s));
            t1.you = t2;
            t2.you = t1;
            t1.open();
            t2.open();
        }
        p.sendMessage(ChatColor.GRAY + "거래 요청에 수락했습니다.");
    }

    @Override
    protected void ReceiverRefuse(Player p) {
        p.sendMessage(ChatColor.GRAY + "거래 요청에 거절했습니다.");
    }

    @Override
    protected void SenderAccept(Player p) {
        p.sendMessage(ChatColor.GRAY + "상대가 거래 요청에 수락했습니다.");
    }

    @Override
    protected void SenderRefuse(Player p) {
        p.sendMessage(ChatColor.GRAY + "상대가 거래 요청에 거절했습니다.");
    }
}
