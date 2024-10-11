package Air.FourCore.npc.quest;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import Air.FourCore.FourCore;
import Air.FourCore.user.UserData;

public class DungeonQuest2 extends Quest{
    public DungeonQuest2(UserData data) {
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
        return "황소";
    }

    @Override
    public String getNpc() {
        return "2던전퀘스트";
    }

    @Override
    public String getName() {
        return "황소를 "+getTotal()+"마리 잡으세요. ("+current+"/"+getTotal()+")";
    }
    @Override
    public String getRewardString() {
        return "보상: 20만원, 5,700경험치";
    }
    @Override
    public Boolean getRecord() {
        return data.record.dungeonQuestClear.get(1);
    }

    @Override
    public void setRecord() {
        data.record.dungeonQuestClear.set(1, true);
    }

    @Override
    public void reward() {
        data.addExp(5700);
        OfflinePlayer p = Bukkit.getOfflinePlayer(data.uuid);
        FourCore.economy.depositPlayer(p, 200000);
        p.getPlayer().sendMessage(getRewardString());
    }
}
