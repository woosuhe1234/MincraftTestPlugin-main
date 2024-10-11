package Air.FourCore.npc.quest;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import Air.FourCore.FourCore;
import Air.FourCore.user.UserData;

public class DungeonQuest3 extends Quest{
    public DungeonQuest3(UserData data) {
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
        return "설인";
    }

    @Override
    public String getNpc() {
        return "3던전퀘스트";
    }

    @Override
    public String getName() {
        return "설인을 "+getTotal()+"마리 잡으세요. ("+current+"/"+getTotal()+")";
    }
    @Override
    public String getRewardString() {
        return "보상: 30만원, 10,800경험치";
    }
    @Override
    public Boolean getRecord() {
        return data.record.dungeonQuestClear.get(2);
    }

    @Override
    public void setRecord() {
        data.record.dungeonQuestClear.set(2, true);
    }

    @Override
    public void reward() {
        data.addExp(10800);
        OfflinePlayer p = Bukkit.getOfflinePlayer(data.uuid);
        FourCore.economy.depositPlayer(p, 300000);
        p.getPlayer().sendMessage(getRewardString());
    }
}
