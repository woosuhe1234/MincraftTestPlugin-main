package Air.FourCore.npc.quest;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import Air.FourCore.user.UserData;

public abstract class Quest {
    public UserData data;
    public int current = 0;

    public Quest(UserData data, int current) {
        this(data);
        this.current = current;
    }

    public Quest(UserData data) {
        Player p = Bukkit.getPlayer(data.uuid);
        int i = getKind().ordinal();
        this.data = data;
        if (getRecord() != null) {
            if (getRecord()) {
                Bukkit.getPlayer(data.uuid).sendTitle("", "이미 완료한 퀘스트입니다.", 10, 60, 20);
                return;
            }
        }
        if (data.questList[i] == null) {
            data.questList[i] = this;
            if (p != null) {
                p.sendTitle("", "퀘스트를 받으셨습니다.", 10, 60, 20);
                p.playSound(p.getLocation(), Sound.ENTITY_CHICKEN_EGG, 1, 1);
            }
        } else {
            if (data.questList[i].getClass().isInstance(this)) {
                if (data.questList[i].current >= data.questList[i].getTotal()) {
                    if (p != null) {
                        p.sendTitle("", "퀘스트 완료!", 10, 60, 20);
                        p.playSound(p.getLocation(), Sound.ENTITY_CHICKEN_EGG, 1, 1);
                    }
                    data.questList[i].clear();
                } else {
                    if (p != null) {
                        p.sendTitle("", "이미 퀘스트를 진행중 입니다.", 10, 60, 20);
                    }
                }
            } else {
                if (p != null) {
                    p.sendTitle("", "이미 다른 퀘스트를 받으셨습니다.", 10, 60, 20);
                }
            }
        }
    }

    public void add() {
        current++;
        Bukkit.getPlayer(data.uuid).sendMessage("퀘스트 진행도 (" + current + "/" + getTotal() + ")");
        if (current >= getTotal()) {
            Bukkit.getPlayer(data.uuid).sendTitle("", "퀘스트 조건 달성! 보상을 받으러가세요.", 10, 40, 20);
        }
    }

    public void clear() {
        data.questList[getKind().ordinal()] = null;
        setRecord();
        reward();
    }

    public abstract QuestKind getKind();

    public abstract int getTotal();

    public abstract String getTarget();

    public abstract String getNpc();

    public abstract String getName();

    public abstract String getRewardString();

    public abstract Boolean getRecord();

    public abstract void setRecord();

    public abstract void reward();

    enum QuestKind {
        main, normal, daily
    }
}
