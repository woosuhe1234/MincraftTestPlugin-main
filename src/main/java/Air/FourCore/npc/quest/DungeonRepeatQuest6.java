package Air.FourCore.npc.quest;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import Air.FourCore.FourCore;
import Air.FourCore.user.UserData;

public class DungeonRepeatQuest6 extends Quest{
    public DungeonRepeatQuest6(UserData data) {
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
        return "6던전반복퀘";
    }

    @Override
    public String getName() {
        return "창기병을 "+getTotal()+"마리 잡으세요. ("+current+"/"+getTotal()+")";
    }
    @Override
    public String getRewardString() {
        return "보상: 1만원, 6,000경험치";
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
        data.addExp(6000);
        OfflinePlayer p = Bukkit.getOfflinePlayer(data.uuid);
        FourCore.economy.depositPlayer(p, 10000);
        p.getPlayer().sendMessage(getRewardString());
    }
}
