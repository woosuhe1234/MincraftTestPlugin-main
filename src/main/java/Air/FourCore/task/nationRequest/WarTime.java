package Air.FourCore.task.nationRequest;

import Air.FourCore.mongoDB.MongoDB;
import Air.FourCore.nation.NationData;
import Air.FourCore.nation.NationUtility;
import Air.FourCore.user.UserData;
import com.mongodb.client.model.UpdateOptions;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import Air.FourCore.FourCore;

import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class WarTime extends WarTimerable {

    public WarTime(long timer, NationData nation, NationData targetNation) {
        super(timer, nation, targetNation);
    }

    @Override
    public void tick() {

    }

    @Override
    public void end() {
        super.stop();
        //수성 성공
        targetNation.score++;
    }

    @Override
    public void stop(){
        super.stop();
    }

    public void destroy(boolean reverse){
        if(reverse){
            NationData temp = nation;
            nation = targetNation;
            targetNation = temp;
        }

        stop();
        List<UserData> losers = targetNation.getAllPlayers();
        double total = 0;
        for(UserData loser : losers){
            try {
                OfflinePlayer p = Bukkit.getOfflinePlayer(loser.uuid);
                //total += FourCore.economy.getBalance(p) * 0.1;
                //FourCore.economy.withdrawPlayer(p, FourCore.economy.getBalance(p) * 0.1);

                Document doc = new Document()
                        .append("money", FourCore.economy.getBalance(p));
                UpdateOptions options = new UpdateOptions().upsert(true);
                MongoDB.user.updateOne(eq("uuid", loser.uuid.toString()), new Document("$set", doc), options);
            }catch (Exception e){}
        }

        nation.money += total;
        nation.money += targetNation.money;
        NationUtility.broadcastTitleAlliance(nation, "&6[ 국가 전쟁 ]", "&6"+targetNation.nationName+" &f국가 멸망");
        NationUtility.broadcastAlliance(nation, NationUtility.nation("상대국가 금고에 있던 &a"+String.format("%,d", (long)targetNation.money)+"원&f이 국가 금고에 입금되었습니다."));
        //broadcastAlliance(nation, nation("상대국가원 총재산의 &c10%&f인 &a"+String.format("%,d", (long)total)+"원&f이 국가 금고에 입금되었습니다.\n"));
        Bukkit.broadcastMessage(NationUtility.nation("&6"+targetNation.nationName+" &f국가의 성이 더이상 존재하지 않아 멸망합니다."));
        Bukkit.broadcastMessage(NationUtility.nation("&6"+nation.nationName+" &f국가가 공성전에서 승리하였습니다!"));
        targetNation.money = 0;
        targetNation.remove();
    }

}
