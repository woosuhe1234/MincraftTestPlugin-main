package Air.FourCore;

import Air.FourCore.menuSystem.MenuUtility;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class BossRoom{
    Player player = null;
    int time = 0;

    public void Tick(){
        if(player != null){
            if(!player.isOnline() || !player.getWorld().getName().equalsIgnoreCase("VS")){
                player = null;
                return;
            }

            time--;
            if(time <= 0){
                if(player.getWorld().getName().equalsIgnoreCase("VS")){
                    Location loc = MenuUtility.getLocation("로비");
                    if(loc != null) {
                        player.teleport(loc);
                        player.sendMessage("보스 방의 시간이 초과되어 로비로 이동합니다.");
                    }
                }
                player = null;
                time = 0;
            }
        }
    }

    public boolean TryJoin(Player p){
        if(player == null){
            player = p;
            time = 480;
            return true;
        }else{
            return false;
        }
    }
}