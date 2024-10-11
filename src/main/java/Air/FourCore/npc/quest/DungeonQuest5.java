package Air.FourCore.npc.quest;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import Air.FourCore.FourCore;
import Air.FourCore.user.UserData;

public class DungeonQuest5 extends Quest{
    public DungeonQuest5(UserData data) {
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
        return "페어리";
    }

    @Override
    public String getNpc() {
        return "5던전퀘스트";
    }

    @Override
    public String getName() {
        return "페어리를 "+getTotal()+"마리 잡으세요. ("+current+"/"+getTotal()+")";
    }
    @Override
    public String getRewardString() {
        return "보상: 50만원, 65,000경험치";
    }
    @Override
    public Boolean getRecord() {
        return data.record.dungeonQuestClear.get(4);
    }

    @Override
    public void setRecord() {
        data.record.dungeonQuestClear.set(4, true);
    }

    @Override
    public void reward() {
        data.addExp(65000);
        OfflinePlayer p = Bukkit.getOfflinePlayer(data.uuid);
        FourCore.economy.depositPlayer(p, 500000);
        p.getPlayer().sendMessage(getRewardString());
    }
}
