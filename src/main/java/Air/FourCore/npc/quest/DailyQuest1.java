package Air.FourCore.npc.quest;

import Air.FourCore.item.CustomItemUtility;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import Air.FourCore.user.UserData;

public class DailyQuest1 extends Quest{
    public DailyQuest1(UserData data) {
        super(data);
    }

    @Override
    public QuestKind getKind() {
        return QuestKind.daily;
    }

    @Override
    public int getTotal() {
        return 1000;
    }

    @Override
    public String getTarget() {
        return " - ";
    }

    @Override
    public String getNpc() {
        return "일일퀘스트";
    }

    @Override
    public String getName() {
        return getTotal()+"걸음을 걸어다니세요. ("+current+"/"+getTotal()+")";
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
