package Air.FourCore.npc.quest;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import Air.FourCore.FourCore;
import Air.FourCore.user.UserData;

public class DungeonQuest8 extends Quest{
    public DungeonQuest8(UserData data) {
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
        return "사헬의 공포";
    }

    @Override
    public String getNpc() {
        return "8던전퀘스트";
    }

    @Override
    public String getName() {
        return "사헬의 공포를 "+getTotal()+"마리 잡으세요. ("+current+"/"+getTotal()+")";
    }
    @Override
    public String getRewardString() {
        return "보상: 100만원, 1,100,000경험치";
    }
    @Override
    public Boolean getRecord() {
        return data.record.dungeonQuestClear.get(7);
    }

    @Override
    public void setRecord() {
        data.record.dungeonQuestClear.set(7, true);
    }

    @Override
    public void reward() {
        data.addExp(1100000);
        OfflinePlayer p = Bukkit.getOfflinePlayer(data.uuid);
        FourCore.economy.depositPlayer(p, 1000000);
        p.getPlayer().sendMessage(getRewardString());
    }
}
