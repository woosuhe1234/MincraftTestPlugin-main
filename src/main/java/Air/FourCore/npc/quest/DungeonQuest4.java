package Air.FourCore.npc.quest;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import Air.FourCore.FourCore;
import Air.FourCore.user.UserData;

public class DungeonQuest4 extends Quest{
    public DungeonQuest4(UserData data) {
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
        return "카투스";
    }

    @Override
    public String getNpc() {
        return "4던전퀘스트";
    }

    @Override
    public String getName() {
        return "카투스를 "+getTotal()+"마리 잡으세요. ("+current+"/"+getTotal()+")";
    }
    @Override
    public String getRewardString() {
        return "보상: 40만원, 26,000경험치";
    }
    @Override
    public Boolean getRecord() {
        return data.record.dungeonQuestClear.get(3);
    }

    @Override
    public void setRecord() {
        data.record.dungeonQuestClear.set(3, true);
    }

    @Override
    public void reward() {
        data.addExp(26000);
        OfflinePlayer p = Bukkit.getOfflinePlayer(data.uuid);
        FourCore.economy.depositPlayer(p, 400000);
        p.getPlayer().sendMessage(getRewardString());
    }
}