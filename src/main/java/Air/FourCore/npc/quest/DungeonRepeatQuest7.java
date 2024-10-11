package Air.FourCore.npc.quest;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import Air.FourCore.FourCore;
import Air.FourCore.user.UserData;

public class DungeonRepeatQuest7 extends Quest{
    public DungeonRepeatQuest7(UserData data) {
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
        return "7던전반복퀘";
    }

    @Override
    public String getName() {
        return "스윔가이를 "+getTotal()+"마리 잡으세요. ("+current+"/"+getTotal()+")";
    }
    @Override
    public String getRewardString() {
        return "보상: 1만원, 20,000경험치";
    }
    @Override
    public Boolean getRecord() {
        return false;
    }

    @Override
    public void setRecord() {

    }

    @Override
    public void reward() {
        data.addExp(20000);
        OfflinePlayer p = Bukkit.getOfflinePlayer(data.uuid);
        FourCore.economy.depositPlayer(p, 10000);
        p.getPlayer().sendMessage(getRewardString());
    }
}
