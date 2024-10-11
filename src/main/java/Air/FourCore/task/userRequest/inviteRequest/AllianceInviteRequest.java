package Air.FourCore.task.userRequest.inviteRequest;

import Air.FourCore.item.CustomItemUtility;
import Air.FourCore.nation.NationData;
import Air.FourCore.nation.NationUtility;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import Air.FourCore.user.UserData;

public class AllianceInviteRequest extends InviteRequest {

    public NationData nation;
    public NationData targetNation;

    public AllianceInviteRequest(long timer, UserData data, UserData sender, NationData nation, NationData targetNation) {
        super(timer, data, sender);
        this.nation = nation;
        this.targetNation = targetNation;
    }

    @Override
    public void tick() {
        if (timer % 2 == 0) {
            Player p = Bukkit.getPlayer(data.uuid);
            if (p != null) {
                p.sendMessage(ChatColor.GREEN + nation.nationName + ChatColor.RESET + " 국가로부터 동맹을 제안받았습니다!");
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a/수락 &f또는 &c/거절&f 명령어를 쳐주세요. &7" + timer + "초 남았습니다."));
            }
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
        nation.allies.add(targetNation.nationName);
        targetNation.allies.add(nation.nationName);

        Player pp = Bukkit.getPlayer(data.uuid);
        Player ss = Bukkit.getPlayer(sender.uuid);
        if (pp != null) {
            CustomItemUtility.remove(pp.getInventory(), CustomItemUtility.AllianceTicketItem(), 1);
        }
        if (ss != null) {
            CustomItemUtility.remove(ss.getInventory(), CustomItemUtility.AllianceTicketItem(), 1);
        }
        NationUtility.broadcastAlliance(nation, "동맹이 체결되었습니다. &a" + nation.nationName + "&f 국과 &6" + targetNation.nationName + "&f 국은 현 시간부로 동맹입니다!");
    }

    @Override
    protected void ReceiverRefuse(Player p) {
        p.sendMessage(ChatColor.GRAY + "초대에 거절했습니다.");
    }

    @Override
    protected void SenderAccept(Player p) {
        p.sendMessage(ChatColor.GRAY + "상대국이 동맹에 수락했습니다.");
    }

    @Override
    protected void SenderRefuse(Player p) {
        p.sendMessage(ChatColor.GRAY + "상대국이 동맹에 거절했습니다.");
    }
}
