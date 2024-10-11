package Air.FourCore.npc.quest;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import Air.FourCore.FourCore;
import Air.FourCore.user.UserData;

public class DungeonQuest7 extends Quest{
    public DungeonQuest7(UserData data) {
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
        return "스윔가이";
    }

    @Override
    public String getNpc() {
        return "7던전퀘스트";
    }

    @Override
    public String getName() {
        return "스윔가이를 "+getTotal()+"마리 잡으세요. ("+current+"/"+getTotal()+")";
    }
    @Override
    public String getRewardString() {
        return "보상: 80만원, 480,000경험치";
    }
    @Override
    public Boolean getRecord() {
        return data.record.dungeonQuestClear.get(6);
    }

    @Override
    public void setRecord() {
        data.record.dungeonQuestClear.set(6, true);
    }

    @Override
    public void reward() {
        data.addExp(480000);
        OfflinePlayer p = Bukkit.getOfflinePlayer(data.uuid);
        FourCore.economy.depositPlayer(p, 800000);
        p.getPlayer().sendMessage(getRewardString());
    }
}
