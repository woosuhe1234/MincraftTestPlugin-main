package Air.FourCore.npc.quest;

import Air.FourCore.item.CustomItemUtility;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import Air.FourCore.user.UserData;

public class DailyQuest2 extends Quest{
    public DailyQuest2(UserData data) {
        super(data);
    }

    @Override
    public QuestKind getKind() {
        return QuestKind.daily;
    }

    @Override
    public int getTotal() {
        return 100;
    }

    @Override
    public String getTarget() {
        return "ALL";
    }

    @Override
    public String getNpc() {
        return "일일퀘스트";
    }

    @Override
    public String getName() {
        return "아무 몬스터를 "+getTotal()+"마리 잡으세요. ("+current+"/"+getTotal()+")";
    }

    @Override
    public String getRewardString() {
        return "보상: 거래가능 네더의별 1개";
    }

    @Override
    public Boolean getRecord() {
        return null;
    }

    @Override
    public void setRecord() {

    }

    @Override
    public void reward() {
        OfflinePlayer p = Bukkit.getOfflinePlayer(data.uuid);
        p.getPlayer().getInventory().addItem(CustomItemUtility.NetherStartItemNone(1));
        p.getPlayer().sendMessage(getRewardString());
    }
}
