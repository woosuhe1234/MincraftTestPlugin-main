package Air.FourCore.npc.quest;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import Air.FourCore.FourCore;
import Air.FourCore.user.UserData;

public class DungeonQuest1 extends Quest{
    public DungeonQuest1(UserData data) {
        super(data);
    }

    @Override
    public QuestKind getKind() {
        return QuestKind.normal;
    }

    @Override
    public int getTotal() {
        return 50;
    }

    @Override
    public String getTarget() {
        return "성실한 광부";
    }

    @Override
    public String getNpc() {
        return "1던전퀘스트";
    }

    @Override
    public String getName() {
        return "성실한 광부를 "+getTotal()+"마리 잡으세요. ("+current+"/"+getTotal()+")";
    }
    @Override
    public String getRewardString() {
        return "보상: 10만원, 3,300경험치";
    }
    @Override
    public Boolean getRecord() {
        return data.record.dungeonQuestClear.get(0);
    }

    @Override
    public void setRecord() {
        data.record.dungeonQuestClear.set(0, true);
    }

    @Override
    public void reward() {
        data.addExp(3300);
        OfflinePlayer p = Bukkit.getOfflinePlayer(data.uuid);
        FourCore.economy.depositPlayer(p, 100000);
        p.getPlayer().sendMessage(getRewardString());
    }
}
