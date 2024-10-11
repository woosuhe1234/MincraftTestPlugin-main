package Air.FourCore.npc.quest;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import Air.FourCore.FourCore;
import Air.FourCore.user.UserData;

public class DungeonQuest6 extends Quest{
    public DungeonQuest6(UserData data) {
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
        return "창기병";
    }

    @Override
    public String getNpc() {
        return "6던전퀘스트";
    }

    @Override
    public String getName() {
        return "창기병을 "+getTotal()+"마리 잡으세요. ("+current+"/"+getTotal()+")";
    }
    @Override
    public String getRewardString() {
        return "보상: 70만원, 150,000경험치";
    }
    @Override
    public Boolean getRecord() {
        return data.record.dungeonQuestClear.get(5);
    }

    @Override
    public void setRecord() {
        data.record.dungeonQuestClear.set(5, true);
    }

    @Override
    public void reward() {
        data.addExp(150000);
        OfflinePlayer p = Bukkit.getOfflinePlayer(data.uuid);
        FourCore.economy.depositPlayer(p, 700000);
        p.getPlayer().sendMessage(getRewardString());
    }
}
