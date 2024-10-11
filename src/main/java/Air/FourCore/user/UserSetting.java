package Air.FourCore.user;

import org.bson.Document;
import org.bukkit.entity.Player;

public class UserSetting {

    // on DB
    public boolean muteActionBar = false;
    public boolean muteBroadcast = false;
    public boolean isBuyNetherStar = false;
    public boolean isGetBasicItem = false;
    public boolean isRewarded = false;
    public boolean isRewarded_1h = false;
    public boolean isRewarded_2h = false;
    public boolean isRewarded_3h = false;
    public boolean isRewarded_4h = false;
    public int dailyPlayTime = 0;

    // on yml
    public int countGambling = 0;

    // none DB
    public boolean checkRewardActivate = false;
    public boolean ignoreSpeed = false;
    public ChatMod chatMod = ChatMod.일반;
    public Player latestWhisper = null;
    public boolean battleMode = false;

    public enum Role{
        대표, 관리자, 가이드, 일반유저
    }

    public enum ChatMod{
        일반, 국가, 동맹, 가이드
    }

    public void setData(Document doc) {
        try {
            muteActionBar = doc.getBoolean("muteActionBar");
            muteBroadcast = doc.getBoolean("muteBroadcast");
            try {
                isBuyNetherStar = doc.getBoolean("isBuyNetherStar");
            }catch (Exception e){}
            isGetBasicItem = doc.getBoolean("isGetBasicItem");
            isRewarded = doc.getBoolean("isRewarded");
            isRewarded_1h = doc.getBoolean("isRewarded_1h");
            isRewarded_2h = doc.getBoolean("isRewarded_2h");
            isRewarded_3h = doc.getBoolean("isRewarded_3h");
            isRewarded_4h = doc.getBoolean("isRewarded_4h");
            dailyPlayTime = doc.getInteger("dailyPlayTime");
        }catch (Exception e){}
    }

    public Document getDocument(){
        return new Document()
                .append("muteActionBar", muteActionBar)
                .append("muteBroadcast", muteBroadcast)
                .append("isBuyNetherStar", isBuyNetherStar)
                .append("isGetBasicItem", isGetBasicItem)
                .append("isRewarded", isRewarded)
                .append("isRewarded_1h", isRewarded_1h)
                .append("isRewarded_2h", isRewarded_2h)
                .append("isRewarded_3h", isRewarded_3h)
                .append("isRewarded_4h", isRewarded_4h)
                .append("dailyPlayTime", dailyPlayTime);
    }

}
