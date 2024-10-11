package Air.FourCore.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

import java.util.Arrays;

public class ServerEvent implements Listener {

    @EventHandler
    public void onWeatherChange(WeatherChangeEvent e){
        // 날씨 고정
        e.setCancelled(true);
    }


    @EventHandler
    public void onInventoryMoveItem(PlayerCommandPreprocessEvent e) {
        // 명령어 막기
        String[] block = {"/back", "/sethome", "/home", "/tpa", "/help", "/pl"};
        String[] block1 = {"list"};

        String msg = e.getMessage();
        String[] args = msg.split(" ");
        Player p = e.getPlayer();
        boolean blocked = false;
        if(Arrays.asList(block).contains(args[0].toLowerCase()))
            blocked = true;;
        if(args.length >= 2 && Arrays.asList(block1).contains(args[1].toLowerCase()))
            blocked = true;

        if(blocked && !e.getPlayer().isOp()){
            e.setCancelled(true);
            p.sendMessage("그 명령어는 사용할 수 없습니다.");
        }
    }
}
